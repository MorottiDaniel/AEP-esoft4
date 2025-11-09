import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;


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
        Scanner scanf = new Scanner(System.in);

        Interface.limpar();


        int opao;
        do
        {
            System.out.printf("Entrar - 1\n" +
                    "Cadastrar - 2\n" +
                    "Sair - 3\n");
            opao = scanf.nextInt();
            scanf.nextLine();
            Interface.limpar();
            switch (opao){
                case 1:
                    //Usuario user = Interface.entrar();
                    Interface.PaginacaoInicial(disciplinaDAO.buscarTodos(),2);
                    break;

                case 2:
                    usuarioDAO.inserir(Interface.cadastrar());
                    break;
            }
            Interface.limpar();
        }while (opao!=3);



    }
}

