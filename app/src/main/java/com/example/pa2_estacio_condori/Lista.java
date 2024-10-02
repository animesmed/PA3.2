package com.example.pa2_estacio_condori;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.pa2_estacio_condori.databinding.ActivityListaBinding;

public class Lista extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityListaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityListaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Cusco and move the camera
        LatLng Ubi1 = new LatLng(-13.516994006035379, -71.98009095351105);
        mMap.addMarker(new MarkerOptions().position(Ubi1).title("Marker in Cusco"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Ubi1));
        CameraPosition cameraPosition = CameraPosition.builder().target(Ubi1).zoom(16).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(@NonNull LatLng latLng) {
                mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador))
                        .title("Estoy aqui").snippet("Este es un lugar recién visitado")
                        .position(latLng).anchor(0.5f,0.7f));
                double Milat1 = latLng.latitude;
                double Milong1 = latLng.longitude;
                Toast.makeText(Lista.this,
                        Milat1 +">"+ Milong1, Toast.LENGTH_LONG).show();
            }
        });

    }
}