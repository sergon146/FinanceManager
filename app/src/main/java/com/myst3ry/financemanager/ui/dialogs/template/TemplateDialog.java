package com.myst3ry.financemanager.ui.dialogs.template;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.ui.adapters.TemplateAdapter;
import com.myst3ry.financemanager.ui.base.dialog.BaseDialogMvpFragment;
import com.myst3ry.financemanager.ui.component.AutoFitGridRecyclerView;
import com.myst3ry.model.Template;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class TemplateDialog extends BaseDialogMvpFragment<TemplatePresenter>
        implements TemplateView {
    @Inject
    @InjectPresenter
    public TemplatePresenter presenter;
    @BindView(R.id.template_recycler)
    AutoFitGridRecyclerView recyclerView;
    @BindView(R.id.empty)
    View emptyHolder;
    private TemplateSelectionListener listener;
    private TemplateAdapter adapter;

    public static TemplateDialog newInstance() {
        return new TemplateDialog();
    }

    @Override
    @ProvidePresenter
    protected TemplatePresenter providePresenter() {
        return presenter;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_template, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new TemplateAdapter(new TemplateAdapter.TemplateClickListener() {
            @Override
            public void onTemplateClick(Template template) {
                listener.onTemplateChosen(template);
                dismiss();
            }

            @Override
            public void onTemplateDeleteClick(Template template) {
                getPresenter().deleteTemplate(template);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public void setListener(TemplateSelectionListener listener) {
        this.listener = listener;
    }

    @Override
    public void showTemplate(List<Template> templates) {
        adapter.setTemplates(templates);
    }

    @Override
    public void hideEmpty() {
        emptyHolder.setVisibility(View.GONE);
    }

    @Override
    public void showEmpty() {
        emptyHolder.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.cancel)
    public void onCancelClick() {
        dismiss();
    }

    public interface TemplateSelectionListener {
        void onTemplateChosen(Template template);
    }

}
