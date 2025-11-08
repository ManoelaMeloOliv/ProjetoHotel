package service;

import java.util.List;
import model.DAO.ServicoDAO;
import model.bo.Servico;

/**
 * ================================================================
 * SERVICE DE SERVIÇO - CAMADA DE NEGÓCIOS
 * ================================================================
 * 
 * Camada intermediária entre Controller e DAO
 * Contém a lógica de negócio da aplicação
 */
public class ServicoService {

    /**
     * Cria um novo serviço no banco de dados
     */
    public static void Criar(Servico objeto) {
        ServicoDAO servicoDAO = new ServicoDAO();
        servicoDAO.Create(objeto);
    }

    /**
     * Carrega um serviço específico por ID
     */
    public static Servico Carregar(int id) {
        ServicoDAO servicoDAO = new ServicoDAO();
        return servicoDAO.Retrieve(id);
    }

    /**
     * Busca serviços por atributo
     */
    public static List<Servico> Carregar(String atributo, String valor) {
        ServicoDAO servicoDAO = new ServicoDAO();
        return servicoDAO.Retrieve(atributo, valor);
    }

    /**
     * Atualiza um serviço existente
     */
    public static void Atualizar(Servico objeto) {
        ServicoDAO servicoDAO = new ServicoDAO();
        servicoDAO.Update(objeto);
    }

    /**
     * Inativa um serviço (soft delete)
     */
    public static void Apagar(Servico objeto) {
        ServicoDAO servicoDAO = new ServicoDAO();
        servicoDAO.Delete(objeto);
    }
}