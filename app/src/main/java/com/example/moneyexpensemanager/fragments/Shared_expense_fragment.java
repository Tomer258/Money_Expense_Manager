package com.example.moneyexpensemanager.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.moneyexpensemanager.Models.IncomeModel;
import com.example.moneyexpensemanager.Models.OutcomeModel;
import com.example.moneyexpensemanager.Models.userExpense;
import com.example.moneyexpensemanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;

public class Shared_expense_fragment extends Fragment {
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_shared_expense_fragment, container, false);
        recyclerView=view.findViewById(R.id.mSharedRecyclerView);
        initFragment(view);
        return view;
    }


    private void initSpinner(View view, ArrayList<String> memberNames,HashMap<String,userExpense> members)
    {

        Spinner memberSpinner=view.findViewById(R.id.familyMember_Spinner);
        //type Spinner
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,memberNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        memberSpinner.setAdapter(adapter);

        memberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String spinnerResult=parent.getItemAtPosition(position).toString();
                createRV(spinnerResult,members);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initFragment(View view)
    {
        DatabaseReference user= FirebaseDatabase.getInstance().getReference("users");


        user.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    userExpense value = dataSnapshot.getValue(userExpense.class);
                    DatabaseReference userFamily=FirebaseDatabase.getInstance().getReference().child("families").child(value.getFamilyCode());
                    userFamily.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful())
                            {
                                DataSnapshot dataSnapshot = task.getResult();
                                GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                                ArrayList<String> usersRefDB = dataSnapshot.getValue(t);
                                if (usersRefDB!=null)
                                {
                                    ArrayList<String> memberNames=new ArrayList<>();
                                    HashMap<String,userExpense> members=new HashMap<>();
                                    Query mQuery;
                                    for (int i = 0; i < usersRefDB.size(); i++)
                                    {
                                        String temp = usersRefDB.get(i);
                                        mQuery=user.equalTo(temp);
                                        mQuery.getRef().get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                if (task.isSuccessful())
                                                {
                                                    DataSnapshot dataSnapshot1=task.getResult();
                                                    userExpense userExpense=dataSnapshot1.getValue(userExpense.class);
                                                    memberNames.add(userExpense.getName());
                                                    members.put(userExpense.getName(),userExpense);
                                                }
                                            }
                                        });

                                    }

                                    initSpinner(view,memberNames,members);
                                }

                            }
                            else
                            {
                                Log.d("firebase", String.valueOf(task.getResult().getValue()));
                            }
                        }
                    });
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });

    }

    private void createRV(String name, HashMap<String,userExpense> members)
    {

    }
}