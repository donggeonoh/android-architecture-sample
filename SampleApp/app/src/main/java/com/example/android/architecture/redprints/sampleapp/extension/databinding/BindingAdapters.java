package com.example.android.architecture.redprints.sampleapp.extension.databinding;

import android.webkit.WebView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author zeropol2
 * @since 2019. 2. 2.
 */
public class BindingAdapters {

    @SuppressWarnings("unchecked")
    @BindingAdapter("items")
    public static <T, VH extends RecyclerView.ViewHolder> void setItems(
            @NonNull final RecyclerView recyclerView,
            @Nullable final List<T> items) {
        final ListAdapter<T, VH> adapter = (ListAdapter<T, VH>) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.submitList(items == null ? null : new ArrayList<>(items));
        }
    }

    @BindingAdapter("url")
    public static void loadUrl(
            @NonNull final WebView webView,
            @Nullable final String url) {
        if (url != null) {
            webView.loadUrl(url);
        }
    }
}
