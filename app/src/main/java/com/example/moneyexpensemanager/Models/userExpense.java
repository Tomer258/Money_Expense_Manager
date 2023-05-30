package com.example.moneyexpensemanager.Models;



import java.util.ArrayList;

public class userExpense {
    private String uID;
    private ArrayList<IncomeModel> incomeList=null;
    private ArrayList<OutcomeModel> outcomeList=null;
    private int sumOfIncome=0,sumOfOutcome=0;

    public userExpense(String uID) {
        this.uID=uID;
        incomeList=new ArrayList<>();
        outcomeList=new ArrayList<>();
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
}
