package br.com.brunodelima.dao;

import br.com.brunodelima.model.Produto;
import java.util.List;

public interface IProdutoDao {
    String salvar(Produto produto);
    List<Produto> listarTodos();
}