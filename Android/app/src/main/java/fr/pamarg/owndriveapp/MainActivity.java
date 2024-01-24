package fr.pamarg.owndriveapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.pamarg.owndriveapp.model.treeManager.JsonManager;
import fr.pamarg.owndriveapp.model.treeManager.directoryfiles.Folders;
import fr.pamarg.owndriveapp.view.treestructure.DrawerAdapter;
import fr.pamarg.owndriveapp.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity implements DrawerAdapter.DrawerItemClickedListener{

    private int currentId, selectTab = 2;
    private NavController navController;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private final List<Object> drawerItemList = new ArrayList<>();

    MainActivityViewModel mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Request Permissions
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
            return;
        }

        final LinearLayout linearLayoutFiles = findViewById(R.id.linearLayoutFiles);
        final LinearLayout linearLayoutHome = findViewById(R.id.linearLayoutHome);
        final LinearLayout linearLayoutProfile = findViewById(R.id.linearLayoutProfile);

        final ImageView imageViewFiles = findViewById(R.id.imageViewFiles);
        final ImageView imageViewHome = findViewById(R.id.imageViewHome);
        final ImageView imageViewProfile = findViewById(R.id.imageViewProfile);

        final TextView textViewFiles = findViewById(R.id.textViewFiles);
        final TextView textViewHome = findViewById(R.id.textViewHome);
        final TextView textViewProfile = findViewById(R.id.textViewProfile);

        View bottom_navigation_bar = findViewById(R.id.include);

        RecyclerView recyclerView = findViewById(R.id.drawer_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DrawerAdapter drawerAdapter = new DrawerAdapter(this, drawerItemList, this);
        recyclerView.setAdapter(drawerAdapter);

        View headerView = LayoutInflater.from(this).inflate(R.layout.drawer_header, recyclerView, false);
        drawerAdapter.addHeaderView(headerView);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer)
        {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if(currentId == R.id.profileFragment)
                {
                    selectTab = 3;
                    textViewFiles.setVisibility(View.GONE);
                    textViewHome.setVisibility(View.GONE);
                    linearLayoutFiles.setBackgroundColor(getColor(android.R.color.transparent));
                    linearLayoutHome.setBackgroundColor(getColor(android.R.color.transparent));
                    imageViewFiles.setImageResource(R.drawable.button_folder);
                    imageViewHome.setImageResource(R.drawable.button_home);

                    linearLayoutProfile.setBackgroundResource(R.drawable.round_background);
                    imageViewProfile.setImageResource(R.drawable.button_profile_on);
                    textViewProfile.setVisibility(View.VISIBLE);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(300);
                    scaleAnimation.setFillAfter(true);
                    linearLayoutProfile.startAnimation(scaleAnimation);

                }
                else if(currentId == R.id.filesFragment)
                {
                    selectTab = 2;
                    textViewFiles.setVisibility(View.GONE);
                    textViewProfile.setVisibility(View.GONE);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    linearLayoutFiles.setBackgroundColor(getColor(android.R.color.transparent));
                    linearLayoutProfile.setBackgroundColor(getColor(android.R.color.transparent));
                    imageViewFiles.setImageResource(R.drawable.button_folder);
                    imageViewProfile.setImageResource(R.drawable.button_profile);

                    linearLayoutHome.setBackgroundResource(R.drawable.round_background);
                    imageViewHome.setImageResource(R.drawable.button_home_on);
                    textViewHome.setVisibility(View.VISIBLE);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(300);
                    scaleAnimation.setFillAfter(true);
                    linearLayoutHome.startAnimation(scaleAnimation);
                }
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);


        linearLayoutHome.setOnClickListener(view -> {
            if(selectTab != 2)
            {
                if (selectTab == 3)
                {
                    if(drawerLayout.isDrawerOpen(GravityCompat.START))
                    {
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
                    navController.navigate(R.id.action_profileFragment_to_filesFragment);
                }
                else
                {
                    if(drawerLayout.isDrawerOpen(GravityCompat.START))
                    {
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
                    navController.navigate(R.id.filesFragment);
                }
            }
        });

        linearLayoutFiles.setOnClickListener(view -> {

            selectTab = 1;

            JsonManager.getTreeFiles(mainActivityViewModel, mainActivityViewModel.getIpAddress().getValue());
            addItems();
            drawerAdapter.notifyDataSetChanged();

            textViewHome.setVisibility(View.GONE);
            textViewProfile.setVisibility(View.GONE);
            linearLayoutHome.setBackgroundColor(getColor(android.R.color.transparent));
            linearLayoutProfile.setBackgroundColor(getColor(android.R.color.transparent));
            imageViewHome.setImageResource(R.drawable.button_home);
            imageViewProfile.setImageResource(R.drawable.button_profile);

            linearLayoutFiles.setBackgroundResource(R.drawable.round_background);
            imageViewFiles.setImageResource(R.drawable.button_folder_on);
            textViewFiles.setVisibility(View.VISIBLE);

            ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
            scaleAnimation.setDuration(300);
            scaleAnimation.setFillAfter(true);
            linearLayoutFiles.startAnimation(scaleAnimation);
            drawerLayout.openDrawer(GravityCompat.START);
        });

        linearLayoutProfile.setOnClickListener(view -> {
            if (selectTab == 2)
            {
                if(drawerLayout.isDrawerOpen(GravityCompat.START))
                {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                navController.navigate(R.id.action_filesFragment_to_profileFragment);
            }
            else
            {
                if(drawerLayout.isDrawerOpen(GravityCompat.START))
                {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                navController.navigate(R.id.profileFragment);
            }
        });


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();

        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            currentId = navDestination.getId();
            if(currentId == R.id.filesFragment)
            {
                if(selectTab != 2)
                {
                    selectTab = 2;
                    textViewFiles.setVisibility(View.GONE);
                    textViewProfile.setVisibility(View.GONE);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    linearLayoutFiles.setBackgroundColor(getColor(android.R.color.transparent));
                    linearLayoutProfile.setBackgroundColor(getColor(android.R.color.transparent));
                    imageViewFiles.setImageResource(R.drawable.button_folder);
                    imageViewProfile.setImageResource(R.drawable.button_profile);

                    linearLayoutHome.setBackgroundResource(R.drawable.round_background);
                    imageViewHome.setImageResource(R.drawable.button_home_on);
                    textViewHome.setVisibility(View.VISIBLE);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(300);
                    scaleAnimation.setFillAfter(true);
                    linearLayoutHome.startAnimation(scaleAnimation);
                }
            }
            else if (currentId == R.id.profileFragment)
            {
                if(selectTab != 3) {
                    selectTab = 3;
                    textViewFiles.setVisibility(View.GONE);
                    textViewHome.setVisibility(View.GONE);
                    linearLayoutFiles.setBackgroundColor(getColor(android.R.color.transparent));
                    linearLayoutHome.setBackgroundColor(getColor(android.R.color.transparent));
                    imageViewFiles.setImageResource(R.drawable.button_folder);
                    imageViewHome.setImageResource(R.drawable.button_home);



                    linearLayoutProfile.setBackgroundResource(R.drawable.round_background);
                    imageViewProfile.setImageResource(R.drawable.button_profile_on);
                    textViewProfile.setVisibility(View.VISIBLE);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(300);
                    scaleAnimation.setFillAfter(true);
                    linearLayoutProfile.startAnimation(scaleAnimation);
                }
            }

            if(currentId != R.id.loginFragment)
            {
                bottom_navigation_bar.setVisibility(View.VISIBLE);
            }
            else
            {
                bottom_navigation_bar.setVisibility(View.GONE);
            }
        });
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClicked(Folders folders) {

        switch (folders.getName())
        {
            default:
                break;
        }
    }

    @Override
    public void onBackPressed()
    {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if (currentId == R.id.filesFragment)
        {
            finish();
        }
        else
        {
            super.onBackPressed();
        }
    }

    private void addItems()
    {
        drawerItemList.clear();
        Folders treeFolders = mainActivityViewModel.getTreeFolders().getValue();
        if(treeFolders != null)
        {
            drawerItemList.add(treeFolders);
        }
        else
        {
            drawerItemList.add(new Folders("home", true, R.drawable.button_folder));
        }
    }
}