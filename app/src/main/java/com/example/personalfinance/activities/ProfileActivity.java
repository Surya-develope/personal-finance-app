package com.example.personalfinance.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.personalfinance.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    private TextView emailText, uidText;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        emailText = findViewById(R.id.userEmail);
        uidText   = findViewById(R.id.userUid);

        if (user != null) {
            emailText.setText("Email: " + user.getEmail());
            uidText.setText("User ID: " + user.getUid());
        }
    }
}
