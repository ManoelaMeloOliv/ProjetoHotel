package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.bo.Servico;
import view.TelaServico;

public class ControllerServico implements ActionListener {

    private TelaServico telaServico;

    public ControllerServico(TelaServico telaServico) {
        this.telaServico = telaServico;

        this.telaServico.getjButtonNovo().addActionListener(this);
        this.telaServico.getjButtonCancelar().addActionListener(this);
        this.telaServico.getjButtonGravar().addActionListener(this);
        this.telaServico.getjButtonFiltrar().addActionListener(this);
        this.telaServico.getjButtonBuscarTodos().addActionListener(this);
        this.telaServico.getjButtonSair().addActionListener(this);

        this.telaServico.getjTableDados().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    carregarServicoSelecionado();
                }
            }
        });

        utilities.Utilities.ativaDesativa(this.telaServico.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaServico.getjPanelDados(), false);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaServico.getjButtonNovo()) {
            novoServico();
        } else if (evento.getSource() == this.telaServico.getjButtonCancelar()) {
            cancelar();
        } else if (evento.getSource() == this.telaServico.getjButtonGravar()) {
            gravarServico();
        } else if (evento.getSource() == this.telaServico.getjButtonFiltrar()) {
            filtrarServicos();
        } else if (evento.getSource() == this.telaServico.getjButtonBuscarTodos()) {
            buscarTodosServicos();
        } else if (evento.getSource() == this.telaServico.getjButtonSair()) {
            javax.swing.SwingUtilities.getWindowAncestor(this.telaServico).dispose();
        }
    }

    private void novoServico() {
        utilities.Utilities.ativaDesativa(this.telaServico.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaServico.getjPanelDados(), true);
        this.telaServico.getjTextFieldId().setEnabled(false);
        this.telaServico.getjTextFieldDescricao().requestFocus();
    }

    private void cancelar() {
        utilities.Utilities.ativaDesativa(this.telaServico.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaServico.getjPanelDados(), false);
    }

    private void gravarServico() {
        if (this.telaServico.getjTextFieldDescricao().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Descrição é obrigatório!");
            this.telaServico.getjTextFieldDescricao().requestFocus();
            return;
        }

        Servico servico = new Servico();
        servico.setDescricao(this.telaServico.getjTextFieldDescricao().getText());
        servico.setObs(this.telaServico.getjTextAreaObs().getText());

        if (this.telaServico.getjTextFieldId().getText().trim().isEmpty()) {
            servico.setStatus('A');
            service.ServicoService.Criar(servico);
            JOptionPane.showMessageDialog(null, "Serviço cadastrado com sucesso!");
        } else {
            servico.setId(Integer.parseInt(this.telaServico.getjTextFieldId().getText()));
            servico.setStatus('A');
            service.ServicoService.Atualizar(servico);
            JOptionPane.showMessageDialog(null, "Serviço atualizado com sucesso!");
        }

        utilities.Utilities.ativaDesativa(this.telaServico.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaServico.getjPanelDados(), false);
    }

    private void buscarTodosServicos() {
        DefaultTableModel tabela = (DefaultTableModel) this.telaServico.getjTableDados().getModel();
        tabela.setRowCount(0);
        
        List<Servico> listaServicos = service.ServicoService.Carregar("status", "A");
        
        for (Servico servicoAtual : listaServicos) {
            tabela.addRow(new Object[]{
                servicoAtual.getId(),
                servicoAtual.getDescricao(),
                servicoAtual.getStatus()
            });
        }
        
        if (listaServicos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum serviço ativo cadastrado!");
        } else {
            JOptionPane.showMessageDialog(null, "Total de " + listaServicos.size() + " serviço(s) encontrado(s)!");
        }
    }

    private void filtrarServicos() {
        if (this.telaServico.getjTFFiltro().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite algo para filtrar!");
            this.telaServico.getjTFFiltro().requestFocus();
            return;
        }

        DefaultTableModel tabela = (DefaultTableModel) this.telaServico.getjTableDados().getModel();
        tabela.setRowCount(0);

        if (this.telaServico.getjCBFiltro().getSelectedIndex() == 0) {
            try {
                int id = Integer.parseInt(this.telaServico.getjTFFiltro().getText());
                Servico servico = service.ServicoService.Carregar(id);

                if (servico.getId() != 0) {
                    tabela.addRow(new Object[]{
                        servico.getId(),
                        servico.getDescricao(),
                        servico.getStatus()
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "Serviço não encontrado!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Digite um ID válido (número)!");
            }

        } else if (this.telaServico.getjCBFiltro().getSelectedIndex() == 1) {
            List<Servico> listaServicos = service.ServicoService.Carregar("descricao", this.telaServico.getjTFFiltro().getText());

            for (Servico servicoAtual : listaServicos) {
                tabela.addRow(new Object[]{
                    servicoAtual.getId(),
                    servicoAtual.getDescricao(),
                    servicoAtual.getStatus()
                });
            }

            if (listaServicos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum serviço encontrado com essa descrição!");
            }
        }
    }

    private void carregarServicoSelecionado() {
        if (this.telaServico.getjTableDados().getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um serviço na tabela!");
            return;
        }

        int codigo = (int) this.telaServico.getjTableDados().getValueAt(
            this.telaServico.getjTableDados().getSelectedRow(), 0
        );

        utilities.Utilities.ativaDesativa(this.telaServico.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaServico.getjPanelDados(), true);

        this.telaServico.getjTextFieldId().setText(codigo + "");
        this.telaServico.getjTextFieldId().setEnabled(false);

        Servico servico = service.ServicoService.Carregar(codigo);

        this.telaServico.getjTextFieldDescricao().setText(servico.getDescricao());
        this.telaServico.getjTextAreaObs().setText(servico.getObs());

        this.telaServico.getjTextFieldDescricao().requestFocus();
    }
}