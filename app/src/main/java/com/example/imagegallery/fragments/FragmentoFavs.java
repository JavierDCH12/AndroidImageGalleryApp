package com.example.imagegallery.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.imagegallery.FavoritesManager;
import com.example.imagegallery.R;
import com.example.imagegallery.adapters.GalleryAdapter;
import com.example.imagegallery.databinding.FragmentFragmentoFavsBinding;
import com.example.imagegallery.model.Image;

import java.util.List;


public class FragmentoFavs extends Fragment {

    FragmentFragmentoFavsBinding binding;
    private RecyclerView recyclerView;
    private List<Image> favoriteImages;
    private GalleryAdapter galleryAdapter;


    public FragmentoFavs() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFragmentoFavsBinding.inflate(inflater, container, false);

        favoriteImages = FavoritesManager.getInstance().getFavorites();
        galleryAdapter = new GalleryAdapter(favoriteImages);
        binding.recycFavs.setAdapter(galleryAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        binding.recycFavs.setLayoutManager(gridLayoutManager);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    if (position >= 0 && position < favoriteImages.size()) {
                        Image imageSwiped = favoriteImages.get(position);

                        FavoritesManager.getInstance().removeFavorites(imageSwiped);
                        favoriteImages.remove(position);

                        Toast.makeText(getContext(), "Image removed from favorites", Toast.LENGTH_SHORT).show();

                        galleryAdapter.notifyDataSetChanged();
                    }
                }
            }

        }).attachToRecyclerView(binding.recycFavs);



    }




}//FRAGMENT END