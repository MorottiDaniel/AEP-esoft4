import java.util.List;
import java.util.Scanner;
import java.util.function.BiFunction;



public class Interface {
    static UsuarioDAO usuarioDAO = new UsuarioDAO();
    static DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
    static MaterialDAO materialDAO = new MaterialDAO();
    static QuestoesForumDAO questoesForumDAO = new QuestoesForumDAO();
    static RespostasForumDAO respostasForumDAO = new RespostasForumDAO();
    static ChatsDAO chatsDAO = new ChatsDAO();
    static MensagensChatDAO mensagensChatDAO = new MensagensChatDAO();
    static RankingTutoresDAO rankingTutoresDAO = new RankingTutoresDAO();
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
        List<RankingTutores> rankings = rankingTutoresDAO.buscarTodos();
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

    public static void PaginacaoInicial(List<Disciplina> lista, int tamanhoPagina){
        int opao, inicio = 0 ;
        do{
            Interface.limpar();
            exibirRankingTutores();
            System.out.printf("===============================================================\n" +
                    "DISCIPLINAS DISPONÍVEIS\n" );
            System.out.printf("===============================================================\n" +
                    "|-1-Voltar Pagina-1-|    |-2-Proxima Pagina-2-|    |-3-Sair-3-|\n" +
                    "===============================================================\n");

            int indice = 0;
            for(int i = inicio; i < Math.min(inicio + tamanhoPagina,lista.size()); i++){
                System.out.println(lista.get(i).toString() + " -"+(indice+4)+"-");
                indice++;
            }

            System.out.print("\nEscolha uma opção: ");
            opao = scanf.nextInt();
            scanf.nextLine();

            if(opao == 1){
                int x = Math.max(0, inicio - tamanhoPagina);
                inicio = x;
                indice = x;
            }else if(opao == 2){
                int limiteSuperiorInicio = Math.max(0, lista.size() - tamanhoPagina);
                int x = Math.min(lista.size(), inicio + tamanhoPagina);
                inicio = x;
                indice = x;
            } else if (opao ==4) {
                PaginacaoDisciplina(lista.get(opao-(4-inicio)).getIdDisciplina() ,tamanhoPagina);
            }else if (opao ==5) {
                PaginacaoDisciplina(lista.get(opao-(4-inicio)).getIdDisciplina() ,tamanhoPagina);
            }else if (opao ==6) {
                PaginacaoDisciplina(lista.get(opao-(4-inicio)).getIdDisciplina() ,tamanhoPagina);
            }else if (opao ==7) {
                PaginacaoDisciplina(lista.get(opao-(4-inicio)).getIdDisciplina() ,tamanhoPagina);
            }else if (opao ==8) {

            }

        }while (opao!=3);
    }

    public static void PaginacaoDisciplina(int id_disciplina, int tamanhoPagina){
        List <Material> lista = materialDAO.buscarDisciplina(id_disciplina);
        int opao, inicio = 0 ;
        do{
            Interface.limpar();
            System.out.printf("===============================================================\n" +
                    "%s\n",disciplinaDAO.buscarPorId(id_disciplina).getNomeDisciplina());
            System.out.printf("===============================================================\n" +
                    "|-1-Voltar Pagina-1-|    |-2-Proxima Pagina-2-|    |-3-Sair-3-|\n" +
                    "===============================================================\n");

            int indice = 0;
            for(int i = inicio; i < Math.min(inicio + tamanhoPagina,lista.size()); i++){
                System.out.println(lista.get(i).toString() + " -"+(i+4)+"-");
                indice++;
            }

            System.out.print("\nEscolha uma opção: ");
            opao = scanf.nextInt();
            scanf.nextLine();

            if(opao == 1){
                int x = Math.max(0, inicio - tamanhoPagina);
                inicio = x;
                indice = x;
            }else if(opao == 2){
                int limiteSuperiorInicio = Math.max(0, lista.size() - tamanhoPagina);
                int x = Math.min(lista.size(), inicio + tamanhoPagina);
                inicio = x;
                indice = x;
            } else if (opao ==4) {
                PaginacaoMaterial(lista.get(opao-(4-inicio)).getIdMaterial() ,tamanhoPagina);
            }else if (opao ==5) {
                PaginacaoMaterial(lista.get(opao-(4-inicio)).getIdMaterial() ,tamanhoPagina);
            }else if (opao ==6) {
                PaginacaoMaterial(lista.get(opao-(4-inicio)).getIdMaterial() ,tamanhoPagina);
            }else if (opao ==7) {
                PaginacaoMaterial(lista.get(opao-(4-inicio)).getIdMaterial() ,tamanhoPagina);
            }else if (opao ==8) {
                PaginacaoMaterial(lista.get(opao-(4-inicio)).getIdMaterial() ,tamanhoPagina);
            }


            Interface.limpar();
        }while (opao!=3);
    }

    public static void PaginacaoMaterial(int id_material, int tamanhoPagina){
        Material material = materialDAO.buscarPorId(id_material);
        List <QuestoesForum> lista = questoesForumDAO.buscarMaterial(id_material);



        int opao, inicio = 0 ;
        do{
            Interface.limpar();
            System.out.printf("Material: %s| Tutor: %s \n" +
                            "---------------------------------------------------------------\n" +
                            "Descrição: %s\n" +
                            "---------------------------------------------------------------\n" +
                            "Arquivo: %s\n" +
                            "---------------------------------------------------------------\n",
                    material.getTitulo(),material.getTutor().getNome(),material.getDescricao(),material.getCaminhoArquivo());
            System.out.printf("===============================================================\n" +
                    "|-1-Voltar Pagina-1-|    |-2-Proxima Pagina-2-|    |-3-Sair-3-|\n" +
                    "===============================================================\n");

            int indice = 0;
            for(int i = inicio; i < Math.min(inicio + tamanhoPagina,lista.size()); i++){
                System.out.println(lista.get(i).toString() + " -"+(i+4)+"-");
                indice++;
            }

            System.out.print("\nEscolha uma opção: ");
            opao = scanf.nextInt();
            scanf.nextLine();

            if(opao == 1){
                int x = Math.max(0, inicio - tamanhoPagina);
                inicio = x;
                indice = x;
            }else if(opao == 2){
                int limiteSuperiorInicio = Math.max(0, lista.size() - tamanhoPagina);
                int x = Math.min(lista.size(), inicio + tamanhoPagina);
                inicio = x;
                indice = x;
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
