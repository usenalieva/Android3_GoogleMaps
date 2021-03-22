package com.makhabatusen.googlemaps.data.frameworks.room;


import androidx.room.TypeConverter;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class LatLngConverter {

    @TypeConverter
    public LatLng fromType(String gson) {
        Type type = new TypeToken<LatLng>(){}.getType();
        return new Gson().fromJson(gson, type);
    }

    @TypeConverter
    public String toType(LatLng latLngMap) {
        return new Gson().toJson(latLngMap);
    }


}
