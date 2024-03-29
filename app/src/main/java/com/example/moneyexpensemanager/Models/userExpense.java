package com.example.moneyexpensemanager.Models;



import java.util.ArrayList;

public class userExpense {
    String familyCode="";

    String name="";

    String uID="";

    private ArrayList<IncomeModel> incomeList;
    private ArrayList<OutcomeModel> outcomeList;
    private int sumOfIncome=0,sumOfOutcome=0;

    public userExpense() {
        incomeList=new ArrayList<>();
        outcomeList= new ArrayList<>();
    }

    public void addIncome(IncomeModel incomeModel)
    {
        incomeList.add(incomeModel);
        this.sumOfIncome+=incomeModel.getIncomeAmount();
    }

    public void removeIncome(IncomeModel incomeModel)
    {
       if(incomeList.remove(incomeModel))
           this.sumOfIncome-=incomeModel.getIncomeAmount();
    }

    public void addOutcome(OutcomeModel outcomeModel)
    {
        outcomeList.add(outcomeModel);
        this.sumOfOutcome+=outcomeModel.getOutcomeAmount();
    }

    public void removeOutcome(OutcomeModel outcomeModel)
    {
        if(outcomeList.remove(outcomeModel))
            this.sumOfOutcome-=outcomeModel.getOutcomeAmount();
    }

    public void clearIncome()
    {
        incomeList.clear();
        this.sumOfIncome=0;
    }

    public void clearOutcome()
    {
        outcomeList.clear();
        this.sumOfOutcome=0;
    }

    public ArrayList<IncomeModel> getIncomeList() {
        return incomeList;
    }

    public IncomeModel getIncomeModelAt(int i)
    {
        return  incomeList.get(i);
    }

    public userExpense setIncomeList(ArrayList<IncomeModel> incomeList) {
        this.incomeList = incomeList;
        return this;
    }

    public ArrayList<OutcomeModel> getOutcomeList() {
        return outcomeList;
    }

    public userExpense setOutcomeList(ArrayList<OutcomeModel> outcomeList) {
        this.outcomeList = outcomeList;
        return this;
    }

    public int getSumOfIncome() {
        return sumOfIncome;
    }

    public userExpense setSumOfIncome(int sumOfIncome) {
        this.sumOfIncome = sumOfIncome;
        return this;
    }

    public int getSumOfOutcome() {
        return sumOfOutcome;
    }

    public userExpense setSumOfOutcome(int sumOfOutcome) {
        this.sumOfOutcome = sumOfOutcome;
        return this;
    }

    public String getFamilyCode() {
        return familyCode;
    }

    public userExpense setFamilyCode(String familyCode) {
        this.familyCode = familyCode;
        return this;
    }

    public String getName() {
        return name;
    }

    public userExpense setName(String name) {
        this.name = name;
        return this;
    }

    public String getuID() {
        return uID;
    }

    public userExpense setuID(String uID) {
        this.uID = uID;
        return this;
    }

    @Override
    public String toString() {
        return "userExpense{" +
                "incomeList=" + incomeList +
                ", outcomeList=" + outcomeList +
                ", sumOfIncome=" + sumOfIncome +
                ", sumOfOutcome=" + sumOfOutcome +
                '}';
    }
}
