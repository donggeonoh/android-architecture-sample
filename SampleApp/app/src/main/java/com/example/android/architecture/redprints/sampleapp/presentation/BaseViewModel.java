package com.example.android.architecture.redprints.sampleapp.presentation;

import com.example.android.architecture.redprints.sampleapp.extension.rx.AutoDisposableOwner;
import com.example.android.architecture.redprints.sampleapp.extension.rx.AutoDisposeException;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.CompletableSubject;

/**
 * @author zeropol2
 * @since 2019. 2. 2.
 */
public class BaseViewModel extends ViewModel implements AutoDisposableOwner {

    @NonNull
    private CompletableSubject autoDisposeSignal = CompletableSubject.create();

    @Override
    protected void onCleared() {
        autoDisposeSignal.onComplete();
        super.onCleared();
    }

    public Single<Object> autoDisposeSignalSingle() {
        return autoDisposeSignal.andThen(Single.error(new AutoDisposeException()));
    }

    public Maybe<Object> autoDisposeSignalMaybe() {
        return autoDisposeSignal.andThen(Maybe.error(new AutoDisposeException()));
    }

    public Observable<Object> autoDisposeSignalObservable() {
        return autoDisposeSignal.andThen(Observable.error(new AutoDisposeException()));
    }

    public Completable autoDisposeSignalCompletable() {
        return autoDisposeSignal.andThen(Completable.error(new AutoDisposeException()));
    }

}
