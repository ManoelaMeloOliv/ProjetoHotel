package service;

import java.util.List;
import model.DAO.VagaEstacionamentoDAO;
import model.bo.VagaEstacionamento;

public class VagaEstacionamentoService {

    public static void Criar(VagaEstacionamento objeto) {
        VagaEstacionamentoDAO vagaEstacionamentoDAO = new VagaEstacionamentoDAO();
        vagaEstacionamentoDAO.Create(objeto);
    }

    public static VagaEstacionamento Carregar(int id) {

        VagaEstacionamentoDAO vagaEstacionamentoDAO = new VagaEstacionamentoDAO();
        return vagaEstacionamentoDAO.Retrieve(id);
    }

    public static List<VagaEstacionamento> Carregar(String atributo, String valor) {
        VagaEstacionamentoDAO vagaEstacionamentoDAO = new VagaEstacionamentoDAO();
        return vagaEstacionamentoDAO.Retrieve(atributo, valor);
    }

    public static void Atualizar(VagaEstacionamento objeto) {
        VagaEstacionamentoDAO vagaEstacionamentoDAO = new VagaEstacionamentoDAO();
        vagaEstacionamentoDAO.Update(objeto);
    }

    public static void Apagar(VagaEstacionamento objeto) {
        VagaEstacionamentoDAO vagaEstacionamentoDAO = new VagaEstacionamentoDAO();
        vagaEstacionamentoDAO.Delete(objeto);
    }
}