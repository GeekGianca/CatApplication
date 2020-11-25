package com.gksoftwaresolutions.catapp.component;

import android.text.TextUtils;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.gksoftwaresolutions.catapp.util.Common.BASE_URL;

public class SustainableService {

    private static final Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

    private static Retrofit retrofit = null;

    private static final HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);


    private static final OkHttpClient.Builder httpClientBuilder =
            new OkHttpClient.Builder();

    public static <S> S createService(
            Class<S> serviceClass) {
        AuthenticationInterceptor interceptor =
                new AuthenticationInterceptor("450ef1ee-d2b4-4a6d-8e1a-d4d2a0f4c28c");
        if (!httpClientBuilder.interceptors().contains(interceptor)) {
            httpClientBuilder.addInterceptor(interceptor);
            retrofitBuilder.client(httpClientBuilder.build());
            retrofit = retrofitBuilder.build();
        }
        if (!httpClientBuilder.interceptors().contains(logging)) {
            httpClientBuilder.addInterceptor(logging);
            retrofitBuilder.client(httpClientBuilder.build());
            retrofit = retrofitBuilder.build();
        }

        return retrofit.create(serviceClass);
    }
}
