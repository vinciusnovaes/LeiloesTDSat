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

    public ArrayList<ProdutosDTO> listarProdutos() {
        return listagem;
    }
}
