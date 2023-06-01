package com.example.moneyexpensemanager.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.moneyexpensemanager.Models.IncomeModel;
import com.example.moneyexpensemanager.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class Personal_Income_fragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal__income_fragment, container, false);


        return view;
    }
}