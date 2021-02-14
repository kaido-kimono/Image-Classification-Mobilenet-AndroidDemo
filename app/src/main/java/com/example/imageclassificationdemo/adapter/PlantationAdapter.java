package com.example.imageclassificationdemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.viewbinding.ViewBinding;

import com.example.imageclassificationdemo.databinding.ItemPlantationBinding;

import models.Plantation;

public class PlantationAdapter extends ListAdapter<Plantation, CustomViewHolder> {

    private final SimpleClickListener<Plantation> listener;

    public PlantationAdapter(SimpleClickListener<Plantation> listener) {
        super(new PlantationDiffUtil());
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewBinding view = ItemPlantationBinding.inflate(inflater, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        ItemPlantationBinding binding = (ItemPlantationBinding) holder.binding;
        final Plantation plantation = getItem(position);

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(plantation);
            }
        });

        binding.setPlantation(plantation);
        binding.executePendingBindings();
    }

    static class PlantationDiffUtil extends DiffUtil.ItemCallback<Plantation> {

        @Override
        public boolean areItemsTheSame(@NonNull Plantation oldItem, @NonNull Plantation newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Plantation oldItem, @NonNull Plantation newItem) {
            return oldItem.getuid().equals(newItem.getuid());
        }
    }
}
