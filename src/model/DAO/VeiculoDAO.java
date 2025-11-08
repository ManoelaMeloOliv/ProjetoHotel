package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.bo.Marca;
import model.bo.Modelo;
import model.bo.Veiculo;

public class VeiculoDAO implements InterfaceDAO<Veiculo> {
   

    @Override
    public void Create(Veiculo objeto) {
        String sqlInstrucao = "INSERT INTO veiculo("
                + " placa, "
                + " cor, "
                + " modelo_id, "
                + " status) "
                + " VALUES (?, ?, ?, ?)";
        
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        
        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getPlaca());
            pstm.setString(2, objeto.getCor());
            pstm.setInt(3, objeto.getModelo().getId());
            pstm.setString(4, String.valueOf(objeto.getStatus()));
           
            pstm.execute();
            System.out.println(" Veículo cadastrado com sucesso!");

        } catch (SQLException ex) {
            System.err.println(" Erro ao cadastrar veículo:");
            ex.printStackTrace();

           
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public Veiculo Retrieve(int id) {
        String sqlInstrucao = "SELECT "
                + " v.id, "
                + " v.placa, "
                + " v.cor, "
                + " v.status, "
                + " v.modelo_id, "
                + " mo.descricao AS modelo_descricao, "
                + " mo.status AS modelo_status, "
                + " ma.id AS marca_id, "
                + " ma.descricao AS marca_descricao, "
                + " ma.status AS marca_status "
                + " FROM veiculo v "
                + " INNER JOIN modelo mo ON v.modelo_id = mo.id "
                + " INNER JOIN marca ma ON mo.marca_id = ma.id "
                + " WHERE v.id = ?";

        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        Veiculo veiculo = new Veiculo();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();

            if (rst.next()) {
                veiculo.setId(rst.getInt("id"));
                veiculo.setPlaca(rst.getString("placa"));
                veiculo.setCor(rst.getString("cor"));
                veiculo.setStatus(rst.getString("status").charAt(0));

                Marca marca = new Marca();
                marca.setId(rst.getInt("marca_id"));
                marca.setDescricao(rst.getString("marca_descricao"));
                marca.setStatus(rst.getString("marca_status").charAt(0));

                Modelo modelo = new Modelo();
                modelo.setId(rst.getInt("modelo_id"));
                modelo.setDescricao(rst.getString("modelo_descricao"));
                modelo.setStatus(rst.getString("modelo_status").charAt(0));
                modelo.setMarca(marca);

                veiculo.setModelo(modelo);
            }

        } catch (SQLException ex) {
            System.err.println(" Erro ao buscar veículo por ID:");
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return veiculo;
        }
    }
    

    @Override
    public List<Veiculo> Retrieve(String atributo, String valor) {
        String sqlInstrucao = "SELECT "
                + " v.id, "
                + " v.placa, "
                + " v.cor, "
                + " v.status, "
                + " v.modelo_id, "
                + " mo.descricao AS modelo_descricao, "
                + " mo.status AS modelo_status, "
                + " ma.id AS marca_id, "
                + " ma.descricao AS marca_descricao, "
                + " ma.status AS marca_status "
                + " FROM veiculo v "
                + " INNER JOIN modelo mo ON v.modelo_id = mo.id "
                + " INNER JOIN marca ma ON mo.marca_id = ma.id "
                + " WHERE " + atributo + " LIKE ? "
                + " AND v.status != 'C'";  

        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Veiculo> listaVeiculos = new ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            rst = pstm.executeQuery();
            
            while (rst.next()) {
                Veiculo veiculo = new Veiculo();
                veiculo.setId(rst.getInt("id"));
                veiculo.setPlaca(rst.getString("placa"));
                veiculo.setCor(rst.getString("cor"));
                veiculo.setStatus(rst.getString("status").charAt(0));

                Marca marca = new Marca();
                marca.setId(rst.getInt("marca_id"));
                marca.setDescricao(rst.getString("marca_descricao"));
                marca.setStatus(rst.getString("marca_status").charAt(0));

                Modelo modelo = new Modelo();
                modelo.setId(rst.getInt("modelo_id"));
                modelo.setDescricao(rst.getString("modelo_descricao"));
                modelo.setStatus(rst.getString("modelo_status").charAt(0));
                modelo.setMarca(marca);

                veiculo.setModelo(modelo);
                listaVeiculos.add(veiculo);
            }

        } catch (SQLException ex) {
            System.err.println(" Erro ao buscar veículos:");
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return listaVeiculos;
        }
    }

    public List<Veiculo> Retrieve() {
        String sqlInstrucao = "SELECT "
                + " v.id, "
                + " v.placa, "
                + " v.cor, "
                + " v.status, "
                + " v.modelo_id, "
                + " mo.descricao AS modelo_descricao, "
                + " mo.status AS modelo_status, "
                + " ma.id AS marca_id, "
                + " ma.descricao AS marca_descricao, "
                + " ma.status AS marca_status "
                + " FROM veiculo v "
                + " INNER JOIN modelo mo ON v.modelo_id = mo.id "
                + " INNER JOIN marca ma ON mo.marca_id = ma.id "
                + " WHERE v.status != 'C'";  

        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Veiculo> listaVeiculos = new ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            rst = pstm.executeQuery();

            while (rst.next()) {
                Veiculo veiculo = new Veiculo();
                veiculo.setId(rst.getInt("id"));
                veiculo.setPlaca(rst.getString("placa"));
                veiculo.setCor(rst.getString("cor"));
                veiculo.setStatus(rst.getString("status").charAt(0));

                Marca marca = new Marca();
                marca.setId(rst.getInt("marca_id"));
                marca.setDescricao(rst.getString("marca_descricao"));
                marca.setStatus(rst.getString("marca_status").charAt(0));

                Modelo modelo = new Modelo();
                modelo.setId(rst.getInt("modelo_id"));
                modelo.setDescricao(rst.getString("modelo_descricao"));
                modelo.setStatus(rst.getString("modelo_status").charAt(0));
                modelo.setMarca(marca);

                veiculo.setModelo(modelo);
                listaVeiculos.add(veiculo);
            }

            System.out.println(" Total de veículos carregados: " + listaVeiculos.size());

        } catch (SQLException ex) {
            System.err.println(" Erro ao buscar todos os veículos:");
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return listaVeiculos;
        }
    }

    @Override
    public void Update(Veiculo objeto) {
        String sqlInstrucao = "UPDATE veiculo "
                + " SET "
                + " placa = ?, "
                + " cor = ?, "
                + " modelo_id = ?, "
                + " status = ? "
                + " WHERE id = ?";

        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getPlaca());
            pstm.setString(2, objeto.getCor());
            pstm.setInt(3, objeto.getModelo().getId());
            pstm.setString(4, String.valueOf(objeto.getStatus()));
            pstm.setInt(5, objeto.getId());

            pstm.execute();
            System.out.println(" Veículo atualizado com sucesso!");

        } catch (SQLException ex) {
            System.err.println("Erro ao atualizar veículo:");
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public void Delete(Veiculo objeto) {
       
}
}