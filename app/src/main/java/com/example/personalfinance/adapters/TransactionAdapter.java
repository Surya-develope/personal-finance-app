package com.example.personalfinance.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.personalfinance.R;
import com.example.personalfinance.models.Transaction;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends ArrayAdapter<Transaction> {

    public TransactionAdapter(Context context, List<Transaction> transactions) {
        super(context, 0, transactions);
    }

    private static class ViewHolder {
        TextView typeText, amountText, noteText, timestampText;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_transaction, parent, false);
            holder = new ViewHolder();
            holder.typeText      = convertView.findViewById(R.id.transactionType);
            holder.amountText    = convertView.findViewById(R.id.transactionAmount);
            holder.noteText      = convertView.findViewById(R.id.transactionNote);
            holder.timestampText = convertView.findViewById(R.id.transactionTimestamp);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Transaction t = getItem(position);
        if (t != null) {
            // Tampilkan tipe (Income / Expense)
            holder.typeText.setText(
                    t.type.equals("income") ? "Pemasukan" : "Pengeluaran"
            );

            // Format jumlah ke Rupiah dengan pemisah ribuan
            holder.amountText.setText(formatCurrency(t.amount));

            // Catatan
            holder.noteText.setText(t.note);

            // Format timestamp ke tanggal yang bisa dibaca
            holder.timestampText.setText(formatDate(t.timestamp));
        }

        return convertView;
    }

    private String formatCurrency(int amount) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        // Hasil akan seperti "Rp10.000,00" — jika ingin tanpa desimal, hapus fractionDigits:
        nf.setMaximumFractionDigits(0);
        return nf.format(amount);
    }

    private String formatDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }
}
