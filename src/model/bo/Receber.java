package model.bo;

public class Receber {

    private int id;
    private String dataHoraCadastro;
    private float valorOriginal;
    private float desconto;
    private float acrescimo;
    private float valorPago;
    private String obs;
    private char status;

    public Receber() {
    }

    public Receber(int id, String dataHoraCadastro, float valorOriginal, float desconto, float acrescimo, float valorPago, String obs, char status) {
        this.id = id;
        this.dataHoraCadastro = dataHoraCadastro;
        this.valorOriginal = valorOriginal;
        this.desconto = desconto;
        this.acrescimo = acrescimo;
        this.valorPago = valorPago;
        this.obs = obs;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDataHoraCadastro() {
        return dataHoraCadastro;
    }

    public void setDataHoraCadastro(String dataHoraCadastro) {
        this.dataHoraCadastro = dataHoraCadastro;
    }

    public float getValorOriginal() {
        return valorOriginal;
    }

    public void setValorOriginal(float valorOriginal) {
        this.valorOriginal = valorOriginal;
    }

    public float getDesconto() {
        return desconto;
    }

    public void setDesconto(float desconto) {
        this.desconto = desconto;
    }

    public float getAcrescimo() {
        return acrescimo;
    }

    public void setAcrescimo(float acrescimo) {
        this.acrescimo = acrescimo;
    }

    public float getValorPago() {
        return valorPago;
    }

    public void setValorPago(float valorPago) {
        this.valorPago = valorPago;
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
