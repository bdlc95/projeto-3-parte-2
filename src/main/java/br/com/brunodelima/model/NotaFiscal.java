package br.com.brunodelima.model;

import java.util.List;

public class NotaFiscal {

    private String numero;
    private Cliente cliente;
    private List<Produto> produtos;

    public NotaFiscal(String numero, Cliente cliente, List<Produto> produtos) {
        this.numero = numero;
        this.cliente = cliente;
        this.produtos = produtos;
    }

    public String getNumero() { return numero; }
    public Cliente getCliente() { return cliente; }
    public List<Produto> getProdutos() { return produtos; }

    public Double calcularTotal() {
        return produtos.stream()
                .mapToDouble(Produto::getPreco)
                .sum();
    }

    @Override
    public String toString() {
        return "NotaFiscal{numero='" + numero +
                "', cliente=" + cliente.getNome() +
                ", total=" + calcularTotal() + "}";
    }
}