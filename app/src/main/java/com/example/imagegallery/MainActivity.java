package com.example.imagegallery;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.imagegallery.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //TOOLBAR

        setSupportActionBar(binding.toolbar);



        //BOTTOM MENU

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(binding.navHost.getId());
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.bottomMenu, navController);

        binding.bottomMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() ==R.id.fragmentoGallery){
                    binding.toolbar.setTitle(getString(R.string.toolbar_gallery_title));
                    binding.toolbar.setSubtitle(getString(R.string.toolbar_gallery_subtitle));
                    navController.navigate(R.id.fragmentoGallery);

                    
                }else if(item.getItemId() ==R.id.fragmentoFavs){
                    binding.toolbar.setTitle(getString(R.string.toolbar_favorites_title));
                    binding.toolbar.setSubtitle(getString(R.string.toolbar_favorites_subtitle));

                    navController.navigate(R.id.fragmentoFavs);

                }else if(item.getItemId() ==R.id.fragmentoCategories){
                    binding.toolbar.setTitle(getString(R.string.toolbar_categories));

                    navController.navigate(R.id.fragmentoCategories);


                }

                return true;
            }
        });




    }//ONCREATE END















}//MAIN END