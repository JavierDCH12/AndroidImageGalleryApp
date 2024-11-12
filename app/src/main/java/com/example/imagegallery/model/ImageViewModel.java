package com.example.imagegallery.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import java.util.ArrayList;
import java.util.List;

public class ImageViewModel extends ViewModel {

    private final MutableLiveData<Image> selectedImage = new MutableLiveData<Image>();
    private final MutableLiveData<List<Image>> favoriteImages = new MutableLiveData<List<Image>>();

    public ImageViewModel() {
    }

    public void selectImage(Image image) {
        selectedImage.setValue(image);
    }


    public MutableLiveData<Image> getSelectedImage() {
        return selectedImage;
    }

    public void addFavorite(Image image) {
        List<Image> currentFavorites = favoriteImages.getValue();
        if (currentFavorites == null) {
            currentFavorites = new ArrayList<>();
        }
        if (!currentFavorites.contains(image)) {
            currentFavorites.add(image);
            favoriteImages.setValue(currentFavorites);
        }
    }

    public void removeFavorite(Image image) {
        List<Image> currentFavorites = favoriteImages.getValue();
        if (currentFavorites != null && currentFavorites.contains(image)) {
            currentFavorites.remove(image);
            favoriteImages.setValue(currentFavorites);
        }
    }

    public LiveData<List<Image>> getFavoriteImages() {
        return favoriteImages;
    }

    public boolean isFavorite(Image image) {
        List<Image> currentFavorites = favoriteImages.getValue();
        return currentFavorites!= null && currentFavorites.contains(image);
    }


}
