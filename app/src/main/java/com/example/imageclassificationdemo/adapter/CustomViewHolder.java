package com.example.imageclassificationdemo.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

class CustomViewHolder extends RecyclerView.ViewHolder {
    ViewBinding binding;
    public CustomViewHolder(@NonNull ViewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}