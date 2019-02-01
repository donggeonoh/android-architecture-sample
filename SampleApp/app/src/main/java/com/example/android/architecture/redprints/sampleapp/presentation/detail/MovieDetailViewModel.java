package com.example.android.architecture.redprints.sampleapp.presentation.detail;

import com.example.android.architecture.redprints.sampleapp.presentation.BaseViewModel;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * @author zeropol2
 * @since 2019. 2. 2.
 */
public class MovieDetailViewModel extends BaseViewModel {

    private MutableLiveData<String> linkUrl = new MutableLiveData<>();

    @NonNull
    public LiveData<String> getLinkUrl() {
        return linkUrl;
    }

    @MainThread
    public void setLinkUrl(String url) {
        this.linkUrl.setValue(url);
    }
}
