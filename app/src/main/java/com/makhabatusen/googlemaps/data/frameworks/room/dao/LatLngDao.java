package com.makhabatusen.googlemaps.data.frameworks.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.google.android.gms.maps.model.LatLng;
import com.makhabatusen.googlemaps.data.model.LatLngMap;

import java.util.List;

@Dao
public interface LatLngDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
     void insertLatLng (LatLngMap latLngMap);

    @Query("DELETE FROM LatLngMap WHERE latLng= :latLngMap")
    void deleteLatLng(LatLng latLngMap);

    @Query("SELECT * FROM LatLngMap")
    List<LatLngMap> getLatLngList();

    @Query("DELETE FROM LatLngMap")
    void deleteLatLngList();

}
