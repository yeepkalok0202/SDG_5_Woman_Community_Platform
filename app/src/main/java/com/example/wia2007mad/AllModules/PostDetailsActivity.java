package com.example.wia2007mad.AllModules;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.wia2007mad.AllModules.utilities.Constants;
import com.example.wia2007mad.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class PostDetailsActivity extends AppCompatActivity {


    String ptime, myuid, myname, myemail, mydp, uimage, postId, plike, hisdp, hisname; //dp means profile picture
    ImageView picture, image, backtodisplaysection;
    TextView name, time, title, description;
    LinearLayout profile;
    EditText comment;
    ImageButton sendb;
    RecyclerView recyclerView;
    ArrayList<CommentModel> commentList;
    Comment_Adapter adapterComment;
    ImageView imagep;
    ProgressDialog progressDialog;
    boolean count = false;
    private ValueEventListener valueEventListener;


    FirebaseFirestore firebaseFirestore;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://authenticationmodule-bebd2-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference databaseReference = firebaseDatabase.getReference("users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_comment_all);
        postId = getIntent().getStringExtra("pid");
        recyclerView = findViewById(R.id.recyclecomment);
        picture = findViewById(R.id.picturetv);
        image = findViewById(R.id.pimagetvv);
        name = findViewById(R.id.unametv);
        time = findViewById(R.id.utimetv);
        title = findViewById(R.id.ptitletv);
        myemail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        myuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        description = findViewById(R.id.descript);
        comment = findViewById(R.id.typecommet);
        sendb = findViewById(R.id.sendcomment);
        imagep = findViewById(R.id.commentimge);
        profile = findViewById(R.id.profilelayout);
        progressDialog = new ProgressDialog(this);
        backtodisplaysection=findViewById(R.id.backtodisplaysection);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        myname=firebaseAuth.getUid();
        DocumentReference documentReference=FirebaseFirestore.getInstance().collection("users").document(myname);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    User user=documentSnapshot.toObject(User.class);
                    if(user!=null){
                        myname=user.getUsername();
                    }
                }
            }
        });
        loadPostInfo();
        loadUserInfo();
        loadComments();
        sendb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postComment();
            }
        });

        backtodisplaysection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });


    }

    private void loadComments() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        commentList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comment").child(postId);
        valueEventListener=reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    CommentModel modelComment = dataSnapshot1.getValue(CommentModel.class);
                    commentList.add(modelComment);
                    adapterComment = new Comment_Adapter(getApplicationContext(), commentList, myuid, postId);
                    recyclerView.setAdapter(adapterComment);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void postComment() {
        progressDialog.setMessage("Publishing Comment");

        final String commentss = comment.getText().toString().trim();
        if (TextUtils.isEmpty(commentss)) {
            Toast.makeText(PostDetailsActivity.this, "Empty comment!", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.show();
        String timestamp = String.valueOf(System.currentTimeMillis());
        DatabaseReference datarf = FirebaseDatabase.getInstance().getReference("Comment").child(postId).child("Comments at "+timestamp);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("cId", timestamp);
        hashMap.put("comment", commentss);
        hashMap.put("ptime", timestamp);
        hashMap.put("uid", myuid);
        hashMap.put("uemail", myemail);
        hashMap.put("udp", mydp);


        hashMap.put("uname", myname);
        datarf.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressDialog.dismiss();
                Toast.makeText(PostDetailsActivity.this, "Comment posted!", Toast.LENGTH_LONG).show();
                comment.setText("");
                updatecommetcount();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(PostDetailsActivity.this, "Check connectivity issues!", Toast.LENGTH_LONG).show();
            }
        });
    }

    //count == true at first, stop counting when end==false
    private void updatecommetcount() {
        count = true;
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts").child(postId);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (count) {
                    String comments = "" + dataSnapshot.child("pcomment").getValue();
                    int newcomment = Integer.parseInt(comments) + 1;
                    reference.child("pcomment").setValue("" + newcomment);
                    count = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadUserInfo() {


        Query query = databaseReference.child(myuid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    mydp = dataSnapshot1.getValue(String.class);
                    Glide.with(getApplicationContext()).load(mydp).into(imagep);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }

    private void loadPostInfo() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Posts");
        final Query query = databaseReference.orderByChild("ptime").equalTo(postId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String ptitle = dataSnapshot1.child("title").getValue().toString();
                    String descriptions = dataSnapshot1.child("description").getValue().toString();
                    uimage = dataSnapshot1.child("uimage").getValue().toString();
                    hisdp = dataSnapshot1.child("udp").getValue().toString();
                    hisname = dataSnapshot1.child("uname").getValue().toString();
                    ptime = dataSnapshot1.child("ptime").getValue().toString();
                    plike = dataSnapshot1.child("plike").getValue().toString();


                    Instant instant = null;
                    LocalDateTime dateTime = null;
                    DateTimeFormatter formatter = null;
                    String timestamptemp = null;

                    Locale locale = Locale.getDefault();
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        instant = Instant.now();
                        dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        timestamptemp = dateTime.format(formatter);
                    }
                    final String timestamp=timestamptemp;
                    String timedate =timestamp;
                    name.setText(hisname);
                    title.setText(ptitle);
                    description.setText(descriptions);
                    time.setText(timedate);
                    Glide.with(getApplicationContext()).load(uimage).into(image);
                    Glide.with(getApplicationContext()).load(hisdp).into(picture);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }




}
