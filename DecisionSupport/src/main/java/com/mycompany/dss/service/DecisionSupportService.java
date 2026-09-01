package com.mycompany.dss.service;

import com.mycompany.dss.dto.CategoryResultDTO;
import com.mycompany.dss.dto.DecisionCriteriaDTO;
import com.mycompany.dss.dto.DecisionResultDTO;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class DecisionSupportService {

    public DecisionResultDTO calculateDecision(DecisionCriteriaDTO c) {

        // ========================
        // TECHNICAL
        // ========================
        int soh = (c.getSoh() != null) ? c.getSoh() : 0;
        int remaining = (c.getRemainingCharge() != null) ? c.getRemainingCharge() : 0;
        int internalR = (c.getInternalResistance() != null) ? c.getInternalResistance() : 0;
        int safety = (c.getSafetyDamage() != null) ? c.getSafetyDamage() : 0;
        int bms = (c.getBmsCondition() != null) ? c.getBmsCondition() : 0;

        final int MIN_SOH = 70;
        final int MIN_REMAINING = 54;
        final int MIN_INTERNAL = 60;
        final int MIN_SAFETY = 75;
        final int MIN_BMS = 100;

        double wfSoh = 1.0, wfRem = 0.6, wfInt = 0.8, wfSafety = 1.0, wfBms = 0.4;
        double sumWFTech = wfSoh + wfRem + wfInt + wfSafety + wfBms;

        double techRaw = (soh / 100.0) * wfSoh
                + (remaining / 100.0) * wfRem
                + (internalR / 100.0) * wfInt
                + (safety / 100.0) * wfSafety
                + (bms / 100.0) * wfBms;

        double techWeighted = (techRaw / sumWFTech) * 100.0;
        techWeighted = Math.max(0.0, Math.min(100.0, techWeighted));

        int techMandatory = 2;
        int techMandatoryPassed = (soh >= MIN_SOH ? 1 : 0) + (remaining >= MIN_REMAINING ? 1 : 0);
        int techOptional = 3; 
        int techOptionalFailed = 0;

        if (internalR < MIN_INTERNAL) {
            techOptionalFailed++;
        }
        if (safety < MIN_SAFETY) {
            techOptionalFailed++;
        }
        if (bms < MIN_BMS) {
            techOptionalFailed++;
        }

        CategoryResultDTO technicalCategory = new CategoryResultDTO(
                techWeighted, 5,
                techMandatory, techMandatoryPassed,
                techOptional, techOptionalFailed
        );

        boolean failsHardRule = soh < MIN_SOH || remaining < MIN_REMAINING;

        if (failsHardRule) {
            Map<String, CategoryResultDTO> cats = new HashMap<>();
            cats.put("technical", technicalCategory);
            return new DecisionResultDTO(false, techWeighted, cats);
        }

        // ========================
        // ECONOMIC
        // ========================
        int costRefurbishment = (c.getCostRefurbishment() != null) ? c.getCostRefurbishment() : 0;
        int market = (c.getMarketDemand() != null) ? c.getMarketDemand() : 0;

        // Mindestgrenzen für Reuse
        final int MIN_REFURB = 40;   
        final int MIN_MARKET = 66;   

        double wfCost = 0.7, wfMarket = 1.0;
        double sumWFEco = wfCost + wfMarket;

        double ecoRaw = (costRefurbishment / 100.0) * wfCost + (market / 100.0) * wfMarket;
        double ecoWeighted = (ecoRaw / sumWFEco) * 100.0;
        ecoWeighted = Math.max(0.0, Math.min(100.0, ecoWeighted));

        int ecoOptionalFailed = 0;
        if (costRefurbishment < MIN_REFURB) {
            ecoOptionalFailed++;
        }
        if (market < MIN_MARKET) {
            ecoOptionalFailed++;
        }

        CategoryResultDTO economicCategory = new CategoryResultDTO(
                ecoWeighted, 2,
                0, 0,
                2, ecoOptionalFailed
        );

        // ========================
        // ENVIRONMENTAL
        // ========================
        int envBenefit = (c.getEnvironmentalBenefit() != null) ? c.getEnvironmentalBenefit() : 0;
        int logisticsDistance = (c.getLogisticsFootprint() != null) ? c.getLogisticsFootprint() : 0;

        int distanceScore = (logisticsDistance <= 100) ? 100 : 0;

        // Mindestgrenzen für Reuse
        final int MIN_BENEFIT = 50;

        double wfEnvBenefit = 1.0, wfDistance = 0.5;
        double sumWFEnv = wfEnvBenefit + wfDistance;

        double envRaw = (envBenefit / 100.0) * wfEnvBenefit
                + (distanceScore / 100.0) * wfDistance;
        double envWeighted = (sumWFEnv > 0) ? (envRaw / sumWFEnv) * 100.0 : 0.0;
        envWeighted = Math.max(0.0, Math.min(100.0, envWeighted));

        int envOptionalFailed = 0;

        if (envBenefit < MIN_BENEFIT) {
            envOptionalFailed++;
        }
        if (distanceScore == 0) {
            envOptionalFailed++;
        }

        CategoryResultDTO environmentalCategory = new CategoryResultDTO(
                envWeighted, 2,
                0, 0,
                2, envOptionalFailed
        );

        // ========================
        // REGULATORY
        // ========================
        int hazard = (c.getHazardClassification() != null) ? c.getHazardClassification() : 0;
        int producer = (c.getProducerResponsibility() != null) ? c.getProducerResponsibility() : 0;
        boolean hazardAllowed = (logisticsDistance <= 100) && (safety >= MIN_SAFETY);

        // Mindestgrenzen für Reuse
        final int MIN_HAZARD = 50;
        final int MIN_PRODUCER = 100;

        // Gewichtsfaktoren (höher = wichtiger)
        double wfHazard = 1.0;
        double wfProducer = 0.5;
        double sumWF_Reg = wfHazard + wfProducer;

        // Gewichtete Berechnung
        double regRaw = (hazard / 100.0) * wfHazard
                + (producer / 100.0) * wfProducer;
        double regWeighted = (sumWF_Reg > 0) ? (regRaw / sumWF_Reg) * 100.0 : 0.0;

        regWeighted = Math.max(0.0, Math.min(100.0, regWeighted));

        int regOptional = 1;
        int regOptionalFailed = 0;

        // Hazard < 50 = failed (Spezialregel)
        int regMandatory = 1;
        boolean failedHazard = (hazard < MIN_HAZARD || !hazardAllowed);
        int regMandatoryPassed = failedHazard ? 0 : 1;

        // Producer < 100 = failed
        if (producer < MIN_PRODUCER) {
            regOptionalFailed++;
        }

        CategoryResultDTO regulatoryCategory = new CategoryResultDTO(
                regWeighted,
                2,
                regMandatory,
                regMandatoryPassed,
                regOptional,
                regOptionalFailed
        );

        // ========================
        // APPLICATION
        // ========================
        int power = (c.getPowerRequirements() != null) ? c.getPowerRequirements() : 0;
        int age = (c.getAgeChemistry() != null) ? c.getAgeChemistry() : 0;

        // Mindestgrenzen für Reuse
        final int MIN_POWER = 50;
        final int MAX_AGE = 10;    

        // Gewichtsfaktoren
        double wfPower = 1.0;
        double wfAge = 0.7;
        double sumWFApp = wfPower + wfAge;

        // Power-Score: linear bis MIN_POWER
        double powerScore = (power >= MIN_POWER) ? 100.0 : (power / (double) MIN_POWER) * 100.0;

        // Age-Score: je älter, desto schlechter
        double ageScore = 100.0 - Math.min(age, MAX_AGE) / (double) MAX_AGE * 100.0;

        // Gewichtung
        double appRaw = (powerScore / 100.0) * wfPower + (ageScore / 100.0) * wfAge;
        double appWeighted = (sumWFApp > 0) ? (appRaw / sumWFApp) * 100.0 : 0.0;
        appWeighted = Math.max(0.0, Math.min(100.0, appWeighted));

        // Optional/Pflicht
        int appMandatory = 1;
        int appMandatoryPassed = (power >= MIN_POWER ? 1 : 0);
        int appOptional = 1; 
        int appOptionalFailed = (ageScore < 50 ? 1 : 0);

        CategoryResultDTO applicationCategory = new CategoryResultDTO(
                appWeighted,
                2,
                appMandatory, appMandatoryPassed,
                appOptional, appOptionalFailed
        );

        // ========================
        // TOTAL SCORE
        // ========================
        double overall = technicalCategory.getWeighted() * 0.35
                + economicCategory.getWeighted() * 0.20
                + environmentalCategory.getWeighted() * 0.20
                + regulatoryCategory.getWeighted() * 0.10
                + applicationCategory.getWeighted() * 0.15;

        // ========================
        // FIRST DECISION CRITERIA
        // ========================
        // 1. Alle Mandatory-Kriterien müssen erfüllt sein
        boolean allMandatoryPassed
                = (technicalCategory.getMandatoryPassed() == technicalCategory.getMandatory()) && (regulatoryCategory.getMandatoryPassed() == regulatoryCategory.getMandatory())
                && (applicationCategory.getMandatoryPassed() == applicationCategory.getMandatory());
        // Economic, Environmental haben keine Mandatory (= 0), also immer erfüllt

        // 2. Zähle alle optionalen Kriterien über alle Kategorien
        int totalOptional = technicalCategory.getOptional()
                + economicCategory.getOptional()
                + environmentalCategory.getOptional()
                + regulatoryCategory.getOptional()
                + applicationCategory.getOptional();

        int totalOptionalFailed = technicalCategory.getOptionalFailed()
                + economicCategory.getOptionalFailed()
                + environmentalCategory.getOptionalFailed()
                + regulatoryCategory.getOptionalFailed()
                + applicationCategory.getOptionalFailed();

        // Max. 50% der optionalen dürfen failed sein
        boolean optionalCriteriaOk = (totalOptional == 0)
                || (totalOptionalFailed <= totalOptional * 0.5);

        // 3. Gesamtscore muss >= 60 sein
        boolean scoreOk = (overall >= 60);

        // FINALE ENTSCHEIDUNG
        boolean recommendReuse = allMandatoryPassed && optionalCriteriaOk && scoreOk;

        //Packt alle Kategorien sauber ins Ergebnis
        Map<String, CategoryResultDTO> resultCats = new HashMap<>();
        resultCats.put("technical", technicalCategory);
        resultCats.put("economic", economicCategory);
        resultCats.put("environmental", environmentalCategory);
        resultCats.put("regulatory", regulatoryCategory);
        resultCats.put("application", applicationCategory);

        return new DecisionResultDTO(recommendReuse, overall, resultCats);
    }
}
