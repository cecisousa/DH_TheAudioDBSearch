package com.example.dh_theaudiodbsearch.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dh_theaudiodbsearch.R;
import com.example.dh_theaudiodbsearch.model.pojos.Album;
import com.example.dh_theaudiodbsearch.view.adapter.AlbumRecyclerViewAdapter;
import com.example.dh_theaudiodbsearch.view.interfaces.OnClick;
import com.example.dh_theaudiodbsearch.viewmodel.AlbumViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClick {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SearchView searchView;
    private AlbumViewModel viewModel;
    private List<Album> listaAlbuns = new ArrayList<>();
    private AlbumRecyclerViewAdapter adapter;
    private String itemBusca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        viewModel.getAlbuns(itemBusca);
        viewModel.getAllAlbuns("Iron Maiden");
        viewModel.getListaAlbum().observe(this, resultadoLista -> {
            adapter.atualizaLista(resultadoLista);
        });

        viewModel.getLoading().observe(this, loading -> {
            if (loading) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });


        //Chamada do editTextSearch
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                itemBusca = query;
                //Chama o método clear do adapter para limpar a lista
                adapter.clear();
                viewModel.getAlbuns(itemBusca);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.length() > 3){
                    itemBusca = newText;
                    adapter.clear();
                    viewModel.getAlbuns(itemBusca);
                }
                return false;
            }
        });

    }

    public void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        searchView = findViewById(R.id.searchView);
        viewModel = ViewModelProviders.of(this).get(AlbumViewModel.class);
        adapter = new AlbumRecyclerViewAdapter(listaAlbuns, this);
    }

    @Override
    public void click(Album album) {
        Intent intent = new Intent(MainActivity.this, DetalheActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("Álbum", album);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
