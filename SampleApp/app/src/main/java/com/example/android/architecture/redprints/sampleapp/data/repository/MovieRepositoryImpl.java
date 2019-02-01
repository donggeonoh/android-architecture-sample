package com.example.android.architecture.redprints.sampleapp.data.repository;

import com.example.android.architecture.redprints.sampleapp.data.local.room.dao.FavoriteDao;
import com.example.android.architecture.redprints.sampleapp.data.local.room.entity.Favorite;
import com.example.android.architecture.redprints.sampleapp.data.model.MovieInfo;
import com.example.android.architecture.redprints.sampleapp.data.remote.thirdparty.naver.NaverApiClient;
import com.example.android.architecture.redprints.sampleapp.data.remote.thirdparty.naver.request.SearchMovieListRequest;
import com.example.android.architecture.redprints.sampleapp.data.remote.thirdparty.naver.response.SearchMovieItemResponse;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * @author zeropol2
 * @since 2019. 2. 2.
 */
public class MovieRepositoryImpl implements MovieRepository {

    private static volatile MovieRepository INSTANCE;

    public static MovieRepository getInstance(@NonNull final NaverApiClient naverApiClient,
                                              @NonNull final FavoriteDao favoriteDao) {
        if (INSTANCE == null) {
            synchronized (MovieRepositoryImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieRepositoryImpl(naverApiClient, favoriteDao);
                }
            }
        }
        return INSTANCE;
    }

    private final NaverApiClient naverApiClient;
    private final FavoriteDao favoriteDao;

    private MovieRepositoryImpl(@NonNull final NaverApiClient naverApiClient,
                                @NonNull final FavoriteDao favoriteDao) {
        this.naverApiClient = naverApiClient;
        this.favoriteDao = favoriteDao;
    }

    @NonNull
    @Override
    public Single<List<MovieInfo>> observeMovieInfoList(@NonNull SearchMovieListRequest request) {
        return Single.zip(naverApiClient.searchMovieList(request).subscribeOn(Schedulers.io()),
                favoriteDao.selectAll().subscribeOn(Schedulers.io()),
                (searchMovieListResponse, favorites) -> {
                    final List<MovieInfo> result = new ArrayList<>();
                    for (SearchMovieItemResponse item : searchMovieListResponse.getItems()) {
                        final String id = item.getLinkUrl();
                        boolean isFavorite = false;
                        for (Favorite favorite : favorites) {
                            if (favorite.getId().equals(id)) {
                                isFavorite = true;
                                break;
                            }
                        }
                        final MovieInfo movieInfo = new MovieInfo(item.getLinkUrl(), item.getTitle(), item.getLinkUrl(), isFavorite);
                        result.add(movieInfo);
                    }
                    return result;
                }).subscribeOn(Schedulers.io());
    }

    @NonNull
    @Override
    public Single<Favorite> observeFavoriteByLinkUrl(@NonNull String linkUrl) {
        return favoriteDao.selectById(linkUrl).subscribeOn(Schedulers.io());
    }

    @NonNull
    @Override
    public Completable observeInsertFavorite(@NonNull String linkUrl) {
        return favoriteDao.insert(new Favorite(linkUrl)).subscribeOn(Schedulers.io());
    }

    @NonNull
    @Override
    public Completable observeDeleteFavorite(@NonNull String linkUrl) {
        return favoriteDao.delete(new Favorite(linkUrl)).subscribeOn(Schedulers.io());
    }
}
