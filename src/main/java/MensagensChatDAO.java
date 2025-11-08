import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MensagensChatDAO {

    private ChatsDAO chatsDAO = new ChatsDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void inserir(MensagensChat mensagem) {
        String sql = "INSERT INTO MensagensChat (id_chat, id_remetente, conteudo_mensagem, data_envio) VALUES (?, ?, ?, ?)";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, mensagem.getChat().getIdChat());
            stmt.setInt(2, mensagem.getRemetente().getIdUsuario());
            stmt.setString(3, mensagem.getConteudoMensagem());
            stmt.setTimestamp(4, Timestamp.valueOf(mensagem.getDataEnvio()));

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        mensagem.setIdMensagem(rs.getInt(1));
                    }
                }
                System.out.println("Mensagem de chat inserida com sucesso! ID: " + mensagem.getIdMensagem());
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inserir mensagem de chat: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public MensagensChat buscarPorId(int id) {
        String sql = "SELECT * FROM MensagensChat WHERE id_mensagem = ?";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarMensagemDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar mensagem de chat por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<MensagensChat> buscarPorChat(int idChat) {
        List<MensagensChat> mensagens = new ArrayList<>();
        String sql = "SELECT * FROM MensagensChat WHERE id_chat = ? ORDER BY data_envio ASC";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idChat);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    mensagens.add(criarMensagemDoResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar mensagens por chat: " + e.getMessage());
            e.printStackTrace();
        }
        return mensagens;
    }


    public List<MensagensChat> buscarTodos() {
        List<MensagensChat> mensagens = new ArrayList<>();
        String sql = "SELECT * FROM MensagensChat";
        try (Connection conn = BancoDados.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                mensagens.add(criarMensagemDoResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todas as mensagens de chat: " + e.getMessage());
            e.printStackTrace();
        }
        return mensagens;
    }

    public void atualizar(MensagensChat mensagem) {
        String sql = "UPDATE MensagensChat SET id_chat = ?, id_remetente = ?, conteudo_mensagem = ? WHERE id_mensagem = ?";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, mensagem.getChat().getIdChat());
            stmt.setInt(2, mensagem.getRemetente().getIdUsuario());
            stmt.setString(3, mensagem.getConteudoMensagem());
            stmt.setInt(4, mensagem.getIdMensagem());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Mensagem de chat ID " + mensagem.getIdMensagem() + " atualizada com sucesso!");
            } else {
                System.out.println("Nenhuma mensagem de chat encontrada com ID " + mensagem.getIdMensagem() + " para atualizar.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar mensagem de chat: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM MensagensChat WHERE id_mensagem = ?";
        try (Connection conn = BancoDados.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Mensagem de chat ID " + id + " deletada com sucesso!");
            } else {
                System.out.println("Nenhuma mensagem de chat encontrada com ID " + id + " para deletar.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao deletar mensagem de chat: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private MensagensChat criarMensagemDoResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_mensagem");
        int idChat = rs.getInt("id_chat");
        int idRemetente = rs.getInt("id_remetente");
        String conteudoMensagem = rs.getString("conteudo_mensagem");
        LocalDateTime dataEnvio = rs.getTimestamp("data_envio").toLocalDateTime();

        Chats chat = chatsDAO.buscarPorId(idChat);
        Usuario remetente = usuarioDAO.buscarPorId(idRemetente);

        return new MensagensChat(id, chat, remetente, conteudoMensagem, dataEnvio);
    }
}