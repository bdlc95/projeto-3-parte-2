package br.com.brunodelima.service;

import br.com.brunodelima.model.NotaFiscal;
import java.util.List;

public interface INotaFiscalService {
    String salvar(NotaFiscal notaFiscal);
    String excluir(String numero);
    NotaFiscal buscarPorNumero(String numero);
    List<NotaFiscal> listarTodos();
}