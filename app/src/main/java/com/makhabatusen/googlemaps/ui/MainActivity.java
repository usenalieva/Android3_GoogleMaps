package com.makhabatusen.googlemaps.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.makhabatusen.googlemaps.App;
import com.makhabatusen.googlemaps.R;
import com.makhabatusen.googlemaps.data.model.LatLngMap;
import com.makhabatusen.googlemaps.databinding.ActivityMainBinding;
import com.makhabatusen.googlemaps.ui.bottom_sheet.BottomSheetListener;
import com.makhabatusen.googlemaps.ui.bottom_sheet.MapBottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener,
        BottomSheetListener
{

    private ActivityMainBinding ui;
    private GoogleMap map;
    private final List<LatLng> coordinates = new ArrayList<>();
    private static final int LOCATION_REQUEST_CODE = 101;
    private static final String BOTTOM_SHEET = "bottom_sheet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());

        getSupportMapManager();

        setUpBottomSheet();

    }

    private void setUpBottomSheet() {
        ui.btnBottomSheet.setOnClickListener(v-> {
            MapBottomSheetDialog dialog = new MapBottomSheetDialog();
            dialog.show(getSupportFragmentManager(), BOTTOM_SHEET);
        });
    }


    private void getSupportMapManager() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    private void polylineOptions() {
        map.clear();
        PolylineOptions polylineOptions = new PolylineOptions()
                .addAll(getCoordinates())
                .width(13f)
                .color(Color.BLUE);
        map.addPolyline(polylineOptions);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMapClickListener(this);
        map.setOnMarkerClickListener(this);
        permissions();
//      position();
    }

    private List<LatLng> getCoordinates() {
        for (LatLngMap latLngMap : App.appDataBase.latLngDao().getLatLngList()) {
            coordinates.add(latLngMap.getLatLng());
        }
        return coordinates;
    }

    private void permissions() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new
                    String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);

        }
        map.setMyLocationEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    map.setMyLocationEnabled(true);
                }
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        markerOptions(latLng);
        coordinates.add(latLng);
        App.appDataBase.latLngDao().insertLatLng(new LatLngMap(latLng));
    }

    private void markerOptions(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions()
//              .title("GeekTech")
                .position(latLng)
                .draggable(true)
                .anchor(0.5f, 1f);
//              markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.arrow));
        map.addMarker(markerOptions);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.remove();
        coordinates.remove(marker.getPosition());
        App.appDataBase.latLngDao().deleteLatLng(marker.getPosition());
        return false;
    }

    private void polygonOptions() {
        PolygonOptions polygonOptions = new PolygonOptions()
                .addAll(coordinates)
                .strokeWidth(13f)
                .fillColor(Color.RED);
        map.addPolygon(polygonOptions);

    }

    private void position() {
        CameraPosition cameraPosition = CameraPosition.builder()
                .target(new LatLng(42.8791324, 74.6172347))
                .zoom(16.12f)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 5000, new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, "FINISH", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {

            }
        });
    }

    @Override
    public void setMapType(int mapType) {
        map.setMapType(mapType);
    }

    @Override
    public void onPolylineClick(String message) {
        polylineOptions();
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
//      polygonOptions();
    }

    @Override
    public void onMarkersClick(String message) {
        for (LatLng latLng : getCoordinates()) {
            markerOptions(latLng);
        }
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClearClick(String message) {
        map.clear();
        coordinates.clear();
        App.appDataBase.latLngDao().deleteLatLngList();
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}