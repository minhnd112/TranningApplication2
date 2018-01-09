package com.sample.minhnd.tranningapplication2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by W10-PRO on 4/1/2018.
 */

public class ListAllUserFragment extends Fragment {
    @BindView(R.id.lstUser)
    RecyclerView listUser;
    private CustomAdapter customAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_list_all_user, container, false);
        ButterKnife.bind(this, v);
        listUser.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        listUser.setHasFixedSize(true);
        listUser.setItemAnimator(new DefaultItemAnimator());
        loadRecycleViewData();
        return v;
    }

    public void loadRecycleViewData(){
        customAdapter = new CustomAdapter(this.getActivity());
        listUser.setAdapter(customAdapter);
    }

    @OnClick(R.id.btnAddUser)
    public void startInsertFragment(View view) {
        InsertFragment insertFragment = new InsertFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, insertFragment).addToBackStack("").commit();
    }
}
