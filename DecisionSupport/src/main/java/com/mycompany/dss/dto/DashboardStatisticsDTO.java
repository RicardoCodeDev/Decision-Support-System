package com.mycompany.dss.dto;

import java.util.Map;
import java.util.HashMap;

//DTO für Dashboard-Statistiken
public class DashboardStatisticsDTO {

    private long totalBatteries;
    private long perfectBatteries;
    private Map<String, Long> statusDistribution = new HashMap<>();
    private Map<String, Long> manufacturerDistribution = new HashMap<>();
    private Double averageCapacity;
    private Double averageEnergy;

    public DashboardStatisticsDTO() {}

    public long getTotalBatteries() {
        return totalBatteries;
    }

    public void setTotalBatteries(long totalBatteries) {
        this.totalBatteries = totalBatteries;
    }

    public long getPerfectBatteries() {
        return perfectBatteries;
    }

    public void setPerfectBatteries(long perfectBatteries) {
        this.perfectBatteries = perfectBatteries;
    }

    public Map<String, Long> getStatusDistribution() {
        return statusDistribution;
    }

    public void setStatusDistribution(Map<String, Long> statusDistribution) {
        this.statusDistribution = statusDistribution;
    }

    public Map<String, Long> getManufacturerDistribution() {
        return manufacturerDistribution;
    }

    public void setManufacturerDistribution(Map<String, Long> manufacturerDistribution) {
        this.manufacturerDistribution = manufacturerDistribution;
    }

    public Double getAverageCapacity() {
        return averageCapacity;
    }

    public void setAverageCapacity(Double averageCapacity) {
        this.averageCapacity = averageCapacity;
    }

    public Double getAverageEnergy() {
        return averageEnergy;
    }

    public void setAverageEnergy(Double averageEnergy) {
        this.averageEnergy = averageEnergy;
    }

    @Override
    public String toString() {
        return "DashboardStatisticsDTO{" +
                "totalBatteries=" + totalBatteries +
                ", perfectBatteries=" + perfectBatteries +
                ", averageCapacity=" + averageCapacity +
                ", averageEnergy=" + averageEnergy +
                '}';
    }
}