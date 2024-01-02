package com.example.wia2007mad.AllModules.socialmarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.wia2007mad.AllModules.webViewPage;
import com.example.wia2007mad.R;
import com.example.wia2007mad.databinding.HomeBinding;

public class Home extends AppCompatActivity {
    ImageButton successarrow, marketarrow;

    Button businessreadmore;

    private HomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        binding= HomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //set home success stories image
        String  successstory1= "https://www.carscoops.com/wp-content/uploads/2021/09/Mary-Barra-2.jpg",
                successstory2="https://assets-global.website-files.com/636b444a0271e517e1e38c84/64c66c85d117162fb3c56bd7_NLA.IO%20Blog%20Banner%201280%20x%20720%20(6).webp",
                successstory3="https://sugermint.com/wp-content/uploads/2022/08/Evgeniya-Malina-Food-Rocket.jpg";
        Glide.with(this)
                .load(successstory1)
                .into(binding.successimage1);
        Glide.with(this)
                .load(successstory2)
                .into(binding.successimage2);
        Glide.with(this)
                .load(successstory3)
                .into(binding.successimage3);

        //set success story

        binding.successcardview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, webViewPage.class);
                String urlToLoad = "https://www.automotivehalloffame.org/honoree/mary-barra/#:~:text=As%20the%20first%20female%20CEO,Hall%20of%20Fame%20in%202023.";
                intent.putExtra("url", urlToLoad);
                startActivity(intent);
            }
        });
        binding.successcardview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, webViewPage.class);
                String urlToLoad = "https://www.nextlevelacademy.io/blog-posts/from-jobless-to-beauty-boss-the-huda-kattan-success-story";
                intent.putExtra("url", urlToLoad);
                startActivity(intent);
            }
        });

        binding.successcardview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, webViewPage.class);
                String urlToLoad = "https://sugermint.com/evgeniya-malina/";
                intent.putExtra("url", urlToLoad);
                startActivity(intent);
            }
        });

        //set home marketing resource image
        String  marketingresource1= "https://i0.wp.com/simonkingsnorth.com/wp-content/uploads/2019/04/80d59-img_5837.jpg?fit=1200%2C628&ssl=1",
                marketingresource2="https://buybooks.ng/wp-content/uploads/2021/02/9781591847380-1.jpg",
                marketingresource3="https://i0.wp.com/images-na.ssl-images-amazon.com/images/I/51UYILvuvtL._SX352_BO1,204,203,200_.jpg?resize=232%2C327&ssl=1";
        Glide.with(this)
                .load(marketingresource1)
                .into(binding.marketimage1);
        Glide.with(this)
                .load(marketingresource2)
                .into(binding.marketimage2);
        Glide.with(this)
                .load(marketingresource3)
                .into(binding.marketimage3);

        //set marketing resource

        binding.marketingcardview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, webViewPage.class);
                String urlToLoad = "https://simonkingsnorth.com/digital-marketing-books/";
                intent.putExtra("url", urlToLoad);
                startActivity(intent);
            }
        });
        binding.marketingcardview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, webViewPage.class);
                String urlToLoad = "https://sendpulse.com/support/glossary/growth-hacking";
                intent.putExtra("url", urlToLoad);
                startActivity(intent);
            }
        });

        binding.marketingcardview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, webViewPage.class);
                String urlToLoad = "https://seths.blog/tim/";
                intent.putExtra("url", urlToLoad);
                startActivity(intent);
            }
        });


        //button from social market home page to app home page
        binding.socialmarkethomebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //arrow from home page to success
        successarrow = findViewById(R.id.sucessarrow);
        successarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, Success.class));
            }
        });

        //arrow from home page to market
        marketarrow = findViewById(R.id.marketingarrow);
        marketarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, Marketing.class));
            }
        });

        //button from home page to business
        businessreadmore = findViewById(R.id.businessreadmore);
        businessreadmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, Business.class));
            }
        });
    }
}