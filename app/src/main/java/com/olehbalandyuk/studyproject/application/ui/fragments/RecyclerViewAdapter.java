package com.olehbalandyuk.studyproject.application.ui.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.olehbalandyuk.studyproject.R;

import java.util.Collections;
import java.util.List;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<RecyclerViewModel> mData = Collections.emptyList();

    public RecyclerViewAdapter(List<RecyclerViewModel> data) {
        mData = data;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_cabinet_recycler_item, parent, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.mId.setText(mData.get(position).getId());
        holder.mDate.setText(mData.get(position).getDate());
        holder.mTitle.setText(mData.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}