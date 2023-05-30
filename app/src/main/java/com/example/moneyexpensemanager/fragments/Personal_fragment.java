package com.example.moneyexpensemanager.fragments;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.moneyexpensemanager.Adapters.myViewpagerAdapter;
import com.example.moneyexpensemanager.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Personal_fragment extends Fragment {

    ViewPager2 viewPager2;
    TabLayout tabLayout;
    FloatingActionButton addButton;
    String TypeSpinnerResult,CategorySpinnerResult;
    Dialog dialog;
    String type="";

    Spinner typeSpinner,categorySpinner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_fragment, container, false);

        //dialog
        initDialog();

        //yes
        initFragment(view);

        //Spinners

        initSpinners();

        //Firebase stuff
        String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());

                type=tabLayout.getTabAt(tab.getPosition()).getText().toString();
                initSpinners();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();

                type=tabLayout.getTabAt(position).getText().toString();
                initSpinners();
            }
        });
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
                Toast.makeText(getContext(),"DEAD DIALOG",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("Income"))
                    addIncomeToUser();
                else
                    addOutcomeToUser();
                dialog.dismiss();
            }
        });


    }

    void initSpinners()
    {
        //type Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TypeSpinnerResult=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Category Spinner
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),
                type.equals("Income")?R.array.income_Category_array:R.array.outcome_Category_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter2);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CategorySpinnerResult=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void initFragment(View view)
    {
        tabLayout=view.findViewById(R.id.tabLayout_container);
        viewPager2=view.findViewById(R.id.personal_viewPager);
        FragmentStateAdapter fragmentStateAdapter=new myViewpagerAdapter(getActivity());
        viewPager2.setAdapter(fragmentStateAdapter);
        addButton=view.findViewById(R.id.main_addBTN);
        typeSpinner=dialog.findViewById(R.id.typeSpinner);
        categorySpinner=dialog.findViewById(R.id.categorySpinner);
    }

    private void addOutcomeToUser() {
        Toast.makeText(getContext(), "ADD OUTCOME FUNCTION",Toast.LENGTH_SHORT).show();
    }

    private void addIncomeToUser() {
        Toast.makeText(getContext(), "ADD INCOME FUNCTION",Toast.LENGTH_SHORT).show();
    }

}