import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public void cadastrarProduto(ProdutosDTO produto) {

        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

        conn = new conectaDAO().connectDB();

        try {
            prep = conn.prepareStatement(sql);

            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());

            prep.execute();

            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");

        } catch (SQLException erro) {

            JOptionPane.showMessageDialog(
                null,
                "Erro ao cadastrar produto: " + erro.getMessage()
            );

        } finally {

            try {
                if (prep != null) {
                    prep.close();
                }

                if (conn != null) {
                    conn.close();
                }

            } catch (SQLException erro) {
                JOptionPane.showMessageDialog(
                    null,
                    "Erro ao fechar conexão: " + erro.getMessage()
                );
            }
        }
    }
    public void venderProduto(int id){
    String sql = "UPDATE produtos SET status = ? WHERE id = ?";

    conn = new conectaDAO().connectDB();

    try {

        prep = conn.prepareStatement(sql);

        prep.setString(1, "Vendido");
        prep.setInt(2, id);

        int linhasAlteradas = prep.executeUpdate();

        if (linhasAlteradas > 0) {

            JOptionPane.showMessageDialog(
                null,
                "Produto vendido com sucesso!"
            );

        } else {

            JOptionPane.showMessageDialog(
                null,
                "Nenhum produto encontrado com o ID informado."
            );
        }

    } catch (SQLException erro) {

        JOptionPane.showMessageDialog(
            null,
            "Erro ao vender produto: " + erro.getMessage()
        );

    } finally {

        try {

            if (prep != null) {
                prep.close();
            }

            if (conn != null) {
                conn.close();
            }

        } catch (SQLException erro) {

            JOptionPane.showMessageDialog(
                null,
                "Erro ao fechar conexão: " + erro.getMessage()
            );
        }
    }
}
    public ArrayList<ProdutosDTO> listarProdutos() {
         listagem = new ArrayList<>();

    String sql = "SELECT * FROM produtos";

    conn = new conectaDAO().connectDB();

    try {
        prep = conn.prepareStatement(sql);
        resultset = prep.executeQuery();

        while (resultset.next()) {

            ProdutosDTO produto = new ProdutosDTO();

            produto.setId(resultset.getInt("id"));
            produto.setNome(resultset.getString("nome"));
            produto.setValor(resultset.getInt("valor"));
            produto.setStatus(resultset.getString("status"));

            listagem.add(produto);
        }

    } catch (SQLException erro) {

        JOptionPane.showMessageDialog(
            null,
            "Erro ao listar produtos: " + erro.getMessage()
        );

    } finally {

        try {

            if (resultset != null) {
                resultset.close();
            }

            if (prep != null) {
                prep.close();
            }

            if (conn != null) {
                conn.close();
            }

        } catch (SQLException erro) {

            JOptionPane.showMessageDialog(
                null,
                "Erro ao fechar conexão: " + erro.getMessage()
            );
        }
    }

    return listagem;
    }
}
