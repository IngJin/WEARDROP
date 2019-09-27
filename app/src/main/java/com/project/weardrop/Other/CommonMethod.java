package com.project.weardrop.Other;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CommonMethod {

    /*public static String  ipConfig = "http://192.168.200.151:8989";*/
    public static String ipConfig = "http://112.164.58.217:80"; //로컬 다른곳에서는 못씀
    //public static String  ipConfig = "http://121.148.239.200:80"; // 고정 아이피 어디서든 쓸수있음

    // 네트워크에 연결되어 있는가
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService( Context.CONNECTIVITY_SERVICE );

        return cm.getActiveNetworkInfo() != null;
    }

    // 이미지 로테이트 및 사이즈 변경
    public static Bitmap imageRotateAndResize(String path){ // state 1:insert, 2:update
        BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inSampleSize = 8;

        File file = new File(path);
        if (file != null) {
            // 돌아간 앵글각도 알기
            int rotateAngle = setImageOrientation(file.getAbsolutePath());
            Bitmap bitmapTmp = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

            // 사진 바로 보이게 이미지 돌리기
            Bitmap bitmap = imgRotate(bitmapTmp, rotateAngle);

            // 이미지 돌린것 다시 저장
            //SaveBitmapToFileCache(bitmap, file.getAbsolutePath());

            return bitmap;
        }
        return null;
    }

    // 사진 찍을때 돌린 각도 알아보기 : 가로로 찍는게 기본임
    public static int setImageOrientation(String path){
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int oriention = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        return oriention;
    }

    // 이미지 돌리기
    public static Bitmap imgRotate(Bitmap bitmap, int orientation){

        Matrix matrix = new Matrix();

        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }

    }

    // 새로 생성한 이미지 경로에 overwrite 하기
    public static void SaveBitmapToFileCache(Bitmap bitmap, String strFilePath) {

        File fileCacheItem = new File(strFilePath);

        Log.d("commandMathod:size1 ", fileCacheItem.length() + "");

        OutputStream out = null;

        try
        {
            //fileCacheItem.createNewFile();
            out = new FileOutputStream(fileCacheItem);

            Log.d("commandMathod:size2 ", fileCacheItem.length() + "");

            if(fileCacheItem.length() > 4000000) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, out);
            } else if(fileCacheItem.length() > 3000000) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 25, out);
            } else if(fileCacheItem.length() > 2000000) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 40, out);
            } else if(fileCacheItem.length() > 1000000) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 60, out);
            } else if(fileCacheItem.length() <= 1000000 ) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                out.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

}
