package com.example.android.architecture.redprints.sampleapp.presentation.movielist;

import com.example.android.architecture.redprints.sampleapp.data.repository.MovieRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * @author zeropol2
 * @since 2019. 2. 2.
 */
public class MovieListViewModelFactory implements ViewModelProvider.Factory {

    private final MovieRepository movieRepository;

    MovieListViewModelFactory(@NonNull final MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MovieListViewModel.class)) {
            //noinspection unchecked
            return (T) new MovieListViewModel(movieRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
