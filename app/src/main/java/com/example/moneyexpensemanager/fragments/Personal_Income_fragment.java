package com.example.moneyexpensemanager.fragments;


import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.moneyexpensemanager.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class Personal_Income_fragment extends Fragment {
    Dialog dialog;
    FloatingActionButton addButton;
    String incomeTypeSpinnerResult,incomeCategorySpinnerResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal__income_fragment, container, false);
        addButton=view.findViewById(R.id.income_addBTN);
        initDialog();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });


        return view;
    }

    void initDialog()
    {
        //dialog creation
        dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_add_income_dialog);
        dialog.getWindow().setBackgroundDrawable(getDrawable(getActivity(),R.drawable.dialog_background));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        //Buttons
        Button cancel =dialog.findViewById(R.id.cancelBtn);
        Button add = dialog.findViewById(R.id.addButton);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"DEAD DIALOG",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "ADD FUNCTION",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        //Spinners
        Spinner typeSpinner=dialog.findViewById(R.id.typeSpinner);
        Spinner categorySpinner=dialog.findViewById(R.id.categorySpinner);
        initSpinners(typeSpinner,categorySpinner);
    }


    void initSpinners( Spinner typeSpinner, Spinner categorySpinner)
    {
        //type Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                incomeTypeSpinnerResult=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Category Spinner
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),
                R.array.income_Category_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter2);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                incomeCategorySpinnerResult=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}