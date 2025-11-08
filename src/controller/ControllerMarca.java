package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.bo.Marca;
import view.TelaMarca;

public class ControllerMarca implements ActionListener {

    private TelaMarca telaMarca;

    public ControllerMarca(TelaMarca telaMarca) {
        this.telaMarca = telaMarca;
        this.telaMarca.getjButtonNovo().addActionListener(this);
        this.telaMarca.getjButtonCancelar().addActionListener(this);
        this.telaMarca.getjButtonGravar().addActionListener(this);
        this.telaMarca.getjButtonFiltrar().addActionListener(this);
        this.telaMarca.getjButtonBuscarTodos().addActionListener(this);
        this.telaMarca.getjButtonSair().addActionListener(this);

        this.telaMarca.getjTableDados().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    carregarMarcaSelecionada();
                }
            }
        });

        this.telaMarca.getjTFFiltro().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    filtrarMarcas();
                }
            }
        });

        utilities.Utilities.ativaDesativa(this.telaMarca.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaMarca.getjPanelDados(), false);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaMarca.getjButtonNovo()) {
            novaMarca();
        } else if (evento.getSource() == this.telaMarca.getjButtonCancelar()) {
            cancelar();
        } else if (evento.getSource() == this.telaMarca.getjButtonGravar()) {
            gravarMarca();
        } else if (evento.getSource() == this.telaMarca.getjButtonFiltrar()) {
            filtrarMarcas();
        } else if (evento.getSource() == this.telaMarca.getjButtonBuscarTodos()) {
            buscarTodasMarcas();
        } else if (evento.getSource() == this.telaMarca.getjButtonSair()) {
            javax.swing.SwingUtilities.getWindowAncestor(this.telaMarca).dispose();
        }
    }

    private void novaMarca() {
        utilities.Utilities.ativaDesativa(this.telaMarca.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaMarca.getjPanelDados(), true);
        this.telaMarca.getjTextFieldId().setEnabled(false);
        this.telaMarca.getjComboBoxStatus().setSelectedItem("Ativo");
        this.telaMarca.getjTextFieldDescricao().requestFocus();
    }

    private void cancelar() {
        utilities.Utilities.ativaDesativa(this.telaMarca.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaMarca.getjPanelDados(), false);
    }

    private void gravarMarca() {
        if (this.telaMarca.getjTextFieldDescricao().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Descrição é obrigatório!");
            this.telaMarca.getjTextFieldDescricao().requestFocus();
            return;
        }

        Marca marca = new Marca();
        String descricao = this.telaMarca.getjTextFieldDescricao().getText().trim();
        marca.setDescricao(descricao);

        String statusTexto = (String) this.telaMarca.getjComboBoxStatus().getSelectedItem();
        char status = statusTexto.equals("Ativo") ? 'A' : 'C';
        marca.setStatus(status);

        if (this.telaMarca.getjTextFieldId().getText().trim().isEmpty()) {
            service.MarcaService.Criar(marca);
            JOptionPane.showMessageDialog(null, "Marca cadastrada com sucesso!");
        } else {
            
            marca.setId(Integer.parseInt(this.telaMarca.getjTextFieldId().getText()));
            service.MarcaService.Atualizar(marca);
            JOptionPane.showMessageDialog(null, "Marca atualizada com sucesso!");
        }

        utilities.Utilities.ativaDesativa(this.telaMarca.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaMarca.getjPanelDados(), false);
    }

    private void buscarTodasMarcas() {
        DefaultTableModel tabela = (DefaultTableModel) this.telaMarca.getjTableDados().getModel();
        tabela.setRowCount(0);

        List<Marca> listaMarcas = service.MarcaService.Carregar("descricao", "");

        for (Marca marcaAtual : listaMarcas) {
            String statusTexto = marcaAtual.getStatus() == 'A' ? "Ativo" : "Cancelado";
            tabela.addRow(new Object[]{
                marcaAtual.getId(),
                marcaAtual.getDescricao(),
                statusTexto
            });
        }

        if (listaMarcas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma marca cadastrada!");
        } else {
            JOptionPane.showMessageDialog(null, "Total de " + listaMarcas.size() + " marca(s) encontrada(s)!");
        }
    }

    private void filtrarMarcas() {
        if (this.telaMarca.getjTFFiltro().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite algo para filtrar!");
            this.telaMarca.getjTFFiltro().requestFocus();
            return;
        }

        DefaultTableModel tabela = (DefaultTableModel) this.telaMarca.getjTableDados().getModel();
        tabela.setRowCount(0);

        if (this.telaMarca.getjCBFiltro().getSelectedIndex() == 0) {
            try {
                int id = Integer.parseInt(this.telaMarca.getjTFFiltro().getText());
                Marca marca = service.MarcaService.Carregar(id);

                if (marca.getId() != 0) {
                    String statusTexto = marca.getStatus() == 'A' ? "Ativo" : "Cancelado";
                    tabela.addRow(new Object[]{
                        marca.getId(),
                        marca.getDescricao(),
                        statusTexto
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "Marca não encontrada!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Digite um ID válido (número)!");
            }

        } else if (this.telaMarca.getjCBFiltro().getSelectedIndex() == 1) {
            List<Marca> listaMarcas = service.MarcaService.Carregar("descricao", this.telaMarca.getjTFFiltro().getText());

            for (Marca marcaAtual : listaMarcas) {
                String statusTexto = marcaAtual.getStatus() == 'A' ? "Ativo" : "Cancelado";
                tabela.addRow(new Object[]{
                    marcaAtual.getId(),
                    marcaAtual.getDescricao(),
                    statusTexto
                });
            }

            if (listaMarcas.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhuma marca encontrada com essa descrição!");
            }
        }
    }

    private void carregarMarcaSelecionada() {
        if (this.telaMarca.getjTableDados().getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Selecione uma marca na tabela!");
            return;
        }

        int codigo = (int) this.telaMarca.getjTableDados().getValueAt(
                this.telaMarca.getjTableDados().getSelectedRow(), 0
        );

        utilities.Utilities.ativaDesativa(this.telaMarca.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaMarca.getjPanelDados(), true);

        this.telaMarca.getjTextFieldId().setText(codigo + "");
        this.telaMarca.getjTextFieldId().setEnabled(false);

        Marca marca = service.MarcaService.Carregar(codigo);

        this.telaMarca.getjTextFieldDescricao().setText(marca.getDescricao());

        if (marca.getStatus() == 'A') {
            this.telaMarca.getjComboBoxStatus().setSelectedItem("Ativo");
        } else {
            this.telaMarca.getjComboBoxStatus().setSelectedItem("Cancelado");
        }

        this.telaMarca.getjTextFieldDescricao().requestFocus();
    }
}
