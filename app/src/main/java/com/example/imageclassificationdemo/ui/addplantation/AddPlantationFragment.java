package com.example.imageclassificationdemo.ui.addplantation;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MaterialButton button = view.findViewById(R.id.btnAdd);
        final TextInputEditText edName = view.findViewById(R.id.edName);
        final TextInputEditText edSuperficie = view.findViewById(R.id.edSupercifie);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String plantationUid = UUID.randomUUID().toString();
                DocumentReference docRef = FirebaseFirestore.getInstance()
                        .document("utilisateurs/" + user.getUid() + "/plantations/" + plantationUid);

                Plantation plantation = new Plantation(
                    docRef.getId(),
                    edName.getText().toString(),
                    Double.parseDouble(edSuperficie.getText().toString()),
                    0
                );

                docRef.set(plantation);
                NavController navController = Navigation.findNavController(v);
                navController.navigateUp();
            }
        });
    }
}