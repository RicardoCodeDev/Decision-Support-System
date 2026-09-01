function initDecisionSupport() {
    console.log('Initializing Decision Support System');
    loadAvailableBatteries();

    // Synchronisiere Range und Number Inputs
    criteriaFields.forEach(field => {
        const rangeInput = document.getElementById(`${field}-range`);
        const numberInput = document.getElementById(`${field}-number`);

        if (rangeInput && numberInput) {
            rangeInput.addEventListener('input', () => {
                numberInput.value = rangeInput.value;
                updateRangeBackground(rangeInput);
            });

            numberInput.addEventListener('input', () => {
                const value = Math.max(0, Math.min(100, Number(numberInput.value) || 0));
                numberInput.value = value;
                rangeInput.value = value;
                updateRangeBackground(rangeInput);
            });

            if (field === 'ageChemistry') {
                rangeInput.value = 10;
                numberInput.value = 10;
                rangeInput.style.background = `linear-gradient(to right, #667eea 0%, #667eea 100%, #e5e7eb 100%, #e5e7eb 100%)`;
            }
            updateRangeBackground(rangeInput);
        }
    });

    const calculateBtn = document.getElementById('btn-calculate');
    if (calculateBtn) {
        calculateBtn.addEventListener('click', calculateDecision);
    }

    const resetBtn = document.getElementById('btn-reset');
    if (resetBtn) {
        resetBtn.addEventListener('click', resetDecisionForm);
    }

    const saveBtn = document.getElementById('btn-save-decision');
    if (saveBtn) {
        saveBtn.addEventListener('click', saveDecisionData);
    }

    const sohInput = document.getElementById("soh-number");
    if (sohInput) {
        sohInput.addEventListener("input", () => {
            const sohVal = parseInt(sohInput.value || 0);
            lockAllCriteriaExceptSOH(sohVal < 70);
        });
    }

    const sohRange = document.getElementById("soh-range");
    if (sohRange) {
        sohRange.addEventListener("input", () => {
            const sohVal = parseInt(sohRange.value || 0);
            lockAllCriteriaExceptSOH(sohVal < 70);
        });
    }

    const rem = document.getElementById('remainingCharge-range');
    if (rem) {
        rem.addEventListener('input', () => {
            const val = Number(rem.value);
            if (val < 54) {
                disableBelowRemaining();
            } else {
                enableAllCriteria();
            }
        });
    }

    // ============================================================
    // LOGISTICS Toggle
    // ============================================================
    const logisticsToggle = document.getElementById('logisticsFootprint-toggle');
    const logisticsNumber = document.getElementById('logisticsFootprint-number');
    const logisticsSlider = document.querySelector('#logisticsFootprint-toggle + .toggle-slider');

    if (logisticsToggle && logisticsNumber && logisticsSlider) {
        logisticsToggle.checked = false;
        logisticsNumber.value = 101;
        logisticsSlider.style.backgroundColor = '#ef4444';

        logisticsToggle.addEventListener('change', () => {
            logisticsNumber.value = logisticsToggle.checked ? 100 : 101;
            logisticsSlider.style.backgroundColor = logisticsToggle.checked ? '#22c55e' : '#ef4444';
        });
    }

    // ============================================================
    // BMS Toggle
    // ============================================================
    const bmsToggle = document.getElementById("bmsCondition-toggle");
    const bmsNumber = document.getElementById("bmsCondition-number");
    const bmsSlider = document.querySelector('#bmsCondition-toggle + .toggle-slider');

    if (bmsToggle && bmsNumber && bmsSlider) {
        bmsToggle.checked = false;
        bmsNumber.value = 0;
        bmsSlider.style.backgroundColor = '#ef4444';

        bmsToggle.addEventListener("change", () => {
            bmsNumber.value = bmsToggle.checked ? 100 : 0;
            bmsSlider.style.backgroundColor = bmsToggle.checked ? '#22c55e' : '#ef4444';
        });
    }

    // ============================================================
    // PRODUCER Toggle
    // ============================================================
    const producerToggle = document.getElementById("producerResponsibility-toggle");
    const producerNumber = document.getElementById("producerResponsibility-number");
    const producerSlider = document.querySelector('#producerResponsibility-toggle + .toggle-slider');

    if (producerToggle && producerNumber && producerSlider) {
        producerToggle.checked = false;
        producerNumber.value = 0;
        producerSlider.style.backgroundColor = '#ef4444';

        producerToggle.addEventListener("change", () => {
            producerNumber.value = producerToggle.checked ? 100 : 0;
            producerSlider.style.backgroundColor = producerToggle.checked ? '#22c55e' : '#ef4444';
        });
    }

    // ============================================================
    // MARKET DEMAND 4-step slider
    // ============================================================
    const marketSlider = document.getElementById('marketDemand-range');
    const marketNumber = document.getElementById('marketDemand-number');
    const marketDisplay = document.getElementById('marketDemand-display');

    if (marketSlider && marketNumber && marketDisplay) {
        marketSlider.addEventListener('input', () => {
            const mapping = {
                0: 0,
                1: 33,
                2: 66,
                3: 100
            };

            const percentValue = mapping[marketSlider.value];
            marketNumber.value = percentValue;
            marketDisplay.textContent = marketSlider.value;

            const percentage = (marketSlider.value / 3) * 100;
            marketSlider.style.background = `linear-gradient(to right, #667eea 0%, #667eea ${percentage}%, #e5e7eb ${percentage}%, #e5e7eb 100%)`;
        });

        marketNumber.value = 0;
        marketDisplay.textContent = '0';
        marketSlider.style.background = `linear-gradient(to right, #667eea 0%, #667eea 0%, #e5e7eb 0%, #e5e7eb 100%)`;
    }

    // ============================================================
    // AGE CHEMISTRY slider
    // ============================================================
    const ageSlider = document.getElementById('ageChemistry-range');
    const ageNumber = document.getElementById('ageChemistry-number');

    if (ageSlider && ageNumber) {
        ageSlider.addEventListener('input', () => {
            ageNumber.value = ageSlider.value;
            const percentage = (ageSlider.value / 10) * 100;
            ageSlider.style.background = `linear-gradient(to right, #667eea 0%, #667eea ${percentage}%, #e5e7eb ${percentage}%, #e5e7eb 100%)`;
        });

        ageNumber.addEventListener('input', () => {
            const value = Math.max(0, Math.min(10, Number(ageNumber.value) || 0));
            ageNumber.value = value;
            ageSlider.value = value;
            const percentage = (value / 10) * 100;
            ageSlider.style.background = `linear-gradient(to right, #667eea 0%, #667eea ${percentage}%, #e5e7eb ${percentage}%, #e5e7eb 100%)`;
        });
        ageSlider.style.background = `linear-gradient(to right, #667eea 0%, #667eea 100%, #e5e7eb 100%, #e5e7eb 100%)`;
    }
}

function disableBelowRemaining() {
    criteriaFields.forEach(field => {
        if (field !== 'soh' && field !== 'remainingCharge') {
            const range = document.getElementById(`${field}-range`);
            const num = document.getElementById(`${field}-number`);
            if (range) {
                range.disabled = true;
                range.classList.add('disabled-field');
            }
            if (num) {
                num.disabled = true;
                num.classList.add('disabled-field');
            }
        }
    });
}

function enableAllCriteria() {
    criteriaFields.forEach(field => {
        const range = document.getElementById(`${field}-range`);
        const num = document.getElementById(`${field}-number`);
        if (range) {
            range.disabled = false;
            range.classList.remove('disabled-field');
        }
        if (num) {
            num.disabled = false;
            num.classList.remove('disabled-field');
        }
    });
}

function updateRangeBackground(rangeInput) {
    const value = rangeInput.value;
    const percentage = value;
    rangeInput.style.background = `linear-gradient(to right, #667eea 0%, #667eea ${percentage}%, #e5e7eb ${percentage}%, #e5e7eb 100%)`;
}

function lockAllCriteriaExceptSOH(lock) {
    criteriaFields.forEach(field => {
        if (field === "soh") return;
        const range = document.getElementById(`${field}-range`);
        const number = document.getElementById(`${field}-number`);
        if (!range || !number) return;

        if (lock) {
            range.disabled = true;
            number.disabled = true;
            range.classList.add("disabled-range");
            number.classList.add("disabled-range");
        } else {
            range.disabled = false;
            number.disabled = false;
            range.classList.remove("disabled-range");
            number.classList.remove("disabled-range");
        }
    });
}

const MIN_VALUES = {
    soh: 70,
    remainingCharge: 54,
    internalResistance: 60,
    safetyDamage: 70,
    bmsCondition: 45
};

function disableFieldsExcept(excepts = []) {
    criteriaFields.forEach(field => {
        if (field === 'soh') return;
        if (excepts.includes(field)) return;
        const rng = document.getElementById(`${field}-range`);
        const num = document.getElementById(`${field}-number`);
        if (rng) {
            rng.disabled = true;
            rng.classList.add('disabled-field');
        }
        if (num) {
            num.disabled = true;
            num.classList.add('disabled-field');
        }
    });
}

function enableAllFields() {
    criteriaFields.forEach(field => {
        const rng = document.getElementById(`${field}-range`);
        const num = document.getElementById(`${field}-number`);
        if (rng) {
            rng.disabled = false;
            rng.classList.remove('disabled-field');
        }
        if (num) {
            num.disabled = false;
            num.classList.remove('disabled-field');
        }
    });
}

function checkTechnicalThresholdsAndApplyUI() {
    const sohVal = Number(document.getElementById('soh-number')?.value || 0);
    const remVal = Number(document.getElementById('remainingCharge-number')?.value || 0);
    const intVal = Number(document.getElementById('internalResistance-number')?.value || 0);
    const safetyVal = Number(document.getElementById('safetyDamage-number')?.value || 0);
    const bmsVal = Number(document.getElementById('bmsCondition-number')?.value || 0);

    const allZero = (sohVal === 0 && remVal === 0 && intVal === 0 && safetyVal === 0 && bmsVal === 0);
    if (allZero) {
        enableAllFields();
        hideDecisionWarning();
        return;
    }

    const violations = [];
    if (sohVal < MIN_VALUES.soh) violations.push('soh');
    if (remVal < MIN_VALUES.remainingCharge) violations.push('remainingCharge');
    if (intVal < MIN_VALUES.internalResistance) violations.push('internalResistance');
    if (safetyVal < MIN_VALUES.safetyDamage) violations.push('safetyDamage');
    if (bmsVal < MIN_VALUES.bmsCondition) violations.push('bmsCondition');

    if (violations.length === 0) {
        enableAllFields();
        hideDecisionWarning();
        return;
    }

    if (violations.includes('soh')) {
        disableFieldsExcept(['soh']);
        showDecisionWarning('sohLow');
        return;
    }

    if (violations.includes('remainingCharge')) {
        disableFieldsExcept(['soh', 'remainingCharge']);
        showDecisionWarning('remainingLow');
        return;
    }

    const excepts = ['soh', ...violations.filter(f => f !== 'soh')];
    disableFieldsExcept(excepts);

    const first = violations[0];
    const warningKeys = {
        'internalResistance': 'resistanceLow',
        'safetyDamage': 'safetyLow',
        'bmsCondition': 'bmsCritical'
    };
    showDecisionWarning(warningKeys[first] || 'technicalViolation');
}

// ============================================================
// Formular zurücksetzen
// ============================================================
function resetDecisionForm() {
    // Felder zurücksetzen
    criteriaFields.forEach(field => {
        const rangeInput = document.getElementById(`${field}-range`);
        const numberInput = document.getElementById(`${field}-number`);

        if (rangeInput && numberInput) {
            if (field === 'ageChemistry') {
                rangeInput.value = 10;
                numberInput.value = 10;
                rangeInput.style.background = `linear-gradient(to right, #667eea 0%, #667eea 100%, #e5e7eb 100%, #e5e7eb 100%)`;
            } else {
                rangeInput.value = 0;
                numberInput.value = 0;
                updateRangeBackground(rangeInput);
            }
        }
    });

    // Toggles zurücksetzen
    const logisticsToggle = document.getElementById('logisticsFootprint-toggle');
    const logisticsNumber = document.getElementById('logisticsFootprint-number');
    const logisticsSlider = document.querySelector('#logisticsFootprint-toggle + .toggle-slider');
    if (logisticsToggle && logisticsNumber && logisticsSlider) {
        logisticsToggle.checked = false;
        logisticsNumber.value = 101;
        logisticsSlider.style.backgroundColor = '#ef4444';
    }

    const bmsToggle = document.getElementById('bmsCondition-toggle');
    const bmsNumber = document.getElementById('bmsCondition-number');
    const bmsSlider = document.querySelector('#bmsCondition-toggle + .toggle-slider');
    if (bmsToggle && bmsNumber && bmsSlider) {
        bmsToggle.checked = false;
        bmsNumber.value = 0;
        bmsSlider.style.backgroundColor = '#ef4444';
    }

    const producerToggle = document.getElementById('producerResponsibility-toggle');
    const producerNumber = document.getElementById('producerResponsibility-number');
    const producerSlider = document.querySelector('#producerResponsibility-toggle + .toggle-slider');
    if (producerToggle && producerNumber && producerSlider) {
        producerToggle.checked = false;
        producerNumber.value = 0;
        producerSlider.style.backgroundColor = '#ef4444';
    }

    // Market Demand zurücksetzen
    const marketSlider = document.getElementById('marketDemand-range');
    const marketNumber = document.getElementById('marketDemand-number');
    const marketDisplay = document.getElementById('marketDemand-display');
    if (marketSlider && marketNumber && marketDisplay) {
        marketSlider.value = 0;
        marketNumber.value = 0;
        marketDisplay.textContent = '0';
        marketSlider.style.background = `linear-gradient(to right, #667eea 0%, #667eea 0%, #e5e7eb 0%, #e5e7eb 100%)`;
    }

    enableAllFields();

    const resultContainer = document.getElementById('result-container');
    if (resultContainer) {
        resultContainer.classList.remove('show');
    }
}

// ============================================================
// Entscheidung berechnen & Ergebnis anzeigen
// ============================================================
async function calculateDecision() {
    const t = translations[currentLang];
    try {
        showLoading('decision');
        const criteria = {};
        criteriaFields.forEach(field => {
            const input = document.getElementById(`${field}-number`);
            criteria[field] = input ? parseInt(input.value) : 0;
        });

        console.log('Sending criteria:', criteria);

        const response = await fetch(`${DECISION_SUPPORT_API}/calculate`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(criteria)
        });

        if (!response.ok) {
            const error = await response.json();
            throw new Error(error.message || t.decision.errors.calculationFailed);
        }

        const result = await response.json();
        console.log('Decision result:', result);
        displayDecisionResult(result);
        hideLoading('decision');
    } catch (error) {
        hideLoading('decision');
        console.error('Error calculating decision:', error);
        showAlert(t.alertError + ': ' + error.message, 'error');
    }
}

function displayDecisionResult(result) {
    const score = result.overallScore ?? 0;
    const t = translations[currentLang].decision;
    const resultContainer = document.getElementById('result-container');
    const resultMain = document.getElementById('result-main');
    const resultRecommendation = document.getElementById('result-recommendation');
    const resultScore = document.getElementById('result-score');
    const resultCategories = document.getElementById('result-categories');

    const resultHeaderTitle = document.getElementById('result-header-title');
    const resultDetailsTitle = document.getElementById('result-details-title');
    const infoBoxTitle = document.getElementById('info-box-title');

    if (resultHeaderTitle) resultHeaderTitle.textContent = t.recommendation || 'Empfehlung';
    if (resultDetailsTitle) resultDetailsTitle.textContent = t.detailedEvaluation || 'Detaillierte Bewertung';
    if (infoBoxTitle) infoBoxTitle.innerHTML = '<strong>' + (t.decisionCriteria || 'Entscheidungskriterien:') + '</strong>';

    const infoRule1 = document.getElementById('info-rule-1');
    const infoRule2 = document.getElementById('info-rule-2');
    const infoRule3 = document.getElementById('info-rule-3');

    if (infoRule1) infoRule1.textContent = t.rule1 || 'Alle Pflichtkriterien müssen ≥ 70 Punkte erreichen';
    if (infoRule2) infoRule2.textContent = t.rule2 || 'Maximal 50% der optionalen Kriterien dürfen < 54 Punkte haben';
    if (infoRule3) infoRule3.textContent = t.rule3 || 'Gesamtgewichtete Punktzahl sollte ≥ 60 sein';

    if (result.recommendation) {
        resultMain.className = 'result-main reuse';
        resultRecommendation.textContent = t.reuseRecommended;
    } else {
        resultMain.className = 'result-main recycle';
        resultRecommendation.textContent = t.recycleRecommended;
    }

    resultScore.textContent = `${t.weightedScore}: ${score.toFixed(1)}/100`;

    resultCategories.innerHTML = '';

    const categoryNames = {
        technical: t.technical,
        economic: t.economic,
        environmental: t.environmental,
        regulatory: t.regulatory,
        application: t.application
    };

    const categoryClasses = {
        technical: 'category-result technical',
        economic: 'category-result economic',
        environmental: 'category-result environmental',
        regulatory: 'category-result regulatory',
        application: 'category-result application'
    };

    const categoryOrder = ['technical', 'economic', 'environmental', 'regulatory', 'application'];

    categoryOrder.forEach(category => {
        const data = result.categories?.[category];
        if (!data) return;
        const categoryDiv = document.createElement('div');
        categoryDiv.className = categoryClasses[category];

        const avgWeighted = data.weighted.toFixed(1);

        let mandatoryHtml = '';
        if (data.mandatory > 0) {
            const passedClass = data.mandatoryPassed === data.mandatory ? 'passed' : 'failed';
            mandatoryHtml = `
                <div class="result-item">
                    <span class="label">${t.mandatory}:</span>
                    <span class="value ${passedClass}">${data.mandatoryPassed}/${data.mandatory} ${t.passed}</span>
                </div>
            `;
        }

        let optionalHtml = '';
        if (data.optional > 0) {
            const optionalPassed = data.optional - data.optionalFailed;
            const passedClass = data.optionalFailed <= data.optional * 0.5 ? 'passed' : 'failed';
            optionalHtml = `
                <div class="result-item">
                    <span class="label">${t.optional}:</span>
                    <span class="value ${passedClass}">${optionalPassed}/${data.optional} ${t.passed}</span>
                </div>
            `;
        }

        categoryDiv.innerHTML = `
            <h4>${categoryNames[category]}</h4>
            <div class="result-grid">
                <div class="result-item">
                    <span class="label">${t.weightedScore}:</span>
                    <span class="value">${avgWeighted}/100</span>
                </div>
                ${mandatoryHtml}
                ${optionalHtml}
            </div>
        `;

        resultCategories.appendChild(categoryDiv);
    });

    resultContainer.classList.add('show');

    setTimeout(() => {
        resultContainer.scrollIntoView({behavior: 'smooth', block: 'nearest'});
    }, 100);
}