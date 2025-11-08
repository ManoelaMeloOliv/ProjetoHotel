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
import model.bo.Hospede;
import view.TelaHospede;


public class ControllerHospede implements ActionListener {

    private TelaHospede telaHospede;

    public ControllerHospede(TelaHospede telaHospede) {
        this.telaHospede = telaHospede;

        this.telaHospede.getjButtonNovo().addActionListener(this);
        this.telaHospede.getjButtonCancelar().addActionListener(this);
        this.telaHospede.getjButtonGravar().addActionListener(this);
        this.telaHospede.getjButtonFiltrar().addActionListener(this);
        this.telaHospede.getjButtonBuscarTodos().addActionListener(this);
        this.telaHospede.getjButtonSair().addActionListener(this);

        this.telaHospede.getjTableDados().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    carregarHospedeSelecionado();
                }
            }
        });

        this.telaHospede.getjTFFiltro().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    filtrarHospedes();
                }
            }
        });

        utilities.Utilities.ativaDesativa(this.telaHospede.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaHospede.getjPanelDados(), false);
    }
               
    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaHospede.getjButtonNovo()) {
            novoHospede();
        } else if (evento.getSource() == this.telaHospede.getjButtonCancelar()) {
            cancelar();
        } else if (evento.getSource() == this.telaHospede.getjButtonGravar()) {
            gravarHospede();
        } else if (evento.getSource() == this.telaHospede.getjButtonFiltrar()) {
            filtrarHospedes();
        } else if (evento.getSource() == this.telaHospede.getjButtonBuscarTodos()) {
            buscarTodosHospedes();
        } else if (evento.getSource() == this.telaHospede.getjButtonSair()) {
            javax.swing.SwingUtilities.getWindowAncestor(this.telaHospede).dispose();
        }
    }

    private void novoHospede() {
        utilities.Utilities.ativaDesativa(this.telaHospede.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaHospede.getjPanelDados(), true);
        this.telaHospede.getjTextFieldId().setEnabled(false);
        this.telaHospede.getjTextFieldNomeFantasia().requestFocus();
    }

    private void cancelar() {
        utilities.Utilities.ativaDesativa(this.telaHospede.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaHospede.getjPanelDados(), false);
    }

    private void gravarHospede() {
        if (this.telaHospede.getjTextFieldNomeFantasia().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Nome é obrigatório!");
            this.telaHospede.getjTextFieldNomeFantasia().requestFocus();
            return;
        }

        Hospede hospede = new Hospede();
        hospede.setNome(this.telaHospede.getjTextFieldNomeFantasia().getText());
        hospede.setRazaoSocial(this.telaHospede.getjTextFieldRazaoSocial().getText());
        hospede.setCpf(this.telaHospede.getjFormattedTextFieldCpf().getText());
        hospede.setRg(this.telaHospede.getjTextFieldRg().getText());
        hospede.setCnpj(this.telaHospede.getjFormattedTextFieldCnpj().getText());
        hospede.setInscricaoEstdual(this.telaHospede.getjTextFieldInscricaoEstadual().getText());
        hospede.setContato(this.telaHospede.getjTextFieldContato().getText());
        hospede.setEmail(this.telaHospede.getjTextFieldEmail().getText());
        hospede.setFone1(this.telaHospede.getjFormattedTextFieldFone1().getText());
        hospede.setFone2(this.telaHospede.getjFormattedTextFieldFone2().getText());
        hospede.setCep(this.telaHospede.getjFormattedTextFieldCep().getText());
        hospede.setLogradouro(this.telaHospede.getjTextFieldLogradouro().getText());
        hospede.setBairro(this.telaHospede.getjTextFieldBairro().getText());
        hospede.setCidade(this.telaHospede.getjTextFieldCidade().getText());
        hospede.setComplemento(this.telaHospede.getjTextFieldComplemento().getText());
        hospede.setObs(this.telaHospede.getjTextAreaObs().getText());
        
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        hospede.setDataCadastro(sdf.format(new java.util.Date()));

        if (this.telaHospede.getjTextFieldId().getText().trim().isEmpty()) {
            hospede.setStatus('A');
            service.HospedeService.Criar(hospede);
            JOptionPane.showMessageDialog(null, "Hóspede cadastrado com sucesso!");
        } else {
            
            hospede.setId(Integer.parseInt(this.telaHospede.getjTextFieldId().getText()));
            service.HospedeService.Atualizar(hospede);
            JOptionPane.showMessageDialog(null, "Hóspede atualizado com sucesso!");
        }

        utilities.Utilities.ativaDesativa(this.telaHospede.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaHospede.getjPanelDados(), false);
    }

    private void buscarTodosHospedes() {
        DefaultTableModel tabela = (DefaultTableModel) this.telaHospede.getjTableDados().getModel();
        tabela.setRowCount(0);
        
        List<Hospede> listaHospedes = service.HospedeService.Carregar("nome", "");
        
        for (Hospede hospedeAtual : listaHospedes) {
            tabela.addRow(new Object[]{
                hospedeAtual.getId(),
                hospedeAtual.getNome(),
                hospedeAtual.getCpf(),
                hospedeAtual.getStatus()
            });
        }
        
        if (listaHospedes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum hóspede cadastrado!");
        } else {
            JOptionPane.showMessageDialog(null, "Total de " + listaHospedes.size() + " hóspede(s) encontrado(s)!");
        }
    }

    private void filtrarHospedes() {
        if (this.telaHospede.getjTFFiltro().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite algo para filtrar!");
            this.telaHospede.getjTFFiltro().requestFocus();
            return;
        }

        DefaultTableModel tabela = (DefaultTableModel) this.telaHospede.getjTableDados().getModel();
        tabela.setRowCount(0);

        if (this.telaHospede.getjCBFiltro().getSelectedIndex() == 0) {
            try {
                int id = Integer.parseInt(this.telaHospede.getjTFFiltro().getText());
                Hospede hospede = service.HospedeService.Carregar(id);
                
                if (hospede.getId() != 0) {
                    tabela.addRow(new Object[]{
                        hospede.getId(),
                        hospede.getNome(),
                        hospede.getCpf(),
                        hospede.getStatus()
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "Hóspede não encontrado!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Digite um ID válido (número)!");
            }
            
        } else if (this.telaHospede.getjCBFiltro().getSelectedIndex() == 1) {
            List<Hospede> listaHospedes = service.HospedeService.Carregar("nome", this.telaHospede.getjTFFiltro().getText());
            
            for (Hospede hospedeAtual : listaHospedes) {
                tabela.addRow(new Object[]{
                    hospedeAtual.getId(),
                    hospedeAtual.getNome(),
                    hospedeAtual.getCpf(),
                    hospedeAtual.getStatus()
                });
            }
            
            if (listaHospedes.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum hóspede encontrado com esse nome!");
            }
            
        } else if (this.telaHospede.getjCBFiltro().getSelectedIndex() == 2) {
            List<Hospede> listaHospedes = service.HospedeService.Carregar("cpf", this.telaHospede.getjTFFiltro().getText());
            
            for (Hospede hospedeAtual : listaHospedes) {
                tabela.addRow(new Object[]{
                    hospedeAtual.getId(),
                    hospedeAtual.getNome(),
                    hospedeAtual.getCpf(),
                    hospedeAtual.getStatus()
                });
            }
            
            if (listaHospedes.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum hóspede encontrado com esse CPF!");
            }
        }
    }

    private void carregarHospedeSelecionado() {
        if (this.telaHospede.getjTableDados().getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um hóspede na tabela!");
            return;
        }

        int codigo = (int) this.telaHospede.getjTableDados().getValueAt(
            this.telaHospede.getjTableDados().getSelectedRow(), 0
        );

        utilities.Utilities.ativaDesativa(this.telaHospede.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaHospede.getjPanelDados(), true);

        this.telaHospede.getjTextFieldId().setText(codigo + "");
        this.telaHospede.getjTextFieldId().setEnabled(false);

        Hospede hospede = service.HospedeService.Carregar(codigo);

        this.telaHospede.getjTextFieldNomeFantasia().setText(hospede.getNome());
        this.telaHospede.getjTextFieldRazaoSocial().setText(hospede.getRazaoSocial());
        this.telaHospede.getjFormattedTextFieldCpf().setText(hospede.getCpf());
        this.telaHospede.getjTextFieldRg().setText(hospede.getRg());
        this.telaHospede.getjFormattedTextFieldCnpj().setText(hospede.getCnpj());
        this.telaHospede.getjTextFieldInscricaoEstadual().setText(hospede.getInscricaoEstdual());
        this.telaHospede.getjTextFieldContato().setText(hospede.getContato());
        this.telaHospede.getjTextFieldEmail().setText(hospede.getEmail());
        this.telaHospede.getjFormattedTextFieldFone1().setText(hospede.getFone1());
        this.telaHospede.getjFormattedTextFieldFone2().setText(hospede.getFone2());
        this.telaHospede.getjFormattedTextFieldCep().setText(hospede.getCep());
        this.telaHospede.getjTextFieldLogradouro().setText(hospede.getLogradouro());
        this.telaHospede.getjTextFieldBairro().setText(hospede.getBairro());
        this.telaHospede.getjTextFieldCidade().setText(hospede.getCidade());
        this.telaHospede.getjTextFieldComplemento().setText(hospede.getComplemento());
        this.telaHospede.getjTextAreaObs().setText(hospede.getObs());

        this.telaHospede.getjTextFieldNomeFantasia().requestFocus();
    }
}