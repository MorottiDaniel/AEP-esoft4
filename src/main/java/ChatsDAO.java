import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChatsDAO {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    // MensagensChatDAO seria usado para carregar as mensagens de um chat, mas não para o objeto Chat em si.

    public void inserir(Chats chat) {
        String sql = "INSERT INTO Chats (id_usuario1, id_usuario2, data_inicio) VALUES (?, ?, ?)";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, chat.getUsuario1().getIdUsuario());
            stmt.setInt(2, chat.getUsuario2().getIdUsuario());
            stmt.setTimestamp(3, Timestamp.valueOf(chat.getDataInicio()));

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        chat.setIdChat(rs.getInt(1));
                    }
                }
                System.out.println("Chat inserido com sucesso! ID: " + chat.getIdChat());
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inserir chat: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Chats buscarPorId(int id) {
        String sql = "SELECT * FROM Chats WHERE id_chat = ?";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarChatDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar chat por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public Chats buscarPorUsuarios(int idUsuario1, int idUsuario2) {
        // Busca um chat entre dois usuários específicos, independentemente da ordem
        String sql = "SELECT * FROM Chats WHERE (id_usuario1 = ? AND id_usuario2 = ?) OR (id_usuario1 = ? AND id_usuario2 = ?)";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario1);
            stmt.setInt(2, idUsuario2);
            stmt.setInt(3, idUsuario2);
            stmt.setInt(4, idUsuario1);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarChatDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar chat por usuários: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    public List<Chats> buscarTodos() {
        List<Chats> chats = new ArrayList<>();
        String sql = "SELECT * FROM Chats";
        try (Connection conn = BancoDados.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                chats.add(criarChatDoResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os chats: " + e.getMessage());
            e.printStackTrace();
        }
        return chats;
    }

    public void atualizar(Chats chat) {
        String sql = "UPDATE Chats SET id_usuario1 = ?, id_usuario2 = ? WHERE id_chat = ?";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, chat.getUsuario1().getIdUsuario());
            stmt.setInt(2, chat.getUsuario2().getIdUsuario());
            stmt.setInt(3, chat.getIdChat());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Chat ID " + chat.getIdChat() + " atualizado com sucesso!");
            } else {
                System.out.println("Nenhum chat encontrado com ID " + chat.getIdChat() + " para atualizar.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar chat: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM Chats WHERE id_chat = ?";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Chat ID " + id + " deletado com sucesso!");
            } else {
                System.out.println("Nenhum chat encontrado com ID " + id + " para deletar.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao deletar chat: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Chats criarChatDoResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_chat");
        int idUsuario1 = rs.getInt("id_usuario1");
        int idUsuario2 = rs.getInt("id_usuario2");
        LocalDateTime dataInicio = rs.getTimestamp("data_inicio").toLocalDateTime();

        Usuario usuario1 = usuarioDAO.buscarPorId(idUsuario1);
        Usuario usuario2 = usuarioDAO.buscarPorId(idUsuario2);

        // Não carregamos as mensagens aqui para evitar sobrecarga.
        // As mensagens seriam carregadas separadamente por um MensagensChatDAO.buscarPorChat(idChat).
        Chats chat = new Chats(id, usuario1, usuario2, dataInicio);

        // Se desejar carregar as mensagens imediatamente:
        // MensagensChatDAO mensagensChatDAO = new MensagensChatDAO(); // Instancie se necessário
        // chat.getMensagens().addAll(mensagensChatDAO.buscarPorChat(id));

        return chat;
    }
}