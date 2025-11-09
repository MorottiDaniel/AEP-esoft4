import java.util.List;
import java.util.Scanner;
import java.util.function.BiFunction;



public class Interface {
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
    MaterialDAO materialDAO = new MaterialDAO();
    QuestoesForumDAO questoesForumDAO = new QuestoesForumDAO();
    RespostasForumDAO respostasForumDAO = new RespostasForumDAO();
    ChatsDAO chatsDAO = new ChatsDAO();
    MensagensChatDAO mensagensChatDAO = new MensagensChatDAO();
    RankingTutoresDAO rankingTutoresDAO = new RankingTutoresDAO();
    static Scanner scanf = new Scanner(System.in);

    public Interface() {
    }


    public static void limpar() {
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }

    public static Usuario entrar(){
        System.out.printf("\nEmail: ");
        String email = scanf.nextLine();
        System.out.println(email + "  teste");
        Usuario user = new UsuarioDAO().buscarPorEmail(email);
        if(user == null){
            System.out.println("Usuario não encontrado");
            return null;
        }
        System.out.printf("\nSenha: ");
        String senha = scanf.nextLine();
        if(senha.equals(user.getSenhaHash())){
            System.out.println("Login feio com sucesso");
        }else{
            System.out.println("senha incorreta!");
            user = null;
        }
        scanf.nextLine();
        return user;
    }

    public static Usuario cadastrar(){
        System.out.printf("Nome: ");
        String nome = scanf.nextLine();
        System.out.printf("Email: ");
        String email = scanf.nextLine();
        System.out.printf("Universidade: ");
        String universidade = scanf.nextLine();
        System.out.printf("Curso: ");
        String curso = scanf.nextLine();
        System.out.printf("Senha: ");
        String senha = scanf.nextLine();
        System.out.printf("Tipo usuario: ");
        int opao;
        Usuario user = null;
        do{
            System.out.printf("Aluno - 1" +
                    "\nTutor - 2\n");
            opao = scanf.nextInt();
            scanf.nextLine();
        }while (opao != 1 && opao != 2);
        if(opao == 1){
            user = new Usuario(nome, email, universidade, curso, senha, TipoUsuario.ALUNO);
        }if(opao == 2){
            user = new Usuario(nome, email, universidade, curso, senha, TipoUsuario.TUTOR);
        }
        return user;


    }

    public static void exibirDisciplinas(DisciplinaDAO disciplinaDAO) {
        System.out.println("=== DISCIPLINAS DISPONÍVEIS ===");

        List<Disciplina> disciplinas = disciplinaDAO.buscarTodos();
        if (disciplinas.isEmpty()) {
            System.out.println("Nenhuma disciplina cadastrada.");
            return;
        }


    }

    public static void exibirRankingTutores() {
        System.out.println("=== RANKING DOS TUTORES ===");
        List<RankingTutores> rankings = rankingDAO.buscarTodos();
        if (rankings.isEmpty()) {
            System.out.println("Nenhum tutor cadastrado no ranking.");
            return;
        }

        int posicao = 1;
        for (RankingTutores r : rankings) {
            System.out.println(posicao++ + ". " + r.getTutor().getNome()
                    + " | Pontuação: " + r.getPontuacaoTotal()
                    + " | Nível: " + r.getNivel());
        }
    }

    public static <T> void PaginacaoInicial(List<T> lista, int tamanhoPagina){
        int opao, inicio = 0 ;
        do{
            exibirRankingTutores();
            System.out.printf("===============================================================\n" +
                    "|-1-Voltar Pagina-1-|    |-2-Proxima Pagina-2-|    |-3-Sair-3-|\n" +
                    "===============================================================\n");

            for(int i = inicio; i < Math.min(inicio + tamanhoPagina,lista.size()); i++){
                T t = lista.get(i);
                System.out.println(t.toString() + " -"+(i+4)+"-");
            }

            System.out.print("\nEscolha uma opção: ");
            opao = scanf.nextInt();
            scanf.nextLine();

            if(opao == 1){
                inicio = Math.max(0, inicio - tamanhoPagina);
            }else if(opao == 2){
                int limiteSuperiorInicio = Math.max(0, lista.size() - tamanhoPagina);
                inicio = Math.min(lista.size(), inicio + tamanhoPagina);
            } else if (opao ==4) {
                PaginacaoDisciplina();
            }else if (opao ==5) {

            }else if (opao ==6) {

            }else if (opao ==7) {

            }else if (opao ==8) {

            }


            Interface.limpar();
        }while (opao!=3);
    }

    public static <T> void PaginacaoDisciplina(List<T> lista, int tamanhoPagina,){
        int opao, inicio = 0 ;
        do{
            exibirRankingTutores();
            System.out.printf("===============================================================\n" +
                    "|-1-Voltar Pagina-1-|    |-2-Proxima Pagina-2-|    |-3-Sair-3-|\n" +
                    "===============================================================\n");

            for(int i = inicio; i < Math.min(inicio + tamanhoPagina,lista.size()); i++){
                T t = lista.get(i);
                System.out.println(t.toString() + " -"+(i+4)+"-");
            }

            System.out.print("\nEscolha uma opção: ");
            opao = scanf.nextInt();
            scanf.nextLine();

            if(opao == 1){
                inicio = Math.max(0, inicio - tamanhoPagina);
            }else if(opao == 2){
                int limiteSuperiorInicio = Math.max(0, lista.size() - tamanhoPagina);
                inicio = Math.min(lista.size(), inicio + tamanhoPagina);
            } else if (opao ==4) {

            }else if (opao ==5) {

            }else if (opao ==6) {

            }else if (opao ==7) {

            }else if (opao ==8) {

            }


            Interface.limpar();
        }while (opao!=3);
    }



}
