package com.example.imagegallery.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.imagegallery.R;
import com.example.imagegallery.adapters.GalleryAdapter;
import com.example.imagegallery.databinding.FragmentFragmentoFavsBinding;
import com.example.imagegallery.model.Image;
import com.example.imagegallery.model.ImageViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class FragmentoFavs extends Fragment {

    private FragmentFragmentoFavsBinding binding;
    private ImageViewModel imageViewModel;
    private GalleryAdapter galleryAdapter;
    private List<Image> favoriteImages = new ArrayList<>();

    public FragmentoFavs() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFragmentoFavsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // GET VIEWMODEL
        imageViewModel = new ViewModelProvider(requireActivity()).get(ImageViewModel.class);

        galleryAdapter = new GalleryAdapter(favoriteImages, imageViewModel, false);
        binding.recycFavs.setAdapter(galleryAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        binding.recycFavs.setLayoutManager(gridLayoutManager);

        imageViewModel.getFavoriteImages().observe(getViewLifecycleOwner(), new Observer<List<Image>>() {
            @Override
            public void onChanged(List<Image> images) {
                favoriteImages.clear();
                if (images != null && !images.isEmpty()) {
                    favoriteImages.addAll(images);
                    galleryAdapter.notifyDataSetChanged();
                    binding.textViewEmptyFavorites.setVisibility(View.GONE);
                    binding.recycFavs.setVisibility(View.VISIBLE);
                } else {
                    binding.textViewEmptyFavorites.setVisibility(View.VISIBLE);
                    binding.recycFavs.setVisibility(View.GONE);
                }
            }
        });




        Snackbar.make(binding.getRoot(), getString(R.string.swipe_remove), Snackbar.LENGTH_SHORT).show();
        //SWIPE RIGHT TO ELIMINATE
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.RIGHT) {
                    if (position >= 0 && position < favoriteImages.size()) {
                        Image imageSwiped = favoriteImages.get(position);

                        viewHolder.itemView.animate()
                                .alpha(0f)
                                .setDuration(200)
                                .withEndAction(() -> {
                                    imageViewModel.removeFavorite(imageSwiped);
                                    Toast.makeText(getContext(), getString(R.string.swipe_remove), Toast.LENGTH_LONG).show();
                                }).start();
                    }
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    Paint paint = new Paint();
                    Bitmap icon;

                    if (dX > 0) {
                        paint.setColor(ContextCompat.getColor(getContext(), R.color.red));
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.remove_icon);

                        View itemView = viewHolder.itemView;
                        float height = (float) itemView.getBottom() - (float) itemView.getTop();
                        float iconMargin = (height - icon.getHeight()) / 2;
                        float iconTop = itemView.getTop() + iconMargin;
                        float iconLeft = itemView.getLeft() + iconMargin;

                        c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX,
                                (float) itemView.getBottom(), paint);

                        if (icon != null) {
                            c.drawBitmap(icon, iconLeft, iconTop, paint);
                        }
                    }
                }
            }
        }).attachToRecyclerView(binding.recycFavs);
    }
}