package com.sample.minhnd.tranningapplication2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.io.File;

import static com.sample.minhnd.tranningapplication2.MainActivity.MY_PERMISSIONS_REQUEST_CAMERA;
import static com.sample.minhnd.tranningapplication2.MainActivity.MY_PERMISSIONS_REQUEST_READ;

/**
 * Created by tulh2 on 1/5/18.
 */

public class Camera {
    private String dir;
    private String file;
    private Fragment fragment;
    private int TAKE_PHOTO_CODE = 0;
    private static int count;


    public Camera(){

    }

    public Camera(Fragment fragment){
        this.fragment = fragment;
        dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/";
        final File newdir = new File(dir);
        newdir.mkdirs();
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }


    public String takePicture(){
        if (ContextCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(fragment.getActivity(), new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        }else {
            count = getNumberOfPictures();
            file = dir + count + ".jpg";
            File newFile = new File(file);
            try {
                newFile.createNewFile();
            } catch (Exception e) {
                Toast.makeText(fragment.getActivity().getApplicationContext(), "create file error", Toast.LENGTH_LONG).show();
            }
            Uri ouputFileUri = Uri.fromFile(newFile);
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, ouputFileUri);
            fragment.startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaScanIntent.setData(ouputFileUri);
            fragment.getActivity().sendBroadcast(mediaScanIntent);
        }
        return file;
    }

    public int getNumberOfPictures() {
        int count = 0;
        if ((ContextCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)||
                (ContextCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(fragment.getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ);
        } else {
            final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
            final String orderBy = MediaStore.Images.Media._ID;
            //Stores all the images from the gallery in Cursor
            Cursor cursor = fragment.getActivity().getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                    null, orderBy);
            //Total number of images
            if (cursor != null) {
                count = cursor.getCount();
            }
            cursor.close();
        }
        return count;
    }
}
