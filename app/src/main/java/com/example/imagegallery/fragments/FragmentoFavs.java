package com.example.imagegallery.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

import com.example.imagegallery.FavoritesManager;
import com.example.imagegallery.R;
import com.example.imagegallery.adapters.GalleryAdapter;
import com.example.imagegallery.databinding.FragmentFragmentoFavsBinding;
import com.example.imagegallery.model.Image;

import java.util.List;


public class FragmentoFavs extends Fragment {

    FragmentFragmentoFavsBinding binding;
    private RecyclerView recyclerView;
    private List<Image> favoriteImages;
    private GalleryAdapter galleryAdapter;


    public FragmentoFavs() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFragmentoFavsBinding.inflate(inflater, container, false);

        List<Image> list_favs = FavoritesManager.getInstance().getFavorites();

        if (list_favs.isEmpty()){
            binding.textViewEmptyFavorites.setVisibility(View.VISIBLE);
            binding.recycFavs.setVisibility(View.GONE);

        }else{
            favoriteImages = FavoritesManager.getInstance().getFavorites();
            galleryAdapter = new GalleryAdapter(favoriteImages, true);
            binding.recycFavs.setAdapter(galleryAdapter);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
            binding.recycFavs.setLayoutManager(gridLayoutManager);
            binding.textViewEmptyFavorites.setVisibility(View.GONE);

        }




        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    if (position >= 0 && position < favoriteImages.size()) {
                        Image imageSwiped = favoriteImages.get(position);

                        FavoritesManager.getInstance().removeFavorites(imageSwiped);
                        favoriteImages.remove(position);

                        Toast.makeText(getContext(), "Image removed from favorites", Toast.LENGTH_SHORT).show();

                        galleryAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);


                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                    Paint paint = new Paint();

                    int iconResId;
                    Bitmap icon;

                    if (dX < 0) {
                        paint.setColor(Color.parseColor("#F44336"));
                        iconResId = R.drawable.remove_icon;

                        icon = BitmapFactory.decodeResource(recyclerView.getResources(), iconResId);

                        View itemView = viewHolder.itemView;
                        float height = (float) itemView.getBottom() - (float) itemView.getTop();
                        float iconMargin = (height - icon.getHeight()) / 2;
                        float iconTop = itemView.getTop() + iconMargin;
                        float iconLeft = itemView.getRight() - iconMargin - icon.getWidth();

                        c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom(), paint);

                        c.drawBitmap(icon, iconLeft, iconTop, paint);
                    }




                }



            }



        }).attachToRecyclerView(binding.recycFavs);





    }




}//FRAGMENT END