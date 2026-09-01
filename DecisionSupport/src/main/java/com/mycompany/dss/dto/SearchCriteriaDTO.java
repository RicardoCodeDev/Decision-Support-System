package com.mycompany.dss.dto;

public class SearchCriteriaDTO {

    private String batteryId;

    private String manufacturer;

    private String status;

    public SearchCriteriaDTO() {}

    public String getBatteryId() { 
        return batteryId; 
    }

    public void setBatteryId(String batteryId) { 
        this.batteryId = batteryId; 
    }

    public String getManufacturer() { 
        return manufacturer; 
    }
    

    public void setManufacturer(String manufacturer) { 
        this.manufacturer = manufacturer; 
    }

    public String getStatus() { 
        return status; 
    }
    
    public void setStatus(String status) { 
        this.status = status; 
    }
}
