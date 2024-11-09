package com.example.imagegallery.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.imagegallery.api.UnsplashApiClient;
import com.example.imagegallery.api.UnsplashApiService;
import com.example.imagegallery.adapters.GalleryAdapter;
import com.example.imagegallery.databinding.FragmentFragmentoGalleryBinding;
import com.example.imagegallery.model.Image;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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


    private void fetchImages() {
        // Obtén la instancia de UnsplashApiService
        UnsplashApiService apiService = UnsplashApiClient.getApiService();

        // Obtén la clave de acceso de Unsplash desde BuildConfig
        String clientId = BuildConfig.UNSPLASH_ACCESS_KEY;

        // Realiza la llamada a la API para obtener la lista de imágenes
        Call<List<Image>> call = apiService.getPhotos(clientId, 1, 20);

        call.enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    images.clear();
                    images.addAll(response.body());
                    galleryAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "API error in GET", Toast.LENGTH_SHORT).show();
                }
            }



            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {
                Toast.makeText(getContext(), "API failure in calling", Toast.LENGTH_SHORT).show();
            }
        });
    }


}//FRAGMENT END