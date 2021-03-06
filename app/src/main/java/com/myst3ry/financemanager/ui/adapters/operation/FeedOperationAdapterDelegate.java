package com.myst3ry.financemanager.ui.adapters.operation;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.delegateadapter.delegate.BaseDelegateAdapter;
import com.example.delegateadapter.delegate.BaseViewHolder;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.utils.Utils;
import com.myst3ry.model.Operation;
import com.myst3ry.model.OperationType;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedOperationAdapterDelegate
        extends BaseDelegateAdapter<FeedOperationAdapterDelegate.ViewHolder, Operation> {

    @Override
    protected void onBindViewHolder(@NonNull View view,
                                    @NonNull Operation operation,
                                    @NonNull ViewHolder viewHolder) {
        viewHolder.bind(operation);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_operation_feed;
    }

    @NonNull
    @Override
    protected ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public boolean isForViewType(@NonNull List list, int i) {
        return list.get(i) instanceof Operation;
    }

    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.category)
        TextView category;
        @BindView(R.id.amount)
        TextView amount;
        @BindView(R.id.account)
        TextView account;
        @BindView(R.id.date)
        TextView date;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Operation item) {
            title.setText(item.getTitle());
            account.setText(item.getAccount().getTitle());

            int coef;
            int color;
            if (item.getType() == OperationType.INCOME) {
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

            date.setText(SimpleDateFormat.getDateInstance().format(item.getDate()));
            String categoryTitle = item.getCategory();
            if (categoryTitle.equals(itemView.getResources().getString(R.string.none))) {
                category.setVisibility(View.GONE);
            } else {
                category.setText(categoryTitle);
                category.setVisibility(View.VISIBLE);
            }
        }
    }
}