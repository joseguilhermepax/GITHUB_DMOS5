package br.edu.dmos5.github_dmos5.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.edu.dmos5.github_dmos5.R;
import br.edu.dmos5.github_dmos5.modelo.Repositorio;

public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.RepositoryViewHolder> {

    private List<Repositorio> repositorios;
    private final Context context;

    public ListaAdapter(Context context, List<Repositorio> repositorios) {
        this.context = context;
        this.repositorios = repositorios;
    }

    @Override
    public ListaAdapter.RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context)
        .inflate(R.layout.activity_lista_adapter, parent, false);

        return new RepositoryViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(ListaAdapter.RepositoryViewHolder holder, int position) {
        Repositorio repositorio = repositorios.get(position);

        holder.textNomeRepositorio.setText(repositorio.getName());
    }

    @Override
    public int getItemCount() {
        if (repositorios != null) {
            return repositorios.size();
        }
        return 0;
    }

    class RepositoryViewHolder extends RecyclerView.ViewHolder {

        private final TextView textNomeRepositorio;

        public RepositoryViewHolder(View itemView) {
            super(itemView);
            textNomeRepositorio = itemView.findViewById(R.id.text_nome_repositorio);
        }
    }

    public void atualizaRepositorio(List<Repositorio> repositorios) {

        this.repositorios = repositorios;
        notifyDataSetChanged();
    }
}