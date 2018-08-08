package com.myst3ry.financemanager.ui.base.dialog;

import com.myst3ry.financemanager.ui.base.BasePresenter;
import com.myst3ry.financemanager.ui.base.BaseView;

public abstract class BaseDialogPresenter<View extends BaseView>
        extends BasePresenter<View> {

    protected final String screenTag;

    public BaseDialogPresenter() {
        super();
        screenTag = getScreenTag();
    }

    protected abstract String getScreenTag();
}