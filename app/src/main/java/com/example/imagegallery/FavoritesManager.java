package com.example.imagegallery;

import com.example.imagegallery.model.Image;

import java.util.ArrayList;

public class FavoritesManager {

    private static FavoritesManager instance;
    private final ArrayList<Image> fav_images_list;

    private FavoritesManager(){
        fav_images_list =  new ArrayList<Image>();
    }
    //SINGLETON
    public static FavoritesManager getInstance () {
        if (instance == null) {
            instance = new FavoritesManager();
        }
        return instance;
    }


    public void addFavorites(Image image){
        if(!fav_images_list.contains(image)){
            fav_images_list.add(image);

        }

    }

    public void removeFavorites(Image image){
        fav_images_list.remove(image);
    }



}
