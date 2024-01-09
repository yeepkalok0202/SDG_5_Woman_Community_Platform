package com.example.wia2007mad.AllModules;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wia2007mad.databinding.ActivityChatRoomFirstPageBinding;

public class GroupChatFirstPage extends AppCompatActivity {
    ActivityChatRoomFirstPageBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatRoomFirstPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.BtnBack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GroupChatFirstPage.this,GroupchatActivity.class);
                startActivity(intent);
            }
        });
    }
}
