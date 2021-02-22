package com.example.imageclassificationdemo.ui.plantation;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.imageclassificationdemo.R;
import com.example.imageclassificationdemo.adapter.PlantationAdapter;
import com.example.imageclassificationdemo.adapter.SimpleClickListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.List;

import models.Plantation;

public class PlantationFragment extends Fragment implements SimpleClickListener<Plantation> {

    private final PlantationAdapter mAdapter = new PlantationAdapter(this);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plantation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView(view);
        getData();
        FloatingActionButton fab = view.findViewById(R.id.btnAddPlantation);
        fab.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.nav_add_plantation);
        });
    }

    private void getData() {



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            FirebaseFirestore.getInstance()
                .collection("utilisateurs/" + user.getUid() + "/plantations")
                .addSnapshotListener((value, error) -> {
                    if (error != null || value == null) {
                        Log.e("TAG", error.getLocalizedMessage());
                        return;
                    }

                    List<Plantation> plantations = value.toObjects(Plantation.class);
                    mAdapter.submitList(plantations);
                });

        }
    }

    private void setupRecyclerView(View view) {
        RecyclerView listPlantation = view.findViewById(R.id.rvListPlantation);
        listPlantation.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(Plantation item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("plantation", item);
        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(R.id.nav_detail_plantation, bundle);
    }

    @Override
    public boolean onItemLongClick(Plantation item) {
        new MaterialAlertDialogBuilder(requireContext())
            .setTitle("Plantation en court")
            .setMessage("Etes vous sur de vouloir cette plantation commme la plantation en cours ?")
            .setPositiveButton("Oui", (dialog, which) -> {
                setCurrentPlantation(item);
            }).setNegativeButton("Non", (dialog, which) -> {
                dialog.dismiss();
            }).show();
        return true;
    }

    private void setCurrentPlantation(Plantation item) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore.getInstance()
            .collection("utilisateurs")
            .document(userId)
            .collection("plantations")
            .document("current-plantation")
            .set(item)
            .addOnSuccessListener(aVoid -> {
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show();
            });

    }
}