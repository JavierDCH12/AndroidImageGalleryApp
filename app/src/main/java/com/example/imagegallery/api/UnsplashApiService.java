package com.example.imagegallery.api;

import android.media.Image;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UnsplashApiService {

    @GET("photos")
    Call<List<Image>> getPhotos(
            @Query("client_id") String clientId,
            @Query("per_page") int perPage,
            @Query("page") int page

    );

}
