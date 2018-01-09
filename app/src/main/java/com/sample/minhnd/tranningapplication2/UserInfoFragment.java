package com.sample.minhnd.tranningapplication2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class UserInfoFragment extends Fragment {
    @BindView(R.id.btnEdit) ImageButton btnEdit;
    @BindView(R.id.btnConfirm)  ImageButton btnConfirm;
    @BindView(R.id.btnAddImage) de.hdodenhof.circleimageview.CircleImageView btnAddImage;
    @BindView(R.id.txtAccount) EditText txtAccount;
    @BindView(R.id.txtFullName) EditText txtFullName;
    private static Camera camera;
    private User user;
    private boolean infoChanged;
    private DataModel dataModel;
    private String file;
    private String name;
    public UserInfoFragment() {
    }

    @SuppressLint("ValidFragment")
    public UserInfoFragment(User user) {
        camera = new Camera(this);
        this.infoChanged = false;
        this.user = user;
        dataModel = new DataModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_info, container, false);
        ButterKnife.bind(this, v);
        file = user.getAvatarUri();
        txtFullName.setText(user.getFullName());
        txtAccount.setText(user.getAccount());
        btnAddImage.setEnabled(false);
        txtFullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                infoChanged = true;
            }
        });
        return v;
    }

    @OnClick(R.id.btnEdit)
    public void editEnable(){
        btnAddImage.setEnabled(true);
        txtFullName.setEnabled(true);
    }

    @OnClick(R.id.btnAddImage)
    public void editAvatar(){
        file =camera.takePicture();
        infoChanged = true;
    }

    @OnClick(R.id.btnConfirm)
    public void confirm() {
        if(txtFullName.getText().toString().trim().isEmpty()){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
            builder1.setMessage("Missing information: Full name");
            builder1.setCancelable(true);
            builder1.setNegativeButton("Re-enter", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog alertDialog = builder1.create();
            alertDialog.show();
        }else if (infoChanged == true) {
            showDialog("Do you want to change user info");
        }
        else{
            ListAllUserFragment listUserFragment = new ListAllUserFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, listUserFragment).commit();
        }


    }

    public void showDialog(String mess) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage(mess);
        builder1.setCancelable(true);
        builder1.setNegativeButton(
                "no",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        ListAllUserFragment listUserFragment = new ListAllUserFragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, listUserFragment).commit();
                    }
                });
        builder1.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dataModel.editAvatar(file, user.getAccount());
                dataModel.editName(txtFullName.getText().toString(), user.getAccount());
                InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                ListAllUserFragment listUserFragment = new ListAllUserFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, listUserFragment).commit();
            }
        });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (user.getAvatarUri() != null && file == null) {
            btnAddImage.setImageBitmap(BitmapFactory.decodeFile(user.getAvatarUri()));
        }
        else if(file != null){
            btnAddImage.setImageBitmap(BitmapFactory.decodeFile(file));
        }
    }


}
