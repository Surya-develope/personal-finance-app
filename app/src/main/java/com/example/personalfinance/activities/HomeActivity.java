package com.example.personalfinance.activities;

import android.content.Intent;                                 // untuk startActivity
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.personalfinance.R;
import com.example.personalfinance.models.Transaction;        // model Transaction
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        FloatingActionButton fab = findViewById(R.id.fabAddTransaction);

        fab.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, AddTransactionActivity.class))
        );

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setSelectedItemId(R.id.nav_home); // default
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                return true;
            } else if (id == R.id.nav_history) {
                startActivity(new Intent(HomeActivity.this, HistoryActivity.class));
                return true;
            } else if (id == R.id.nav_settings) {
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                return true;
            }
            return false;
        });

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
            }
        });
    }
}
