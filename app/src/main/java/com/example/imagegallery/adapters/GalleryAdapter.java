package com.example.imagegallery.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.imagegallery.R;
import com.example.imagegallery.databinding.ItemGalleryBinding;
import com.example.imagegallery.model.Image;
import com.example.imagegallery.model.ImageViewModel;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ImagesViewHolder> {

    private final List<Image> images;
    private final ImageViewModel imageViewModel;
    private final boolean isGallery;

    public GalleryAdapter(List<Image> images, ImageViewModel imageViewModel, boolean isGallery) {
        this.images = images;
        this.imageViewModel = imageViewModel;
        this.isGallery = isGallery;
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

        //IMAGE ATRIBUTES SHOWN BY ITEM
        holder.binding.textViewAuthor.setText(image.getUser().getName());
        //holder.binding.infoIcon.setImageResource(R.drawable.info_icon);
        Glide.with(holder.itemView.getContext())
                .load(image.getUrls().getRegular())
                .into(holder.binding.imageViewThumbnail);

        if (isGallery && imageViewModel.isFavorite(image) && imageViewModel!=null) {//Control the favorite overlay
            holder.binding.imageViewThumbnail.setAlpha(0.5f);
            holder.binding.favoriteOverlayText.setVisibility(View.VISIBLE);
        } else {
            holder.binding.imageViewThumbnail.setAlpha(1.0f);
            holder.binding.favoriteOverlayText.setVisibility(View.GONE);
        }

        holder.binding.infoIcon.setVisibility(View.VISIBLE);


        //INFO CLICK
        holder.binding.infoIcon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                imageViewModel.selectImage(image);
                NavController  navController = Navigation.findNavController(view);

                //CONTROL FROM WHICH FRAGMENT
                if (isGallery) {
                    navController.navigate(R.id.action_fragmentoGallery_to_fragmentoDetail);
                } else {
                    navController.navigate(R.id.action_fragmentoFavs_to_fragmentoDetail);
                }




            }
        });


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
