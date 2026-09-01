let currentLang = localStorage.getItem("lang") || "de";

function toggleLanguage() {
    currentLang = currentLang === "de" ? "en" : "de";
    localStorage.setItem("lang", currentLang);
    applyTranslations();
}

function applyTranslations() {
    const t = translations[currentLang];

    document.querySelectorAll('small.allowed-formats').forEach(el => {
        el.textContent = t.allowedFileFormats;
    });

    const headerH1 = document.querySelector("header h1");
    if (headerH1)
        headerH1.textContent = t.headerTitle;

    const headerP = document.querySelector("header p");
    if (headerP)
        headerP.textContent = t.headerSubtitle;

    const langToggle = document.getElementById("langToggle");
    if (langToggle)
        langToggle.textContent = currentLang === "de" ? t.langEn : t.langDe;

    const envTextarea = document.getElementById('environmentImpactText');
    if (envTextarea)
        envTextarea.placeholder = t.environmentImpactPlaceholder;

    const healthTextarea = document.getElementById('healthSafetyText');
    if (healthTextarea)
        healthTextarea.placeholder = t.healthSafetyPlaceholder;

    setText('[data-page="dashboard"]', t.dashboard);
    setText('[data-page="batteries"]', t.batteries);
    setText('[data-page="create"]', t.create);
    setText('[data-page="analysis"]', t.analysis);

    setText('#dashboard h2', t.overview);
    const stats = document.querySelectorAll('#dashboard .stat-card h3');
    if (stats.length >= 4) {
        stats[0].textContent = t.totalBatteries;
        stats[1].textContent = t.perfectBatteries;
        stats[2].textContent = t.avgCapacity;
        stats[3].textContent = t.avgEnergy;
    }
    const chartH3 = document.querySelectorAll('.chart-container h3');
    if (chartH3.length >= 2) {
        chartH3[0].textContent = t.statusDistribution;
        chartH3[1].textContent = t.manufacturerDistribution;
    }


    setText('#batteries h2', t.batteriesTitle);
    const searchBatteryId = document.getElementById('searchBatteryId');
    if (searchBatteryId)
        searchBatteryId.placeholder = t.searchBatteryId;

    const searchManufacturer = document.getElementById('searchManufacturer');
    if (searchManufacturer)
        searchManufacturer.placeholder = t.searchManufacturer;


    const searchStatus = document.getElementById('searchStatus');
    if (searchStatus && searchStatus.options.length === 5) {
        searchStatus.options[0].text = t.searchAll;     
        searchStatus.options[1].text = t.searchPerfect;   
        searchStatus.options[2].text = t.searchNotApparent; 
        searchStatus.options[3].text = t.searchDefective;  
        searchStatus.options[4].text = t.searchDestroyed;   
    }

    const status = document.getElementById('status');
    if (status && status.options.length === 4) {
        status.options[0].text = t.searchPerfect;    
        status.options[1].text = t.searchNotApparent;
        status.options[2].text = t.searchDefective;   
        status.options[3].text = t.searchDestroyed; 
    }

    const table = document.getElementById("batteryTable");
    if (table) {
        const headers = table.querySelectorAll("thead th");
        const t = translations[currentLang];

        if (headers.length >= 6) {
            headers[0].textContent = t.tableBatteryId;
            headers[1].textContent = t.tableCategory;
            headers[2].textContent = t.tableManufacturer;
            headers[3].textContent = t.tableDate;
            headers[4].textContent = t.tableStatus;
            headers[5].textContent = t.tableActions;
        }
    }

    setText('#batteries .btn', t.searchBtn);

    setText('#create h2', t.createTitle);
    setText('#create h3', t.generalInfo);
    setText('label[for="batteryIdentification"]', t.batteryId);
    setText('label[for="manufacturerIdentification"]', t.manufacturerId);
    setText('label[for="manufacturerDate"]', t.manufacturerDate);
    setText('label[for="status"]', t.status);

    const submitBtn = document.querySelector('#create button[type="submit"]');
    if (submitBtn)
        submitBtn.textContent = t.saveBattery;

    const resetBtn = document.querySelector('#create button[type="reset"]');
    if (resetBtn)
        resetBtn.textContent = t.reset;

    setText('label[for="batteryCategory"]', t.category);

    setText('label[for="chemistry"]', t.chemistry);
    setText('label[for="cathode"]', t.cathode);
    setText('label[for="anode"]', t.anode);
    setText('label[for="electrolyte"]', t.electrolyte);

    setText('label[for="environmentImpactText"]', t.environmentImpact);
    setText('label[for="healthSafetyText"]', t.healthSafety);

    setText('label[for="componentPartNumber"]', t.componentParts);
    setText('label[for="removalInstructionFile"]', t.removalManual);
    setText('label[for="disassemblyInstructionFile"]', t.disassemblyManual);
    setText('label[for="safetyInstructionFile"]', t.safetyInstructions);
    setText('label[for="criticalMaterialsFile"]', t.criticalMaterials);
    setText('label[for="hazardousSubstancesFile"]', t.hazardousSubstances);
    setText('#create .allowed-formats', t.fullNumbers);


    setText('label[for="capacity"]', t.ratedCapacity || 'Nennkapazität (Ah)');
    setText('label[for="energy"]', t.certifiedEnergy || 'Zertifizierte nutzbare Batterieenergie (kWh) (optional)');
    setText('label[for="voltageMin"]', t.minVoltage || 'Minimale Spannung (V)');
    setText('label[for="voltageNominal"]', t.nominalVoltage || 'Nominale Spannung (V)');
    setText('label[for="voltageMax"]', t.maxVoltage || 'Maximale Spannung (V)');
    setText('label[for="temperatureMin"]', t.minTemperature || 'Minimale Temperatur (°C)');
    setText('label[for="temperatureMax"]', t.maxTemperature || 'Maximale Temperatur (°C)');
    setText('label[for="powerOriginalW"]', t.originalPowerCapacity || 'Ursprüngliche Leistungskapazität (W)');
    setText('label[for="maxBatteryCapacity"]', t.maxBatteryCapacity || 'Maximal zulässige Batteriekapazität (Ah)');
    setText('label[for="internalResistanceCell"]', t.internalResistanceCell || 'Innenwiderstand – Zelle (mΩ)');
    setText('label[for="internalResistancePack"]', t.internalResistancePack || 'Innenwiderstand – Akku (mΩ)');


    setText('#create h3:nth-of-type(2)', t.materials);
    setText('#create h3:nth-of-type(3)', t.circularity);
    setText('#create h3:nth-of-type(4)', t.performance);
    setText('#create h3:nth-of-type(5)', t.preRecycled);
    setText('#create h3:nth-of-type(6)', t.postRecycled);
    setText('#create h3:nth-of-type(7)', t.performance);




    setText('#analysis h2', t.analysisTitle);
    const analysisLabel = document.querySelector('label[for="analysisSelect"]');
    if (analysisLabel)
        analysisLabel.textContent = t.selectBattery;


    const overlay = document.getElementById('overlay');
    if (overlay && window.currentDetailsDiv) {
        const btn = window.currentDetailsDiv.querySelector('button.overlay-close-btn');
        if (btn)
            btn.textContent = t.close;
    }


    setText('#dss-title', t.decision.title);
    setText('#dss-subtitle', t.decision.subtitle);

    setText('#cat-technical', t.decision.technical);
    setText('#cat-economic', t.decision.economic);
    setText('#cat-environmental', t.decision.environmental);
    setText('#cat-regulatory', t.decision.regulatory);
    setText('#cat-application', t.decision.application);

    setText('#crit-soh-label', t.decision.criteria.soh);
    setText('#crit-soh-desc', t.decision.criteria.sohDesc);
    setText('#crit-remaining-label', t.decision.criteria.remainingCharge);
    setText('#crit-remaining-desc', t.decision.criteria.remainingChargeDesc);
    setText('#crit-resistance-label', t.decision.criteria.internalResistance);
    setText('#crit-resistance-desc', t.decision.criteria.internalResistanceDesc);
    setText('#crit-safety-label', t.decision.criteria.safetyDamage);
    setText('#crit-safety-desc', t.decision.criteria.safetyDamageDesc);
    setText('#crit-bms-label', t.decision.criteria.bmsCondition);
    setText('#crit-bms-desc', t.decision.criteria.bmsConditionDesc);

    setText('#crit-cost-label', t.decision.criteria.costRefurbishment);
    setText('#crit-cost-desc', t.decision.criteria.costRefurbishmentDesc);
    setText('#crit-market-label', t.decision.criteria.marketDemand);
    setText('#crit-market-desc', t.decision.criteria.marketDemandDesc);

    setText('#crit-envbenefit-label', t.decision.criteria.environmentalBenefit);
    setText('#crit-envbenefit-desc', t.decision.criteria.environmentalBenefitDesc);
    setText('#crit-logistics-label', t.decision.criteria.logisticsFootprint);
    setText('#crit-logistics-desc', t.decision.criteria.logisticsFootprintDesc);

    setText('#crit-hazard-label', t.decision.criteria.hazardClassification);
    setText('#crit-hazard-desc', t.decision.criteria.hazardClassificationDesc);
    setText('#crit-producer-label', t.decision.criteria.producerResponsibility);
    setText('#crit-producer-desc', t.decision.criteria.producerResponsibilityDesc);

    setText('#crit-power-label', t.decision.criteria.powerRequirements);
    setText('#crit-power-desc', t.decision.criteria.powerRequirementsDesc);
    setText('#crit-age-label', t.decision.criteria.ageChemistry);
    setText('#crit-age-desc', t.decision.criteria.ageChemistryDesc);

    document.querySelectorAll('.badge-mandatory').forEach(el => {
        el.textContent = t.decision.mandatory;
    });
    document.querySelectorAll('.badge-optional').forEach(el => {
        el.textContent = t.decision.optional;
    });

    setText('#btn-calculate-text', t.decision.calculate);
    setText('#btn-reset-text', t.decision.reset);
    setText('#result-header-title', t.decision.result);
    setText('#result-details-title', t.decision.details);
    setText('#info-box-title', t.decision.decisionCriteria);
    setText('#info-rule-1', t.decision.rule1);
    setText('#info-rule-2', t.decision.rule2);
    setText('#info-rule-3', t.decision.rule3);
}

function setText(selector, text) {
    const el = document.querySelector(selector);
    if (el)
        el.textContent = text;
}