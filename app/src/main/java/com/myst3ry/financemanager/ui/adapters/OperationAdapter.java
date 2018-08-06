package com.myst3ry.financemanager.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.utils.Utils;
import com.myst3ry.model.Operation;
import com.myst3ry.model.OperationType;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OperationAdapter extends RecyclerView.Adapter<OperationAdapter.BalanceViewHolder> {

    private List<Operation> operationList = new ArrayList<>();

    @NonNull
    @Override
    public BalanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.operation_item, parent, false);
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

    public void setOperations(List<Operation> Operations) {
        this.operationList.clear();
        this.operationList.addAll(Operations);
        notifyDataSetChanged();
    }

    class BalanceViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.category)
        TextView category;
        @BindView(R.id.amount)
        TextView amount;
        @BindView(R.id.date)
        TextView date;

        BalanceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Operation item) {
            title.setText(Utils.getOperationTypeTitle(itemView.getContext(),
                    item.getType()));

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
            category.setText(item.getCategory());
        }
    }
}