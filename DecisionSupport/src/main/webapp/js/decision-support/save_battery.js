let selectedBatteryId = null;
let loadedDecisionData = null;

async function loadAvailableBatteries() {
    try {
        const response = await fetch(API_BASE);
        if (!response.ok)
            throw new Error('Fehler beim Laden der Batterien');
        const batteries = await response.json();
        populateBatterySelect(batteries);
    } catch (error) {
        console.error('Error loading batteries:', error);
        showAlert('Fehler beim Laden der Batterien: ' + error.message, 'error');
    }
}

function populateBatterySelect(batteries) {
    const select = document.getElementById('battery-select');
    if (!select)
        return;

    select.innerHTML = '<option value="">-- Batterie auswählen --</option>';

    batteries.forEach(b => {
        const option = document.createElement('option');
        option.value = b.id;
        option.textContent = `${b.batteryIdentification} (${b.manufacturerIdentification}) - Status: ${b.batteryStatus || 'Unbekannt'}`;
        select.appendChild(option);
    });
}

async function handleBatterySelection(event) {
    const batteryId = event.target.value;
    if (!batteryId) {
        selectedBatteryId = null;
        loadedDecisionData = null;
        resetDecisionForm();
        return;
    }

    selectedBatteryId = batteryId;

    try {
        showLoading('decision');
        const response = await fetch(`${DECISION_API_BASE}/battery/${batteryId}`);
        if (!response.ok)
            throw new Error('Fehler beim Laden der Decision Data');

        const data = await response.json();
        loadedDecisionData = data;

        if (Object.keys(data).length > 0) {
            populateFormWithData(data);
            showAlert('Gespeicherte Daten für diese Batterie wurden geladen', 'success');
        } else {
            resetDecisionForm();
            showAlert('Keine gespeicherten Daten für diese Batterie gefunden', 'info');
        }
    } catch (error) {
        console.error('Error loading decision data:', error);
        showAlert('Fehler beim Laden der Decision Data: ' + error.message, 'error');
    }
}

function populateFormWithData(data) {
    // Erst alle normalen Felder befüllen
    criteriaFields.forEach(field => {
        const rangeInput = document.getElementById(`${field}-range`);
        const numberInput = document.getElementById(`${field}-number`);
        if (!rangeInput || !numberInput) return;
        if (data[field] === null || data[field] === undefined) return;

        switch (field) {
            case 'marketDemand':
                const marketValue = data[field];
                let sliderValue = 0;
                if (marketValue >= 100) sliderValue = 3;
                else if (marketValue >= 66) sliderValue = 2;
                else if (marketValue >= 33) sliderValue = 1;
                
                rangeInput.value = sliderValue;
                numberInput.value = marketValue;
                
                const marketDisplay = document.getElementById('marketDemand-display');
                if (marketDisplay) marketDisplay.textContent = sliderValue;
                
                // Market Demand Slider visuell updaten
                const marketPercentage = (sliderValue / 3) * 100;
                rangeInput.style.background = `linear-gradient(to right, #667eea 0%, #667eea ${marketPercentage}%, #e5e7eb ${marketPercentage}%, #e5e7eb 100%)`;
                break;

            case 'ageChemistry':
                rangeInput.value = data[field];
                numberInput.value = data[field];
                
                // Age Chemistry Slider visuell updaten
                const agePercentage = (data[field] / 10) * 100;
                rangeInput.style.background = `linear-gradient(to right, #667eea 0%, #667eea ${agePercentage}%, #e5e7eb ${agePercentage}%, #e5e7eb 100%)`;
                break;

            default:
                rangeInput.value = data[field];
                numberInput.value = data[field];
                updateRangeBackground(rangeInput);
        }
    });

    // Toggles setzen
    if (data.bmsCondition !== null && data.bmsCondition !== undefined) {
        const bmsToggle = document.getElementById('bmsCondition-toggle');
        const bmsNumber = document.getElementById('bmsCondition-number');
        const bmsSlider = document.querySelector('#bmsCondition-toggle + .toggle-slider');
        
        if (bmsToggle && bmsNumber && bmsSlider) {
            bmsToggle.checked = data.bmsCondition > 0;
            bmsNumber.value = bmsToggle.checked ? 100 : 0;
            
            // Slider-Farbe direkt setzen (nicht über Event)
            bmsSlider.style.backgroundColor = bmsToggle.checked ? '#22c55e' : '#ef4444';
        }
    }

    if (data.logisticsFootprint !== null && data.logisticsFootprint !== undefined) {
        const logisticsToggle = document.getElementById('logisticsFootprint-toggle');
        const logisticsNumber = document.getElementById('logisticsFootprint-number');
        const logisticsSlider = document.querySelector('#logisticsFootprint-toggle + .toggle-slider');
        
        if (logisticsToggle && logisticsNumber && logisticsSlider) {
            // <= 100 = nah (grün), > 100 = weit (rot)
            logisticsToggle.checked = data.logisticsFootprint <= 100;
            logisticsNumber.value = logisticsToggle.checked ? 100 : 101;
            
            // Slider-Farbe direkt setzen
            logisticsSlider.style.backgroundColor = logisticsToggle.checked ? '#22c55e' : '#ef4444';
        }
    }

    if (data.producerResponsibility !== null && data.producerResponsibility !== undefined) {
        const producerToggle = document.getElementById('producerResponsibility-toggle');
        const producerNumber = document.getElementById('producerResponsibility-number');
        const producerSlider = document.querySelector('#producerResponsibility-toggle + .toggle-slider');
        
        if (producerToggle && producerNumber && producerSlider) {
            producerToggle.checked = data.producerResponsibility > 0;
            producerNumber.value = producerToggle.checked ? 100 : 0;
            
            // Slider-Farbe direkt setzen
            producerSlider.style.backgroundColor = producerToggle.checked ? '#22c55e' : '#ef4444';
        }
    }
}

function displaySavedResult(recommendation, overallScore) {
    const t = translations[currentLang].decision;
    const resultContainer = document.getElementById('result-container');
    const resultMain = document.getElementById('result-main');
    const resultRecommendation = document.getElementById('result-recommendation');
    const resultScore = document.getElementById('result-score');
    if (!resultContainer || !resultMain || !resultRecommendation || !resultScore)
        return;

    if (recommendation) {
        resultMain.className = 'result-main reuse';
        resultRecommendation.textContent = t.reuseRecommended;
    } else {
        resultMain.className = 'result-main recycle';
        resultRecommendation.textContent = t.recycleRecommended;
    }

    resultScore.textContent = `${t.weightedScore}: ${overallScore.toFixed(1)}/100`;
    resultContainer.classList.add('show');
}

async function saveDecisionData() {
    try {
        const batterySelect = document.getElementById('battery-select');
        const batteryId = batterySelect?.value;

        if (!batteryId) {
            showAlert('Bitte wählen Sie zuerst eine Batterie aus', 'warning');
            return;
        }

        showLoading('decision');

        const criteriaData = {};
        criteriaFields.forEach(field => {
            const input = document.getElementById(`${field}-number`);
            if (input)
                criteriaData[field] = parseInt(input.value) || 0;
        });

        const resultContainer = document.getElementById('result-container');
        if (resultContainer?.classList.contains('show')) {
            criteriaData.recommendation = document.getElementById('result-main')?.classList.contains('reuse');
            const scoreMatch = document.getElementById('result-score')?.textContent.match(/(\d+\.?\d*)/);
            if (scoreMatch)
                criteriaData.overallScore = parseFloat(scoreMatch[1]);
        }

        console.log('✅ Saving decision data for battery ID:', batteryId, criteriaData);

        const response = await fetch(`${DECISION_API_BASE}/battery/${batteryId}/save`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(criteriaData)
        });

        if (!response.ok) {
            const text = await response.text();
            try {
                const error = JSON.parse(text);
                throw new Error(error.message || 'Fehler beim Speichern');
            } catch {
                throw new Error(text || 'Fehler beim Speichern');
            }
        }

        try {
            await response.json();
        } catch {}

        hideLoading('decision');
        showAlert('Decision Data erfolgreich gespeichert', 'success');
        console.log('✅ Decision data saved successfully');

    } catch (error) {
        hideLoading('decision');
        console.error('❌ Error saving decision data:', error);
        showAlert('Fehler beim Speichern: ' + error.message, 'error');
    }
}

document.getElementById('battery-select').addEventListener('change', handleBatterySelection);