package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.bo.VagaEstacionamento;

public class VagaEstacionamentoDAO implements InterfaceDAO<VagaEstacionamento> {

    @Override
    public void Create(VagaEstacionamento objeto) {
        String sqlInstrucao = "INSERT INTO vaga_estacionamento("
                + " descricao, "
                + " obs, "
                + " metragem_vaga, "
                + " status) "
                + " VALUES (?, ?, ?, ?)";

        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setString(2, objeto.getObs());
            pstm.setFloat(3, objeto.getMetragemVaga());
            pstm.setString(4, String.valueOf(objeto.getStatus()));

            pstm.execute();
            System.out.println(" Vaga de estacionamento cadastrada com sucesso!");

        } catch (SQLException ex) {
            System.err.println(" Erro ao cadastrar vaga:");
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public VagaEstacionamento Retrieve(int id) {
        String sqlInstrucao = "SELECT "
                + " id, "
                + " descricao, "
                + " obs, "
                + " metragem_vaga, "
                + " status "
                + " FROM vaga_estacionamento "
                + " WHERE id = ?";

        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        VagaEstacionamento vaga = new VagaEstacionamento();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();

            if (rst.next()) {
                vaga.setId(rst.getInt("id"));
                vaga.setDescricao(rst.getString("descricao"));
                vaga.setObs(rst.getString("obs"));
                vaga.setMetragemVaga(rst.getFloat("metragem_vaga"));
                vaga.setStatus(rst.getString("status").charAt(0));
            }

        } catch (SQLException ex) {
            System.err.println("Erro ao buscar vaga por ID:");
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return vaga;
        }
    }

    @Override
    public List<VagaEstacionamento> Retrieve(String atributo, String valor) {
        String sqlInstrucao = "SELECT "
                + " id, "
                + " descricao, "
                + " obs, "
                + " metragem_vaga, "
                + " status "
                + " FROM vaga_estacionamento "
                + " WHERE " + atributo + " LIKE ?";

        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<VagaEstacionamento> listaVagas = new ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            rst = pstm.executeQuery();

            while (rst.next()) {
                VagaEstacionamento vaga = new VagaEstacionamento();
                vaga.setId(rst.getInt("id"));
                vaga.setDescricao(rst.getString("descricao"));
                vaga.setObs(rst.getString("obs"));
                vaga.setMetragemVaga(rst.getFloat("metragem_vaga"));
                vaga.setStatus(rst.getString("status").charAt(0));
                listaVagas.add(vaga);
            }

        } catch (SQLException ex) {
            System.err.println("Erro ao buscar vagas:");
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return listaVagas;
        }
    }

    @Override
    public void Update(VagaEstacionamento objeto) {
        String sqlInstrucao = "UPDATE vaga_estacionamento SET "
                + " descricao = ?, "
                + " obs = ?, "
                + " metragem_vaga = ?, "
                + " status = ? "
                + " WHERE id = ?";

        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getDescricao());
            pstm.setString(2, objeto.getObs());
            pstm.setFloat(3, objeto.getMetragemVaga());
            pstm.setString(4, String.valueOf(objeto.getStatus()));
            pstm.setInt(5, objeto.getId());

            pstm.execute();
            System.out.println(" Vaga atualizada com sucesso!");

        } catch (SQLException ex) {
            System.err.println(" Erro ao atualizar vaga:");
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public void Delete(VagaEstacionamento objeto) {

    }
}