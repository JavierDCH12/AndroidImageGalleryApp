package com.example.imagegallery.adapters;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imagegallery.databinding.ItemGalleryBinding;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ImagesViewHolder> {

    private final List<Image> images;

    public GalleryAdapter(List<Image> images) {
        this.images = images;
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
        //holder.binding.textViewAuthor.setText(image.getAuthorName());
        //holder.binding.textViewDescription.setText(image.getDescription());



    }

    @Override
    public int getItemCount() {
        return images.size();
    }


    static class ImagesViewHolder extends RecyclerView.ViewHolder{
        ItemGalleryBinding binding;

        public ImagesViewHolder(@NonNull ItemGalleryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }


    }
}
