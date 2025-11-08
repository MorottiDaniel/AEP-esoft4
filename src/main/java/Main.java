import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Inicializa o banco de dados e cria as tabelas
        try (Connection conn = BancoDados.getConexao()) {
            if (conn != null) {
                DatabaseInitializer.criarTabelas(conn);
                System.out.println("Tabelas verificadas/criadas no banco de dados H2.");
            } else {
                System.err.println("Não foi possível estabelecer conexão com o banco de dados.");
                return;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão no inicializador: " + e.getMessage());
        }

        // Instancia os DAOs
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        MaterialDAO materialDAO = new MaterialDAO();
        QuestoesForumDAO questoesForumDAO = new QuestoesForumDAO();
        RespostasForumDAO respostasForumDAO = new RespostasForumDAO();
        ChatsDAO chatsDAO = new ChatsDAO();
        MensagensChatDAO mensagensChatDAO = new MensagensChatDAO();
        RankingTutoresDAO rankingTutoresDAO = new RankingTutoresDAO();

        ConsoleUtils.limpar();

        List<Usuario> usuarios = usuarioDAO.buscarTodos();
        for(Usuario x: usuarios){
            System.out.println(x.toString());
        }

    }
}