package com.myst3ry.financemanager.ui.dialogs.template;

import com.myst3ry.financemanager.ui.base.BaseView;
import com.myst3ry.model.Template;

import java.util.List;

public interface TemplateView extends BaseView {
    void showTemplate(List<Template> templates);

    void hideEmpty();

    void showEmpty();
}
