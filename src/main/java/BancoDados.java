import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BancoDados {

    private static final String URL = "jdbc:h2:./banco_teste";
    private static final String USUARIO = "sa";
    private static final String SENHA = "";

    public static Connection getConexao() {
        try {
            Class.forName("org.h2.Driver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Driver H2 não encontrado. Adicione a dependência H2 ao seu projeto.");
            e.printStackTrace();
            return null;
        } catch (SQLException erro) {
            System.err.println("Erro ao conectar com o banco de dados: " + erro.getMessage());
            erro.printStackTrace();
            return null;
        }
    }

    public static void fecharConexao(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão com o banco de dados: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}