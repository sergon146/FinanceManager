package com.myst3ry.financemanager.ui.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.utils.Utils;
import com.myst3ry.model.OperationType;
import com.myst3ry.model.Template;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TemplateAdapter extends RecyclerView.Adapter<TemplateAdapter.ViewHolder> {
    private List<Template> templates = new ArrayList<>();
    private TemplateClickListener listener;

    public TemplateAdapter(@Nullable TemplateClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_template, parent, false);

        ViewHolder holder = new ViewHolder(item);
        if (listener != null) {
            holder.itemView.setOnClickListener(v ->
                    listener.onTemplateClick(templates.get(holder.getAdapterPosition())));

            holder.delete.setOnClickListener(v ->
                    listener.onTemplateDeleteClick(templates.get(holder.getAdapterPosition())));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(templates.get(position));
    }

    @Override
    public int getItemCount() {
        return templates.size();
    }

    public void setTemplates(List<Template> templates) {
        this.templates.clear();
        this.templates.addAll(templates);
        notifyDataSetChanged();
    }

    public interface TemplateClickListener {
        void onTemplateClick(Template template);

        void onTemplateDeleteClick(Template template);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.category)
        TextView category;
        @BindView(R.id.amount)
        TextView amount;
        @BindView(R.id.delete)
        View delete;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Template item) {
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
