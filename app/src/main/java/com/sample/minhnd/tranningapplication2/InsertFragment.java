package com.sample.minhnd.tranningapplication2;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class InsertFragment extends Fragment {
    @BindView(R.id.btnConfirm)
    ImageButton btnConfirm;
    @BindView(R.id.txtFullName)
    EditText txtFullName;
    @BindView(R.id.btnAddImage)
    de.hdodenhof.circleimageview.CircleImageView btnAddImage;
    @BindView(R.id.txtAccount)
    EditText txtAccount;
    @Nullable
    private String dir;
    private String file;
    private DataModel dataModel = new DataModel();
    private Camera camera;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_insert, container, false);
        ButterKnife.bind(this, v);
        dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/";
        final File newdir = new File(dir);
        newdir.mkdirs();
        camera = new Camera(this);
        return v;
    }

    @OnClick(R.id.btnAddImage)
    public void clickCapture(View v) {
        file = camera.takePicture();
    }


    @OnClick(R.id.btnConfirm)
    public void clickConfirm() {
        String name = txtFullName.getText().toString();
        String account = txtAccount.getText().toString();

        if (name.trim().isEmpty() && account.trim().isEmpty()) {
            showDialog("Missing information: Full name and Account", "Re-enter");
        } else if (name.trim().isEmpty() && !account.trim().isEmpty()) {
            showDialog("Missing information: Full name", "Re-enter");
        } else if (!name.trim().isEmpty() && account.trim().isEmpty()) {
            showDialog("Missing information: Account", "Re-enter");
        } else if (dataModel.findUserByAccount(account) != null) {
            showDialog("Account must be unique", "Re-enter");
        } else {
            showDialog("User's created successfully", "OK");
            User newUser = new User(name, account, file);
            dataModel.addNewUser(newUser);
            InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            ListAllUserFragment listUserFragment = new ListAllUserFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, listUserFragment).commit();
        }

    }

    public void showDialog(String mess, String button) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage(mess);
        builder1.setCancelable(true);
        builder1.setNegativeButton(
                button,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public int getNumberOfPictures() {
        int count = 0;
        final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
        final String orderBy = MediaStore.Images.Media._ID;
        //Stores all the images from the gallery in Cursor
        Cursor cursor = getActivity().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                null, orderBy);
        //Total number of images
        if (cursor != null) {
            count = cursor.getCount();
        }
        cursor.close();
        return count;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (file != null) {
            btnAddImage.setImageBitmap(BitmapFactory.decodeFile(file));
        }
    }
}
