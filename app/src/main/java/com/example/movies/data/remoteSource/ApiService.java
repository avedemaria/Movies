package com.example.movies.data.remoteSource;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET ("movie?token=4BEYKHY-CZRM08H-KAVD6F5-DBZAX1V&field=rating.kp&search=4-9&sortField=votes.kp&sortType=-1&limit=40")
    Single<MovieResponse> loadMovies (@Query("page") int page);

    @GET ("movie/{id}?token=4BEYKHY-CZRM08H-KAVD6F5-DBZAX1V&page=1&limit=30")
    Single<VideosResponse> loadTrailers (@Path("id") int id);

    @GET ("review?token=4BEYKHY-CZRM08H-KAVD6F5-DBZAX1V")
    Single<ReviewsResponse> loadReviews (@Query("movieId") int id);



}
