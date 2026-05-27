package br.com.brunodelima.dao;

import br.com.brunodelima.infra.ConexaoBanco;
import br.com.brunodelima.model.Cliente;
import br.com.brunodelima.model.NotaFiscal;
import br.com.brunodelima.model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaFiscalDaoImpl implements INotaFiscalDao {

    @Override
    public String salvar(NotaFiscal notaFiscal) {
        String sqlNota    = "INSERT INTO nota_fiscal (numero, cliente_id) " +
                "VALUES (?, (SELECT id FROM cliente WHERE cpf = ?)) " +
                "RETURNING id";
        String sqlProduto = "INSERT INTO nota_fiscal_produto (nota_fiscal_id, produto_id) " +
                "VALUES (?, (SELECT id FROM produto WHERE nome = ?))";

        try (Connection conn = ConexaoBanco.obterConexao()) {

            conn.setAutoCommit(false);

            int notaId;
            try (PreparedStatement stmt = conn.prepareStatement(sqlNota)) {
                stmt.setString(1, notaFiscal.getNumero());
                stmt.setString(2, notaFiscal.getCliente().getCpf());
                ResultSet rs = stmt.executeQuery();
                rs.next();
                notaId = rs.getInt("id");
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlProduto)) {
                for (Produto p : notaFiscal.getProdutos()) {
                    stmt.setInt(1, notaId);
                    stmt.setString(2, p.getNome());
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }

            conn.commit();
            return "Nota fiscal salva com sucesso: " + notaFiscal.getNumero();

        } catch (SQLException e) {
            return "Erro ao salvar nota fiscal: " + e.getMessage();
        }
    }

    @Override
    public String excluir(String numero) {
        String sqlProdutos = "DELETE FROM nota_fiscal_produto WHERE nota_fiscal_id = " +
                "(SELECT id FROM nota_fiscal WHERE numero = ?)";
        String sqlNota     = "DELETE FROM nota_fiscal WHERE numero = ?";

        try (Connection conn = ConexaoBanco.obterConexao()) {

            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sqlProdutos)) {
                stmt.setString(1, numero);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlNota)) {
                stmt.setString(1, numero);
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas == 0) {
                    conn.rollback();
                    return "Nenhuma nota encontrada com número: " + numero;
                }
            }

            conn.commit();
            return "Nota fiscal excluída com sucesso.";

        } catch (SQLException e) {
            return "Erro ao excluir nota fiscal: " + e.getMessage();
        }
    }

    @Override
    public NotaFiscal buscarPorNumero(String numero) {
        String sql = """
                SELECT nf.numero,
                       c.nome AS cliente_nome, c.cpf, c.email, c.telefone,
                       p.nome AS produto_nome, p.preco, p.marca
                FROM nota_fiscal nf
                JOIN cliente c ON c.id = nf.cliente_id
                JOIN nota_fiscal_produto nfp ON nfp.nota_fiscal_id = nf.id
                JOIN produto p ON p.id = nfp.produto_id
                WHERE nf.numero = ?
                """;

        try (Connection conn = ConexaoBanco.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numero);
            ResultSet rs = stmt.executeQuery();

            NotaFiscal nota = null;
            List<Produto> produtos = new ArrayList<>();

            while (rs.next()) {
                if (nota == null) {
                    Cliente cliente = new Cliente(
                            rs.getString("cliente_nome"),
                            rs.getString("cpf"),
                            rs.getString("email"),
                            rs.getString("telefone")
                    );
                    nota = new NotaFiscal(numero, cliente, produtos);
                }
                produtos.add(new Produto(
                        rs.getString("produto_nome"),
                        rs.getDouble("preco"),
                        rs.getString("marca")
                ));
            }

            return nota;

        } catch (SQLException e) {
            System.err.println("Erro ao buscar nota fiscal: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<NotaFiscal> listarTodos() {
        List<NotaFiscal> notas = new ArrayList<>();
        String sql = """
                SELECT nf.numero,
                       c.nome AS cliente_nome, c.cpf, c.email, c.telefone,
                       p.nome AS produto_nome, p.preco, p.marca
                FROM nota_fiscal nf
                JOIN cliente c ON c.id = nf.cliente_id
                JOIN nota_fiscal_produto nfp ON nfp.nota_fiscal_id = nf.id
                JOIN produto p ON p.id = nfp.produto_id
                ORDER BY nf.numero
                """;

        try (Connection conn = ConexaoBanco.obterConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            String ultimoNumero = null;
            NotaFiscal notaAtual = null;
            List<Produto> produtosAtuais = null;

            while (rs.next()) {
                String numero = rs.getString("numero");

                if (!numero.equals(ultimoNumero)) {
                    if (notaAtual != null) notas.add(notaAtual);

                    Cliente cliente = new Cliente(
                            rs.getString("cliente_nome"),
                            rs.getString("cpf"),
                            rs.getString("email"),
                            rs.getString("telefone")
                    );
                    produtosAtuais = new ArrayList<>();
                    notaAtual = new NotaFiscal(numero, cliente, produtosAtuais);
                    ultimoNumero = numero;
                }

                produtosAtuais.add(new Produto(
                        rs.getString("produto_nome"),
                        rs.getDouble("preco"),
                        rs.getString("marca")
                ));
            }

            if (notaAtual != null) notas.add(notaAtual);

        } catch (SQLException e) {
            System.err.println("Erro ao listar notas fiscais: " + e.getMessage());
        }

        return notas;
    }
}