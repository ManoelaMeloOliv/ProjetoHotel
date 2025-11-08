package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.bo.ProdutoCopa;
import view.TelaProdutoCopa;

public class ControllerProdutoCopa implements ActionListener {

    private TelaProdutoCopa telaProdutoCopa;
    private DecimalFormat df = new DecimalFormat("#,##0.00");

    public ControllerProdutoCopa(TelaProdutoCopa telaProdutoCopa) {
        this.telaProdutoCopa = telaProdutoCopa;

        this.telaProdutoCopa.getjButtonNovo().addActionListener(this);
        this.telaProdutoCopa.getjButtonCancelar().addActionListener(this);
        this.telaProdutoCopa.getjButtonGravar().addActionListener(this);
        this.telaProdutoCopa.getjButtonFiltrar().addActionListener(this);
        this.telaProdutoCopa.getjButtonBuscarTodos().addActionListener(this);
        this.telaProdutoCopa.getjButtonSair().addActionListener(this);

        this.telaProdutoCopa.getjTableDados().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    carregarProdutoSelecionado();
                }
            }
        });

        this.telaProdutoCopa.getjTFFiltro().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    filtrarProdutos();
                }
            }
        });

        utilities.Utilities.ativaDesativa(this.telaProdutoCopa.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaProdutoCopa.getjPanelDados(), false);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaProdutoCopa.getjButtonNovo()) {
            novoProduto();
        } else if (evento.getSource() == this.telaProdutoCopa.getjButtonCancelar()) {
            cancelar();
        } else if (evento.getSource() == this.telaProdutoCopa.getjButtonGravar()) {
            gravarProduto();
        } else if (evento.getSource() == this.telaProdutoCopa.getjButtonFiltrar()) {
            filtrarProdutos();
        } else if (evento.getSource() == this.telaProdutoCopa.getjButtonBuscarTodos()) {
            buscarTodosProdutos();
        } else if (evento.getSource() == this.telaProdutoCopa.getjButtonSair()) {
            javax.swing.SwingUtilities.getWindowAncestor(this.telaProdutoCopa).dispose();
        }
    }

    private void novoProduto() {
        utilities.Utilities.ativaDesativa(this.telaProdutoCopa.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaProdutoCopa.getjPanelDados(), true);
        this.telaProdutoCopa.getjTextFieldId().setEnabled(false);
        this.telaProdutoCopa.getjTextFieldDescricao().requestFocus();
    }

    private void cancelar() {
        utilities.Utilities.ativaDesativa(this.telaProdutoCopa.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaProdutoCopa.getjPanelDados(), false);
    }

    private void gravarProduto() {
        if (this.telaProdutoCopa.getjTextFieldDescricao().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Descrição é obrigatório!");
            this.telaProdutoCopa.getjTextFieldDescricao().requestFocus();
            return;
        }

        if (this.telaProdutoCopa.getjFormattedTextFieldValor().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Valor é obrigatório!");
            this.telaProdutoCopa.getjFormattedTextFieldValor().requestFocus();
            return;
        }

        ProdutoCopa produto = new ProdutoCopa();
        produto.setDescricao(this.telaProdutoCopa.getjTextFieldDescricao().getText());
        produto.setObs(this.telaProdutoCopa.getjTextAreaObs().getText());

        try {
            String valorTexto = this.telaProdutoCopa.getjFormattedTextFieldValor().getText();
            valorTexto = valorTexto.replace(".", "").replace(",", ".");
            float valor = Float.parseFloat(valorTexto);
            produto.setValor(valor);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valor inválido! Use formato: 00,00");
            this.telaProdutoCopa.getjFormattedTextFieldValor().requestFocus();
            return;
        }

        if (this.telaProdutoCopa.getjTextFieldId().getText().trim().isEmpty()) {
       
            produto.setStatus('A');
            service.ProdutoCopaService.Criar(produto);
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
        } else {
          
            produto.setId(Integer.parseInt(this.telaProdutoCopa.getjTextFieldId().getText()));
            service.ProdutoCopaService.Atualizar(produto);
            JOptionPane.showMessageDialog(null, "Produto atualizado com sucesso!");
        }

        
        utilities.Utilities.ativaDesativa(this.telaProdutoCopa.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaProdutoCopa.getjPanelDados(), false);
    }

    private void buscarTodosProdutos() {
        DefaultTableModel tabela = (DefaultTableModel) this.telaProdutoCopa.getjTableDados().getModel();
        tabela.setRowCount(0);

        List<ProdutoCopa> listaProdutos = service.ProdutoCopaService.Carregar("descricao", "");

        for (ProdutoCopa produtoAtual : listaProdutos) {
            tabela.addRow(new Object[]{
                produtoAtual.getId(),
                produtoAtual.getDescricao(),
                "R$ " + df.format(produtoAtual.getValor()),
                produtoAtual.getStatus()
            });
        }

        if (listaProdutos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum produto cadastrado!");
        } else {
            JOptionPane.showMessageDialog(null, "Total de " + listaProdutos.size() + " produto(s) encontrado(s)!");
        }
    }

    private void filtrarProdutos() {
        if (this.telaProdutoCopa.getjTFFiltro().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite algo para filtrar!");
            this.telaProdutoCopa.getjTFFiltro().requestFocus();
            return;
        }

        DefaultTableModel tabela = (DefaultTableModel) this.telaProdutoCopa.getjTableDados().getModel();
        tabela.setRowCount(0);

        if (this.telaProdutoCopa.getjCBFiltro().getSelectedIndex() == 0) {
       
            try {
                int id = Integer.parseInt(this.telaProdutoCopa.getjTFFiltro().getText());
                ProdutoCopa produto = service.ProdutoCopaService.Carregar(id);

                if (produto.getId() != 0) {
                    tabela.addRow(new Object[]{
                        produto.getId(),
                        produto.getDescricao(),
                        "R$ " + df.format(produto.getValor()),
                        produto.getStatus()
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "Produto não encontrado!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Digite um ID válido (número)!");
            }
            
        } else if (this.telaProdutoCopa.getjCBFiltro().getSelectedIndex() == 1) {
          
            List<ProdutoCopa> listaProdutos = service.ProdutoCopaService.Carregar("descricao", this.telaProdutoCopa.getjTFFiltro().getText());

            for (ProdutoCopa produtoAtual : listaProdutos) {
                tabela.addRow(new Object[]{
                    produtoAtual.getId(),
                    produtoAtual.getDescricao(),
                    "R$ " + df.format(produtoAtual.getValor()),
                    produtoAtual.getStatus()
                });
            }

            if (listaProdutos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum produto encontrado com essa descrição!");
            }
        }
    }

    private void carregarProdutoSelecionado() {
        if (this.telaProdutoCopa.getjTableDados().getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um produto na tabela!");
            return;
        }

        int codigo = (int) this.telaProdutoCopa.getjTableDados().getValueAt(
            this.telaProdutoCopa.getjTableDados().getSelectedRow(), 0
        );

        utilities.Utilities.ativaDesativa(this.telaProdutoCopa.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaProdutoCopa.getjPanelDados(), true);

        this.telaProdutoCopa.getjTextFieldId().setText(codigo + "");
        this.telaProdutoCopa.getjTextFieldId().setEnabled(false);

        ProdutoCopa produto = service.ProdutoCopaService.Carregar(codigo);

        this.telaProdutoCopa.getjTextFieldDescricao().setText(produto.getDescricao());
        this.telaProdutoCopa.getjFormattedTextFieldValor().setValue(produto.getValor());
        this.telaProdutoCopa.getjTextAreaObs().setText(produto.getObs());

        this.telaProdutoCopa.getjTextFieldDescricao().requestFocus();
    }
}