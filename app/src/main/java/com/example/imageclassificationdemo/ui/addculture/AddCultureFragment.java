package com.example.imageclassificationdemo.ui.addculture;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.imageclassificationdemo.R;
import com.example.imageclassificationdemo.databinding.FragmentAddCultureBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import models.Culture;
import models.TypeCulture;


public class AddCultureFragment extends Fragment {

    private final Culture currentCulture = new Culture();
    private FragmentAddCultureBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddCultureBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        String uidPlantation = getArguments().getString("uidPlantation");
        currentCulture.setUidPlantation(uidPlantation);

        super.onViewCreated(view, savedInstanceState);
        getTypeCulture();
        setupFormulaire();
        binding.btnAdd.setOnClickListener(v -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseFirestore.getInstance()
                .collection("utilisateurs")
                .document(userId)
                .collection("plantations")
                .document(uidPlantation)
                .collection("cultures")
                .document(currentCulture.getUid())
                .set(currentCulture)
                .addOnSuccessListener(aVoid -> {
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(binding.btnAdd).navigateUp();
                });
        });
    }

    private void getTypeCulture() {
        CollectionReference colRef = FirebaseFirestore.getInstance().collection("type-cultures");
        colRef.addSnapshotListener((value, error) -> {
            if (error != null || value == null) {
                return;
            }

            List<TypeCulture> listTypeCulture = value.toObjects(TypeCulture.class);
            ArrayAdapter adapter = new ArrayAdapter(requireContext(), R.layout.list_item, listTypeCulture);
            binding.edType.setAdapter(adapter);
            binding.edType.setOnItemClickListener((parent, view, position, id) -> {
                TypeCulture selectedTypeCulture = listTypeCulture.get(position);
                currentCulture.setUidTypeCulture(selectedTypeCulture.getUid());
                currentCulture.setTypeCulture(selectedTypeCulture.getType());
            });
        });
    }

    private void setupFormulaire() {
        binding.edDateRecolt.setOnClickListener( v -> {
            MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Date Recolte")
                    .build();

            picker.addOnPositiveButtonClickListener(selection -> {
                 Date selectedDate = new Date(selection);
                 currentCulture.setDateRecolte(selectedDate);

                Locale locale = new Locale("fr", "FR");
                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
                String date = dateFormat.format(selectedDate);
                binding.edDateRecolt.setText(date);
            });

            picker.show(getParentFragmentManager(), "Picker 1");
        });

        binding.edDateSemail.setOnClickListener( v -> {
            MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Date Semail")
                    .build();

            picker.addOnPositiveButtonClickListener(selection -> {
                Date selectedDate = new Date(selection);
                currentCulture.setDateSemaille(selectedDate);

                Locale locale = new Locale("fr", "FR");
                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
                String date = dateFormat.format(selectedDate);

                binding.edDateSemail.setText(date);
            });

            picker.show(getParentFragmentManager(), "Picker 1");
        });
    }
}