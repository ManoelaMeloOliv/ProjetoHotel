package model.bo;

public class MovimentoCaixa {

    private int id;
    private String dataHoraMovimento;
    private float valor;
    private String descricao;
    private String obs;
    private char status;

    public MovimentoCaixa() {
    }

    public MovimentoCaixa(int id, String dataHoraMovimento, float valor, String descricao, String obs, char status) {
        this.id = id;
        this.dataHoraMovimento = dataHoraMovimento;
        this.valor = valor;
        this.descricao = descricao;
        this.obs = obs;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDataHoraMovimento() {
        return dataHoraMovimento;
    }

    public void setDataHoraMovimento(String dataHoraMovimento) {
        this.dataHoraMovimento = dataHoraMovimento;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
