package com.example.android.architecture.redprints.sampleapp.presentation.detail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebViewClient;

import com.example.android.architecture.redprints.sampleapp.R;
import com.example.android.architecture.redprints.sampleapp.databinding.ActivityMovieDetailBinding;
import com.example.android.architecture.redprints.sampleapp.presentation.BaseActivity;

import androidx.annotation.NonNull;

/**
 * @author zeropol2
 * @since 2019. 2. 2.
 */
public class MovieDetailActivity extends BaseActivity<ActivityMovieDetailBinding> {

    private static final String EXTRA_LINK_URL = "EXTRA_LINK_URL";

    public static Intent getLaunchIntent(@NonNull final Context context,
                                         @NonNull final String linkUrl) {
        final Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_LINK_URL, linkUrl);
        return intent;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMovieDetailBinding binding = binding(R.layout.activity_movie_detail);
        binding.webView.setWebViewClient(new WebViewClient());
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.setLinkUrl(getIntent().getStringExtra(EXTRA_LINK_URL));
    }
}
