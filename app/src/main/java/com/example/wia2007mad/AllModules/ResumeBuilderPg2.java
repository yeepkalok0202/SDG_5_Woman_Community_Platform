package com.example.wia2007mad.AllModules;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wia2007mad.R;

public class ResumeBuilderPg2 extends AppCompatActivity {

    EditText ETInputName;
    EditText ETInputContact;
    EditText ETInputAboutMe;
    EditText ETInputSkills;
    EditText ETInputLanguages;
    EditText ETInputWorkXP;
    EditText ETInputEducation;
    Button SubmitBtn;
    ImageButton backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_secondpage_used);

        ETInputName = findViewById(R.id.ETInputName);
        ETInputContact = findViewById(R.id.ETInputContact);
        ETInputAboutMe = findViewById(R.id.ETInputAboutMe);
        ETInputSkills = findViewById(R.id.ETInputSkills);
        ETInputEducation = findViewById(R.id.ETEducation);
        ETInputWorkXP = findViewById(R.id.ETInputWorkXP);
        ETInputLanguages = findViewById(R.id.ETLanguages);
        SubmitBtn = findViewById(R.id.submitBtn);
        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });
        SubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ETInputName.getText().toString();
                String contact = ETInputContact.getText().toString();
                String desc = ETInputAboutMe.getText().toString();
                String skills = ETInputSkills.getText().toString();
                String education = ETInputEducation.getText().toString();
                String workXP = ETInputWorkXP.getText().toString();
                String languages = ETInputLanguages.getText().toString();

                Bundle bundle = new Bundle();
                bundle.putString("name",name);
                bundle.putString("contact",contact);
                bundle.putString("desc",desc);
                bundle.putString("skills",skills);
                bundle.putString("education",education);
                bundle.putString("workXP",workXP);
                bundle.putString("languages",languages);
                Intent i = new Intent(ResumeBuilderPg2.this, ResumeBuilderFinalPg.class);
                i.putExtras(bundle);
                startActivity(i);
                finish();


            }
        });


    }
}