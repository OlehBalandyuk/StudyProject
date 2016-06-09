package com.olehbalandyuk.studyproject.application.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.olehbalandyuk.studyproject.R;

public class CabinetFragment extends Fragment {


    private static final String TAG = CabinetFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;

    public CabinetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cabinet, container, false);

        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView");

        return view;
    }

}
