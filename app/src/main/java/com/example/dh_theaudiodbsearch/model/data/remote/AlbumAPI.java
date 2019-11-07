package com.example.dh_theaudiodbsearch.model.data.remote;

import com.example.dh_theaudiodbsearch.model.pojos.Artista;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AlbumAPI {

    @GET("searchalbum.php")
    Observable<Artista> getAllAlbumsArtist(
            @Query("s") String artista);

}
