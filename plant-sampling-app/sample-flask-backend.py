"""
Sample Flask Backend for Plant Sampling Database
This is a reference implementation for testing the mobile app.
Replace with your actual Flask API backend.
"""

from flask import Flask, request, jsonify
from flask_cors import CORS
from datetime import datetime

app = Flask(__name__)
CORS(app)  # Enable CORS for all routes

# In-memory database (for demo purposes)
samples_db = {}
next_id = 1

@app.route('/sample', methods=['POST'])
def add_sample():
    """Create a new plant sample"""
    global next_id
    
    try:
        data = request.get_json()
        
        # Validate required fields
        required_fields = ['ResearcherID', 'LocationID', 'SpeciesID', 
                          'SamplingEventID', 'SamplingDate', 'SampleDescription']
        
        for field in required_fields:
            if field not in data:
                return jsonify({'error': f'Missing required field: {field}'}), 400
        
        # Create sample
        sample_id = next_id
        sample = {
            'SampleID': sample_id,
            'ResearcherID': data['ResearcherID'],
            'LocationID': data['LocationID'],
            'SpeciesID': data['SpeciesID'],
            'SamplingEventID': data['SamplingEventID'],
            'SamplingDate': data['SamplingDate'],
            'SampleDescription': data['SampleDescription'],
            'CreatedAt': datetime.now().isoformat()
        }
        
        samples_db[sample_id] = sample
        next_id += 1
        
        return jsonify({
            'message': 'Sample created successfully',
            'SampleID': sample_id,
            'sample': sample
        }), 201
        
    except Exception as e:
        return jsonify({'error': str(e)}), 500

@app.route('/sample/<int:sample_id>', methods=['GET'])
def get_sample(sample_id):
    """Retrieve a sample by ID"""
    try:
        if sample_id not in samples_db:
            return jsonify({'error': f'Sample {sample_id} not found'}), 404
        
        return jsonify({
            'message': 'Sample retrieved successfully',
            'sample': samples_db[sample_id]
        }), 200
        
    except Exception as e:
        return jsonify({'error': str(e)}), 500

@app.route('/sample/<int:sample_id>', methods=['PUT'])
def update_sample(sample_id):
    """Update an existing sample"""
    try:
        if sample_id not in samples_db:
            return jsonify({'error': f'Sample {sample_id} not found'}), 404
        
        data = request.get_json()
        sample = samples_db[sample_id]
        
        # Update fields if provided
        updatable_fields = ['ResearcherID', 'LocationID', 'SpeciesID', 
                           'SamplingEventID', 'SamplingDate', 'SampleDescription']
        
        for field in updatable_fields:
            if field in data and data[field]:
                sample[field] = data[field]
        
        sample['UpdatedAt'] = datetime.now().isoformat()
        samples_db[sample_id] = sample
        
        return jsonify({
            'message': 'Sample updated successfully',
            'sample': sample
        }), 200
        
    except Exception as e:
        return jsonify({'error': str(e)}), 500

@app.route('/sample/<int:sample_id>', methods=['DELETE'])
def delete_sample(sample_id):
    """Delete a sample"""
    try:
        if sample_id not in samples_db:
            return jsonify({'error': f'Sample {sample_id} not found'}), 404
        
        deleted_sample = samples_db.pop(sample_id)
        
        return jsonify({
            'message': 'Sample deleted successfully',
            'SampleID': sample_id,
            'deleted_sample': deleted_sample
        }), 200
        
    except Exception as e:
        return jsonify({'error': str(e)}), 500

@app.route('/samples', methods=['GET'])
def get_all_samples():
    """Get all samples (bonus endpoint)"""
    return jsonify({
        'message': 'All samples retrieved',
        'count': len(samples_db),
        'samples': list(samples_db.values())
    }), 200

@app.route('/health', methods=['GET'])
def health_check():
    """Health check endpoint"""
    return jsonify({
        'status': 'healthy',
        'message': 'Plant Sampling API is running',
        'samples_count': len(samples_db)
    }), 200

if __name__ == '__main__':
    print("🌱 Plant Sampling Database API")
    print("=" * 50)
    print("Starting Flask server...")
    print("API will be available at: http://localhost:5000")
    print("\nEndpoints:")
    print("  POST   /sample              - Create new sample")
    print("  GET    /sample/<id>         - Get sample by ID")
    print("  PUT    /sample/<id>         - Update sample")
    print("  DELETE /sample/<id>         - Delete sample")
    print("  GET    /samples             - Get all samples")
    print("  GET    /health              - Health check")
    print("=" * 50)
    
    app.run(debug=True, host='0.0.0.0', port=5000)
