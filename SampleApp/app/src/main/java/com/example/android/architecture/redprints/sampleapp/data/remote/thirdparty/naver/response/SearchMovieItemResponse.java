package com.example.android.architecture.redprints.sampleapp.data.remote.thirdparty.naver.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author zeropol2
 * @since 2019. 2. 2.
 */
public class SearchMovieItemResponse {
    @SerializedName("title")
    @Expose
    private final String title;

    @SerializedName("link")
    @Expose
    private final String linkUrl;

    @SerializedName("image")
    @Expose
    private final String imageUrl;

    @SerializedName("subtitle")
    @Expose
    private final String subtitle;

    @SerializedName("director")
    @Expose
    private final String director;

    @SerializedName("actor")
    @Expose
    private final String actor;

    public SearchMovieItemResponse(String title, String linkUrl, String imageUrl, String subtitle, String director, String actor) {
        this.title = title;
        this.linkUrl = linkUrl;
        this.imageUrl = imageUrl;
        this.subtitle = subtitle;
        this.director = director;
        this.actor = actor;
    }

    public String getTitle() {
        return title;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getDirector() {
        return director;
    }

    public String getActor() {
        return actor;
    }

    @Override
    public String toString() {
        return "SearchMovieItemResponse{" +
                "title='" + title + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", director='" + director + '\'' +
                ", actor='" + actor + '\'' +
                '}';
    }
}
