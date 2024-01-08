package com.example.wia2007mad.AllModules;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.wia2007mad.R;

public class ResumeBuilderFinalPg extends AppCompatActivity {

    TextView name;
    TextView aboutMe;
    TextView education;
    TextView workXP;
    TextView skills;
    TextView languages;
    TextView contact;
    Button btnPDF;

    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_resume_finalpage);

        name = findViewById(R.id.TVName);
        aboutMe = findViewById(R.id.TVAboutMe);
        education = findViewById(R.id.TVEducation);
        workXP = findViewById(R.id.TVWorkXP);
        skills = findViewById(R.id.TVSkills);
        contact = findViewById(R.id.TVContact);
        languages = findViewById(R.id.TVLanguages);
        btnPDF = findViewById(R.id.BtnPDF);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // Permission is already granted
            } else {
                // Request the permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String nameInput = bundle.getString("name");
            String aboutMeInput = bundle.getString("desc");
            String educationInput = bundle.getString("education");
            String workXPInput = bundle.getString("workXP");
            String skillsInput = bundle.getString("skills");
            String contactInput = bundle.getString("contact");
            String languagesInput = bundle.getString("languages");

            if(workXPInput.equals("-")){
                workXP.setText("Currently enrolled in university.");
            }
            else
                workXP.setText(workXPInput);
            name.setText(nameInput);
            aboutMe.setText(aboutMeInput);
            education.setText(educationInput);


            skills.setText(skillsInput);
            contact.setText(contactInput);
            languages.setText(languagesInput);

            btnPDF.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    captureAndSaveScreenshotAboveBtn();
                }
            });
        }
    }
    private void captureAndSaveScreenshotAboveBtn() {
        View rootView = getWindow().getDecorView().getRootView();
        Bitmap bitmap = getScreenshotAboveBtn(rootView);

        if (bitmap != null) {
            saveBitmapToGallery(bitmap);
            Toast.makeText(this, "Screenshot above BtnPDF saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to capture screenshot above BtnPDF", Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap getScreenshotAboveBtn(View view) {
        Bitmap screenshot = null;

        try {
            int screenWidth = view.getWidth();
            int btnTop = findViewById(R.id.BtnPDF).getTop();
            int statusBarHeight = getStatusBarHeight();

            // Create a bitmap with the height up to the top of BtnPDF, excluding the status bar
            screenshot = Bitmap.createBitmap(screenWidth, btnTop, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(screenshot);
            canvas.drawColor(Color.WHITE); // Set the background color to white
            canvas.translate(0, -statusBarHeight); // Exclude the status bar
            view.draw(canvas);
        } catch (Exception e) {
            Log.e("CaptureScreenshotAboveBtn", "Error capturing screenshot above BtnPDF: " + e.getMessage());
        }

        return screenshot;
    }

    private void saveBitmapToGallery(Bitmap bitmap) {
        String fileName = "resume_screenshot_" + System.currentTimeMillis() + ".png";
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
        }

        Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        try {
            if (uri != null) {
                getContentResolver().openOutputStream(uri).close();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, getContentResolver().openOutputStream(uri));
            }
        } catch (Exception e) {
            Log.e("SaveBitmapToGallery", "Error saving bitmap to gallery: " + e.getMessage());
        }
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }

}