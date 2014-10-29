package com.nwpu.chenzhongpu.talking;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by chenzhongpu on 10/20/14.
 */
public class TalkUtil {

    public static final String APPKEYID = "100015461";
    public static  final String APPSECRETKEY = "8d985c7460438d6d6b7e07a05022b2cf";

    public static final String ADLISTID = "fc823fb150dc1f1a17d572c8f44b3f33";




   private static boolean isExternalStorageWritable(){

       String state = Environment.getExternalStorageState();
       if(Environment.MEDIA_MOUNTED.equals(state)){
           return true;
       }
       return false;
   }

    public static File saveScreenCap(View view, Context context){

        // get the direcory for public pictures dir

        if(!isExternalStorageWritable()){

            Toast.makeText(context, "无法读取外部存储。",Toast.LENGTH_LONG).show();

            return null;
        }
        File dir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES),"talking");

        if(dir.mkdirs()){

            Log.e("CZP", "Directory not created");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.CHINA);

        File file = new File(dir, sdf.format(new Date()) +".png");

        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();

        Bitmap bitmap = view.getDrawingCache();

        try{

            FileOutputStream out = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

           Toast.makeText(context, "图片保存在"+file.getPath(), Toast.LENGTH_LONG).show();

            out.close();
        }catch (Exception e){

            Log.e("czp","error when save png");
        }

        return file;
    }


    public static String getImagePath(Uri selectedImage, Context context){

        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = context.getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);

        cursor.close();

        return picturePath;

    }


    public static void saveSharedPreferences(Context context, String tag, Object value){


        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();

        if(tag.equals(context.getString(R.string.last_ad_time)))
        {
            Long time = (Long) value;

            editor.putLong(tag, time);

            editor.commit();
        }




    }


    public static Object getSharedPref(Context context, String tag){

        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        if(tag.equals(context.getString(R.string.last_ad_time))){

            Long d = sharedPref.getLong(tag, 1);
            if(d < 5){

               saveSharedPreferences(context,context.getString(R.string.last_ad_time),System.currentTimeMillis());
            }

            return sharedPref.getLong(tag, System.currentTimeMillis());
        }

        return null;

    }


    public static boolean canShowAD(Context context){

        Long lasttime = (Long)getSharedPref(context, context.getString(R.string.last_ad_time));

        Long nowtime = System.currentTimeMillis();

        Log.d("czp",(nowtime - lasttime)+"");

        if(nowtime - lasttime > 1000 * 60 * 10)
        {
            saveSharedPreferences(context, context.getString(R.string.last_ad_time), nowtime);
            return true;
        }else{

            return false;
        }


    }

}
