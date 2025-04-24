package com.example.personalfinance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.personalfinance.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText emailInput, passwordInput;
    private Button loginBtn, registerBtn;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        emailInput = findViewById(R.id.inputEmail);
        passwordInput = findViewById(R.id.inputPassword);
        loginBtn = findViewById(R.id.btnLogin);
        registerBtn = findViewById(R.id.btnRegister);

        loginBtn.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(aVoid -> {
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(LoginActivity.this, "Login gagal: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                    );
        });

        registerBtn.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class))
        );
    }
}
