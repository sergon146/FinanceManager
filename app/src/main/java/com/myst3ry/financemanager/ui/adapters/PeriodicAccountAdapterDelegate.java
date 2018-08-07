package com.myst3ry.financemanager.ui.adapters;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.delegateadapter.delegate.BaseDelegateAdapter;
import com.example.delegateadapter.delegate.BaseViewHolder;
import com.myst3ry.financemanager.R;
import com.myst3ry.model.PeriodicAccount;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PeriodicAccountAdapterDelegate
        extends BaseDelegateAdapter<PeriodicAccountAdapterDelegate.ViewHolder, PeriodicAccount> {
    private PeriodicClickListener listener;

    public PeriodicAccountAdapterDelegate(PeriodicClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull View view,
                                    @NonNull PeriodicAccount periodicOperation,
                                    @NonNull ViewHolder viewHolder) {
        viewHolder.bind(periodicOperation);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_account_periodic;
    }

    @NonNull
    @Override
    protected ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public boolean isForViewType(@NonNull List<?> list, int i) {
        return list.get(i) instanceof PeriodicAccount;
    }

    public interface PeriodicClickListener {
        void onClick();
    }

    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.count)
        TextView count;
        @BindView(R.id.active)
        TextView active;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(PeriodicAccount periodic) {
            count.setText(String.valueOf(periodic.getTotal()));


            count.setText(itemView.getResources()
                    .getString(R.string.total_count, periodic.getTotal()));

            active.setText(itemView.getResources()
                    .getString(R.string.activated_count, periodic.getActive()));
            itemView.setOnClickListener(v -> listener.onClick());
        }
    }
}