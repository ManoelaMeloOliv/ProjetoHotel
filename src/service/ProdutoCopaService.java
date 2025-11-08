package service;

import java.util.List;
import model.DAO.ProdutoCopaDAO;
import model.bo.ProdutoCopa;

/**
 * ================================================================
 * SERVICE DE PRODUTO COPA - CAMADA DE NEGÓCIOS
 * ================================================================
 */
public class ProdutoCopaService {

    public static void Criar(ProdutoCopa objeto) {
        ProdutoCopaDAO produtoDAO = new ProdutoCopaDAO();
        produtoDAO.Create(objeto);
    }

    public static ProdutoCopa Carregar(int id) {
        ProdutoCopaDAO produtoDAO = new ProdutoCopaDAO();
        return produtoDAO.Retrieve(id);
    }

    public static List<ProdutoCopa> Carregar(String atributo, String valor) {
        ProdutoCopaDAO produtoDAO = new ProdutoCopaDAO();
        return produtoDAO.Retrieve(atributo, valor);
    }

    public static void Atualizar(ProdutoCopa objeto) {
        ProdutoCopaDAO produtoDAO = new ProdutoCopaDAO();
        produtoDAO.Update(objeto);
    }

    public static void Apagar(ProdutoCopa objeto) {
        ProdutoCopaDAO produtoDAO = new ProdutoCopaDAO();
        produtoDAO.Delete(objeto);
    }
}