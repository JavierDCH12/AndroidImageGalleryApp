package com.example.imagegallery.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imagegallery.adapters.GalleryAdapter;
import com.example.imagegallery.databinding.FragmentFragmentoGalleryBinding;
import com.example.imagegallery.model.Image;

import java.util.List;


public class FragmentoGallery extends Fragment {

    FragmentFragmentoGalleryBinding binding;
    private GalleryAdapter galleryAdapter;
    private List<Image> images;


    public FragmentoGallery() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFragmentoGalleryBinding.inflate(inflater, container, false);

        //FORMAT AND ADAPTER OF THE RECYCLERVIEW
        binding.recycGallery.setAdapter(galleryAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        binding.recycGallery.setLayoutManager(gridLayoutManager);

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fetchImages();
    }


    private void fetchImages(){



    }


}//FRAGMENT END