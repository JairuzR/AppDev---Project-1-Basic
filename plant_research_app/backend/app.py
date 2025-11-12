from flask import Flask, request, jsonify
from flask_cors import CORS
from database import get_db_connection, init_database
import os

app = Flask(__name__)
CORS(app)

# Initialize database on startup
if not os.path.exists('plant_research.db'):
    init_database()

# Helper function to convert row to dict
def row_to_dict(row):
    return dict(row) if row else None

# ==================== DROPDOWN DATA ENDPOINTS ====================

@app.route('/api/researchers', methods=['GET'])
def get_researchers():
    """Get all researchers for dropdown."""
    conn = get_db_connection()
    researchers = conn.execute('SELECT * FROM ResearcherInformation').fetchall()
    conn.close()
    return jsonify([dict(r) for r in researchers])

@app.route('/api/locations', methods=['GET'])
def get_locations():
    """Get all locations for dropdown."""
    conn = get_db_connection()
    locations = conn.execute('SELECT * FROM SamplingLocation').fetchall()
    conn.close()
    return jsonify([dict(l) for l in locations])

@app.route('/api/species', methods=['GET'])
def get_species():
    """Get all plant species for dropdown."""
    conn = get_db_connection()
    species = conn.execute('SELECT * FROM PlantSpecies').fetchall()
    conn.close()
    return jsonify([dict(s) for s in species])

@app.route('/api/events', methods=['GET'])
def get_events():
    """Get all sampling events for dropdown."""
    conn = get_db_connection()
    events = conn.execute('''
        SELECT se.*, 
               ri.FirstName || ' ' || ri.LastName as ResearcherName,
               sl.LocationDescription
        FROM SamplingEvent se
        LEFT JOIN ResearcherInformation ri ON se.ResearcherID = ri.ResearcherID
        LEFT JOIN SamplingLocation sl ON se.LocationID = sl.LocationID
    ''').fetchall()
    conn.close()
    return jsonify([dict(e) for e in events])

@app.route('/api/soil', methods=['GET'])
def get_soil():
    """Get all soil compositions for dropdown."""
    conn = get_db_connection()
    soil = conn.execute('SELECT * FROM SoilComposition').fetchall()
    conn.close()
    return jsonify([dict(s) for s in soil])

@app.route('/api/environmental', methods=['GET'])
def get_environmental():
    """Get all environmental conditions for dropdown."""
    conn = get_db_connection()
    env = conn.execute('''
        SELECT ec.*, sc.SoilType, sc.SoilPH
        FROM EnvironmentalCondition ec
        LEFT JOIN SoilComposition sc ON ec.SoilCompositionID = sc.SoilCompositionID
    ''').fetchall()
    conn.close()
    return jsonify([dict(e) for e in env])

# ==================== PLANT SAMPLE CRUD ENDPOINTS ====================

@app.route('/api/plantsample', methods=['POST'])
def add_plant_sample():
    """Add a new plant sample with all relationships."""
    data = request.json
    
    try:
        conn = get_db_connection()
        cursor = conn.cursor()
        
        # If environmental condition data is provided, create it first
        env_condition_id = data.get('EnvConditionID')
        if data.get('createEnvironmental'):
            cursor.execute('''
                INSERT INTO EnvironmentalCondition (SoilCompositionID, Altitude, Temperature, Humidity)
                VALUES (?, ?, ?, ?)
            ''', (
                data.get('SoilCompositionID'),
                data.get('Altitude'),
                data.get('Temperature'),
                data.get('Humidity')
            ))
            env_condition_id = cursor.lastrowid
        
        # Insert plant sample
        cursor.execute('''
            INSERT INTO PlantSample 
            (ResearcherID, LocationID, SpeciesID, SamplingEventID, EnvConditionID, SamplingDate, SampleDescription)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        ''', (
            data.get('ResearcherID'),
            data.get('LocationID'),
            data.get('SpeciesID'),
            data.get('SamplingEventID'),
            env_condition_id,
            data.get('SamplingDate'),
            data.get('SampleDescription')
        ))
        
        sample_id = cursor.lastrowid
        conn.commit()
        conn.close()
        
        return jsonify({
            'success': True,
            'message': 'Plant sample added successfully',
            'SampleID': sample_id
        }), 201
        
    except Exception as e:
        return jsonify({'success': False, 'error': str(e)}), 400

@app.route('/api/plantsample/<int:sample_id>', methods=['GET'])
def get_plant_sample(sample_id):
    """Get a plant sample with all joined related data."""
    conn = get_db_connection()
    
    # Complex JOIN query to get all related information
    sample = conn.execute('''
        SELECT 
            ps.*,
            ri.FirstName || ' ' || ri.LastName as ResearcherName,
            ri.Email as ResearcherEmail,
            ri.Affiliation as ResearcherAffiliation,
            sl.LocationDescription,
            sl.Latitude,
            sl.Longitude,
            sl.Region,
            sl.Country,
            sp.ScientificName,
            sp.CommonName,
            se.EventDate,
            se.EventDescription,
            se.Notes as EventNotes,
            ec.Altitude,
            ec.Temperature,
            ec.Humidity,
            sc.SoilPH,
            sc.SoilNutrients,
            sc.SoilType
        FROM PlantSample ps
        LEFT JOIN ResearcherInformation ri ON ps.ResearcherID = ri.ResearcherID
        LEFT JOIN SamplingLocation sl ON ps.LocationID = sl.LocationID
        LEFT JOIN PlantSpecies sp ON ps.SpeciesID = sp.SpeciesID
        LEFT JOIN SamplingEvent se ON ps.SamplingEventID = se.SamplingEventID
        LEFT JOIN EnvironmentalCondition ec ON ps.EnvConditionID = ec.EnvConditionID
        LEFT JOIN SoilComposition sc ON ec.SoilCompositionID = sc.SoilCompositionID
        WHERE ps.SampleID = ?
    ''', (sample_id,)).fetchone()
    
    conn.close()
    
    if sample:
        return jsonify(dict(sample))
    else:
        return jsonify({'error': 'Sample not found'}), 404

@app.route('/api/plantsample', methods=['GET'])
def get_all_plant_samples():
    """Get all plant samples with basic joined data."""
    conn = get_db_connection()
    
    samples = conn.execute('''
        SELECT 
            ps.SampleID,
            ps.SamplingDate,
            ps.SampleDescription,
            ri.FirstName || ' ' || ri.LastName as ResearcherName,
            sl.LocationDescription,
            sp.CommonName as SpeciesName,
            sp.ScientificName
        FROM PlantSample ps
        LEFT JOIN ResearcherInformation ri ON ps.ResearcherID = ri.ResearcherID
        LEFT JOIN SamplingLocation sl ON ps.LocationID = sl.LocationID
        LEFT JOIN PlantSpecies sp ON ps.SpeciesID = sp.SpeciesID
        ORDER BY ps.SampleID DESC
    ''').fetchall()
    
    conn.close()
    return jsonify([dict(s) for s in samples])

@app.route('/api/plantsample/<int:sample_id>', methods=['PUT'])
def update_plant_sample(sample_id):
    """Update a plant sample."""
    data = request.json
    
    try:
        conn = get_db_connection()
        cursor = conn.cursor()
        
        # Update environmental condition if needed
        if data.get('updateEnvironmental') and data.get('EnvConditionID'):
            cursor.execute('''
                UPDATE EnvironmentalCondition
                SET SoilCompositionID = ?, Altitude = ?, Temperature = ?, Humidity = ?
                WHERE EnvConditionID = ?
            ''', (
                data.get('SoilCompositionID'),
                data.get('Altitude'),
                data.get('Temperature'),
                data.get('Humidity'),
                data.get('EnvConditionID')
            ))
        
        # Update plant sample
        cursor.execute('''
            UPDATE PlantSample
            SET ResearcherID = ?, LocationID = ?, SpeciesID = ?, 
                SamplingEventID = ?, SamplingDate = ?, SampleDescription = ?
            WHERE SampleID = ?
        ''', (
            data.get('ResearcherID'),
            data.get('LocationID'),
            data.get('SpeciesID'),
            data.get('SamplingEventID'),
            data.get('SamplingDate'),
            data.get('SampleDescription'),
            sample_id
        ))
        
        conn.commit()
        
        if cursor.rowcount == 0:
            conn.close()
            return jsonify({'success': False, 'error': 'Sample not found'}), 404
        
        conn.close()
        return jsonify({'success': True, 'message': 'Plant sample updated successfully'})
        
    except Exception as e:
        return jsonify({'success': False, 'error': str(e)}), 400

@app.route('/api/plantsample/<int:sample_id>', methods=['DELETE'])
def delete_plant_sample(sample_id):
    """Delete a plant sample."""
    try:
        conn = get_db_connection()
        cursor = conn.cursor()
        
        cursor.execute('DELETE FROM PlantSample WHERE SampleID = ?', (sample_id,))
        conn.commit()
        
        if cursor.rowcount == 0:
            conn.close()
            return jsonify({'success': False, 'error': 'Sample not found'}), 404
        
        conn.close()
        return jsonify({'success': True, 'message': 'Plant sample deleted successfully'})
        
    except Exception as e:
        return jsonify({'success': False, 'error': str(e)}), 400

# ==================== HEALTH CHECK ====================

@app.route('/api/health', methods=['GET'])
def health_check():
    """Health check endpoint."""
    return jsonify({'status': 'healthy', 'message': 'Plant Research API is running'})

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
