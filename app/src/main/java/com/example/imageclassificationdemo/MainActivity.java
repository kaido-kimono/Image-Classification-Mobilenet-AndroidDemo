package com.example.imageclassificationdemo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        navController = Navigation.findNavController(this, R.id.navController);

        setupBottomNavigationView();
        checkForAuth();
    }

    private void checkForAuth() {
        List<AuthUI.IdpConfig> provider = Arrays.asList(
            new AuthUI.IdpConfig.PhoneBuilder().build(),
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent authIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(provider)
                    .build();

            startActivityForResult(authIntent, 12);
        }
    }

    private void setupBottomNavigationView() {
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 12) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {

                FirebaseAuth.getInstance().getCurrentUser();



            } else {
                if (response == null) {
                    Toast.makeText(this, "Cancel", Toast.LENGTH_LONG).show();
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(this, "No internet " +
                            "11", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }
}