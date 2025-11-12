class SoilComposition {
  final int soilCompositionId;
  final double? soilPH;
  final String? soilNutrients;
  final String? soilType;

  SoilComposition({
    required this.soilCompositionId,
    this.soilPH,
    this.soilNutrients,
    this.soilType,
  });

  factory SoilComposition.fromJson(Map<String, dynamic> json) {
    return SoilComposition(
      soilCompositionId: json['SoilCompositionID'],
      soilPH: json['SoilPH']?.toDouble(),
      soilNutrients: json['SoilNutrients'],
      soilType: json['SoilType'],
    );
  }

  String get displayName => soilType ?? 'Soil $soilCompositionId';
}
