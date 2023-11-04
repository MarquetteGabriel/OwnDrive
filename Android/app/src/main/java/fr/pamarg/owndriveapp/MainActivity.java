package fr.pamarg.owndriveapp;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private int currentId, selectTab = 2;
    private NavController navController;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

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

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationViewFiles);

        linearLayoutHome.setOnClickListener(view -> {
            if(selectTab != 2)
            {
                if(selectTab == 1)
                {
                    navController.navigate(R.id.action_treeFragment_to_filesFragment);
                }
                else if (selectTab == 3)
                {
                    navController.navigate(R.id.action_profileFragment_to_filesFragment);
                }
                else
                {
                    navController.navigate(R.id.filesFragment);
                }
            }
        });

        linearLayoutFiles.setOnClickListener(view -> {

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
            if(selectTab == 1)
            {
                navController.navigate(R.id.action_treeFragment_to_profileFragment);
            }
            else if (selectTab == 2)
            {
                navController.navigate(R.id.action_filesFragment_to_profileFragment);
            }
            else
            {
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
                    drawerLayout.setVisibility(View.GONE);
                    linearLayoutFiles.setBackgroundColor(getColor(android.R.color.transparent));
                    linearLayoutProfile.setBackgroundColor(getColor(android.R.color.transparent));
                    imageViewFiles.setImageResource(R.drawable.button_folder);
                    imageViewProfile.setImageResource(R.drawable.button_profile);
                    drawerLayout.closeDrawer(GravityCompat.START);

                    linearLayoutHome.setBackgroundResource(R.drawable.round_background);
                    imageViewHome.setImageResource(R.drawable.button_home_on);
                    textViewHome.setVisibility(View.VISIBLE);

                    //ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    //scaleAnimation.setDuration(300);
                    //scaleAnimation.setFillAfter(true);
                    //linearLayoutHome.startAnimation(scaleAnimation);
                }
            }
            else if (currentId == R.id.treeFragment)
            {

                if(selectTab != 1)
                {
                    selectTab = 1;
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
                }

                }
            else if (currentId == R.id.profileFragment)
            {
                if(selectTab != 3) {
                    selectTab = 3;
                    textViewFiles.setVisibility(View.GONE);
                    textViewHome.setVisibility(View.GONE);
                    drawerLayout.setVisibility(View.GONE);
                    linearLayoutFiles.setBackgroundColor(getColor(android.R.color.transparent));
                    linearLayoutHome.setBackgroundColor(getColor(android.R.color.transparent));
                    imageViewFiles.setImageResource(R.drawable.button_folder);
                    imageViewHome.setImageResource(R.drawable.button_home);
                    drawerLayout.closeDrawer(GravityCompat.START);


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
}