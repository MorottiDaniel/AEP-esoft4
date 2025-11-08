import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class QuestoesForum {
    private int idQuestao;
    private String tituloQuestao;
    private String conteudoQuestao;
    private LocalDateTime dataPostagem;
    private Usuario aluno; // FK para Usuario (aluno)
    private Disciplina disciplina; // FK para Disciplina
    private StatusQuestao statusQuestao;
    private List<RespostasForum> respostas; // Lista de respostas para a questão

    public QuestoesForum(int idQuestao, String tituloQuestao, String conteudoQuestao,
                         LocalDateTime dataPostagem, Usuario aluno, Disciplina disciplina, StatusQuestao statusQuestao) {
        this.idQuestao = idQuestao;
        this.tituloQuestao = tituloQuestao;
        this.conteudoQuestao = conteudoQuestao;
        this.dataPostagem = dataPostagem;
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.statusQuestao = statusQuestao;
        this.respostas = new ArrayList<>();
    }

    public QuestoesForum(String tituloQuestao, String conteudoQuestao,
                         LocalDateTime dataPostagem, Usuario aluno, Disciplina disciplina, StatusQuestao statusQuestao) {
        this.tituloQuestao = tituloQuestao;
        this.conteudoQuestao = conteudoQuestao;
        this.dataPostagem = dataPostagem;
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.statusQuestao = statusQuestao;
        this.respostas = new ArrayList<>();
    }

    // Getters
    public int getIdQuestao() {
        return idQuestao;
    }

    public String getTituloQuestao() {
        return tituloQuestao;
    }

    public String getConteudoQuestao() {
        return conteudoQuestao;
    }

    public LocalDateTime getDataPostagem() {
        return dataPostagem;
    }

    public Usuario getAluno() {
        return aluno;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public StatusQuestao getStatusQuestao() {
        return statusQuestao;
    }

    public List<RespostasForum> getRespostas() {
        return respostas;
    }

    // Setters
    public void setIdQuestao(int idQuestao) {
        this.idQuestao = idQuestao;
    }

    public void setTituloQuestao(String tituloQuestao) {
        this.tituloQuestao = tituloQuestao;
    }

    public void setConteudoQuestao(String conteudoQuestao) {
        this.conteudoQuestao = conteudoQuestao;
    }

    public void setDataPostagem(LocalDateTime dataPostagem) {
        this.dataPostagem = dataPostagem;
    }

    public void setAluno(Usuario aluno) {
        this.aluno = aluno;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public void setStatusQuestao(StatusQuestao statusQuestao) {
        this.statusQuestao = statusQuestao;
    }

    public void addResposta(RespostasForum resposta) {
        this.respostas.add(resposta);
    }

    @Override
    public String toString() {
        return "QuestoesForum{" +
                "idQuestao=" + idQuestao +
                ", tituloQuestao='" + tituloQuestao + '\'' +
                ", conteudoQuestao='" + conteudoQuestao + '\'' +
                ", dataPostagem=" + dataPostagem +
                ", aluno=" + (aluno != null ? aluno.getNome() : "N/A") +
                ", disciplina=" + (disciplina != null ? disciplina.getNomeDisciplina() : "N/A") +
                ", statusQuestao=" + statusQuestao +
                ", numRespostas=" + respostas.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestoesForum that = (QuestoesForum) o;
        return idQuestao == that.idQuestao;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idQuestao);
    }
}
