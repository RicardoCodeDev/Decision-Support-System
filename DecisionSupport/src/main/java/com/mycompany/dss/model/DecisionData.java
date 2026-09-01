package com.mycompany.dss.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "decision_data")
public class DecisionData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "battery_passport_id", nullable = false, unique = true)
    private BatteryPassport batteryPassport;

    // TECHNICAL CRITERIA
    @Column(name = "soh")
    private Integer soh;

    @Column(name = "remaining_charge")
    private Integer remainingCharge;

    @Column(name = "internal_resistance")
    private Integer internalResistance;

    @Column(name = "safety_damage")
    private Integer safetyDamage;

    @Column(name = "bms_condition")
    private Integer bmsCondition;

    // ECONOMIC CRITERIA
    @Column(name = "cost_refurbishment")
    private Integer costRefurbishment;

    @Column(name = "market_demand")
    private Integer marketDemand;

    // ENVIRONMENTAL CRITERIA
    @Column(name = "environmental_benefit")
    private Integer environmentalBenefit;

    @Column(name = "logistics_footprint")
    private Integer logisticsFootprint;

    // REGULATORY CRITERIA
    @Column(name = "hazard_classification")
    private Integer hazardClassification;

    @Column(name = "producer_responsibility")
    private Integer producerResponsibility;

    // APPLICATION CRITERIA
    @Column(name = "power_requirements")
    private Integer powerRequirements;

    @Column(name = "age_chemistry")
    private Integer ageChemistry;

    // DECISION RESULT
    @Column(name = "recommendation")
    private Boolean recommendation;

    @Column(name = "overall_score")
    private Double overallScore;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public DecisionData() {
    }

    public DecisionData(BatteryPassport batteryPassport) {
        this.batteryPassport = batteryPassport;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BatteryPassport getBatteryPassport() {
        return batteryPassport;
    }

    public void setBatteryPassport(BatteryPassport batteryPassport) {
        this.batteryPassport = batteryPassport;
    }

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

    public Boolean getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(Boolean recommendation) {
        this.recommendation = recommendation;
    }

    public Double getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(Double overallScore) {
        this.overallScore = overallScore;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}