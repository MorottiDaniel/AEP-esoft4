import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;


public class Main {
    static int tamanhoPagina = 5;

    public static void main(String[] args) {
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
/*
        List<Usuario> usuarios = usuarioDAO.buscarTodos();
        for(Usuario x : usuarios){
            System.out.printf("%s \nSenha: %s",x.toString(),x.getSenhaHash());
        }
// */

// /*
        int opao;
        do
        {
            System.out.printf("------------------\n" +
                    "Entrar - 1\n" +
                    "------------------\n" +
                    "Cadastrar - 2\n" +
                    "------------------\n" +
                    "Sair - 3\n" +
                    "------------------\n");
            opao = scanf.nextInt();
            scanf.nextLine();
            Interface.limpar();
            switch (opao){
                case 1:
                    Usuario user = Interface.entrar();
                    //Usuario user = usuarioDAO.buscarPorId(1);
                    if(user!=null) {
                        Interface.PaginacaoInicial(disciplinaDAO.buscarTodos(), tamanhoPagina, user);
                    }
                    break;

                case 2:
                    usuarioDAO.inserir(Interface.cadastrar());
                    break;
            }
            Interface.limpar();
        }while (opao!=3);
// */


    }
}

