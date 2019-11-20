package com.example.gallery;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;



import java.io.File;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

public class SingleTouchImageView extends AppCompatActivity {
    PhotoViewAttacher mAttacher;
    String bundle;
    ImageView img;
    int ondeletefalg=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singletouchimageview);
        img=findViewById(R.id.singletouchimageview);
        bundle = getIntent().getExtras().getString("key");
        Bitmap myBitmap = BitmapFactory.decodeFile(bundle);
        img.setImageBitmap(myBitmap);
        mAttacher=new PhotoViewAttacher(img);
        mAttacher.update();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.SingleClickCount=0;
        if(ondeletefalg==1) {
            MainActivity.resultIAV.remove(bundle);
            Intent myIntent = new Intent(SingleTouchImageView.this, MainActivity.class);
            SingleTouchImageView.this.startActivity(myIntent);
            ondeletefalg=0;
        }
    }

    public void share(View view) {
        Intent shareintent=new Intent(Intent.ACTION_SEND);
        shareintent.putExtra(Intent.EXTRA_STREAM,Uri.parse(bundle));
        shareintent.setType("image/jpeg");
        startActivity(Intent.createChooser(shareintent, "Share image using"));
    }

    public void delete(View view) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Bitmap myBitmap = BitmapFactory.decodeFile(MainActivity.resultIAV.get(MainActivity.resultIAV.indexOf(bundle)+1));
                        File file = new File(bundle);
                        file.delete();
                        Toast.makeText(SingleTouchImageView.this,"Image deleted Succesfully",Toast.LENGTH_LONG).show();
                        img.setImageBitmap(myBitmap);
                        ondeletefalg=1;
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure to delete this file?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
}
