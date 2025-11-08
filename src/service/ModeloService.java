package service;

import java.util.List;
import model.DAO.ModeloDAO;
import model.bo.Modelo;

/**
 * ================================================================
 * SERVICE DE VEÍCULO - CAMADA DE NEGÓCIOS
 * ================================================================
 */
public class ModeloService {

    public static void Criar(Modelo objeto) {
        ModeloDAO veiculoDAO = new ModeloDAO();
        veiculoDAO.Create(objeto);
    }

    public static Modelo Carregar(int id) {
        ModeloDAO veiculoDAO = new ModeloDAO();
        return veiculoDAO.Retrieve(id);
    }

    public static List<Modelo> Carregar(String atributo, String valor) {
        ModeloDAO veiculoDAO = new ModeloDAO();
        return veiculoDAO.Retrieve(atributo, valor);
    }

    public static void Atualizar(Modelo objeto) {
        ModeloDAO veiculoDAO = new ModeloDAO();
        veiculoDAO.Update(objeto);
    }

    public static void Apagar(Modelo objeto) {
        ModeloDAO veiculoDAO = new ModeloDAO();
        veiculoDAO.Delete(objeto);
    }
}