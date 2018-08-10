package com.myst3ry.financemanager.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.utils.Utils;
import com.myst3ry.model.CurrencyType;
import com.myst3ry.model.OperationType;
import com.myst3ry.model.ReportData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {
    private List<ReportData> templates = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_report, parent, false);

        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(templates.get(position));
    }

    @Override
    public int getItemCount() {
        return templates.size();
    }

    public void setReports(List<ReportData> templates) {
        this.templates.clear();
        this.templates.addAll(templates);
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.category)
        TextView category;
        @BindView(R.id.amount)
        TextView amount;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(ReportData item) {

            int coef;
            int color;
            if (item.getType() == OperationType.INCOME) {
                coef = 1;
                color = itemView.getResources().getColor(R.color.income);
            } else {
                coef = -1;
                color = itemView.getResources().getColor(R.color.expense);
            }

            String amountTitle = Utils.Currency.getAmountTitle(item.getSum()
                    .multiply(BigDecimal.valueOf(coef)), CurrencyType.RUB);
            amount.setText(amountTitle);
            amount.setTextColor(color);

            category.setText(item.getCategory());
        }
    }
}
