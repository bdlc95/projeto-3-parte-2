package br.com.brunodelima.dao;

import br.com.brunodelima.model.Produto;
import java.util.List;

public interface IProdutoDao {
    String salvar(Produto produto);
    String atualizar(Produto produto);
    String excluir(String nome);
    Produto buscarPorNome(String nome);
    List<Produto> listarTodos();
}