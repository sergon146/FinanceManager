package com.myst3ry.financemanager.ui.adapters.account;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.example.delegateadapter.delegate.BaseDelegateAdapter;
import com.example.delegateadapter.delegate.BaseViewHolder;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.utils.Utils;
import com.myst3ry.financemanager.utils.ViewUtils;
import com.myst3ry.model.Account;
import com.myst3ry.model.AccountType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountAdapterDelegate extends
        BaseDelegateAdapter<AccountAdapterDelegate.ViewHolder, Account> {
    private OnAccountClick listener;

    public AccountAdapterDelegate(OnAccountClick listener) {
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
        return R.layout.item_account;
    }

    @NonNull
    @Override
    protected ViewHolder createViewHolder(View view) {

        ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(v ->
                listener.onAccountClick(holder.getAdapterPosition()));
        return holder;
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

    public interface OnAccountClick {
        void onAccountClick(int position);
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
            if (account.isSelected() && ViewUtils.isTabletUi(itemView)) {
                itemView.setBackgroundColor(ContextCompat
                        .getColor(itemView.getContext(), R.color.item_selected_color));
            } else {
                itemView.setBackgroundColor(ContextCompat
                        .getColor(itemView.getContext(), R.color.white));
            }

            title.setText(account.getTitle());
            amount.setText(Utils.Currency.getAmountTitle(account.getBalance(),
                    account.getCurrencyType()));
            type.setText(Utils.getAccountTypeTitle(itemView.getContext(),
                    account.getAccountType()));
        }
    }
}
