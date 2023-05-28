package com.example.moneyexpensemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.moneyexpensemanager.databinding.ActivityMainBinding;
import com.example.moneyexpensemanager.fragments.Charts_fragment;
import com.example.moneyexpensemanager.fragments.Personal_fragment;
import com.example.moneyexpensemanager.fragments.Settings_fragment;
import com.example.moneyexpensemanager.fragments.Shared_expense_fragment;


public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.bottomNavigationView.setBackground(null);
        replaceFragment(new Personal_fragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int c_id=item.getItemId();
            if (c_id==R.id.bottom_menu_transaction)
                replaceFragment(new Personal_fragment());
            if (c_id==R.id.bottom_menu_Shared)
                replaceFragment(new Shared_expense_fragment());
            if (c_id==R.id.bottom_menu_Stats)
                replaceFragment(new Charts_fragment());
            if (c_id==R.id.bottom_menu_more)
                replaceFragment(new Settings_fragment());

            return true;
        });

    }
    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_container, fragment);
        fragmentTransaction.commit();
    }
}