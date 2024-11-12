package com.example.imagegallery.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.imagegallery.FavoritesManager;
import com.example.imagegallery.R;
import com.example.imagegallery.databinding.ItemGalleryBinding;
import com.example.imagegallery.model.Image;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ImagesViewHolder> {

    private final List<Image> images;
    private boolean isFavorite;

    public GalleryAdapter(List<Image> images, boolean isFavorite) {
        this.images = images;
        this.isFavorite = isFavorite;
    }

    @NonNull
    @Override
    public GalleryAdapter.ImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemGalleryBinding binding = ItemGalleryBinding.inflate(inflater, parent, false);
        return new ImagesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryAdapter.ImagesViewHolder holder, int position) {
        Image image = images.get(position);

        holder.binding.textViewAuthor.setText(image.getUser().getName());
        holder.binding.infoIcon.setImageResource(R.drawable.info_icon);

        Glide.with(holder.itemView.getContext())
                .load(image.getUrls().getRegular())
                .into(holder.binding.imageViewThumbnail);

        if (!isFavorite && FavoritesManager.getInstance().getFavorites().contains(image)) {
            holder.binding.imageViewThumbnail.setAlpha(0.5f);
            holder.binding.favoriteOverlayText.setVisibility(View.VISIBLE);
        } else {
            holder.binding.imageViewThumbnail.setAlpha(1.0f);
            holder.binding.favoriteOverlayText.setVisibility(View.GONE);

        }
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    static class ImagesViewHolder extends RecyclerView.ViewHolder {
        ItemGalleryBinding binding;

        public ImagesViewHolder(@NonNull ItemGalleryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
