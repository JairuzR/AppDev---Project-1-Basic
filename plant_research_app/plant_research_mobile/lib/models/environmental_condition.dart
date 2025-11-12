class EnvironmentalCondition {
  final int envConditionId;
  final int? sampleId;
  final int? soilCompositionId;
  final double? altitude;
  final double? temperature;
  final double? humidity;
  final String? soilType;
  final double? soilPH;

  EnvironmentalCondition({
    required this.envConditionId,
    this.sampleId,
    this.soilCompositionId,
    this.altitude,
    this.temperature,
    this.humidity,
    this.soilType,
    this.soilPH,
  });

  factory EnvironmentalCondition.fromJson(Map<String, dynamic> json) {
    return EnvironmentalCondition(
      envConditionId: json['EnvConditionID'],
      sampleId: json['SampleID'],
      soilCompositionId: json['SoilCompositionID'],
      altitude: json['Altitude']?.toDouble(),
      temperature: json['Temperature']?.toDouble(),
      humidity: json['Humidity']?.toDouble(),
      soilType: json['SoilType'],
      soilPH: json['SoilPH']?.toDouble(),
    );
  }

  String get displayName => 'Env Condition $envConditionId';
}
