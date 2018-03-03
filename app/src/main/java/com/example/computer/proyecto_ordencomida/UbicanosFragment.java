package com.example.computer.proyecto_ordencomida;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class UbicanosFragment extends Fragment implements OnMapReadyCallback {



    GoogleMap map;

    public UbicanosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_ubicanos, container, false);



        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Ubicanos");

        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

            //latitud y longitud cercana a cibertec
        LatLng pp = new LatLng(-11.990403400341158,-77.05947459696807);

        MarkerOptions opcion = new MarkerOptions();
        opcion.position(pp).title("Estas en Eat it");
        map.addMarker(opcion);
        map.moveCamera(CameraUpdateFactory.newLatLng(pp));

    }
}
