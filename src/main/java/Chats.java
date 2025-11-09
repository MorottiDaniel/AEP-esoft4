import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Chats {
    private int idChat;
    private Usuario usuario1;
    private Usuario usuario2;
    private LocalDateTime dataInicio;
    private List<MensagensChat> mensagens;

    public Chats(int idChat, Usuario usuario1, Usuario usuario2, LocalDateTime dataInicio) {
        this.idChat = idChat;
        this.usuario1 = usuario1;
        this.usuario2 = usuario2;
        this.dataInicio = dataInicio;
        this.mensagens = new ArrayList<>();
    }

    public Chats(Usuario usuario1, Usuario usuario2, LocalDateTime dataInicio) {
        this.usuario1 = usuario1;
        this.usuario2 = usuario2;
        this.dataInicio = dataInicio;
        this.mensagens = new ArrayList<>();
    }

    // Getters
    public int getIdChat() {
        return idChat;
    }

    public Usuario getUsuario1() {
        return usuario1;
    }

    public Usuario getUsuario2() {
        return usuario2;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public List<MensagensChat> getMensagens() {
        return mensagens;
    }

    // Setters
    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    public void setUsuario1(Usuario usuario1) {
        this.usuario1 = usuario1;
    }

    public void setUsuario2(Usuario usuario2) {
        this.usuario2 = usuario2;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void addMensagem(MensagensChat mensagem) {
        this.mensagens.add(mensagem);
    }

    @Override
    public String toString() {
        return "Chats{" +
                "idChat=" + idChat +
                ", usuario1=" + (usuario1 != null ? usuario1.getNome() : "N/A") +
                ", usuario2=" + (usuario2 != null ? usuario2.getNome() : "N/A") +
                ", dataInicio=" + dataInicio +
                ", numMensagens=" + mensagens.size() +
                '}';
    }
}
