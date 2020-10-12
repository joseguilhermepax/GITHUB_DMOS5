package br.edu.dmos5.github_dmos5.dao;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

import br.edu.dmos5.github_dmos5.modelo.Repositorio;

public interface GithubAPI {

    String URL = "https://api.github.com/";

    @GET("users/{user}/repos")
    Call<List<Repositorio>> listarRepositorios(@Path("user") String user);
}
