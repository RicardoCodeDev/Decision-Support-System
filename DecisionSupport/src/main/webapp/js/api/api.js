const USE_API = true;
const API_BASE = 'http://localhost:8080/BatteryPassportDS/api/batteries';

// Für Battery-Liste + DecisionData speichern/laden
const DECISION_API_BASE = 'http://localhost:8080/BatteryPassportDS/api/decision';

// Für Berechnung (alte API)
const DECISION_SUPPORT_API = 'http://localhost:8080/BatteryPassportDS/api/decision-support';

async function apiRequest(url, options = {}) {
    try {
        console.log('API Request:', url, options);

        const response = await fetch(url, {
            headers: {
                'Content-Type': 'application/json',
                ...options.headers
            },
            ...options
        });

        console.log('API Response Status:', response.status);

        if (!response.ok) {
            let errorMessage = `HTTP ${response.status}`;
            const body = await response.text();
            try {
                const error = JSON.parse(body);
                errorMessage = error.message || errorMessage;
            } catch (e) {
                errorMessage = body || errorMessage;
            }
            throw new Error(errorMessage);
        }

        if (response.status === 204) {
            return null;
        }

        const data = await response.json();
        console.log('API Response Data:', data);
        return data;
    } catch (error) {
        console.error('API Error:', error);
        if (error.message.includes('Failed to fetch')) {
            throw new Error('Server nicht erreichbar. Bitte prüfen Sie, ob der Application Server läuft.');
        }
        throw error;
}
}

async function apiGet(endpoint) {
    const url = `${API_BASE}${endpoint}`;
    console.log('📡 GET:', url);
    return apiRequest(url, {method: 'GET'});
}

async function apiPost(endpoint, data) {
    return apiRequest(`${API_BASE}${endpoint}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });
}

async function apiDelete(endpoint) {
    return apiRequest(`${API_BASE}${endpoint}`, {method: 'DELETE'});
}