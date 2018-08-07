package com.myst3ry.financemanager.ui.adapters;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.delegateadapter.delegate.BaseDelegateAdapter;
import com.example.delegateadapter.delegate.BaseViewHolder;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.utils.formatter.balance.BalanceFormatterFactory;
import com.myst3ry.model.FeedAccount;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedAccountAdapterDelegate
        extends BaseDelegateAdapter<FeedAccountAdapterDelegate.ViewHolder, FeedAccount> {
    private final OnFeedClickListener listener;
    private BalanceFormatterFactory formatterFactory = new BalanceFormatterFactory();

    public FeedAccountAdapterDelegate(OnFeedClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull View view,
                                    @NonNull FeedAccount FeedAccount,
                                    @NonNull ViewHolder viewHolder) {
        viewHolder.bind(FeedAccount);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_account_feed;
    }

    @NonNull
    @Override
    protected ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public boolean isForViewType(@NonNull List<?> list, int i) {
        return list.get(i) instanceof FeedAccount;
    }


    public interface OnFeedClickListener {
        void onFeedClick();
    }

    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.main_balance)
        TextView mainBalance;
        @BindView(R.id.additional_balance)
        TextView additionalBalance;
        @BindView(R.id.count)
        TextView count;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(FeedAccount account) {
            mainBalance.setText(formatterFactory
                    .create(account.getMainBalance().getCurrencyType())
                    .formatBalance(account.getMainBalance().getAmount()));
            additionalBalance.setText(formatterFactory
                    .create(account.getAdditionalBalance().getCurrencyType())
                    .formatBalance(account.getAdditionalBalance().getAmount()));

            count.setText(itemView.getResources()
                    .getString(R.string.total_count, account.getTotalCount()));
            itemView.setOnClickListener(v -> listener.onFeedClick());
        }
    }
}
