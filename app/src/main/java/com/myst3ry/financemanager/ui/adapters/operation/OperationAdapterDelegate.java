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

public class OperationAdapterDelegate
        extends BaseDelegateAdapter<OperationAdapterDelegate.ViewHolder, Operation> {
    private final OperationClickListener listener;

    public OperationAdapterDelegate(OperationClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull View view,
                                    @NonNull Operation operation,
                                    @NonNull ViewHolder viewHolder) {
        viewHolder.bind(operation);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_operation;
    }

    @NonNull
    @Override
    protected ViewHolder createViewHolder(View view) {
        ViewHolder holder = new ViewHolder(view);
        holder.edit.setOnClickListener(v -> listener.onEditClick(holder.getAdapterPosition()));
        holder.delete.setOnClickListener(v -> listener.onDeleteClick(holder.getAdapterPosition()));
        return holder;
    }

    @Override
    public boolean isForViewType(@NonNull List list, int i) {
        return list.get(i) instanceof Operation;
    }

    public interface OperationClickListener {
        void onEditClick(int position);

        void onDeleteClick(int position);
    }

    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.category)
        TextView category;
        @BindView(R.id.amount)
        TextView amount;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.delete)
        View delete;
        @BindView(R.id.edit)
        View edit;


        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Operation item) {
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