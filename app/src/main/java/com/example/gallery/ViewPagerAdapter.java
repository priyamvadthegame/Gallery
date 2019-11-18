package com.example.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ViewPagerAdapter extends PagerAdapter {
    int noOfImages;
    String  imageList[]=new String[noOfImages];
    Context mcontext;
    LayoutInflater inflater;
    ImageView imageView;
    View itemview;
    ViewPagerAdapter(Context mcontext,String[] imageList,int noOfImages)
    {
        this.imageList=imageList;
        this.mcontext=mcontext;
        this.noOfImages=noOfImages;

    }
    @Override
    public int getCount() {
        return noOfImages;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        inflater=(LayoutInflater)mcontext.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        itemview=inflater.inflate(R.layout.singletouchimageview1,container,false);
       imageView =itemview.findViewById(R.id.singletouchimageview1);
       Glide.with(mcontext).load(imageList[position]).into(imageView);
        container.addView(itemview);


        return itemview;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager)container).removeView((View) object);

    }

}
