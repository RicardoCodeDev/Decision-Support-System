// ============================================================
// Dashboard
// ============================================================
async function loadDashboard() {
    const t = translations[currentLang];
    try {
        showLoading('dashboard');
        const stats = await apiGet('/dashboard/statistics');

        document.getElementById('totalBatteries').textContent = stats.totalBatteries || 0;
        document.getElementById('perfectBatteries').textContent = stats.perfectBatteries || 0;
        document.getElementById('avgCapacity').textContent = (stats.averageCapacity || 0).toFixed(1) + ' Ah';
        document.getElementById('avgEnergy').textContent = (stats.averageEnergy || 0).toFixed(1) + ' kWh';

        const statusDiv = document.getElementById('statusDistribution');
        if (statusDiv && stats.statusDistribution) {
            statusDiv.innerHTML = Object.entries(stats.statusDistribution)
                    .map(([status, count]) => `<div>${escapeHtml(status)}: ${count}</div>`).join('');
        }

        const manufacturerDiv = document.getElementById('manufacturerDistribution');
        if (manufacturerDiv && stats.manufacturerDistribution) {
            manufacturerDiv.innerHTML = Object.entries(stats.manufacturerDistribution)
                    .map(([m, count]) => `<div>${escapeHtml(m)}: ${count}</div>`).join('');
        }

        hideLoading('dashboard');
    } catch (error) {
        hideLoading('dashboard');
        showAlert(translations[currentLang].alertError + ': ' + error.message, 'error');
    }
}

// ============================================================
// Battery List
// ============================================================
async function loadBatteries() {
    try {
        showLoading('batteries');
        const batteries = await apiGet('');
        displayBatteries(batteries);
        hideLoading('batteries');
    } catch (error) {
        hideLoading('batteries');
        showAlert(translations[currentLang].alertError + ': ' + error.message, 'error');
    }
}

function displayBatteries(batteries) {
    const t = translations[currentLang];
    const tbody = document.getElementById('batteryTableBody');
    tbody.innerHTML = '';

    if (!batteries || batteries.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="6" style="text-align: center; padding: 40px;">
                    <p style="font-size: 1.2em; color: #6c757d;">
                        ${escapeHtml(t.noBatteries)}<br>
                        <a href="#" onclick="document.querySelector('[data-page=create]').click(); return false;" 
                           style="color: #667eea; text-decoration: underline;">
                            ${escapeHtml(t.createFirstBattery)}
                        </a>
                    </p>
                </td>
            </tr>
        `;
        return;
    }

    batteries.forEach(battery => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${escapeHtml(battery.batteryIdentification)}</td>
            <td>${escapeHtml(battery.batteryCategory || '-')}</td>
            <td>${escapeHtml(battery.manufacturerIdentification || '-')}</td>
            <td>${battery.manufacturerDate || '-'}</td>
            <td>${escapeHtml(battery.batteryStatus || '-')}</td>
            <td>
                <button class="action-btn view-btn" onclick="viewBattery(${battery.id})">${escapeHtml(t.detailsBtn)}</button>
                <button class="action-btn delete-btn" onclick="deleteBattery(${battery.id})">${escapeHtml(t.deleteBtn)}</button>
            </td>
        `;
        tbody.appendChild(row);
    });
}

async function searchBatteries() {
    try {
        showLoading('batteries');
        const criteria = {
            batteryId: document.getElementById('searchBatteryId').value,
            manufacturer: document.getElementById('searchManufacturer').value,
            status: document.getElementById('searchStatus').value
        };

        const batteries = await apiPost('/search', criteria);
        displayBatteries(batteries);
        hideLoading('batteries');
    } catch (error) {
        hideLoading('batteries');
        showAlert(translations[currentLang].alertError + ': ' + error.message, 'error');
    }
}

async function deleteBattery(id) {
    const t = translations[currentLang];
    if (!confirm(t.confirmDelete))
        return;

    try {
        await apiDelete(`/${id}`);
        showAlert(t.alertDeleted, 'success');
        loadBatteries();
        loadDashboard();
    } catch (error) {
        showAlert(translations[currentLang].alertError + ': ' + error.message, 'error');
    }
}

// ============================================================
// View Battery Details
// ============================================================

async function viewBattery(id) {
    const t = translations[currentLang];
    try {
        const battery = await apiGet(`/${id}`);
        const showVal = v => (v !== null && v !== undefined && v !== '') ? escapeHtml(String(v)) : '-';

        let detailsHTML = `
            <h3>${escapeHtml(t.detailsTitlePrefix)} ${showVal(battery.batteryIdentification)}</h3>
            <div class="assessment-card">
                <h3>${escapeHtml(t.generalInfoHeading)}</h3>
                <br>
                <p><strong>${escapeHtml(t.batteryId.replace('*', ''))}:</strong> ${showVal(battery.batteryIdentification)}</p>
                <p><strong>${escapeHtml(t.category)}:</strong> ${showVal(battery.batteryCategory)}</p>
                <p><strong>${escapeHtml(t.manufacturerId)}:</strong> ${showVal(battery.manufacturerIdentification)}</p>
                <p><strong>${escapeHtml(t.manufacturerDate)}:</strong> ${showVal(battery.manufacturerDate)}</p>
                <p><strong>${escapeHtml(t.status)}:</strong> ${showVal(battery.batteryStatus)}</p>
            </div>
        `;

        // Materialien mit PDF-Links
        if (battery.materials) {
            const mat = battery.materials;
            detailsHTML += `
                <div class="assessment-card">
                    <h3>${escapeHtml(t.materialsHeading || t.materials)}</h3>
                    <br>
                    <p><strong>${escapeHtml(t.chemistry)}:</strong> ${showVal(mat.batteryChemistry)}</p>
                    <p><strong>${escapeHtml(t.cathode)}:</strong> ${showVal(mat.cathodeMaterial)}</p>
                    <p><strong>${escapeHtml(t.anode)}:</strong> ${showVal(mat.anodeMaterial)}</p>
                    <p><strong>${escapeHtml(t.electrolyte)}:</strong> ${showVal(mat.electrolyteMaterial)}</p>
                    <p><strong>${escapeHtml(t.environmentImpact)}:</strong> ${showVal(mat.environmentalImpact)}</p>
                    <p><strong>${escapeHtml(t.healthSafety)}:</strong> ${showVal(mat.healthSafetyImpact)}</p>
                    <br>
                    <p><strong>${escapeHtml(t.criticalMaterials)}:</strong> `;

            // Critical Raw Materials PDF Link
            if (mat.criticalRawMaterialsPdfName) {
                detailsHTML += `
                    <a href="#" onclick="downloadPdf(${battery.id}, 'critical-raw-materials', '${escapeHtml(mat.criticalRawMaterialsPdfName)}'); return false;" 
                       style="color: #667eea; text-decoration: underline; margin-left: 10px;">
                        ${escapeHtml(mat.criticalRawMaterialsPdfName)}
                    </a>`;
            } else {
                detailsHTML += `-`;
            }

            detailsHTML += `</p><p><strong>${escapeHtml(t.hazardousSubstances)}:</strong> `;

            // Hazardous Substances PDF Link
            if (mat.hazardousSubstancesPdfName) {
                detailsHTML += `
                    <a href="#" onclick="downloadPdf(${battery.id}, 'hazardous-substances', '${escapeHtml(mat.hazardousSubstancesPdfName)}'); return false;" 
                       style="color: #667eea; text-decoration: underline; margin-left: 10px;">
                        ${escapeHtml(mat.hazardousSubstancesPdfName)}
                    </a>`;
            } else {
                detailsHTML += `-`;
            }

            detailsHTML += `</p></div>`;
        }

        // Circularity mit PDF-Links
        if (battery.circularity) {
            const circ = battery.circularity;
            detailsHTML += `
                <div class="assessment-card">
                    <h3>${escapeHtml(t.circularityHeading || t.circularity)}</h3>
                    <br>
                    <p><strong>${escapeHtml(t.disassemblyManual)}:</strong> `;

            // Disassembly Manual PDF Link
            if (circ.disassemblyManualPdfName) {
                detailsHTML += `
                    <a href="#" onclick="downloadPdf(${battery.id}, 'disassembly-manual', '${escapeHtml(circ.disassemblyManualPdfName)}'); return false;" 
                       style="color: #667eea; text-decoration: underline; margin-left: 10px;">
                         ${escapeHtml(circ.disassemblyManualPdfName)}
                    </a>`;
            } else {
                detailsHTML += `-`;
            }

            detailsHTML += `</p><p><strong>${escapeHtml(t.removalManual)}:</strong> `;

            // Removal Manual PDF Link
            if (circ.removalManualPdfName) {
                detailsHTML += `
                    <a href="#" onclick="downloadPdf(${battery.id}, 'removal-manual', '${escapeHtml(circ.removalManualPdfName)}'); return false;" 
                       style="color: #667eea; text-decoration: underline; margin-left: 10px;">
                         ${escapeHtml(circ.removalManualPdfName)}
                    </a>`;
            } else {
                detailsHTML += `-`;
            }

            detailsHTML += `</p><p><strong>${escapeHtml(t.safetyInstructions)}:</strong> `;

            // Safety Instructions PDF Link
            if (circ.safetyInstructionsPdfName) {
                detailsHTML += `
                    <a href="#" onclick="downloadPdf(${battery.id}, 'safety-instructions', '${escapeHtml(circ.safetyInstructionsPdfName)}'); return false;" 
                       style="color: #667eea; text-decoration: underline; margin-left: 10px;">
                         ${escapeHtml(circ.safetyInstructionsPdfName)}
                    </a>`;
            } else {
                detailsHTML += `-`;
            }

            detailsHTML += `</p>
                    <p><strong>${escapeHtml(t.componentParts || t.componentPartNumbers)}:</strong> ${showVal(circ.componentPartNumbers)}</p>
                    <br>
                    <p><strong>${escapeHtml(t.preRecycled)}:</strong> Li: ${showVal(circ.preConsumerRecycledLi)}% | Co: ${showVal(circ.preConsumerRecycledCo)}% | Ni: ${showVal(circ.preConsumerRecycledNi)}% | Pb: ${showVal(circ.preConsumerRecycledPb)}%</p>
                    <p><strong>${escapeHtml(t.postRecycled)}:</strong> Li: ${showVal(circ.postConsumerRecycledLi)}% | Co: ${showVal(circ.postConsumerRecycledCo)}% | Ni: ${showVal(circ.postConsumerRecycledNi)}% | Pb: ${showVal(circ.postConsumerRecycledPb)}%</p>
                </div>`;
        }

        // Performance
        if (battery.performance) {
            const perf = battery.performance;
            detailsHTML += `
                <div class="assessment-card">
                    <h3>${escapeHtml(t.performanceHeading || t.performance)}</h3>
                    <br>
                    <p><strong>${escapeHtml(t.avgCapacity)}:</strong> ${showVal(perf.ratedCapacityAh)} Ah</p>
                    <p><strong>${escapeHtml('Certified usable energy')}:</strong> ${showVal(perf.certifiedUsableEnergyKwh)} kWh</p>
                    <p><strong>${escapeHtml('Min voltage')}:</strong> ${showVal(perf.voltageMin)} V</p>
                    <p><strong>${escapeHtml('Nominal voltage')}:</strong> ${showVal(perf.voltageNominal)} V</p>
                    <p><strong>${escapeHtml('Max voltage')}:</strong> ${showVal(perf.voltageMax)} V</p>
                    <p><strong>${escapeHtml('Temperature range')}:</strong> ${showVal(perf.temperatureRangeMin)} bis ${showVal(perf.temperatureRangeMax)} °C</p>
                    <p><strong>${escapeHtml('Original power capacity')}:</strong> ${showVal(perf.originalPowerCapacityWatts)} W</p>
                    <p><strong>${escapeHtml('Max permitted capacity')}:</strong> ${showVal(perf.maximumPermittedCapacity)} Ah</p>
                    <p><strong>${escapeHtml('Internal resistance (cell)')}:</strong> ${showVal(perf.internalResistanceCell)} mΩ</p>
                    <p><strong>${escapeHtml('Internal resistance (pack)')}:</strong> ${showVal(perf.internalResistancePack)} mΩ</p>
                </div>
            `;
        }

        showOverlay(detailsHTML);
    } catch (error) {
        showAlert(translations[currentLang].alertError + ': ' + error.message, 'error');
    }
}

// ============================================================
// Create Battery
// ============================================================
async function saveNewBattery(event) {
    event.preventDefault();
    const t = translations[currentLang];
    let manufacturerDateValue = document.getElementById('manufacturerDate').value;

    if (!manufacturerDateValue) {
        manufacturerDateValue = null;
    }

    try {
        showLoading('create');

        // Helper: Datei zu Base64 konvertieren
        const fileToBase64 = (file) => {
            return new Promise((resolve, reject) => {
                if (!file) {
                    resolve(null);
                    return;
                }
                const reader = new FileReader();
                reader.onload = () => {
                    const base64 = reader.result.split(',')[1]; // Remove data:application/pdf;base64,
                    resolve(base64);
                };
                reader.onerror = reject;
                reader.readAsDataURL(file);
            });
        };

        // PDFs einlesen
        const criticalRawPdf = document.getElementById('criticalRawMaterialsPdf')?.files[0];
        const hazardousPdf = document.getElementById('hazardousSubstancesPdf')?.files[0];
        const disassemblyPdf = document.getElementById('disassemblyManualPdf')?.files[0];
        const removalPdf = document.getElementById('removalManualPdf')?.files[0];
        const safetyPdf = document.getElementById('safetyInstructionsPdf')?.files[0];

        const batteryData = {
            batteryIdentification: document.getElementById('batteryIdentification').value,
            batteryCategory: document.getElementById('batteryCategory').value,
            manufacturerIdentification: document.getElementById('manufacturerIdentification').value,
            manufacturerDate: manufacturerDateValue,
            batteryStatus: document.getElementById('status').value,

            materials: {
                batteryChemistry: document.getElementById('chemistry').value,
                cathodeMaterial: document.getElementById('cathode').value,
                anodeMaterial: document.getElementById('anode').value,
                electrolyteMaterial: document.getElementById('electrolyte').value,
                environmentalImpact: document.getElementById('environmentImpactText')?.value || null,
                healthSafetyImpact: document.getElementById('healthSafetyText')?.value || null,
                
                criticalRawMaterialsPdf: await fileToBase64(criticalRawPdf),
                criticalRawMaterialsPdfName: criticalRawPdf?.name || null,
                hazardousSubstancesPdf: await fileToBase64(hazardousPdf),
                hazardousSubstancesPdfName: hazardousPdf?.name || null
            },

            circularity: {
                componentPartNumbers: document.getElementById('componentPartNumber')?.value || null,
                preConsumerRecycledLi: parseFloatOrNull('preRecycledLi'),
                preConsumerRecycledCo: parseFloatOrNull('preRecycledCo'),
                preConsumerRecycledNi: parseFloatOrNull('preRecycledNi'),
                preConsumerRecycledPb: parseFloatOrNull('preRecycledPb'),
                postConsumerRecycledLi: parseFloatOrNull('recycledLi'),
                postConsumerRecycledCo: parseFloatOrNull('recycledCo'),
                postConsumerRecycledNi: parseFloatOrNull('recycledNi'),
                postConsumerRecycledPb: parseFloatOrNull('recycledPb'),

                disassemblyManualPdf: await fileToBase64(disassemblyPdf),
                disassemblyManualPdfName: disassemblyPdf?.name || null,
                removalManualPdf: await fileToBase64(removalPdf),
                removalManualPdfName: removalPdf?.name || null,
                safetyInstructionsPdf: await fileToBase64(safetyPdf),
                safetyInstructionsPdfName: safetyPdf?.name || null
            },

            performance: {
                ratedCapacityAh: parseFloatOrNull('capacity'),
                certifiedUsableEnergyKwh: parseFloatOrNull('energy'),
                voltageMin: parseFloatOrNull('voltageMin'),
                voltageNominal: parseFloatOrNull('voltageNominal'),
                voltageMax: parseFloatOrNull('voltageMax'),
                temperatureRangeMin: parseFloatOrNull('temperatureMin'),
                temperatureRangeMax: parseFloatOrNull('temperatureMax'),
                originalPowerCapacityWatts: parseFloatOrNull('powerOriginalW'),
                maximumPermittedCapacity: parseFloatOrNull('maxBatteryCapacity'),
                internalResistanceCell: parseFloatOrNull('internalResistanceCell'),
                internalResistancePack: parseFloatOrNull('internalResistancePack')
            }
        };

        console.log('Sending battery data with PDFs:', batteryData);
        const created = await apiPost('', batteryData);
        console.log('Battery created:', created);

        showAlert(t.alertCreated, 'success');
        document.getElementById('batteryForm').reset();
        document.querySelector('[data-page="batteries"]').click();
        hideLoading('create');
    } catch (error) {
        hideLoading('create');
        showAlert(translations[currentLang].alertError + ': ' + error.message, 'error');
        console.error('Create battery error:', error);
    }
}

// PDF Download Funktion
function downloadPdf(batteryId, documentType, fileName) {
    const url = `${API_BASE}/${batteryId}/download/${documentType}`;
    const link = document.createElement('a');
    link.href = url;
    link.download = fileName;
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}