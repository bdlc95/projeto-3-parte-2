package br.com.brunodelima.dao;

import br.com.brunodelima.model.Cliente;
import java.util.List;

public interface IClienteDao {
    String salvar(Cliente cliente);
    String atualizar(Cliente cliente);
    String excluir(String cpf);
    Cliente buscarPorCpf(String cpf);
    List<Cliente> listarTodos();
}