import 'package:flutter/material.dart';
import 'screens/home_screen.dart';

void main() {
  runApp(const PlantResearchApp());
}

class PlantResearchApp extends StatelessWidget {
  const PlantResearchApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Plant Research Manager',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.green),
        useMaterial3: true,
      ),
      home: const HomeScreen(),
      debugShowCheckedModeBanner: false,
    );
  }
}
