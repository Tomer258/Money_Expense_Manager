package com.example.moneyexpensemanager.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneyexpensemanager.LoginActivity;
import com.example.moneyexpensemanager.Models.userExpense;
import com.example.moneyexpensemanager.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Settings_fragment extends Fragment {
    TextView textView,nofamily;
    Button disconnectBTN,createFamilyBTN,joinFamilyBTN;

    EditText familyCodeET;

    ArrayList<String> usersref=new ArrayList<>();

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
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
        nofamily = view.findViewById(R.id.no_family_TXT);
        checkIfInFamily();





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

        createFamilyBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFamily();
            }
        });

        joinFamilyBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinFamily();
            }
        });
    }

    private void joinFamily() {
        DatabaseReference user=FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid());
        String code=familyCodeET.getText().toString();
        String key = user.getKey();
        DatabaseReference family=mDatabase.child("families").child(code);



        user.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    userExpense value = dataSnapshot.getValue(userExpense.class);
                    if (value.getFamilyCode().equals(""))
                    {

                        family.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if (task.isSuccessful())
                                    {
                                        DataSnapshot dataSnapshot = task.getResult();
                                        GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                                        ArrayList<String> usersRefDB = dataSnapshot.getValue(t);
                                        usersRefDB.add(key);
                                        family.setValue(usersRefDB);

                                        String family_key =family.getKey();
                                        value.setFamilyCode(family_key);
                                        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(value);
                                        nofamily.setText("Part of family id: " +family_key);

                                    }
                                }
                            });
                    }
                    else
                    {
                        if (!(value.getFamilyCode().equals(code)))
                        {
                            removeFromFamily(key);

                            family.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if (task.isSuccessful())
                                    {
                                        DataSnapshot dataSnapshot = task.getResult();
                                        GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                                        ArrayList<String> usersRefDB = dataSnapshot.getValue(t);
                                        usersRefDB.add(key);
                                        family.setValue(usersRefDB);

                                        String family_key =family.getKey();
                                        value.setFamilyCode(family_key);
                                        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(value);
                                        nofamily.setText("Part of family id: " +family_key);

                                    }
                                }
                            });
                        }
                        else
                            Toast.makeText(getActivity(),"Cant Join Same Family",Toast.LENGTH_SHORT).show();

                    }
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }

    private void createFamily() {
        DatabaseReference user=FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid());
        String key = user.getKey();
        DatabaseReference families=mDatabase.child("families");
        DatabaseReference pushed =families.push();


        user.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    userExpense value = dataSnapshot.getValue(userExpense.class);
                    if (value.getFamilyCode().equals(""))
                    {
                        usersref.add(key);
                        pushed.setValue(usersref);
                        String family_key =pushed.getKey();
                        value.setFamilyCode(family_key);
                        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(value);
                        nofamily.setText("Part of family id: " +family_key);



                    }
                    else
                    {
                        removeFromFamily(key);

                        usersref.add(key);
                        pushed.setValue(usersref);
                        String family_key =pushed.getKey();
                        value.setFamilyCode(family_key);
                        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(value);
                        nofamily.setText("Part of family id: " +family_key);


                    }
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });



    }

    private void removeFromFamily(String key)
    {
        DatabaseReference user=FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid());


        user.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    userExpense value = dataSnapshot.getValue(userExpense.class);
                    DatabaseReference userFamily=mDatabase.child("families").child(value.getFamilyCode());
                    //Query mQuery = userFamily.equalTo(key);
                    //mQuery.getRef().removeValue();
                    userFamily.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful())
                            {
                                DataSnapshot dataSnapshot = task.getResult();
                                GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                                ArrayList<String> usersRefDB = dataSnapshot.getValue(t);
                                usersRefDB.remove(key);
                                userFamily.setValue(usersRefDB);
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

    //check if user have family id. if no family id returns "" else, returns the id
    private void checkIfInFamily()
    {
        DatabaseReference user=FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid());
        final String[] name = new String[1];
        user.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot dataSnapshot = task.getResult();
                userExpense value = dataSnapshot.getValue(userExpense.class);
                name[0] =value.getFamilyCode();
                updateFamilyIdText(name[0]);
            }
        });

    }

    private void updateFamilyIdText(String name)
    {
        if (!(name.equals("")))
            nofamily.setText("Part of family id: " +name);
    }



}