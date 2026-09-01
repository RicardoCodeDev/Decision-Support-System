package com.mycompany.dss.model;

import jakarta.persistence.*;

@Entity
@Table(name = "performance_data")
public class PerformanceData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "battery_passport_id", unique = true)
    private BatteryPassport batteryPassport;

    @Column(name = "rated_capacity_ah", precision = 10, scale = 2)
    private Double ratedCapacityAh;

    @Column(name = "certified_usable_energy_kwh", precision = 10, scale = 2)
    private Double certifiedUsableEnergyKwh;

    @Column(name = "voltage_min", precision = 10, scale = 2)
    private Double voltageMin;

    @Column(name = "voltage_nominal", precision = 10, scale = 2)
    private Double voltageNominal;

    @Column(name = "voltage_max", precision = 10, scale = 2)
    private Double voltageMax;

    @Column(name = "temperature_range_min", precision = 5, scale = 2)
    private Double temperatureRangeMin;

    @Column(name = "temperature_range_max", precision = 5, scale = 2)
    private Double temperatureRangeMax;

    @Column(name = "original_power_capacity_watts", precision = 10, scale = 2)
    private Double originalPowerCapacityWatts;

    @Column(name = "maximum_permitted_capacity", precision = 10, scale = 2)
    private Double maximumPermittedCapacity;

    @Column(name = "internal_resistance_cell", precision = 10, scale = 4)
    private Double internalResistanceCell;

    @Column(name = "internal_resistance_pack", precision = 10, scale = 4)
    private Double internalResistancePack;

    public PerformanceData() {
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

    public Double getRatedCapacityAh() {
        return ratedCapacityAh;
    }

    public void setRatedCapacityAh(Double ratedCapacityAh) {
        this.ratedCapacityAh = ratedCapacityAh;
    }

    public Double getCertifiedUsableEnergyKwh() {
        return certifiedUsableEnergyKwh;
    }

    public void setCertifiedUsableEnergyKwh(Double certifiedUsableEnergyKwh) {
        this.certifiedUsableEnergyKwh = certifiedUsableEnergyKwh;
    }

    public Double getVoltageMin() {
        return voltageMin;
    }

    public void setVoltageMin(Double voltageMin) {
        this.voltageMin = voltageMin;
    }

    public Double getVoltageNominal() {
        return voltageNominal;
    }

    public void setVoltageNominal(Double voltageNominal) {
        this.voltageNominal = voltageNominal;
    }

    public Double getVoltageMax() {
        return voltageMax;
    }

    public void setVoltageMax(Double voltageMax) {
        this.voltageMax = voltageMax;
    }

    public Double getTemperatureRangeMin() {
        return temperatureRangeMin;
    }

    public void setTemperatureRangeMin(Double temperatureRangeMin) {
        this.temperatureRangeMin = temperatureRangeMin;
    }

    public Double getTemperatureRangeMax() {
        return temperatureRangeMax;
    }

    public void setTemperatureRangeMax(Double temperatureRangeMax) {
        this.temperatureRangeMax = temperatureRangeMax;
    }

    public Double getOriginalPowerCapacityWatts() {
        return originalPowerCapacityWatts;
    }

    public void setOriginalPowerCapacityWatts(Double originalPowerCapacityWatts) {
        this.originalPowerCapacityWatts = originalPowerCapacityWatts;
    }

    public Double getMaximumPermittedCapacity() {
        return maximumPermittedCapacity;
    }

    public void setMaximumPermittedCapacity(Double maximumPermittedCapacity) {
        this.maximumPermittedCapacity = maximumPermittedCapacity;
    }

    public Double getInternalResistanceCell() {
        return internalResistanceCell;
    }

    public void setInternalResistanceCell(Double internalResistanceCell) {
        this.internalResistanceCell = internalResistanceCell;
    }

    public Double getInternalResistancePack() {
        return internalResistancePack;
    }

    public void setInternalResistancePack(Double internalResistancePack) {
        this.internalResistancePack = internalResistancePack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PerformanceData)) {
            return false;
        }
        PerformanceData that = (PerformanceData) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
