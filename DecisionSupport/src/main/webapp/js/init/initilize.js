document.addEventListener('DOMContentLoaded', () => {
    console.log('Battery Passport DSS initialized with API');
    console.log('API Base URL:', API_BASE);
    loadDashboard();
    applyTranslations();
    const langToggleEl = document.getElementById('langToggle');
    if (langToggleEl)
        langToggleEl.addEventListener('click', toggleLanguage);
    
    const searchBtn = document.querySelector('#batteries .btn');
    if (searchBtn)
        searchBtn.addEventListener('click', (e) => {
            e.preventDefault();
            searchBatteries();
        });

    const batteryForm = document.getElementById('batteryForm');
    if (batteryForm)
        batteryForm.addEventListener('submit', saveNewBattery);
});