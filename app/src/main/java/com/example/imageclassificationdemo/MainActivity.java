package com.example.imageclassificationdemo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;

import models.Utilisateur;

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
        setNavController();
    }

    private void setNavController() {
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                String label = destination.getLabel().toString();
                getSupportActionBar().setTitle(label);
            }
        });
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

                FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
                Utilisateur user = new Utilisateur();
                user.setuid(authUser.getUid());
                user.setuEmail(authUser.getEmail());
                user.setPhotoUrl(authUser.getPhotoUrl().toString());
                user.setuNom(authUser.getDisplayName());

                saveUtilisateur(user);

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

    private void saveUtilisateur(Utilisateur user) {
        FirebaseFirestore.getInstance().collection("utilisateurs")
            .document(user.getuid())
            .set(user)
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            }).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                }
            });

    }
}