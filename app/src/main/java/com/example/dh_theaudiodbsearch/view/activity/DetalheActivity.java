package com.example.dh_theaudiodbsearch.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dh_theaudiodbsearch.R;
import com.example.dh_theaudiodbsearch.model.pojos.Album;
import com.squareup.picasso.Picasso;

public class DetalheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        ImageView imageView = findViewById(R.id.imgDetalhe);

        if (getIntent() != null) {
            Album album = getIntent().getParcelableExtra("Álbum");
            Toast.makeText(this, "Álbum: " + album.getStrAlbum(), Toast.LENGTH_LONG).show();

            Picasso.get().load(album.getStrAlbumThumb()).into(imageView);

        }
    }
}
