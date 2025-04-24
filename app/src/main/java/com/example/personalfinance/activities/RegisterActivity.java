package com.example.personalfinance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.personalfinance.R;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText emailInput, passwordInput;
    private Button registerBtn;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        emailInput = findViewById(R.id.inputEmail);
        passwordInput = findViewById(R.id.inputPassword);
        registerBtn = findViewById(R.id.btnRegister);

        registerBtn.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(aVoid -> {
                        startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                        finish();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(RegisterActivity.this, "Gagal daftar: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                    );
        });
    }
}
