package com.olehbalandyuk.studyproject.application.ui.fragments.cabinet;

import android.content.Context;
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

    public interface InteractionListener {

        void showFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (InteractionListener) context;
    }

    @Override
    public void onStart() {
        super.onStart();

        NetworkService.getPackets(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, ">> Method: onCreateView(LayoutInflater, ViewGroup, Bundle)");

        View view = inflater.inflate(R.layout.fragment_cabinet, container, false);

        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.cabinet_recycler_view);

        final CabinetRecyclerViewAdapter adapter = new CabinetRecyclerViewAdapter(loadPackets(), recycler, new CabinetFragmentCallback() {
            @Override
            public void showChannels() {
                mListener.showFragment();
            }
        }, getContext());
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        Log.v(TAG, "<< Method: onCreateView(LayoutInflater, ViewGroup, Bundle)");
        return view;
    }

    private ArrayList<PacketModel> loadPackets() {
        Log.v(TAG, ">> Method: loadPackets()");

        ArrayList<PacketModel> result = new ArrayList<>();

        ArrayList<String[]> packets = DatabaseConnector.loadPacketsFromDB(getActivity());

        for (String[] packet: packets) {
            result.add(new PacketModel(packet));
        }

        Log.v(TAG, ">> Method: loadPackets()");
        return result;
    }

}
