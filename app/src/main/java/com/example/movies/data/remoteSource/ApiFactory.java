package com.example.movies.data.remoteSource;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {
    private static final String BASE_URL = "https://api.kinopoisk.dev/v1.4/";
    private static ApiService apiService = null;

    public static ApiService getApiService () {
        if (apiService == null) {
            return new Retrofit.Builder().baseUrl(BASE_URL).
                    addConverterFactory(GsonConverterFactory.create()).
                    addCallAdapterFactory(RxJava3CallAdapterFactory.create()).
                    build().create(ApiService.class);
        }
        return apiService;
    }
}
