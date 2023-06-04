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
import android.widget.TextView;


import com.example.moneyexpensemanager.Adapters.RecyclerViewAdapter;
import com.example.moneyexpensemanager.Models.IncomeModel;
import com.example.moneyexpensemanager.Models.OutcomeModel;
import com.example.moneyexpensemanager.Models.userExpense;
import com.example.moneyexpensemanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class Personal_Income_fragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<IncomeModel> incomeModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal__income_fragment, container, false);
        getArrayLists();
        RecyclerView recyclerView = view.findViewById(R.id.mRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        RecyclerViewAdapter recyclerViewAdapter=new RecyclerViewAdapter(incomeModel,null,0);
        recyclerView.setAdapter(recyclerViewAdapter);

        if (incomeModel!=null)
        {
            TextView noListFound=view.findViewById(R.id.noIncomeFound_TXT);
            noListFound.setVisibility(View.GONE);
        }







        return view;
    }

    private void getArrayLists()
    {
        DocumentReference docIdRef = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());

        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        DocumentReference docRef = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                incomeModel = (ArrayList<IncomeModel>) document.get("incomeList");
                                Log.d("INCOME LIST RV |", incomeModel.toString());
                                //outcomeModel=   (ArrayList<OutcomeModel>) document.get("outcomeList");
                                //Log.d("OUTCOME LIST RV |", outcomeModel.toString());
                            }
                        });
                    } else {
                    }
                } else {
                    Log.d("GET USER  |", "Failed with: ", task.getException());
                }
            }
        });
    }
}