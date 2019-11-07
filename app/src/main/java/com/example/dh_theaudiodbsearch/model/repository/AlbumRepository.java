package com.example.dh_theaudiodbsearch.model.repository;

import com.example.dh_theaudiodbsearch.model.pojos.Artista;

import io.reactivex.Observable;

import static com.example.dh_theaudiodbsearch.model.data.remote.RetrofitService.getApiService;

public class AlbumRepository {

    public Observable<Artista> getAlbuns (String artista) {
        return getApiService().getAllAlbumsArtist(artista);
    }

}
