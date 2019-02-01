package com.example.android.architecture.redprints.sampleapp.data.remote.thirdparty.naver;

import com.example.android.architecture.redprints.sampleapp.BuildConfig;
import com.example.android.architecture.redprints.sampleapp.data.remote.thirdparty.naver.request.SearchMovieListRequest;
import com.example.android.architecture.redprints.sampleapp.data.remote.thirdparty.naver.response.SearchMovieListResponse;

import androidx.annotation.NonNull;
import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author zeropol2
 * @since 2019. 2. 1.
 */
public class NaverApiClient {
    private static final String BASE_URL = "https://openapi.naver.com/v1/";
    private static final String CLIENT_ID = BuildConfig.CLIENT_ID;
    private static final String CLIENT_SECRET = BuildConfig.CLIENT_SECRET;

    @NonNull
    private final Retrofit retrofit;

    @NonNull
    public static NaverApiClient getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final NaverApiClient INSTANCE = new NaverApiClient();
    }

    private NaverApiClient() {
        final OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(chain -> {
            final Request.Builder builder = chain.request().newBuilder()
                    .header("X-Naver-Client-Id", CLIENT_ID)
                    .header("X-Naver-Client-Secret", CLIENT_SECRET);
            return chain.proceed(builder.build());
        });
        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)).build();
        }
        final Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClientBuilder.build());
        retrofit = retrofitBuilder.build();
    }

    public Single<SearchMovieListResponse> searchMovieList(@NonNull SearchMovieListRequest request) {
        return retrofit.create(SearchApi.class)
                .searchMovieList(request.getQuery());
    }
}
