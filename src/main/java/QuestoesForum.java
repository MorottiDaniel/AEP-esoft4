import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class QuestoesForum {
    private int idQuestao;
    private String tituloQuestao;
    private String conteudoQuestao;
    private LocalDateTime dataPostagem;
    private Usuario aluno;
    private Material material;
    private StatusQuestao statusQuestao;
    private List<RespostasForum> respostas;

    public QuestoesForum(int idQuestao, String tituloQuestao, String conteudoQuestao,
                         LocalDateTime dataPostagem, Usuario aluno, Material material, StatusQuestao statusQuestao) {
        this.idQuestao = idQuestao;
        this.tituloQuestao = tituloQuestao;
        this.conteudoQuestao = conteudoQuestao;
        this.dataPostagem = dataPostagem;
        this.aluno = aluno;
        this.material = material;
        this.statusQuestao = statusQuestao;
        this.respostas = new ArrayList<>();
    }

    public QuestoesForum(String tituloQuestao, String conteudoQuestao,
                         Usuario aluno, Material material) {
        this.tituloQuestao = tituloQuestao;
        this.conteudoQuestao = conteudoQuestao;
        this.aluno = aluno;
        this.material = material;
        this.statusQuestao = StatusQuestao.ABERTA;
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

    public Material getMaterial() {
        return material;
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

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void setStatusQuestao(StatusQuestao statusQuestao) {
        this.statusQuestao = statusQuestao;
    }

    public void addResposta(RespostasForum resposta) {
        this.respostas.add(resposta);
    }

    @Override
    public String toString() {
        return  String.format("%s" +
                "\n%s" +
                "\nDe: %s| Data Postagem: %s| Status: %s", tituloQuestao,conteudoQuestao,aluno.getNome(), dataPostagem, statusQuestao);

    }

}
