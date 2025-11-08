import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RespostasForumDAO {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private QuestoesForumDAO questoesForumDAO; // Lazy initialization ou injetado

    // Construtor para injetar QuestoesForumDAO e evitar loop infinito se QuestoesForumDAO injetar RespostasForumDAO
    public RespostasForumDAO() {
        // Isso pode criar um loop infinito se QuestoesForumDAO também injetar RespostasForumDAO no construtor.
        // Uma alternativa é passar o DAO como parâmetro em métodos específicos ou usar um inicializador.
        // Para este exemplo, vamos supor que QuestoesForumDAO NÃO injeta RespostasForumDAO no construtor.
        this.questoesForumDAO = new QuestoesForumDAO();
    }

    public void inserir(RespostasForum resposta) {
        String sql = "INSERT INTO RespostasForum (conteudo_resposta, data_resposta, id_usuario, id_questao, validada) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, resposta.getConteudoResposta());
            stmt.setTimestamp(2, Timestamp.valueOf(resposta.getDataResposta()));
            stmt.setInt(3, resposta.getUsuario().getIdUsuario());
            stmt.setInt(4, resposta.getQuestao().getIdQuestao());
            stmt.setBoolean(5, resposta.isValidada());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        resposta.setIdResposta(rs.getInt(1));
                    }
                }
                System.out.println("Resposta do fórum inserida com sucesso! ID: " + resposta.getIdResposta());
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inserir resposta do fórum: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public RespostasForum buscarPorId(int id) {
        String sql = "SELECT * FROM RespostasForum WHERE id_resposta = ?";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarRespostaDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar resposta do fórum por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<RespostasForum> buscarPorQuestao(int idQuestao) {
        List<RespostasForum> respostas = new ArrayList<>();
        String sql = "SELECT * FROM RespostasForum WHERE id_questao = ?";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idQuestao);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    respostas.add(criarRespostaDoResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar respostas por questão: " + e.getMessage());
            e.printStackTrace();
        }
        return respostas;
    }

    public List<RespostasForum> buscarTodos() {
        List<RespostasForum> respostas = new ArrayList<>();
        String sql = "SELECT * FROM RespostasForum";
        try (Connection conn = BancoDados.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                respostas.add(criarRespostaDoResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todas as respostas do fórum: " + e.getMessage());
            e.printStackTrace();
        }
        return respostas;
    }

    public void atualizar(RespostasForum resposta) {
        String sql = "UPDATE RespostasForum SET conteudo_resposta = ?, id_usuario = ?, id_questao = ?, validada = ? WHERE id_resposta = ?";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, resposta.getConteudoResposta());
            stmt.setInt(2, resposta.getUsuario().getIdUsuario());
            stmt.setInt(3, resposta.getQuestao().getIdQuestao());
            stmt.setBoolean(4, resposta.isValidada());
            stmt.setInt(5, resposta.getIdResposta());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Resposta do fórum ID " + resposta.getIdResposta() + " atualizada com sucesso!");
            } else {
                System.out.println("Nenhuma resposta do fórum encontrada com ID " + resposta.getIdResposta() + " para atualizar.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar resposta do fórum: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM RespostasForum WHERE id_resposta = ?";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Resposta do fórum ID " + id + " deletada com sucesso!");
            } else {
                System.out.println("Nenhuma resposta do fórum encontrada com ID " + id + " para deletar.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao deletar resposta do fórum: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private RespostasForum criarRespostaDoResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_resposta");
        String conteudoResposta = rs.getString("conteudo_resposta");
        LocalDateTime dataResposta = rs.getTimestamp("data_resposta").toLocalDateTime();

        int idUsuario = rs.getInt("id_usuario");
        int idQuestao = rs.getInt("id_questao");
        boolean validada = rs.getBoolean("validada");

        Usuario usuario = usuarioDAO.buscarPorId(idUsuario);
        QuestoesForum questao = questoesForumDAO.buscarPorId(idQuestao); // Busca o objeto QuestaoForum

        return new RespostasForum(id, conteudoResposta, dataResposta, usuario, questao, validada);
    }
}