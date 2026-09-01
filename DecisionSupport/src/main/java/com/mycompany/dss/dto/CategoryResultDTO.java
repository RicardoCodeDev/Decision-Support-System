package com.mycompany.dss.dto;

public class CategoryResultDTO {

    private double weighted;
    private int count;

    private int mandatory;
    private int mandatoryPassed;

    private int optional;
    private int optionalFailed;

    public CategoryResultDTO() {
    }

    public CategoryResultDTO(double weighted, int count,
            int mandatory, int mandatoryPassed,
            int optional, int optionalFailed) {
        this.weighted = weighted;
        this.count = count;
        this.mandatory = mandatory;
        this.mandatoryPassed = mandatoryPassed;
        this.optional = optional;
        this.optionalFailed = optionalFailed;
    }

    public double getWeighted() {
        return weighted;
    }

    public void setWeighted(double weighted) {
        this.weighted = weighted;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getMandatory() {
        return mandatory;
    }

    public void setMandatory(int mandatory) {
        this.mandatory = mandatory;
    }

    public int getMandatoryPassed() {
        return mandatoryPassed;
    }

    public void setMandatoryPassed(int mandatoryPassed) {
        this.mandatoryPassed = mandatoryPassed;
    }

    public int getOptional() {
        return optional;
    }

    public void setOptional(int optional) {
        this.optional = optional;
    }

    public int getOptionalFailed() {
        return optionalFailed;
    }

    public void setOptionalFailed(int optionalFailed) {
        this.optionalFailed = optionalFailed;
    }
}
