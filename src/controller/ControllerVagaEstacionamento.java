package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.bo.VagaEstacionamento;
import view.TelaVagaEstacionamento;


public class ControllerVagaEstacionamento implements ActionListener {

    private TelaVagaEstacionamento telaVaga;

    public ControllerVagaEstacionamento(TelaVagaEstacionamento telaVaga) {
        this.telaVaga = telaVaga;

        this.telaVaga.getjButtonNovo().addActionListener(this);
        this.telaVaga.getjButtonCancelar().addActionListener(this);
        this.telaVaga.getjButtonGravar().addActionListener(this);
        this.telaVaga.getjButtonFiltrar().addActionListener(this);
        this.telaVaga.getjButtonBuscarTodos().addActionListener(this);
        this.telaVaga.getjButtonSair().addActionListener(this);

        this.telaVaga.getjTableDados().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    carregarVagaSelecionada();
                }
            }
        });

        utilities.Utilities.ativaDesativa(this.telaVaga.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaVaga.getjPanelDados(), false);
    }

   @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaVaga.getjButtonNovo()) {
            novaVaga();
        } else if (evento.getSource() == this.telaVaga.getjButtonCancelar()) {
            cancelar();
        } else if (evento.getSource() == this.telaVaga.getjButtonGravar()) {
            gravarVaga();
        } else if (evento.getSource() == this.telaVaga.getjButtonFiltrar()) {
            filtrarVagas();
        } else if (evento.getSource() == this.telaVaga.getjButtonBuscarTodos()) {
            buscarTodasVagas();
            
        } else if (evento.getSource() == this.telaVaga.getjButtonSair()) {
         
            javax.swing.SwingUtilities.getWindowAncestor(this.telaVaga).dispose();
        }
    }


    private void novaVaga() {
        utilities.Utilities.ativaDesativa(this.telaVaga.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaVaga.getjPanelDados(), true);
        
        this.telaVaga.getjTextFieldId().setEnabled(false);
        this.telaVaga.getjComboBoxStatus().setSelectedIndex(0);
        
        this.telaVaga.getjTextFieldDescricao().requestFocus();
    }

    private void cancelar() {
        utilities.Utilities.ativaDesativa(this.telaVaga.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaVaga.getjPanelDados(), false);
    }

    private void gravarVaga() {
        
        if (this.telaVaga.getjTextFieldDescricao().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Descrição é obrigatório!");
            this.telaVaga.getjTextFieldDescricao().requestFocus();
            return;
        }

        if (this.telaVaga.getjTextFieldMetragem().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Metragem é obrigatório!");
            this.telaVaga.getjTextFieldMetragem().requestFocus();
            return;
        }

       
        float metragem;
        try {
            metragem = Float.parseFloat(this.telaVaga.getjTextFieldMetragem().getText().trim());
            if (metragem <= 0) {
                JOptionPane.showMessageDialog(null, "A metragem deve ser maior que zero!");
                this.telaVaga.getjTextFieldMetragem().requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Digite uma metragem válida (número)!");
            this.telaVaga.getjTextFieldMetragem().requestFocus();
            return;
        }

     
        VagaEstacionamento vaga = new VagaEstacionamento();
        
        vaga.setDescricao(this.telaVaga.getjTextFieldDescricao().getText().trim());
        vaga.setObs(this.telaVaga.getjTextFieldObs().getText().trim());
        vaga.setMetragemVaga(metragem);
        
        char status = this.telaVaga.getjComboBoxStatus().getSelectedIndex() == 0 ? 'A' : 'C';
        vaga.setStatus(status);

        if (this.telaVaga.getjTextFieldId().getText().trim().isEmpty()) {
          
            service.VagaEstacionamentoService.Criar(vaga);
            JOptionPane.showMessageDialog(null, "Vaga cadastrada com sucesso!");
            
        } else {
           
            vaga.setId(Integer.parseInt(this.telaVaga.getjTextFieldId().getText()));
            service.VagaEstacionamentoService.Atualizar(vaga);
            JOptionPane.showMessageDialog(null, "Vaga atualizada com sucesso!");
        }

        utilities.Utilities.ativaDesativa(this.telaVaga.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaVaga.getjPanelDados(), false);
        
        if (!this.telaVaga.getjTFFiltro().getText().trim().isEmpty()) {
            filtrarVagas();
        }
    }

    private void filtrarVagas() {
        if (this.telaVaga.getjTFFiltro().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite algo para filtrar!");
            return;
        }

        DefaultTableModel tabela = (DefaultTableModel) this.telaVaga.getjTableDados().getModel();
        tabela.setRowCount(0);

        if (this.telaVaga.getjCBFiltro().getSelectedIndex() == 0) {
               try {
                int id = Integer.parseInt(this.telaVaga.getjTFFiltro().getText());
                VagaEstacionamento vaga = service.VagaEstacionamentoService.Carregar(id);
                
                if (vaga.getId() != 0) {
                    tabela.addRow(new Object[]{
                        vaga.getId(),
                        vaga.getDescricao(),
                        String.format("%.2f m²", vaga.getMetragemVaga()),
                        vaga.getStatus()
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "Vaga não encontrada!");
                }
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Digite um ID válido (número)!");
            }
            
        } else if (this.telaVaga.getjCBFiltro().getSelectedIndex() == 1) {
           
            List<VagaEstacionamento> listaVagas = service.VagaEstacionamentoService.Carregar(
                "descricao", 
                this.telaVaga.getjTFFiltro().getText()
            );
            
            for (VagaEstacionamento vagaAtual : listaVagas) {
                tabela.addRow(new Object[]{
                    vagaAtual.getId(),
                    vagaAtual.getDescricao(),
                    String.format("%.2f m²", vagaAtual.getMetragemVaga()),
                    vagaAtual.getStatus()
                });
            }
            
            if (listaVagas.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhuma vaga encontrada com essa descrição!");
            }
        }
    }

    private void buscarTodasVagas() {
        DefaultTableModel tabela = (DefaultTableModel) this.telaVaga.getjTableDados().getModel();
        tabela.setRowCount(0);

        List<VagaEstacionamento> listaVagas = service.VagaEstacionamentoService.Carregar("status", "A");

        for (VagaEstacionamento vagaAtual : listaVagas) {
            tabela.addRow(new Object[]{
                vagaAtual.getId(),
                vagaAtual.getDescricao(),
                String.format("%.2f m²", vagaAtual.getMetragemVaga()),
                vagaAtual.getStatus()
            });
        }

        if (listaVagas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma vaga ativa cadastrada!");
        } else {
            JOptionPane.showMessageDialog(null, "Total de vagas ativas: " + listaVagas.size());
        }
    }

    private void carregarVagaSelecionada() {
        if (this.telaVaga.getjTableDados().getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Selecione uma vaga na tabela!");
            return;
        }

        int codigo = (int) this.telaVaga.getjTableDados().getValueAt(
            this.telaVaga.getjTableDados().getSelectedRow(),
            0
        );

        utilities.Utilities.ativaDesativa(this.telaVaga.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaVaga.getjPanelDados(), true);

        this.telaVaga.getjTextFieldId().setText(codigo + "");
        this.telaVaga.getjTextFieldId().setEnabled(false);

        VagaEstacionamento vaga = service.VagaEstacionamentoService.Carregar(codigo);

        this.telaVaga.getjTextFieldDescricao().setText(vaga.getDescricao());
        this.telaVaga.getjTextFieldObs().setText(vaga.getObs());
        this.telaVaga.getjTextFieldMetragem().setText(String.valueOf(vaga.getMetragemVaga()));
        
        this.telaVaga.getjComboBoxStatus().setSelectedIndex(vaga.getStatus() == 'A' ? 0 : 1);

        this.telaVaga.getjTextFieldDescricao().requestFocus();
    }
}