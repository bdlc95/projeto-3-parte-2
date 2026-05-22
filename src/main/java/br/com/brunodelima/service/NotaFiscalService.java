package br.com.brunodelima.service;

import br.com.brunodelima.dao.INotaFiscalDao;
import br.com.brunodelima.model.NotaFiscal;
import java.util.List;

public class NotaFiscalService implements INotaFiscalService {

    private INotaFiscalDao dao;

    public NotaFiscalService(INotaFiscalDao dao) {
        this.dao = dao;
    }

    @Override
    public String salvar(NotaFiscal notaFiscal) {
        return dao.salvar(notaFiscal);
    }

    @Override
    public String excluir(String numero) {
        return dao.excluir(numero);
    }

    @Override
    public NotaFiscal buscarPorNumero(String numero) {
        return dao.buscarPorNumero(numero);
    }

    @Override
    public List<NotaFiscal> listarTodos() {
        return dao.listarTodos();
    }
}