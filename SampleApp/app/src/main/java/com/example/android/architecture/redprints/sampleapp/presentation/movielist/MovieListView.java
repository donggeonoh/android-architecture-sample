package com.example.android.architecture.redprints.sampleapp.presentation.movielist;

import com.example.android.architecture.redprints.sampleapp.data.model.MovieInfo;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * @author zeropol2
 * @since 2019. 2. 2.
 */
interface MovieListView {
    void setMovieInfoList(@NonNull List<MovieInfo> movieInfoList);

    void showError(Throwable throwable);
}
