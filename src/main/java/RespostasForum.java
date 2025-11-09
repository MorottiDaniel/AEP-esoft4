import java.time.LocalDateTime;

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

    public RespostasForum(String conteudoResposta,
                          Usuario usuario, QuestoesForum questao) {
        this.conteudoResposta = conteudoResposta;
        this.usuario = usuario;
        this.questao = questao;
        this.validada = true;
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
        return String.format("%s\n" +
                "De:%s %s| Validada:%b",conteudoResposta,usuario.getNome(),usuario.getTipoUsuario(),validada);


    }

}