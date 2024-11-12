package com.example.imagegallery.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.imagegallery.R;
import com.example.imagegallery.databinding.FragmentFragmentoDetailBinding;
import com.example.imagegallery.databinding.FragmentFragmentoFavsBinding;
import com.example.imagegallery.model.ImageViewModel;


public class FragmentoDetail extends Fragment {
    FragmentFragmentoDetailBinding binding;
    private ImageViewModel imageViewModel;

    public FragmentoDetail() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFragmentoDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageViewModel = new ViewModelProvider(requireActivity()).get(ImageViewModel.class);
        imageViewModel.getSelectedImage().observe(getViewLifecycleOwner(), image -> {
            if (image != null) {
                binding.userDetail.setText(image.getUser().getName());
                binding.descriptionDetail.setText(image.getDescription());
                Glide.with(this)
                        .load(image.getUrls().getRegular())
                        .into(binding.imgDetail);
            }
        });
    }
}