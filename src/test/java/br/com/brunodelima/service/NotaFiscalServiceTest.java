package br.com.brunodelima.service;

import br.com.brunodelima.mock.NotaFiscalDaoMock;
import br.com.brunodelima.model.Cliente;
import br.com.brunodelima.model.NotaFiscal;
import br.com.brunodelima.model.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NotaFiscalServiceTest {

    private INotaFiscalService service;

    @BeforeEach
    void setUp() {
        service = new NotaFiscalService(new NotaFiscalDaoMock());
    }

    private NotaFiscal criarNotaFiscal(String numero) {
        Cliente cliente = new Cliente("Bruno", "123.456.789-00", "bruno@email.com");
        List<Produto> produtos = List.of(
                new Produto("Notebook", 3500.00),
                new Produto("Mouse", 150.00)
        );
        return new NotaFiscal(numero, cliente, produtos);
    }

    @Test
    void deveSalvarNotaFiscalComSucesso() {
        NotaFiscal nota = criarNotaFiscal("NF-001");
        String retorno = service.salvar(nota);
        assertEquals("Sucesso", retorno);
    }

    @Test
    void deveBuscarNotaFiscalPorNumero() {
        NotaFiscal nota = criarNotaFiscal("NF-001");
        service.salvar(nota);
        NotaFiscal encontrada = service.buscarPorNumero("NF-001");
        assertNotNull(encontrada);
        assertEquals("NF-001", encontrada.getNumero());
    }

    @Test
    void deveCalcularTotalDaNotaFiscal() {
        NotaFiscal nota = criarNotaFiscal("NF-001");
        service.salvar(nota);
        NotaFiscal encontrada = service.buscarPorNumero("NF-001");
        assertEquals(3650.00, encontrada.calcularTotal());
    }

    @Test
    void deveExcluirNotaFiscalComSucesso() {
        NotaFiscal nota = criarNotaFiscal("NF-001");
        service.salvar(nota);
        String retorno = service.excluir("NF-001");
        assertEquals("Sucesso", retorno);
        assertNull(service.buscarPorNumero("NF-001"));
    }

    @Test
    void deveListarTodasAsNotasFiscais() {
        service.salvar(criarNotaFiscal("NF-001"));
        service.salvar(criarNotaFiscal("NF-002"));
        List<NotaFiscal> notas = service.listarTodos();
        assertEquals(2, notas.size());
    }
}