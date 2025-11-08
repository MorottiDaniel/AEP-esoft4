import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO {

    // DAOs para buscar objetos relacionados
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private DisciplinaDAO disciplinaDAO = new DisciplinaDAO();

    public void inserir(Material material) {
        String sql = "INSERT INTO Materiais (titulo, descricao, caminho_arquivo, data_upload, id_tutor, id_disciplina) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, material.getTitulo());
            stmt.setString(2, material.getDescricao());
            stmt.setString(3, material.getCaminhoArquivo());
            stmt.setTimestamp(4, Timestamp.valueOf(material.getDataUpload()));

            // Certifique-se de que o tutor e a disciplina já foram inseridos e têm IDs
            // Se o tutor pode ser null na sua lógica de negócio, você precisaria verificar:
            if (material.getTutor() != null) {
                stmt.setInt(5, material.getTutor().getIdUsuario()); // Pega o ID do objeto Tutor
            } else {
                stmt.setNull(5, Types.INTEGER); // Define como NULL no banco de dados
            }

            if (material.getDisciplina() != null) {
                stmt.setInt(6, material.getDisciplina().getIdDisciplina()); // Pega o ID do objeto Disciplina
            } else {
                stmt.setNull(6, Types.INTEGER); // Define como NULL no banco de dados
            }

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        material.setIdMaterial(rs.getInt(1));
                    }
                }
                System.out.println("Material inserido com sucesso! ID: " + material.getIdMaterial());
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inserir material: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Material buscarPorId(int id) {
        String sql = "SELECT * FROM Materiais WHERE id_material = ?";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarMaterialDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar material por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<Material> buscarTodos() {
        List<Material> materiais = new ArrayList<>();
        String sql = "SELECT * FROM Materiais";
        try (Connection conn = BancoDados.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                materiais.add(criarMaterialDoResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os materiais: " + e.getMessage());
            e.printStackTrace();
        }
        return materiais;
    }

    public void atualizar(Material material) {
        String sql = "UPDATE Materiais SET titulo = ?, descricao = ?, caminho_arquivo = ?, id_tutor = ?, id_disciplina = ? WHERE id_material = ?";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, material.getTitulo());
            stmt.setString(2, material.getDescricao());
            stmt.setString(3, material.getCaminhoArquivo());

            if (material.getTutor() != null) {
                stmt.setInt(4, material.getTutor().getIdUsuario());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }

            if (material.getDisciplina() != null) {
                stmt.setInt(5, material.getDisciplina().getIdDisciplina());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            stmt.setInt(6, material.getIdMaterial());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Material ID " + material.getIdMaterial() + " atualizado com sucesso!");
            } else {
                System.out.println("Nenhum material encontrado com ID " + material.getIdMaterial() + " para atualizar.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar material: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM Materiais WHERE id_material = ?";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Material ID " + id + " deletado com sucesso!");
            } else {
                System.out.println("Nenhum material encontrado com ID " + id + " para deletar.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao deletar material: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Material criarMaterialDoResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_material");
        String titulo = rs.getString("titulo");
        String descricao = rs.getString("descricao");
        String caminhoArquivo = rs.getString("caminho_arquivo");
        LocalDateTime dataUpload = rs.getTimestamp("data_upload").toLocalDateTime();

        // Recupera os IDs das FKs
        // Usamos getObject com Integer.class para lidar com valores NULL da coluna INT
        Integer idTutor = rs.getObject("id_tutor", Integer.class);
        Integer idDisciplina = rs.getObject("id_disciplina", Integer.class);

        Usuario tutor = null;
        if (idTutor != null) {
            tutor = usuarioDAO.buscarPorId(idTutor);
        }

        Disciplina disciplina = null;
        if (idDisciplina != null) {
            disciplina = disciplinaDAO.buscarPorId(idDisciplina);
        }

        return new Material(id, titulo, descricao, caminhoArquivo, dataUpload, tutor, disciplina);
    }
}