package com.sample.minhnd.tranningapplication2;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {
    public static int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    public static int MY_PERMISSIONS_REQUEST_READ = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListAllUserFragment fragment = new ListAllUserFragment();
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
