package com.example.dh_theaudiodbsearch.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dh_theaudiodbsearch.R;
import com.example.dh_theaudiodbsearch.model.pojos.Album;
import com.example.dh_theaudiodbsearch.view.adapter.AlbumRecyclerViewAdapter;
import com.example.dh_theaudiodbsearch.view.interfaces.OnClick;
import com.example.dh_theaudiodbsearch.viewmodel.AlbumViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.dh_theaudiodbsearch.view.activity.LoginActivity.GOOGLE_ACCOUNT;

public class MainActivity extends AppCompatActivity implements OnClick {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SearchView searchView;
    private AlbumViewModel viewModel;
    private List<Album> listaAlbuns = new ArrayList<>();
    private AlbumRecyclerViewAdapter adapter;
    private String itemBusca = "Iron Maiden";
    private ImageView imgUsuario;
    private TextView nomeUsuario;
    private Button btnSair;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        pegaOsDados();

        btnSair.setOnClickListener(v -> {
            googleSignInClient.signOut().addOnCompleteListener(task -> {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            });
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        viewModel.getAlbuns(itemBusca);
        viewModel.getAllAlbuns(itemBusca);

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

        viewModel.getErrorAlbum().observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_LONG);
        });


        //Chamada do editTextSearch
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                itemBusca = query;
                adapter.clear();
                viewModel.getAlbuns(itemBusca);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 3) {
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
        imgUsuario = findViewById(R.id.imageProfile);
        nomeUsuario = findViewById(R.id.textViewNome);
        btnSair = findViewById(R.id.buttonSair);
    }

    private void pegaOsDados() {
        GoogleSignInAccount googleSignInAccount = getIntent().getParcelableExtra(GOOGLE_ACCOUNT);
        Picasso.get().load(googleSignInAccount.getPhotoUrl()).centerInside().fit().into(imgUsuario);
        nomeUsuario.setText(googleSignInAccount.getDisplayName());
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
