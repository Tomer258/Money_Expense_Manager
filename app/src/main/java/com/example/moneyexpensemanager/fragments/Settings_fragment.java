package com.example.moneyexpensemanager.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.moneyexpensemanager.LoginActivity;
import com.example.moneyexpensemanager.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Settings_fragment extends Fragment {
    TextView textView;
    Button disconnectBTN,createFamilyBTN,joinFamilyBTN;

    EditText familyCodeET;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings_fragment, container, false);
        initViews(view);




        return view;
    }

    void initViews(View view)
    {
        textView=view.findViewById(R.id.userName_FireBase_Text);
        disconnectBTN=view.findViewById(R.id.disconnect_BTN_settings);
        createFamilyBTN=view.findViewById(R.id.createFamily_BTN_settings);
        joinFamilyBTN=view.findViewById(R.id.joinFamily_BTN_settings);
        familyCodeET=view.findViewById(R.id.join_family_ET);



        textView.setText(getActivity().getIntent().getExtras().getString("username"));
        disconnectBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}