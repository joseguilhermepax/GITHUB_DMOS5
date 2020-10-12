package br.edu.dmos5.github_dmos5.modelo;

public class Repositorio {

    private String name;

    public Repositorio(String nome) {
        this.name = nome;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}