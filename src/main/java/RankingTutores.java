import java.util.Objects;

class RankingTutores {
    private int idRanking;
    private Usuario tutor;
    private int pontuacaoTotal;
    private String nivel;

    public RankingTutores(int idRanking, Usuario tutor, int pontuacaoTotal, String nivel) {
        this.idRanking = idRanking;
        this.tutor = tutor;
        this.pontuacaoTotal = pontuacaoTotal;
        this.nivel = nivel;
    }

    public RankingTutores(Usuario tutor, int pontuacaoTotal, String nivel) {
        this.tutor = tutor;
        this.pontuacaoTotal = pontuacaoTotal;
        this.nivel = nivel;
    }

    // Getters
    public int getIdRanking() {
        return idRanking;
    }

    public Usuario getTutor() {
        return tutor;
    }

    public int getPontuacaoTotal() {
        return pontuacaoTotal;
    }

    public String getNivel() {
        return nivel;
    }

    // Setters
    public void setIdRanking(int idRanking) {
        this.idRanking = idRanking;
    }

    public void setTutor(Usuario tutor) {
        this.tutor = tutor;
    }

    public void setPontuacaoTotal(int pontuacaoTotal) {
        this.pontuacaoTotal = pontuacaoTotal;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return "RankingTutores{" +
                "idRanking=" + idRanking +
                ", tutor=" + (tutor != null ? tutor.getNome() : "N/A") +
                ", pontuacaoTotal=" + pontuacaoTotal +
                "de" +
                ", nivel='" + nivel + '\'' +
                '}';
    }

}