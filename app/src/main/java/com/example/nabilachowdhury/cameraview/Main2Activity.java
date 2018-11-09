package com.example.nabilachowdhury.cameraview;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Main2Activity extends Activity {

    public MyAdapter imageAdapter;
    public ListView imageListView;
    ArrayList<String> names;
    ArrayList<Bitmap> images;
    ArrayList<String> imageArray;
    Bitmap bitmap;
    public static final String DEFAULT="N/A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        names=new ArrayList<String>();
        images=new ArrayList<Bitmap>();
        imageArray=new ArrayList<String>();
        Iterator<String> keysItr;
        Intent intent = getIntent();
        SharedPreferences pSharedPref = getApplicationContext().getSharedPreferences("MyVariables", Context.MODE_PRIVATE);
        try{
            if (pSharedPref != null){
                String jsonString = pSharedPref.getString("My_map", (new JSONObject()).toString());
                JSONObject jsonObject = new JSONObject(jsonString);
                keysItr = jsonObject.keys();
                while(keysItr.hasNext()) {
                    String key = keysItr.next();
                    String value = (String) jsonObject.get(key);
                    keysItr.remove();
                      names.add(key);
                   //Uri tempUri= Uri.fromFile(new File(value));
                    bitmap = BitmapFactory.decodeFile(value);
                    images.add(bitmap);
                    imageListView = findViewById(R.id.ig_list_view);
                    imageAdapter= new MyAdapter(this,names,images);
                    imageListView.setAdapter(imageAdapter);
                    imageAdapter.notifyDataSetChanged();
                      //imageArray.add(value);
                      //images.add(getFileStreamPath(value));
                      //images.add(getimage(value));
                    //keysItr.remove();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        imageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Fragment fragment;
                fragment= new FragmentImage();


                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                images.get(position).compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                Bundle b = new Bundle();
                b.putByteArray("image",byteArray);

                //UserFragment user = new UserFragment();
                //getFragmentManager().beginTransaction().replace(R.id.main, user).commit();
                //ft.add(R.id.frag_view,fragment);
                fragment.setArguments(b);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                   ft.replace(R.id.main_layout,fragment).commit();
                //ft.show(fragment).commit();
            }
        });




        //imageAdapter=new MyAdapter(This)


        //names=intent.getStringArrayListExtra("UserName");
        //images=intent.getParcelableArrayListExtra("UserImage");



    }



}

