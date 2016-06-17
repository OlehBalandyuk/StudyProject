package com.olehbalandyuk.studyproject.application.ui.fragments.cabinet;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.olehbalandyuk.studyproject.R;

import java.util.Collections;
import java.util.List;

class CabinetRecyclerViewAdapter extends RecyclerView.Adapter<CabinetRecyclerViewHolder> {

    private List<CabinetRecyclerViewModel> mData = Collections.emptyList();

    public CabinetRecyclerViewAdapter(List<CabinetRecyclerViewModel> data) {
        mData = data;
    }

    @Override
    public CabinetRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_cabinet_recycler_item, parent, false);
        return new CabinetRecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CabinetRecyclerViewHolder holder, int position) {
        holder.id.setText(mData.get(position).getId());
        holder.date.setText(mData.get(position).getDate());
        holder.title.setText(mData.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}