import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/researcher.dart';
import '../models/location.dart';
import '../models/plant_species.dart';
import '../models/sampling_event.dart';
import '../models/soil_composition.dart';
import '../models/environmental_condition.dart';
import '../models/plant_sample.dart';

class ApiService {
  // Change this to your backend URL
  // For Android emulator: http://10.0.2.2:5000
  // For iOS simulator: http://localhost:5000
  // For physical device: http://YOUR_COMPUTER_IP:5000
  static const String baseUrl = 'http://10.0.2.2:5000/api';

  // ==================== DROPDOWN DATA ====================

  Future<List<Researcher>> getResearchers() async {
    try {
      final response = await http.get(Uri.parse('$baseUrl/researchers'));
      if (response.statusCode == 200) {
        List<dynamic> data = json.decode(response.body);
        return data.map((json) => Researcher.fromJson(json)).toList();
      } else {
        throw Exception('Failed to load researchers');
      }
    } catch (e) {
      throw Exception('Error fetching researchers: $e');
    }
  }

  Future<List<SamplingLocation>> getLocations() async {
    try {
      final response = await http.get(Uri.parse('$baseUrl/locations'));
      if (response.statusCode == 200) {
        List<dynamic> data = json.decode(response.body);
        return data.map((json) => SamplingLocation.fromJson(json)).toList();
      } else {
        throw Exception('Failed to load locations');
      }
    } catch (e) {
      throw Exception('Error fetching locations: $e');
    }
  }

  Future<List<PlantSpecies>> getSpecies() async {
    try {
      final response = await http.get(Uri.parse('$baseUrl/species'));
      if (response.statusCode == 200) {
        List<dynamic> data = json.decode(response.body);
        return data.map((json) => PlantSpecies.fromJson(json)).toList();
      } else {
        throw Exception('Failed to load species');
      }
    } catch (e) {
      throw Exception('Error fetching species: $e');
    }
  }

  Future<List<SamplingEvent>> getEvents() async {
    try {
      final response = await http.get(Uri.parse('$baseUrl/events'));
      if (response.statusCode == 200) {
        List<dynamic> data = json.decode(response.body);
        return data.map((json) => SamplingEvent.fromJson(json)).toList();
      } else {
        throw Exception('Failed to load events');
      }
    } catch (e) {
      throw Exception('Error fetching events: $e');
    }
  }

  Future<List<SoilComposition>> getSoilCompositions() async {
    try {
      final response = await http.get(Uri.parse('$baseUrl/soil'));
      if (response.statusCode == 200) {
        List<dynamic> data = json.decode(response.body);
        return data.map((json) => SoilComposition.fromJson(json)).toList();
      } else {
        throw Exception('Failed to load soil compositions');
      }
    } catch (e) {
      throw Exception('Error fetching soil compositions: $e');
    }
  }

  Future<List<EnvironmentalCondition>> getEnvironmentalConditions() async {
    try {
      final response = await http.get(Uri.parse('$baseUrl/environmental'));
      if (response.statusCode == 200) {
        List<dynamic> data = json.decode(response.body);
        return data.map((json) => EnvironmentalCondition.fromJson(json)).toList();
      } else {
        throw Exception('Failed to load environmental conditions');
      }
    } catch (e) {
      throw Exception('Error fetching environmental conditions: $e');
    }
  }

  // ==================== PLANT SAMPLE CRUD ====================

  Future<Map<String, dynamic>> addPlantSample(Map<String, dynamic> sampleData) async {
    try {
      final response = await http.post(
        Uri.parse('$baseUrl/plantsample'),
        headers: {'Content-Type': 'application/json'},
        body: json.encode(sampleData),
      );
      
      if (response.statusCode == 201) {
        return json.decode(response.body);
      } else {
        throw Exception('Failed to add plant sample: ${response.body}');
      }
    } catch (e) {
      throw Exception('Error adding plant sample: $e');
    }
  }

  Future<PlantSample> getPlantSample(int sampleId) async {
    try {
      final response = await http.get(Uri.parse('$baseUrl/plantsample/$sampleId'));
      if (response.statusCode == 200) {
        return PlantSample.fromJson(json.decode(response.body));
      } else {
        throw Exception('Failed to load plant sample');
      }
    } catch (e) {
      throw Exception('Error fetching plant sample: $e');
    }
  }

  Future<List<PlantSampleListItem>> getAllPlantSamples() async {
    try {
      final response = await http.get(Uri.parse('$baseUrl/plantsample'));
      if (response.statusCode == 200) {
        List<dynamic> data = json.decode(response.body);
        return data.map((json) => PlantSampleListItem.fromJson(json)).toList();
      } else {
        throw Exception('Failed to load plant samples');
      }
    } catch (e) {
      throw Exception('Error fetching plant samples: $e');
    }
  }

  Future<Map<String, dynamic>> updatePlantSample(int sampleId, Map<String, dynamic> sampleData) async {
    try {
      final response = await http.put(
        Uri.parse('$baseUrl/plantsample/$sampleId'),
        headers: {'Content-Type': 'application/json'},
        body: json.encode(sampleData),
      );
      
      if (response.statusCode == 200) {
        return json.decode(response.body);
      } else {
        throw Exception('Failed to update plant sample: ${response.body}');
      }
    } catch (e) {
      throw Exception('Error updating plant sample: $e');
    }
  }

  Future<Map<String, dynamic>> deletePlantSample(int sampleId) async {
    try {
      final response = await http.delete(Uri.parse('$baseUrl/plantsample/$sampleId'));
      
      if (response.statusCode == 200) {
        return json.decode(response.body);
      } else {
        throw Exception('Failed to delete plant sample: ${response.body}');
      }
    } catch (e) {
      throw Exception('Error deleting plant sample: $e');
    }
  }

  Future<bool> checkHealth() async {
    try {
      final response = await http.get(Uri.parse('$baseUrl/health'));
      return response.statusCode == 200;
    } catch (e) {
      return false;
    }
  }
}
