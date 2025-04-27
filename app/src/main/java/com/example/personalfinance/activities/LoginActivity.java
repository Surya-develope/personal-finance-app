package com.example.personalfinance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.personalfinance.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText emailInput, passwordInput;
    private Button loginBtn, registerBtn;
    private ImageButton showPasswordBtn;
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
        showPasswordBtn = findViewById(R.id.btnShowPassword);

        showPasswordBtn.setOnClickListener(v -> {
            if (passwordInput.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                passwordInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                showPasswordBtn.setImageResource(R.drawable.ic_eye_off); // Ganti dengan ikon mata terbuka
            } else {
                passwordInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
                showPasswordBtn.setImageResource(R.drawable.ic_eye_off); // Ganti dengan ikon mata tertutup
            }
        });

        loginBtn.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                emailInput.setError("Email tidak boleh kosong");
                emailInput.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                passwordInput.setError("Password tidak boleh kosong");
                passwordInput.requestFocus();
                return;
            }

            loginUser(email, password);
        });

        registerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser(String email, String password) {
        loginBtn.setEnabled(false);
        loginBtn.setText("Memuat...");

        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    loginBtn.setEnabled(true);
                    loginBtn.setText("Masuk");
                    FirebaseUser user = auth.getCurrentUser();
                    if (user != null) {
                        Toast.makeText(LoginActivity.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(e -> {
                    loginBtn.setEnabled(true);
                    loginBtn.setText("Masuk");
                    Toast.makeText(LoginActivity.this, "Login gagal: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}
