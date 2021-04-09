package com.example.rafwalletproject.view.recyclerview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.rafwalletproject.models.Finances;


public class FinancesDiffItemCallback extends DiffUtil.ItemCallback<Finances> {
    @Override
    public boolean areItemsTheSame(@NonNull Finances oldItem, @NonNull Finances newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Finances oldItem, @NonNull Finances newItem) {
        return oldItem.getDescription().equals(newItem.getDescription())
                && oldItem.getTitle().equals(newItem.getTitle())
                && oldItem.getQuantity().equals(newItem.getQuantity());
    }
}
