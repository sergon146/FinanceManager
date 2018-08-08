package com.myst3ry.financemanager.ui.operationslist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.delegateadapter.delegate.diff.DiffUtilCompositeAdapter;
import com.example.delegateadapter.delegate.diff.IComparableItem;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.ui.adapters.operation.FeedOperationAdapterDelegate;
import com.myst3ry.financemanager.ui.adapters.operation.OperationAdapterDelegate;
import com.myst3ry.financemanager.ui.adapters.operation.PeriodicOperationAdapterDelegate;
import com.myst3ry.financemanager.ui.base.BaseFragment;
import com.myst3ry.financemanager.ui.dialogs.addoperation.AddOperationDialog;
import com.myst3ry.model.AccountItemType;
import com.myst3ry.model.PeriodicOperation;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class OperationListFragment extends BaseFragment<OperationListPresenter>
        implements OperationListView {
    private static final String ACCOUNT_TYPE = "ACCOUNT_TYPE";
    private static final String ACCOUNT_ID = "ACCOUNT_ID";
    @BindView(R.id.operation_recycler)
    RecyclerView operationsRecycler;
    @BindView(R.id.empty)
    View emptyHolder;

    @Inject
    @InjectPresenter
    OperationListPresenter operationListPresenter;

    private DiffUtilCompositeAdapter adapter;
    private long accountId;

    public static OperationListFragment newInstance(AccountItemType type) {
        OperationListFragment fragment = new OperationListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ACCOUNT_TYPE, type.name());
        fragment.setArguments(bundle);
        return fragment;
    }

    public static OperationListFragment newInstance(Long id) {
        OperationListFragment fragment = new OperationListFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(ACCOUNT_ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    @ProvidePresenter
    protected OperationListPresenter providePresenter() {
        return operationListPresenter;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_operation_list, container, false);
    }

    @Override
    protected void prepareViews() {
        super.prepareViews();
        AccountItemType accountItemType = AccountItemType.ACCOUNT;

        if (getArguments() != null) {
            String type = getArguments().getString(ACCOUNT_TYPE);

            if (type != null) {
                accountItemType = AccountItemType.valueOf(type);
            }

            if (accountItemType == AccountItemType.ACCOUNT) {
                accountId = getArguments().getLong(ACCOUNT_ID);
                getPresenter().loadByAccountId(accountId);
            } else {
                getPresenter().loadByType(accountItemType);
            }
        }


        operationsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        DiffUtilCompositeAdapter.Builder builder = new DiffUtilCompositeAdapter.Builder()
                .add(new PeriodicOperationAdapterDelegate((isActive, operation) ->
                        getPresenter().togglePeriodic(isActive, operation)));

        if (accountItemType == AccountItemType.ACCOUNT) {
            builder.add(new OperationAdapterDelegate());
        } else {
            builder.add(new FeedOperationAdapterDelegate());
        }

        adapter = builder.build();

        operationsRecycler.setAdapter(adapter);
    }


    @OnClick(R.id.fab_add)
    void onFabClick() {
        AddOperationDialog dialog = new AddOperationDialog();
        dialog.show(getChildFragmentManager(), getScreenTag());
        dialog.setCancelable(false);
    }

    @Override
    public void showOperations(List<IComparableItem> operations) {
        operationsRecycler.scrollToPosition(0);
        adapter.swapData(operations);
    }

    @Override
    public void periodicToggleError(boolean isActive, PeriodicOperation periodic) {
        adapter.notifyDataSetChanged();
        showLongToast(R.string.error);
    }

    @Override
    public void showEmpty() {
        emptyHolder.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmpty() {
        emptyHolder.setVisibility(View.GONE);
    }

    @Override
    public String getScreenTag() {
        return "OperationListFragment";
    }
}
