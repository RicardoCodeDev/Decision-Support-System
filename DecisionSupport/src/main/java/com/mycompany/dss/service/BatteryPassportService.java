package com.mycompany.dss.service;

import com.mycompany.dss.dao.BatteryPassportDAO;
import com.mycompany.dss.dto.*;
import com.mycompany.dss.model.*;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;


import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Stateless
@Transactional
public class BatteryPassportService {

    private static final Logger LOGGER = Logger.getLogger(BatteryPassportService.class.getName());

    @Inject
    private BatteryPassportDAO dao;
    
    //  Erstellt eine neue Batterie aus einem DTO.

    public BatteryPassportDTO createBattery(BatteryPassportDTO dto) {
        try {
            // Validierung
            if (dto.getBatteryIdentification() == null || dto.getBatteryIdentification().trim().isEmpty()) {
                throw new IllegalArgumentException("Battery identification is required");
            }

            // Prüfe auf Duplikate
            Optional<BatteryPassport> existing = dao.findByBatteryIdentification(dto.getBatteryIdentification());
            if (existing.isPresent()) {
                throw new IllegalArgumentException("Battery with ID " + dto.getBatteryIdentification() + " already exists");
            }

            // Entity erstellen
            BatteryPassport battery = new BatteryPassport();
            battery.setBatteryIdentification(dto.getBatteryIdentification());
            battery.setBatteryCategory(dto.getBatteryCategory());
            battery.setManufacturerIdentification(dto.getManufacturerIdentification());
            battery.setManufacturerDate(dto.getManufacturerDate());
            battery.setBatteryStatus(dto.getBatteryStatus());

            // Materials mit PDFs
            if (dto.getMaterials() != null) {
                BatteryMaterials materials = new BatteryMaterials();
                mapMaterialsFromDTO(dto.getMaterials(), materials);
                
                // PDF-Bytes setzen (Base64 dekodieren)
                if (dto.getMaterials().getCriticalRawMaterialsPdf() != null 
                        && !dto.getMaterials().getCriticalRawMaterialsPdf().isEmpty()) {
                    try {
                        materials.setCriticalRawMaterialsPdf(
                            Base64.getDecoder().decode(dto.getMaterials().getCriticalRawMaterialsPdf())
                        );
                        LOGGER.info("Critical raw materials PDF saved: " + 
                                  dto.getMaterials().getCriticalRawMaterialsPdfName());
                    } catch (Exception e) {
                        LOGGER.warning("Failed to decode critical raw materials PDF: " + e.getMessage());
                    }
                }
                
                if (dto.getMaterials().getHazardousSubstancesPdf() != null 
                        && !dto.getMaterials().getHazardousSubstancesPdf().isEmpty()) {
                    try {
                        materials.setHazardousSubstancesPdf(
                            Base64.getDecoder().decode(dto.getMaterials().getHazardousSubstancesPdf())
                        );
                        LOGGER.info("Hazardous substances PDF saved: " + 
                                  dto.getMaterials().getHazardousSubstancesPdfName());
                    } catch (Exception e) {
                        LOGGER.warning("Failed to decode hazardous substances PDF: " + e.getMessage());
                    }
                }
                
                battery.setMaterials(materials);
            }

            // Performance
            if (dto.getPerformance() != null) {
                PerformanceData performance = new PerformanceData();
                mapPerformanceFromDTO(dto.getPerformance(), performance);
                battery.setPerformance(performance);
            }

            // Circularity mit PDFs
            if (dto.getCircularity() != null) {
                CircularityData circularity = new CircularityData();
                mapCircularityFromDTO(dto.getCircularity(), circularity);
                
                // PDF-Bytes setzen (Base64 dekodieren)
                if (dto.getCircularity().getDisassemblyManualPdf() != null 
                        && !dto.getCircularity().getDisassemblyManualPdf().isEmpty()) {
                    try {
                        circularity.setDisassemblyManualPdf(
                            Base64.getDecoder().decode(dto.getCircularity().getDisassemblyManualPdf())
                        );
                        LOGGER.info("Disassembly manual PDF saved: " + 
                                  dto.getCircularity().getDisassemblyManualPdfName());
                    } catch (Exception e) {
                        LOGGER.warning("Failed to decode disassembly manual PDF: " + e.getMessage());
                    }
                }
                
                if (dto.getCircularity().getRemovalManualPdf() != null 
                        && !dto.getCircularity().getRemovalManualPdf().isEmpty()) {
                    try {
                        circularity.setRemovalManualPdf(
                            Base64.getDecoder().decode(dto.getCircularity().getRemovalManualPdf())
                        );
                        LOGGER.info("Removal manual PDF saved: " + 
                                  dto.getCircularity().getRemovalManualPdfName());
                    } catch (Exception e) {
                        LOGGER.warning("Failed to decode removal manual PDF: " + e.getMessage());
                    }
                }
                
                if (dto.getCircularity().getSafetyInstructionsPdf() != null 
                        && !dto.getCircularity().getSafetyInstructionsPdf().isEmpty()) {
                    try {
                        circularity.setSafetyInstructionsPdf(
                            Base64.getDecoder().decode(dto.getCircularity().getSafetyInstructionsPdf())
                        );
                        LOGGER.info("Safety instructions PDF saved: " + 
                                  dto.getCircularity().getSafetyInstructionsPdfName());
                    } catch (Exception e) {
                        LOGGER.warning("Failed to decode safety instructions PDF: " + e.getMessage());
                    }
                }
                
                battery.setCircularity(circularity);
            }

            BatteryPassport created = dao.create(battery);
            LOGGER.info("Battery created successfully: " + created.getBatteryIdentification());

            return toDTO(created);
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Validation error creating battery: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            LOGGER.severe("Error creating battery: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to create battery", e);
        }
    }

    public BatteryPassportDTO updateBattery(Long id, BatteryPassportDTO dto) {
        try {
            Optional<BatteryPassport> existingOpt = dao.findById(id);
            if (existingOpt.isEmpty()) {
                throw new IllegalArgumentException("Battery not found with ID: " + id);
            }

            BatteryPassport battery = existingOpt.get();

            // Hauptdaten aktualisieren
            if (dto.getBatteryCategory() != null) {
                battery.setBatteryCategory(dto.getBatteryCategory());
            }
            if (dto.getManufacturerIdentification() != null) {
                battery.setManufacturerIdentification(dto.getManufacturerIdentification());
            }
            if (dto.getManufacturerDate() != null) {
                battery.setManufacturerDate(dto.getManufacturerDate());
            }
            if (dto.getBatteryStatus() != null) {
                battery.setBatteryStatus(dto.getBatteryStatus());
            }

            // Materials aktualisieren
            if (dto.getMaterials() != null) {
                if (battery.getMaterials() == null) {
                    battery.setMaterials(new BatteryMaterials());
                }
                mapMaterialsFromDTO(dto.getMaterials(), battery.getMaterials());
            }

            // Performance aktualisieren
            if (dto.getPerformance() != null) {
                if (battery.getPerformance() == null) {
                    battery.setPerformance(new PerformanceData());
                }
                mapPerformanceFromDTO(dto.getPerformance(), battery.getPerformance());
            }

            // Circularity aktualisieren
            if (dto.getCircularity() != null) {
                if (battery.getCircularity() == null) {
                    battery.setCircularity(new CircularityData());
                }
                mapCircularityFromDTO(dto.getCircularity(), battery.getCircularity());
            }

            BatteryPassport updated = dao.update(battery);
            LOGGER.info("Battery updated successfully: " + updated.getBatteryIdentification());

            return toDTO(updated);
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Validation error updating battery: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            LOGGER.severe("Error updating battery: " + e.getMessage());
            throw new RuntimeException("Failed to update battery", e);
        }
    }

    public List<BatteryPassportDTO> getAllBatteries() {
        try {
            List<BatteryPassport> batteries = dao.findAll();
            return batteries.stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOGGER.severe("Error getting all batteries: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve batteries", e);
        }
    }

    public Optional<BatteryPassportDTO> getBatteryById(Long id) {
        return dao.findById(id).map(this::toDTO);
    }

    public void deleteBattery(Long id) {
        try {
            Optional<BatteryPassport> battery = dao.findById(id);
            if (battery.isEmpty()) {
                throw new IllegalArgumentException("Battery not found with ID: " + id);
            }

            dao.deleteBattery(id);
            LOGGER.info("Battery deleted successfully with ID: " + id);
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Validation error deleting battery: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            LOGGER.severe("Error deleting battery: " + e.getMessage());
            throw new RuntimeException("Failed to delete battery", e);
        }
    }

    public List<BatteryPassportDTO> searchBatteries(SearchCriteriaDTO criteria) {
        try {
            List<BatteryPassport> batteries = dao.search(
                    criteria.getBatteryId(),
                    criteria.getManufacturer(),
                    criteria.getStatus()
            );
            return batteries.stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOGGER.severe("Error searching batteries: " + e.getMessage());
            throw new RuntimeException("Failed to search batteries", e);
        }
    }

    public DashboardStatisticsDTO getDashboardStatistics() {
        try {
            DashboardStatisticsDTO stats = new DashboardStatisticsDTO();

            stats.setTotalBatteries(dao.countAll());
            stats.setPerfectBatteries(dao.countByStatus("Perfekt"));
            stats.setStatusDistribution(dao.getStatusDistribution());
            stats.setManufacturerDistribution(dao.getManufacturerDistribution());
            stats.setAverageCapacity(dao.getAverageCapacity());
            stats.setAverageEnergy(dao.getAverageEnergy());

            LOGGER.info("Dashboard statistics retrieved successfully");
            return stats;
        } catch (Exception e) {
            LOGGER.severe("Error getting dashboard statistics: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve dashboard statistics", e);
        }
    }

    // ========== Mapping-Methoden ==========
    
    private void mapMaterialsFromDTO(BatteryPassportDTO.MaterialsDTO dto, BatteryMaterials entity) {
        entity.setBatteryChemistry(dto.getBatteryChemistry());
        entity.setCathodeMaterial(dto.getCathodeMaterial());
        entity.setAnodeMaterial(dto.getAnodeMaterial());
        entity.setElectrolyteMaterial(dto.getElectrolyteMaterial());
        entity.setEnvironmentalImpact(dto.getEnvironmentalImpact());
        entity.setHealthSafetyImpact(dto.getHealthSafetyImpact());
        
        // PDF-Namen setzen
        entity.setCriticalRawMaterialsPdfName(dto.getCriticalRawMaterialsPdfName());
        entity.setHazardousSubstancesPdfName(dto.getHazardousSubstancesPdfName());
    }

    private void mapPerformanceFromDTO(BatteryPassportDTO.PerformanceDTO dto, PerformanceData entity) {
        entity.setRatedCapacityAh(dto.getRatedCapacityAh());
        entity.setCertifiedUsableEnergyKwh(dto.getCertifiedUsableEnergyKwh());
        entity.setVoltageMin(dto.getVoltageMin());
        entity.setVoltageNominal(dto.getVoltageNominal());
        entity.setVoltageMax(dto.getVoltageMax());
        entity.setTemperatureRangeMin(dto.getTemperatureRangeMin());
        entity.setTemperatureRangeMax(dto.getTemperatureRangeMax());
        entity.setOriginalPowerCapacityWatts(dto.getOriginalPowerCapacityWatts());
        entity.setMaximumPermittedCapacity(dto.getMaximumPermittedCapacity());
        entity.setInternalResistanceCell(dto.getInternalResistanceCell());
        entity.setInternalResistancePack(dto.getInternalResistancePack());
    }

    private void mapCircularityFromDTO(BatteryPassportDTO.CircularityDTO dto, CircularityData entity) {
        entity.setComponentPartNumbers(dto.getComponentPartNumbers());
        entity.setPreConsumerRecycledLi(dto.getPreConsumerRecycledLi());
        entity.setPreConsumerRecycledCo(dto.getPreConsumerRecycledCo());
        entity.setPreConsumerRecycledNi(dto.getPreConsumerRecycledNi());
        entity.setPreConsumerRecycledPb(dto.getPreConsumerRecycledPb());
        entity.setPostConsumerRecycledLi(dto.getPostConsumerRecycledLi());
        entity.setPostConsumerRecycledCo(dto.getPostConsumerRecycledCo());
        entity.setPostConsumerRecycledNi(dto.getPostConsumerRecycledNi());
        entity.setPostConsumerRecycledPb(dto.getPostConsumerRecycledPb());
        entity.setDisassemblyManualPdfName(dto.getDisassemblyManualPdfName());
        entity.setRemovalManualPdfName(dto.getRemovalManualPdfName());
        entity.setSafetyInstructionsPdfName(dto.getSafetyInstructionsPdfName());
    }

    private BatteryPassportDTO toDTO(BatteryPassport battery) {
        BatteryPassportDTO dto = new BatteryPassportDTO();
        dto.setId(battery.getId());
        dto.setBatteryIdentification(battery.getBatteryIdentification());
        dto.setBatteryCategory(battery.getBatteryCategory());
        dto.setManufacturerIdentification(battery.getManufacturerIdentification());
        dto.setManufacturerDate(battery.getManufacturerDate());
        dto.setBatteryStatus(battery.getBatteryStatus());

        if (battery.getMaterials() != null) {
            BatteryPassportDTO.MaterialsDTO mat = new BatteryPassportDTO.MaterialsDTO();
            mat.setBatteryChemistry(battery.getMaterials().getBatteryChemistry());
            mat.setCathodeMaterial(battery.getMaterials().getCathodeMaterial());
            mat.setAnodeMaterial(battery.getMaterials().getAnodeMaterial());
            mat.setElectrolyteMaterial(battery.getMaterials().getElectrolyteMaterial());
            mat.setEnvironmentalImpact(battery.getMaterials().getEnvironmentalImpact());
            mat.setHealthSafetyImpact(battery.getMaterials().getHealthSafetyImpact());
            
            // PDF-Namen übertragen (WICHTIG für Frontend)
            mat.setCriticalRawMaterialsPdfName(battery.getMaterials().getCriticalRawMaterialsPdfName());
            mat.setHazardousSubstancesPdfName(battery.getMaterials().getHazardousSubstancesPdfName());
            
            dto.setMaterials(mat);
        }

        if (battery.getPerformance() != null) {
            BatteryPassportDTO.PerformanceDTO perf = new BatteryPassportDTO.PerformanceDTO();
            perf.setRatedCapacityAh(battery.getPerformance().getRatedCapacityAh());
            perf.setCertifiedUsableEnergyKwh(battery.getPerformance().getCertifiedUsableEnergyKwh());
            perf.setVoltageMin(battery.getPerformance().getVoltageMin());
            perf.setVoltageNominal(battery.getPerformance().getVoltageNominal());
            perf.setVoltageMax(battery.getPerformance().getVoltageMax());
            perf.setTemperatureRangeMin(battery.getPerformance().getTemperatureRangeMin());
            perf.setTemperatureRangeMax(battery.getPerformance().getTemperatureRangeMax());
            perf.setOriginalPowerCapacityWatts(battery.getPerformance().getOriginalPowerCapacityWatts());
            perf.setMaximumPermittedCapacity(battery.getPerformance().getMaximumPermittedCapacity());
            perf.setInternalResistanceCell(battery.getPerformance().getInternalResistanceCell());
            perf.setInternalResistancePack(battery.getPerformance().getInternalResistancePack());
            dto.setPerformance(perf);
        }

        if (battery.getCircularity() != null) {
            BatteryPassportDTO.CircularityDTO circ = new BatteryPassportDTO.CircularityDTO();
            circ.setComponentPartNumbers(battery.getCircularity().getComponentPartNumbers());
            circ.setPreConsumerRecycledLi(battery.getCircularity().getPreConsumerRecycledLi());
            circ.setPreConsumerRecycledCo(battery.getCircularity().getPreConsumerRecycledCo());
            circ.setPreConsumerRecycledNi(battery.getCircularity().getPreConsumerRecycledNi());
            circ.setPreConsumerRecycledPb(battery.getCircularity().getPreConsumerRecycledPb());
            circ.setPostConsumerRecycledLi(battery.getCircularity().getPostConsumerRecycledLi());
            circ.setPostConsumerRecycledCo(battery.getCircularity().getPostConsumerRecycledCo());
            circ.setPostConsumerRecycledNi(battery.getCircularity().getPostConsumerRecycledNi());
            circ.setPostConsumerRecycledPb(battery.getCircularity().getPostConsumerRecycledPb());
            
            // PDF-Namen übertragen (WICHTIG für Frontend)
            circ.setDisassemblyManualPdfName(battery.getCircularity().getDisassemblyManualPdfName());
            circ.setRemovalManualPdfName(battery.getCircularity().getRemovalManualPdfName());
            circ.setSafetyInstructionsPdfName(battery.getCircularity().getSafetyInstructionsPdfName());
            
            dto.setCircularity(circ);
        }
        
        return dto;
    }
}