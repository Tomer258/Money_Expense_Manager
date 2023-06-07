package com.example.moneyexpensemanager.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.example.moneyexpensemanager.Models.OutcomeModel;
import com.example.moneyexpensemanager.Models.userExpense;
import com.example.moneyexpensemanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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


public class Charts_fragment extends Fragment {

    AnyChartView anyChartView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_charts_fragment, container, false);
        initGraph(view);

        return view;
    }

    private void initGraph(View view)
    {
        Pie pie = AnyChart.pie();
        anyChartView =view.findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(view.findViewById(R.id.progress_bar));


        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(getActivity(), event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });

        pie.title("Family member's income");

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Retail channels")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);



        processDataBase(pie);


    }

    private void processDataBase(Pie pie)
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
                    String code=value.getFamilyCode();
                    DatabaseReference userFamily=FirebaseDatabase.getInstance().getReference().child("families").child(code);
                    userFamily.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful())
                            {
                                DataSnapshot dataSnapshot = task.getResult();
                                GenericTypeIndicator<HashMap<String,userExpense>> t = new GenericTypeIndicator<HashMap<String, userExpense>>() {};
                                HashMap<String,userExpense> usersRefDB=dataSnapshot.getValue(t);
                                if (usersRefDB!=null)
                                {
                                    buildGraph(usersRefDB,pie);
                                }
                            }
                            else
                            {
                                Log.d("firebase", String.valueOf(task.getResult().getValue()));
                            }
                        }
                    });

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error){}
        });
    }

    private void buildGraph(HashMap<String, userExpense> usersRefDB, Pie pie) {
        List<DataEntry> data = new ArrayList<>();

        for (Map.Entry<String,userExpense> entry : usersRefDB.entrySet()) {
            data.add(new ValueDataEntry((entry.getValue().getName()),entry.getValue().getSumOfIncome()));
        }
        pie.data(data);

        anyChartView.setChart(pie);
        APIlib.getInstance().setActiveAnyChartView(anyChartView);
    }


}