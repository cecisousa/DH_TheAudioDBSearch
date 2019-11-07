
package com.example.dh_theaudiodbsearch.model.pojos;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Artista {

    @Expose
    private List<Album> album;

    public List<Album> getAlbum() {
        if (album == null) {
            return new ArrayList<>();
        }
        return album;
    }

    public void setAlbum(List<Album> album) {
        this.album = album;
    }

}
