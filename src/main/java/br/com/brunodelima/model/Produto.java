package br.com.brunodelima.model;

public class Produto {

    private String nome;
    private Double preco;
    private String marca; // campo novo

    public Produto(String nome, Double preco, String marca) {
        this.nome = nome;
        this.preco = preco;
        this.marca = marca;
    }

    public String getNome()  { return nome; }
    public Double getPreco() { return preco; }
    public String getMarca() { return marca; }

    @Override
    public String toString() {
        return "Produto{nome='" + nome + "', preco=" + preco + ", marca='" + marca + "'}";
    }
}