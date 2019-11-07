package com.example.dh_theaudiodbsearch.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dh_theaudiodbsearch.R;
import com.example.dh_theaudiodbsearch.model.pojos.Album;
import com.example.dh_theaudiodbsearch.view.interfaces.OnClick;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AlbumRecyclerViewAdapter extends RecyclerView.Adapter<AlbumRecyclerViewAdapter.ViewHolder> {
    private List<Album> listaAlbuns;
    private OnClick listener;

    public AlbumRecyclerViewAdapter(List<Album> listaAlbuns, OnClick listener){
        this.listaAlbuns = listaAlbuns;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Album album = listaAlbuns.get(position);
        holder.onBind(album);
        holder.itemView.setOnClickListener(v-> listener.click(album));
    }

    @Override
    public int getItemCount() {
        return listaAlbuns.size();
    }

    public void atualizaLista(List<Album> novaLista) {
        this.listaAlbuns.clear();
        this.listaAlbuns = novaLista;
        notifyDataSetChanged();
    }

    public void clear() {
        this.listaAlbuns.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagem;
        private TextView texto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagem = itemView.findViewById(R.id.imgAlbum);
            texto = itemView.findViewById(R.id.txtTitulo);
        }

        public void onBind(Album album) {
            Picasso.get().load(album.getStrAlbumThumb()).into(imagem);
            texto.setText(album.getStrAlbum());
        }
    }
}
