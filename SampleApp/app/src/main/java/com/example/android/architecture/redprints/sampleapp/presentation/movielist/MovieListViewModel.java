package com.example.android.architecture.redprints.sampleapp.presentation.movielist;

import android.annotation.SuppressLint;

import com.example.android.architecture.redprints.sampleapp.data.model.MovieInfo;
import com.example.android.architecture.redprints.sampleapp.data.remote.thirdparty.naver.request.SearchMovieListRequest;
import com.example.android.architecture.redprints.sampleapp.data.repository.MovieRepository;
import com.example.android.architecture.redprints.sampleapp.extension.rx.AutoDisposeException;
import com.example.android.architecture.redprints.sampleapp.presentation.BaseViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author zeropol2
 * @since 2019. 2. 2.
 */
public class MovieListViewModel extends BaseViewModel {

    @NonNull
    private final MovieRepository movieRepository;

    @NonNull
    private MutableLiveData<List<MovieInfo>> movieInfoList = new MutableLiveData<>();
    @NonNull
    private MutableLiveData<Throwable> error = new MutableLiveData<>();

    MovieListViewModel(@NonNull final MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @NonNull
    public LiveData<List<MovieInfo>> getMovieInfoList() {
        return movieInfoList;
    }

    @NonNull
    LiveData<Throwable> getError() {
        return error;
    }

    @SuppressLint("CheckResult")
    void loadMovieInfoList(@NonNull final String query) {
        movieRepository.observeMovieInfoList(new SearchMovieListRequest(query))
                .observeOn(AndroidSchedulers.mainThread())
                .takeUntil(autoDisposeSignalSingle())
                .subscribe(movieInfoList::setValue, error::setValue);
    }

    @SuppressLint("CheckResult")
    void updateMovieInfo(@NonNull final MovieInfo oldMovieInfo,
                         boolean updatedFavorite) {
        final MovieInfo newMovieInfo = new MovieInfo(oldMovieInfo.getId(),
                oldMovieInfo.getTitle(), oldMovieInfo.getLinkUrl(), updatedFavorite);
        updateMovieInfoList(newMovieInfo);
        Single.fromCallable(() -> updatedFavorite)
                .flatMapCompletable(isFavorite -> {
                    if (isFavorite) {
                        return movieRepository.observeInsertFavorite(oldMovieInfo.getLinkUrl());
                    } else {
                        return movieRepository.observeDeleteFavorite(oldMovieInfo.getLinkUrl());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .takeUntil(autoDisposeSignalCompletable())
                .subscribe(() -> updateMovieInfoList(newMovieInfo),
                        throwable -> {
                            if (throwable instanceof AutoDisposeException) {
                                return;
                            }
                            updateMovieInfoList(oldMovieInfo);
                            error.setValue(throwable);
                        });
    }

    private void updateMovieInfoList(@NonNull MovieInfo movieInfo) {
        final List<MovieInfo> items = movieInfoList.getValue();
        if (items == null) {
            return;
        }
        int i;
        for (i = 0; i < items.size(); i++) {
            final MovieInfo item = items.get(i);
            if (item.getId().equals(movieInfo.getId())) {
                items.set(i, movieInfo);
                break;
            }
        }
        movieInfoList.setValue(items);
    }

}
