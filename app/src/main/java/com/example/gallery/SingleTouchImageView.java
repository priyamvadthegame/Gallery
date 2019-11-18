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



import java.io.File;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

public class SingleTouchImageView extends AppCompatActivity {
    PhotoViewAttacher mAttacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singletouchimageview);
        ImageView img=findViewById(R.id.singletouchimageview);
        String bundle = getIntent().getExtras().getString("key");
        Bitmap myBitmap = BitmapFactory.decodeFile(bundle);
        img.setImageBitmap(myBitmap);
        mAttacher=new PhotoViewAttacher(img);
        mAttacher.update();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.SingleClickCount=0;
    }

}
