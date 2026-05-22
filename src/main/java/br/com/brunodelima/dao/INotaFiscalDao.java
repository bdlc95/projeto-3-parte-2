package br.com.brunodelima.dao;

import br.com.brunodelima.model.NotaFiscal;
import java.util.List;

public interface INotaFiscalDao {
    String salvar(NotaFiscal notaFiscal);
    String excluir(String numero);
    NotaFiscal buscarPorNumero(String numero);
    List<NotaFiscal> listarTodos();
}