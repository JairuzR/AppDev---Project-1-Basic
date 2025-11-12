class SamplingLocation {
  final int locationId;
  final double? latitude;
  final double? longitude;
  final String? locationDescription;
  final String? region;
  final String? country;

  SamplingLocation({
    required this.locationId,
    this.latitude,
    this.longitude,
    this.locationDescription,
    this.region,
    this.country,
  });

  factory SamplingLocation.fromJson(Map<String, dynamic> json) {
    return SamplingLocation(
      locationId: json['LocationID'],
      latitude: json['Latitude']?.toDouble(),
      longitude: json['Longitude']?.toDouble(),
      locationDescription: json['LocationDescription'],
      region: json['Region'],
      country: json['Country'],
    );
  }

  String get displayName => locationDescription ?? 'Location $locationId';
}
