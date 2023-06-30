package com.example.assigmentgd1.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.assigmentgd1.R;
import com.example.assigmentgd1.adapter.ViewPager2Adapter;

import me.relex.circleindicator.CircleIndicator3;


public class IntroActivity extends AppCompatActivity {

    private TextView tvSkip;
    private ViewPager2 viewPager2;
    private RelativeLayout rlBottom;
    private CircleIndicator3 circleIndicator;
    private ImageView imgNext;
    private ViewPager2Adapter viewPager2Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        initView();
        viewPager2Adapter = new ViewPager2Adapter(this);
        viewPager2.setAdapter(viewPager2Adapter);
        circleIndicator.setViewPager(viewPager2);
    }

    private void initView() {
        tvSkip = findViewById(R.id.tvSkip);
        viewPager2 = findViewById(R.id.viewPager2);
        rlBottom = findViewById(R.id.rlBottom);
        circleIndicator = findViewById(R.id.indicator);
        imgNext = findViewById(R.id.imgNext);

        tvSkip.setOnClickListener(v -> {
            viewPager2.setCurrentItem(2);
        });

        imgNext.setOnClickListener(v -> {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        });


//        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                if (position == 2) {
//                    rlBottom.setVisibility(View.GONE);
//                    tvSkip.setVisibility(View.GONE);
//                } else {
//                    rlBottom.setVisibility(View.VISIBLE);
//                    tvSkip.setVisibility(View.VISIBLE);
//                }
//            }
//        });
    }

}