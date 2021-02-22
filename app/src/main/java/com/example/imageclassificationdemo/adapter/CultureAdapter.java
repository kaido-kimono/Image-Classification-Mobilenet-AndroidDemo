package com.example.imageclassificationdemo.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.viewbinding.ViewBinding;

import com.example.imageclassificationdemo.databinding.ItemCultureBinding;
import com.example.imageclassificationdemo.databinding.ItemPlantationBinding;

import java.text.DateFormat;

import models.Culture;
import models.Plantation;

public class CultureAdapter extends ListAdapter<Culture, CustomViewHolder> {

    private final SimpleClickListener<Culture> listener;

    public CultureAdapter(SimpleClickListener<Culture> listener) {
        super(new CultureDiffUtil());
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewBinding view = ItemCultureBinding.inflate(inflater, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        ItemCultureBinding binding = (ItemCultureBinding) holder.binding;
        final Culture culture = getItem(position);

        String dateRecolt = DateFormat.getDateInstance().format(culture.getDateRecolte());
        String dateSemail = DateFormat.getDateInstance().format(culture.getDateSemaille());

        binding.getRoot().setOnClickListener(v -> listener.onItemClick(culture));
        binding.setCulture(culture);
        binding.tvDateRecolte.setText("Date Recolte : " + dateRecolt);
        binding.tvDateSemail.setText("Date Semail : " + dateSemail);
        binding.executePendingBindings();
    }

    static class CultureDiffUtil extends DiffUtil.ItemCallback<Culture> {

        @Override
        public boolean areItemsTheSame(@NonNull Culture oldItem, @NonNull Culture newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Culture oldItem, @NonNull Culture newItem) {
            return oldItem.getUid().equals(newItem.getUid());
        }
    }
}
