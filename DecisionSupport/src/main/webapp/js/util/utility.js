function parseFloatOrNull(id) {
    const el = document.getElementById(id);
    if (!el)
        return null;
    const val = parseFloat(el.value);
    return isNaN(val) ? null : val;
}

function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

function showOverlay(html) {
    const t = translations[currentLang];
    closeOverlay();

    const detailsDiv = document.createElement('div');
    detailsDiv.style.cssText = 'position: fixed; top: 50%; left: 50%; transform: translate(-50%, -50%); background: white; padding: 30px; border-radius: 10px; box-shadow: 0 20px 60px rgba(0,0,0,0.3); max-width: 800px; max-height: 80vh; overflow-y: auto; z-index: 10000;';
    detailsDiv.innerHTML = html;

    const closeBtn = document.createElement('button');
    closeBtn.className = 'btn overlay-close-btn';
    closeBtn.textContent = t.close;
    closeBtn.style.cssText = 'display: block; margin: 20px auto 0;';
    closeBtn.onclick = closeOverlay;
    detailsDiv.appendChild(closeBtn);

    const overlay = document.createElement('div');
    overlay.id = 'overlay';
    overlay.style.cssText = 'position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.5); z-index: 9999;';
    overlay.onclick = closeOverlay;

    document.body.appendChild(overlay);
    document.body.appendChild(detailsDiv);
    window.currentDetailsDiv = detailsDiv;
}

function closeOverlay() {
    const overlay = document.getElementById('overlay');
    if (overlay)
        overlay.remove();
    if (window.currentDetailsDiv)
        window.currentDetailsDiv.remove();
    window.currentDetailsDiv = null;
}

function showAlert(message, type) {
    const alert = document.createElement('div');
    alert.textContent = message;
    alert.style.cssText = `
        position: fixed; top: 20px; right: 20px; z-index: 10001; 
        padding: 15px 20px; border-radius: 5px; 
        box-shadow: 0 4px 6px rgba(0,0,0,0.1);
        background: ${type === 'success' ? '#22c55e' : '#ef4444'};
        color: white; font-weight: 500;
    `;
    document.body.appendChild(alert);
    setTimeout(() => alert.remove(), 5000);
}

function showLoading(page) {
    const t = translations[currentLang];
    const pageElement = document.getElementById(page);
    if (pageElement) {
        let loader = pageElement.querySelector('.loading-indicator');
        if (!loader) {
            loader = document.createElement('div');
            loader.className = 'loading-indicator';
            loader.innerHTML = `<div style="text-align: center;"><p>${escapeHtml(t.loading)}</p></div>`;
            loader.style.cssText = 'position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); z-index: 1000;';
            pageElement.style.position = 'relative';
            pageElement.appendChild(loader);
        }
    }
}

function hideLoading(page) {
    const pageElement = document.getElementById(page);
    if (pageElement) {
        const loader = pageElement.querySelector('.loading-indicator');
        if (loader)
            loader.remove();
    }
}

const criteriaFields = [
    'soh',
    'remainingCharge',
    'internalResistance',
    'safetyDamage',
    'bmsCondition',
    'costRefurbishment',
    'marketDemand',
    'environmentalBenefit',
    'logisticsFootprint',
    'hazardClassification',
    'producerResponsibility',
    'powerRequirements',
    'ageChemistry'
];
