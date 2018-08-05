package com.myst3ry.financemanager.ui.base;

import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public abstract class BasePresenter<View extends BaseView> extends MvpPresenter<View> {
    private final CompositeDisposable viewDisposable = new CompositeDisposable();
    private final CompositeDisposable uiDisposable = new CompositeDisposable();
    private final CompositeDisposable presenterDisposable = new CompositeDisposable();

    protected void bind(Disposable disposable) {
        bind(disposable, LifeLevel.PER_VIEW);
    }

    protected void bind(Disposable disposable, LifeLevel level) {
        switch (level) {
            case PER_VIEW:
                viewDisposable.add(disposable);
                break;
            case PER_PRESENTER:
                presenterDisposable.add(disposable);
                break;
            case PER_UI:
                uiDisposable.add(disposable);
                break;
            default:
                viewDisposable.add(disposable);
                break;
        }
    }

    @Override
    public void detachView(final View view) {
        super.detachView(view);
        if (getAttachedViews().size() == 0) {
            viewDisposable.clear();
        }
    }

    @Override
    public void destroyView(View view) {
        super.destroyView(view);
        uiDisposable.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getAttachedViews().size() == 0) {
            presenterDisposable.clear();
        }
    }

    protected <T> Observable<T> onUi(final Observable<T> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    protected Completable onUi(final Completable completable) {
        return completable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    protected <T> Flowable<T> onUi(final Flowable<T> flowable) {
        return flowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
