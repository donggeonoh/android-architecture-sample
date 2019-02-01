package com.example.android.architecture.redprints.sampleapp.presentation.movielist;

import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.android.architecture.redprints.sampleapp.R;
import com.example.android.architecture.redprints.sampleapp.data.model.MovieInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

/**
 * @author zeropol2
 * @since 2019. 2. 2.
 */
class MovieListAdapter extends ListAdapter<MovieInfo, MovieItemViewHolder> {

    MovieListAdapter() {
        super(DIFF_CALLBACK);
    }

    interface OnItemClickListener {
        void onItemClick(MovieInfo movieInfo);
        void onItemLongClick(MovieInfo movieInfo);
    }

    @Nullable
    private OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public MovieItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_movie_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieItemViewHolder holder, int position) {
        final MovieInfo data = getItem(position);
        holder.textView.setText(Html.fromHtml(data.getTitle()));
        holder.textView.setTextColor(data.isFavorite() ? Color.RED : Color.BLACK);
        holder.textView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(getItem(holder.getAdapterPosition()));
            }
        });
        holder.textView.setOnLongClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemLongClick(getItem(holder.getAdapterPosition()));
                return true;
            }
            return false;
        });
    }

    void setOnItemClickListener(@Nullable OnItemClickListener l) {
        this.onItemClickListener = l;
    }

    private static final DiffUtil.ItemCallback<MovieInfo> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<MovieInfo>() {

                @Override
                public boolean areItemsTheSame(@NonNull MovieInfo oldItem, @NonNull MovieInfo newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull MovieInfo oldItem, @NonNull MovieInfo newItem) {
                    return oldItem.equals(newItem);
                }
            };
}
