import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BancoDados {

    private static final String URL = "jdbc:h2:./banco_teste"; // Onde o banco será salvo
    private static final String USUARIO = "sa"; // Usuário padrão para H2
    private static final String SENHA = ""; // Senha vazia para H2

    public static Connection getConexao() {
        try {
            // Garante que o driver H2 esteja carregado
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