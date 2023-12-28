package com.example.wia2007mad.AllModules;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wia2007mad.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

public class ResetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);

        EditText ETGetEmail = findViewById(R.id.ETGetEmail);
        Button BtnResetPassword = findViewById(R.id.BtnResetPassword);
        Button BtnCancelReset = findViewById(R.id.BtnCancelReset);
        FirebaseUser user;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        BtnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = ETGetEmail.getText().toString();

                        if(TextUtils.isEmpty(userEmail) || !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
                            Toast.makeText(ResetPassword.this, "Enter your registered email id",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ResetPassword.this, "Check your email", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                } else {
                                    Toast.makeText(ResetPassword.this, "Unable to send, failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                }});


                BtnCancelReset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });

            }
    private void checkIfEmailExists(String userEmail, EmailCheckCallback callback) {
        FirebaseAuth.getInstance().fetchSignInMethodsForEmail(userEmail)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        boolean isNewUser = task.getResult().getSignInMethods().isEmpty();
                        if (isNewUser) {
                            callback.onEmailChecked(false); // Email does not exist
                        } else {
                            callback.onEmailChecked(true); // Email exists
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Consider handling the failure case or passing the failure through the callback
                        callback.onEmailChecked(false);
                    }
                });
    }


}

