package com.olehbalandyuk.studyproject.application.ui.fragments.cabinet;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.olehbalandyuk.studyproject.R;

class CabinetRecyclerViewHolder extends RecyclerView.ViewHolder {

    public final TextView id;
    public final TextView date;
    public final TextView title;

    CabinetRecyclerViewHolder(View itemView) {
        super(itemView);
        id = (TextView) itemView.findViewById(R.id.fragment_cabinet_packet_id);
        date = (TextView) itemView.findViewById(R.id.fragment_cabinet_packet_date);
        title = (TextView) itemView.findViewById(R.id.fragment_cabinet_packet_title);
    }
}