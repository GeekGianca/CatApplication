package com.gksoftwaresolutions.catapp.component.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.gksoftwaresolutions.catapp.R;
import com.gksoftwaresolutions.catapp.databinding.ItemBreedCatLayoutBinding;
import com.gksoftwaresolutions.catapp.model.BreedItem;

import org.jetbrains.annotations.NotNull;

public class BreedAdapter extends PagedListAdapter<BreedItem, BreedViewHolder> {
    private final Context context;
    private final IBreedSelectionListener listener;

    private static DiffUtil.ItemCallback<BreedItem> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<BreedItem>() {
                @Override
                public boolean areItemsTheSame(BreedItem oldConcert, BreedItem newConcert) {
                    return oldConcert.getId().equals(newConcert.getId());
                }

                @Override
                public boolean areContentsTheSame(BreedItem oldConcert,
                                                  @NotNull BreedItem newConcert) {
                    return oldConcert.equals(newConcert);
                }
            };

    public BreedAdapter(DiffUtil.ItemCallback<BreedItem> diffCallback, Context context, IBreedSelectionListener listener) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BreedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BreedViewHolder(LayoutInflater.from(context).inflate(R.layout.item_breed_cat_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BreedViewHolder holder, int position) {
        BreedItem item = getItem(position);
        if (item != null)
            holder.bind(item, listener);
    }

    public interface IBreedSelectionListener {
        void onSelectionClickBreed(BreedItem item);
    }
}

class BreedViewHolder extends RecyclerView.ViewHolder {
    private ItemBreedCatLayoutBinding binding;

    public BreedViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = ItemBreedCatLayoutBinding.bind(itemView);
    }

    public void bind(BreedItem item, BreedAdapter.IBreedSelectionListener listener) {
        binding.breed.setText(String.format("Breed: %s", item.getName()));
        binding.origin.setText(String.format("Origin: %s", item.getOrigin()));
        binding.contentBreed.setOnClickListener(v -> listener.onSelectionClickBreed(item));
    }
}
