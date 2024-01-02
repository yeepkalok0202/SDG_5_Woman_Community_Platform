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

import com.example.wia2007mad.AllModules.utilities.Constants;
import com.example.wia2007mad.AllModules.utilities.PreferenceManager;
import com.example.wia2007mad.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private PreferenceManager preferenceManager;
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://authenticationmodule-bebd2-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference databaseReference = firebaseDatabase.getReference("users");

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        preferenceManager = new PreferenceManager(getApplicationContext());

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
                                preferenceManager.putString(Constants.KEY_EMAIL, email);
                                database.collection(Constants.KEY_COLLECTION_USERS)
                                        .whereEqualTo(Constants.KEY_EMAIL, email)
                                        .get()
                                        .addOnCompleteListener(task1 -> {
                                            if (task1.isSuccessful() && task1.getResult().getDocuments().size() > 0) {
                                                DocumentSnapshot documentSnapshot = task1.getResult().getDocuments().get(0);
                                                preferenceManager.putString(Constants.KEY_USER_ID, documentSnapshot.getId());
                                                preferenceManager.putString(Constants.KEY_NAME, documentSnapshot.getString(Constants.KEY_NAME));
                                                preferenceManager.putString(Constants.KEY_ROLE, documentSnapshot.getString(Constants.KEY_ROLE));

                                                Query query = databaseReference.child("image");

                                                query.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        if (dataSnapshot.exists()) {
                                                            String image = dataSnapshot.getValue(String.class);
                                                            System.out.println("Image link retrieved: " + image);

                                                            try {
                                                                preferenceManager.putString(Constants.KEY_IMAGE, image);
                                                            } catch (Exception e) {
                                                                // Handle any exceptions here
                                                            }
                                                        } else {
                                                            // Handle the case where the data doesn't exist
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                                        // Handle errors here
                                                    }
                                                });

                                                Toast.makeText(getApplicationContext(), "Login Successful.",
                                                        Toast.LENGTH_SHORT).show();
                                                SharedPreferences preferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
                                                SharedPreferences.Editor editor = preferences.edit();
                                                editor.putBoolean("isLoggedOut", false);
                                                editor.apply();
                                                Intent intent = new Intent(getApplicationContext(), MainHomePage.class);
                                                startActivity(intent);
                                                finish();

                                            } else {
                                                // If sign in fails, display a message to the user.
                                                Toast.makeText(Login.this, "Wrong credentials.",
                                                        Toast.LENGTH_SHORT).show();

                                            }
                                        });
                            }

            });
        }
    });
}
}