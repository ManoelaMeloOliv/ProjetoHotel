package model.bo;

public class Reserva {

    private int id;
    private String dataHoraReserva;
    private String dataPrevistaEntrada;
    private String dataPrevistaSaida;
    private String obs;
    private char status;

    public Reserva() {
    }

    public Reserva(int id, String dataHoraReserva, String dataPrevistaEntrada, String dataPrevistaSaida, String obs, char status) {
        this.id = id;
        this.dataHoraReserva = dataHoraReserva;
        this.dataPrevistaEntrada = dataPrevistaEntrada;
        this.dataPrevistaSaida = dataPrevistaSaida;
        this.obs = obs;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDataHoraReserva() {
        return dataHoraReserva;
    }

    public void setDataHoraReserva(String dataHoraReserva) {
        this.dataHoraReserva = dataHoraReserva;
    }

    public String getDataPrevistaEntrada() {
        return dataPrevistaEntrada;
    }

    public void setDataPrevistaEntrada(String dataPrevistaEntrada) {
        this.dataPrevistaEntrada = dataPrevistaEntrada;
    }

    public String getDataPrevistaSaida() {
        return dataPrevistaSaida;
    }

    public void setDataPrevistaSaida(String dataPrevistaSaida) {
        this.dataPrevistaSaida = dataPrevistaSaida;
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
