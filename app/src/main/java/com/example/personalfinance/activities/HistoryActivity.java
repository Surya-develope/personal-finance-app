package com.example.personalfinance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.personalfinance.R;
import com.example.personalfinance.adapters.TransactionAdapter;
import com.example.personalfinance.models.Transaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Transaction> transactionList;
    private TransactionAdapter adapter;
    private DatabaseReference db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Inisialisasi ListView & Adapter
        listView = findViewById(R.id.listViewHistory);
        transactionList = new ArrayList<>();
        adapter = new TransactionAdapter(this, transactionList);
        listView.setAdapter(adapter);

        // Inisialisasi BottomNavigationView
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setSelectedItemId(R.id.nav_history);  // Set tab aktif
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(this, HomeActivity.class));
                finish();
                return true;
            } else if (id == R.id.nav_history) {
                // tetap di sini
                return true;
            } else if (id == R.id.nav_settings) {
                startActivity(new Intent(this, SettingsActivity.class));
                finish();
                return true;
            }
            return false;
        });

        // Inisialisasi Firebase dan ambil data transaksi
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance()
                .getReference("transactions")
                .child(auth.getUid());

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                transactionList.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Transaction t = snap.getValue(Transaction.class);
                    if (t != null) {
                        transactionList.add(t);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // tangani error jika perlu
            }
        });
    }
}
