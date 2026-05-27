package br.com.brunodelima.dao;

import br.com.brunodelima.infra.ConexaoBanco;
import br.com.brunodelima.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDaoImpl implements IClienteDao {

    @Override
    public String salvar(Cliente cliente) {
        String sql = "INSERT INTO cliente (nome, cpf, email, telefone) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoBanco.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefone());
            stmt.executeUpdate();

            return "Cliente salvo com sucesso: " + cliente.getNome();

        } catch (SQLException e) {
            return "Erro ao salvar cliente: " + e.getMessage();
        }
    }

    @Override
    public String atualizar(Cliente cliente) {
        String sql = "UPDATE cliente SET nome = ?, email = ?, telefone = ? WHERE cpf = ?";

        try (Connection conn = ConexaoBanco.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getCpf());
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) return "Nenhum cliente encontrado com CPF: " + cliente.getCpf();
            return "Cliente atualizado com sucesso: " + cliente.getNome();

        } catch (SQLException e) {
            return "Erro ao atualizar cliente: " + e.getMessage();
        }
    }

    @Override
    public String excluir(String cpf) {
        String sql = "DELETE FROM cliente WHERE cpf = ?";

        try (Connection conn = ConexaoBanco.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) return "Nenhum cliente encontrado com CPF: " + cpf;
            return "Cliente excluído com sucesso.";

        } catch (SQLException e) {
            return "Erro ao excluir cliente: " + e.getMessage();
        }
    }

    @Override
    public Cliente buscarPorCpf(String cpf) {
        String sql = "SELECT nome, cpf, email, telefone FROM cliente WHERE cpf = ?";

        try (Connection conn = ConexaoBanco.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Cliente(
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("email"),
                        rs.getString("telefone")
                );
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Cliente> listarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT nome, cpf, email, telefone FROM cliente";

        try (Connection conn = ConexaoBanco.obterConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                clientes.add(new Cliente(
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("email"),
                        rs.getString("telefone")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar clientes: " + e.getMessage());
        }

        return clientes;
    }
}