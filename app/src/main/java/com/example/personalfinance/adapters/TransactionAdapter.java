package com.example.personalfinance.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.personalfinance.R;
import com.example.personalfinance.models.Transaction;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private Context context;
    private List<Transaction> transactionList;

    public TransactionAdapter(Context context, List<Transaction> transactionList) {
        this.context = context;
        this.transactionList = transactionList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTransactionTitle, textTransactionAmount, textTransactionDate;

        public ViewHolder(View itemView) {
            super(itemView);
            textTransactionTitle = itemView.findViewById(R.id.textTransactionTitle);
            textTransactionAmount = itemView.findViewById(R.id.textTransactionAmount);
            textTransactionDate = itemView.findViewById(R.id.textTransactionDate);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Transaction t = transactionList.get(position);

        holder.textTransactionTitle.setText(
                t.note.isEmpty() ? (t.type.equals("income") ? "Pemasukan" : "Pengeluaran") : t.note
        );
        holder.textTransactionAmount.setText(formatCurrency(t.amount));
        holder.textTransactionDate.setText(formatDate(t.timestamp));
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    private String formatCurrency(int amount) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        nf.setMaximumFractionDigits(0);
        return nf.format(amount);
    }

    private String formatDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }
}
