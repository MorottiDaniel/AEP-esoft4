import java.time.LocalDateTime;
import java.util.Objects;

class Material {
    private int idMaterial;
    private String titulo;
    private String descricao;
    private String caminhoArquivo;
    private LocalDateTime dataUpload;
    private Usuario tutor; // FK para Usuario (tutor)
    private Disciplina disciplina; // FK para Disciplina

    public Material(int idMaterial, String titulo, String descricao, String caminhoArquivo,
                    LocalDateTime dataUpload, Usuario tutor, Disciplina disciplina) {
        this.idMaterial = idMaterial;
        this.titulo = titulo;
        this.descricao = descricao;
        this.caminhoArquivo = caminhoArquivo;
        this.dataUpload = dataUpload;
        this.tutor = tutor;
        this.disciplina = disciplina;
    }

    public Material(String titulo, String descricao, String caminhoArquivo,
                    LocalDateTime dataUpload, Usuario tutor, Disciplina disciplina) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.caminhoArquivo = caminhoArquivo;
        this.dataUpload = dataUpload;
        this.tutor = tutor;
        this.disciplina = disciplina;
    }




    // Getters
    public int getIdMaterial() {
        return idMaterial;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public LocalDateTime getDataUpload() {
        return dataUpload;
    }

    public Usuario getTutor() {
        return tutor;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    // Setters
    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public void setDataUpload(LocalDateTime dataUpload) {
        this.dataUpload = dataUpload;
    }

    public void setTutor(Usuario tutor) {
        this.tutor = tutor;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    @Override
    public String toString() {
        return String.format("%s|Tutor: %s" +
                "\nDescrição: %s ",titulo,tutor.getNome(),descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMaterial);
    }
}

// Enum para o status da questão do fórum
enum StatusQuestao {
    ABERTA,
    RESPONDIDA,
    FECHADA
}