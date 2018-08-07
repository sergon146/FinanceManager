package com.myst3ry.financemanager.ui.adapters;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.delegateadapter.delegate.BaseDelegateAdapter;
import com.example.delegateadapter.delegate.BaseViewHolder;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.utils.Utils;
import com.myst3ry.model.Account;
import com.myst3ry.model.AccountType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountAdapter extends
        BaseDelegateAdapter<AccountAdapter.ViewHolder, Account> {
    private List<Account> accounts = new ArrayList<>();
    private OnAccountClick listener;

    public AccountAdapter(OnAccountClick listener) {
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull View view,
                                    @NonNull Account account,
                                    @NonNull ViewHolder viewHolder) {
        viewHolder.bind(account);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.account_item;
    }

    @NonNull
    @Override
    protected ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public boolean isForViewType(@NonNull List<?> list, int i) {
        Object object = list.get(i);
        if (object instanceof Account) {
            AccountType type = ((Account) object).getAccountType();
            return type.equals(AccountType.DEBIT_CARD) || type.equals(AccountType.CASH);
        } else {
            return false;
        }
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts.clear();
        this.accounts.addAll(accounts);
    }

    public void addAccount(Account account) {
        int newPos = accounts.size();
        accounts.add(account);
    }

    public interface OnAccountClick {
        void onAccountClick(Account account);
    }

    class ViewHolder extends BaseViewHolder {
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
