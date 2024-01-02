package com.example.wia2007mad.AllModules;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wia2007mad.AllModules.adapter.CounsellorsAdapter;
import com.example.wia2007mad.AllModules.listeners.CounsellorListener;
import com.example.wia2007mad.AllModules.model.Counsellor;
import com.example.wia2007mad.AllModules.utilities.Constants;
import com.example.wia2007mad.AllModules.utilities.PreferenceManager;
import com.example.wia2007mad.databinding.ActivitySelectCounsellorBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class selectCounsellorActivity extends AppCompatActivity implements CounsellorListener {

    private ActivitySelectCounsellorBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectCounsellorBinding.inflate(getLayoutInflater());
        FirebaseApp.initializeApp(this);
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        setListeners();
        getUsers();
    }

    private void setListeners(){
        binding.imageBack.setOnClickListener(view ->
                onBackPressed());
                //startActivity(new Intent(getApplicationContext(), recentConversation.class)));
    }

    private void getUsers() {
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        // Retrieve user role from PreferenceManager
        String userRole = preferenceManager.getString(Constants.KEY_ROLE);

        // Prepare your query based on the user role
        Query query;
        if ("user".equals(userRole)) {
            // If the role is 'user', fetch 'counsellor' list
            query = database.collection(Constants.KEY_COLLECTION_USERS)
                    .whereEqualTo("role", "counsellor");
        } else {
            // If the role is 'counsellor', fetch 'user' list
            query = database.collection(Constants.KEY_COLLECTION_USERS)
                    .whereNotEqualTo("role", "counsellor");
        }

        // Initialize the users list outside of the query
        List<Counsellor> users = new ArrayList<>();

        // Execute the query
        query.get().addOnCompleteListener(task -> {
            loading(false);
            String currentUserId = preferenceManager.getString(Constants.KEY_USER_ID);
            if (task.isSuccessful() && task.getResult() != null) {
                for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                    if (currentUserId.equals(queryDocumentSnapshot.getId())) {
                        continue;
                    }
                    Counsellor user = new Counsellor();
                    user.name = queryDocumentSnapshot.getString(Constants.KEY_NAME);
                    user.email = queryDocumentSnapshot.getString(Constants.KEY_EMAIL);
                    user.token = queryDocumentSnapshot.getString(Constants.KEY_FCM_TOKEN);
                    user.id = queryDocumentSnapshot.getId();

                    // Retrieve the image URL from the Realtime Database
                    DatabaseReference imageRef = FirebaseDatabase.getInstance("https://authenticationmodule-bebd2-default-rtdb.asia-southeast1.firebasedatabase.app/")
                            .getReference("users")
                            .child(user.id)
                            .child("image");

                    imageRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String imageUrl = dataSnapshot.getValue(String.class);
                                user.image = imageUrl; // Set the image URL

                                // Add the user to the list after retrieving the image URL
                                users.add(user);

                                // Check if all users have been added, then update the RecyclerView
                                if (users.size() == task.getResult().size() - 1) {
                                    if (users.size() > 0) {
                                        CounsellorsAdapter usersAdapter = new CounsellorsAdapter(users, selectCounsellorActivity.this);
                                        binding.counsellorsRecyclerView.setAdapter(usersAdapter);
                                        binding.counsellorsRecyclerView.setVisibility(View.VISIBLE);
                                    } else {
                                        showErrorMessage();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle errors here
                        }
                    });
                }
            }
        });
    }




    private void showErrorMessage(){
        binding.textErrorMessage.setText(String.format("%s", "No user available"));
        binding.textErrorMessage.setVisibility(View.VISIBLE);
    }

    private void loading(Boolean isLoading){
        if(isLoading){
            binding.progressBar.setVisibility(View.VISIBLE);
        }else{
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onUserClicked(Counsellor counsellor) {
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra(Constants.KEY_USER, counsellor);
        startActivity(intent);
        finish();
    }
}