package com.example.gallery;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ViewPager extends AppCompatActivity {
    static androidx.viewpager.widget.ViewPager viewPager;
    ViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpageractivity);

    }

    @Override
    protected void onResume() {
        super.onResume();
        viewPager=findViewById(R.id.viewpager);
        String imagelistArray[]=new String[MainActivity.resultIAV.size()];
        int pos=getIntent().getExtras().getInt("position");
        MainActivity.resultIAV.toArray(imagelistArray);
        adapter=new ViewPagerAdapter(ViewPager.this,imagelistArray,imagelistArray.length);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(pos,true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.SingleClickCount=0;
        if(ViewPagerAdapter.ondeletefalg==1) {
            MainActivity.resultIAV.remove(ViewPagerAdapter.filesTobedeleted);
            Intent myIntent = new Intent(ViewPager.this, MainActivity.class);
            ViewPager.this.startActivity(myIntent);
            ViewPagerAdapter.ondeletefalg=0;
        }
    }

}
