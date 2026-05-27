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
        Cliente cliente = new Cliente("Bruno", "123.456.789-00", "bruno@email.com", "(11) 98888-7777");
        String retorno = service.salvar(cliente);
        assertEquals("Sucesso", retorno);
    }

    @Test
    void deveBuscarClientePorCpf() {
        Cliente cliente = new Cliente("Bruno", "123.456.789-00", "bruno@email.com", "(11) 98888-7777");
        service.salvar(cliente);
        Cliente encontrado = service.buscarPorCpf("123.456.789-00");
        assertNotNull(encontrado);
        assertEquals("Bruno", encontrado.getNome());
    }

    @Test
    void deveExcluirClienteComSucesso() {
        Cliente cliente = new Cliente("Bruno", "123.456.789-00", "bruno@email.com", "(11) 98888-7777");
        service.salvar(cliente);
        String retorno = service.excluir("123.456.789-00");
        assertEquals("Sucesso", retorno);
        assertNull(service.buscarPorCpf("123.456.789-00"));
    }

    @Test
    void deveListarTodosOsClientes() {
        service.salvar(new Cliente("Bruno", "123.456.789-00", "bruno@email.com", "(11) 98888-7777"));
        service.salvar(new Cliente("Ana", "987.654.321-00", "ana@email.com", "(11) 97777-6666"));
        List<Cliente> clientes = service.listarTodos();
        assertEquals(2, clientes.size());
    }

    @Test
    void deveValidarTelefoneDoCliente() {
        Cliente cliente = new Cliente("Bruno", "123.456.789-00", "bruno@email.com", "(11) 98888-7777");
        service.salvar(cliente);
        Cliente encontrado = service.buscarPorCpf("123.456.789-00");
        assertNotNull(encontrado);
        assertEquals("(11) 98888-7777", encontrado.getTelefone());
    }
}