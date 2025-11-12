class Researcher {
  final int researcherId;
  final String firstName;
  final String lastName;
  final String? email;
  final String? phoneNumber;
  final String? affiliation;

  Researcher({
    required this.researcherId,
    required this.firstName,
    required this.lastName,
    this.email,
    this.phoneNumber,
    this.affiliation,
  });

  factory Researcher.fromJson(Map<String, dynamic> json) {
    return Researcher(
      researcherId: json['ResearcherID'],
      firstName: json['FirstName'],
      lastName: json['LastName'],
      email: json['Email'],
      phoneNumber: json['PhoneNumber'],
      affiliation: json['Affiliation'],
    );
  }

  String get fullName => '$firstName $lastName';
}
