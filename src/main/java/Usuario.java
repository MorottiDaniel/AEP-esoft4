import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Enum para o tipo de usuário
enum TipoUsuario {
    ALUNO,
    TUTOR,
    ADMIN
}

// Classe Usuario
class Usuario {
    private int idUsuario;
    private String nome;
    private String email;
    private String senhaHash;
    private String universidade;
    private String curso;
    private LocalDateTime dataCadastro;
    private TipoUsuario tipoUsuario;
    private Integer reputacaoTutor;// Pode ser null para alunos

    public Usuario(int idUsuario, String nome, String email, String senhaHash, String universidade,
                   String curso, LocalDateTime dataCadastro, TipoUsuario tipoUsuario, Integer reputacaoTutor) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.universidade = universidade;
        this.curso = curso;
        this.dataCadastro = dataCadastro;
        this.tipoUsuario = tipoUsuario;
        this.reputacaoTutor = reputacaoTutor;
    }

    public Usuario(String nome, String email, String senhaHash, String universidade,
                   String curso, TipoUsuario tipoUsuario) {
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.universidade = universidade;
        this.curso = curso;
        this.tipoUsuario = tipoUsuario;
    }


    // Getters
    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public String getUniversidade() {
        return universidade;
    }

    public String getCurso() {
        return curso;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public Integer getReputacaoTutor() {
        return reputacaoTutor;
    }

    // Setters
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public void setUniversidade(String universidade) {
        this.universidade = universidade;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public void setReputacaoTutor(Integer reputacaoTutor) {
        this.reputacaoTutor = reputacaoTutor;
    }

    @Override
    public String toString() {
        if (tipoUsuario == TipoUsuario.ALUNO) {
            return String.format("\nID: %d" +
                    "\nNome: %s" +
                    "\nEmail: %s" +
                    "\nSenha: %s" +
                    "\nUniversidade: %s" +
                    "\nCurso: %s" +
                    "\ntipo: " + tipoUsuario, idUsuario, nome, email, senhaHash, universidade, curso);

        }else {
            return String.format("\nID: %d" +
                    "\nNome: %s" +
                    "\nEmail: %s" +
                    "\nSenha: %s" +
                    "\nUniversidade: %s" +
                    "\nCurso: %s" +
                    "\ntipo: " + tipoUsuario +
                    "\nReputação: %d", idUsuario, nome, email, senhaHash, universidade, curso, reputacaoTutor);
    }


    }


}
