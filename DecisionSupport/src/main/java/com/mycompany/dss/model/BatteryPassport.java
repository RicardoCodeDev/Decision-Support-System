package com.mycompany.dss.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "battery_passport")
@NamedQueries({
    @NamedQuery(
            name = "BatteryPassport.findAll",
            query = "SELECT b FROM BatteryPassport b ORDER BY b.createdAt DESC"
    ),
    @NamedQuery(
            name = "BatteryPassport.findByStatus",
            query = "SELECT b FROM BatteryPassport b WHERE b.batteryStatus = :status"
    ),
    @NamedQuery(
            name = "BatteryPassport.countByStatus",
            query = "SELECT COUNT(b) FROM BatteryPassport b WHERE b.batteryStatus = :status"
    )
})
public class BatteryPassport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotBlank(message = "Battery identification is required")
    @Column(name = "battery_identification", unique = true, nullable = false, length = 255)
    private String batteryIdentification;

    @Column(name = "battery_category", length = 100)
    private String batteryCategory;

    @Column(name = "manufacturer_identification", length = 255)
    private String manufacturerIdentification;

    @Column(name = "manufacturer_date")
    private LocalDate manufacturerDate;

    @Column(name = "battery_status", length = 50)
    private String batteryStatus;

    @OneToOne(mappedBy = "batteryPassport", cascade = CascadeType.ALL, orphanRemoval = true)
    private BatteryMaterials materials;

    @OneToOne(mappedBy = "batteryPassport", cascade = CascadeType.ALL, orphanRemoval = true)
    private CircularityData circularity;

    @OneToOne(mappedBy = "batteryPassport", cascade = CascadeType.ALL, orphanRemoval = true)
    private PerformanceData performance;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public BatteryPassport() {
    }

    public BatteryPassport(String batteryIdentification) {
        this.batteryIdentification = batteryIdentification;
    }

    public void setMaterials(BatteryMaterials materials) {
        if (materials == null) {
            if (this.materials != null) {
                this.materials.setBatteryPassport(null);
            }
        } else {
            materials.setBatteryPassport(this);
        }
        this.materials = materials;
    }

    public void setCircularity(CircularityData circularity) {
        if (circularity == null) {
            if (this.circularity != null) {
                this.circularity.setBatteryPassport(null);
            }
        } else {
            circularity.setBatteryPassport(this);
        }
        this.circularity = circularity;
    }

    public void setPerformance(PerformanceData performance) {
        if (performance == null) {
            if (this.performance != null) {
                this.performance.setBatteryPassport(null);
            }
        } else {
            performance.setBatteryPassport(this);
        }
        this.performance = performance;
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

    public BatteryMaterials getMaterials() {
        return materials;
    }

    public CircularityData getCircularity() {
        return circularity;
    }

    public PerformanceData getPerformance() {
        return performance;
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

    @Override
    public String toString() {
        return "BatteryPassport{"
                + "id=" + id
                + ", batteryIdentification='" + batteryIdentification + '\''
                + ", batteryStatus='" + batteryStatus + '\''
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BatteryPassport)) {
            return false;
        }
        BatteryPassport that = (BatteryPassport) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
