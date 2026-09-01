const translations = {
    de: {
        // --- Navigation ---
        dashboard: "Übersicht",
        batteries: "Batterien verwalten",
        create: "Batterien erstellen",
        analysis: "Analyse",
        
        // --- Header ---
        headerTitle: "Batterie Pass & Digital Decision Support",
        headerSubtitle: "Digital Decision Support System für recyclebare E-Auto-Batterien",
        langDe: "🇩🇪 Deutsch",
        langEn: "🇬🇧 Englisch",
        
        // --- Overview ---
        overview: "Übersicht",
        totalBatteries: "Gesamt Batterien",
        perfectBatteries: "Perfekte Batterien",
        avgCapacity: "Durchschn. Kapazität",
        avgEnergy: "Durchschn. Energie",
        statusDistribution: "Status Verteilung",
        manufacturerDistribution: "Hersteller Verteilung",
        
        // --- Batteries list ---
        batteriesTitle: "Batterien Verwaltung",
        searchBatteryId: "Batterie ID",
        searchManufacturer: "Hersteller",
        searchStatus: "Status",
        searchAll: "Alle",
        searchPerfect: "Perfekter Zustand",
        searchNotApparent: "Zustand nicht erkennbar",
        searchDefective: "Defekt",
        searchDestroyed: "Zerstört",
        searchBtn: "Suchen",
        noBatteries: "Keine Batterien vorhanden.",
        createFirstBattery: "Erstellen Sie jetzt Ihre erste Batterie!",
        tableBatteryId: "Batterie ID",
        tableCategory: "Kategorie",
        tableManufacturer: "Hersteller",
        tableDate: "Datum",
        tableStatus: "Status",
        tableActions: "Aktionen",
        detailsBtn: "Details",
        deleteBtn: "Löschen",
        
        // --- Create battery ---
        createTitle: "Neue Batterie erstellen",
        generalInfo: "1. Allgemeine Batterie- und Herstellerinformationen",
        batteryId: "Batterie ID *",
        category: "Kategorie",
        manufacturerId: "Hersteller ID",
        manufacturerDate: "Herstellungsdatum",
        status: "Status",
        materials: "2. Batteriematerialien und -zusammensetzung",
        chemistry: "Batterie Chemie",
        cathode: "Kathoden Material",
        anode: "Anoden Material",
        electrolyte: "Elektrolyt Material",
        environmentImpact: "Umweltauswirkungen (optional)",
        healthSafety: "Auswirkungen auf Gesundheit & Sicherheit (optional)",
        criticalMaterials: "Kritische Rohstoffe (optional)",
        hazardousSubstances: "Gefahrstoffe (optional)",
        circularity: "3. Kreislaufwirtschaft und Ressourceneffizienz",
        removalManual: "Anleitung zum Entfernen der Batterie aus dem Gerät",
        disassemblyManual: "Anleitung zum Zerlegen des Akkus",
        componentParts: "Teilenummern der Komponenten",
        safetyInstructions: "Sicherheitsanweisungen",
        preRecycled: "Anteil des recycelten Inhalts vor dem Verbrauch",
        postRecycled: "Anteil des recycelten Inhalts nach dem Verbrauch",
        
        // --- Performance ---
        performance: "4. Performance",
        ratedCapacity: "Nennkapazität (Ah)",
        certifiedEnergy: "Zertifizierte nutzbare Batterieenergie (kWh) (optional)",
        minVoltage: "Minimale Spannung (V)",
        nominalVoltage: "Nominale Spannung (V)",
        maxVoltage: "Maximale Spannung (V)",
        minTemperature: "Minimale Temperatur (°C)",
        maxTemperature: "Maximale Temperatur (°C)",
        originalPowerCapacity: "Ursprüngliche Leistungskapazität (W)",
        maxBatteryCapacity: "Maximal zulässige Batteriekapazität (Ah)",
        internalResistanceCell: "Innenwiderstand – Zelle (mΩ)",
        internalResistancePack: "Innenwiderstand – Akku (mΩ)",
        saveBattery: "Batterie speichern",
        reset: "Zurücksetzen",
        
        // --- Alerts / Feedback ---
        alertCreated: "Batterie erfolgreich erstellt!",
        alertDeleted: "Batterie erfolgreich gelöscht",
        alertError: "Fehler aufgetreten",
        confirmDelete: "Batterie wirklich löschen?",
        close: "Schließen",
        loading: "Laden...",
        
        // --- Details view ---
        detailsTitlePrefix: "Details von Batterie-ID:",
        generalInfoHeading: "1. Allgemeine Batterie- und Herstellerinformationen:",
        materialsHeading: "2. Batteriematerialien und -zusammensetzung:",
        circularityHeading: "3. Kreislaufwirtschaft und Ressourceneffizienz:",
        performanceHeading: "4. Performance:",
        filesHeading: "Dateien:",
        
        // --- Analysis ---
        analysisTitle: "Analyse",
        selectBattery: "Batterie auswählen",
        selectBatteryOption: "-- Batterie wählen --",
        recyclingAssessment: "♻️ Recyclingfähigkeit Assessment",
        performanceAnalysis: "⚡ Performance Analyse",
        recommendations: "Empfehlungen",
        
        // --- Misc ---
        noBatteriesOption: "Keine Batterien vorhanden",
        selectUnknownManufacturer: "Unbekannt",
        environmentImpactPlaceholder: "Beschreiben Sie hier die Auswirkungen...",
        healthSafetyPlaceholder: "Beschreiben Sie hier mögliche Auswirkungen oder Maßnahmen...",
        allowedFileFormats: "Erlaubte Dateiformate: PDF, DOC, DOCX, TXT",
        fullNumbers: "Nur ganze Zahlen ≥ 0 erlaubt.",
        browse: "Durchsuchen",

        // --- Decision Support ---
        decision: {
            title: "Decision Support System - Batterie Recycling vs. Wiederverwendung",
            subtitle: "Bewerten Sie alle Kriterien auf einer Skala von 0-100",
            technical: "Technische Kriterien",
            economic: "Wirtschaftliche Kriterien",
            environmental: "Umweltkriterien",
            regulatory: "Regulatorische Kriterien",
            application: "Anwendungseignung",
            calculate: "Entscheidung berechnen",
            reset: "Zurücksetzen",
            recommendation: "Empfehlung",
            detailedEvaluation: "Detaillierte Bewertung",
            reuseRecommended: "Wiederverwendung empfohlen",
            recycleRecommended: "Recycling empfohlen",
            weightedScore: "Gewichtete Punktzahl",
            mandatory: "Pflichtkriterium",
            optional: "Optional",
            passed: "Bestanden",
            decisionCriteria: "Entscheidungskriterien:",
            rule1: "Alle Pflichtkriterien müssen erfüllt sein",
            rule2: "Maximal 50% der optionalen Kriterien dürfen die Anforderungen nicht erfüllen",
            rule3: "Gesamtgewichtete Punktzahl sollte ≥ 60 sein",
   
            criteria: {
                soh: "Aktueller Gesundheitszustand",
                sohDesc: "(≥ 70% für Wiederverwendung)",
                remainingCharge: "Restladung / Kapazität",
                remainingChargeDesc: "0 = instabil; 100 = stabil",
                internalResistance: "Innenwiderstand",
                internalResistanceDesc: " 0 = unsicher; 100 = sicher",
                safetyDamage: "Sicherheit / Schäden",
                safetyDamageDesc: "0 = beschädigt; 100 = intakt",
                bmsCondition: "BMS Zustand",
                bmsConditionDesc: "0 = nicht ersetzbar; 100 = ersetzbar",
                costRefurbishment: "Kosten für Aufbereitung",
                costRefurbishmentDesc: "100 = kostspielig; 0 = rentabel",
                marketDemand: "Marktnachfrage",
                marketDemandDesc: "0 = nicht verfügbar; 100 = Markt verfügbar",
                environmentalBenefit: "Umweltvorteil",
                environmentalBenefitDesc: "0 = Material zurückgewinnen erforderlich; 100 = verlängert Lebensdauer mit Vorteil",
                logisticsFootprint: "Lieferdistanz",
                logisticsFootprintDesc: "Mehr als 100km = hohe Emissionen; Weniger als 100km = minimale Emissionen",
                hazardClassification: "Gefahrstoffklassifizierung",
                hazardClassificationDesc: "0 = nicht geeignet für Second-Life Einsatz; 100 = erfüllt Standards",
                producerResponsibility: "Anforderungen an die Herstellerverantwortung",
                producerResponsibilityDesc: "0 = Recycling vorgeschrieben; 100 = erlaubt",
                powerRequirements: "Leistungs- / Energieanforderungen",
                powerRequirementsDesc: "0 = muss recycelt werden; 100 = ausreichende Leistung",
                ageChemistry: "Chemikalienalter",
                ageChemistryDesc: "0 = geeignet; 10 = nicht geeignet"
            },
            
            warnings: {
                sohLow: "SoH < 70% – Recycling wird empfohlen (harte Regel).",
                remainingLow: "Restladung < 54% – Recycling wird empfohlen (harte Regel).",
                resistanceLow: "Innenwiderstand < 60 – Bereich technisch kritisch.",
                safetyLow: "Sicherheit/Kapazitätsverlust < 70 – Bereich technisch kritisch.",
                bmsCritical: "BMS Zustand < 45 – BMS kritisch.",
                technicalViolation: "Technische Mindestwerte verletzt."
            },
            
            errors: {
                calculationFailed: "Fehler bei der Berechnung"
            }
        }
    },

    en: {
        // --- Navigation ---
        dashboard: "Overview",
        batteries: "Manage Batteries",
        create: "Create Battery",
        analysis: "Analysis",
        
        // --- Header ---
        headerTitle: "Battery Passport & Digital Decision Support",
        headerSubtitle: "Digital Decision Support System for Recyclable EV Batteries",
        langDe: "🇩🇪 German",
        langEn: "🇬🇧 English",
        
        // --- Dashboard ---
        overview: "Overview",
        totalBatteries: "Total Batteries",
        perfectBatteries: "Perfect Batteries",
        avgCapacity: "Avg. Capacity",
        avgEnergy: "Avg. Energy",
        statusDistribution: "Status Distribution",
        manufacturerDistribution: "Manufacturer Distribution",
        
        // --- Batteries list ---
        batteriesTitle: "Battery Management",
        searchBatteryId: "Battery ID",
        searchManufacturer: "Manufacturer",
        searchStatus: "Status",
        searchAll: "All",
        searchPerfect: "Perfect Condition",
        searchNotApparent: "Condition not apparent",
        searchDefective: "Defective",
        searchDestroyed: "Destroyed",
        searchBtn: "Search",
        noBatteries: "No batteries available.",
        createFirstBattery: "Create your first battery now!",
        tableBatteryId: "Battery ID",
        tableCategory: "Category",
        tableManufacturer: "Manufacturer",
        tableDate: "Date",
        tableStatus: "Status",
        tableActions: "Actions",
        detailsBtn: "Details",
        deleteBtn: "Delete",
        
        // --- Create battery ---
        createTitle: "Create New Battery",
        generalInfo: "1. General Battery and Manufacturer Information",
        batteryId: "Battery ID *",
        category: "Category",
        manufacturerId: "Manufacturer ID",
        manufacturerDate: "Manufacture Date",
        status: "Status",
        materials: "2. Battery Materials and Composition",
        chemistry: "Battery Chemistry",
        cathode: "Cathode Material",
        anode: "Anode Material",
        electrolyte: "Electrolyte Material",
        environmentImpact: "Impact of substances on environment (optional)",
        healthSafety: "Impact of substances on health, safety and persons (optional)",
        criticalMaterials: "Critical Raw Materials (optional)",
        hazardousSubstances: "Hazardous Substances (optional)",
        circularity: "3. Circular Economy & Resource Efficiency",
        removalManual: "Manual for battery pack removal",
        disassemblyManual: "Manual for battery pack disassembly",
        componentParts: "Part numbers for components",
        safetyInstructions: "Safety measures",
        preRecycled: "Pre-Consumer Recycled Content",
        postRecycled: "Post-Consumer Recycled Content",
        
        // --- Performance ---
        performance: "4. Performance",
        ratedCapacity: "Rated Capacity (Ah)",
        certifiedEnergy: "Certified Usable Energy (kWh) (optional)",
        minVoltage: "Minimum Voltage (V)",
        nominalVoltage: "Nominal Voltage (V)",
        maxVoltage: "Maximum Voltage (V)",
        minTemperature: "Minimum Temperature (°C)",
        maxTemperature: "Maximum Temperature (°C)",
        originalPowerCapacity: "Original Power Capacity (W)",
        maxBatteryCapacity: "Maximum Battery Capacity (Ah)",
        internalResistanceCell: "Internal resistance increase – Cell (mΩ)",
        internalResistancePack: "Internal resistance increase – Pack/Module (mΩ)",
        saveBattery: "Save Battery",
        reset: "Reset",
        
        // --- Alerts / Feedback ---
        alertCreated: "Battery successfully created!",
        alertDeleted: "Battery successfully deleted",
        alertError: "An error occurred",
        confirmDelete: "Really delete battery?",
        close: "Close",
        loading: "Loading...",
        
        // --- Details view ---
        detailsTitlePrefix: "Details for Battery ID:",
        generalInfoHeading: "1. General Battery and Manufacturer Information:",
        materialsHeading: "2. Battery Materials and Composition:",
        circularityHeading: "3. Circular Economy & Resource Efficiency:",
        performanceHeading: "4. Performance:",
        filesHeading: "Files:",
        
        // --- Analysis ---
        analysisTitle: "Analysis",
        selectBattery: "Select Battery",
        selectBatteryOption: "-- Select Battery --",
        recyclingAssessment: "♻️ Recyclability Assessment",
        performanceAnalysis: "⚡ Performance Analysis",
        recommendations: "Recommendations",
        
        // --- Misc ---
        noBatteriesOption: "No batteries available",
        selectUnknownManufacturer: "Unknown",
        environmentImpactPlaceholder: "Describe the environmental impact...",
        healthSafetyPlaceholder: "Describe possible health or safety impacts or measures...",
        allowedFileFormats: "Allowed file formats: PDF, DOC, DOCX, TXT",
        fullNumbers: "Only whole numbers ≥ 0 are allowed.",
        browse: "Browse",

        // --- Decision Support ---
        decision: {
            title: "Decision Support System - Battery Recycling vs. Reuse",
            subtitle: "Rate all criteria on a scale of 0-100",
            technical: "Technical Criteria",
            economic: "Economic Criteria",
            environmental: "Environmental Criteria",
            regulatory: "Regulatory Criteria",
            application: "Application Suitability",
            calculate: "Calculate Decision",
            reset: "Reset",
            recommendation: "Recommendation",
            detailedEvaluation: "Detailed Assessment",
            reuseRecommended: "Reuse Recommended",
            recycleRecommended: "Recycling Recommended",
            weightedScore: "Weighted Score",
            mandatory: "Mandatory",
            optional: "Optional",
            passed: "Passed",
            decisionCriteria: "Decision Criteria:",
            rule1: "All mandatory criteria must be met",
            rule2: "A maximum of 50% of the optional criteria may not meet the requirements.",
            rule3: "The total weighted score should be ≥ 60.",
            
            criteria: {
                soh: "State of Health",
                sohDesc: "(≥ 70% for reuse)",
                remainingCharge: "Remaining Charge/Capacity",
                remainingChargeDesc: "0 = unstable; 100 = stable",
                internalResistance: "Internal Resistance",
                internalResistanceDesc: "100 = safe; 0 = unsafe",
                safetyDamage: "Safety/Damage",
                safetyDamageDesc: "0 = damaged; 100 = intact",
                bmsCondition: "BMS Condition",
                bmsConditionDesc: "0 = not replaceable; 100 = replaceable",
                costRefurbishment: "Cost of Testing + Refurbishment",
                costRefurbishmentDesc: "0 = profitable; 100 = costly",
                marketDemand: "Market Demand",
                marketDemandDesc: "very bad / bad = not available; good / very good = market available",
                environmentalBenefit: "Environmental Benefit",
                environmentalBenefitDesc: "0 = need to reclaim materials; 100 = extends life with benefit",
                logisticsFootprint: "Delivery Distance",
                logisticsFootprintDesc: "less than 100km = low emissionrate; more than 100km = high emissionrate",
                hazardClassification: "Hazard Classification",
                hazardClassificationDesc: "0 = not suitable for second-life; 100 = passes standards",
                producerResponsibility: "Producer Responsibility Requirements",
                producerResponsibilityDesc: "0 = recycling mandated; 100 = allowed",
                powerRequirements: "Power / Energy Requirements",
                powerRequirementsDesc: "0 = must be recycled; 100 = sufficient performance",
                ageChemistry: "Chemistry age",
                ageChemistryDesc: "0 = suitable; 10 = unsuitable"
            },
            
            warnings: {
                sohLow: "SoH < 70% – Recycling is recommended (hard rule).",
                remainingLow: "Remaining charge < 54% – Recycling is recommended (hard rule).",
                resistanceLow: "Internal resistance < 60 – Technically critical range.",
                safetyLow: "Safety/capacity loss < 70 – Technically critical range.",
                bmsCritical: "BMS condition < 45 – BMS critical.",
                technicalViolation: "Technical minimum values violated."
            },
            
            errors: {
                calculationFailed: "Calculation failed"
            }
        }
    }
};

if (typeof module !== 'undefined' && module.exports) {
    module.exports = { translations };
}