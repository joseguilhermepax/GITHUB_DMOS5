package br.edu.dmos5.github_dmos5.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import br.edu.dmos5.github_dmos5.R;
import br.edu.dmos5.github_dmos5.dao.GithubAPI;
import br.edu.dmos5.github_dmos5.modelo.Repositorio;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_PERMISSION = 64;
    private ListaAdapter adapter;

    private EditText NomeUsuarioEditText;
    private Button BuscarButton;
    private RecyclerView listaRepositorioRecyclerView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaRepositorioRecyclerView = findViewById(R.id.lista_repositorio);
        NomeUsuarioEditText          = findViewById(R.id.edittext_usuario);
        BuscarButton                 = findViewById(R.id.button_buscar);

        adapter = new ListaAdapter(this, null);

        listaRepositorioRecyclerView.setAdapter(adapter);

        BuscarButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        String usuario = NomeUsuarioEditText.getText().toString();
        
        switch (view.getId()){

            case R.id.button_buscar:

                if (checkPermission()) {

                    if (!usuario.isEmpty()) {
                        SearchRepositories(usuario);
                    }
                    else {
                        Toast.makeText(this, "Digite um nome de usuário", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    requestPermission();
                }
        }
    }
    
    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {

            final Activity activity = this;

            new AlertDialog.Builder(this)
            .setMessage("Este aplicativo necessita de acesso a internet")
            .setPositiveButton("Permitir", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.INTERNET}, REQUEST_PERMISSION);
                }
            })
            .setNegativeButton("Não permitir", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            })
            .show();
        }
        else {

            ActivityCompat.requestPermissions(
            this,
            new String[]{
                    Manifest.permission.INTERNET
            },
            REQUEST_PERMISSION);
        }
    }

    private boolean checkPermission() {

        return ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED;
    }

    private void SearchRepositories(String usuario) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(GithubAPI.URL).addConverterFactory(GsonConverterFactory.create())
        .build();

        GithubAPI service = retrofit.create(GithubAPI.class);

        final Call<List<Repositorio>> repositorio = service.listarRepositorios(usuario);

        repositorio.enqueue(new Callback<List<Repositorio>>() {

            @Override
            public void onResponse(Call<List<Repositorio>> call, Response<List<Repositorio>> response) {
                if (response.isSuccessful()) {
                    List<Repositorio> repositorios = response.body();
                    adapter.atualizaRepositorio(repositorios);
                }
            }

            @Override
            public void onFailure(Call<List<Repositorio>> call, Throwable t) {
                Toast.makeText(
                        MainActivity.this,
                        "Erro ao buscar usuário",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}