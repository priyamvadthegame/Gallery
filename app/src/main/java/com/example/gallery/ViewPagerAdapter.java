package com.example.gallery;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ViewPagerAdapter extends PagerAdapter implements android.view.View.OnClickListener{
    static int noOfImages;
    static String  imageList[]=new String[noOfImages];
    static Context mcontext;
    LayoutInflater inflater;
    View itemview;
    PhotoView imageView;
    ImageButton shareimagebutton;
    ImageButton deleteimagebutton;
    static int pos;
    static int ondeletefalg=0;
    static  String filesTobedeleted;

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
        shareimagebutton=itemview.findViewById(R.id.shareimagebutton);
        deleteimagebutton=itemview.findViewById(R.id.deleteimagebutton);
        shareimagebutton.setOnClickListener(this);
        deleteimagebutton.setOnClickListener(this);
        pos=position;
        Glide.with(mcontext).load(imageList[position]).into(imageView);
        container.addView(itemview);
        return itemview;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager)container).removeView((View) object);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.shareimagebutton:
                Intent shareintent=new Intent(Intent.ACTION_SEND);
                shareintent.putExtra(Intent.EXTRA_STREAM, Uri.parse(imageList[pos-1]));
                shareintent.setType("image/jpeg");
                mcontext.startActivity(Intent.createChooser(shareintent, "Share image using"));
                break;
            case R.id.deleteimagebutton:


                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                try {
                                    filesTobedeleted=imageList[pos-1];
                                    File file = new File(filesTobedeleted);
                                    file.delete();
                                    Toast.makeText(mcontext,"Image deleted Succesfully",Toast.LENGTH_LONG).show();
                                    ondeletefalg=1;
                                }
                                catch(UnsupportedOperationException e)
                                {
                                    e.printStackTrace();
                                }

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                dialog.cancel();
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                builder.setMessage("Are you sure,you want to exit?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
                break;
            }
        }

}



