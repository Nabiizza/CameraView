package com.example.nabilachowdhury.cameraview;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;



import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends Activity implements View.OnClickListener {
    ImageView imageView;
    EditText name;
    Button submit;
    Button viewAll;
    ArrayList<Bitmap> images;
    ArrayList<String> imagePath;
    ArrayList<String> names;
    MyAdapter imageAdapter;
    Bitmap bitmap;
    String currentImagePath;
    SharedPreferences sharedPref;
    //Map<String, String> map;
    JSONObject jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView =(ImageView)findViewById(R.id.ig_image_view);
        name=(EditText)findViewById(R.id.et_name);
        submit=(Button)findViewById(R.id.bt_submit);
        viewAll=(Button)findViewById(R.id.bt_view_all);
        imageView.setOnClickListener(this);
        submit.setOnClickListener(this);
        viewAll.setOnClickListener(this);
        imageAdapter=new MyAdapter(this,names,images);
        //imageAdapter=new MyAdapter(this,map);
        names=new ArrayList<String>();
        images=new ArrayList<Bitmap>();
        imagePath = new ArrayList<String>();
        //map = new HashMap<>();
        jsonObject = new JSONObject();
        loadMap();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            bitmap = getImage(data);
            imageView.setImageBitmap(bitmap);
            Uri tempUri = getImageUri(this,bitmap);
            File finalFile = new File(getRealPathFromURI(tempUri));
            currentImagePath = getRealPathFromURI(tempUri);
            }
}

    public Bitmap getImage(Intent data) {
        Bitmap image = (Bitmap) data.getExtras().get("data");
        return image;
    }
    @Override
    public void onClick(View v) {
        if(v== imageView ){
            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //camera.putExtra(MediaStore.EXTRA_OUTPUT,finalFile);
            startActivityForResult(camera,0);
            } else if (v==submit){
            try {
                jsonObject.put(name.getText().toString(), currentImagePath);
                Log.v("Json",jsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            name.setText("");
            imageView.setImageResource(R.drawable.camera);

            }
        else if (v == viewAll) {

            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(intent);
            saveMap();

        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    private void saveMap(){
        SharedPreferences pSharedPref = getApplicationContext().getSharedPreferences("MyVariables", Context.MODE_PRIVATE);
        if (pSharedPref != null){
            String jsonString = jsonObject.toString();
            SharedPreferences.Editor editor = pSharedPref.edit();
            //editor.remove("My_map").commit();
            editor.putString("My_map", jsonString);
            Log.v("sabid",jsonObject.toString());
            editor.commit();
        }
    }

        private void loadMap(){
        //Map<String,String> outputMap = new HashMap<String,String>();
        SharedPreferences pSharedPref = getApplicationContext().getSharedPreferences("MyVariables", Context.MODE_PRIVATE);
        try{
            if (pSharedPref != null) {
                String jsonString = pSharedPref.getString("My_map", (new JSONObject()).toString());
                jsonObject = new JSONObject(jsonString);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}

