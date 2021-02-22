package com.example.imageclassificationdemo.ui.detailplantation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavAction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.imageclassificationdemo.R;
import com.example.imageclassificationdemo.adapter.CultureAdapter;
import com.example.imageclassificationdemo.adapter.SimpleClickListener;
import com.example.imageclassificationdemo.databinding.FragmentDetailPlantationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import models.Culture;
import models.Plantation;


public class DetailPlantationFragment extends Fragment implements SimpleClickListener<Culture> {

    private FragmentDetailPlantationBinding binding;
    private Plantation plantation;
    private final CultureAdapter mAdapter = new CultureAdapter(this);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        plantation = (Plantation) getArguments().getSerializable("plantation");
        binding = FragmentDetailPlantationBinding.inflate(inflater, container, false);
        binding.setPlantation(plantation);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        displayCulturePlantation();
        displayPlantation(plantation);

        binding.btnAddCulture.setOnClickListener(v -> {
            DetailPlantationFragmentDirections.NavAddCulture action = DetailPlantationFragmentDirections.navAddCulture(plantation.getUid());
            NavController navController = Navigation.findNavController(v);
            navController.navigate(action);
        });
    }

    private void displayCulturePlantation() {
        binding.rvListCulture.setAdapter(mAdapter);
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore.getInstance()
            .collection("utilisateurs")
            .document(userId)
            .collection("plantations")
            .document(plantation.getUid())
            .collection("cultures").addSnapshotListener((value, error) -> {
                if (error != null || value == null) {
                    Log.e("TAG", error.getLocalizedMessage());
                    return;
                }

                List<Culture> plantations = value.toObjects(Culture.class);
                mAdapter.submitList(plantations);
            });
    }

    private void displayPlantation(Plantation plantation) {

    }

    @Override
    public void onItemClick(Culture item) {
        NavController navController = Navigation.findNavController(requireView());
        Bundle bundle = new Bundle();
        bundle.putSerializable("culture", item);
        navController.navigate(R.id.nav_detail_culture, bundle);
    }

    @Override
    public boolean onItemLongClick(Culture item) {
        return false;
    }
}