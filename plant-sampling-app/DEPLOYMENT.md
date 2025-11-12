# 📱 Deployment Guide

## Overview
This guide covers different deployment options for the Plant Sampling Database mobile app.

## Option 1: Web App (Easiest)

### Deploy to Vercel (Recommended)
```bash
npm install -g vercel
cd plant-sampling-app
vercel
```

### Deploy to Netlify
```bash
npm run build
# Drag and drop 'build' folder to netlify.com
```

### Deploy to GitHub Pages
```bash
npm install --save-dev gh-pages

# Add to package.json:
"homepage": "https://yourusername.github.io/plant-sampling-app",
"scripts": {
  "predeploy": "npm run build",
  "deploy": "gh-pages -d build"
}

npm run deploy
```

## Option 2: Progressive Web App (PWA)

The app is already PWA-ready! Users can:
1. Open the web app in mobile browser
2. Tap "Add to Home Screen"
3. Use like a native app

### Enable Offline Mode
Add service worker in `src/index.js`:
```javascript
import * as serviceWorkerRegistration from './serviceWorkerRegistration';
serviceWorkerRegistration.register();
```

## Option 3: Native Mobile App with Capacitor

### Setup Capacitor
```bash
npm install @capacitor/core @capacitor/cli
npx cap init "Plant Sampling" "com.plantsampling.app"
```

### Add Android Platform
```bash
npm install @capacitor/android
npx cap add android
npm run build
npx cap sync
npx cap open android
```

Build in Android Studio:
- Build > Build Bundle(s) / APK(s) > Build APK(s)

### Add iOS Platform
```bash
npm install @capacitor/ios
npx cap add ios
npm run build
npx cap sync
npx cap open ios
```

Build in Xcode:
- Product > Archive > Distribute App

## Option 4: React Native Conversion

### Using React Native Web
```bash
npm install react-native-web
# Reconfigure webpack to support RN components
```

### Full React Native Rewrite
Use the existing logic but with React Native components:
- `View` instead of `div`
- `Text` instead of `p`, `h1`, etc.
- `TextInput` instead of `input`
- `TouchableOpacity` instead of `button`

## Option 5: Electron (Desktop App)

```bash
npm install --save-dev electron electron-builder

# Add to package.json:
"main": "public/electron.js",
"scripts": {
  "electron": "electron .",
  "electron-build": "electron-builder"
}
```

Create `public/electron.js`:
```javascript
const { app, BrowserWindow } = require('electron');
const path = require('path');

function createWindow() {
  const win = new BrowserWindow({
    width: 800,
    height: 600,
    webPreferences: {
      nodeIntegration: true
    }
  });

  win.loadFile('build/index.html');
}

app.whenReady().then(createWindow);
```

## Configuration for Production

### Update API URL
For production, update the default API URL in `src/services/apiService.js`:
```javascript
this.baseUrl = localStorage.getItem('apiBaseUrl') || 'https://your-api.com';
```

### Environment Variables
Create `.env` file:
```
REACT_APP_API_URL=https://your-api.com
```

Use in code:
```javascript
this.baseUrl = process.env.REACT_APP_API_URL || 'http://localhost:5000';
```

### Build Optimization
```bash
# Production build with optimizations
npm run build

# Analyze bundle size
npm install --save-dev source-map-explorer
npm run build
npx source-map-explorer 'build/static/js/*.js'
```

## Backend Deployment

### Deploy Flask API to Heroku
```bash
# Create Procfile
echo "web: gunicorn app:app" > Procfile

# Create runtime.txt
echo "python-3.11.0" > runtime.txt

# Deploy
heroku create plant-sampling-api
git push heroku main
```

### Deploy Flask API to Railway
```bash
railway login
railway init
railway up
```

### Deploy Flask API to AWS Lambda
Use Zappa or AWS SAM for serverless deployment.

## Testing Deployed App

### Test Checklist
- [ ] App loads correctly
- [ ] API URL configuration works
- [ ] Add sample operation succeeds
- [ ] Query sample returns data
- [ ] Update sample modifies data
- [ ] Delete sample removes data
- [ ] Error messages display properly
- [ ] Mobile responsive design works
- [ ] CORS is properly configured
- [ ] HTTPS works (if applicable)

### Performance Testing
```bash
# Lighthouse audit
npm install -g lighthouse
lighthouse https://your-app.com --view
```

## Security Considerations

### For Production:
1. **Enable HTTPS** - Use SSL certificates
2. **API Authentication** - Add JWT or OAuth
3. **Rate Limiting** - Prevent API abuse
4. **Input Validation** - Sanitize all inputs
5. **CORS Configuration** - Restrict allowed origins
6. **Environment Variables** - Never commit secrets

### Example: Add Authentication
```javascript
// In apiService.js
async addSample(sampleData) {
  const token = localStorage.getItem('authToken');
  const response = await fetch(`${this.baseUrl}/sample`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    },
    body: JSON.stringify(sampleData),
  });
  // ...
}
```

## Monitoring and Analytics

### Add Google Analytics
```bash
npm install react-ga4
```

```javascript
// In src/index.js
import ReactGA from 'react-ga4';
ReactGA.initialize('G-XXXXXXXXXX');
```

### Add Error Tracking (Sentry)
```bash
npm install @sentry/react
```

```javascript
import * as Sentry from "@sentry/react";
Sentry.init({ dsn: "your-dsn" });
```

## Maintenance

### Update Dependencies
```bash
npm outdated
npm update
npm audit fix
```

### Backup Strategy
- Regular database backups
- Version control (Git)
- Environment configuration backups

## Support

For deployment issues:
1. Check build logs
2. Verify environment variables
3. Test API connectivity
4. Review CORS configuration
5. Check browser console for errors

---

**Choose the deployment option that best fits your needs!**
