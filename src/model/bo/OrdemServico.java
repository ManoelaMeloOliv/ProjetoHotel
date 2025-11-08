package model.bo;

public class OrdemServico {

    private int id;
    private String dataHoraCadastro;
    private String dataHoraPrevisaoInicio;
    private String dataHoraPrevisaoTermino;
    private String obs;
    private char status;

    public OrdemServico() {
    }

    public OrdemServico(int id, String dataHoraCadastro, String dataHoraPrevisaoInicio, String dataHoraPrevisaoTermino, String obs, char status) {
        this.id = id;
        this.dataHoraCadastro = dataHoraCadastro;
        this.dataHoraPrevisaoInicio = dataHoraPrevisaoInicio;
        this.dataHoraPrevisaoTermino = dataHoraPrevisaoTermino;
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

    public String getDataHoraPrevisaoInicio() {
        return dataHoraPrevisaoInicio;
    }

    public void setDataHoraPrevisaoInicio(String dataHoraPrevisaoInicio) {
        this.dataHoraPrevisaoInicio = dataHoraPrevisaoInicio;
    }

    public String getDataHoraPrevisaoTermino() {
        return dataHoraPrevisaoTermino;
    }

    public void setDataHoraPrevisaoTermino(String dataHoraPrevisaoTermino) {
        this.dataHoraPrevisaoTermino = dataHoraPrevisaoTermino;
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
