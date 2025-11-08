import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class RespostasForum {
    private int idResposta;
    private String conteudoResposta;
    private LocalDateTime dataResposta;
    private Usuario usuario; // FK para Usuario (quem respondeu)
    private QuestoesForum questao; // FK para QuestoesForum
    private boolean validada;

    public RespostasForum(int idResposta, String conteudoResposta, LocalDateTime dataResposta,
                          Usuario usuario, QuestoesForum questao, boolean validada) {
        this.idResposta = idResposta;
        this.conteudoResposta = conteudoResposta;
        this.dataResposta = dataResposta;
        this.usuario = usuario;
        this.questao = questao;
        this.validada = validada;
    }

    public RespostasForum(String conteudoResposta, LocalDateTime dataResposta,
                          Usuario usuario, QuestoesForum questao, boolean validada) {
        this.conteudoResposta = conteudoResposta;
        this.dataResposta = dataResposta;
        this.usuario = usuario;
        this.questao = questao;
        this.validada = validada;
    }

    // Getters
    public int getIdResposta() {
        return idResposta;
    }

    public String getConteudoResposta() {
        return conteudoResposta;
    }

    public LocalDateTime getDataResposta() {
        return dataResposta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public QuestoesForum getQuestao() {
        return questao;
    }

    public boolean isValidada() {
        return validada;
    }

    // Setters
    public void setIdResposta(int idResposta) {
        this.idResposta = idResposta;
    }

    public void setConteudoResposta(String conteudoResposta) {
        this.conteudoResposta = conteudoResposta;
    }

    public void setDataResposta(LocalDateTime dataResposta) {
        this.dataResposta = dataResposta;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setQuestao(QuestoesForum questao) {
        this.questao = questao;
    }

    public void setValidada(boolean validada) {
        this.validada = validada;
    }

    @Override
    public String toString() {
        return "RespostasForum{" +
                "idResposta=" + idResposta +
                ", conteudoResposta='" + conteudoResposta + '\'' +
                ", dataResposta=" + dataResposta +
                ", usuario=" + (usuario != null ? usuario.getNome() : "N/A") +
                ", idQuestao=" + (questao != null ? questao.getIdQuestao() : "N/A") +
                ", validada=" + validada +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RespostasForum that = (RespostasForum) o;
        return idResposta == that.idResposta;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idResposta);
    }
}