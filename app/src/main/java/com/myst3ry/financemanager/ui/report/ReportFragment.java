package com.myst3ry.financemanager.ui.report;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.ui.adapters.ReportAdapter;
import com.myst3ry.financemanager.ui.base.BaseFragment;
import com.myst3ry.financemanager.utils.Utils;
import com.myst3ry.model.ReportData;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ReportFragment extends BaseFragment<ReportPresenter> implements ReportView {

    @Inject
    @InjectPresenter
    ReportPresenter presenter;
    @BindView(R.id.start)
    TextView startDay;
    @BindView(R.id.end)
    TextView endDay;
    @BindView(R.id.data_recycler)
    RecyclerView dataRecycler;
    @BindView(R.id.empty)
    View emptyHolder;
    private Calendar calendar = Calendar.getInstance();
    private Date startDate;
    private Date endDate;
    private ReportAdapter adapter;

    public static ReportFragment newInstance() {
        return new ReportFragment();
    }

    @Override
    @ProvidePresenter
    public ReportPresenter providePresenter() {
        return presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report, container, false);
    }

    @Override
    protected void prepareViews() {
        setScreenTitle(R.string.tab_report);
        dataRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ReportAdapter();
        dataRecycler.setAdapter(adapter);
    }

    @OnClick( {R.id.start, R.id.start_title})
    void onStartClick(View view) {
        DatePickerDialog dialog = new DatePickerDialog(view.getContext(),
                this::prepareStart,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }


    @OnClick( {R.id.end, R.id.end_title})
    void onEndClick(View view) {
        DatePickerDialog dialog = new DatePickerDialog(view.getContext(),
                this::prepareEnd,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private void prepareStart(DatePicker datePicker, int year, int month, int day) {
        startDate = Utils.getPickerDate(datePicker);
        startDay.setText(getString(R.string.date_pattern, day, month, year));
    }

    private void prepareEnd(DatePicker datePicker, int year, int month, int day) {
        endDate = Utils.getPickerDate(datePicker);
        endDay.setText(getString(R.string.date_pattern, day, month, year));
    }

    @OnClick(R.id.generate)
    void onGenerateClick() {
        if (startDate == null || endDate == null) {
            showToast(R.string.error);
        } else {
            getPresenter().loadReport(startDate, endDate);
        }
    }

    @Override
    public String getScreenTag() {
        return "ReportFragment";
    }

    @Override
    public void showReport(List<ReportData> reportData) {
        adapter.setReports(reportData);
    }

    @Override
    public void showEmpty() {
        emptyHolder.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmpty() {
        emptyHolder.setVisibility(View.GONE);
    }
}
