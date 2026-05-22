package br.com.brunodelima.service;

import br.com.brunodelima.model.Cliente;
import java.util.List;

public interface IClienteService {
    String salvar(Cliente cliente);
    String atualizar(Cliente cliente);
    String excluir(String cpf);
    Cliente buscarPorCpf(String cpf);
    List<Cliente> listarTodos();
}