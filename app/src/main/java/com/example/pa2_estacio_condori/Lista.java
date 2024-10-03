package com.example.pa2_estacio_condori;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.pa2_estacio_condori.databinding.ActivityListaBinding;

public class Lista extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private ActivityListaBinding binding;
    Button btnUser1, btnUser2;
    public LatLng Lugar1;
    public LatLng Lugar2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityListaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Ubicación primer alumno
        Lugar1 = new LatLng(-13.516994006035379, -71.98009095351105);
        btnUser1 = findViewById(R.id.btnUser1);


        //ubicacion segundo alumno
        Lugar2 = new LatLng(-13.526706939517446, -71.97117890421889);
        btnUser2 = findViewById(R.id.btnUser2);

        //Llamadas de botones
        btnUser1.setOnClickListener(this);
        btnUser2.setOnClickListener(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.setOnMyLocationButtonClickListener(() -> {
                obtenerUbicacion();
                return false;
            });
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,R.raw.miestilo1));

        // Add a marker in Cusco and move the camera
        LatLng Ubi1 = new LatLng(-13.516994006035379, -71.98009095351105);
        mMap.addMarker(new MarkerOptions().position(Ubi1).title("Marker in Cusco"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Ubi1));
        CameraPosition cameraPosition = CameraPosition.builder().target(Ubi1).zoom(16).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        //Rutas primer alumno
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
        //Rutas segundo alumno
        mMap.setOnMapLongClickListener((new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(@NonNull LatLng latLng) {
                mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador))
                        .title("Estoy aqui").snippet("Este es un lugar recién visto")
                        .position(latLng).anchor(0.5f,0.7f));
                double Milat2 = latLng.latitude;
                double Milong2 = latLng.longitude;
                Toast.makeText(Lista.this,Milat2+">"+Milong2,Toast.LENGTH_LONG).show();
            }
        }));

    }

    private void obtenerUbicacion() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            // Obtener la latitud y longitud
                            double latitud = location.getLatitude();
                            double longitud = location.getLongitude();

                            // Mostrar la ubicación en un Toast
                            String mensaje = "Ubicación actual: Latitud: " + latitud + ", Longitud: " + longitud;
                            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();

                            // Mover la cámara a la ubicación actual
                            LatLng ubicacionActual = new LatLng(latitud, longitud);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionActual, 15));
                        } else {
                            // Si no se pudo obtener la ubicación, mostrar un mensaje
                            Toast.makeText(this, "No se pudo obtener la ubicación.", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            // Solicitar permisos de ubicación si no están concedidos
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btnUser1){
            //mMap.setMapStyle(GoogleMap.)
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true); // Habilitar "Mi ubicación" una vez otorgado el permiso
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}