package com.makhabatusen.googlemaps.ui.bottom_sheet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.makhabatusen.googlemaps.R;
import com.makhabatusen.googlemaps.databinding.BottomSheetLayoutBinding;

public class MapBottomSheetDialog extends BottomSheetDialogFragment {

    private BottomSheetLayoutBinding ui;

    private BottomSheetListener mListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ui = BottomSheetLayoutBinding.inflate(getLayoutInflater());
        return ui.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpListeners();
    }

    private void setUpListeners() {
        ui.btnHybrid.setOnClickListener(v-> {
            mListener.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        });

        ui.btnNormal.setOnClickListener(v-> {
            mListener.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        });

        ui.btnPolyline.setOnClickListener(v-> {
            mListener.onPolylineClick(getString(R.string.polyline_has_added));
        });

        ui.btnMarkers.setOnClickListener(v-> {
            mListener.onMarkersClick(getString(R.string.markers_have_added));
        });

        ui.btnClear.setOnClickListener(v-> {
            mListener.onClearClick(getString(R.string.map_has_cleaned));
        });

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
        mListener = (BottomSheetListener) context;}
        catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + getString(R.string.implement_listener));
        }
    }
}
