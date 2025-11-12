# 🌱 Plant Sampling Database Mobile App MVP

A mobile-responsive web application for managing plant samples through a Flask API backend. This MVP provides full CRUD (Create, Read, Update, Delete) functionality for plant sampling data.

## Team Members
- John Harley P. Belandres
- Aude Bos Hyro V. Busaco
- Jonnelle Gorgonio
- Jairuz B. Ruaya
- Marc Christian B. Sosobrado

## Features

✅ **Add Plant Sample** - Create new plant samples with all required fields  
✅ **Query Sample** - Retrieve sample details by Sample ID  
✅ **Update Sample** - Modify existing sample information  
✅ **Delete Sample** - Remove samples from the database  
✅ **Configurable API URL** - Easy setup for different environments  
✅ **JSON Response Display** - View all API responses in formatted JSON  
✅ **Mobile Responsive** - Works on phones, tablets, and desktops  
✅ **Error Handling** - Clear error messages for failed requests  

## Prerequisites

- Node.js 14+ and npm
- Flask API backend running (your Plant Sampling Database API)
- Modern web browser (Chrome, Firefox, Safari, Edge)

## Installation

1. **Navigate to the project directory:**
   ```bash
   cd plant-sampling-app
   ```

2. **Install dependencies:**
   ```bash
   npm install
   ```

## Running the Application

### Development Mode

Start the development server:
```bash
npm start
```

The app will open automatically at `http://localhost:3000`

### Production Build

Create an optimized production build:
```bash
npm run build
```

Serve the production build:
```bash
npm install -g serve
serve -s build
```

## Configuration

### Setting Up API URL

1. Click the **⚙️ Settings** button in the top-right corner
2. Enter your Flask API base URL:
   - **Local development:** `http://localhost:5000`
   - **Android Emulator:** `http://10.0.2.2:5000`
   - **iOS Simulator:** `http://localhost:5000`
   - **Physical Device:** `http://YOUR_COMPUTER_IP:5000` (e.g., `http://192.168.1.100:5000`)
3. Click **Save**

The API URL is stored in browser localStorage and persists across sessions.

## API Endpoints

The app connects to the following Flask API endpoints:

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/sample` | Create a new plant sample |
| GET | `/sample/<SampleID>` | Retrieve a sample by ID |
| PUT | `/sample/<SampleID>` | Update an existing sample |
| DELETE | `/sample/<SampleID>` | Delete a sample |

## Usage Guide

### 1. Add a Plant Sample

1. Navigate to the **Add Sample** tab
2. Fill in all required fields:
   - Researcher ID
   - Location ID
   - Species ID
   - Sampling Event ID
   - Sampling Date
   - Sample Description
3. Click **Add Sample**
4. View the JSON response below the form

### 2. Query a Sample

1. Navigate to the **Query Sample** tab
2. Enter the Sample ID you want to retrieve
3. Click **Query Sample**
4. View the sample details in JSON format

### 3. Update a Sample

1. Navigate to the **Update Sample** tab
2. Enter the Sample ID you want to update
3. Fill in the fields you want to modify (leave others empty)
4. Click **Update Sample**
5. View the confirmation response

### 4. Delete a Sample

1. Navigate to the **Delete Sample** tab
2. Enter the Sample ID you want to delete
3. Click **Delete Sample**
4. View the confirmation response

## Testing with Flask Backend

### Step 1: Start Your Flask API

Make sure your Flask API is running:
```bash
# In your Flask project directory
python app.py
```

Your API should be accessible at `http://localhost:5000`

### Step 2: Configure CORS (if needed)

If you encounter CORS errors, add CORS support to your Flask app:

```python
from flask_cors import CORS

app = Flask(__name__)
CORS(app)  # Enable CORS for all routes
```

Install flask-cors if needed:
```bash
pip install flask-cors
```

### Step 3: Test Each Operation

1. **Test Add Sample:**
   - Fill the form with test data
   - Expected response: `{"message": "Sample created", "SampleID": 123}`

2. **Test Query Sample:**
   - Use the SampleID from the add operation
   - Expected response: Full sample object with all fields

3. **Test Update Sample:**
   - Use an existing SampleID
   - Modify one or more fields
   - Expected response: `{"message": "Sample updated"}`

4. **Test Delete Sample:**
   - Use an existing SampleID
   - Expected response: `{"message": "Sample deleted"}`

## Testing on Mobile Devices

### Option 1: Same Network (Recommended)

1. Connect your mobile device to the same WiFi network as your computer
2. Find your computer's IP address:
   - **Windows:** `ipconfig` (look for IPv4 Address)
   - **Mac/Linux:** `ifconfig` or `ip addr` (look for inet address)
3. Start the React app: `npm start`
4. On your mobile device, open browser and navigate to: `http://YOUR_IP:3000`
5. Configure API URL in settings to: `http://YOUR_IP:5000`

### Option 2: Android Emulator

1. Start Android Emulator
2. Open browser in emulator and navigate to: `http://10.0.2.2:3000`
3. Configure API URL to: `http://10.0.2.2:5000`

### Option 3: Deploy as PWA

The app can be installed as a Progressive Web App on mobile devices:
1. Open the app in mobile browser
2. Look for "Add to Home Screen" option
3. Install and use like a native app

## Converting to Native Mobile App

This React web app can be converted to a native mobile app using:

### Capacitor (Recommended)
```bash
npm install @capacitor/core @capacitor/cli
npx cap init
npx cap add android
npx cap add ios
npm run build
npx cap sync
npx cap open android  # or ios
```

### Cordova
```bash
npm install -g cordova
cordova create myapp
# Copy build files to www/
cordova platform add android
cordova build android
```

## Project Structure

```
plant-sampling-app/
├── public/
│   └── index.html
├── src/
│   ├── App.js              # Main application component
│   ├── App.css             # Styling and responsive design
│   ├── services/
│   │   └── apiService.js   # API client for Flask backend
│   └── index.js            # React entry point
├── package.json
└── README.md
```

## Troubleshooting

### CORS Errors
**Problem:** Browser blocks API requests  
**Solution:** Enable CORS in your Flask backend (see Testing section)

### Network Errors
**Problem:** "Failed to fetch" or connection refused  
**Solution:** 
- Verify Flask API is running
- Check API URL configuration
- Ensure firewall allows connections
- For mobile devices, use correct IP address

### API URL Not Saving
**Problem:** Settings don't persist  
**Solution:** 
- Check browser localStorage is enabled
- Try clearing browser cache
- Ensure cookies/storage are not blocked

### Empty Response
**Problem:** API returns empty or unexpected data  
**Solution:**
- Check Flask API logs for errors
- Verify request payload format
- Test API directly with curl or Postman

## Sample API Request/Response

### Add Sample Request
```json
POST /sample
{
  "ResearcherID": "R001",
  "LocationID": "L001",
  "SpeciesID": "S001",
  "SamplingEventID": "E001",
  "SamplingDate": "2025-11-12",
  "SampleDescription": "Oak tree sample from north forest"
}
```

### Success Response
```json
{
  "success": true,
  "status": 201,
  "data": {
    "message": "Sample created successfully",
    "SampleID": 123
  }
}
```

### Error Response
```json
{
  "success": false,
  "status": 400,
  "data": {
    "error": "Missing required field: ResearcherID"
  }
}
```

## Technologies Used

- **React** - UI framework
- **JavaScript (ES6+)** - Programming language
- **Fetch API** - HTTP client
- **LocalStorage** - Configuration persistence
- **CSS3** - Styling and responsive design

## Future Enhancements

- [ ] Authentication and user login
- [ ] Offline mode with local caching
- [ ] Batch operations (add/update multiple samples)
- [ ] Search and filter functionality
- [ ] Data visualization and charts
- [ ] Export data to CSV/Excel
- [ ] Camera integration for sample photos
- [ ] GPS location capture

## License

This is an MVP project for educational/demonstration purposes.

## Support

For issues or questions, contact the development team members listed above.

---

**Built with ❤️ for Plant Sampling Research**
