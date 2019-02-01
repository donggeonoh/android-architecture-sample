package com.example.android.architecture.redprints.sampleapp.data.remote.thirdparty.naver.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author zeropol2
 * @since 2019. 2. 2.
 */
public class SearchMovieListResponse {

    @SerializedName("items")
    @Expose
    private final List<SearchMovieItemResponse> items;

    public SearchMovieListResponse(List<SearchMovieItemResponse> items) {
        this.items = items;
    }

    public List<SearchMovieItemResponse> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "SearchMovieListResponse{" +
                "items=" + items +
                '}';
    }
}
