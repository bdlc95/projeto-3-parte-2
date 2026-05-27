package br.com.brunodelima;

import br.com.brunodelima.dao.*;
import br.com.brunodelima.model.*;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        IClienteDao    clienteDao = new ClienteDaoImpl();
        IProdutoDao    produtoDao = new ProdutoDaoImpl();
        INotaFiscalDao notaDao    = new NotaFiscalDaoImpl();

        // --- CADASTROS ---
        Cliente cliente = new Cliente("Bruce Lima", "123.456.789-00", "bruce@email.com", "(19) 99999-8888");
        Produto p1      = new Produto("Teclado Mecânico", 350.00, "Redragon");
        Produto p2      = new Produto("Mouse Gamer", 180.00, "Logitech");

        System.out.println(clienteDao.salvar(cliente));
        System.out.println(produtoDao.salvar(p1));
        System.out.println(produtoDao.salvar(p2));

        NotaFiscal nota = new NotaFiscal("NF-001", cliente, List.of(p1, p2));
        System.out.println(notaDao.salvar(nota));

        // --- LISTAGENS ---
        System.out.println("\n--- CLIENTES ---");
        clienteDao.listarTodos().forEach(System.out::println);

        System.out.println("\n--- PRODUTOS ---");
        produtoDao.listarTodos().forEach(System.out::println);

        System.out.println("\n--- NOTAS FISCAIS ---");
        notaDao.listarTodos().forEach(System.out::println);
    }
}