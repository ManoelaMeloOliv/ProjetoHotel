package model.bo;

public class Veiculo {
    private int id;
    private String placa;
    private String cor;
    private char status;
    private Modelo modelo;

    public Veiculo() {
    }

    public Veiculo(int id, String placa, String cor, char status, Modelo modelo) {
        this.id = id;
        this.placa = placa;
        this.cor = cor;
        this.status = status;
        this.modelo = modelo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        if ((status == 'A') || (status == 'C') || (status == 'a') || (status == 'c')) {
            this.status = status;
        } else {
            this.status = 'A';
        }
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    @Override
    public String toString() {
        return "id     = " + this.getId()
                + "\nPlaca  = " + this.getPlaca()
                + "\nCor    = " + this.getCor()
                + "\nModelo = " + (this.modelo != null ? this.modelo.getDescricao() : "N/A")
                + "\nMarca  = " + (this.modelo != null && this.modelo.getMarca() != null ? this.modelo.getMarca().getDescricao() : "N/A")
                + "\nStatus = " + this.getStatus();
    }
}