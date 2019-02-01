package com.example.android.architecture.redprints.sampleapp.data.model;

import java.util.Objects;

import androidx.annotation.NonNull;

/**
 * @author zeropol2
 * @since 2019. 2. 2.
 */
public class MovieInfo {
    @NonNull
    private final String id;
    @NonNull
    private final String title;
    @NonNull
    private final String linkUrl;
    private final boolean isFavorite;

    public MovieInfo(@NonNull final String id, @NonNull String title, @NonNull String linkUrl, boolean isFavorite) {
        this.id = id;
        this.title = title;
        this.linkUrl = linkUrl;
        this.isFavorite = isFavorite;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public String getLinkUrl() {
        return linkUrl;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    @NonNull
    @Override
    public String toString() {
        return "MovieInfo{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                ", isFavorite=" + isFavorite +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieInfo movieInfo = (MovieInfo) o;
        return isFavorite == movieInfo.isFavorite &&
                Objects.equals(id, movieInfo.id) &&
                Objects.equals(title, movieInfo.title) &&
                Objects.equals(linkUrl, movieInfo.linkUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, linkUrl, isFavorite);
    }
}
