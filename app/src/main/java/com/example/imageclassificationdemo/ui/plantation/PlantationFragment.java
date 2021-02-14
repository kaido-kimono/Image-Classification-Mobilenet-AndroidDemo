package com.example.imageclassificationdemo.ui.plantation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imageclassificationdemo.R;
import com.example.imageclassificationdemo.adapter.PlantationAdapter;
import com.example.imageclassificationdemo.adapter.SimpleClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        FloatingActionButton fab = view.findViewById(R.id.btnAddPlantation);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.nav_add_plantation);
            }
        });

        setupRecyclerView(view);
    }

    private void setupRecyclerView(View view) {
        List<Plantation> plantations = Arrays.asList(
            new Plantation("P1", "Plantation 1", 2345, 23456),
            new Plantation("P1", "Plantation 3", 2345, 23456),
            new Plantation("P1", "Plantation 2", 2345, 23456),
            new Plantation("P1", "Plantation 3", 2345, 23456),
            new Plantation("P1", "Plantation 15", 2345, 23456),
            new Plantation("P1", "Plantation 41", 2345, 23456),
            new Plantation("P1", "Plantation 41", 2345, 23456),
            new Plantation("P1", "Plantation 41", 2345, 23456),
            new Plantation("P1", "Plantation4 1", 2345, 23456),
            new Plantation("P1", "Plantation 41", 2345, 23456)
        );

        RecyclerView listPlantation = view.findViewById(R.id.rvListPlantation);
        listPlantation.setAdapter(mAdapter);
        mAdapter.submitList(plantations);
    }

    @Override
    public void onItemClick(Plantation item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("plantation", item);
        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(R.id.nav_detail_plantation, bundle);
    }
}