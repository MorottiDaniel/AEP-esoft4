import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public void inserir(Usuario usuario) {
        String sql = "INSERT INTO Usuarios (nome, email, senha_hash, universidade, curso, data_cadastro, tipo_usuario, reputacao_tutor) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenhaHash());
            stmt.setString(4, usuario.getUniversidade());
            stmt.setString(5, usuario.getCurso());
            stmt.setTimestamp(6, Timestamp.valueOf(usuario.getDataCadastro()));
            stmt.setString(7, usuario.getTipoUsuario().name());
            if (usuario.getReputacaoTutor() != null) {
                stmt.setInt(8, usuario.getReputacaoTutor());
            } else {
                stmt.setNull(8, Types.INTEGER);
            }

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        usuario.setIdUsuario(rs.getInt(1));
                    }
                }
                System.out.println("Usuário inserido com sucesso! ID: " + usuario.getIdUsuario());
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inserir usuário: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM Usuarios WHERE id_usuario = ?";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarUsuarioDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<Usuario> buscarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuarios";
        try (Connection conn = BancoDados.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                usuarios.add(criarUsuarioDoResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os usuários: " + e.getMessage());
            e.printStackTrace();
        }
        return usuarios;
    }

    public void atualizar(Usuario usuario) {
        String sql = "UPDATE Usuarios SET nome = ?, email = ?, senha_hash = ?, universidade = ?, curso = ?, tipo_usuario = ?, reputacao_tutor = ? WHERE id_usuario = ?";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenhaHash());
            stmt.setString(4, usuario.getUniversidade());
            stmt.setString(5, usuario.getCurso());
            stmt.setString(6, usuario.getTipoUsuario().name());
            if (usuario.getReputacaoTutor() != null) {
                stmt.setInt(7, usuario.getReputacaoTutor());
            } else {
                stmt.setNull(7, Types.INTEGER);
            }
            stmt.setInt(8, usuario.getIdUsuario());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Usuário ID " + usuario.getIdUsuario() + " atualizado com sucesso!");
            } else {
                System.out.println("Nenhum usuário encontrado com ID " + usuario.getIdUsuario() + " para atualizar.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM Usuarios WHERE id_usuario = ?";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Usuário ID " + id + " deletado com sucesso!");
            } else {
                System.out.println("Nenhum usuário encontrado com ID " + id + " para deletar.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao deletar usuário: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Usuario criarUsuarioDoResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_usuario");
        String nome = rs.getString("nome");
        String email = rs.getString("email");
        String senhaHash = rs.getString("senha_hash");
        String universidade = rs.getString("universidade");
        String curso = rs.getString("curso");
        LocalDateTime dataCadastro = rs.getTimestamp("data_cadastro").toLocalDateTime();
        TipoUsuario tipoUsuario = TipoUsuario.valueOf(rs.getString("tipo_usuario"));
        Integer reputacaoTutor = rs.getObject("reputacao_tutor", Integer.class); // Usa getObject para lidar com NULL

        return new Usuario(id, nome, email, senhaHash, universidade, curso, dataCadastro, tipoUsuario, reputacaoTutor);
    }
}