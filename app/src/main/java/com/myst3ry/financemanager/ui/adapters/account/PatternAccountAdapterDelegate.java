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
import com.myst3ry.model.PatternAccount;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PatternAccountAdapterDelegate
        extends BaseDelegateAdapter<PatternAccountAdapterDelegate.ViewHolder, PatternAccount> {
    private final OnPatternClickListener listener;
    private BalanceFormatterFactory formatterFactory = new BalanceFormatterFactory();

    public PatternAccountAdapterDelegate(OnPatternClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull View view,
                                    @NonNull PatternAccount PatternAccount,
                                    @NonNull ViewHolder viewHolder) {
        viewHolder.bind(PatternAccount);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_account_patterns;
    }
    @NonNull
    @Override
    protected ViewHolder createViewHolder(View view) {

        ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(v ->
                listener.onPatternClick(holder.getAdapterPosition()));
        return holder;
    }


    @Override
    public boolean isForViewType(@NonNull List<?> list, int i) {
        return list.get(i) instanceof PatternAccount;
    }


    public interface OnPatternClickListener {
        void onPatternClick(int position);
    }

    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.total)
        TextView total;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(PatternAccount account) {
            if (account.isSelected() && ViewUtils.isTabletUi(itemView)) {
                itemView.setBackgroundColor(ContextCompat
                        .getColor(itemView.getContext(), R.color.item_selected_color));
            } else {
                itemView.setBackgroundColor(ContextCompat
                        .getColor(itemView.getContext(), R.color.white));
            }
        }
    }
}
