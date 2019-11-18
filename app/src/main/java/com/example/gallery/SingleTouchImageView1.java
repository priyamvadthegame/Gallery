package com.example.gallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;



import java.io.File;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

public class SingleTouchImageView1 extends AppCompatActivity {
    ViewPager viewPager;
    ViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpageractivity);
        viewPager=findViewById(R.id.viewpager);
        List<String> imagelist = getIntent().getExtras().getStringArrayList("imagelist");
        String imagelistArray[]=new String[imagelist.size()];
        imagelist.toArray(imagelistArray);
        adapter=new ViewPagerAdapter(SingleTouchImageView1.this,imagelistArray,imagelistArray.length);
        viewPager.setAdapter(adapter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.SingleClickCount=0;
    }
}
