package com.olehbalandyuk.studyproject.application.ui.fragments.cabinet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.olehbalandyuk.studyproject.R;
import com.olehbalandyuk.studyproject.application.data.database.DatabaseConnector;

import java.util.Collections;
import java.util.List;

class CabinetRecyclerViewAdapter extends RecyclerView.Adapter<CabinetRecyclerViewHolder>{
    private static final String TAG = CabinetRecyclerViewAdapter.class.getSimpleName();

    private List<PacketModel> mData = Collections.emptyList();
    private RecyclerView mRecycler;
    private Context mContext;
    private CabinetFragmentCallback mCallback;

    public CabinetRecyclerViewAdapter(List<PacketModel> data, RecyclerView recycler, CabinetFragmentCallback callback, Context context) {
        Log.v(TAG, ">> Constructor CabinetRecyclerViewAdapter(List<>, RecyclerView, CabinetFragmentCallback, Context");
        mData = data;
        mRecycler = recycler;
        mCallback = callback;
        mContext = context;
        Log.v(TAG, "<< Constructor CabinetRecyclerViewAdapter(List<>, RecyclerView, CabinetFragmentCallback, Context");
    }

    @Override
    public CabinetRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v(TAG, ">> Method: onCreateViewHolder(ViewGroup, int)");

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_cabinet_recycler_item, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mRecycler.getChildAdapterPosition(v);
                showPacketChannels(position);

            }
        });

        Log.v(TAG, "<< Method: onCreateViewHolder(ViewGroup, int)");
        return new CabinetRecyclerViewHolder(view);
    }

    private void showPacketChannels(int position) {
        Log.v(TAG, ">> Method: showPacketChannels(int)");

        String packetTitle = mData.get(position).getTitle();

        if (!DatabaseConnector.isLoggedInPacket(mContext, packetTitle)) {
            //TODO delete next line of code
            Toast.makeText(mContext, "not logged", Toast.LENGTH_SHORT).show();
            authorizeInPacket(position);
            DatabaseConnector.setLoggedInPacket(mContext, packetTitle);
        } else {
            Toast.makeText(mContext, "logged :)", Toast.LENGTH_SHORT).show();
        }

        mCallback.showChannels();

        Log.v(TAG, "<< Method: showPacketChannels(int)");
    }

    private void authorizeInPacket(int position) {
        //future method
    }

    @Override
    public void onBindViewHolder(CabinetRecyclerViewHolder holder, int position) {
        Log.v(TAG, ">> Method: onBindViewHolder(CabinetRecyclerViewHolder, int)");

        holder.id.setText(mData.get(position).getId());
        holder.date.setText(mData.get(position).getDate());
        holder.title.setText(mData.get(position).getTitle());

        Log.v(TAG, "<< Method: onBindViewHolder(CabinetRecyclerViewHolder, int)");
    }

    @Override
    public int getItemCount() {
        Log.v(TAG, ">> << Method: getItemCount()");

        return mData.size();
    }
}