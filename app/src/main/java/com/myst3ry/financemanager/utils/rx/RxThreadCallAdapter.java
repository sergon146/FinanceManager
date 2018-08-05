package com.myst3ry.financemanager.utils.rx;

import android.support.annotation.NonNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class RxThreadCallAdapter extends CallAdapter.Factory {

    private RxJava2CallAdapterFactory rxFactory = RxJava2CallAdapterFactory.create();
    private Scheduler subscribeScheduler;

    public RxThreadCallAdapter(Scheduler subscribeScheduler) {
        this.subscribeScheduler = subscribeScheduler;
    }

    @Override
    public CallAdapter<?, ?> get(@NonNull Type returnType,
                                 @NonNull Annotation[] annotations,
                                 @NonNull Retrofit retrofit) {
        CallAdapter<?, Observable<?>> callAdapter =
                (CallAdapter<?, Observable<?>>) rxFactory.get(returnType, annotations, retrofit);
        return callAdapter != null ? new ThreadCallAdapter(callAdapter) : null;
    }

    final class ThreadCallAdapter<T> implements CallAdapter<T, Observable<?>> {
        CallAdapter<T, Observable<?>> delegateAdapter;

        ThreadCallAdapter(CallAdapter<T, Observable<?>> delegateAdapter) {
            this.delegateAdapter = delegateAdapter;
        }

        @Override
        public Type responseType() {
            return delegateAdapter.responseType();
        }

        @Override
        public Observable<?> adapt(@NonNull Call<T> call) {
            return delegateAdapter.adapt(call).subscribeOn(subscribeScheduler);
        }
    }
}
