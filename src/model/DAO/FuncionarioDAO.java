package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.bo.Funcionario;

public class FuncionarioDAO implements InterfaceDAO<Funcionario> {

    @Override
    public void Create(Funcionario objeto) {
        String sqlInstrucao = "Insert Into funcionario("
                + " nome, "
                + " usuario, "
                + " senha, "
                + " fone, "
                + " fone2, "
                + " email, "
                + " cep, "
                + " logradouro, "
                + " bairro, "
                + " cidade, "
                + " complemento, "
                + " data_cadastro, "
                + " cpf, "
                + " rg, "
                + " obs, "
                + " status ) "
                + " Values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getNome());
            pstm.setString(2, objeto.getUsuario());
            pstm.setString(3, objeto.getSenha());
            pstm.setString(4, objeto.getFone1());
            pstm.setString(5, objeto.getFone2());
            pstm.setString(6, objeto.getEmail());
            pstm.setString(7, objeto.getCep());
            pstm.setString(8, objeto.getLogradouro());
            pstm.setString(9, objeto.getBairro());
            pstm.setString(10, objeto.getCidade());
            pstm.setString(11, objeto.getComplemento());
            pstm.setString(12, objeto.getDataCadastro());
            pstm.setString(13, objeto.getCpf());
            pstm.setString(14, objeto.getRg());
            pstm.setString(15, objeto.getObs());
            pstm.setString(16, String.valueOf(objeto.getStatus()));

            pstm.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public Funcionario Retrieve(int id) {
        String sqlInstrucao = "Select "
                + " id, "
                + " nome, "
                + " usuario, "
                + " senha, "
                + " fone, "
                + " fone2, "
                + " email, "
                + " cep, "
                + " logradouro, "
                + " bairro, "
                + " cidade, "
                + " complemento, "
                + " data_cadastro, "
                + " cpf, "
                + " rg, "
                + " obs, "
                + " status "
                + " From funcionario"
                + " Where id = ? ";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        Funcionario funcionario = new Funcionario();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setInt(1, id);
            rst = pstm.executeQuery();

            while (rst.next()) {
                funcionario.setId(rst.getInt("id"));
                funcionario.setNome(rst.getString("nome"));
                funcionario.setUsuario(rst.getString("usuario"));
                funcionario.setSenha(rst.getString("senha"));
                funcionario.setFone1(rst.getString("fone"));
                funcionario.setFone2(rst.getString("fone2"));
                funcionario.setEmail(rst.getString("email"));
                funcionario.setCep(rst.getString("cep"));
                funcionario.setLogradouro(rst.getString("logradouro"));
                funcionario.setBairro(rst.getString("bairro"));
                funcionario.setCidade(rst.getString("cidade"));
                funcionario.setComplemento(rst.getString("complemento"));
                funcionario.setDataCadastro(rst.getString("data_cadastro"));
                funcionario.setCpf(rst.getString("cpf"));
                funcionario.setRg(rst.getString("rg"));
                funcionario.setObs(rst.getString("obs"));
                funcionario.setStatus(rst.getString("status").charAt(0));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return funcionario;
        }
    }

    @Override
    public List<Funcionario> Retrieve(String atributo, String valor) {
 
        String sqlInstrucao = "Select "
                + " id, "
                + " nome, "
                + " usuario, "
                + " senha, "
                + " fone, "
                + " fone2, "
                + " email, "
                + " cep, "
                + " logradouro, "
                + " bairro, "
                + " cidade, "
                + " complemento, "
                + " data_cadastro, "
                + " cpf, "
                + " rg, "
                + " obs, "
                + " status "
                + " FROM funcionario"
                + " WHERE " + atributo + " LIKE ?";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<Funcionario> listaFuncionarios = new ArrayList<>();

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, "%" + valor + "%");
            rst = pstm.executeQuery();

            while (rst.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setId(rst.getInt("id"));
                funcionario.setNome(rst.getString("nome"));
                funcionario.setUsuario(rst.getString("usuario"));
                funcionario.setSenha(rst.getString("senha"));
                funcionario.setFone1(rst.getString("fone"));
                funcionario.setFone2(rst.getString("fone2"));
                funcionario.setEmail(rst.getString("email"));
                funcionario.setCep(rst.getString("cep"));
                funcionario.setLogradouro(rst.getString("logradouro"));
                funcionario.setBairro(rst.getString("bairro"));
                funcionario.setCidade(rst.getString("cidade"));
                funcionario.setComplemento(rst.getString("complemento"));
                funcionario.setDataCadastro(rst.getString("data_cadastro"));
                funcionario.setCpf(rst.getString("cpf"));
                funcionario.setRg(rst.getString("rg"));
                funcionario.setObs(rst.getString("obs"));
                funcionario.setStatus(rst.getString("status").charAt(0));
                listaFuncionarios.add(funcionario);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm, rst);
            return listaFuncionarios;
        }
    }

    @Override
    public void Update(Funcionario objeto) {
        String sqlInstrucao = "Update funcionario "
                + " Set"
                + " nome = ?, "
                + " usuario = ?, "
                + " senha = ?, "
                + " fone = ?, "
                + " fone2 = ?, "
                + " email = ?, "
                + " cep = ?, "
                + " logradouro = ?, "
                + " bairro = ?, "
                + " cidade = ?, "
                + " complemento = ?, "
                + " cpf = ?, "
                + " rg = ?, "
                + " obs = ?, "
                + " status = ? "
                + " Where id = ? ";

        Connection conexao = model.DAO.ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            pstm = conexao.prepareStatement(sqlInstrucao);
            pstm.setString(1, objeto.getNome());
            pstm.setString(2, objeto.getUsuario());
            pstm.setString(3, objeto.getSenha());
            pstm.setString(4, objeto.getFone1());
            pstm.setString(5, objeto.getFone2());
            pstm.setString(6, objeto.getEmail());
            pstm.setString(7, objeto.getCep());
            pstm.setString(8, objeto.getLogradouro());
            pstm.setString(9, objeto.getBairro());
            pstm.setString(10, objeto.getCidade());
            pstm.setString(11, objeto.getComplemento());
            pstm.setString(12, objeto.getCpf());
            pstm.setString(13, objeto.getRg());
            pstm.setString(14, objeto.getObs());
            pstm.setString(15, String.valueOf(objeto.getStatus()));
            pstm.setInt(16, objeto.getId());

            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(conexao, pstm);
        }
    }

    @Override
    public void Delete(Funcionario objeto) {
    }
}