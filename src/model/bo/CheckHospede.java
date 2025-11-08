package model.bo;

public class CheckHospede {

    private int id;
    private String tipoHospede;
    private String obs;
    private char status;

    public CheckHospede() {
    }

    public CheckHospede(int id, String tipoHospede, String obs, char status) {
        this.id = id;
        this.tipoHospede = tipoHospede;
        this.obs = obs;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoHospede() {
        return tipoHospede;
    }

    public void setTipoHospede(String tipoHospede) {
        this.tipoHospede = tipoHospede;
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
