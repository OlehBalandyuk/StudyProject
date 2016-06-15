package com.olehbalandyuk.studyproject.application.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.olehbalandyuk.studyproject.R;

import java.util.ArrayList;
import java.util.Date;

public class CabinetFragment extends Fragment {


    private static final String TAG = CabinetFragment.class.getSimpleName();

    protected ArrayList<RecyclerViewModel> mData = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cabinet, container, false);

        tempMethod_fillModel();

        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.cabinet_recycler_view);

        final RecyclerViewAdapter adapter = new RecyclerViewAdapter(mData);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        Log.d(TAG, "onCreateView");

        return view;
    }

    protected void tempMethod_fillModel() {
        Log.v(TAG, ">> Method - fillModel()");
        for (int i = 0; i < 15; i++) {
            final RecyclerViewModel model = new RecyclerViewModel();
            model.setId("#" + String.valueOf(i));
            model.setDate(new Date(System.currentTimeMillis() + i*i*1000*1000*300*i).toString());
            model.setTitle("Test packet #" + i);
            mData.add(model);
        }

        Log.v(TAG, "<< Method - fillModel()");
    }

}
