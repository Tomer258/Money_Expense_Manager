package com.example.moneyexpensemanager.Models;

public class OutcomeModel {
    private int outcomeAmount=0;
    private String date,type,category,description;

    public OutcomeModel(int outcomeAmount, String date, String type, String category, String description) {
        this.outcomeAmount = outcomeAmount;
        this.date = date;
        this.type = type;
        this.category = category;
        this.description = description;
    }

    public int getOutcomeAmount() {
        return outcomeAmount;
    }

    public void setOutcomeAmount(int outcomeAmount) {
        this.outcomeAmount = outcomeAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}