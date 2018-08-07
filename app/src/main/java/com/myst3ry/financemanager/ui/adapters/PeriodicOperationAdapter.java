package com.myst3ry.financemanager.ui.adapters;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.example.delegateadapter.delegate.BaseDelegateAdapter;
import com.example.delegateadapter.delegate.BaseViewHolder;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.utils.Utils;
import com.myst3ry.model.Operation;
import com.myst3ry.model.OperationType;
import com.myst3ry.model.PeriodicOperation;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PeriodicOperationAdapter
        extends BaseDelegateAdapter<PeriodicOperationAdapter.ViewHolder, PeriodicOperation> {
    private PeriodicSwitchListener listener;

    public PeriodicOperationAdapter(PeriodicSwitchListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull View view,
                                    @NonNull PeriodicOperation periodicOperation,
                                    @NonNull ViewHolder viewHolder) {
        viewHolder.bind(periodicOperation);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.periodic_operation_item;
    }

    @NonNull
    @Override
    protected ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public boolean isForViewType(@NonNull List<?> list, int i) {
        return list.get(i) instanceof PeriodicOperation;
    }

    public interface PeriodicSwitchListener {
        void onSwitchToggled(boolean isActive, PeriodicOperation operation);
    }

    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.amount)
        TextView amount;
        @BindView(R.id.category)
        TextView category;
        @BindView(R.id.type)
        TextView type;
        @BindView(R.id.periodic_status)
        Switch status;
        @BindView(R.id.count)
        TextView dayRepeat;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(PeriodicOperation periodic) {
            status.setChecked(periodic.isActive());
            status.setOnCheckedChangeListener((compound, toggled) -> {
                if (listener != null) {
                    listener.onSwitchToggled(toggled, periodic);
                }
            });
            dayRepeat.setText(String.valueOf(periodic.getDayRepeat()));

            Operation item = periodic.getOperation();
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

            type.setText(Utils.getOperationTypeTitle(itemView.getContext(), item.getType()));
            category.setText(item.getCategory());
        }
    }
}