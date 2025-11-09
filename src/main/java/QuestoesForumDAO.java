import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class QuestoesForumDAO {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private MaterialDAO materialDAO = new MaterialDAO();
    // RespostasForumDAO não é diretamente usado aqui para buscar, mas seria para gerenciar as respostas da questão.
    // Se precisarmos carregar todas as respostas de uma questão ao buscá-la, instanciaríamos um RespostasForumDAO.

    public void inserir(QuestoesForum questao) {
        String sql = "INSERT INTO QuestoesForum (titulo_questao, conteudo_questao, data_postagem, id_aluno, id_material, status_questao) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, questao.getTituloQuestao());
            stmt.setString(2, questao.getConteudoQuestao());
            stmt.setTimestamp(3, Timestamp.valueOf(questao.getDataPostagem()));
            stmt.setInt(4, questao.getAluno().getIdUsuario());
            stmt.setInt(5, questao.getMaterial().getIdMaterial());
            stmt.setString(6, questao.getStatusQuestao().name());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        questao.setIdQuestao(rs.getInt(1));
                    }
                }
                System.out.println("Questão do fórum inserida com sucesso! ID: " + questao.getIdQuestao());
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inserir questão do fórum: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public QuestoesForum buscarPorId(int id) {
        String sql = "SELECT * FROM QuestoesForum WHERE id_questao = ?";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarQuestaoDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar questão do fórum por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<QuestoesForum> buscarTodos() {
        List<QuestoesForum> questoes = new ArrayList<>();
        String sql = "SELECT * FROM QuestoesForum";
        try (Connection conn = BancoDados.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                questoes.add(criarQuestaoDoResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todas as questões do fórum: " + e.getMessage());
            e.printStackTrace();
        }
        return questoes;
    }

    public List<QuestoesForum> buscarMaterial(int id_material) {
        List<QuestoesForum> foruns = new ArrayList<>();
        String sql = "SELECT * FROM QuestoesForum WHERE id_material = ?";
        try (Connection conn = BancoDados.getConexao(); // Supondo que BancoDados.getConexao() retorna uma nova conexão
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id_material);
            try (ResultSet rs = pstmt.executeQuery()) { // Executa a query
                while (rs.next()) {
                    foruns.add(criarQuestaoDoResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os foruns: " + e.getMessage());
            e.printStackTrace();
        }
        return foruns;
    }

    public void atualizar(QuestoesForum questao) {
        String sql = "UPDATE QuestoesForum SET titulo_questao = ?, conteudo_questao = ?, id_aluno = ?, id_Material = ?, status_questao = ? WHERE id_questao = ?";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, questao.getTituloQuestao());
            stmt.setString(2, questao.getConteudoQuestao());
            stmt.setInt(3, questao.getAluno().getIdUsuario());
            stmt.setInt(4, questao.getMaterial().getIdMaterial());
            stmt.setString(5, questao.getStatusQuestao().name());
            stmt.setInt(6, questao.getIdQuestao());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Questão do fórum ID " + questao.getIdQuestao() + " atualizada com sucesso!");
            } else {
                System.out.println("Nenhuma questão do fórum encontrada com ID " + questao.getIdQuestao() + " para atualizar.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar questão do fórum: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM QuestoesForum WHERE id_questao = ?";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Questão do fórum ID " + id + " deletada com sucesso!");
            } else {
                System.out.println("Nenhuma questão do fórum encontrada com ID " + id + " para deletar.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao deletar questão do fórum: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private QuestoesForum criarQuestaoDoResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_questao");
        String tituloQuestao = rs.getString("titulo_questao");
        String conteudoQuestao = rs.getString("conteudo_questao");
        LocalDateTime dataPostagem = rs.getTimestamp("data_postagem").toLocalDateTime();

        int idAluno = rs.getInt("id_aluno");
        int id_material = rs.getInt("id_material");
        StatusQuestao statusQuestao = StatusQuestao.valueOf(rs.getString("status_questao"));

        Usuario aluno = usuarioDAO.buscarPorId(idAluno);
        Material material = materialDAO.buscarPorId(id_material);

        // Não carregamos as respostas aqui para evitar recursão infinita ou sobrecarga.
        // As respostas seriam carregadas separadamente por um RespostasForumDAO.buscarPorQuestao(idQuestao).
        QuestoesForum questao = new QuestoesForum(id, tituloQuestao, conteudoQuestao, dataPostagem, aluno, material, statusQuestao);

        // Se desejar carregar as respostas imediatamente (tenha cuidado com lazy loading vs eager loading)
        // RespostasForumDAO respostasForumDAO = new RespostasForumDAO(); // Instancie se necessário
        // questao.getRespostas().addAll(respostasForumDAO.buscarPorQuestao(id));

        return questao;
    }
}