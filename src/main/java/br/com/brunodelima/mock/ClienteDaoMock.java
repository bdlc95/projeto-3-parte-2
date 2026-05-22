package br.com.brunodelima.mock;

import br.com.brunodelima.dao.IClienteDao;
import br.com.brunodelima.model.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteDaoMock implements IClienteDao {

    private List<Cliente> clientes = new ArrayList<>();

    @Override
    public String salvar(Cliente cliente) {
        clientes.add(cliente);
        return "Sucesso";
    }

    @Override
    public String atualizar(Cliente cliente) {
        return "Sucesso";
    }

    @Override
    public String excluir(String cpf) {
        clientes.removeIf(c -> c.getCpf().equals(cpf));
        return "Sucesso";
    }

    @Override
    public Cliente buscarPorCpf(String cpf) {
        return clientes.stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Cliente> listarTodos() {
        return clientes;
    }
}