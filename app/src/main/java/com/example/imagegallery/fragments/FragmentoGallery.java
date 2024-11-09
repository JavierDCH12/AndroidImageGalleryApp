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

import com.example.imagegallery.BuildConfig;
import com.example.imagegallery.api.UnsplashApiClient;
import com.example.imagegallery.api.UnsplashApiService;
import com.example.imagegallery.adapters.GalleryAdapter;
import com.example.imagegallery.databinding.FragmentFragmentoGalleryBinding;
import com.example.imagegallery.model.Image;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentoGallery extends Fragment {

    FragmentFragmentoGalleryBinding binding;
    private GalleryAdapter galleryAdapter;
    private List<Image> images = new ArrayList<>();

    private int currentPage=20;
    private final int itemsPerPage = 30;


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
        galleryAdapter = new GalleryAdapter(images);
        binding.recycGallery.setAdapter(galleryAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        binding.recycGallery.setLayoutManager(gridLayoutManager);

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fetchImages();


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback() {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                if(direction == ItemTouchHelper.RIGHT){

                }

            }
        }).attachToRecyclerView(binding.recycGallery);


    }


    private void fetchImages() {
        binding.progressBar.setVisibility(View.VISIBLE);

        UnsplashApiService apiService = UnsplashApiClient.getApiService();
        String clientId = BuildConfig.UNSPLASH_ACCESS_KEY;

        Call<List<Image>> call = apiService.getPhotos(clientId, currentPage, itemsPerPage);

        call.enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                binding.progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    images.clear();
                    images.addAll(response.body());
                    galleryAdapter.notifyDataSetChanged();
                    currentPage++;
                } else {
                    Toast.makeText(getContext(), "API error in GET", Toast.LENGTH_SHORT).show();
                }
            }



            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "API failure in calling", Toast.LENGTH_SHORT).show();
            }
        });
    }


}//FRAGMENT END