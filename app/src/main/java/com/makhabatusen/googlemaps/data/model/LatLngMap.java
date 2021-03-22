package com.makhabatusen.googlemaps.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

@Entity
public class LatLngMap {
    @PrimaryKey (autoGenerate = true)
    private int id;

    private LatLng latLng;


    public LatLngMap(LatLng latLng) {
        this.latLng = latLng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    @Override
    public String toString() {
        return "LatLngMap{" +
                "id=" + id +
                ", latLng=" + latLng +
                '}';
    }
}
