package com.mycompany.dss.model;

import jakarta.persistence.*;

@Entity
@Table(name = "battery_materials")
public class BatteryMaterials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "battery_passport_id", unique = true)
    private BatteryPassport batteryPassport;

    @Column(name = "battery_chemistry", length = 100)
    private String batteryChemistry;

    @Column(name = "cathode_material", length = 255)
    private String cathodeMaterial;

    @Column(name = "anode_material", length = 255)
    private String anodeMaterial;

    @Column(name = "electrolyte_material", length = 255)
    private String electrolyteMaterial;

    @Column(name = "environmental_impact", columnDefinition = "TEXT")
    private String environmentalImpact;

    @Column(name = "health_safety_impact", columnDefinition = "TEXT")
    private String healthSafetyImpact;

    // PDF-Felder für kritische Rohstoffe
    @Lob
    @Column(name = "critical_raw_materials_pdf")
    private byte[] criticalRawMaterialsPdf;

    @Column(name = "critical_raw_materials_pdf_name")
    private String criticalRawMaterialsPdfName;

    // PDF-Felder für Gefahrstoffe
    @Lob
    @Column(name = "hazardous_substances_pdf")
    private byte[] hazardousSubstancesPdf;

    @Column(name = "hazardous_substances_pdf_name")
    private String hazardousSubstancesPdfName;

    public BatteryMaterials() {
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

    public String getBatteryChemistry() {
        return batteryChemistry;
    }

    public void setBatteryChemistry(String batteryChemistry) {
        this.batteryChemistry = batteryChemistry;
    }

    public String getCathodeMaterial() {
        return cathodeMaterial;
    }

    public void setCathodeMaterial(String cathodeMaterial) {
        this.cathodeMaterial = cathodeMaterial;
    }

    public String getAnodeMaterial() {
        return anodeMaterial;
    }

    public void setAnodeMaterial(String anodeMaterial) {
        this.anodeMaterial = anodeMaterial;
    }

    public String getElectrolyteMaterial() {
        return electrolyteMaterial;
    }

    public void setElectrolyteMaterial(String electrolyteMaterial) {
        this.electrolyteMaterial = electrolyteMaterial;
    }

    public String getEnvironmentalImpact() {
        return environmentalImpact;
    }

    public void setEnvironmentalImpact(String environmentalImpact) {
        this.environmentalImpact = environmentalImpact;
    }

    public String getHealthSafetyImpact() {
        return healthSafetyImpact;
    }

    public void setHealthSafetyImpact(String healthSafetyImpact) {
        this.healthSafetyImpact = healthSafetyImpact;
    }

    public byte[] getCriticalRawMaterialsPdf() {
        return criticalRawMaterialsPdf;
    }

    public void setCriticalRawMaterialsPdf(byte[] criticalRawMaterialsPdf) {
        this.criticalRawMaterialsPdf = criticalRawMaterialsPdf;
    }

    public String getCriticalRawMaterialsPdfName() {
        return criticalRawMaterialsPdfName;
    }

    public void setCriticalRawMaterialsPdfName(String criticalRawMaterialsPdfName) {
        this.criticalRawMaterialsPdfName = criticalRawMaterialsPdfName;
    }

    public byte[] getHazardousSubstancesPdf() {
        return hazardousSubstancesPdf;
    }

    public void setHazardousSubstancesPdf(byte[] hazardousSubstancesPdf) {
        this.hazardousSubstancesPdf = hazardousSubstancesPdf;
    }

    public String getHazardousSubstancesPdfName() {
        return hazardousSubstancesPdfName;
    }

    public void setHazardousSubstancesPdfName(String hazardousSubstancesPdfName) {
        this.hazardousSubstancesPdfName = hazardousSubstancesPdfName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BatteryMaterials)) {
            return false;
        }
        BatteryMaterials that = (BatteryMaterials) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}