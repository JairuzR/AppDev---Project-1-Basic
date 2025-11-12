class PlantSpecies {
  final int speciesId;
  final String scientificName;
  final String? commonName;

  PlantSpecies({
    required this.speciesId,
    required this.scientificName,
    this.commonName,
  });

  factory PlantSpecies.fromJson(Map<String, dynamic> json) {
    return PlantSpecies(
      speciesId: json['SpeciesID'],
      scientificName: json['ScientificName'],
      commonName: json['CommonName'],
    );
  }

  String get displayName => commonName ?? scientificName;
}
