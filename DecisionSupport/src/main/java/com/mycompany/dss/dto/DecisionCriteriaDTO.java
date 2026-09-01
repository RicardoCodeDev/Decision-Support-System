package com.mycompany.dss.dto;

// DTO für Decision Support Kriterien

public class DecisionCriteriaDTO {
    
    // Technical 
    private Integer soh;
    private Integer remainingCharge;
    private Integer internalResistance;
    private Integer safetyDamage;
    private Integer bmsCondition;
    
    // Economic 
    private Integer costRefurbishment;
    private Integer marketDemand;
    
    // Environmental
    private Integer environmentalBenefit;
    private Integer logisticsFootprint;
    
    // Regulatory 
    private Integer hazardClassification;
    private Integer producerResponsibility;
    
    // Application 
    private Integer powerRequirements;
    private Integer ageChemistry;

    public DecisionCriteriaDTO() {}
    
    public Integer getSoh() {
        return soh;
    }

    public void setSoh(Integer soh) {
        this.soh = soh;
    }

    public Integer getRemainingCharge() {
        return remainingCharge;
    }

    public void setRemainingCharge(Integer remainingCharge) {
        this.remainingCharge = remainingCharge;
    }

    public Integer getInternalResistance() {
        return internalResistance;
    }

    public void setInternalResistance(Integer internalResistance) {
        this.internalResistance = internalResistance;
    }

    public Integer getSafetyDamage() {
        return safetyDamage;
    }

    public void setSafetyDamage(Integer safetyDamage) {
        this.safetyDamage = safetyDamage;
    }

    public Integer getBmsCondition() {
        return bmsCondition;
    }

    public void setBmsCondition(Integer bmsCondition) {
        this.bmsCondition = bmsCondition;
    }

    public Integer getCostRefurbishment() {
        return costRefurbishment;
    }

    public void setCostRefurbishment(Integer costRefurbishment) {
        this.costRefurbishment = costRefurbishment;
    }

    public Integer getMarketDemand() {
        return marketDemand;
    }

    public void setMarketDemand(Integer marketDemand) {
        this.marketDemand = marketDemand;
    }

    public Integer getEnvironmentalBenefit() {
        return environmentalBenefit;
    }

    public void setEnvironmentalBenefit(Integer environmentalBenefit) {
        this.environmentalBenefit = environmentalBenefit;
    }

    public Integer getLogisticsFootprint() {
        return logisticsFootprint;
    }

    public void setLogisticsFootprint(Integer logisticsFootprint) {
        this.logisticsFootprint = logisticsFootprint;
    }

    public Integer getHazardClassification() {
        return hazardClassification;
    }

    public void setHazardClassification(Integer hazardClassification) {
        this.hazardClassification = hazardClassification;
    }

    public Integer getProducerResponsibility() {
        return producerResponsibility;
    }

    public void setProducerResponsibility(Integer producerResponsibility) {
        this.producerResponsibility = producerResponsibility;
    }

    public Integer getPowerRequirements() {
        return powerRequirements;
    }

    public void setPowerRequirements(Integer powerRequirements) {
        this.powerRequirements = powerRequirements;
    }

    public Integer getAgeChemistry() {
        return ageChemistry;
    }

    public void setAgeChemistry(Integer ageChemistry) {
        this.ageChemistry = ageChemistry;
    }
}
