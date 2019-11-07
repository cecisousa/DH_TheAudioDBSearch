package com.example.dh_theaudiodbsearch.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dh_theaudiodbsearch.model.pojos.Album;
import com.example.dh_theaudiodbsearch.model.repository.AlbumRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.dh_theaudiodbsearch.model.data.util.AppUtil.isNetworkConnected;

public class AlbumViewModel extends AndroidViewModel {

    private MutableLiveData<List<Album>> listaAlbuns = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private AlbumRepository repository = new AlbumRepository();

    public AlbumViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Album>> getListaAlbum() {
        return this.listaAlbuns;
    }

    public LiveData<Boolean> getLoading() {
        return this.loading;
    }

    public void getAlbuns(String item) {
        if (isNetworkConnected(getApplication())) {
            getFromNetwork(item);
        }
    }

    private void getFromNetwork(String item) {
        disposable.add(
                repository.getAlbuns(item)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(subscription -> {
                            loading.setValue(true);
                        })
                        .doAfterTerminate(() -> {
                            loading.setValue(false);
                        })
                        .subscribe(albumResult -> {
                            listaAlbuns.setValue(albumResult.getAlbum());
                        }, throwable -> {
                            Log.i("LOG", "getFromNetwork" + throwable.getMessage());
                        })
        );
    }

    public void getAllAlbuns(String artista) {
        disposable.add(
                repository.getAlbuns(artista)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable1 -> loading.setValue(true))
                        .doOnTerminate(() -> loading.setValue(false))
                        .subscribe(albumResult -> {
                            listaAlbuns.setValue(albumResult.getAlbum());
                        }, throwable -> {
                            Log.i("LOG", "Erro" + throwable.getMessage());
                        })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
