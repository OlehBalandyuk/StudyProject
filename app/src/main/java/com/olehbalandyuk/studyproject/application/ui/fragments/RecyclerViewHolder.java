package com.olehbalandyuk.studyproject.application.ui.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.olehbalandyuk.studyproject.R;

class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public final TextView mId;
    public final TextView mDate;
    public final TextView mTitle;

    RecyclerViewHolder(View itemView) {
        super(itemView);
        mId = (TextView) itemView.findViewById(R.id.fragment_cabinet_packet_id);
        mDate = (TextView) itemView.findViewById(R.id.fragment_cabinet_packet_date);
        mTitle = (TextView) itemView.findViewById(R.id.fragment_cabinet_packet_title);
    }
}