package com.example.chatapp.adapters;

import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatapp.Constants;
import com.example.chatapp.R;
import com.example.chatapp.interfaces.AdapterListener;
import com.example.chatapp.model.Conversation;
import com.example.chatapp.model.Message;
import com.example.chatapp.model.User;

import java.util.ArrayList;

public class RecentUsersAdapter extends RecyclerView.Adapter<RecentUsersAdapter.ViewHolder> {
    private final ArrayList<Conversation> conversations;
    private AdapterListener listener;


    public RecentUsersAdapter(ArrayList<Conversation> conversations) {
        this.conversations = conversations;
    }

    public void setListener(AdapterListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecentUsersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_user_item_row, parent, false);
        return new RecentUsersAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentUsersAdapter.ViewHolder holder, int position) {
        Conversation con = conversations.get(position);
        User data = con.getOtherUser();
        Message lastMess = con.getLastMessage();

        holder.recentUserName.setText(data.getUserName());
        Glide.with(holder.itemView.getContext()).load(Uri.parse(data.getImageUri()))
                .into(holder.recentUserAvatar);
        holder.recentUserLastMess.setText(lastMess.getMessage());
        holder.recentUserTimeStamp.setText(lastMess.getTimeStamp());

        Typeface typeface = holder.itemView.getResources().getFont(R.font.nexa_bold);
        if ((!lastMess.getSeen()) && (lastMess.getSenderUid().equals(data.getUid()))) {
            holder.recentUserLastMess.setTypeface(typeface);
        }

        if (data.getStatus().equals(Constants.ONLINE)) {
            holder.imgOn.setVisibility(View.VISIBLE);
            holder.imgOff.setVisibility(View.GONE);
        } else {
            holder.imgOn.setVisibility(View.GONE);
            holder.imgOff.setVisibility(View.VISIBLE);
        }

        holder.u = data;

    }

    @Override
    public int getItemCount() {
        return conversations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView recentUserAvatar;
        public TextView recentUserName, recentUserLastMess, recentUserTimeStamp;
        public ImageView imgOn;
        public ImageView imgOff;
        public RelativeLayout container;
        public User u;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recentUserAvatar = itemView.findViewById(R.id.recent_user_avatar);
            recentUserName = itemView.findViewById(R.id.recent_user_name);
            recentUserLastMess = itemView.findViewById(R.id.recent_user_last_mess);
            imgOn = itemView.findViewById(R.id.recent_user_img_on);
            imgOff = itemView.findViewById(R.id.recent_user_img_off);
            container = itemView.findViewById(R.id.recent_user_item_container);
            recentUserTimeStamp = itemView.findViewById(R.id.recent_user_time_stamp);
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(u);
                    }
                }
            });

        }
    }
}
