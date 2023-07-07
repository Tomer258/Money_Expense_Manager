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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;


import java.util.ArrayList;
import java.util.HashMap;

public class Settings_fragment extends Fragment {
    private TextView textView,nofamily;
    private Button disconnectBTN,createFamilyBTN,joinFamilyBTN,clearIncomeBTN,clearOutcomeBTN,clearAllBTN;

    private EditText familyCodeET;

    private  ArrayList<String> usersref=new ArrayList<>();
    private HashMap<String,userExpense> member=new HashMap<>();

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
        clearIncomeBTN=view.findViewById(R.id.clearIncome_BTN_settings);;
        clearOutcomeBTN=view.findViewById(R.id.clearOutcome_BTN_settings);
        clearAllBTN=view.findViewById(R.id.clearall_BTN_settings);
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

        clearIncomeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData(0);
            }
        });

        clearOutcomeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData(1);
            }
        });

        clearAllBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData(2);
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
                    if (value!=null)
                    {
                        if (value.getFamilyCode().equals(""))
                        {

                            family.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if (task.isSuccessful())
                                    {
                                        DataSnapshot dataSnapshot = task.getResult();
                                        GenericTypeIndicator<HashMap<String,userExpense>> t = new GenericTypeIndicator<HashMap<String, userExpense>>() {};
                                        HashMap<String,userExpense> usersRefDB=dataSnapshot.getValue(t);
                                        usersRefDB.put(key,value);
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
                                            GenericTypeIndicator<HashMap<String,userExpense>> t = new GenericTypeIndicator<HashMap<String, userExpense>>() {};
                                            HashMap<String,userExpense> usersRefDB=dataSnapshot.getValue(t);
                                            usersRefDB.put(key,value);
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
                    if (value!=null)
                    {
                        if (value.getFamilyCode().equals(""))
                        {
                            usersref.add(key);
                            String family_key =pushed.getKey();
                            value.setFamilyCode(family_key);
                            member.put(key,value);
                            //pushed.setValue(usersref);
                            pushed.setValue(member);
                            user.setValue(value);
                            nofamily.setText("Part of family id: " +family_key);
                        }
                        else
                        {
                            removeFromFamily(key);
                            usersref.add(key);
                            String family_key =pushed.getKey();
                            value.setFamilyCode(family_key);
                            member.put(key,value);
                            pushed.setValue(member);
                            user.setValue(value);
                            nofamily.setText("Part of family id: " +family_key);


                        }
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
                    userFamily.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful())
                            {
                                DataSnapshot dataSnapshot = task.getResult();

                                GenericTypeIndicator<HashMap<String,userExpense>> t = new GenericTypeIndicator<HashMap<String, userExpense>>() {};
                                HashMap<String,userExpense> usersRefDB=dataSnapshot.getValue(t);

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
                if (value!=null)
                {
                    name[0] =value.getFamilyCode();
                    updateFamilyIdText(name[0]);
                }
            }
        });

    }

    private void updateFamilyIdText(String name)
    {
        if (!(name.equals("")))
            nofamily.setText("Part of family id: " +name);
    }

    private void deleteData(int choice)
    {
        DatabaseReference user=FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid());

        user.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    userExpense value = dataSnapshot.getValue(userExpense.class);
                    if (value!=null)
                    {
                        switch (choice)
                        {
                            case 0:
                            {
                                value.clearIncome();
                                break;
                            }

                            case 1:
                            {
                                value.clearOutcome();
                                break;
                            }

                            case 2:
                            {
                                value.clearIncome();
                                value.clearOutcome();
                                break;
                            }
                        }

                        user.setValue(value);
                        if (!(value.getFamilyCode().equals("")))
                            mDatabase.child("families").child(value.getFamilyCode()).child(FirebaseAuth.getInstance().getUid()).setValue(value);
                    }

                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }


}