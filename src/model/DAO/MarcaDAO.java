package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.bo.Marca;

public class MarcaDAO implements InterfaceDAO<Marca> {

    @Override
    public void Create(Marca objeto) {
        String sqlInstrucao = "INSERT INTO marca ("
                + "descricao, "
                + "status) "
                + "VALUES (?, ?)";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            
            System.out.println("=== DEBUG CREATE ===");
            System.out.println("Descrição: " + objeto.getDescricao());
            System.out.println("Status: " + objeto.getStatus());
            
            pstm.setString(1, objeto.getDescricao());
            pstm.setString(2, String.valueOf(objeto.getStatus()));

            pstm.execute();
            
            System.out.println("Marca inserida com sucesso!");

        } catch (SQLException ex) {
            System.err.println("Erro no CREATE:");
            System.err.println("SQL: " + sqlInstrucao);
            System.err.println("Descrição: " + objeto.getDescricao());
            System.err.println("Status: " + objeto.getStatus());
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public Marca Retrieve(int id) {
        String sqlInstrucao = "SELECT"
                + " id,"
                + " descricao, "
                + "status "
                + "FROM marca"
                + " WHERE id = ?";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        Marca marca = new Marca();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();

            if (rst.next()) {
                marca.setId(rst.getInt("id"));
                marca.setDescricao(rst.getString("descricao"));
                marca.setStatus(rst.getString("status").charAt(0));
            }
        } catch (SQLException ex) {
            System.err.println("Erro no RETRIEVE por ID:");
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return marca;
        }
    }

    @Override
    public List<Marca> Retrieve(String atributo, String valor) {
        String sqlInstrucao = "SELECT "
                + "id,"
                + " descricao,"
                + " status "
                + "FROM marca "
                + "WHERE " + atributo + " LIKE ? "
                + "AND status != 'C'";  

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Marca> listaMarcas = new ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            rst = pstm.executeQuery();

            while (rst.next()) {
                Marca marca = new Marca();
                marca.setId(rst.getInt("id"));
                marca.setDescricao(rst.getString("descricao"));
                marca.setStatus(rst.getString("status").charAt(0));
                listaMarcas.add(marca);
            }
        } catch (SQLException ex) {
            System.err.println("Erro no RETRIEVE por atributo:");
            System.err.println("Atributo: " + atributo);
            System.err.println("Valor: " + valor);
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return listaMarcas;
        }
    }
    
    public List<Marca> RetrieveAll() {
        String sqlInstrucao = "SELECT "
                + "id,"
                + " descricao,"
                + " status "
                + "FROM marca "
                + "WHERE status != 'C'";  
        
        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Marca> listaMarcas = new ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            rst = pstm.executeQuery();

            while (rst.next()) {
                Marca marca = new Marca();
                marca.setId(rst.getInt("id"));
                marca.setDescricao(rst.getString("descricao"));
                marca.setStatus(rst.getString("status").charAt(0));
                listaMarcas.add(marca);
            }
        } catch (SQLException ex) {
            System.err.println("Erro no RETRIEVE ALL:");
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return listaMarcas;
        }
    }

    @Override
    public void Update(Marca objeto) {
        String sqlInstrucao = "UPDATE marca SET "
                + "descricao = ?,"
                + "status = ? "
                + "WHERE id = ?";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            
            pstm.setString(1, objeto.getDescricao());
            pstm.setString(2, String.valueOf(objeto.getStatus()));
            pstm.setInt(3, objeto.getId());

            pstm.execute();
            
            System.out.println("Marca atualizada com sucesso!");

        } catch (SQLException ex) {
            System.err.println("Erro no UPDATE:");
            System.err.println("ID: " + objeto.getId());
            System.err.println("Descrição: " + objeto.getDescricao());
            System.err.println("Status: " + objeto.getStatus());
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public void Delete(Marca objeto) {
       
    }
}