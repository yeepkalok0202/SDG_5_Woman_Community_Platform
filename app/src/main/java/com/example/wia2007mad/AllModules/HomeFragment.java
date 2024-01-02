package com.example.wia2007mad.AllModules;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Engine;
import com.example.wia2007mad.databinding.FragmentHomeBinding;
import com.example.wia2007mad.databinding.MainHomePageBinding;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    String url1="https://www.unwomen.org/sites/default/files/Headquarters/Images/Sections/Multimedia/2017/infographic-spotlight-sdg5-data-card-violence-front.png?h=624&la=en&w=960&fbclid=IwAR0i65TNRFmgd22ortO11fWRK0nJENjZcTT-Rb9_Z0QIvIWL1annO3TX8YM",
    url2="https://pcw.gov.ph/assets/files/2020/05/Picture1.png?x46289&fbclid=IwAR1t9znvgFGsE0pH3eq9pcYALp8kEfF0iQiskEG9jkN25TZ_Yz-oONh5-yg",
    url3="https://i.pinimg.com/originals/6d/e9/9c/6de99c9cfd33f37747ae5f7b89c16c17.png?fbclid=IwAR15soBftfVPTFHh1sXGyHBTi7oizKp-K0rVilXE88niCbhLdoNuabZ9ENw",
    url4="https://live.staticflickr.com/693/21627969361_668759f18c_b.jpg?fbclid=IwAR3JED8u3DSh45SDcHU3K-gwvx8m_MzZrxYRPazSuuQyCHDO0L0mV1wTEL8",
    url5="https://www.dogoodpeople.com/wp-content/uploads/2022/03/SDG-5-grande-en.jpeg?fbclid=IwAR232ppAAT5YNYoVMo2B23rCctrDgPGJ3FasRejkyeO_ipHzK31cIBn0mno";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= FragmentHomeBinding.inflate(inflater,container,false);

        SliderView sliderView = binding.imageSlider;
        SliderAdapter adapter = new SliderAdapter(this);
        //add as u wish haha
        adapter.addItem(new SliderItem(url1));
        adapter.addItem(new SliderItem(url2));
        adapter.addItem(new SliderItem(url3));
        adapter.addItem(new SliderItem(url4));
        adapter.addItem(new SliderItem(url5));


        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();


        //set articles content
        // Load image from URL using Glide or Picasso
        String  article1="https://sseinitiative.org/wp-content/uploads/2022/03/GenderEqu_report.jpg",
                article2="https://www.paho.org/sites/default/files/styles/max_1500x1500/public/2020-03/gender-equality-927x618.jpg?itok=J0bmc7AD",
                article3="https://www.omfif.org/wp-content/uploads/2023/03/gender-equality.png";
        Glide.with(this)
                .load(article1)
                .into(binding.articleimage1);
        Glide.with(this)
                .load(article2)
                .into(binding.articleimage2);
        Glide.with(this)
                .load(article3)
                .into(binding.articleimage3);

        binding.articlecardview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), webViewPage.class);
                String urlToLoad = "https://sseinitiative.org/all-news/how-exchanges-can-advance-gender-equality-updated-guidance-and-best-practice/"; // Replace with the URL you want to open
                intent.putExtra("url", urlToLoad);
                startActivity(intent);
            }
        });
        binding.articlecardview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), webViewPage.class);
                String urlToLoad = "https://www.paho.org/en/news/6-3-2020-gender-equality-glass-half-full-or-half-empty"; // Replace with the URL you want to open
                intent.putExtra("url", urlToLoad);
                startActivity(intent);
            }
        });
        binding.articlecardview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), webViewPage.class);
                String urlToLoad = "https://www.omfif.org/2023/03/global-economy-cant-afford-to-wait-for-gender-equality/"; // Replace with the URL you want to open
                intent.putExtra("url", urlToLoad);
                startActivity(intent);
            }
        });

        binding.elearningbuttongogogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MainPageElearning.class);
                startActivity(intent);
            }
        });

        binding.healthbuttongogogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), HealthHome.class);
                startActivity(intent);
            }
        });
        return binding.getRoot();
    }


}