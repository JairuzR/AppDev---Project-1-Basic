class PlantSample {
  final int sampleId;
  final int? researcherId;
  final int? locationId;
  final int? speciesId;
  final int? samplingEventId;
  final int? envConditionId;
  final String? samplingDate;
  final String? sampleDescription;
  
  // Joined data
  final String? researcherName;
  final String? researcherEmail;
  final String? researcherAffiliation;
  final String? locationDescription;
  final double? latitude;
  final double? longitude;
  final String? region;
  final String? country;
  final String? scientificName;
  final String? commonName;
  final String? eventDate;
  final String? eventDescription;
  final String? eventNotes;
  final double? altitude;
  final double? temperature;
  final double? humidity;
  final double? soilPH;
  final String? soilNutrients;
  final String? soilType;

  PlantSample({
    required this.sampleId,
    this.researcherId,
    this.locationId,
    this.speciesId,
    this.samplingEventId,
    this.envConditionId,
    this.samplingDate,
    this.sampleDescription,
    this.researcherName,
    this.researcherEmail,
    this.researcherAffiliation,
    this.locationDescription,
    this.latitude,
    this.longitude,
    this.region,
    this.country,
    this.scientificName,
    this.commonName,
    this.eventDate,
    this.eventDescription,
    this.eventNotes,
    this.altitude,
    this.temperature,
    this.humidity,
    this.soilPH,
    this.soilNutrients,
    this.soilType,
  });

  factory PlantSample.fromJson(Map<String, dynamic> json) {
    return PlantSample(
      sampleId: json['SampleID'],
      researcherId: json['ResearcherID'],
      locationId: json['LocationID'],
      speciesId: json['SpeciesID'],
      samplingEventId: json['SamplingEventID'],
      envConditionId: json['EnvConditionID'],
      samplingDate: json['SamplingDate'],
      sampleDescription: json['SampleDescription'],
      researcherName: json['ResearcherName'],
      researcherEmail: json['ResearcherEmail'],
      researcherAffiliation: json['ResearcherAffiliation'],
      locationDescription: json['LocationDescription'],
      latitude: json['Latitude']?.toDouble(),
      longitude: json['Longitude']?.toDouble(),
      region: json['Region'],
      country: json['Country'],
      scientificName: json['ScientificName'],
      commonName: json['CommonName'],
      eventDate: json['EventDate'],
      eventDescription: json['EventDescription'],
      eventNotes: json['EventNotes'],
      altitude: json['Altitude']?.toDouble(),
      temperature: json['Temperature']?.toDouble(),
      humidity: json['Humidity']?.toDouble(),
      soilPH: json['SoilPH']?.toDouble(),
      soilNutrients: json['SoilNutrients'],
      soilType: json['SoilType'],
    );
  }
}

class PlantSampleListItem {
  final int sampleId;
  final String? samplingDate;
  final String? sampleDescription;
  final String? researcherName;
  final String? locationDescription;
  final String? speciesName;
  final String? scientificName;

  PlantSampleListItem({
    required this.sampleId,
    this.samplingDate,
    this.sampleDescription,
    this.researcherName,
    this.locationDescription,
    this.speciesName,
    this.scientificName,
  });

  factory PlantSampleListItem.fromJson(Map<String, dynamic> json) {
    return PlantSampleListItem(
      sampleId: json['SampleID'],
      samplingDate: json['SamplingDate'],
      sampleDescription: json['SampleDescription'],
      researcherName: json['ResearcherName'],
      locationDescription: json['LocationDescription'],
      speciesName: json['SpeciesName'],
      scientificName: json['ScientificName'],
    );
  }
}
