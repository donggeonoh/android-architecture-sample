package com.example.android.architecture.redprints.sampleapp.presentation.movielist;

import android.annotation.SuppressLint;

import com.example.android.architecture.redprints.sampleapp.data.model.MovieInfo;
import com.example.android.architecture.redprints.sampleapp.data.remote.thirdparty.naver.request.SearchMovieListRequest;
import com.example.android.architecture.redprints.sampleapp.data.repository.MovieRepository;
import com.example.android.architecture.redprints.sampleapp.extension.rx.AutoDisposeException;
import com.example.android.architecture.redprints.sampleapp.presentation.BasePresenter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author zeropol2
 * @since 2019. 2. 2.
 */
class MovieListPresenter extends BasePresenter {

    @NonNull
    private final MovieRepository movieRepository;

    @NonNull
    private final MovieListView view;

    MovieListPresenter(@NonNull final MovieRepository movieRepository,
                       @NonNull final MovieListView view) {
        this.movieRepository = movieRepository;
        this.view = view;
    }

    @SuppressLint("CheckResult")
    void loadMovieInfoList(@NonNull String query) {
        movieRepository.observeMovieInfoList(new SearchMovieListRequest(query))
                .observeOn(AndroidSchedulers.mainThread())
                .takeUntil(autoDisposeSignalSingle())
                .subscribe(view::setMovieInfoList, view::showError);
    }

    @SuppressLint("CheckResult")
    void updateMovieInfo(@Nullable final List<MovieInfo> movieInfoList,
                         @NonNull final MovieInfo oldMovieInfo, boolean updatedFavorite) {
        final MovieInfo newMovieInfo = new MovieInfo(oldMovieInfo.getId(),
                oldMovieInfo.getTitle(), oldMovieInfo.getLinkUrl(), updatedFavorite);
        updateMovieInfoList(movieInfoList, newMovieInfo);
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
                .subscribe(() -> updateMovieInfoList(movieInfoList, newMovieInfo),
                        throwable -> {
                            if (throwable instanceof AutoDisposeException) {
                                return;
                            }
                            updateMovieInfoList(movieInfoList, oldMovieInfo);
                            view.showError(throwable);
                        });
    }

    private void updateMovieInfoList(@Nullable List<MovieInfo> items,
                                     @NonNull MovieInfo movieInfo) {
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
        view.setMovieInfoList(items);
    }
}
