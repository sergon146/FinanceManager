package com.myst3ry.financemanager.ui.balance;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myst3ry.calculations.model.Transaction;
import com.myst3ry.calculations.model.TransactionType;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.utils.Utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.BalanceViewHolder> {

    private List<Transaction> transactionList = new ArrayList<>();
    private boolean isShowWallet = false;

    @NonNull
    @Override
    public BalanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_item, parent, false);
        return new BalanceViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull BalanceViewHolder holder, int position) {
        holder.bind(transactionList.get(position));
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactionList.clear();
        this.transactionList.addAll(transactions);
        notifyDataSetChanged();
    }

    class BalanceViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.category)
        TextView category;
        @BindView(R.id.amount)
        TextView amount;
        @BindView(R.id.date)
        TextView date;

        BalanceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Transaction item) {
            title.setText(Utils.getTransactionTypeTitle(itemView.getContext(),
                    item.getType()));

            int coef;
            int color;
            if (item.getType() == TransactionType.INCOME) {
                coef = 1;
                color = itemView.getResources().getColor(R.color.income);
            } else {
                coef = -1;
                color = itemView.getResources().getColor(R.color.expense);
            }

            String amountTitle = Utils.Currency.getAmountTitle(item.getAmount()
                            .multiply(BigDecimal.valueOf(coef)),
                    item.getCurrencyType());
            amount.setText(amountTitle);
            amount.setTextColor(color);

            date.setText(SimpleDateFormat.getDateTimeInstance().format(new Date()));
            category.setText(item.getCategory());
        }
    }
}