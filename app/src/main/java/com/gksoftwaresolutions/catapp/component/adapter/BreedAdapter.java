package com.gksoftwaresolutions.catapp.component.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.gksoftwaresolutions.catapp.model.BreedItem;

import org.jetbrains.annotations.NotNull;

public class BreedAdapter extends PagedListAdapter<BreedItem, BreedAdapter.BreedViewHolder> {
    private Context context;
    private IBreedSelectionListener listener;

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

    public BreedAdapter(@NonNull DiffUtil.ItemCallback<BreedItem> diffCallback, Context context, IBreedSelectionListener listener) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BreedAdapter.BreedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BreedAdapter.BreedViewHolder holder, int position) {

    }

    class BreedViewHolder extends RecyclerView.ViewHolder {

        public BreedViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    interface IBreedSelectionListener {
        void onSelectionClickBreed(BreedItem item);
    }
}
