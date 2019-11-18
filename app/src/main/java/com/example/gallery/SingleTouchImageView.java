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

public class SingleTouchImageView extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singletouchimageview);
        ImageView img=findViewById(R.id.singletouchimageview);
        String bundle = getIntent().getExtras().getString("key");
        Bitmap myBitmap = BitmapFactory.decodeFile(bundle);
        img.setImageBitmap(myBitmap);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.SingleClickCount=0;
    }
}
