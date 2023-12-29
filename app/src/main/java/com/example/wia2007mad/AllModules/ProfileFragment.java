package com.example.wia2007mad.AllModules;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.wia2007mad.R;
import com.example.wia2007mad.databinding.DialogUpdateProfileBinding;
import com.example.wia2007mad.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;


public class ProfileFragment extends Fragment {

   // FirebaseDatabase firebaseDatabase;
   // DatabaseReference databaseReference;
   // StorageReference storageReference;
    String storagepath = "Users_Profile_image/";
    ProgressDialog pd;
    private static final int CAMERA_REQUEST = 100;
    private static final int STORAGE_REQUEST = 200;
    private static final int IMAGEPICK_GALLERY_REQUEST = 300;
    private static final int IMAGE_PICKCAMERA_REQUEST = 400;
    String cameraPermission[];
    String storagePermission[];
    Uri imageuri;
    String profileOrCoverPhoto;

    //important attribute
    FirebaseFirestore firebaseFirestore;
    DocumentReference userreference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String newusernameinput,newphonenumberinput;
    FragmentProfileBinding binding;
    Context fragmentContext;
    Dialog dialogupdateprofiledetails;
    Dialog dialogconfirmpassword;
    FirebaseDatabase firebaseDatabase;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= FragmentProfileBinding.inflate(inflater,container,false);
        fragmentContext=getContext();
        dialogconfirmpassword=new Dialog(fragmentContext);
        dialogupdateprofiledetails=new Dialog(fragmentContext);
        pd = new ProgressDialog(getContext());
        pd.setCanceledOnTouchOutside(false);


        //1st section
        //get database documents
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        assert firebaseUser != null;
        String uid=firebaseUser.getUid();
        firebaseFirestore=FirebaseFirestore.getInstance();
        userreference=firebaseFirestore.collection("users").document(uid);
        userreference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    NormalUser user=documentSnapshot.toObject(NormalUser.class);
                    if(user!=null){
                        String profileusername=binding.profileusername.getText().toString()+" "+user.getUsername();
                        binding.profileusername.setText(profileusername);
                        String profileemail=binding.profileemail.getText().toString()+" "+user.getEmail();
                        binding.profileemail.setText(profileemail);
                        String profilephonenumber=binding.profilephonenumber.getText().toString()+" "+user.getPhone_number();
                        binding.profilephonenumber.setText(profilephonenumber);
                    }
                }
            }
        });
        binding.ChangeProfileDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupToChangeProfileDetails();
            }
        });
        // 2nd section retrieve
        //3rd section , using the dialog to do changes if password is betul ^_^

        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        //4th section profile pic change
        firebaseDatabase = FirebaseDatabase.getInstance("https://authenticationmodule-bebd2-default-rtdb.asia-southeast1.firebasedatabase.app/");
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = firebaseDatabase.getReference("users");

        Query query = databaseReference.equalTo(firebaseUser.getUid());
        System.out.println(firebaseUser.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    String image = dataSnapshot1.getValue(String.class);
                    System.out.println("wow lets try thIS "+image);
                    try {
                        Glide.with(getContext())
                                .load(image)
                                .into(binding.profilepicholder);
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //set let user change
        binding.profilepicholder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Updating Profile Picture");
                profileOrCoverPhoto="image";
                showImagePicDialog();
            }
        });
        return binding.getRoot();
    }

    private void showPopupToChangeProfileDetails() {
        // Create the dialog
        dialogupdateprofiledetails.setContentView(R.layout.dialog_update_profile);
        dialogupdateprofiledetails.setCancelable(false);
        // Initialize the views
        EditText newusername=dialogupdateprofiledetails.findViewById(R.id.newusername),newphonenumber=dialogupdateprofiledetails.findViewById(R.id.newphonenumber);
        TextView cancelupdate=dialogupdateprofiledetails.findViewById(R.id.cancelupdate),confirmupdate=dialogupdateprofiledetails.findViewById(R.id.updateprofiledetailsconfirmbutton);
        // Set text or other properties if needed

        // Set the close button action
        cancelupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogupdateprofiledetails.dismiss();
            }
        });
        confirmupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newusernameinput=newusername.getText().toString();
                newphonenumberinput=newphonenumber.getText().toString();
                if(newusernameinput.isEmpty()||newphonenumberinput.isEmpty()){
                    Toast.makeText(getContext(),"Please update your profile details...",Toast.LENGTH_SHORT).show();
                }else{
                    showPopupToConfirmPasswordToApproveChanges();
                }
            }
        });
        // Set the dialog background to transparent
        if (dialogupdateprofiledetails.getWindow() != null) {
            dialogupdateprofiledetails.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        // Show the popup dialog
        dialogupdateprofiledetails.show();
    }

    private void showPopupToConfirmPasswordToApproveChanges() {
        // Create the dialog
        dialogconfirmpassword.setContentView(R.layout.dialog_confirm_password);
        dialogconfirmpassword.setCancelable(false);

        // Initialize the views
        EditText confirmpassword=dialogconfirmpassword.findViewById(R.id.confirmpassword);
        TextView backconfirmpassword=dialogconfirmpassword.findViewById(R.id.backconfirmpassword),proceedconfirmpassword=dialogconfirmpassword.findViewById(R.id.proceedconfirmpassword);
        backconfirmpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogconfirmpassword.dismiss();
            }
        });
        proceedconfirmpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String confirmpasswordinput=confirmpassword.getText().toString();
                if(firebaseUser!=null){
                    AuthCredential credential= EmailAuthProvider.getCredential(firebaseUser.getEmail(),confirmpasswordinput);
                    firebaseUser.reauthenticate(credential).addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            Map<String,Object> updateddata=new HashMap<>();
                            updateddata.put("username",newusernameinput);
                            updateddata.put("phone_number",newphonenumberinput);

                            //update data
                            userreference.update(updateddata).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getContext(), "Personal Details Updated Successfully !", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Error updating user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else{
                            Toast.makeText(getContext(), "Wrong Credentials..." , Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                dialogconfirmpassword.dismiss();
                dialogupdateprofiledetails.dismiss();
            }
        });
        // Set the dialog background to transparent
        if (dialogconfirmpassword.getWindow() != null) {
            dialogconfirmpassword.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        // Show the popup dialog
        dialogconfirmpassword.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        Query query = databaseReference.equalTo(firebaseUser.getUid());
        System.out.println(firebaseUser.getUid());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String image = dataSnapshot1.getValue(String.class);
                    System.out.println("wow lets try thIS: "+image);

                    try {
                        Glide.with(getContext())
                                .load(image)
                                .into(binding.profilepicholder);
                    } catch (Exception e) {
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        DatabaseReference query = databaseReference.child(firebaseUser.getUid());


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String image = dataSnapshot1.getValue(String.class);

                    try {
                        Glide.with(getContext())
                                .load(image)
                                .into(binding.profilepicholder);
                    } catch (Exception e) {
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // checking storage permission ,if given then we can add something in our storage
    private Boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    // requesting for storage permission
    private void requestStoragePermission() {
        requestPermissions(storagePermission, STORAGE_REQUEST);
    }

    // checking camera permission ,if given then we can click image using our camera
    private Boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    // requesting for camera permission if not given
    private void requestCameraPermission() {
        requestPermissions(cameraPermission, CAMERA_REQUEST);
    }

    private void pickFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGEPICK_GALLERY_REQUEST);
    }

    private void pickFromCamera() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "Temp_pic");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Temp Description");
        imageuri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        Intent camerIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camerIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageuri);
        startActivityForResult(camerIntent, IMAGE_PICKCAMERA_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST: {
                if (grantResults.length > 0) {
                    boolean camera_accepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageaccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (camera_accepted && writeStorageaccepted) {
                        pickFromCamera();
                    } else {
                        Toast.makeText(getContext(), "Please Enable Camera and Storage Permissions", Toast.LENGTH_LONG).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST: {
                if (grantResults.length > 0) {
                    boolean writeStorageaccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (writeStorageaccepted) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(getContext(), "Please Enable Storage Permissions", Toast.LENGTH_LONG).show();
                    }
                }
            }
            break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGEPICK_GALLERY_REQUEST) {
                imageuri = data.getData();
                updateProfilePicToDatabase(imageuri);
            }
            if (requestCode == IMAGE_PICKCAMERA_REQUEST) {
                updateProfilePicToDatabase(imageuri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showImagePicDialog() {
        String options[] = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Pick Image From");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // if access is not given then we will request for permission
                if (which == 0) {
                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    } else {
                        pickFromCamera();
                    }
                } else if (which == 1) {
                    if (!checkStoragePermission()) {
                        requestStoragePermission();
                    } else {
                        pickFromGallery();
                    }
                }
            }
        });
        builder.create().show();
    }

    //Send the upload profile pic to realtime database

    // We will upload the image from here.
    private void updateProfilePicToDatabase(final Uri uri) {
        pd.show();
        // We are taking the filepath as storagepath + firebaseauth.getUid()+".png"
        String filepathname = storagepath + "" + profileOrCoverPhoto + "_" + firebaseUser.getUid();
        StorageReference storageReference1 = storageReference.child(filepathname);
        storageReference1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful()) ;

                // We will get the url of our image using uritask
                final Uri downloadUri = uriTask.getResult();
                if (uriTask.isSuccessful()) {

                    // updating our image url into the realtime database
                    HashMap<String, Object> userprofilepicurl = new HashMap<>();
                    userprofilepicurl.put(profileOrCoverPhoto, downloadUri.toString());
                    System.out.println(downloadUri.toString());
                    databaseReference.child(firebaseUser.getUid()).updateChildren(userprofilepicurl).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            pd.dismiss();
                            Toast.makeText(getActivity(), "Updated", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(getActivity(), "Error Updating "+e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    pd.dismiss();
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }
}


