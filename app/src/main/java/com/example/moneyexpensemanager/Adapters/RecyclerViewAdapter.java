package com.example.moneyexpensemanager.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneyexpensemanager.Models.IncomeModel;
import com.example.moneyexpensemanager.Models.OutcomeModel;
import com.example.moneyexpensemanager.R;


import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private ArrayList<IncomeModel> income;
     private ArrayList<OutcomeModel> outcome;
    private int type;

    //type:  0 - income , 1 - outcome,
    public RecyclerViewAdapter(ArrayList<IncomeModel> incomeModel, ArrayList<OutcomeModel> outcomeModel, int type)
    {
        this.type=type;
        this.income=incomeModel;
        this.outcome=outcomeModel;

    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rv_item,parent,false);
        return new RecyclerViewAdapter.MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.amountView.setText(type==0?income.get(position).getIncomeAmount()+"":outcome.get(position).getOutcomeAmount()+"");
        holder.descriptionView.setText(type==0?income.get(position).getDescription():outcome.get(position).getDescription());
        holder.typeView.setText(type==0?income.get(position).getType():outcome.get(position).getType());
        holder.categoryView.setText(type==0?income.get(position).getCategory():outcome.get(position).getCategory());
        holder.dateView.setText(type==0?income.get(position).getDate():outcome.get(position).getDate());
        holder.posView.setText((position+1)+"");

    }

    @Override
    public int getItemCount() {
        return type==0?this.income==null ? 0:this.income.size():this.outcome==null ? 0:this.outcome.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView amountView,descriptionView,typeView,categoryView,posView,dateView;
       // CardView mCardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            amountView=itemView.findViewById(R.id.income_amount_TXT);
            descriptionView=itemView.findViewById(R.id.income_description);
            typeView=itemView.findViewById(R.id.rv_type);
            categoryView=itemView.findViewById(R.id.rv_category_TXT);
            posView=itemView.findViewById(R.id.pos);
            dateView=itemView.findViewById(R.id.date);
            //mCardView=itemView.findViewById(R.id.mCardView);
        }
    }
}
