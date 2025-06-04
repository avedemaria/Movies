package com.example.movies.presentation.detailedInfoScreen;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movies.data.Movie;
import com.example.movies.data.localSource.MovieDatabase;
import com.example.movies.data.localSource.MoviesDao;
import com.example.movies.data.remoteSource.ReviewCard;
import com.example.movies.data.remoteSource.ReviewsResponse;
import com.example.movies.data.remoteSource.Trailer;
import com.example.movies.data.remoteSource.VideosResponse;
import com.example.movies.data.remoteSource.ApiFactory;

import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailedInfoViewModel extends AndroidViewModel {


    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<List<Trailer>> trailersLD = new MutableLiveData<>();

    public LiveData<List<Trailer>> getTrailersLD() {
        return trailersLD;
    }

    private MutableLiveData<List<ReviewCard>> reviewsLD = new MutableLiveData<>();

    public MutableLiveData<List<ReviewCard>> getReviewsLD() {
        return reviewsLD;
    }

    private final MoviesDao moviesDao;



    public DetailedInfoViewModel(@NonNull Application application) {
        super(application);
        moviesDao = MovieDatabase.getMovieDatabase(getApplication()).moviesDao();
    }

    public LiveData<Movie> getFavoriteMovie (int id) {
        return moviesDao.getMovieById(id);
    }


    public void addFavoriteMovieToDb (Movie movie) {
      Disposable disposable = moviesDao.insertMovie(movie)
              .subscribeOn(Schedulers.io())
              .subscribe(new Action() {
                  @Override
                  public void run() throws Throwable {

                  }
              }, new Consumer<Throwable>() {
                  @Override
                  public void accept(Throwable throwable) throws Throwable {

                  }
              });

      compositeDisposable.add(disposable);
    }

    public void removeFavoriteMovieFromDb (int id) {
        Disposable disposable = moviesDao.deleteMovie(id)
                .subscribeOn(Schedulers.io())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {

                    }
                });

        compositeDisposable.add(disposable);
    }

    public void loadTrailers(int id) {
        Disposable disposable = ApiFactory.getApiService().loadTrailers(id).
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).map(new Function<VideosResponse, List<Trailer>>() {
                    @Override
                    public List<Trailer> apply(VideosResponse videosResponse) throws Throwable {
                        return videosResponse.getTrailersList().getTrailers();
                    }
                })
                .subscribe(new Consumer<List<Trailer>>() {
                    @Override
                    public void accept(List<Trailer> trailers) throws Throwable {
                        trailersLD.setValue(trailers);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d("DetailedInfo", throwable.toString());
                    }
                });
        compositeDisposable.add(disposable);
    }


    public void loadReviews (int id) {
        Disposable disposable = ApiFactory.getApiService().loadReviews(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ReviewsResponse, List<ReviewCard>>() {
                    @Override
                    public List<ReviewCard> apply(ReviewsResponse reviewsResponse) throws Throwable {
                        return reviewsResponse.getReviews();
                    }
                }).subscribe(new Consumer<List<ReviewCard>>() {
                    @Override
                    public void accept(List<ReviewCard> reviewCards) throws Throwable {
                        reviewsLD.setValue(reviewCards);
                        Log.d("DetailedInfoViewModel", reviewCards.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                       Log.d("DetailedInfoViewModel", throwable.toString());
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

}
