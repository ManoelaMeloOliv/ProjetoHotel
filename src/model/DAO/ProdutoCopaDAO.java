package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.bo.ProdutoCopa;

public class ProdutoCopaDAO implements InterfaceDAO<ProdutoCopa> {

    @Override
    public void Create(ProdutoCopa objeto) {

        String sqlInstrucao = "INSERT INTO produto_copa("
                + " decricao, "
                + " valor, "
                + " obs, "
                + " status) "
                + " VALUES (?, ?, ?, ?)";

        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {

            if (conexao != null) {
                pstm = conexao.prepareStatement(sqlInstrucao);

                pstm.setString(1, objeto.getDescricao());
                pstm.setFloat(2, objeto.getValor());
                pstm.setString(3, objeto.getObs());
                pstm.setString(4, String.valueOf(objeto.getStatus()));

                pstm.execute();

                System.out.println(" Produto cadastrado com sucesso!");
            } else {
                System.err.println("ERRO");
            }

        } catch (SQLException ex) {
            System.err.println("ERRO ao cadastrar produto:");
            System.err.println("   Mensagem: " + ex.getMessage());
            ex.printStackTrace();

        } finally {

            if (pstm != null) {
                try {
                    pstm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public ProdutoCopa Retrieve(int id) {
        String sqlInstrucao = "SELECT "
                + " id, "
                + " decricao, "
                + " valor, "
                + " obs, "
                + " status, "
                + " copa_quarto_id "
                + " FROM produto_copa "
                + " WHERE id = ?";

        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        ProdutoCopa produto = new ProdutoCopa();

        try {
            if (conexao != null) {
                pstm = conexao.prepareStatement(sqlInstrucao);
                pstm.setInt(1, id);
                rst = pstm.executeQuery();

                while (rst.next()) {
                    produto.setId(rst.getInt("id"));
                    produto.setDescricao(rst.getString("decricao"));
                    produto.setValor(rst.getFloat("valor"));
                    produto.setObs(rst.getString("obs"));
                    produto.setStatus(rst.getString("status").charAt(0));
                }
            }
        } catch (SQLException ex) {
            System.err.println("❌ Erro ao buscar produto ID " + id);
            ex.printStackTrace();
        } finally {

            if (rst != null) {
                try {
                    rst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstm != null) {
                try {
                    pstm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return produto;
    }

    @Override
    public List<ProdutoCopa> Retrieve(String atributo, String valor) {
        if (atributo.equalsIgnoreCase("descricao")) {
            atributo = "decricao";
        }

        String sqlInstrucao = "SELECT "
                + " id, "
                + " decricao, "
                + " valor, "
                + " obs, "
                + " status, "
                + " copa_quarto_id "
                + " FROM produto_copa "
                + " WHERE " + atributo + " LIKE ?";

        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<ProdutoCopa> listaProdutos = new ArrayList<>();

        try {
            if (conexao != null) {
                pstm = conexao.prepareStatement(sqlInstrucao);
                pstm.setString(1, "%" + valor + "%");
                rst = pstm.executeQuery();

                while (rst.next()) {
                    ProdutoCopa produto = new ProdutoCopa();
                    produto.setId(rst.getInt("id"));
                    produto.setDescricao(rst.getString("decricao"));
                    produto.setValor(rst.getFloat("valor"));
                    produto.setObs(rst.getString("obs"));
                    produto.setStatus(rst.getString("status").charAt(0));
                    listaProdutos.add(produto);
                }
            }
        } catch (SQLException ex) {
            System.err.println("❌ Erro ao buscar produtos");
            ex.printStackTrace();
        } finally {

            if (rst != null) {
                try {
                    rst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstm != null) {
                try {
                    pstm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return listaProdutos;
    }

    public List<ProdutoCopa> RetrieveAll() {
        String sqlInstrucao = "SELECT "
                + " id, "
                + " decricao, "
                + " valor, "
                + " obs, "
                + " status, "
                + " copa_quarto_id "
                + " FROM produto_copa "
                + " WHERE status = 'A' "
                + " ORDER BY decricao";

        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<ProdutoCopa> listaProdutos = new ArrayList<>();

        try {
            if (conexao != null) {
                pstm = conexao.prepareStatement(sqlInstrucao);
                rst = pstm.executeQuery();

                while (rst.next()) {
                    ProdutoCopa produto = new ProdutoCopa();
                    produto.setId(rst.getInt("id"));
                    produto.setDescricao(rst.getString("decricao"));
                    produto.setValor(rst.getFloat("valor"));
                    produto.setObs(rst.getString("obs"));
                    produto.setStatus(rst.getString("status").charAt(0));
                    listaProdutos.add(produto);
                }
            }
        } catch (SQLException ex) {
            System.err.println("❌ Erro ao buscar todos os produtos");
            ex.printStackTrace();
        } finally {

            if (rst != null) {
                try {
                    rst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstm != null) {
                try {
                    pstm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return listaProdutos;
    }

    @Override
    public void Update(ProdutoCopa objeto) {
        String sqlInstrucao = "UPDATE produto_copa SET "
                + " decricao = ?, "
                + " valor = ?, "
                + " status = ?, "
                + " copa_quarto_id = ? "
                + " WHERE id = ?";

        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement pstm = null;

        try {
            if (conexao != null) {
                pstm = conexao.prepareStatement(sqlInstrucao);

                pstm.setString(1, objeto.getDescricao());
                pstm.setFloat(2, objeto.getValor());
                pstm.setString(3, objeto.getObs());
                pstm.setString(4, String.valueOf(objeto.getStatus()));
                pstm.setNull(5, java.sql.Types.INTEGER);
                pstm.setInt(6, objeto.getId());

                pstm.execute();

                System.out.println("✅ Produto atualizado com sucesso!");
            }
        } catch (SQLException ex) {
            System.err.println("❌ Erro ao atualizar produto");
            ex.printStackTrace();
        } finally {

            if (pstm != null) {
                try {
                    pstm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void Delete(ProdutoCopa objeto) {

    }
}
