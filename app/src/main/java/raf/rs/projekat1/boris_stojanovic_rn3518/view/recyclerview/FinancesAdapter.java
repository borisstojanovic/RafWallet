package raf.rs.projekat1.boris_stojanovic_rn3518.view.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import raf.rs.projekat1.boris_stojanovic_rn3518.R;
import raf.rs.projekat1.boris_stojanovic_rn3518.models.Finances;

import java.util.function.Function;

public class FinancesAdapter extends ListAdapter<Finances, FinancesAdapter.ViewHolder> {

    private final Function<Finances, Void> onFinancesClicked;
    private final Function<Finances, Void> onDeleteButtonClicked;
    private final Function<Finances, Void> onEditButtonClicked;

    public FinancesAdapter(@NonNull DiffUtil.ItemCallback<Finances> diffCallback, Function<Finances, Void> onFinancesClicked,
                           Function<Finances, Void> onDeleteButtonClicked, Function<Finances, Void> onEditButtonClicked) {
        super(diffCallback);
        this.onFinancesClicked = onFinancesClicked;
        this.onDeleteButtonClicked = onDeleteButtonClicked;
        this.onEditButtonClicked = onEditButtonClicked;
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
        }, pos -> {
            Finances finance = getItem(pos);
            onDeleteButtonClicked.apply(finance);
            return null;
        }, pos -> {
            onEditButtonClicked.apply(getItem(pos));
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

        public ViewHolder(@NonNull View itemView, Context context, Function<Integer, Void> onItemClicked,
                          Function<Integer, Void> onDeleteButtonClicked, Function<Integer, Void> onEditButtonClicked) {
            super(itemView);
            this.context = context;
            Button button = itemView.findViewById(R.id.btnDeleteItem);
            button.setOnClickListener(v -> {
                if(getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onDeleteButtonClicked.apply(getAdapterPosition());
                }
            });

            itemView.findViewById(R.id.btnEditItem).setOnClickListener(v -> {
                if(getAdapterPosition() != RecyclerView.NO_POSITION){
                    onEditButtonClicked.apply(getAdapterPosition());
                }
            });

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
