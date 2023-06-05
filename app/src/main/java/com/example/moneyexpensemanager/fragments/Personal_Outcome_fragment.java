package com.example.moneyexpensemanager.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moneyexpensemanager.Adapters.RecyclerViewAdapter;
import com.example.moneyexpensemanager.Models.IncomeModel;
import com.example.moneyexpensemanager.Models.OutcomeModel;
import com.example.moneyexpensemanager.Models.userExpense;
import com.example.moneyexpensemanager.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Personal_Outcome_fragment extends Fragment {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    ArrayList<OutcomeModel> outcome;
    userExpense userExpenseInstance = new userExpense();
    TextView noListFound;

    RecyclerView recyclerView;

    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_personal__outcome_fragment, container, false);
        initViews(view);
        processDataBase();
        return view;
    }

    private void processDataBase()
    {
        DatabaseReference users= FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid());
        users.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                userExpense value = dataSnapshot.getValue(userExpense.class);
                if(value!=null)
                {
                    userExpenseInstance=value;
                    updateRV();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error){}
        });
    }

    private void updateRV()
    {
        outcome=userExpenseInstance.getOutcomeList();

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(null, outcome, 1);
        recyclerView.setAdapter(recyclerViewAdapter);

        if (outcome != null) {

            noListFound.setVisibility(View.GONE);
        }
    }

    private void initViews(View view)
    {
        noListFound = view.findViewById(R.id.noOutcomeFound_TXT);
        recyclerView = view.findViewById(R.id.mOutcomeRecyclerView);
        context=view.getContext();
    }

}