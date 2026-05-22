package br.com.brunodelima.service;

import br.com.brunodelima.mock.ClienteDaoMock;
import br.com.brunodelima.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClienteServiceTest {

    private IClienteService service;

    @BeforeEach
    void setUp() {
        service = new ClienteService(new ClienteDaoMock());
    }

    @Test
    void deveSalvarClienteComSucesso() {
        Cliente cliente = new Cliente("Bruno", "123.456.789-00", "bruno@email.com");
        String retorno = service.salvar(cliente);
        assertEquals("Sucesso", retorno);
    }

    @Test
    void deveBuscarClientePorCpf() {
        Cliente cliente = new Cliente("Bruno", "123.456.789-00", "bruno@email.com");
        service.salvar(cliente);
        Cliente encontrado = service.buscarPorCpf("123.456.789-00");
        assertNotNull(encontrado);
        assertEquals("Bruno", encontrado.getNome());
    }

    @Test
    void deveExcluirClienteComSucesso() {
        Cliente cliente = new Cliente("Bruno", "123.456.789-00", "bruno@email.com");
        service.salvar(cliente);
        String retorno = service.excluir("123.456.789-00");
        assertEquals("Sucesso", retorno);
        assertNull(service.buscarPorCpf("123.456.789-00"));
    }

    @Test
    void deveListarTodosOsClientes() {
        service.salvar(new Cliente("Bruno", "111.111.111-11", "bruno@email.com"));
        service.salvar(new Cliente("Ana", "222.222.222-22", "ana@email.com"));
        List<Cliente> clientes = service.listarTodos();
        assertEquals(2, clientes.size());
    }
}