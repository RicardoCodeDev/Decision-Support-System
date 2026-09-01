package com.mycompany.dss.model;

import jakarta.persistence.*;

@Entity
@Table(name = "circularity_data")
public class CircularityData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "battery_passport_id", unique = true)
    private BatteryPassport batteryPassport;

    @Column(name = "component_part_numbers", length = 255)
    private String componentPartNumbers;

    // Recycling-Anteile vor Verbrauch
    @Column(name = "pre_consumer_recycled_li")
    private Double preConsumerRecycledLi;

    @Column(name = "pre_consumer_recycled_co")
    private Double preConsumerRecycledCo;

    @Column(name = "pre_consumer_recycled_ni")
    private Double preConsumerRecycledNi;

    @Column(name = "pre_consumer_recycled_pb")
    private Double preConsumerRecycledPb;

    // Recycling-Anteile nach Verbrauch
    @Column(name = "post_consumer_recycled_li")
    private Double postConsumerRecycledLi;

    @Column(name = "post_consumer_recycled_co")
    private Double postConsumerRecycledCo;

    @Column(name = "post_consumer_recycled_ni")
    private Double postConsumerRecycledNi;

    @Column(name = "post_consumer_recycled_pb")
    private Double postConsumerRecycledPb;

    // PDF-Felder für Disassembly Manual
    @Lob
    @Column(name = "disassembly_manual_pdf")
    private byte[] disassemblyManualPdf;

    @Column(name = "disassembly_manual_pdf_name")
    private String disassemblyManualPdfName;

    // PDF-Felder für Removal Manual
    @Lob
    @Column(name = "removal_manual_pdf")
    private byte[] removalManualPdf;

    @Column(name = "removal_manual_pdf_name")
    private String removalManualPdfName;

    // PDF-Felder für Safety Instructions
    @Lob
    @Column(name = "safety_instructions_pdf")
    private byte[] safetyInstructionsPdf;

    @Column(name = "safety_instructions_pdf_name")
    private String safetyInstructionsPdfName;

    public CircularityData() {
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

    public String getComponentPartNumbers() {
        return componentPartNumbers;
    }

    public void setComponentPartNumbers(String componentPartNumbers) {
        this.componentPartNumbers = componentPartNumbers;
    }

    public Double getPreConsumerRecycledLi() {
        return preConsumerRecycledLi;
    }

    public void setPreConsumerRecycledLi(Double preConsumerRecycledLi) {
        this.preConsumerRecycledLi = preConsumerRecycledLi;
    }

    public Double getPreConsumerRecycledCo() {
        return preConsumerRecycledCo;
    }

    public void setPreConsumerRecycledCo(Double preConsumerRecycledCo) {
        this.preConsumerRecycledCo = preConsumerRecycledCo;
    }

    public Double getPreConsumerRecycledNi() {
        return preConsumerRecycledNi;
    }

    public void setPreConsumerRecycledNi(Double preConsumerRecycledNi) {
        this.preConsumerRecycledNi = preConsumerRecycledNi;
    }

    public Double getPreConsumerRecycledPb() {
        return preConsumerRecycledPb;
    }

    public void setPreConsumerRecycledPb(Double preConsumerRecycledPb) {
        this.preConsumerRecycledPb = preConsumerRecycledPb;
    }

    public Double getPostConsumerRecycledLi() {
        return postConsumerRecycledLi;
    }

    public void setPostConsumerRecycledLi(Double postConsumerRecycledLi) {
        this.postConsumerRecycledLi = postConsumerRecycledLi;
    }

    public Double getPostConsumerRecycledCo() {
        return postConsumerRecycledCo;
    }

    public void setPostConsumerRecycledCo(Double postConsumerRecycledCo) {
        this.postConsumerRecycledCo = postConsumerRecycledCo;
    }

    public Double getPostConsumerRecycledNi() {
        return postConsumerRecycledNi;
    }

    public void setPostConsumerRecycledNi(Double postConsumerRecycledNi) {
        this.postConsumerRecycledNi = postConsumerRecycledNi;
    }

    public Double getPostConsumerRecycledPb() {
        return postConsumerRecycledPb;
    }

    public void setPostConsumerRecycledPb(Double postConsumerRecycledPb) {
        this.postConsumerRecycledPb = postConsumerRecycledPb;
    }

    public byte[] getDisassemblyManualPdf() {
        return disassemblyManualPdf;
    }

    public void setDisassemblyManualPdf(byte[] disassemblyManualPdf) {
        this.disassemblyManualPdf = disassemblyManualPdf;
    }

    public String getDisassemblyManualPdfName() {
        return disassemblyManualPdfName;
    }

    public void setDisassemblyManualPdfName(String disassemblyManualPdfName) {
        this.disassemblyManualPdfName = disassemblyManualPdfName;
    }

    public byte[] getRemovalManualPdf() {
        return removalManualPdf;
    }

    public void setRemovalManualPdf(byte[] removalManualPdf) {
        this.removalManualPdf = removalManualPdf;
    }

    public String getRemovalManualPdfName() {
        return removalManualPdfName;
    }

    public void setRemovalManualPdfName(String removalManualPdfName) {
        this.removalManualPdfName = removalManualPdfName;
    }

    public byte[] getSafetyInstructionsPdf() {
        return safetyInstructionsPdf;
    }

    public void setSafetyInstructionsPdf(byte[] safetyInstructionsPdf) {
        this.safetyInstructionsPdf = safetyInstructionsPdf;
    }

    public String getSafetyInstructionsPdfName() {
        return safetyInstructionsPdfName;
    }

    public void setSafetyInstructionsPdfName(String safetyInstructionsPdfName) {
        this.safetyInstructionsPdfName = safetyInstructionsPdfName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CircularityData)) {
            return false;
        }
        CircularityData that = (CircularityData) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}