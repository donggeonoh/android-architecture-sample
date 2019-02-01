package com.example.android.architecture.redprints.sampleapp.extension.rx;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * @author zeropol2
 * @since 2019. 2. 2.
 */
public interface AutoDisposableOwner {

    Single<Object> autoDisposeSignalSingle();

    Maybe<Object> autoDisposeSignalMaybe();

    Observable<Object> autoDisposeSignalObservable();

    Completable autoDisposeSignalCompletable();

}
