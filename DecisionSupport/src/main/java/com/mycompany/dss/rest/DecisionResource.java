package com.mycompany.dss.rest;

import com.mycompany.dss.dao.BatteryPassportDAO;
import com.mycompany.dss.dto.DecisionCriteriaDTO;
import com.mycompany.dss.dto.DecisionResultDTO;
import com.mycompany.dss.model.BatteryPassport;
import com.mycompany.dss.model.DecisionData;
import com.mycompany.dss.service.DecisionSupportService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/decision")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DecisionResource {

    @Inject
    private DecisionSupportService decisionService;

    @Inject
    private BatteryPassportDAO batteryDAO;

    // Berechnet eine Entscheidung basierend auf Kriterien.

    @POST
    @Path("/calculate")
    public Response calculateDecision(DecisionCriteriaDTO criteria) {
        try {
            DecisionResultDTO result = decisionService.calculateDecision(criteria);
            return Response.ok(result).build();
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Fehler bei der Berechnung: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(error).build();
        }
    }

    //Lädt alle Batterien für die Auswahl.
    @GET
    @Path("/batteries")
    public Response getAllBatteries() {
        try {
            List<BatteryPassport> batteries = batteryDAO.findAll();
            
            // Nur relevante Felder zurückgeben
            List<Map<String, Object>> simplifiedList = batteries.stream()
                    .map(b -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", b.getId());
                        map.put("batteryIdentification", b.getBatteryIdentification());
                        map.put("manufacturerIdentification", b.getManufacturerIdentification());
                        map.put("batteryStatus", b.getBatteryStatus());
                        return map;
                    })
                    .collect(Collectors.toList());
            
            return Response.ok(simplifiedList).build();
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Fehler beim Laden der Batterien: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(error).build();
        }
    }

    //Lädt DecisionData für eine bestimmte Batterie.

    @GET
    @Path("/battery/{batteryId}")
    public Response getDecisionDataForBattery(@PathParam("batteryId") Long batteryId) {
        try {
            Optional<DecisionData> decisionData = batteryDAO.findDecisionDataByBatteryId(batteryId);
            
            if (decisionData.isPresent()) {
                // Konvertiere zu DTO
                DecisionData data = decisionData.get();
                Map<String, Object> response = new HashMap<>();
                response.put("soh", data.getSoh());
                response.put("remainingCharge", data.getRemainingCharge());
                response.put("internalResistance", data.getInternalResistance());
                response.put("safetyDamage", data.getSafetyDamage());
                response.put("bmsCondition", data.getBmsCondition());
                response.put("costRefurbishment", data.getCostRefurbishment());
                response.put("marketDemand", data.getMarketDemand());
                response.put("environmentalBenefit", data.getEnvironmentalBenefit());
                response.put("logisticsFootprint", data.getLogisticsFootprint());
                response.put("hazardClassification", data.getHazardClassification());
                response.put("producerResponsibility", data.getProducerResponsibility());
                response.put("powerRequirements", data.getPowerRequirements());
                response.put("ageChemistry", data.getAgeChemistry());
                response.put("recommendation", data.getRecommendation());
                response.put("overallScore", data.getOverallScore());
                
                return Response.ok(response).build();
            } else {
                // Keine vorhandenen Daten
                return Response.ok(new HashMap<>()).build();
            }
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Fehler beim Laden der Decision Data: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(error).build();
        }
    }

    //Speichert DecisionData für eine Batterie.
    @POST
    @Path("/battery/{batteryId}/save")
    public Response saveDecisionData(
            @PathParam("batteryId") Long batteryId,
            Map<String, Object> criteriaData) {
        try {
            // Finde Batterie
            Optional<BatteryPassport> battery = batteryDAO.findById(batteryId);
            if (!battery.isPresent()) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Batterie nicht gefunden");
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(error).build();
            }

            // Finde oder erstelle DecisionData
            Optional<DecisionData> existingData = batteryDAO.findDecisionDataByBatteryId(batteryId);
            DecisionData decisionData;
            
            if (existingData.isPresent()) {
                decisionData = existingData.get();
            } else {
                decisionData = new DecisionData(battery.get());
            }

            // Setze alle Kriterien-Werte
            if (criteriaData.containsKey("soh")) {
                decisionData.setSoh(getIntValue(criteriaData, "soh"));
            }
            if (criteriaData.containsKey("remainingCharge")) {
                decisionData.setRemainingCharge(getIntValue(criteriaData, "remainingCharge"));
            }
            if (criteriaData.containsKey("internalResistance")) {
                decisionData.setInternalResistance(getIntValue(criteriaData, "internalResistance"));
            }
            if (criteriaData.containsKey("safetyDamage")) {
                decisionData.setSafetyDamage(getIntValue(criteriaData, "safetyDamage"));
            }
            if (criteriaData.containsKey("bmsCondition")) {
                decisionData.setBmsCondition(getIntValue(criteriaData, "bmsCondition"));
            }
            if (criteriaData.containsKey("costRefurbishment")) {
                decisionData.setCostRefurbishment(getIntValue(criteriaData, "costRefurbishment"));
            }
            if (criteriaData.containsKey("marketDemand")) {
                decisionData.setMarketDemand(getIntValue(criteriaData, "marketDemand"));
            }
            if (criteriaData.containsKey("environmentalBenefit")) {
                decisionData.setEnvironmentalBenefit(getIntValue(criteriaData, "environmentalBenefit"));
            }
            if (criteriaData.containsKey("logisticsFootprint")) {
                decisionData.setLogisticsFootprint(getIntValue(criteriaData, "logisticsFootprint"));
            }
            if (criteriaData.containsKey("hazardClassification")) {
                decisionData.setHazardClassification(getIntValue(criteriaData, "hazardClassification"));
            }
            if (criteriaData.containsKey("producerResponsibility")) {
                decisionData.setProducerResponsibility(getIntValue(criteriaData, "producerResponsibility"));
            }
            if (criteriaData.containsKey("powerRequirements")) {
                decisionData.setPowerRequirements(getIntValue(criteriaData, "powerRequirements"));
            }
            if (criteriaData.containsKey("ageChemistry")) {
                decisionData.setAgeChemistry(getIntValue(criteriaData, "ageChemistry"));
            }
            if (criteriaData.containsKey("recommendation")) {
                decisionData.setRecommendation((Boolean) criteriaData.get("recommendation"));
            }
            if (criteriaData.containsKey("overallScore")) {
                Object score = criteriaData.get("overallScore");
                decisionData.setOverallScore(score instanceof Number ? 
                    ((Number) score).doubleValue() : null);
            }

            // Speichere in Datenbank
            DecisionData saved = batteryDAO.saveDecisionData(decisionData);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Decision Data erfolgreich gespeichert");
            response.put("id", saved.getId());

            return Response.ok(response).build();
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Fehler beim Speichern: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(error).build();
        }
    }

    private Integer getIntValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) return null;
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return null;
    }
}