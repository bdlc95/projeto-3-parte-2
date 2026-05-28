package br.com.brunodelima.dao;

import br.com.brunodelima.infra.ConexaoBanco;
import br.com.brunodelima.model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDaoImpl implements IProdutoDao {

    @Override
    public String salvar(Produto produto) {
        String sqlProduto = "INSERT INTO produto (nome, preco, marca) VALUES (?, ?, ?) RETURNING id";
        String sqlEstoque = "INSERT INTO estoque (produto_id, quantidade) VALUES (?, 0)";

        try (Connection conn = ConexaoBanco.obterConexao()) {

            conn.setAutoCommit(false);

            int produtoId;
            try (PreparedStatement stmt = conn.prepareStatement(sqlProduto)) {
                stmt.setString(1, produto.getNome());
                stmt.setDouble(2, produto.getPreco());
                stmt.setString(3, produto.getMarca());
                ResultSet rs = stmt.executeQuery();
                rs.next();
                produtoId = rs.getInt("id");
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlEstoque)) {
                stmt.setInt(1, produtoId);
                stmt.executeUpdate();
            }

            conn.commit();
            return "Produto salvo com sucesso: " + produto.getNome();

        } catch (SQLException e) {
            return "Erro ao salvar produto: " + e.getMessage();
        }
    }

    @Override
    public String atualizar(Produto produto) {
        String sql = "UPDATE produto SET preco = ?, marca = ? WHERE nome = ?";

        try (Connection conn = ConexaoBanco.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, produto.getPreco());
            stmt.setString(2, produto.getMarca());
            stmt.setString(3, produto.getNome());
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) return "Nenhum produto encontrado com nome: " + produto.getNome();
            return "Produto atualizado com sucesso: " + produto.getNome();

        } catch (SQLException e) {
            return "Erro ao atualizar produto: " + e.getMessage();
        }
    }

    @Override
    public String excluir(String nome) {
        String sqlEstoque = "DELETE FROM estoque WHERE produto_id = (SELECT id FROM produto WHERE nome = ?)";
        String sqlProduto = "DELETE FROM produto WHERE nome = ?";

        try (Connection conn = ConexaoBanco.obterConexao()) {

            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sqlEstoque)) {
                stmt.setString(1, nome);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlProduto)) {
                stmt.setString(1, nome);
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas == 0) {
                    conn.rollback();
                    return "Nenhum produto encontrado com nome: " + nome;
                }
            }

            conn.commit();
            return "Produto excluído com sucesso.";

        } catch (SQLException e) {
            return "Erro ao excluir produto: " + e.getMessage();
        }
    }

    @Override
    public Produto buscarPorNome(String nome) {
        String sql = "SELECT nome, preco, marca FROM produto WHERE nome = ?";

        try (Connection conn = ConexaoBanco.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Produto(
                        rs.getString("nome"),
                        rs.getDouble("preco"),
                        rs.getString("marca")
                );
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar produto: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Produto> listarTodos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT nome, preco, marca FROM produto";

        try (Connection conn = ConexaoBanco.obterConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                produtos.add(new Produto(
                        rs.getString("nome"),
                        rs.getDouble("preco"),
                        rs.getString("marca")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos: " + e.getMessage());
        }

        return produtos;
    }
}