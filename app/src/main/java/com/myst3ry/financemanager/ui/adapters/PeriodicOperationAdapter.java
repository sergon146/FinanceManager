package com.myst3ry.financemanager.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.utils.Utils;
import com.myst3ry.model.Operation;
import com.myst3ry.model.OperationType;
import com.myst3ry.model.PeriodicOperation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PeriodicOperationAdapter
        extends RecyclerView.Adapter<PeriodicOperationAdapter.BalanceViewHolder> {
    private List<PeriodicOperation> operationList = new ArrayList<>();
    private PeriodicSwitchListener listener;

    @NonNull
    @Override
    public BalanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.periodic_operation_item, parent, false);
        return new BalanceViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull BalanceViewHolder holder, int position) {
        holder.bind(operationList.get(position));
    }

    @Override
    public int getItemCount() {
        return operationList.size();
    }

    public void setOperations(List<PeriodicOperation> periodic) {
        this.operationList.clear();
        this.operationList.addAll(periodic);
        notifyDataSetChanged();
    }

    public void setListener(PeriodicSwitchListener listener) {
        this.listener = listener;
    }

    public void periodicToggleError(boolean isActive, PeriodicOperation periodic) {
        for (int i = 0; i < operationList.size(); i++) {
            PeriodicOperation periodicOperation = operationList.get(i);
            if (periodic.getId() == periodicOperation.getId()) {
                periodicOperation.setActive(isActive);
                notifyItemChanged(i);
            }
        }
    }

    public interface PeriodicSwitchListener {
        void onSwitchToggled(boolean isActive, PeriodicOperation operation);
    }

    class BalanceViewHolder extends RecyclerView.ViewHolder {
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

        BalanceViewHolder(View itemView) {
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