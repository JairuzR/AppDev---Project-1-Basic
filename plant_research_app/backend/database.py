import sqlite3
import os

DATABASE_PATH = 'plant_research.db'

def get_db_connection():
    """Create and return a database connection."""
    conn = sqlite3.connect(DATABASE_PATH)
    conn.row_factory = sqlite3.Row
    return conn

def init_database():
    """Initialize the database with all tables and sample data."""
    conn = get_db_connection()
    cursor = conn.cursor()
    
    # Create ResearcherInformation table
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS ResearcherInformation (
            ResearcherID INTEGER PRIMARY KEY AUTOINCREMENT,
            FirstName TEXT NOT NULL,
            LastName TEXT NOT NULL,
            Email TEXT,
            PhoneNumber TEXT,
            Affiliation TEXT
        )
    ''')
    
    # Create SamplingLocation table
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS SamplingLocation (
            LocationID INTEGER PRIMARY KEY AUTOINCREMENT,
            Latitude REAL,
            Longitude REAL,
            LocationDescription TEXT,
            Region TEXT,
            Country TEXT
        )
    ''')
    
    # Create PlantSpecies table
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS PlantSpecies (
            SpeciesID INTEGER PRIMARY KEY AUTOINCREMENT,
            ScientificName TEXT NOT NULL,
            CommonName TEXT
        )
    ''')
    
    # Create SamplingEvent table
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS SamplingEvent (
            SamplingEventID INTEGER PRIMARY KEY AUTOINCREMENT,
            ResearcherID INTEGER,
            LocationID INTEGER,
            EventDate TEXT,
            EventDescription TEXT,
            Notes TEXT,
            FOREIGN KEY (ResearcherID) REFERENCES ResearcherInformation(ResearcherID),
            FOREIGN KEY (LocationID) REFERENCES SamplingLocation(LocationID)
        )
    ''')
    
    # Create SoilComposition table
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS SoilComposition (
            SoilCompositionID INTEGER PRIMARY KEY AUTOINCREMENT,
            SoilPH REAL,
            SoilNutrients TEXT,
            SoilType TEXT
        )
    ''')
    
    # Create EnvironmentalCondition table
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS EnvironmentalCondition (
            EnvConditionID INTEGER PRIMARY KEY AUTOINCREMENT,
            SampleID INTEGER,
            SoilCompositionID INTEGER,
            Altitude REAL,
            Temperature REAL,
            Humidity REAL,
            FOREIGN KEY (SoilCompositionID) REFERENCES SoilComposition(SoilCompositionID)
        )
    ''')
    
    # Create PlantSample table
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS PlantSample (
            SampleID INTEGER PRIMARY KEY AUTOINCREMENT,
            ResearcherID INTEGER,
            LocationID INTEGER,
            SpeciesID INTEGER,
            SamplingEventID INTEGER,
            EnvConditionID INTEGER,
            SamplingDate TEXT,
            SampleDescription TEXT,
            FOREIGN KEY (ResearcherID) REFERENCES ResearcherInformation(ResearcherID),
            FOREIGN KEY (LocationID) REFERENCES SamplingLocation(LocationID),
            FOREIGN KEY (SpeciesID) REFERENCES PlantSpecies(SpeciesID),
            FOREIGN KEY (SamplingEventID) REFERENCES SamplingEvent(SamplingEventID),
            FOREIGN KEY (EnvConditionID) REFERENCES EnvironmentalCondition(EnvConditionID)
        )
    ''')
    
    # Insert sample data for testing
    # Check if data already exists
    cursor.execute('SELECT COUNT(*) FROM ResearcherInformation')
    if cursor.fetchone()[0] == 0:
        # Insert sample researchers
        cursor.execute('''
            INSERT INTO ResearcherInformation (FirstName, LastName, Email, PhoneNumber, Affiliation)
            VALUES 
                ('John', 'Smith', 'john.smith@research.edu', '555-0101', 'University of Botany'),
                ('Maria', 'Garcia', 'maria.garcia@research.edu', '555-0102', 'Institute of Plant Sciences'),
                ('David', 'Chen', 'david.chen@research.edu', '555-0103', 'Botanical Research Center')
        ''')
        
        # Insert sample locations
        cursor.execute('''
            INSERT INTO SamplingLocation (Latitude, Longitude, LocationDescription, Region, Country)
            VALUES 
                (40.7128, -74.0060, 'Central Park Forest', 'New York', 'USA'),
                (34.0522, -118.2437, 'Angeles National Forest', 'California', 'USA'),
                (51.5074, -0.1278, 'Kew Gardens', 'London', 'UK')
        ''')
        
        # Insert sample species
        cursor.execute('''
            INSERT INTO PlantSpecies (ScientificName, CommonName)
            VALUES 
                ('Quercus alba', 'White Oak'),
                ('Acer saccharum', 'Sugar Maple'),
                ('Pinus strobus', 'Eastern White Pine'),
                ('Betula papyrifera', 'Paper Birch')
        ''')
        
        # Insert sample soil compositions
        cursor.execute('''
            INSERT INTO SoilComposition (SoilPH, SoilNutrients, SoilType)
            VALUES 
                (6.5, 'High nitrogen, moderate phosphorus', 'Loamy'),
                (7.0, 'Balanced NPK', 'Clay'),
                (5.8, 'Low nitrogen, high organic matter', 'Sandy loam')
        ''')
        
        # Insert sample environmental conditions
        cursor.execute('''
            INSERT INTO EnvironmentalCondition (SampleID, SoilCompositionID, Altitude, Temperature, Humidity)
            VALUES 
                (NULL, 1, 150.5, 22.5, 65.0),
                (NULL, 2, 320.0, 18.3, 72.0),
                (NULL, 3, 85.0, 25.1, 58.0)
        ''')
        
        # Insert sample events
        cursor.execute('''
            INSERT INTO SamplingEvent (ResearcherID, LocationID, EventDate, EventDescription, Notes)
            VALUES 
                (1, 1, '2024-03-15', 'Spring Forest Survey', 'Clear weather, optimal conditions'),
                (2, 2, '2024-04-20', 'Mountain Ecosystem Study', 'High altitude sampling'),
                (3, 3, '2024-05-10', 'Urban Garden Research', 'Controlled environment')
        ''')
        
        # Insert sample plant samples
        cursor.execute('''
            INSERT INTO PlantSample (ResearcherID, LocationID, SpeciesID, SamplingEventID, EnvConditionID, SamplingDate, SampleDescription)
            VALUES 
                (1, 1, 1, 1, 1, '2024-03-15', 'Mature oak specimen, healthy leaves'),
                (2, 2, 3, 2, 2, '2024-04-20', 'Young pine tree, cone samples collected'),
                (3, 3, 2, 3, 3, '2024-05-10', 'Maple sapling, early spring growth')
        ''')
    
    conn.commit()
    conn.close()
    print("Database initialized successfully!")

if __name__ == '__main__':
    init_database()
