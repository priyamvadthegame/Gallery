package com.example.gallery;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.FileFileFilter;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

import jp.wasabeef.blurry.Blurry;
import rm.com.longpresspopup.LongPressPopup;
import rm.com.longpresspopup.LongPressPopupBuilder;
import rm.com.longpresspopup.PopupInflaterListener;
import rm.com.longpresspopup.PopupOnHoverListener;
import rm.com.longpresspopup.PopupStateListener;

public class MainActivity extends AppCompatActivity implements PopupInflaterListener,
        PopupStateListener, PopupOnHoverListener
{   public static int SingleClickCount=0;
    public static ArrayList<String> resultIAV = new ArrayList<String>();
    public static int posSingleclick;
    ViewGroup viewGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View decorView = getWindow().getDecorView();
        viewGroup = (ViewGroup) decorView.findViewById(android.R.id.content);
    }
    private static final int req_permissions=123;
    private static final String[] permission={
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int permission_count=2;
    @TargetApi(Build.VERSION_CODES.M)
    private boolean isPermissionDenied()
    {
        for(int i=0;i<permission_count;i++)
        {
            if(checkSelfPermission(permission[i])!= PackageManager.PERMISSION_GRANTED)
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == req_permissions && grantResults.length>0) {
            if (isPermissionDenied())
            {
                ((ActivityManager) Objects.requireNonNull(this.getSystemService(ACTIVITY_SERVICE))).clearApplicationUserData();
                recreate();
            }
            else
            {
                onResume();
                }
        }
    }
    private boolean isGalleryInitialized=false;
    ImageView popupImg;
    GridView gridView;
    GalleryAdapter galleryAdapter=new GalleryAdapter(MainActivity.this);
    LongPressPopup popup;
    boolean LongSingleClickFlag=false;
    List<String> image_list=galleryAdapter.get_image_list();
    int pos;
    @Override
    protected void onResume() {
        super.onResume();

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M&&isPermissionDenied())
        {
            requestPermissions(permission,req_permissions);
        }
        if(!isGalleryInitialized)
        {

            gridView=findViewById(R.id.grid);
            Uri u = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            String[] projection = {MediaStore.Images.ImageColumns.DATA};
            Cursor c = null;
            SortedSet<String> dirList = new TreeSet<String>();

            if (u != null)
            {
                c = managedQuery(u, projection, null, null, null);
            }

            if ((c != null) && (c.moveToFirst()))
            {
                do
                {
                    String tempDir = c.getString(0);
                    tempDir = tempDir.substring(0, tempDir.lastIndexOf("/"));
                    try{
                        dirList.add(tempDir);
                    }
                    catch(Exception e)
                    {

                    }
                }
                while (c.moveToNext());


            }
            String directories[]=new String[dirList.size()];
            dirList.toArray(directories);

            for(int i=0;i<dirList.size();i++)
            {
                File imageDir = new File(directories[i]);
                File[] imageList = imageDir.listFiles();
                Arrays.sort(imageList, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
                if(imageList == null)
                    continue;
                for (File imagePath : imageList) {
                    try {

                        if(imagePath.isDirectory())
                        {
                            imageList = imagePath.listFiles();




                        }
                        if ( imagePath.getName().contains(".jpg")|| imagePath.getName().contains(".JPG")
                                || imagePath.getName().contains(".jpeg")|| imagePath.getName().contains(".JPEG")
                                || imagePath.getName().contains(".png") || imagePath.getName().contains(".PNG")
                                || imagePath.getName().contains(".gif") || imagePath.getName().contains(".GIF")
                                || imagePath.getName().contains(".bmp") || imagePath.getName().contains(".BMP")
                        )
                        {

                            String path= imagePath.getAbsolutePath();
                            resultIAV.add(path);

                        }
                    }
                    //  }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            galleryAdapter.set_data(resultIAV);
            gridView.setAdapter(galleryAdapter);
            final List<String> image_list1=galleryAdapter.get_image_list();
            popup = new LongPressPopupBuilder(this)
                    .setTarget(gridView)
                    //.setPopupView(textView)// Not using this time
                    .setPopupView(R.layout.popup_view,MainActivity.this)
                    .setLongPressDuration(750)
                    .setDismissOnLongPressStop(false)
                    .setDismissOnTouchOutside(false)
                    .setDismissOnBackPressed(false)
                    .setCancelTouchOnDragOutsideView(true)
                    .setOnHoverListener(this)
                    .setPopupListener(this)
                    .setTag("PopupFoo")
                    .setAnimationType(LongPressPopup.ANIMATION_TYPE_FROM_CENTER)
                    .setDismissOnBackPressed(true)
                    .setDismissOnLongPressStop(true)
                    .setDismissOnTouchOutside(true)
                    .build();
            popup.register();

            gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    pos=i;
                    return false;
                }
            });
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                        SingleClickCount++;
                                                        posSingleclick=i;
                                                            if (SingleClickCount <= 1) {


                                                                    Intent intent = new Intent(MainActivity.this, SingleTouchImageView1.class);
                                                                    intent.putExtra("position", posSingleclick);
                                                                    startActivity(intent);

                                                            }
                                                    }

                                                });
            isGalleryInitialized=true;
        }

        }



    @Override
    public void onViewInflated(@Nullable String popupTag, View root) {
        popupImg = (ImageView) root.findViewById(R.id.popup_detail_img);

    }

    @Override
    public void onHoverChanged(View view, boolean isHovered) {

    }




    @Override
    public void onPopupShow(@Nullable String popupTag) {

        Glide.with(MainActivity.this)
                .load(image_list.get(pos))
                .into(popupImg);
        Blurry.with(MainActivity.this).radius(25).sampling(2).onto(viewGroup);

    }

    @Override
    public void onPopupDismiss(@Nullable String popupTag) {
        Blurry.delete(viewGroup);

    }

    @Override
    public void onBackPressed() {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                       finishAffinity();
                       System.exit(0);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        dialog.cancel();
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure to close the app").setCancelable(false).setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
    }



