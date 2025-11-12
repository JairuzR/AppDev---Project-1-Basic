class SamplingEvent {
  final int samplingEventId;
  final int? researcherId;
  final int? locationId;
  final String? eventDate;
  final String? eventDescription;
  final String? notes;
  final String? researcherName;
  final String? locationDescription;

  SamplingEvent({
    required this.samplingEventId,
    this.researcherId,
    this.locationId,
    this.eventDate,
    this.eventDescription,
    this.notes,
    this.researcherName,
    this.locationDescription,
  });

  factory SamplingEvent.fromJson(Map<String, dynamic> json) {
    return SamplingEvent(
      samplingEventId: json['SamplingEventID'],
      researcherId: json['ResearcherID'],
      locationId: json['LocationID'],
      eventDate: json['EventDate'],
      eventDescription: json['EventDescription'],
      notes: json['Notes'],
      researcherName: json['ResearcherName'],
      locationDescription: json['LocationDescription'],
    );
  }

  String get displayName => eventDescription ?? 'Event $samplingEventId';
}
