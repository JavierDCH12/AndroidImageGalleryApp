package com.example.imagegallery.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.imagegallery.BuildConfig;
import com.example.imagegallery.R;
import com.example.imagegallery.api.UnsplashApiClient;
import com.example.imagegallery.api.UnsplashApiService;
import com.example.imagegallery.adapters.GalleryAdapter;
import com.example.imagegallery.databinding.FragmentFragmentoGalleryBinding;
import com.example.imagegallery.model.Image;
import com.example.imagegallery.model.ImageViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentoGallery extends Fragment {

    FragmentFragmentoGalleryBinding binding;
    private GalleryAdapter galleryAdapter;
    private List<Image> images = new ArrayList<>();
    private ImageViewModel imageViewModel;

    private int currentPage = 5;
    private final int itemsPerPage = 5;
    private boolean isLoading = false;

    public FragmentoGallery() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFragmentoGalleryBinding.inflate(inflater, container, false);



        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageViewModel = new ViewModelProvider(requireActivity()).get(ImageViewModel.class);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        binding.recycGallery.setLayoutManager(gridLayoutManager);
        galleryAdapter = new GalleryAdapter(images, imageViewModel, true);
        binding.recycGallery.setAdapter(galleryAdapter);


        //Swipe warning
        Snackbar.make(binding.getRoot(), getString(R.string.swipe_add), Snackbar.LENGTH_SHORT).show();

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        binding.recycGallery.setLayoutManager(gridLayoutManager);

        //SCROLL TO CONTINUE CALLING THE API
        binding.recycGallery.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0 && !isLoading) {
                    int visibleItemCount = gridLayoutManager.getChildCount();
                    int totalItemCount = gridLayoutManager.getItemCount();
                    int firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        fetchImages();
                    }
                }
            }
        });//ADDONSCROLLLISTENER END

        fetchImages();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    Image imageSwiped = images.get(position);
                    imageViewModel.addFavorite(imageSwiped);
                    Toast.makeText(getContext(), getString(R.string.added_favs), Toast.LENGTH_LONG).show();
                }

                galleryAdapter.notifyItemChanged(position);
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    Paint paint = new Paint();
                    Bitmap icon;

                    if (dX < 0) { // SWIPE LEFT: GREEN COLOR, ADD FAVORITES
                        paint.setColor(ContextCompat.getColor(getContext(), R.color.green));
                        icon = BitmapFactory.decodeResource(recyclerView.getResources(), R.drawable.add_icon);

                        View itemView = viewHolder.itemView;
                        float height = (float) itemView.getBottom() - (float) itemView.getTop();
                        float iconMargin = (height - icon.getHeight()) / 2;
                        float iconTop = itemView.getTop() + iconMargin;
                        float iconRight = itemView.getRight() - iconMargin - icon.getWidth();

                        c.drawRect((float) itemView.getRight(), (float) itemView.getTop(), dX, (float) itemView.getBottom(), paint);

                        if (icon != null) {
                            c.drawBitmap(icon, iconRight, iconTop, paint);
                        }
                    }
                }
            }
        }).attachToRecyclerView(binding.recycGallery);//ITEM TOUCH HELPER END


    }//ON VIEW CREATED END



    private void fetchImages() {
        if (isLoading) return;
        isLoading = true;

        binding.progressBar.setVisibility(View.VISIBLE);

        UnsplashApiService apiService = UnsplashApiClient.getApiService();
        String clientId = BuildConfig.UNSPLASH_ACCESS_KEY;

        Call<List<Image>> call = apiService.getPhotos(clientId, currentPage, itemsPerPage);

        call.enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                binding.progressBar.setVisibility(View.GONE);
                isLoading = false;

                if (response.isSuccessful() && response.body() != null) {
                    images.addAll(response.body());


                    //JSON LOG
                    Gson gson = new Gson();
                    String jsonResponse = gson.toJson(response.body());
                    Log.i("API JSON Response", jsonResponse);

                    galleryAdapter.notifyDataSetChanged();
                    currentPage++;
                } else {
                    Toast.makeText(getContext(), "API error in response GET", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                isLoading = false;
                Toast.makeText(getContext(), "API failure in calling", Toast.LENGTH_SHORT).show();
            }
        });
    }//FETCH END
}