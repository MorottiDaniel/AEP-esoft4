import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RankingTutoresDAO {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void inserir(RankingTutores ranking) {
        String sql = "INSERT INTO RankingTutores (id_tutor, pontuacao_total, nivel) VALUES (?, ?, ?)";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, ranking.getTutor().getIdUsuario());
            stmt.setInt(2, ranking.getPontuacaoTotal());
            stmt.setString(3, ranking.getNivel());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        ranking.setIdRanking(rs.getInt(1));
                    }
                }
                System.out.println("Entrada de ranking de tutores inserida com sucesso! ID: " + ranking.getIdRanking());
            }

        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                System.err.println("Erro: Tutor ID " + ranking.getTutor().getIdUsuario() + " já possui uma entrada no ranking.");
            } else {
                System.err.println("Erro ao inserir entrada de ranking de tutores: " + e.getMessage());
            }
            e.printStackTrace();
        }
    }

    public RankingTutores buscarPorId(int id) {
        String sql = "SELECT * FROM RankingTutores WHERE id_ranking = ?";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarRankingDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar entrada de ranking de tutores por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public RankingTutores buscarPorTutor(int idTutor) {
        String sql = "SELECT * FROM RankingTutores WHERE id_tutor = ?";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTutor);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarRankingDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar entrada de ranking de tutores por ID do Tutor: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    public List<RankingTutores> buscarTodos() {
        List<RankingTutores> rankings = new ArrayList<>();
        String sql = "SELECT * FROM RankingTutores ORDER BY pontuacao_total DESC"; // Ordena por pontuação
        try (Connection conn = BancoDados.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                rankings.add(criarRankingDoResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todas as entradas de ranking de tutores: " + e.getMessage());
            e.printStackTrace();
        }
        return rankings;
    }

    public void atualizar(RankingTutores ranking) {
        String sql = "UPDATE RankingTutores SET pontuacao_total = ?, nivel = ? WHERE id_ranking = ?";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ranking.getPontuacaoTotal());
            stmt.setString(2, ranking.getNivel());
            stmt.setInt(3, ranking.getIdRanking());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Entrada de ranking de tutores ID " + ranking.getIdRanking() + " atualizada com sucesso!");
            } else {
                System.out.println("Nenhuma entrada de ranking de tutores encontrada com ID " + ranking.getIdRanking() + " para atualizar.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar entrada de ranking de tutores: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM RankingTutores WHERE id_ranking = ?";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Entrada de ranking de tutores ID " + id + " deletada com sucesso!");
            } else {
                System.out.println("Nenhuma entrada de ranking de tutores encontrada com ID " + id + " para deletar.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao deletar entrada de ranking de tutores: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private RankingTutores criarRankingDoResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_ranking");
        int idTutor = rs.getInt("id_tutor");
        int pontuacaoTotal = rs.getInt("pontuacao_total");
        String nivel = rs.getString("nivel");

        Usuario tutor = usuarioDAO.buscarPorId(idTutor);

        return new RankingTutores(id, tutor, pontuacaoTotal, nivel);
    }
}