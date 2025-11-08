package model.bo;

public class Quarto {

    private int id;
    private String descricao;
    private int capacidadeDeHospede;
    private float metragem;
    private String indentificacao;
    private int andar;
    private boolean flagAnimais;
    private String obs;
    private char status;

    public Quarto() {
    }

    public Quarto(int id, String descricao, int capacidadeDeHospede, float metragem, String indentificacao, int andar, boolean flagAnimais, String obs, char status) {
        this.id = id;
        this.descricao = descricao;
        this.capacidadeDeHospede = capacidadeDeHospede;
        this.metragem = metragem;
        this.indentificacao = indentificacao;
        this.andar = andar;
        this.flagAnimais = flagAnimais;
        this.obs = obs;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCapacidadeDeHospede() {
        return capacidadeDeHospede;
    }

    public void setCapacidadeDeHospede(int capacidadeDeHospede) {
        this.capacidadeDeHospede = capacidadeDeHospede;
    }

    public float getMetragem() {
        return metragem;
    }

    public void setMetragem(float metragem) {
        this.metragem = metragem;
    }

    public String getIndentificacao() {
        return indentificacao;
    }

    public void setIndentificacao(String indentificacao) {
        this.indentificacao = indentificacao;
    }

    public int getAndar() {
        return andar;
    }

    public void setAndar(int andar) {
        this.andar = andar;
    }

    public boolean isFlagAnimais() {
        return flagAnimais;
    }

    public void setFlagAnimais(boolean flagAnimais) {
        this.flagAnimais = flagAnimais;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

}
