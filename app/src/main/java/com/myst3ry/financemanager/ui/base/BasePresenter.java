package com.myst3ry.financemanager.ui.base;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<View> {
    private final CompositeDisposable disposable = new CompositeDisposable();

    protected View view;

    public void attachView(View view) {
        this.view = view;
    }

    protected void bind(Disposable disposable) {
        this.disposable.add(disposable);
    }

    public void detachView() {
        this.view = null;
        disposable.clear();
    }

    protected <T> Observable<T> onUi(final Observable<T> observable) {
        return observable.observeOn(AndroidSchedulers.mainThread());
    }
}
