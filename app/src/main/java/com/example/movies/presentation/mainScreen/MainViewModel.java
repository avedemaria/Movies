package com.example.movies.presentation.mainScreen;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movies.data.Movie;
import com.example.movies.data.remoteSource.MovieResponse;
import com.example.movies.data.remoteSource.ApiFactory;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private int page = 1;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private MutableLiveData<Boolean> isProgressVisible = new MutableLiveData<>();

    private final MutableLiveData<List<Movie>> moviesLD = new MutableLiveData<>();

    private static final String TAG = "MainViewModel";

    public MainViewModel(@NonNull Application application) {
        super(application);
        loadMovies();
    }

    public LiveData<List<Movie>> getMoviesLD() {
        return moviesLD;
    }

    public MutableLiveData<Boolean> getIsProgressVisible() {
        return isProgressVisible;
    }

    public void loadMovies() {
        Boolean isLoading = isProgressVisible.getValue();
        if (isLoading!= null && isLoading) {
            return;
        }
        Disposable disposable = loadMoviesRx(page).
                subscribeOn(Schedulers.io()).
                doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Throwable {
                        isProgressVisible.setValue(true);
                    }
                }).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Consumer<MovieResponse>() {
                    @Override
                    public void accept(MovieResponse movieResponse) throws Throwable {
                        Log.d("MainViewModel", movieResponse.toString());

                        List<Movie> loadedMovies = moviesLD.getValue();
                        if (loadedMovies != null) {
                            loadedMovies.addAll(movieResponse.getMovies());
                            moviesLD.setValue(loadedMovies);
                        } else {
                            moviesLD.setValue(movieResponse.getMovies());
                        }
                        isProgressVisible.setValue(false);
                        page++;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.toString());
                        isProgressVisible.setValue(false);
                    }
                });
        compositeDisposable.add(disposable);
    }


    private Single<MovieResponse> loadMoviesRx(int page) {
        return ApiFactory.getApiService().loadMovies(page);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
