package com.mycompany.dss.dto;

import java.util.Map;

public class DecisionResultDTO {

    private boolean recommendation;
    private double overallScore;

    private Map<String, CategoryResultDTO> categories;

    public DecisionResultDTO() {}

    public DecisionResultDTO(boolean recommendation, double overallScore,
                             Map<String, CategoryResultDTO> categories) {
        this.recommendation = recommendation;
        this.overallScore = overallScore;
        this.categories = categories;
    }

    public boolean isRecommendation() {
        return recommendation;
    }

    public void setRecommendation(boolean recommendation) {
        this.recommendation = recommendation;
    }

    public double getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(double overallScore) {
        this.overallScore = overallScore;
    }

    public Map<String, CategoryResultDTO> getCategories() {
        return categories;
    }

    public void setCategories(Map<String, CategoryResultDTO> categories) {
        this.categories = categories;
    }
}
