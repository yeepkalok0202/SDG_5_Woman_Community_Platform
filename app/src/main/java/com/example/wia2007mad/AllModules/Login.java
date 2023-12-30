package com.example.wia2007mad.AllModules;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wia2007mad.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        SharedPreferences sharedPreferences= getSharedPreferences("AppPrefs",MODE_PRIVATE);
        boolean isLoggedOut=sharedPreferences.getBoolean("isLoggedOut",false);
        if(currentUser != null && !isLoggedOut){
            Intent intent = new Intent(getApplicationContext(),MainHomePage.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        TextView ETLoginEmail = findViewById(R.id.ETLoginEmail);
        TextView ETLoginPassword = findViewById(R.id.ETLoginPassword);
        Button BtnSignIn = findViewById(R.id.BtnSignIn);
        TextView TVRegisterNow = findViewById(R.id.TVRegisterNow);
        mAuth = FirebaseAuth.getInstance();
        TextView TVForgotPassword = findViewById(R.id.TVForgotPassword);

        TVRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Register.class);
                startActivity(intent);
            }
        });

        TVForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (getApplicationContext(),ResetPassword.class);
                startActivity(intent);
            }
        });
        BtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email, password;
                email = String.valueOf(ETLoginEmail.getText());
                password = String.valueOf(ETLoginPassword.getText());

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Login.this,"Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Login.this,"Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Login Successful.",
                                            Toast.LENGTH_SHORT).show();
                                    SharedPreferences preferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putBoolean("isLoggedOut", false);
                                    editor.apply();
                                    Intent intent = new Intent(getApplicationContext(),MainHomePage.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Login.this, "Wrong credentials.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }
}