import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class MensagensChat {
    private int idMensagem;
    private Chats chat; // FK para Chats
    private Usuario remetente; // FK para Usuario
    private String conteudoMensagem;
    private LocalDateTime dataEnvio;

    public MensagensChat(int idMensagem, Chats chat, Usuario remetente, String conteudoMensagem, LocalDateTime dataEnvio) {
        this.idMensagem = idMensagem;
        this.chat = chat;
        this.remetente = remetente;
        this.conteudoMensagem = conteudoMensagem;
        this.dataEnvio = dataEnvio;
    }

    public MensagensChat(Chats chat, Usuario remetente, String conteudoMensagem, LocalDateTime dataEnvio) {
        this.chat = chat;
        this.remetente = remetente;
        this.conteudoMensagem = conteudoMensagem;
        this.dataEnvio = dataEnvio;
    }

    // Getters
    public int getIdMensagem() {
        return idMensagem;
    }

    public Chats getChat() {
        return chat;
    }

    public Usuario getRemetente() {
        return remetente;
    }

    public String getConteudoMensagem() {
        return conteudoMensagem;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    // Setters
    public void setIdMensagem(int idMensagem) {
        this.idMensagem = idMensagem;
    }

    public void setChat(Chats chat) {
        this.chat = chat;
    }

    public void setRemetente(Usuario remetente) {
        this.remetente = remetente;
    }

    public void setConteudoMensagem(String conteudoMensagem) {
        this.conteudoMensagem = conteudoMensagem;
    }

    public void setDataEnvio(LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    @Override
    public String toString() {
        return "MensagensChat{" +
                "idMensagem=" + idMensagem +
                ", idChat=" + (chat != null ? chat.getIdChat() : "N/A") +
                ", remetente=" + (remetente != null ? remetente.getNome() : "N/A") +
                ", conteudoMensagem='" + conteudoMensagem + '\'' +
                ", dataEnvio=" + dataEnvio +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MensagensChat that = (MensagensChat) o;
        return idMensagem == that.idMensagem;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMensagem);
    }
}