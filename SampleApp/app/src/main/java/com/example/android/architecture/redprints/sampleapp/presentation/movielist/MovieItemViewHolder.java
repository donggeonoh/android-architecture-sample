package com.example.android.architecture.redprints.sampleapp.presentation.movielist;

import android.view.View;
import android.widget.TextView;

import com.example.android.architecture.redprints.sampleapp.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author zeropol2
 * @since 2019. 2. 2.
 */
class MovieItemViewHolder extends RecyclerView.ViewHolder {

    TextView textView;

    MovieItemViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.titleView);
    }


}
