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
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.ui.adapters.PeriodicOperationAdapter;
import com.myst3ry.financemanager.ui.base.BaseFragment;
import com.myst3ry.model.PeriodicOperation;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class OperationListFragment extends BaseFragment<OperationListPresenter>
        implements OperationListView {
    private static final String SHOW_PERIODIC = "SHOW_PERIODIC";
    @BindView(R.id.operation_recycler)
    RecyclerView operationRecycler;
    @BindView(R.id.empty)
    View emptyHolder;
    @Inject
    @InjectPresenter
    OperationListPresenter operationListPresenter;
    private RecyclerView.Adapter adapter;

    public static OperationListFragment newInstance(boolean isShowPeriodic) {
        OperationListFragment fragment = new OperationListFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(SHOW_PERIODIC, isShowPeriodic);
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

        if (getArguments() != null) {
            boolean isPeriodic = getArguments().getBoolean(SHOW_PERIODIC);
            getPresenter().setShowPeriodic(isPeriodic);
            setScreenTitle(isPeriodic ? R.string.perioidc_operation_title
                    : R.string.operation_title);
        }
        operationRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PeriodicOperationAdapter();
        operationRecycler.setAdapter(adapter);
    }

    @Override
    public String getScreenTag() {
        return "OperationListFragment";
    }

    @Override
    public void showPeriodicOperations(List<PeriodicOperation> periodicOperation) {
        if (periodicOperation.isEmpty()) {
            emptyHolder.setVisibility(View.VISIBLE);
        } else {
            PeriodicOperationAdapter periodicAdapter = (PeriodicOperationAdapter) adapter;
            periodicAdapter.setListener((isActive, periodic) ->
                    getPresenter().togglePeriodic(isActive, periodic));
            periodicAdapter.setOperations(periodicOperation);
        }
    }

    @Override
    public void periodicToggleError(boolean isActive, PeriodicOperation periodic) {
        PeriodicOperationAdapter periodicAdapter = (PeriodicOperationAdapter) adapter;
        periodicAdapter.periodicToggleError(isActive, periodic);
    }
}
