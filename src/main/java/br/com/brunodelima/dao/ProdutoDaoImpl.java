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

            // toda vez que cadastrar produto, já entra no estoque com quantidade 0
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