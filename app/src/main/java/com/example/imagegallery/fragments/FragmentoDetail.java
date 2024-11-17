package com.example.imagegallery.fragments;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.imagegallery.R;
import com.example.imagegallery.databinding.FragmentFragmentoDetailBinding;
import com.example.imagegallery.databinding.FragmentFragmentoFavsBinding;
import com.example.imagegallery.model.Image;
import com.example.imagegallery.model.ImageViewModel;
import com.google.android.material.snackbar.Snackbar;


public class FragmentoDetail extends Fragment {
    FragmentFragmentoDetailBinding binding;
    private ImageViewModel imageViewModel;
    private ActivityResultLauncher<String> requestPermissionLauncher;


    public FragmentoDetail() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        startDownload();
                    } else {
                        Toast.makeText(getContext(), R.string.img_permision_denied, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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


        binding.downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    startDownload();
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }

            }
        });









    }//ONVIEWCREATED END

    private void startDownload(){

        Image selectedImage = imageViewModel.getSelectedImage().getValue();

        if(selectedImage != null){
            String url = imageViewModel.getSelectedImage().getValue().getUrls().getRegular();

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            String imageUrl = selectedImage.getUrls().getRegular();
            String filename = "image_"+System.currentTimeMillis()+ "_jpg";
            
            request.setTitle("Downloading image");
            request.setDescription("Downloading image");
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);

            DownloadManager downloadManager = (DownloadManager) requireContext().getSystemService(Context.DOWNLOAD_SERVICE);
            if(downloadManager!= null){
                downloadManager.enqueue(request);
                Snackbar.make(binding.getRoot() ,R.string.img_donwload_start, Snackbar.LENGTH_SHORT).show();

            }else{
                Toast.makeText(getContext(), R.string.img_download_error, Toast.LENGTH_SHORT).show();
            }




        }
    }


}//FRAGMENT END