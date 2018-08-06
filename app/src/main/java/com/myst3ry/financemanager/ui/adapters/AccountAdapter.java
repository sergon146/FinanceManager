package com.myst3ry.financemanager.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.utils.Utils;
import com.myst3ry.model.Account;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {
    private List<Account> accounts = new ArrayList<>();
    private OnAccountClick listener;

    public AccountAdapter(OnAccountClick listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.account_item, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(accounts.get(position));
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts.clear();
        this.accounts.addAll(accounts);
        notifyDataSetChanged();
    }

    public void addAccount(Account account) {
        int newPos = accounts.size();
        accounts.add(account);
        notifyItemInserted(newPos);
    }

    public interface OnAccountClick {
        void onAccountClick(Account account);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.type)
        TextView type;
        @BindView(R.id.amount)
        TextView amount;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Account account) {
            itemView.setOnClickListener(v -> listener.onAccountClick(account));
            title.setText(account.getTitle());
            amount.setText(Utils.Currency.getAmountTitle(account.getBalance(),
                    account.getCurrencyType()));
            type.setText(Utils.getAccountTypeTitle(itemView.getContext(),
                    account.getAccountType()));
        }
    }
}
