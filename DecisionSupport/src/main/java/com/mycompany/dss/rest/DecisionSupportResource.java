package com.mycompany.dss.rest;

import com.mycompany.dss.dto.DecisionCriteriaDTO;
import com.mycompany.dss.dto.DecisionResultDTO;
import com.mycompany.dss.service.DecisionSupportService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.logging.Logger;


@Path("/decision-support")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DecisionSupportResource {

    private static final Logger LOGGER = Logger.getLogger(DecisionSupportResource.class.getName());

    @Inject
    private DecisionSupportService decisionSupportService;

    // Berechnet eine Entscheidungsempfehlung

    @POST
    @Path("/calculate")
    public Response calculateDecision(DecisionCriteriaDTO criteria) {
        try {
            LOGGER.info("Received decision support calculation request");

            if (criteria == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("No criteria provided"))
                        .build();
            }

            DecisionResultDTO result = decisionSupportService.calculateDecision(criteria);

            return Response.ok(result).build();

        } catch (IllegalArgumentException e) {
            LOGGER.warning("Validation error: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (Exception e) {
            LOGGER.severe("Error calculating decision: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to calculate decision: " + e.getMessage()))
                    .build();
        }
    }

    //Health Check für Decision Support

    @GET
    @Path("/health")
    public Response healthCheck() {
        return Response.ok(new HealthResponse("OK", "Decision Support Service is running"))
                .build();
    }

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

        public HealthResponse() {}

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
