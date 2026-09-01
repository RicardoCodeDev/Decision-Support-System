package com.mycompany.dss.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class BatteryPassportDTO {

    private Long id;
    private String batteryIdentification;
    private String batteryCategory;
    private String manufacturerIdentification;
    private String batteryStatus;
    private MaterialsDTO materials;
    private CircularityDTO circularity;
    private PerformanceDTO performance;
    private List<FileMetadataDTO> files = new ArrayList<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate manufacturerDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    public BatteryPassportDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBatteryIdentification() {
        return batteryIdentification;
    }

    public void setBatteryIdentification(String batteryIdentification) {
        this.batteryIdentification = batteryIdentification;
    }

    public String getBatteryCategory() {
        return batteryCategory;
    }

    public void setBatteryCategory(String batteryCategory) {
        this.batteryCategory = batteryCategory;
    }

    public String getManufacturerIdentification() {
        return manufacturerIdentification;
    }

    public void setManufacturerIdentification(String manufacturerIdentification) {
        this.manufacturerIdentification = manufacturerIdentification;
    }

    public LocalDate getManufacturerDate() {
        return manufacturerDate;
    }

    public void setManufacturerDate(LocalDate manufacturerDate) {
        this.manufacturerDate = manufacturerDate;
    }

    public String getBatteryStatus() {
        return batteryStatus;
    }

    public void setBatteryStatus(String batteryStatus) {
        this.batteryStatus = batteryStatus;
    }

    public MaterialsDTO getMaterials() {
        return materials;
    }

    public void setMaterials(MaterialsDTO materials) {
        this.materials = materials;
    }

    public CircularityDTO getCircularity() {
        return circularity;
    }

    public void setCircularity(CircularityDTO circularity) {
        this.circularity = circularity;
    }

    public PerformanceDTO getPerformance() {
        return performance;
    }

    public void setPerformance(PerformanceDTO performance) {
        this.performance = performance;
    }

    public List<FileMetadataDTO> getFiles() {
        return files;
    }

    public void setFiles(List<FileMetadataDTO> files) {
        this.files = files;
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
    
    //MaterialsDTO mit PDF-Unterstützung
    public static class MaterialsDTO {

        private String batteryChemistry;
        private String cathodeMaterial;
        private String anodeMaterial;
        private String electrolyteMaterial;
        private String environmentalImpact;
        private String healthSafetyImpact;
        private String criticalRawMaterials;
        private String hazardousSubstances;
        private String criticalRawMaterialsPdfName;
        private String hazardousSubstancesPdfName;

        @JsonIgnore
        private String criticalRawMaterialsPdf;

        @JsonIgnore
        private String hazardousSubstancesPdf;

        public MaterialsDTO() {
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

        public String getCriticalRawMaterials() {
            return criticalRawMaterials;
        }

        public void setCriticalRawMaterials(String criticalRawMaterials) {
            this.criticalRawMaterials = criticalRawMaterials;
        }

        public String getHazardousSubstances() {
            return hazardousSubstances;
        }

        public void setHazardousSubstances(String hazardousSubstances) {
            this.hazardousSubstances = hazardousSubstances;
        }

        public String getCriticalRawMaterialsPdfName() {
            return criticalRawMaterialsPdfName;
        }

        public void setCriticalRawMaterialsPdfName(String criticalRawMaterialsPdfName) {
            this.criticalRawMaterialsPdfName = criticalRawMaterialsPdfName;
        }

        public String getHazardousSubstancesPdfName() {
            return hazardousSubstancesPdfName;
        }

        public void setHazardousSubstancesPdfName(String hazardousSubstancesPdfName) {
            this.hazardousSubstancesPdfName = hazardousSubstancesPdfName;
        }

        public String getCriticalRawMaterialsPdf() {
            return criticalRawMaterialsPdf;
        }

        public void setCriticalRawMaterialsPdf(String criticalRawMaterialsPdf) {
            this.criticalRawMaterialsPdf = criticalRawMaterialsPdf;
        }

        public String getHazardousSubstancesPdf() {
            return hazardousSubstancesPdf;
        }

        public void setHazardousSubstancesPdf(String hazardousSubstancesPdf) {
            this.hazardousSubstancesPdf = hazardousSubstancesPdf;
        }
    }

    //CircularityDTO mit PDF-Unterstützung
    public static class CircularityDTO {

        private String removalManual;
        private String disassemblyManual;
        private String componentPartNumbers;
        private String safetyInstructions;
        private Double preConsumerRecycledLi;
        private Double preConsumerRecycledCo;
        private Double preConsumerRecycledNi;
        private Double preConsumerRecycledPb;
        private Double postConsumerRecycledLi;
        private Double postConsumerRecycledCo;
        private Double postConsumerRecycledNi;
        private Double postConsumerRecycledPb;
        
        // PDF-Metadaten
        private String disassemblyManualPdfName;
        private String removalManualPdfName;
        private String safetyInstructionsPdfName;

        // PDF-Daten als Base64
        @JsonIgnore
        private String disassemblyManualPdf;

        @JsonIgnore
        private String removalManualPdf;

        @JsonIgnore
        private String safetyInstructionsPdf;

        public CircularityDTO() {
        }

        public String getRemovalManual() {
            return removalManual;
        }

        public void setRemovalManual(String removalManual) {
            this.removalManual = removalManual;
        }

        public String getDisassemblyManual() {
            return disassemblyManual;
        }

        public void setDisassemblyManual(String disassemblyManual) {
            this.disassemblyManual = disassemblyManual;
        }

        public String getComponentPartNumbers() {
            return componentPartNumbers;
        }

        public void setComponentPartNumbers(String componentPartNumbers) {
            this.componentPartNumbers = componentPartNumbers;
        }

        public String getSafetyInstructions() {
            return safetyInstructions;
        }

        public void setSafetyInstructions(String safetyInstructions) {
            this.safetyInstructions = safetyInstructions;
        }

        public Double getPreConsumerRecycledLi() {
            return preConsumerRecycledLi;
        }

        public void setPreConsumerRecycledLi(Double value) {
            this.preConsumerRecycledLi = value;
        }

        public Double getPreConsumerRecycledCo() {
            return preConsumerRecycledCo;
        }

        public void setPreConsumerRecycledCo(Double value) {
            this.preConsumerRecycledCo = value;
        }

        public Double getPreConsumerRecycledNi() {
            return preConsumerRecycledNi;
        }

        public void setPreConsumerRecycledNi(Double value) {
            this.preConsumerRecycledNi = value;
        }

        public Double getPreConsumerRecycledPb() {
            return preConsumerRecycledPb;
        }

        public void setPreConsumerRecycledPb(Double value) {
            this.preConsumerRecycledPb = value;
        }

        public Double getPostConsumerRecycledLi() {
            return postConsumerRecycledLi;
        }

        public void setPostConsumerRecycledLi(Double value) {
            this.postConsumerRecycledLi = value;
        }

        public Double getPostConsumerRecycledCo() {
            return postConsumerRecycledCo;
        }

        public void setPostConsumerRecycledCo(Double value) {
            this.postConsumerRecycledCo = value;
        }

        public Double getPostConsumerRecycledNi() {
            return postConsumerRecycledNi;
        }

        public void setPostConsumerRecycledNi(Double value) {
            this.postConsumerRecycledNi = value;
        }

        public Double getPostConsumerRecycledPb() {
            return postConsumerRecycledPb;
        }

        public void setPostConsumerRecycledPb(Double value) {
            this.postConsumerRecycledPb = value;
        }

        public String getDisassemblyManualPdfName() {
            return disassemblyManualPdfName;
        }

        public void setDisassemblyManualPdfName(String disassemblyManualPdfName) {
            this.disassemblyManualPdfName = disassemblyManualPdfName;
        }

        public String getRemovalManualPdfName() {
            return removalManualPdfName;
        }

        public void setRemovalManualPdfName(String removalManualPdfName) {
            this.removalManualPdfName = removalManualPdfName;
        }

        public String getSafetyInstructionsPdfName() {
            return safetyInstructionsPdfName;
        }

        public void setSafetyInstructionsPdfName(String safetyInstructionsPdfName) {
            this.safetyInstructionsPdfName = safetyInstructionsPdfName;
        }

        public String getDisassemblyManualPdf() {
            return disassemblyManualPdf;
        }

        public void setDisassemblyManualPdf(String disassemblyManualPdf) {
            this.disassemblyManualPdf = disassemblyManualPdf;
        }

        public String getRemovalManualPdf() {
            return removalManualPdf;
        }

        public void setRemovalManualPdf(String removalManualPdf) {
            this.removalManualPdf = removalManualPdf;
        }

        public String getSafetyInstructionsPdf() {
            return safetyInstructionsPdf;
        }

        public void setSafetyInstructionsPdf(String safetyInstructionsPdf) {
            this.safetyInstructionsPdf = safetyInstructionsPdf;
        }
    }

    //PerformanceDTO

    public static class PerformanceDTO {

        private Double ratedCapacityAh;
        private Double certifiedUsableEnergyKwh;
        private Double voltageMin;
        private Double voltageNominal;
        private Double voltageMax;
        private Double temperatureRangeMin;
        private Double temperatureRangeMax;
        private Double originalPowerCapacityWatts;
        private Double maximumPermittedCapacity;
        private Double internalResistanceCell;
        private Double internalResistancePack;

        public PerformanceDTO() {
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
    }

    // FileMetadataDTO
    public static class FileMetadataDTO {

        private Long id;
        private String fileType;
        private String fileName;
        private String mimeType;
        private Long fileSize;
        private LocalDateTime uploadedAt;
        private String downloadUrl;

        public FileMetadataDTO() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getFileType() {
            return fileType;
        }

        public void setFileType(String fileType) {
            this.fileType = fileType;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getMimeType() {
            return mimeType;
        }

        public void setMimeType(String mimeType) {
            this.mimeType = mimeType;
        }

        public Long getFileSize() {
            return fileSize;
        }

        public void setFileSize(Long fileSize) {
            this.fileSize = fileSize;
        }

        public LocalDateTime getUploadedAt() {
            return uploadedAt;
        }

        public void setUploadedAt(LocalDateTime uploadedAt) {
            this.uploadedAt = uploadedAt;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }
    }
}