package com.makhabatusen.googlemaps.ui.bottom_sheet;

public interface BottomSheetListener {
    void setMapType(int mapType);
    void onPolylineClick(String message);
    void onMarkersClick(String message);
    void onClearClick(String message);
}
