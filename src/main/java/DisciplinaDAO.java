import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaDAO {

    public void inserir(Disciplina disciplina) {
        String sql = "INSERT INTO Disciplinas (nome_disciplina, ementa) VALUES (?, ?)";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, disciplina.getNomeDisciplina());
            stmt.setString(2, disciplina.getEmenta());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        disciplina.setIdDisciplina(rs.getInt(1));
                    }
                }
                System.out.println("Disciplina inserida com sucesso! ID: " + disciplina.getIdDisciplina());
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inserir disciplina: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Disciplina buscarPorId(int id) {
        String sql = "SELECT * FROM Disciplinas WHERE id_disciplina = ?";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarDisciplinaDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar disciplina por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<Disciplina> buscarTodos() {
        List<Disciplina> disciplinas = new ArrayList<>();
        String sql = "SELECT * FROM Disciplinas";
        try (Connection conn = BancoDados.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                disciplinas.add(criarDisciplinaDoResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todas as disciplinas: " + e.getMessage());
            e.printStackTrace();
        }
        return disciplinas;
    }

    public void atualizar(Disciplina disciplina) {
        String sql = "UPDATE Disciplinas SET nome_disciplina = ?, ementa = ? WHERE id_disciplina = ?";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, disciplina.getNomeDisciplina());
            stmt.setString(2, disciplina.getEmenta());
            stmt.setInt(3, disciplina.getIdDisciplina());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Disciplina ID " + disciplina.getIdDisciplina() + " atualizada com sucesso!");
            } else {
                System.out.println("Nenhuma disciplina encontrada com ID " + disciplina.getIdDisciplina() + " para atualizar.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar disciplina: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM Disciplinas WHERE id_disciplina = ?";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Disciplina ID " + id + " deletada com sucesso!");
            } else {
                System.out.println("Nenhuma disciplina encontrada com ID " + id + " para deletar.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao deletar disciplina: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Disciplina criarDisciplinaDoResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_disciplina");
        String nomeDisciplina = rs.getString("nome_disciplina");
        String ementa = rs.getString("ementa");
        return new Disciplina(id, nomeDisciplina, ementa);
    }
}