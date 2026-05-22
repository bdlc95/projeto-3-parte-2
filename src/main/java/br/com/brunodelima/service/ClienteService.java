package br.com.brunodelima.service;

import br.com.brunodelima.dao.IClienteDao;
import br.com.brunodelima.model.Cliente;
import java.util.List;

public class ClienteService implements IClienteService {

    private IClienteDao dao;

    public ClienteService(IClienteDao dao) {
        this.dao = dao;
    }

    @Override
    public String salvar(Cliente cliente) {
        return dao.salvar(cliente);
    }

    @Override
    public String atualizar(Cliente cliente) {
        return dao.atualizar(cliente);
    }

    @Override
    public String excluir(String cpf) {
        return dao.excluir(cpf);
    }

    @Override
    public Cliente buscarPorCpf(String cpf) {
        return dao.buscarPorCpf(cpf);
    }

    @Override
    public List<Cliente> listarTodos() {
        return dao.listarTodos();
    }
}