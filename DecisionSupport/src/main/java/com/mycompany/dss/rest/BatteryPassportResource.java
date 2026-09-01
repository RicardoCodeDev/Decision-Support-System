package com.mycompany.dss.rest;

import com.mycompany.dss.dao.BatteryPassportDAO;
import com.mycompany.dss.dto.*;
import com.mycompany.dss.model.BatteryPassport;
import com.mycompany.dss.service.BatteryPassportService;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;
import java.util.logging.Logger;

//REST API Endpoint für Battery Passport Operations.

@Path("/batteries")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BatteryPassportResource {

    private static final Logger LOGGER = Logger.getLogger(BatteryPassportResource.class.getName());

    @Inject
    private BatteryPassportService service;
    
    @EJB
    private BatteryPassportDAO dao;

    // ========== CRUD Endpoints ==========
    
    @POST
    public Response createBattery(BatteryPassportDTO dto) {
        try {
            LOGGER.info(() -> "Creating new battery: " + dto.getBatteryIdentification());
            BatteryPassportDTO created = service.createBattery(dto);
            return Response.status(Response.Status.CREATED)
                    .entity(created)
                    .build();
        } catch (IllegalArgumentException e) {
            LOGGER.warning(() -> "Validation error: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (Exception e) {
            LOGGER.severe(() -> "Error creating battery: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to create battery: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    public Response getAllBatteries() {
        try {
            LOGGER.info("Retrieving all batteries");
            List<BatteryPassportDTO> batteries = service.getAllBatteries();
            return Response.ok(batteries).build();
        } catch (Exception e) {
            LOGGER.severe(() -> "Error retrieving batteries: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to retrieve batteries"))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getBatteryById(@PathParam("id") Long id) {
        try {
            LOGGER.info(() -> "Retrieving battery with ID: " + id);
            return service.getBatteryById(id)
                    .map(dto -> Response.ok(dto).build())
                    .orElse(Response.status(Response.Status.NOT_FOUND)
                            .entity(new ErrorResponse("Battery not found with ID: " + id))
                            .build());
        } catch (Exception e) {
            LOGGER.severe(() -> "Error retrieving battery: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to retrieve battery"))
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateBattery(@PathParam("id") Long id, BatteryPassportDTO dto) {
        try {
            LOGGER.info(() -> "Updating battery with ID: " + id);
            BatteryPassportDTO updated = service.updateBattery(id, dto);
            return Response.ok(updated).build();
        } catch (IllegalArgumentException e) {
            LOGGER.warning(() -> "Validation error: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (Exception e) {
            LOGGER.severe(() -> "Error updating battery: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to update battery"))
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBattery(@PathParam("id") Long id) {
        try {
            LOGGER.info(() -> "Deleting battery with ID: " + id);
            service.deleteBattery(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            LOGGER.warning(() -> "Validation error: " + e.getMessage());
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (Exception e) {
            LOGGER.severe(() -> "Error deleting battery: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to delete battery"))
                    .build();
        }
    }

    // ========== Such-Endpoint ==========
    
    @POST
    @Path("/search")
    public Response searchBatteries(SearchCriteriaDTO criteria) {
        try {
            LOGGER.info("Searching batteries with criteria");
            List<BatteryPassportDTO> results = service.searchBatteries(criteria);
            return Response.ok(results).build();
        } catch (Exception e) {
            LOGGER.severe(() -> "Error searching batteries: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to search batteries"))
                    .build();
        }
    }

    // ========== Dashboard-Endpoint ==========
    
    @GET
    @Path("/dashboard/statistics")
    public Response getDashboardStatistics() {
        try {
            LOGGER.info("Retrieving dashboard statistics");
            DashboardStatisticsDTO stats = service.getDashboardStatistics();
            return Response.ok(stats).build();
        } catch (Exception e) {
            LOGGER.severe(() -> "Error retrieving dashboard statistics: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to retrieve dashboard statistics"))
                    .build();
        }
    }

    // ========== PDF Download Endpoint ==========
    
    @GET
    @Path("/{id}/download/{documentType}")
    @Produces("application/pdf")
    public Response downloadDocument(
            @PathParam("id") Long id,
            @PathParam("documentType") String documentType) {

        try {
            LOGGER.info("Downloading document: " + documentType + " for battery ID: " + id);

            BatteryPassport battery = dao.findByIdWithDetails(id)
                    .orElseThrow(() -> new NotFoundException("Battery not found with ID: " + id));

            byte[] pdfData = null;
            String fileName = null;

            // Je nach Document-Typ die entsprechenden Daten holen
            switch (documentType) {
                case "critical-raw-materials":
                    if (battery.getMaterials() != null) {
                        pdfData = battery.getMaterials().getCriticalRawMaterialsPdf();
                        fileName = battery.getMaterials().getCriticalRawMaterialsPdfName();
                    }
                    break;

                case "hazardous-substances":
                    if (battery.getMaterials() != null) {
                        pdfData = battery.getMaterials().getHazardousSubstancesPdf();
                        fileName = battery.getMaterials().getHazardousSubstancesPdfName();
                    }
                    break;

                case "disassembly-manual":
                    if (battery.getCircularity() != null) {
                        pdfData = battery.getCircularity().getDisassemblyManualPdf();
                        fileName = battery.getCircularity().getDisassemblyManualPdfName();
                    }
                    break;

                case "removal-manual":
                    if (battery.getCircularity() != null) {
                        pdfData = battery.getCircularity().getRemovalManualPdf();
                        fileName = battery.getCircularity().getRemovalManualPdfName();
                    }
                    break;

                case "safety-instructions":
                    if (battery.getCircularity() != null) {
                        pdfData = battery.getCircularity().getSafetyInstructionsPdf();
                        fileName = battery.getCircularity().getSafetyInstructionsPdfName();
                    }
                    break;

                default:
                    LOGGER.warning("Unknown document type: " + documentType);
                    throw new NotFoundException("Document type not found: " + documentType);
            }

            // Prüfen ob PDF vorhanden ist
            if (pdfData == null || pdfData.length == 0) {
                LOGGER.warning("No PDF data found for type: " + documentType);
                throw new NotFoundException("Document not available");
            }

            // Default Filename falls keiner gespeichert wurde
            if (fileName == null || fileName.isEmpty()) {
                fileName = documentType + ".pdf";
            }

            LOGGER.info("Sending PDF: " + fileName + " (" + pdfData.length + " bytes)");

            return Response.ok(pdfData)
                    .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                    .header("Content-Type", "application/pdf")
                    .header("Content-Length", pdfData.length)
                    .build();

        } catch (NotFoundException e) {
            LOGGER.warning("Not found: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            LOGGER.severe("Error downloading document: " + e.getMessage());
            e.printStackTrace();
            throw new WebApplicationException("Error downloading document: " + e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    // ========== Health Check ==========
    
    @GET
    @Path("/health")
    public Response healthCheck() {
        return Response.ok(new HealthResponse("OK", "Battery Passport DSS is running"))
                .build();
    }

    // ========== Response Classes ==========
    
    public static class ErrorResponse {
        private String message;
        private long timestamp;

        public ErrorResponse() {
            this.timestamp = System.currentTimeMillis();
        }

        public ErrorResponse(String message) {
            this.message = message;
            this.timestamp = System.currentTimeMillis();
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }
    }

    public static class HealthResponse {
        private String status;
        private String message;

        public HealthResponse() {
        }

        public HealthResponse(String status, String message) {
            this.status = status;
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}