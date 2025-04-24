package com.example.personalfinance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.personalfinance.R;
import com.example.personalfinance.models.Transaction;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

public class HomeActivity extends AppCompatActivity {
    private TextView incomeText, expenseText, balanceText;
    private DatabaseReference db;
    private FirebaseAuth auth;
    private int totalIncome = 0, totalExpense = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        incomeText = findViewById(R.id.totalIncome);
        expenseText = findViewById(R.id.totalExpense);
        balanceText = findViewById(R.id.totalBalance);

        // Button untuk tambah transaksi
        FloatingActionButton fab = findViewById(R.id.fabAddTransaction);
        fab.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, AddTransactionActivity.class));
        });

        // Inisialisasi Firebase dan listener data
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance()
                .getReference("transactions")
                .child(auth.getUid());

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalIncome = 0;
                totalExpense = 0;
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Transaction t = snap.getValue(Transaction.class);
                    if (t != null) {
                        if ("income".equals(t.type)) totalIncome += t.amount;
                        else totalExpense += t.amount;
                    }
                }
                incomeText.setText("Pemasukan: Rp" + totalIncome);
                expenseText.setText("Pengeluaran: Rp" + totalExpense);
                balanceText.setText("Saldo: Rp" + (totalIncome - totalExpense));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Bisa ditambahkan log error jika perlu
            }
        });
    }
}
