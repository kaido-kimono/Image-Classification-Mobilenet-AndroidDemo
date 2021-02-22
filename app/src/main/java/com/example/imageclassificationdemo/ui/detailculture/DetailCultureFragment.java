package com.example.imageclassificationdemo.ui.detailculture;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imageclassificationdemo.R;
import com.example.imageclassificationdemo.databinding.FragmentDetailCultureBinding;

import models.Culture;
import models.Plantation;


public class DetailCultureFragment extends Fragment {

    private FragmentDetailCultureBinding binding;
    private Culture culture;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        culture = (Culture) getArguments().getSerializable("culture");
        binding = FragmentDetailCultureBinding.inflate(inflater, container, false);
        binding.setCulture(culture);
        return binding.getRoot();
    }
}