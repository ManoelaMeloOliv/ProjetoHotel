package model.bo;

public class Caixa {

    private int id;
    private float valorDeAbertura;
    private float valorDeFechamento;
    private String dataHoraDeAbertura;
    private String dataHoraDeFechamento;
    private String obs;
    private char status;

    public Caixa() {
    }

    public Caixa(int id, float valorDeAbertura, float valorDeFechamento, String dataHoraDeAbertura, String dataHoraDeFechamento, String obs, char status) {
        this.id = id;
        this.valorDeAbertura = valorDeAbertura;
        this.valorDeFechamento = valorDeFechamento;
        this.dataHoraDeAbertura = dataHoraDeAbertura;
        this.dataHoraDeFechamento = dataHoraDeFechamento;
        this.obs = obs;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getValorDeAbertura() {
        return valorDeAbertura;
    }

    public void setValorDeAbertura(float valorDeAbertura) {
        this.valorDeAbertura = valorDeAbertura;
    }

    public float getValorDeFechamento() {
        return valorDeFechamento;
    }

    public void setValorDeFechamento(float valorDeFechamento) {
        this.valorDeFechamento = valorDeFechamento;
    }

    public String getDataHoraDeAbertura() {
        return dataHoraDeAbertura;
    }

    public void setDataHoraDeAbertura(String dataHoraDeAbertura) {
        this.dataHoraDeAbertura = dataHoraDeAbertura;
    }

    public String getDataHoraDeFechamento() {
        return dataHoraDeFechamento;
    }

    public void setDataHoraDeFechamento(String dataHoraDeFechamento) {
        this.dataHoraDeFechamento = dataHoraDeFechamento;
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
