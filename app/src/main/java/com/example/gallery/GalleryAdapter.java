package com.example.gallery;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public final class GalleryAdapter extends BaseAdapter{
    private List<String> data=new ArrayList<String>();
    private Context mcntext;
    GalleryAdapter()
    {

    }
    GalleryAdapter(Context context)
    {
        this.mcntext=context;

    }
    void set_data(List<String> data)
    {
        if(this.data.size()>0)
        {
            data.clear();
        }
        this.data.addAll(data);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ImageView img;
        if(view==null)
        {
            img=(ImageView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,viewGroup,false);
        }
        else
        {
            img=(ImageView)view;
        }
        Glide.with(mcntext).load(data.get(i)).centerCrop().into(img);
        return img;
    }
    public  List<String> get_image_list()
    {
      return  data;
    }
}
