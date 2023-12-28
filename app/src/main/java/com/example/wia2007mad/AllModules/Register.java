package com.example.wia2007mad.AllModules;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wia2007mad.R;
import com.example.wia2007mad.databinding.RegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    RegisterBinding binding;

    FirebaseAuth mAuth;
/*
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(),MainHomePage.class);
            startActivity(intent);
            finish();
        }
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding=RegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EditText ETName = binding.ETName;
        EditText ETHpNumber = binding.ETHpNumber;
        EditText ETEmail = binding.ETEmail;
        EditText ETPassword = binding.ETPassword;
        EditText EtConfirmPassword = binding.ETConfirmPassword;
        Button BtnRegister = binding.BtnRegister;


        TextView TVLogin = findViewById(R.id.TVLogin);
        mAuth = FirebaseAuth.getInstance();

        TVLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        BtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password,username,phonenumber;
                email = String.valueOf(ETEmail.getText());
                password = String.valueOf(ETPassword.getText());
                username = String.valueOf(ETName.getText());
                phonenumber= String.valueOf(ETHpNumber.getText());

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Register.this,"Enter email", Toast.LENGTH_SHORT).show();

                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Register.this,"Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Register.this, "Account created.",
                                            Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    assert user != null;
                                    addUserDetailsToFirestore(user,phonenumber,username);
                                    Intent intent = new Intent(getApplicationContext(),Login.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Register.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }
    public void addUserDetailsToFirestore(FirebaseUser user,String phonenumber,String username) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("uid", user.getUid());
        userDetails.put("email", user.getEmail());
        userDetails.put("phone_number", phonenumber);
        userDetails.put("username",username);
        userDetails.put("role","user");
        // Add other user details as needed

        db.collection("users").document(user.getUid()).set(userDetails)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Handle success
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failure
                    }
                });
    }



}