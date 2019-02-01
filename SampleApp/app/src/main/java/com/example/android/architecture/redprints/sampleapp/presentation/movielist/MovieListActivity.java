package com.example.android.architecture.redprints.sampleapp.presentation.movielist;

import android.os.Bundle;
import android.widget.Toast;

import com.example.android.architecture.redprints.sampleapp.BuildConfig;
import com.example.android.architecture.redprints.sampleapp.R;
import com.example.android.architecture.redprints.sampleapp.data.local.room.AppDatabase;
import com.example.android.architecture.redprints.sampleapp.data.model.MovieInfo;
import com.example.android.architecture.redprints.sampleapp.data.remote.thirdparty.naver.NaverApiClient;
import com.example.android.architecture.redprints.sampleapp.data.repository.MovieRepositoryImpl;
import com.example.android.architecture.redprints.sampleapp.databinding.ActivityMovieListBinding;
import com.example.android.architecture.redprints.sampleapp.extension.rx.AutoDisposeException;
import com.example.android.architecture.redprints.sampleapp.presentation.BaseActivity;
import com.example.android.architecture.redprints.sampleapp.presentation.detail.MovieDetailActivity;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

public class MovieListActivity extends BaseActivity<ActivityMovieListBinding> {

    private ActivityMovieListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = binding(R.layout.activity_movie_list);
        initViewModel();
        initViews();
        subscribeError();
    }

    private void initViewModel() {
        final MovieListViewModel movieListViewModel = ViewModelProviders.of(this,
                new MovieListViewModelFactory(MovieRepositoryImpl.getInstance(
                NaverApiClient.getInstance(),
                AppDatabase.getDatabase(this).favoriteDao()
        ))).get(MovieListViewModel.class);
        binding.setVm(movieListViewModel);
    }

    private void initViews() {
        initRecyclerView();
        initButton();
    }

    private void initRecyclerView() {
        final RecyclerView recyclerView = findViewById(R.id.recylcerView);
        final MovieListAdapter adapter = new MovieListAdapter();
        adapter.setOnItemClickListener(new MovieListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MovieInfo movieInfo) {
                startActivity(MovieDetailActivity
                        .getLaunchIntent(MovieListActivity.this, movieInfo.getLinkUrl()));
            }

            @Override
            public void onItemLongClick(MovieInfo movieInfo) {
                binding.getVm().updateMovieInfo(movieInfo, !movieInfo.isFavorite());
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void initButton() {
        findViewById(R.id.button).setOnClickListener(v ->
                binding.getVm().loadMovieInfoList(binding.editText.getText().toString()));
    }

    private void subscribeError() {
        binding.getVm().getError().observe(this, throwable -> {
            if (BuildConfig.DEBUG) {
                throwable.printStackTrace();
            }
            if (throwable instanceof AutoDisposeException) {
                return;
            }
            Toast.makeText(this, "에러가 발생했습니다.", Toast.LENGTH_SHORT).show();
        });
    }

}
