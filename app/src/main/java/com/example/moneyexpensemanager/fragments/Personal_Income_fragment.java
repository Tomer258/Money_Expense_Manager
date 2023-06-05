package com.example.moneyexpensemanager.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


//import com.example.moneyexpensemanager.Adapters.RecyclerViewAdapter;
import com.example.moneyexpensemanager.Models.IncomeModel;
import com.example.moneyexpensemanager.Models.userExpense;
import com.example.moneyexpensemanager.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;


public class Personal_Income_fragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    ArrayList<IncomeModel> income;
    userExpense userExpenseInstance = new userExpense();
    TextView noListFound;

    String save;
    int num;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal__income_fragment, container, false);
        income = userExpenseInstance.getIncomeList();
        //getListItems();
        omo();
        num=1;
        //Toast.makeText(getActivity(),"Im inside Main thread",Toast.LENGTH_LONG).show();
        Log.d("FUCK ME", "shit");


        System.out.println(save);
        //RecyclerView recyclerView = view.findViewById(R.id.mRecyclerView);
        // recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        // RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(income, null, 0);
        //recyclerView.setAdapter(recyclerViewAdapter);
        if (income != null) {
            noListFound = view.findViewById(R.id.noIncomeFound_TXT);
            //noListFound.setVisibility(View.GONE);
        }


        return view;
    }

    private void getListItems() {

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Toast.makeText(getActivity(),"Im inside bitch",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("zibi", "loadPost:onCancelled", databaseError.toException());
            }
        };



        //mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(postListener);


    }

    private void omo()
    {
        DatabaseReference users=FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid());
        users.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                userExpense value = dataSnapshot.getValue(userExpense.class);
                if(value!=null)
                {
                    userExpenseInstance=value;
                    print();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error){}
        });
    }

    private void print()
    {
        String omo=new Gson().toJson(userExpenseInstance);
        noListFound.setText(omo);
    }
}



