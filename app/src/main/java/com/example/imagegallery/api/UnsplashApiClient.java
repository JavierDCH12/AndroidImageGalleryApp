package com.example.imagegallery.api;

import com.example.imagegallery.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UnsplashApiClient {
    private static final String BASE_URL = BuildConfig.BASE_URL;

    private static UnsplashApiService apiService;

    public static UnsplashApiService getApiService() {
        if (apiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiService = retrofit.create(UnsplashApiService.class);
        }
        return apiService;
    }
}
