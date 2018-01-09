package com.sample.minhnd.tranningapplication2;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by W10-PRO on 4/1/2018.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private List<User> userList;
    private Context context;
    MainActivity activity;
    DataModel dataModel = new DataModel();

    public CustomAdapter(Context context) {
        this.userList = dataModel.getAllUser();
        this.context = context;
        this.activity = (MainActivity) context;
    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CustomAdapter.ViewHolder holder, int position) {
        final User item = userList.get(position);
        holder.txtFullName.setText(item.getFullName());
        holder.txtAccount.setText(item.getAccount());
        if(item.getAvatarUri()!= null) {
            holder.imgAva.setImageBitmap(BitmapFactory.decodeFile(item.getAvatarUri()));
        }
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                builder1.setMessage("Do you want to delete this user?");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dataModel.deleteUser(item);
                        ListAllUserFragment fragment = new ListAllUserFragment();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                    }
                });
                builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserInfoFragment fragment = new UserInfoFragment(item);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("").commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.btnDelete)
        ImageButton btnDelete;
        @BindView(R.id.txtFullName)
        TextView txtFullName;
        @BindView(R.id.txtAccount) TextView txtAccount;
        @BindView(R.id.imgAva) de.hdodenhof.circleimageview.CircleImageView imgAva;
        @BindView(R.id.linearLayout)
        LinearLayout layout;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
