import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void criarTabelas(Connection conn) {
        if (conn == null) {
            System.err.println("Conexão nula, impossível criar tabelas.");
            return;
        }

        try (Statement stmt = conn.createStatement()) {

            // Desativar a checagem de FK temporariamente (útil para criar tabelas em qualquer ordem)
            // stmt.execute("SET REFERENTIAL_INTEGRITY FALSE"); // H2 specific

            // 1. Tabela Usuarios
            String sqlUsuarios = "CREATE TABLE IF NOT EXISTS Usuarios (" +
                    "id_usuario INT AUTO_INCREMENT PRIMARY KEY," +
                    "nome VARCHAR(255) NOT NULL," +
                    "email VARCHAR(255) NOT NULL UNIQUE," +
                    "senha_hash VARCHAR(255) NOT NULL," +
                    "universidade VARCHAR(255)," +
                    "curso VARCHAR(255)," +
                    "data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "tipo_usuario VARCHAR(50) NOT NULL," + // ALUNO, TUTOR, ADMIN
                    "reputacao_tutor INT" + // Pode ser NULL
                    ");";
            stmt.execute(sqlUsuarios);
            System.out.println("Tabela Usuarios criada ou já existe.");

            // 2. Tabela Disciplinas
            String sqlDisciplinas = "CREATE TABLE IF NOT EXISTS Disciplinas (" +
                    "id_disciplina INT AUTO_INCREMENT PRIMARY KEY," +
                    "nome_disciplina VARCHAR(255) NOT NULL UNIQUE," +
                    "ementa TEXT" +
                    ");";
            stmt.execute(sqlDisciplinas);
            System.out.println("Tabela Disciplinas criada ou já existe.");

            // 3. Tabela Materiais
            String sqlMateriais = "CREATE TABLE IF NOT EXISTS Materiais (" +
                    "id_material INT AUTO_INCREMENT PRIMARY KEY," +
                    "titulo VARCHAR(255) NOT NULL," +
                    "descricao TEXT," +
                    "caminho_arquivo VARCHAR(255)," +
                    "data_upload TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "id_tutor INT," +
                    "id_disciplina INT," +
                    "FOREIGN KEY (id_tutor) REFERENCES Usuarios(id_usuario) ON DELETE SET NULL," +
                    "FOREIGN KEY (id_disciplina) REFERENCES Disciplinas(id_disciplina) ON DELETE SET NULL" +
                    ");";
            stmt.execute(sqlMateriais);
            System.out.println("Tabela Materiais criada ou já existe.");

            // 4. Tabela QuestoesForum
            String sqlQuestoesForum = "CREATE TABLE IF NOT EXISTS QuestoesForum (" +
                    "id_questao INT AUTO_INCREMENT PRIMARY KEY," +
                    "titulo_questao VARCHAR(255) NOT NULL," +
                    "conteudo_questao TEXT NOT NULL," +
                    "data_postagem TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "id_aluno INT," +
                    "id_disciplina INT," +
                    "status_questao VARCHAR(50) NOT NULL," + // ABERTA, RESPONDIDA, FECHADA
                    "FOREIGN KEY (id_aluno) REFERENCES Usuarios(id_usuario) ON DELETE CASCADE," +
                    "FOREIGN KEY (id_disciplina) REFERENCES Disciplinas(id_disciplina) ON DELETE CASCADE" +
                    ");";
            stmt.execute(sqlQuestoesForum);
            System.out.println("Tabela QuestoesForum criada ou já existe.");

            // 5. Tabela RespostasForum
            String sqlRespostasForum = "CREATE TABLE IF NOT EXISTS RespostasForum (" +
                    "id_resposta INT AUTO_INCREMENT PRIMARY KEY," +
                    "conteudo_resposta TEXT NOT NULL," +
                    "data_resposta TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "id_usuario INT," +
                    "id_questao INT," +
                    "validada BOOLEAN DEFAULT FALSE," +
                    "FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario) ON DELETE CASCADE," +
                    "FOREIGN KEY (id_questao) REFERENCES QuestoesForum(id_questao) ON DELETE CASCADE" +
                    ");";
            stmt.execute(sqlRespostasForum);
            System.out.println("Tabela RespostasForum criada ou já existe.");

            // 6. Tabela Chats
            String sqlChats = "CREATE TABLE IF NOT EXISTS Chats (" +
                    "id_chat INT AUTO_INCREMENT PRIMARY KEY," +
                    "id_usuario1 INT NOT NULL," +
                    "id_usuario2 INT NOT NULL," +
                    "data_inicio TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (id_usuario1) REFERENCES Usuarios(id_usuario) ON DELETE CASCADE," +
                    "FOREIGN KEY (id_usuario2) REFERENCES Usuarios(id_usuario) ON DELETE CASCADE," +
                    "CONSTRAINT unique_chat_pair UNIQUE (id_usuario1, id_usuario2)" + // Para evitar chats duplicados entre as mesmas duas pessoas
                    ");";
            stmt.execute(sqlChats);
            System.out.println("Tabela Chats criada ou já existe.");

            // 7. Tabela MensagensChat
            String sqlMensagensChat = "CREATE TABLE IF NOT EXISTS MensagensChat (" +
                    "id_mensagem INT AUTO_INCREMENT PRIMARY KEY," +
                    "id_chat INT NOT NULL," +
                    "id_remetente INT NOT NULL," +
                    "conteudo_mensagem TEXT NOT NULL," +
                    "data_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (id_chat) REFERENCES Chats(id_chat) ON DELETE CASCADE," +
                    "FOREIGN KEY (id_remetente) REFERENCES Usuarios(id_usuario) ON DELETE CASCADE" +
                    ");";
            stmt.execute(sqlMensagensChat);
            System.out.println("Tabela MensagensChat criada ou já existe.");

            // 8. Tabela RankingTutores
            String sqlRankingTutores = "CREATE TABLE IF NOT EXISTS RankingTutores (" +
                    "id_ranking INT AUTO_INCREMENT PRIMARY KEY," +
                    "id_tutor INT NOT NULL UNIQUE," + // Um tutor pode ter apenas uma entrada no ranking
                    "pontuacao_total INT DEFAULT 0," +
                    "nivel VARCHAR(50)," +
                    "FOREIGN KEY (id_tutor) REFERENCES Usuarios(id_usuario) ON DELETE CASCADE" +
                    ");";
            stmt.execute(sqlRankingTutores);
            System.out.println("Tabela RankingTutores criada ou já existe.");

            // Reativar a checagem de FK
            // stmt.execute("SET REFERENTIAL_INTEGRITY TRUE"); // H2 specific

        } catch (SQLException e) {
            System.err.println("Erro ao criar tabelas: " + e.getMessage());
            e.printStackTrace();
        }
    }
}