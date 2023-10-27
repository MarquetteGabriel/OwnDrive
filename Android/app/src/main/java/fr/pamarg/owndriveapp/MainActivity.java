package fr.pamarg.owndriveapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class MainActivity extends AppCompatActivity {


    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();
    }

    public void onClickFragProfile(View v)
    {
        navController.navigate(R.id.profileFragment);
    }

    public void onClickFragFiles(View v)
    {
        navController.navigate(R.id.filesFragment);
    }

    public void onClickFragTree(View v)
    {
        navController.navigate(R.id.treeFragment);
    }

}