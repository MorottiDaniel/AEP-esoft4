import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Disciplina {
    private int idDisciplina;
    private String nomeDisciplina;
    private String ementa;

    public Disciplina(int idDisciplina, String nomeDisciplina, String ementa) {
        this.idDisciplina = idDisciplina;
        this.nomeDisciplina = nomeDisciplina;
        this.ementa = ementa;
    }

    public Disciplina(String nomeDisciplina, String ementa) {
        this.nomeDisciplina = nomeDisciplina;
        this.ementa = ementa;
    }

    // Getters
    public int getIdDisciplina() {
        return idDisciplina;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public String getEmenta() {
        return ementa;
    }

    // Setters
    public void setIdDisciplina(int idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public void setEmenta(String ementa) {
        this.ementa = ementa;
    }

    @Override
    public String toString() {
        return String.format("Nome: %s\n" +
                "Descrição: %s",nomeDisciplina,ementa);
    }

}
