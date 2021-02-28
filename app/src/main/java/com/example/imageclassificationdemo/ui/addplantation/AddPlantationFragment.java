package com.example.imageclassificationdemo.ui.addplantation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imageclassificationdemo.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.schibstedspain.leku.LocationPickerActivity;

import java.util.UUID;

import models.Plantation;


public class AddPlantationFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_plantation, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == 12) {
                double latitude = data.getDoubleExtra("latitude", 0.0);
                double longitude = data.getDoubleExtra("longitude", 0.0);

                final TextInputEditText edName = requireView().findViewById(R.id.edName);
                final TextInputEditText edSuperficie = requireView().findViewById(R.id.edSupercifie);


                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String plantationUid = UUID.randomUUID().toString();
                DocumentReference docRef = FirebaseFirestore.getInstance()
                        .document("utilisateurs/" + user.getUid() + "/plantations/" + plantationUid);

                Plantation plantation = new Plantation();
                plantation.setUid(docRef.getId());
                plantation.setSuperficie(Double.parseDouble(edSuperficie.getText().toString()));
                plantation.setNom(edName.getText().toString());
                plantation.setLatitude(latitude);
                plantation.setLongitude(longitude);

                docRef.set(plantation);
                NavController navController = Navigation.findNavController(requireView());
                navController.navigateUp();
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MaterialButton button = view.findViewById(R.id.btnAdd);

        button.setOnClickListener(v -> {
            Intent locationPickerIntent = new LocationPickerActivity.Builder()
                .withDefaultLocaleSearchZone()
                .withStreetHidden()
                .withLegacyLayout()
                .withCityHidden()
                .withZipCodeHidden()
                .withSatelliteViewHidden()
                .withGoogleTimeZoneEnabled()
                .withVoiceSearchHidden()
                .withUnnamedRoadHidden()
                .build(getContext());

            startActivityForResult(locationPickerIntent, 12);
        });
    }
}