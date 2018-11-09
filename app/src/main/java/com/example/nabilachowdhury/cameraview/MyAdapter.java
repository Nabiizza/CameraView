package com.example.nabilachowdhury.cameraview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;

public class MyAdapter extends BaseAdapter implements View.OnClickListener{
     ArrayList<String>names;
     ArrayList<Bitmap>images;
     //Map<String,String>map;
     Context context;


    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return names.get(position); //names
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

@Override
public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder viewHolder = new ViewHolder();
    if (convertView ==null) {
        LayoutInflater imageInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = imageInflater.inflate(R.layout.list_view, parent, false);
        viewHolder.cImage = (ImageView) convertView.findViewById(R.id.ig_pic_view);
        viewHolder.cText = (TextView) convertView.findViewById(R.id.tx_name);
        convertView.setTag(viewHolder);

    } else {
        viewHolder=(ViewHolder) convertView.getTag();
    }

    viewHolder.cImage.setImageBitmap(images.get(position));
    viewHolder.cText.setText(names.get(position));
    return convertView;

}

    public void remove(Object item) {

    }
    public class ViewHolder{
        ImageView cImage;
        TextView cText;

    }


    @Override
    public void onClick(View v) {

    }


//    public MyAdapter(Context context, Map<String,String>map){
//        super();
//        this.context=context;
//        this.map=map;
//
//        //this.names=userNames;
//        //this.images=userImage;
//    }

    public MyAdapter(Context context, ArrayList<String>userNames,ArrayList<Bitmap>userImage){
        super();
        this.context=context;
        this.names=userNames;
        this.images=userImage;
        }





}
