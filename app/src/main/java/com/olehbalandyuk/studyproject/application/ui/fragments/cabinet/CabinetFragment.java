package com.olehbalandyuk.studyproject.application.ui.fragments.cabinet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.olehbalandyuk.studyproject.R;
import com.olehbalandyuk.studyproject.application.data.database.DatabaseConnector;
import com.olehbalandyuk.studyproject.application.http.NetworkService;

import java.util.ArrayList;

public class CabinetFragment extends Fragment {
    private static final String TAG = CabinetFragment.class.getSimpleName();

    private InteractionListener mListener;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v(TAG, "anonymous class BroadcastReceiver, Method onReceive()");

            showPackets();

        }
    };

    private RecyclerView mRecycler;

    public interface InteractionListener {

        void showFragment();
    }

    public CabinetFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (InteractionListener) context;
    }

    @Override
    public void onStart() {
        super.onStart();

        loadPackets();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, ">> Method: onCreateView(LayoutInflater, ViewGroup, Bundle)");

        View view = inflater.inflate(R.layout.fragment_cabinet, container, false);


        mRecycler = (RecyclerView) view.findViewById(R.id.cabinet_recycler_view);

        Log.v(TAG, "<< Method: onCreateView(LayoutInflater, ViewGroup, Bundle)");
        return view;
    }

    private void showPackets() {
        final CabinetRecyclerViewAdapter adapter = new CabinetRecyclerViewAdapter(loadPacketsFromDB(), mRecycler, new CabinetFragmentCallback() {
            @Override
            public void showChannels() {
                mListener.showFragment();
            }
        }, getContext());
        mRecycler.setAdapter(adapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        getActivity().unregisterReceiver(mReceiver);
    }

    private ArrayList<PacketModel> loadPacketsFromDB() {
        Log.v(TAG, ">> Method: loadPackets()");

        ArrayList<PacketModel> result = new ArrayList<>();

        ArrayList<String[]> packets = DatabaseConnector.loadPacketsFromDB(getActivity());

        for (String[] packet : packets) {
            result.add(new PacketModel(packet));
        }

        Log.v(TAG, ">> Method: loadPackets()");
        return result;
    }

    private void loadPackets() {
        Log.v(TAG, ">> Method: sendRequest()");

        IntentFilter filter = new IntentFilter(NetworkService.BROADCAST_ACTION);
        getActivity().registerReceiver(mReceiver, filter);

        sendService();

        Log.v(TAG, "<< Method: sendRequest()");
    }

    private void sendService() {
        Log.v(TAG, ">> Method: sendService()");

        NetworkService.getPackets(getActivity());

        Log.v(TAG, "<< Method: sendService()");
    }

}
