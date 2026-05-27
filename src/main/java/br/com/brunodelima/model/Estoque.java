package br.com.brunodelima.model;

public class Estoque {

    private Produto produto;
    private int quantidade;

    public Estoque(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto()   { return produto; }
    public int getQuantidade()    { return quantidade; }

    @Override
    public String toString() {
        return "Estoque{produto='" + produto.getNome() +
                "', marca='" + produto.getMarca() +
                "', quantidade=" + quantidade + "}";
    }
}