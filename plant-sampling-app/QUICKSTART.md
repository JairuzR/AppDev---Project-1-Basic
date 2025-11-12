# 🚀 Quick Start Guide

## Get Started in 3 Steps

### 1️⃣ Install Dependencies
```bash
cd plant-sampling-app
npm install
```

### 2️⃣ Start the App
```bash
npm start
```
App opens at `http://localhost:3000`

### 3️⃣ Configure API
1. Click **⚙️ Settings** button
2. Enter your Flask API URL:
   - Local: `http://localhost:5000`
   - Android Emulator: `http://10.0.2.2:5000`
   - Mobile Device: `http://YOUR_IP:5000`
3. Click **Save**

## Test the App

### Add a Sample
1. Go to **Add Sample** tab
2. Fill in the form:
   - ResearcherID: `R001`
   - LocationID: `L001`
   - SpeciesID: `S001`
   - SamplingEventID: `E001`
   - SamplingDate: `2025-11-12`
   - SampleDescription: `Test sample`
3. Click **Add Sample**
4. Check JSON response

### Query a Sample
1. Go to **Query Sample** tab
2. Enter Sample ID (from add response)
3. Click **Query Sample**
4. View sample details

### Update a Sample
1. Go to **Update Sample** tab
2. Enter Sample ID
3. Modify any fields
4. Click **Update Sample**

### Delete a Sample
1. Go to **Delete Sample** tab
2. Enter Sample ID
3. Click **Delete Sample**

## Mobile Testing

### On Same WiFi Network
1. Find your computer's IP: `ipconfig` (Windows) or `ifconfig` (Mac/Linux)
2. On mobile browser: `http://YOUR_IP:3000`
3. Set API URL: `http://YOUR_IP:5000`

### Android Emulator
1. Browser: `http://10.0.2.2:3000`
2. API URL: `http://10.0.2.2:5000`

## Troubleshooting

**CORS Error?**
```python
# Add to Flask app
from flask_cors import CORS
CORS(app)
```

**Can't Connect?**
- Check Flask is running: `python app.py`
- Verify API URL in Settings
- Check firewall settings

## Need Help?

See full [README.md](README.md) for detailed documentation.

---

**Happy Testing! 🌱**
