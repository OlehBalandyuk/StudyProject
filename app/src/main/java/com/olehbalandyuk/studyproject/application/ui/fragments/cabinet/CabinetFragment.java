package com.olehbalandyuk.studyproject.application.ui.fragments.cabinet;

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

    private static final int PACKET_ID = 0;
    private static final int PACKET_PASSWORD = 1;
    private static final int PACKET_TITLE = 2;
    private static final int PACKET_DATE_END = 3;
    private static final int PACKET_STATUS = 4;

    protected ArrayList<CabinetRecyclerViewModel> mData = new ArrayList<>();

    @Override
    public void onStart() {
        super.onStart();

        NetworkService.getPackets(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cabinet, container, false);

        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.cabinet_recycler_view);


        final CabinetRecyclerViewAdapter adapter = new CabinetRecyclerViewAdapter(loadPackets());
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        Log.d(TAG, "onCreateView");

        return view;
    }

    private ArrayList<CabinetRecyclerViewModel> loadPackets() {
        ArrayList<CabinetRecyclerViewModel> result = new ArrayList<>();

        ArrayList<String[]> packets = DatabaseConnector.loadPacketsFromDB(getActivity());

        for (String[] packet: packets) {
            final CabinetRecyclerViewModel model = new CabinetRecyclerViewModel();
            model.setId(packet[PACKET_ID]);
            model.setTitle(packet[PACKET_TITLE]);
            model.setDateEnd(packet[PACKET_DATE_END]);
            result.add(model);
        }

        return result;
    }

}
