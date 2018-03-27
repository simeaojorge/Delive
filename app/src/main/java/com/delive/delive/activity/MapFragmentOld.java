package com.delive.delive.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

import com.delive.delive.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;

public class MapFragmentOld extends SupportMapFragment implements OnMapReadyCallback, LocationSource.OnLocationChangedListener {

    private static final int REQUEST_FINE_LOCATION = 0;
    private GoogleMap mMap;
    private LocationManager locationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        loadMap();
    }

    private void loadMap(){

        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);

        if (checkSelfPermission(getContext(), ACCESS_FINE_LOCATION) != PERMISSION_GRANTED/* && checkSelfPermission(getContext(), ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED*/) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return;
            }
            if (checkSelfPermission(getContext(), ACCESS_FINE_LOCATION) == PERMISSION_GRANTED) {
                return;
            }

            requestPermissions(new String[]{ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);

            return;
        }

        Location currentLocation = locationManager.getLastKnownLocation(provider);

        moveMap(currentLocation.getLatitude(), currentLocation.getLongitude(), 15);

        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        loadMap();
    }

    @Override
    public void onLocationChanged(Location location){


    }

    private void moveMap(double latitude, double longitude, int zoom){

        LatLng latLong = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLong, zoom));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(zoom), 2000, null);
    }
}
