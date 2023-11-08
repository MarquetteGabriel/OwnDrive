package fr.pamarg.owndriveapp;

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
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.pamarg.owndriveapp.view.treestructure.DrawerAdapter;
import fr.pamarg.owndriveapp.view.treestructure.DrawerItem;
import fr.pamarg.owndriveapp.view.treestructure.SubItems;

public class MainActivity extends AppCompatActivity implements DrawerAdapter.DrawerItemClickedListener{

    private int currentId, selectTab = 2;
    private NavController navController;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private final List<DrawerItem> drawerItemList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout linearLayoutFiles = findViewById(R.id.linearLayoutFiles);
        final LinearLayout linearLayoutHome = findViewById(R.id.linearLayoutHome);
        final LinearLayout linearLayoutProfile = findViewById(R.id.linearLayoutProfile);

        final ImageView imageViewFiles = findViewById(R.id.imageViewFiles);
        final ImageView imageViewHome = findViewById(R.id.imageViewHome);
        final ImageView imageViewProfile = findViewById(R.id.imageViewProfile);

        final TextView textViewFiles = findViewById(R.id.textViewFiles);
        final TextView textViewHome = findViewById(R.id.textViewHome);
        final TextView textViewProfile = findViewById(R.id.textViewProfile);

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
            /*
            if(selectTab == 2)
            {
                navController.navigate(R.id.action_filesFragment_to_treeFragment);
            }
            else if (selectTab == 3)
            {
                navController.navigate(R.id.action_profileFragment_to_treeFragment);
            }
            else
            {
                navController.navigate(R.id.treeFragment);
            }*/
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
    public void onItemClicked(DrawerItem drawerItem) {
        switch (drawerItem.getName())
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
        else
        {
            super.onBackPressed();
        }
    }

    private void addItems()
    {
        drawerItemList.add(new DrawerItem("Dossier", R.drawable.button_folder));

        DrawerItem item = new DrawerItem("Autre", R.drawable.button_folder, true);
        DrawerItem item2 = new DrawerItem("Autre - Sub", R.drawable.button_folder, true);
        DrawerItem item3 = new DrawerItem("Autre - Sub - Sub", R.drawable.button_folder, true);
        SubItems subItem1 = new SubItems("Sous-élément 1", R.drawable.new_file);
        SubItems subItem2 = new SubItems("Sous-élément 2", R.drawable.new_file);
        SubItems subItem31 = new SubItems("Sous-élément 31", R.drawable.new_file);
        SubItems subItem32 = new SubItems("Sous-élément 32", R.drawable.new_file);
        SubItems subItem33 = new SubItems("Sous-élément 33", R.drawable.new_file);
        SubItems subItem34 = new SubItems("Sous-élément 34", R.drawable.new_file);
        SubItems subItem35 = new SubItems("Sous-élément 35", R.drawable.new_file);
        SubItems subItem36 = new SubItems("Sous-élément 36", R.drawable.new_file);
        SubItems subItem37 = new SubItems("Sous-élément 37", R.drawable.new_file);
        item.addSubItem(subItem1);
        item2.addSubItem(subItem2);
        item3.addSubItem(subItem31);
        item3.addSubItem(subItem32);
        item3.addSubItem(subItem33);
        item3.addSubItem(subItem34);
        item3.addSubItem(subItem35);
        item3.addSubItem(subItem36);
        item3.addSubItem(subItem37);
        item2.addSubFolder(item3);
        item.addSubFolder(item2);

        drawerItemList.add(item);
        drawerItemList.add(new DrawerItem("Autre 2", R.drawable.button_folder));
        drawerItemList.add(new DrawerItem("Dossier", R.drawable.button_folder));

    }


}