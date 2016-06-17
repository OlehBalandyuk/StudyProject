package com.olehbalandyuk.studyproject.application.ui.fragments.current_packet;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.olehbalandyuk.studyproject.R;

import java.util.Collections;
import java.util.List;

class CurrentPacketRecyclerViewAdapter extends RecyclerView.Adapter<CurrentPacketRecyclerViewHolder> {

    private List<CurrentPacketRecyclerViewModel> mData = Collections.emptyList();

    public CurrentPacketRecyclerViewAdapter(List<CurrentPacketRecyclerViewModel> data) {
        mData = data;
    }

    @Override
    public CurrentPacketRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_current_packet_recycler_item, parent, false);
        return new CurrentPacketRecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CurrentPacketRecyclerViewHolder holder, int position) {
        holder.title.setText(mData.get(position).getTitle());
        holder.number.setText(mData.get(position).getNumber());
        holder.genre.setText(mData.get(position).getGenre());
        holder.available.setVisibility(mData.get(position).isAvailable() ? View.VISIBLE : View.INVISIBLE);
        holder.favorite.setVisibility(mData.get(position).isFavorite() ? View.VISIBLE : View.INVISIBLE);
        holder.info.setText(mData.get(position).getInfo());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}