package com.mycompany.dss.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import com.mycompany.dss.model.BatteryPassport;
import com.mycompany.dss.model.DecisionData;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Logger;

@Stateless
public class BatteryPassportDAO {

    private static final Logger LOGGER = Logger.getLogger(BatteryPassportDAO.class.getName());

    @PersistenceContext(unitName = "BatteryPassportPU")
    private EntityManager em;

    public BatteryPassport create(BatteryPassport battery) {
        try {
            em.persist(battery);
            em.flush();
            LOGGER.info(() -> "Battery created with ID: " + battery.getId());
            return battery;
        } catch (Exception e) {
            LOGGER.severe(() -> "Error creating battery: " + e.getMessage());
            throw new RuntimeException("Failed to create battery", e);
        }
    }

    // Sucht eine Batterie anhand der Battery-Identification-Nummer
    public Optional<BatteryPassport> findByBatteryIdentification(String batteryId) {
        try {
            TypedQuery<BatteryPassport> query = em.createQuery(
                    "SELECT b FROM BatteryPassport b WHERE b.batteryIdentification = :batteryId",
                    BatteryPassport.class
            );
            query.setParameter("batteryId", batteryId);
            return Optional.ofNullable(query.getSingleResult());
        } catch (Exception e) {
            // Wenn kein Ergebnis oder Fehler: leeres Optional zurückgeben
            return Optional.empty();
        }
    }

    //Gibt alle Batterien zurück, sortiert nach Erstellungsdatum (neueste als erstes)
    public List<BatteryPassport> findAll() {
        TypedQuery<BatteryPassport> query = em.createQuery(
                "SELECT b FROM BatteryPassport b ORDER BY b.createdAt DESC",
                BatteryPassport.class
        );
        return query.getResultList();
    }


    public Optional<BatteryPassport> findById(Long id) {
        try {
            BatteryPassport battery = em.find(BatteryPassport.class, id);
            return Optional.ofNullable(battery);
        } catch (Exception e) {
            LOGGER.severe(() -> "Error finding battery: " + e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<BatteryPassport> findByIdWithDetails(Long id) {
        try {
            TypedQuery<BatteryPassport> query = em.createQuery(
                    "SELECT b FROM BatteryPassport b "
                    + "LEFT JOIN FETCH b.materials "
                    + "LEFT JOIN FETCH b.performance "
                    + "LEFT JOIN FETCH b.circularity "
                    + "WHERE b.id = :id",
                    BatteryPassport.class
            );
            query.setParameter("id", id);

            List<BatteryPassport> results = query.getResultList();
            return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
        } catch (Exception e) {
            LOGGER.severe(() -> "Error finding battery with details: " + e.getMessage());
            return Optional.empty();
        }
    }

    public BatteryPassport update(BatteryPassport battery) {
        return em.merge(battery);
    }

    @Transactional
    public void deleteBattery(Long id) {
        BatteryPassport batteryPassport = em.find(BatteryPassport.class, id);
        if (batteryPassport == null) {
            throw new IllegalArgumentException("Battery not found with ID: " + id);
        }

        try {
            int deletedDecisions = em.createQuery(
                    "DELETE FROM DecisionData d WHERE d.batteryPassport.id = :batteryId")
                    .setParameter("batteryId", id)
                    .executeUpdate();

            if (deletedDecisions > 0) {
                LOGGER.info(() -> "DecisionData deleted for battery ID: " + id);
            }

            em.flush();
        
            em.refresh(batteryPassport);
           
            if (batteryPassport.getMaterials() != null) {
                em.remove(batteryPassport.getMaterials());
            }
            if (batteryPassport.getPerformance() != null) {
                em.remove(batteryPassport.getPerformance());
            }
            if (batteryPassport.getCircularity() != null) {
                em.remove(batteryPassport.getCircularity());
            }

            em.flush();
            em.remove(batteryPassport);
            em.flush();

            LOGGER.info(() -> "Battery successfully deleted with ID: " + id);

        } catch (Exception e) {
            LOGGER.severe(() -> "Error deleting battery ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to delete battery: " + e.getMessage(), e);
        }
    }

    //Führt eine flexible Suche durch, basierend auf optionalen Kriterien: Batterie-ID, Hersteller und Status.
    public List<BatteryPassport> search(String batteryId, String manufacturer, String status) {
        StringBuilder jpql = new StringBuilder("SELECT b FROM BatteryPassport b WHERE 1=1");

        if (batteryId != null && !batteryId.isEmpty()) {
            jpql.append(" AND b.batteryIdentification LIKE :batteryId");
        }
        if (manufacturer != null && !manufacturer.isEmpty()) {
            jpql.append(" AND b.manufacturerIdentification LIKE :manufacturer");
        }
        if (status != null && !status.isEmpty()) {
            jpql.append(" AND b.batteryStatus = :status");
        }

        TypedQuery<BatteryPassport> query = em.createQuery(jpql.toString(), BatteryPassport.class);

        // Parameter setzen
        if (batteryId != null && !batteryId.isEmpty()) {
            query.setParameter("batteryId", "%" + batteryId + "%");
        }
        if (manufacturer != null && !manufacturer.isEmpty()) {
            query.setParameter("manufacturer", "%" + manufacturer + "%");
        }
        if (status != null && !status.isEmpty()) {
            query.setParameter("status", status);
        }

        return query.getResultList();
    }

    // ========== Statistik-Funktionen ==========
    
    //Zählt alle Batterien
    public long countAll() {
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(b) FROM BatteryPassport b",
                    Long.class
            );
            return query.getSingleResult();
        } catch (Exception e) {
            LOGGER.severe(() -> "Error counting batteries: " + e.getMessage());
            return 0L;
        }
    }

    //Zählt Batterien nach Status
    public long countByStatus(String status) {
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(b) FROM BatteryPassport b WHERE b.batteryStatus = :status",
                    Long.class
            );
            query.setParameter("status", status);
            return query.getSingleResult();
        } catch (Exception e) {
            LOGGER.severe(() -> "Error counting batteries by status: " + e.getMessage());
            return 0L;
        }
    }

    //Gibt die Status-Verteilung zurück
    public Map<String, Long> getStatusDistribution() {
        try {
            TypedQuery<Object[]> query = em.createQuery(
                    "SELECT b.batteryStatus, COUNT(b) FROM BatteryPassport b "
                    + "GROUP BY b.batteryStatus",
                    Object[].class
            );

            List<Object[]> results = query.getResultList();
            Map<String, Long> distribution = new HashMap<>();

            for (Object[] result : results) {
                String status = (String) result[0];
                Long count = (Long) result[1];
                distribution.put(status != null ? status : "Unbekannt", count);
            }

            return distribution;
        } catch (Exception e) {
            LOGGER.severe(() -> "Error getting status distribution: " + e.getMessage());
            return new HashMap<>();
        }
    }

    // Gibt die Hersteller-Verteilung zurück
    public Map<String, Long> getManufacturerDistribution() {
        try {
            TypedQuery<Object[]> query = em.createQuery(
                    "SELECT b.manufacturerIdentification, COUNT(b) FROM BatteryPassport b "
                    + "GROUP BY b.manufacturerIdentification",
                    Object[].class
            );

            List<Object[]> results = query.getResultList();
            Map<String, Long> distribution = new HashMap<>();

            for (Object[] result : results) {
                String manufacturer = (String) result[0];
                Long count = (Long) result[1];
                distribution.put(manufacturer != null ? manufacturer : "Unbekannt", count);
            }

            return distribution;
        } catch (Exception e) {
            LOGGER.severe(() -> "Error getting manufacturer distribution: " + e.getMessage());
            return new HashMap<>();
        }
    }

    // Berechnet die durchschnittliche Kapazität
    public Double getAverageCapacity() {
        try {
            TypedQuery<Double> query = em.createQuery(
                    "SELECT AVG(p.ratedCapacityAh) FROM PerformanceData p "
                    + "WHERE p.ratedCapacityAh IS NOT NULL",
                    Double.class
            );
            Double result = query.getSingleResult();
            return result != null ? result : 0.0;
        } catch (Exception e) {
            LOGGER.severe(() -> "Error calculating average capacity: " + e.getMessage());
            return 0.0;
        }
    }

    //Berechnet die durchschnittliche Energie
    public Double getAverageEnergy() {
        try {
            TypedQuery<Double> query = em.createQuery(
                    "SELECT AVG(p.certifiedUsableEnergyKwh) FROM PerformanceData p "
                    + "WHERE p.certifiedUsableEnergyKwh IS NOT NULL",
                    Double.class
            );
            Double result = query.getSingleResult();
            return result != null ? result : 0.0;
        } catch (Exception e) {
            LOGGER.severe(() -> "Error calculating average energy: " + e.getMessage());
            return 0.0;
        }
    }

    //Findet DecisionData für eine bestimmte Batterie
    public Optional<DecisionData> findDecisionDataByBatteryId(Long batteryId) {
        try {
            TypedQuery<DecisionData> query = em.createQuery(
                    "SELECT d FROM DecisionData d WHERE d.batteryPassport.id = :batteryId",
                    DecisionData.class
            );
            query.setParameter("batteryId", batteryId);
            return Optional.ofNullable(query.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // Speichert oder aktualisiert DecisionData für eine Batterie.
    @Transactional
    public DecisionData saveDecisionData(DecisionData decisionData) {
        try {
            if (decisionData.getId() == null) {
                em.persist(decisionData);
                LOGGER.info("DecisionData created for battery ID: "
                        + decisionData.getBatteryPassport().getId());
            } else {
                decisionData = em.merge(decisionData);
                LOGGER.info("DecisionData updated for battery ID: "
                        + decisionData.getBatteryPassport().getId());
            }
            em.flush();
            return decisionData;
        } catch (Exception e) {
            LOGGER.severe(() -> "Error saving decision data: " + e.getMessage());
            throw new RuntimeException("Failed to save decision data", e);
        }
    }

    // Löscht DecisionData für eine bestimmte Batterie
    @Transactional
    public void deleteDecisionData(Long batteryId) {
        try {
            Optional<DecisionData> decisionData = findDecisionDataByBatteryId(batteryId);
            if (decisionData.isPresent()) {
                em.remove(decisionData.get());
                em.flush();
                LOGGER.info(() -> "DecisionData deleted for battery ID: " + batteryId);
            }
        } catch (Exception e) {
            LOGGER.severe(() -> "Error deleting decision data: " + e.getMessage());
            throw new RuntimeException("Failed to delete decision data", e);
        }
    }
}
