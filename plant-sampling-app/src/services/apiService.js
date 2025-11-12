// API Service for Plant Sampling Database
class ApiService {
  constructor() {
    // Default API URL (can be changed via settings)
    this.baseUrl = localStorage.getItem('apiBaseUrl') || 'http://localhost:5000';
  }

  setBaseUrl(url) {
    // Remove trailing slash if present
    this.baseUrl = url.replace(/\/$/, '');
    localStorage.setItem('apiBaseUrl', this.baseUrl);
  }

  getBaseUrl() {
    return this.baseUrl;
  }

  async addSample(sampleData) {
    try {
      const response = await fetch(`${this.baseUrl}/sample`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(sampleData),
      });

      const data = await response.json();
      
      return {
        success: response.ok,
        status: response.status,
        data: data,
      };
    } catch (error) {
      return {
        success: false,
        status: 0,
        data: { error: error.message },
      };
    }
  }

  async getSample(sampleId) {
    try {
      const response = await fetch(`${this.baseUrl}/sample/${sampleId}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      });

      const data = await response.json();
      
      return {
        success: response.ok,
        status: response.status,
        data: data,
      };
    } catch (error) {
      return {
        success: false,
        status: 0,
        data: { error: error.message },
      };
    }
  }

  async updateSample(sampleId, sampleData) {
    try {
      const response = await fetch(`${this.baseUrl}/sample/${sampleId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(sampleData),
      });

      const data = await response.json();
      
      return {
        success: response.ok,
        status: response.status,
        data: data,
      };
    } catch (error) {
      return {
        success: false,
        status: 0,
        data: { error: error.message },
      };
    }
  }

  async deleteSample(sampleId) {
    try {
      const response = await fetch(`${this.baseUrl}/sample/${sampleId}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
        },
      });

      const data = await response.json();
      
      return {
        success: response.ok,
        status: response.status,
        data: data,
      };
    } catch (error) {
      return {
        success: false,
        status: 0,
        data: { error: error.message },
      };
    }
  }
}

const apiServiceInstance = new ApiService();
export default apiServiceInstance;
