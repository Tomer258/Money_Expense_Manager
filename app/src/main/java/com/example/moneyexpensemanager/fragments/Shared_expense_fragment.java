package com.example.moneyexpensemanager.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.moneyexpensemanager.Adapters.RecyclerViewAdapter;
import com.example.moneyexpensemanager.Models.IncomeModel;
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
import java.util.Map;

public class Shared_expense_fragment extends Fragment {
    RecyclerView incomeRecyclerView,outcomeRecyclerView;

    ArrayList<String> memberNames=new ArrayList<>();

    DatabaseReference user= FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid());

    int savedPos=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_shared_expense_fragment, container, false);
        incomeRecyclerView=view.findViewById(R.id.mIncomeSharedRecyclerView);
        outcomeRecyclerView=view.findViewById(R.id.mOutcomeSharedRecyclerView);
        initFragment(view);
        return view;
    }


    private void initFragment(View view)
    {

        user.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    userExpense value = dataSnapshot.getValue(userExpense.class);
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
                                    initSpinner(view,usersRefDB);
                                }
                            }
                            else
                            {
                                Log.d("firebase", String.valueOf(task.getResult().getValue()));
                            }
                        }
                    });

                    userFamily.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            GenericTypeIndicator<HashMap<String,userExpense>> t = new GenericTypeIndicator<HashMap<String, userExpense>>() {};
                            HashMap<String,userExpense> usersRefDB=snapshot.getValue(t);
                            if (usersRefDB!=null)
                            {
                                initSpinner(view,usersRefDB);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });

    }



    private void initSpinner(View view,HashMap<String,userExpense> members)
    {

        Spinner memberSpinner=view.findViewById(R.id.familyMember_Spinner);
        ArrayList<String> memberNames=new ArrayList<>();
        HashMap<String,String> names=new HashMap<>();

        for (Map.Entry<String,userExpense> entry : members.entrySet()) {
            memberNames.add(entry.getValue().getName());
            names.put(entry.getValue().getName(),entry.getValue().getuID());
        }

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(view.getContext(),android.R.layout.simple_spinner_item,memberNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        memberSpinner.setAdapter(adapter);
        memberSpinner.setSelection(savedPos);

        memberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String spinnerResult=parent.getItemAtPosition(position).toString();
                savedPos=position;
                createRV(names.get(spinnerResult),members);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void createRV(String uId, HashMap<String,userExpense> members)
    {
        ArrayList<IncomeModel> incomeModels=members.get(uId).getIncomeList();
        ArrayList<OutcomeModel> outcomeModels = members.get(uId).getOutcomeList();

        refreshIncomeList(incomeModels);
        refreshOutcomeList(outcomeModels);
    }

    private void refreshIncomeList(ArrayList<IncomeModel> income)
    {
        incomeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(income, null, 0);
        incomeRecyclerView.setAdapter(recyclerViewAdapter);
    }

    private void refreshOutcomeList(ArrayList<OutcomeModel> outcome)
    {
        outcomeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(null, outcome, 1);
        outcomeRecyclerView.setAdapter(recyclerViewAdapter);
    }

}