package com.olehbalandyuk.studyproject.application.ui.fragments.current_packet;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.olehbalandyuk.studyproject.R;

class CurrentPacketRecyclerViewHolder extends RecyclerView.ViewHolder {

    public final ImageView logo;
    public final ImageView available;
    public final TextView title;
    public final TextView number;
    public final TextView genre;
    public final TextView info;
    public final ImageView favorite;


    CurrentPacketRecyclerViewHolder(View itemView) {
        super(itemView);
        logo = (ImageView) itemView.findViewById(R.id.channel_logo);
        available = (ImageView) itemView.findViewById(R.id.channel_is_available);
        title = (TextView) itemView.findViewById(R.id.channel_title);
        number = (TextView) itemView.findViewById(R.id.channel_number);
        genre = (TextView) itemView.findViewById(R.id.channel_genre);
        info = (TextView) itemView.findViewById(R.id.channel_info);
        favorite = (ImageView) itemView.findViewById(R.id.channel_favorite);
    }
}