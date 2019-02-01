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
import com.example.android.architecture.redprints.sampleapp.presentation.BaseActivity;
import com.example.android.architecture.redprints.sampleapp.presentation.detail.MovieDetailActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieListActivity extends BaseActivity<ActivityMovieListBinding> implements MovieListView {

    private ActivityMovieListBinding binding;

    private MovieListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = binding(R.layout.activity_movie_list);
        initPresenter();
        initViews();
    }

    @Override
    protected void onDestroy() {
        presenter.onCleared();
        super.onDestroy();
    }

    private void initPresenter() {
        presenter = new MovieListPresenter(
                MovieRepositoryImpl.getInstance(NaverApiClient.getInstance(),
                        AppDatabase.getDatabase(this).favoriteDao()),
                this);
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
                presenter.updateMovieInfo(binding.getMovieInfoList(), movieInfo, !movieInfo.isFavorite());
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void initButton() {
        findViewById(R.id.button).setOnClickListener(v ->
                presenter.loadMovieInfoList(binding.editText.getText().toString()));
    }

    @Override
    public void setMovieInfoList(@NonNull List<MovieInfo> movieInfoList) {
        binding.setMovieInfoList(movieInfoList);
    }

    @Override
    public void showError(Throwable throwable) {
        if (BuildConfig.DEBUG) {
            throwable.printStackTrace();
        }
        Toast.makeText(this, "에러가 발생했습니다.", Toast.LENGTH_SHORT).show();
    }
}
