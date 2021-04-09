package com.example.rafwalletproject.view.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rafwalletproject.R;
import com.example.rafwalletproject.models.Finances;

import java.util.function.Function;

public class FinancesAdapter extends ListAdapter<Finances, FinancesAdapter.ViewHolder> {


    private final Function<Finances, Void> onFinancesClicked;

    public FinancesAdapter(@NonNull DiffUtil.ItemCallback<Finances> diffCallback, Function<Finances, Void> onFinancesClicked) {
        super(diffCallback);
        this.onFinancesClicked = onFinancesClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.finances_list_item, parent, false);
        return new ViewHolder(view, parent.getContext(), position -> {
            Finances finances = getItem(position);
            onFinancesClicked.apply(finances);
            return null;
        });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Finances finances = getItem(position);
        holder.bind(finances);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final Context context;

        public ViewHolder(@NonNull View itemView, Context context, Function<Integer, Void> onItemClicked) {
            super(itemView);
            this.context = context;
            itemView.setOnClickListener(v -> {
                if(getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItemClicked.apply(getAdapterPosition());
                }
            });
        }

        public void bind(Finances finances) {
            ImageView imageView = itemView.findViewById(R.id.imgViewRecycler);
            if(finances.isIncome()) {
                imageView.setColorFilter(Color.GREEN);
            }else{
                imageView.setColorFilter(Color.RED);
            }
            ((TextView)itemView.findViewById(R.id.txtFinancesItemTitle)).setText(finances.getTitle());
            ((TextView)itemView.findViewById(R.id.txtFinancesItemQuantity)).setText(finances.getQuantity().toString());
        }

    }
}
