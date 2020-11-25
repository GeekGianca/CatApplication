package com.gksoftwaresolutions.catapp.component.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gksoftwaresolutions.catapp.R;
import com.gksoftwaresolutions.catapp.databinding.ItemImageCatLayoutBinding;
import com.gksoftwaresolutions.catapp.model.BreedItem;
import com.gksoftwaresolutions.catapp.model.ImageItem;

import org.jetbrains.annotations.NotNull;

public class ImageAdapter extends PagedListAdapter<ImageItem, ImageViewHolder> {
    private final Context context;
    private final IMakeVoteListener listener;

    private static final DiffUtil.ItemCallback<ImageItem> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ImageItem>() {
                @Override
                public boolean areItemsTheSame(ImageItem oldConcert, ImageItem newConcert) {
                    return oldConcert.getId().equals(newConcert.getId());
                }

                @Override
                public boolean areContentsTheSame(ImageItem oldConcert,
                                                  @NotNull ImageItem newConcert) {
                    return oldConcert.equals(newConcert);
                }
            };

    public ImageAdapter(DiffUtil.ItemCallback<ImageItem> diffCallback, Context context, IMakeVoteListener listener) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_image_cat_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        ImageItem item = getItem(position);
        if (item != null)
            holder.bind(item, context, listener);
    }

    public interface IMakeVoteListener {
        void onCreateVote(ImageItem item, int vote);
    }

}

class ImageViewHolder extends RecyclerView.ViewHolder {
    private final ItemImageCatLayoutBinding binding;

    public ImageViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = ItemImageCatLayoutBinding.bind(itemView);
    }

    public void bind(ImageItem item, Context context, ImageAdapter.IMakeVoteListener listener) {
        Glide.with(context)
                .load(item.getUrl())
                .centerCrop()
                .into(binding.imageCat);

        binding.upVote.setOnClickListener(v -> listener.onCreateVote(item, 1));
        binding.downVote.setOnClickListener(v -> listener.onCreateVote(item, 0));

        try {
            if (!item.getBreeds().isEmpty()) {
                binding.breedDescription.setText(item.getBreeds().get(0).getDescription());
            } else {
                binding.breedDescription.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            binding.breedDescription.setVisibility(View.GONE);
        }
    }
}
