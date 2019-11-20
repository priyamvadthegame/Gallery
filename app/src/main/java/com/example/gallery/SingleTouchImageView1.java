package com.example.gallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;



import java.io.File;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

public class SingleTouchImageView1 extends AppCompatActivity {
    public static ViewPager viewPager;
    ViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpageractivity);
        viewPager=findViewById(R.id.viewpager);
        String imagelistArray[]=new String[MainActivity.resultIAV.size()];
        int pos=getIntent().getExtras().getInt("position");
        MainActivity.resultIAV.toArray(imagelistArray);
        adapter=new ViewPagerAdapter(SingleTouchImageView1.this,imagelistArray,imagelistArray.length);
        viewPager.setAdapter(adapter);
        if(ViewPagerAdapter.ondeletefalg==1)
        viewPager.setCurrentItem(pos+1);
        else
            viewPager.setCurrentItem(pos);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.SingleClickCount=0;
        if(ViewPagerAdapter.ondeletefalg==1) {
            MainActivity.resultIAV.remove(ViewPagerAdapter.filesTobedeleted);
            Intent myIntent = new Intent(SingleTouchImageView1.this, MainActivity.class);
            SingleTouchImageView1.this.startActivity(myIntent);
            ViewPagerAdapter.ondeletefalg=0;
        }
    }

}
