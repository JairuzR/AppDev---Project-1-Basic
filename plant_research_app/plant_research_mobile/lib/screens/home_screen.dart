import 'package:flutter/material.dart';
import 'add_sample_screen.dart';
import 'samples_list_screen.dart';
import '../services/api_service.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  final ApiService _apiService = ApiService();
  bool _isConnected = false;
  bool _isChecking = true;

  @override
  void initState() {
    super.initState();
    _checkConnection();
  }

  Future<void> _checkConnection() async {
    setState(() => _isChecking = true);
    final connected = await _apiService.checkHealth();
    setState(() {
      _isConnected = connected;
      _isChecking = false;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Plant Research Manager'),
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        actions: [
          IconButton(
            icon: const Icon(Icons.refresh),
            onPressed: _checkConnection,
            tooltip: 'Check Connection',
          ),
        ],
      ),
      body: Column(
        children: [
          // Connection Status Banner
          Container(
            width: double.infinity,
            padding: const EdgeInsets.all(12),
            color: _isChecking
                ? Colors.orange.shade100
                : _isConnected
                    ? Colors.green.shade100
                    : Colors.red.shade100,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Icon(
                  _isChecking
                      ? Icons.sync
                      : _isConnected
                          ? Icons.check_circle
                          : Icons.error,
                  color: _isChecking
                      ? Colors.orange
                      : _isConnected
                          ? Colors.green
                          : Colors.red,
                ),
                const SizedBox(width: 8),
                Text(
                  _isChecking
                      ? 'Checking connection...'
                      : _isConnected
                          ? 'Connected to API'
                          : 'Not connected to API',
                  style: TextStyle(
                    color: _isChecking
                        ? Colors.orange.shade900
                        : _isConnected
                            ? Colors.green.shade900
                            : Colors.red.shade900,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ],
            ),
          ),
          
          // Main Content
          Expanded(
            child: Padding(
              padding: const EdgeInsets.all(16.0),
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.stretch,
                children: [
                  const Icon(
                    Icons.eco,
                    size: 80,
                    color: Colors.green,
                  ),
                  const SizedBox(height: 24),
                  const Text(
                    'Plant Sample Management',
                    textAlign: TextAlign.center,
                    style: TextStyle(
                      fontSize: 24,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  const SizedBox(height: 8),
                  const Text(
                    'Manage plant research data with full CRUD operations',
                    textAlign: TextAlign.center,
                    style: TextStyle(
                      fontSize: 14,
                      color: Colors.grey,
                    ),
                  ),
                  const SizedBox(height: 48),
                  
                  // Add Sample Button
                  ElevatedButton.icon(
                    onPressed: _isConnected
                        ? () {
                            Navigator.push(
                              context,
                              MaterialPageRoute(
                                builder: (context) => const AddSampleScreen(),
                              ),
                            );
                          }
                        : null,
                    icon: const Icon(Icons.add),
                    label: const Text('Add New Sample'),
                    style: ElevatedButton.styleFrom(
                      padding: const EdgeInsets.all(16),
                      textStyle: const TextStyle(fontSize: 16),
                    ),
                  ),
                  const SizedBox(height: 16),
                  
                  // View Samples Button
                  ElevatedButton.icon(
                    onPressed: _isConnected
                        ? () {
                            Navigator.push(
                              context,
                              MaterialPageRoute(
                                builder: (context) => const SamplesListScreen(),
                              ),
                            );
                          }
                        : null,
                    icon: const Icon(Icons.list),
                    label: const Text('View All Samples'),
                    style: ElevatedButton.styleFrom(
                      padding: const EdgeInsets.all(16),
                      textStyle: const TextStyle(fontSize: 16),
                    ),
                  ),
                  
                  if (!_isConnected && !_isChecking) ...[
                    const SizedBox(height: 32),
                    Container(
                      padding: const EdgeInsets.all(16),
                      decoration: BoxDecoration(
                        color: Colors.red.shade50,
                        borderRadius: BorderRadius.circular(8),
                        border: Border.all(color: Colors.red.shade200),
                      ),
                      child: Column(
                        children: [
                          const Text(
                            'Connection Error',
                            style: TextStyle(
                              fontWeight: FontWeight.bold,
                              color: Colors.red,
                            ),
                          ),
                          const SizedBox(height: 8),
                          const Text(
                            'Make sure the Flask backend is running on port 5000',
                            textAlign: TextAlign.center,
                            style: TextStyle(fontSize: 12),
                          ),
                          const SizedBox(height: 8),
                          TextButton(
                            onPressed: _checkConnection,
                            child: const Text('Retry Connection'),
                          ),
                        ],
                      ),
                    ),
                  ],
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }
}
