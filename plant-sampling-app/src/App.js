import React, { useState } from 'react';
import './App.css';
import apiService from './services/apiService';

function App() {
  const [activeTab, setActiveTab] = useState('add');
  const [apiUrl, setApiUrl] = useState(apiService.getBaseUrl());
  const [showSettings, setShowSettings] = useState(false);
  const [response, setResponse] = useState(null);

  // Add Sample Form State
  const [addForm, setAddForm] = useState({
    ResearcherID: '',
    LocationID: '',
    SpeciesID: '',
    SamplingEventID: '',
    SamplingDate: '',
    SampleDescription: ''
  });

  // Query Sample Form State
  const [querySampleId, setQuerySampleId] = useState('');

  // Update Sample Form State
  const [updateSampleId, setUpdateSampleId] = useState('');
  const [updateForm, setUpdateForm] = useState({
    ResearcherID: '',
    LocationID: '',
    SpeciesID: '',
    SamplingEventID: '',
    SamplingDate: '',
    SampleDescription: ''
  });

  // Delete Sample Form State
  const [deleteSampleId, setDeleteSampleId] = useState('');

  const handleApiUrlSave = () => {
    apiService.setBaseUrl(apiUrl);
    setShowSettings(false);
    setResponse({ success: true, data: { message: 'API URL updated successfully' } });
  };

  const handleAddSample = async (e) => {
    e.preventDefault();
    const result = await apiService.addSample(addForm);
    setResponse(result);
  };

  const handleQuerySample = async (e) => {
    e.preventDefault();
    const result = await apiService.getSample(querySampleId);
    setResponse(result);
  };

  const handleUpdateSample = async (e) => {
    e.preventDefault();
    const result = await apiService.updateSample(updateSampleId, updateForm);
    setResponse(result);
  };

  const handleDeleteSample = async (e) => {
    e.preventDefault();
    const result = await apiService.deleteSample(deleteSampleId);
    setResponse(result);
  };

  return (
    <div className="App">
      <header className="App-header">
        <h1>🌱 Plant Sampling Database</h1>
        <button 
          className="settings-btn"
          onClick={() => setShowSettings(!showSettings)}
        >
          ⚙️ Settings
        </button>
      </header>

      {showSettings && (
        <div className="settings-panel">
          <h3>API Configuration</h3>
          <div className="form-group">
            <label>API Base URL:</label>
            <input
              type="text"
              value={apiUrl}
              onChange={(e) => setApiUrl(e.target.value)}
              placeholder="http://localhost:5000"
            />
            <small>For Android Emulator use: http://10.0.2.2:5000</small>
          </div>
          <button onClick={handleApiUrlSave} className="btn-primary">Save</button>
          <button onClick={() => setShowSettings(false)} className="btn-secondary">Cancel</button>
        </div>
      )}

      <div className="tabs">
        <button 
          className={activeTab === 'add' ? 'tab active' : 'tab'}
          onClick={() => setActiveTab('add')}
        >
          Add Sample
        </button>
        <button 
          className={activeTab === 'query' ? 'tab active' : 'tab'}
          onClick={() => setActiveTab('query')}
        >
          Query Sample
        </button>
        <button 
          className={activeTab === 'update' ? 'tab active' : 'tab'}
          onClick={() => setActiveTab('update')}
        >
          Update Sample
        </button>
        <button 
          className={activeTab === 'delete' ? 'tab active' : 'tab'}
          onClick={() => setActiveTab('delete')}
        >
          Delete Sample
        </button>
      </div>

      <div className="content">
        {activeTab === 'add' && (
          <div className="form-container">
            <h2>Add New Plant Sample</h2>
            <form onSubmit={handleAddSample}>
              <div className="form-group">
                <label>Researcher ID:</label>
                <input
                  type="text"
                  value={addForm.ResearcherID}
                  onChange={(e) => setAddForm({...addForm, ResearcherID: e.target.value})}
                  required
                />
              </div>
              <div className="form-group">
                <label>Location ID:</label>
                <input
                  type="text"
                  value={addForm.LocationID}
                  onChange={(e) => setAddForm({...addForm, LocationID: e.target.value})}
                  required
                />
              </div>
              <div className="form-group">
                <label>Species ID:</label>
                <input
                  type="text"
                  value={addForm.SpeciesID}
                  onChange={(e) => setAddForm({...addForm, SpeciesID: e.target.value})}
                  required
                />
              </div>
              <div className="form-group">
                <label>Sampling Event ID:</label>
                <input
                  type="text"
                  value={addForm.SamplingEventID}
                  onChange={(e) => setAddForm({...addForm, SamplingEventID: e.target.value})}
                  required
                />
              </div>
              <div className="form-group">
                <label>Sampling Date:</label>
                <input
                  type="date"
                  value={addForm.SamplingDate}
                  onChange={(e) => setAddForm({...addForm, SamplingDate: e.target.value})}
                  required
                />
              </div>
              <div className="form-group">
                <label>Sample Description:</label>
                <textarea
                  value={addForm.SampleDescription}
                  onChange={(e) => setAddForm({...addForm, SampleDescription: e.target.value})}
                  rows="4"
                  required
                />
              </div>
              <button type="submit" className="btn-primary">Add Sample</button>
            </form>
          </div>
        )}

        {activeTab === 'query' && (
          <div className="form-container">
            <h2>Query Plant Sample</h2>
            <form onSubmit={handleQuerySample}>
              <div className="form-group">
                <label>Sample ID:</label>
                <input
                  type="text"
                  value={querySampleId}
                  onChange={(e) => setQuerySampleId(e.target.value)}
                  placeholder="Enter Sample ID"
                  required
                />
              </div>
              <button type="submit" className="btn-primary">Query Sample</button>
            </form>
          </div>
        )}

        {activeTab === 'update' && (
          <div className="form-container">
            <h2>Update Plant Sample</h2>
            <form onSubmit={handleUpdateSample}>
              <div className="form-group">
                <label>Sample ID:</label>
                <input
                  type="text"
                  value={updateSampleId}
                  onChange={(e) => setUpdateSampleId(e.target.value)}
                  placeholder="Enter Sample ID to update"
                  required
                />
              </div>
              <div className="form-group">
                <label>Researcher ID:</label>
                <input
                  type="text"
                  value={updateForm.ResearcherID}
                  onChange={(e) => setUpdateForm({...updateForm, ResearcherID: e.target.value})}
                />
              </div>
              <div className="form-group">
                <label>Location ID:</label>
                <input
                  type="text"
                  value={updateForm.LocationID}
                  onChange={(e) => setUpdateForm({...updateForm, LocationID: e.target.value})}
                />
              </div>
              <div className="form-group">
                <label>Species ID:</label>
                <input
                  type="text"
                  value={updateForm.SpeciesID}
                  onChange={(e) => setUpdateForm({...updateForm, SpeciesID: e.target.value})}
                />
              </div>
              <div className="form-group">
                <label>Sampling Event ID:</label>
                <input
                  type="text"
                  value={updateForm.SamplingEventID}
                  onChange={(e) => setUpdateForm({...updateForm, SamplingEventID: e.target.value})}
                />
              </div>
              <div className="form-group">
                <label>Sampling Date:</label>
                <input
                  type="date"
                  value={updateForm.SamplingDate}
                  onChange={(e) => setUpdateForm({...updateForm, SamplingDate: e.target.value})}
                />
              </div>
              <div className="form-group">
                <label>Sample Description:</label>
                <textarea
                  value={updateForm.SampleDescription}
                  onChange={(e) => setUpdateForm({...updateForm, SampleDescription: e.target.value})}
                  rows="4"
                />
              </div>
              <button type="submit" className="btn-primary">Update Sample</button>
            </form>
          </div>
        )}

        {activeTab === 'delete' && (
          <div className="form-container">
            <h2>Delete Plant Sample</h2>
            <form onSubmit={handleDeleteSample}>
              <div className="form-group">
                <label>Sample ID:</label>
                <input
                  type="text"
                  value={deleteSampleId}
                  onChange={(e) => setDeleteSampleId(e.target.value)}
                  placeholder="Enter Sample ID to delete"
                  required
                />
              </div>
              <button type="submit" className="btn-danger">Delete Sample</button>
            </form>
          </div>
        )}

        {response && (
          <div className="response-container">
            <h3>API Response:</h3>
            <div className={`response-box ${response.success ? 'success' : 'error'}`}>
              <div className="status-badge">
                Status: {response.status || 'Network Error'}
              </div>
              <pre>{JSON.stringify(response.data, null, 2)}</pre>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default App;
