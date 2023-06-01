package com.example.moneyexpensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.example.moneyexpensemanager.Models.userExpense;
import com.example.moneyexpensemanager.databinding.ActivityMainBinding;
import com.example.moneyexpensemanager.fragments.Charts_fragment;
import com.example.moneyexpensemanager.fragments.Personal_fragment;
import com.example.moneyexpensemanager.fragments.Settings_fragment;
import com.example.moneyexpensemanager.fragments.Shared_expense_fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.SetOptions;


public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Bundle bundle = new Bundle();
    userExpense userExpense;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.bottomNavigationView.setBackground(null);

        String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //Firebase stuff
        initFireBase(uId);


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

    private void initFireBase(String uId)
    {
        DocumentReference docIdRef = db.collection("users").document(uId);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        DocumentReference docRef = db.collection("users").document(uId);
                        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                userExpense = documentSnapshot.toObject(userExpense.class);
                            }
                        });
                    } else {
                        userExpense=new userExpense();
                        db.collection("users").document(uId).set(userExpense);
                    }
                } else {
                    Log.d("GET USER  |", "Failed with: ", task.getException());
                }
            }
        });
    }
}