package com.example.android.architecture.redprints.sampleapp.data.remote.thirdparty.naver;

import com.example.android.architecture.redprints.sampleapp.data.remote.thirdparty.naver.response.SearchMovieListResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author zeropol2
 * @since 2019. 2. 2.
 */
public interface SearchApi {
    @GET("search/movie.json")
    Single<SearchMovieListResponse> searchMovieList(@Query(value = "query", encoded = true) String query);
}
