package com.example.moneyexpensemanager.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.moneyexpensemanager.Models.userExpense;
import com.example.moneyexpensemanager.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class Charts_fragment extends Fragment {




    PieChart pieChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_charts_fragment, container, false);


        pieChart=view.findViewById(R.id.pie1);


        initGraph();



        return view;
    }

    private void initGraph()
    {
        processDataBase();
    }

    private void processDataBase()
    {
        DatabaseReference users= FirebaseDatabase.getInstance().getReference("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()));
        users.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                userExpense value = dataSnapshot.getValue(userExpense.class);
                if(value!=null)
                {
                    String code=value.getFamilyCode();
                    DatabaseReference userFamily=FirebaseDatabase.getInstance().getReference().child("families").child(code);
                    userFamily.get().addOnCompleteListener(task -> {
                        if (task.isSuccessful())
                        {
                            DataSnapshot dataSnapshot1 = task.getResult();
                            GenericTypeIndicator<HashMap<String,userExpense>> t = new GenericTypeIndicator<HashMap<String, userExpense>>() {};
                            HashMap<String,userExpense> usersRefDB= dataSnapshot1.getValue(t);
                            if (usersRefDB!=null)
                            {
                                buildGraph(usersRefDB);
                            }
                        }
                        else
                        {
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                        }
                    });

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error){}
        });
    }

    private void buildGraph(HashMap<String, userExpense> usersRefDB) {
        List<PieEntry> data = new ArrayList<>();
        for (Map.Entry<String,userExpense> entry : usersRefDB.entrySet()) {
            data.add(new PieEntry(entry.getValue().getSumOfIncome(),entry.getValue().getName()));
        }
        PieDataSet pieDataSet=new PieDataSet(data,"PieChart");
        PieData pieData=new PieData(pieDataSet);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextSize(12f);
        pieChart.setData(pieData);
        pieChart.invalidate();

    }


}