package com.myst3ry.financemanager.ui.base;

import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<View extends BaseView> extends MvpPresenter<View> {
    private final CompositeDisposable disposable = new CompositeDisposable();

    protected void bind(Disposable disposable) {
        this.disposable.add(disposable);
    }

    @Override
    public void detachView(View view) {
        super.detachView(view);
        if (getAttachedViews().size() == 0) {
            disposable.clear();
        }
    }

    protected <T> Observable<T> onUi(final Observable<T> observable) {
        return observable.observeOn(AndroidSchedulers.mainThread());
    }

    protected Completable onUi(final Completable completable) {
        return completable.observeOn(AndroidSchedulers.mainThread());
    }

    protected <T> Flowable<T> onUi(final Flowable<T> flowable) {
        return flowable.observeOn(AndroidSchedulers.mainThread());
    }
}
