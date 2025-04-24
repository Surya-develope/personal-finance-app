package com.example.personalfinance.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.personalfinance.models.Transaction;
import com.example.personalfinance.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTransactionActivity extends AppCompatActivity {
    private EditText amountInput, noteInput;
    private RadioGroup typeGroup;
    private Button saveBtn;
    private DatabaseReference db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        amountInput = findViewById(R.id.inputAmount);
        noteInput = findViewById(R.id.inputNote);
        typeGroup = findViewById(R.id.radioGroupType);
        saveBtn = findViewById(R.id.btnSave);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference("transactions")
                .child(auth.getUid());

        saveBtn.setOnClickListener(v -> {
            int amount = Integer.parseInt(amountInput.getText().toString());
            String note = noteInput.getText().toString();
            String type = typeGroup.getCheckedRadioButtonId() == R.id.radioIncome ? "income" : "expense";
            String id = db.push().getKey();

            Transaction transaction = new Transaction(id, type, amount, note, System.currentTimeMillis());
            db.child(id).setValue(transaction).addOnSuccessListener(aVoid ->
                    Toast.makeText(this, "Tersimpan", Toast.LENGTH_SHORT).show()
            );
        });
    }
}
