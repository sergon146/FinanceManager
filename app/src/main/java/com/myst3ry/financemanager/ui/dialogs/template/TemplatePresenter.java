package com.myst3ry.financemanager.ui.dialogs.template;

import com.arellomobile.mvp.InjectViewState;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.ui.base.dialog.BaseDialogPresenter;
import com.myst3ry.financemanager.usecase.TemplateUseCase;
import com.myst3ry.model.Template;

@InjectViewState
public class TemplatePresenter extends BaseDialogPresenter<TemplateView> {

    private final TemplateUseCase useCase;

    public TemplatePresenter(TemplateUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        bind(onUi(useCase.getAllTemplate()).subscribe(
                templates -> {
                    if (templates.isEmpty()) {
                        getViewState().showEmpty();
                    } else {
                        getViewState().hideEmpty();
                    }
                    getViewState().showTemplate(templates);
                }));
    }

    public void deleteTemplate(Template template) {
        bind(onUi(useCase.deleteTemplate(template)).subscribe(
                () -> {
                }, throwable -> getViewState().showToast(R.string.error)));
    }

    @Override
    protected String getScreenTag() {
        return "TemplatePresenter";
    }
}
