package br.com.brunodelima.mock;

import br.com.brunodelima.dao.INotaFiscalDao;
import br.com.brunodelima.model.NotaFiscal;

import java.util.ArrayList;
import java.util.List;

public class NotaFiscalDaoMock implements INotaFiscalDao {

    private List<NotaFiscal> notas = new ArrayList<>();

    @Override
    public String salvar(NotaFiscal notaFiscal) {
        notas.add(notaFiscal);
        return "Sucesso";
    }

    @Override
    public String excluir(String numero) {
        notas.removeIf(n -> n.getNumero().equals(numero));
        return "Sucesso";
    }

    @Override
    public NotaFiscal buscarPorNumero(String numero) {
        return notas.stream()
                .filter(n -> n.getNumero().equals(numero))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<NotaFiscal> listarTodos() {
        return notas;
    }
}