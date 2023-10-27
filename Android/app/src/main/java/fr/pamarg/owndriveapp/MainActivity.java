package fr.pamarg.owndriveapp;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class MainActivity extends AppCompatActivity {

    private int selectTab = 2;
    private NavController navController;

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

        linearLayoutHome.setOnClickListener(view -> {
            if(selectTab != 2)
            {
                textViewFiles.setVisibility(View.GONE);
                textViewProfile.setVisibility(View.GONE);
                linearLayoutFiles.setBackgroundColor(getColor(android.R.color.transparent));
                linearLayoutProfile.setBackgroundColor(getColor(android.R.color.transparent));

                linearLayoutHome.setBackgroundResource(R.drawable.round_background);
                textViewHome.setVisibility(View.VISIBLE);

                ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                scaleAnimation.setDuration(200);
                scaleAnimation.setFillAfter(true);
                linearLayoutHome.startAnimation(scaleAnimation);

                selectTab = 2;
                navController.navigate(R.id.filesFragment);
            }
        });

        linearLayoutFiles.setOnClickListener(view -> {
            if(selectTab != 1)
            {
                textViewHome.setVisibility(View.GONE);
                textViewProfile.setVisibility(View.GONE);
                linearLayoutHome.setBackgroundColor(getColor(android.R.color.transparent));
                linearLayoutProfile.setBackgroundColor(getColor(android.R.color.transparent));

                linearLayoutFiles.setBackgroundResource(R.drawable.round_background);
                textViewFiles.setVisibility(View.VISIBLE);

                ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                scaleAnimation.setDuration(200);
                scaleAnimation.setFillAfter(true);
                linearLayoutFiles.startAnimation(scaleAnimation);

                selectTab = 1;
                navController.navigate(R.id.treeFragment);
            }
        });

        linearLayoutProfile.setOnClickListener(view -> {
            if(selectTab != 3)
            {
                textViewFiles.setVisibility(View.GONE);
                textViewHome.setVisibility(View.GONE);
                linearLayoutFiles.setBackgroundColor(getColor(android.R.color.transparent));
                linearLayoutHome.setBackgroundColor(getColor(android.R.color.transparent));

                linearLayoutProfile.setBackgroundResource(R.drawable.round_background);
                textViewProfile.setVisibility(View.VISIBLE);

                ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                scaleAnimation.setDuration(200);
                scaleAnimation.setFillAfter(true);
                linearLayoutProfile.startAnimation(scaleAnimation);

                selectTab = 3;
                navController.navigate(R.id.profileFragment);
            }
        });


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();
    }

    public void onClickFragProfile(View v)
    {

    }

    public void onClickFragFiles(View v)
    {

    }

    public void onClickFragTree(View v)
    {

    }

}