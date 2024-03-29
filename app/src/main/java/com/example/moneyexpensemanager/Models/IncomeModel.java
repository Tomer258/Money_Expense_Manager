package com.example.moneyexpensemanager.Models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class IncomeModel {
    private int incomeAmount=0;
    private String date="",type="",category="",description="";

    public IncomeModel(int incomeAmount, String type, String category, String description) {
        this.incomeAmount = incomeAmount;
        this.date=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        this.type = type;
        this.category = category;
        this.description = description;
    }


    public IncomeModel(int incomeAmount, String type, String category, String description,String date) {
        this.incomeAmount = incomeAmount;
        this.date=date;
        this.type = type;
        this.category = category;
        this.description = description;
    }

    public IncomeModel() {
    }

    public void copyOf(IncomeModel other)
    {
        this.incomeAmount = other.incomeAmount;
        this.date=other.date;
        this.type = other.type;
        this.category = other.category;
        this.description = other.description;
    }

    public int getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(int incomeAmount) {
        this.incomeAmount = incomeAmount;
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

    @Override
    public String toString() {
        return "IncomeModel{" +
                "incomeAmount=" + incomeAmount +
                ", date='" + date + '\'' +
                ", type='" + type + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
