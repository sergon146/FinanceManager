package com.myst3ry.financemanager.ui.adapters.account;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.example.delegateadapter.delegate.BaseDelegateAdapter;
import com.example.delegateadapter.delegate.BaseViewHolder;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.utils.ViewUtils;
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

        ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(v ->
                listener.onFeedClick(holder.getAdapterPosition()));
        return holder;
    }

    @Override
    public boolean isForViewType(@NonNull List<?> list, int i) {
        return list.get(i) instanceof FeedAccount;
    }


    public interface OnFeedClickListener {
        void onFeedClick(int position);
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
            if (account.isSelected() && ViewUtils.isTabletUi(itemView)) {
                itemView.setBackgroundColor(ContextCompat
                        .getColor(itemView.getContext(), R.color.item_selected_color));
            } else {
                itemView.setBackgroundColor(ContextCompat
                        .getColor(itemView.getContext(), R.color.white));
            }

            mainBalance.setText(formatterFactory
                    .create(account.getMainBalance().getCurrencyType())
                    .formatBalance(account.getMainBalance().getAmount()));
            additionalBalance.setText(formatterFactory
                    .create(account.getAdditionalBalance().getCurrencyType())
                    .formatBalance(account.getAdditionalBalance().getAmount()));

            count.setText(itemView.getResources()
                    .getString(R.string.total_count, account.getTotalCount()));
        }
    }
}
