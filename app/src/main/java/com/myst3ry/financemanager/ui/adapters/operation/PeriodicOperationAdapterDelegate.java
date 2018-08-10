package com.myst3ry.financemanager.ui.adapters.operation;

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

public class PeriodicOperationAdapterDelegate
        extends BaseDelegateAdapter<PeriodicOperationAdapterDelegate.ViewHolder, PeriodicOperation> {
    private PeriodicClickListener listener;

    public PeriodicOperationAdapterDelegate(@NonNull PeriodicClickListener listener) {
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
        return R.layout.item_operation_periodic;
    }

    @NonNull
    @Override
    protected ViewHolder createViewHolder(View view) {
        ViewHolder holder = new ViewHolder(view);
        holder.status.setOnCheckedChangeListener((compound, toggled) ->
                listener.onSwitchToggled(toggled, holder.getAdapterPosition()));

        holder.delete.setOnClickListener(v ->
                listener.onDeleteClick(holder.getAdapterPosition()));
        return holder;
    }

    @Override
    public boolean isForViewType(@NonNull List<?> list, int i) {
        return list.get(i) instanceof PeriodicOperation;
    }

    public interface PeriodicClickListener {
        void onSwitchToggled(boolean isActive, int pos);

        void onDeleteClick(int pos);
    }

    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.title)
        TextView title;
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
        @BindView(R.id.delete)
        View delete;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(PeriodicOperation periodic) {
            status.setChecked(periodic.isTurnOn());
            dayRepeat.setText(String.valueOf(periodic.getDayRepeat()));

            Operation item = periodic.getOperation();
            title.setText(item.getTitle());
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