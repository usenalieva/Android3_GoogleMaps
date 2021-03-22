package com.makhabatusen.googlemaps.data.frameworks.room.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.makhabatusen.googlemaps.data.frameworks.room.LatLngConverter;
import com.makhabatusen.googlemaps.data.frameworks.room.dao.LatLngDao;
import com.makhabatusen.googlemaps.data.model.LatLngMap;

@Database(entities = {LatLngMap.class}, version = 1)
@TypeConverters ({LatLngConverter.class})
public abstract class AppDataBase extends RoomDatabase {
    public abstract LatLngDao latLngDao();
}
