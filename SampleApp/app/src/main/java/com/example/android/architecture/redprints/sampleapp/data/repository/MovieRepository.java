package com.example.android.architecture.redprints.sampleapp.data.repository;

import com.example.android.architecture.redprints.sampleapp.data.local.room.entity.Favorite;
import com.example.android.architecture.redprints.sampleapp.data.model.MovieInfo;
import com.example.android.architecture.redprints.sampleapp.data.remote.thirdparty.naver.request.SearchMovieListRequest;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * @author zeropol2
 * @since 2019. 2. 2.
 */
public interface MovieRepository {

    @NonNull
    Single<List<MovieInfo>> observeMovieInfoList(@NonNull SearchMovieListRequest request);

    @NonNull
    Single<Favorite> observeFavoriteByLinkUrl(@NonNull String linkUrl);

    @NonNull
    Completable observeInsertFavorite(@NonNull String linkUrl);

    @NonNull
    Completable observeDeleteFavorite(@NonNull String linkUrl);

}
