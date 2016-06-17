package com.olehbalandyuk.studyproject.application.ui.fragments.current_packet;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.olehbalandyuk.studyproject.R;
import com.olehbalandyuk.studyproject.application.widgets.Fab;

import java.util.ArrayList;
import java.util.Random;

public class CurrentPacketFragment extends Fragment {
    private static final String TAG = CurrentPacketFragment.class.getSimpleName();

    private Fab mFab;
    private MaterialSheetFab mMaterialFab;

    protected ArrayList<CurrentPacketRecyclerViewModel> mData = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_packet, container, false);

        tempMethod_fillModel();

        initMaterialFab(view);
        handleOverlayItemClick(view);

        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.current_packet_recycler_view);
        setRecyclerViewAdapter(recycler);

        Log.d(TAG, "onCreateView");

        return view;
    }

    private void initMaterialFab(View view) {
        mFab = (Fab) view.findViewById(R.id.fab);
        View sheetView = view.findViewById(R.id.fab_sheet);
        View overlay = view.findViewById(R.id.overlay);
        int sheetColor = getResources().getColor(R.color.colorPrimary);
        int fabColor = getResources().getColor(R.color.colorPrimaryDark);
        mMaterialFab = new MaterialSheetFab<>(mFab, sheetView, overlay, sheetColor, fabColor);
    }

    private void handleOverlayItemClick(View view) {
        handleAllChannelsClick(view);
        handleFavoriteChannelsClick(view);
        handleAvailableChannelsClick(view);
        handleCensoredChannelsClick(view);
    }

    private void handleAllChannelsClick(View view) {
        TextView all = (TextView) view.findViewById(R.id.fab_sheet_item_all);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialFab.hideSheet();
            }
        });
    }

    private void handleFavoriteChannelsClick(View view) {
        TextView favorite = (TextView) view.findViewById(R.id.fab_sheet_item_favorite);
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMaterialFab.hideSheet();
            }
        });
    }

    private void handleAvailableChannelsClick(View view) {
        TextView available = (TextView) view.findViewById(R.id.fab_sheet_item_available);
        available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialFab.hideSheet();
            }
        });
    }

    private void handleCensoredChannelsClick(View view) {
        TextView censored = (TextView) view.findViewById(R.id.fab_sheet_item_censored);
        censored.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialFab.hideSheet();
            }
        });
    }

    private void setRecyclerViewAdapter(RecyclerView recycler) {
        final CurrentPacketRecyclerViewAdapter adapter = new CurrentPacketRecyclerViewAdapter(mData);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    protected void tempMethod_fillModel() {
        Log.v(TAG, ">> Method - fillModel()");
        for (int i = 0; i < 15; i++) {
            final CurrentPacketRecyclerViewModel model = new CurrentPacketRecyclerViewModel();
            Random random = new Random();
            model.setTitle("Channel #" + String.valueOf(i));
            model.setNumber(String.valueOf(i));
            model.setGenre("Some genre");
            model.setInfo("Some info about channel");
            model.setIsAvailable(String.valueOf(random.nextBoolean()));
            model.setIsFavorite(String.valueOf(random.nextBoolean()));
            mData.add(model);
        }

        Log.v(TAG, "<< Method - fillModel()");
    }

}
