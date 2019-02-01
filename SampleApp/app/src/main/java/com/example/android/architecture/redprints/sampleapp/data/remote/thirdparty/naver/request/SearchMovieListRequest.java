package com.example.android.architecture.redprints.sampleapp.data.remote.thirdparty.naver.request;

import androidx.annotation.NonNull;

/**
 * @author zeropol2
 * @since 2019. 2. 2.
 */
public class SearchMovieListRequest {
    @NonNull
    private final String query;

    public SearchMovieListRequest(@NonNull String query) {
        this.query = query;
    }

    @NonNull
    public String getQuery() {
        return query;
    }
}
