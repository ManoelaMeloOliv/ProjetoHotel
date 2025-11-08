package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.bo.Quarto;
import view.TelaQuarto;


public class ControllerQuarto implements ActionListener {

    private TelaQuarto telaQuarto;

    public ControllerQuarto(TelaQuarto telaQuarto) {
        this.telaQuarto = telaQuarto;

        this.telaQuarto.getjButtonNovo().addActionListener(this);
        this.telaQuarto.getjButtonCancelar().addActionListener(this);
        this.telaQuarto.getjButtonGravar().addActionListener(this);
        this.telaQuarto.getjButtonFiltrar().addActionListener(this);
        this.telaQuarto.getjButtonBuscarTodos().addActionListener(this);
        this.telaQuarto.getjButtonSair().addActionListener(this);

        this.telaQuarto.getjTableDados().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    carregarQuartoSelecionado();
                }
            }
        });

        utilities.Utilities.ativaDesativa(this.telaQuarto.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaQuarto.getjPanelDados(), false);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaQuarto.getjButtonNovo()) {
            novoQuarto();
        } else if (evento.getSource() == this.telaQuarto.getjButtonCancelar()) {
            cancelar();
        } else if (evento.getSource() == this.telaQuarto.getjButtonGravar()) {
            gravarQuarto();
        } else if (evento.getSource() == this.telaQuarto.getjButtonFiltrar()) {
            filtrarQuartos();
        } else if (evento.getSource() == this.telaQuarto.getjButtonBuscarTodos()) {
            buscarTodosQuartos();
            
        } else if (evento.getSource() == this.telaQuarto.getjButtonSair()) {
            javax.swing.SwingUtilities.getWindowAncestor(this.telaQuarto).dispose();
        }
    }

    private void novoQuarto() {
        utilities.Utilities.ativaDesativa(this.telaQuarto.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaQuarto.getjPanelDados(), true);
        this.telaQuarto.getjTextFieldId().setEnabled(false);
        this.telaQuarto.getjTextFieldIdentificacao().requestFocus();
    }

    private void cancelar() {
        utilities.Utilities.ativaDesativa(this.telaQuarto.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaQuarto.getjPanelDados(), false);
    }

    private void gravarQuarto() {
        if (this.telaQuarto.getjTextFieldIdentificacao().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Identificação é obrigatório!");
            this.telaQuarto.getjTextFieldIdentificacao().requestFocus();
            return;
        }

        if (this.telaQuarto.getjTextFieldDescricao().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Descrição é obrigatório!");
            this.telaQuarto.getjTextFieldDescricao().requestFocus();
            return;
        }

        if (this.telaQuarto.getjTextFieldCapacidade().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Capacidade é obrigatório!");
            this.telaQuarto.getjTextFieldCapacidade().requestFocus();
            return;
        }

        if (this.telaQuarto.getjTextFieldAndar().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Andar é obrigatório!");
            this.telaQuarto.getjTextFieldAndar().requestFocus();
            return;
        }

        try {
            Integer.parseInt(this.telaQuarto.getjTextFieldCapacidade().getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Capacidade deve ser um número inteiro!");
            this.telaQuarto.getjTextFieldCapacidade().requestFocus();
            return;
        }

        try {
            Integer.parseInt(this.telaQuarto.getjTextFieldAndar().getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Andar deve ser um número inteiro!");
            this.telaQuarto.getjTextFieldAndar().requestFocus();
            return;
        }

        Quarto quarto = new Quarto();
        quarto.setDescricao(this.telaQuarto.getjTextFieldDescricao().getText());
        quarto.setIndentificacao(this.telaQuarto.getjTextFieldIdentificacao().getText());
        quarto.setCapacidadeDeHospede(Integer.parseInt(this.telaQuarto.getjTextFieldCapacidade().getText().trim()));
        quarto.setAndar(Integer.parseInt(this.telaQuarto.getjTextFieldAndar().getText().trim()));
        
        if (!this.telaQuarto.getjTextFieldMetragem().getText().trim().isEmpty()) {
            try {
                quarto.setMetragem(Float.parseFloat(this.telaQuarto.getjTextFieldMetragem().getText().trim()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Metragem deve ser um número!");
                this.telaQuarto.getjTextFieldMetragem().requestFocus();
                return;
            }
        } else {
            quarto.setMetragem(0.0f);
        }
        
        quarto.setFlagAnimais(this.telaQuarto.getjCheckBoxAnimais().isSelected());
        quarto.setObs(this.telaQuarto.getjTextAreaObs().getText());

        if (this.telaQuarto.getjTextFieldId().getText().trim().isEmpty()) {
            
            quarto.setStatus('A');
            service.QuartoService.Criar(quarto);
            JOptionPane.showMessageDialog(null, "Quarto cadastrado com sucesso!");
        } else {
            
            quarto.setId(Integer.parseInt(this.telaQuarto.getjTextFieldId().getText()));
            quarto.setStatus('A');
            service.QuartoService.Atualizar(quarto);
            JOptionPane.showMessageDialog(null, "Quarto atualizado com sucesso!");
        }

       
        utilities.Utilities.ativaDesativa(this.telaQuarto.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaQuarto.getjPanelDados(), false);
    }

    private void buscarTodosQuartos() {
        DefaultTableModel tabela = (DefaultTableModel) this.telaQuarto.getjTableDados().getModel();
        tabela.setRowCount(0);
        
       
        List<Quarto> listaQuartos = service.QuartoService.Carregar("status", "A");
        
        for (Quarto quartoAtual : listaQuartos) {
            tabela.addRow(new Object[]{
                quartoAtual.getId(),
                quartoAtual.getIndentificacao(),
                quartoAtual.getDescricao(),
                quartoAtual.getAndar(),
                quartoAtual.getStatus()
            });
        }
        
        if (listaQuartos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum quarto ativo cadastrado!");
        } else {
            JOptionPane.showMessageDialog(null, "Total de " + listaQuartos.size() + " quarto(s) encontrado(s)!");
        }
    }

    private void filtrarQuartos() {
        if (this.telaQuarto.getjTFFiltro().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite algo para filtrar!");
            this.telaQuarto.getjTFFiltro().requestFocus();
            return;
        }

        DefaultTableModel tabela = (DefaultTableModel) this.telaQuarto.getjTableDados().getModel();
        tabela.setRowCount(0);

        if (this.telaQuarto.getjCBFiltro().getSelectedIndex() == 0) {
        
            try {
                int id = Integer.parseInt(this.telaQuarto.getjTFFiltro().getText());
                Quarto quarto = service.QuartoService.Carregar(id);
                
                if (quarto.getId() != 0) {
                    tabela.addRow(new Object[]{
                        quarto.getId(),
                        quarto.getIndentificacao(),
                        quarto.getDescricao(),
                        quarto.getAndar(),
                        quarto.getStatus()
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "Quarto não encontrado!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Digite um ID válido (número)!");
            }
            
        } else if (this.telaQuarto.getjCBFiltro().getSelectedIndex() == 1) {

            List<Quarto> listaQuartos = service.QuartoService.Carregar("descricao", this.telaQuarto.getjTFFiltro().getText());
            
            for (Quarto quartoAtual : listaQuartos) {
                tabela.addRow(new Object[]{
                    quartoAtual.getId(),
                    quartoAtual.getIndentificacao(),
                    quartoAtual.getDescricao(),
                    quartoAtual.getAndar(),
                    quartoAtual.getStatus()
                });
            }
            
            if (listaQuartos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum quarto encontrado com essa descrição!");
            }
            
        } else if (this.telaQuarto.getjCBFiltro().getSelectedIndex() == 2) {
       
            List<Quarto> listaQuartos = service.QuartoService.Carregar("identificacao", this.telaQuarto.getjTFFiltro().getText());
            
            for (Quarto quartoAtual : listaQuartos) {
                tabela.addRow(new Object[]{
                    quartoAtual.getId(),
                    quartoAtual.getIndentificacao(),
                    quartoAtual.getDescricao(),
                    quartoAtual.getAndar(),
                    quartoAtual.getStatus()
                });
            }
            
            if (listaQuartos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum quarto encontrado com essa identificação!");
            }
        }
    }

    private void carregarQuartoSelecionado() {
        if (this.telaQuarto.getjTableDados().getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um quarto na tabela!");
            return;
        }

        int codigo = (int) this.telaQuarto.getjTableDados().getValueAt(
            this.telaQuarto.getjTableDados().getSelectedRow(), 0
        );

        utilities.Utilities.ativaDesativa(this.telaQuarto.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaQuarto.getjPanelDados(), true);

        this.telaQuarto.getjTextFieldId().setText(codigo + "");
        this.telaQuarto.getjTextFieldId().setEnabled(false);

        Quarto quarto = service.QuartoService.Carregar(codigo);

        this.telaQuarto.getjTextFieldDescricao().setText(quarto.getDescricao());
        this.telaQuarto.getjTextFieldIdentificacao().setText(quarto.getIndentificacao());
        this.telaQuarto.getjTextFieldCapacidade().setText(String.valueOf(quarto.getCapacidadeDeHospede()));
        this.telaQuarto.getjTextFieldMetragem().setText(String.valueOf(quarto.getMetragem()));
        this.telaQuarto.getjTextFieldAndar().setText(String.valueOf(quarto.getAndar()));
        this.telaQuarto.getjCheckBoxAnimais().setSelected(quarto.isFlagAnimais());
        this.telaQuarto.getjTextAreaObs().setText(quarto.getObs());

        this.telaQuarto.getjTextFieldIdentificacao().requestFocus();
    }
}