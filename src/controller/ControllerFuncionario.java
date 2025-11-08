package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.bo.Funcionario;
import view.TelaFuncionario;


public class ControllerFuncionario implements ActionListener {

    private TelaFuncionario telaFuncionario;

    public ControllerFuncionario(TelaFuncionario telaFuncionario) {
        this.telaFuncionario = telaFuncionario;

        this.telaFuncionario.getjButtonNovo().addActionListener(this);
        this.telaFuncionario.getjButtonCancelar().addActionListener(this);
        this.telaFuncionario.getjButtonGravar().addActionListener(this);
        this.telaFuncionario.getjButtonFiltrar().addActionListener(this);
        this.telaFuncionario.getjButtonBuscarTodos().addActionListener(this);
        this.telaFuncionario.getjButtonSair().addActionListener(this);

        this.telaFuncionario.getjTableDados().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    carregarFuncionarioSelecionado();
                }
            }
        });

      
        utilities.Utilities.ativaDesativa(this.telaFuncionario.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaFuncionario.getjPanelDados(), false);
    }

     @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaFuncionario.getjButtonNovo()) {
            novoFuncionario();
        } else if (evento.getSource() == this.telaFuncionario.getjButtonCancelar()) {
            cancelar();
        } else if (evento.getSource() == this.telaFuncionario.getjButtonGravar()) {
            gravarFuncionario();
        } else if (evento.getSource() == this.telaFuncionario.getjButtonFiltrar()) {
            filtrarFuncionarios();
        } else if (evento.getSource() == this.telaFuncionario.getjButtonBuscarTodos()) {
            buscarTodosFuncionarios();
            
        } else if (evento.getSource() == this.telaFuncionario.getjButtonSair()) {
          
            javax.swing.SwingUtilities.getWindowAncestor(this.telaFuncionario).dispose();
        }
    }

    private void novoFuncionario() {
        utilities.Utilities.ativaDesativa(this.telaFuncionario.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaFuncionario.getjPanelDados(), true);
        this.telaFuncionario.getjTextFieldId().setEnabled(false);
        this.telaFuncionario.getjTextFieldNome().requestFocus();
    }

    private void cancelar() {
        utilities.Utilities.ativaDesativa(this.telaFuncionario.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaFuncionario.getjPanelDados(), false);
    }

    private void gravarFuncionario() {
        
        if (this.telaFuncionario.getjTextFieldNome().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Nome é obrigatório!");
            this.telaFuncionario.getjTextFieldNome().requestFocus();
            return;
        }
        
        if (this.telaFuncionario.getjTextFieldUsuario().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Usuário é obrigatório!");
            this.telaFuncionario.getjTextFieldUsuario().requestFocus();
            return;
        }
        
        if (new String(this.telaFuncionario.getjPasswordFieldSenha().getPassword()).trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Senha é obrigatório!");
            this.telaFuncionario.getjPasswordFieldSenha().requestFocus();
            return;
        }

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(this.telaFuncionario.getjTextFieldNome().getText());
        funcionario.setUsuario(this.telaFuncionario.getjTextFieldUsuario().getText());
        funcionario.setSenha(new String(this.telaFuncionario.getjPasswordFieldSenha().getPassword()));
        
      
        funcionario.setFone1(this.telaFuncionario.getjFormattedTextFieldFone1().getText());
        funcionario.setFone2(this.telaFuncionario.getjFormattedTextFieldFone2().getText());
        
        funcionario.setEmail(this.telaFuncionario.getjTextFieldEmail().getText());
        funcionario.setCep(this.telaFuncionario.getjFormattedTextFieldCep().getText());
        funcionario.setLogradouro(this.telaFuncionario.getjTextFieldLogradouro().getText());
        funcionario.setBairro(this.telaFuncionario.getjTextFieldBairro().getText());
        funcionario.setCidade(this.telaFuncionario.getjTextFieldCidade().getText());
        funcionario.setComplemento(this.telaFuncionario.getjTextFieldComplemento().getText());
        funcionario.setCpf(this.telaFuncionario.getjFormattedTextFieldCpf().getText());
        funcionario.setRg(this.telaFuncionario.getjTextFieldRg().getText());
        funcionario.setObs(this.telaFuncionario.getjTextAreaObs().getText());
    
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        funcionario.setDataCadastro(sdf.format(new java.util.Date()));

        if (this.telaFuncionario.getjTextFieldId().getText().trim().isEmpty()) {
           
            funcionario.setStatus('A');
            service.FuncionarioService.Criar(funcionario);
            JOptionPane.showMessageDialog(null, "Funcionário cadastrado com sucesso!");
        } else {
          
            funcionario.setId(Integer.parseInt(this.telaFuncionario.getjTextFieldId().getText()));
            service.FuncionarioService.Atualizar(funcionario);
            JOptionPane.showMessageDialog(null, "Funcionário atualizado com sucesso!");
        }

        
        utilities.Utilities.ativaDesativa(this.telaFuncionario.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaFuncionario.getjPanelDados(), false);
    }

    private void buscarTodosFuncionarios() {
        DefaultTableModel tabela = (DefaultTableModel) this.telaFuncionario.getjTableDados().getModel();
        tabela.setRowCount(0);
     
        List<Funcionario> listaFuncionarios = service.FuncionarioService.Carregar("status", "A");
        
        for (Funcionario funcionarioAtual : listaFuncionarios) {
            tabela.addRow(new Object[]{
                funcionarioAtual.getId(),
                funcionarioAtual.getNome(),
                funcionarioAtual.getUsuario(),
                funcionarioAtual.getStatus()
            });
        }
        
        if (listaFuncionarios.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum funcionário ativo cadastrado!");
        } else {
            JOptionPane.showMessageDialog(null, "Total de " + listaFuncionarios.size() + " funcionário(s) encontrado(s)!");
        }
    }

    private void filtrarFuncionarios() {
       
        if (this.telaFuncionario.getjTFFiltro().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite algo para filtrar!");
            this.telaFuncionario.getjTFFiltro().requestFocus();
            return;
        }

        DefaultTableModel tabela = (DefaultTableModel) this.telaFuncionario.getjTableDados().getModel();
        tabela.setRowCount(0);

        if (this.telaFuncionario.getjCBFiltro().getSelectedIndex() == 0) {
           
            try {
                int id = Integer.parseInt(this.telaFuncionario.getjTFFiltro().getText());
                Funcionario funcionario = service.FuncionarioService.Carregar(id);
                
                if (funcionario.getId() != 0) {
                    tabela.addRow(new Object[]{
                        funcionario.getId(),
                        funcionario.getNome(),
                        funcionario.getUsuario(),
                        funcionario.getStatus()
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "Funcionário não encontrado!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Digite um ID válido (número)!");
            }
            
        } else if (this.telaFuncionario.getjCBFiltro().getSelectedIndex() == 1) {
         
            List<Funcionario> listaFuncionarios = service.FuncionarioService.Carregar("nome", this.telaFuncionario.getjTFFiltro().getText());
            
            for (Funcionario funcionarioAtual : listaFuncionarios) {
                tabela.addRow(new Object[]{
                    funcionarioAtual.getId(),
                    funcionarioAtual.getNome(),
                    funcionarioAtual.getUsuario(),
                    funcionarioAtual.getStatus()
                });
            }
            
            if (listaFuncionarios.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum funcionário encontrado com esse nome!");
            }
            
        } else if (this.telaFuncionario.getjCBFiltro().getSelectedIndex() == 2) {
        
            List<Funcionario> listaFuncionarios = service.FuncionarioService.Carregar("usuario", this.telaFuncionario.getjTFFiltro().getText());
            
            for (Funcionario funcionarioAtual : listaFuncionarios) {
                tabela.addRow(new Object[]{
                    funcionarioAtual.getId(),
                    funcionarioAtual.getNome(),
                    funcionarioAtual.getUsuario(),
                    funcionarioAtual.getStatus()
                });
            }
            
            if (listaFuncionarios.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum funcionário encontrado com esse usuário!");
            }
        }
    }

    private void carregarFuncionarioSelecionado() {
        if (this.telaFuncionario.getjTableDados().getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um funcionário na tabela!");
            return;
        }

        int codigo = (int) this.telaFuncionario.getjTableDados().getValueAt(
            this.telaFuncionario.getjTableDados().getSelectedRow(), 0
        );

        utilities.Utilities.ativaDesativa(this.telaFuncionario.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaFuncionario.getjPanelDados(), true);

        this.telaFuncionario.getjTextFieldId().setText(codigo + "");
        this.telaFuncionario.getjTextFieldId().setEnabled(false);

        Funcionario funcionario = service.FuncionarioService.Carregar(codigo);

        this.telaFuncionario.getjTextFieldNome().setText(funcionario.getNome());
        this.telaFuncionario.getjTextFieldUsuario().setText(funcionario.getUsuario());
        this.telaFuncionario.getjPasswordFieldSenha().setText(funcionario.getSenha());
        this.telaFuncionario.getjFormattedTextFieldFone1().setText(funcionario.getFone1());
        this.telaFuncionario.getjFormattedTextFieldFone2().setText(funcionario.getFone2());
        
        this.telaFuncionario.getjTextFieldEmail().setText(funcionario.getEmail());
        this.telaFuncionario.getjFormattedTextFieldCep().setText(funcionario.getCep());
        this.telaFuncionario.getjTextFieldLogradouro().setText(funcionario.getLogradouro());
        this.telaFuncionario.getjTextFieldBairro().setText(funcionario.getBairro());
        this.telaFuncionario.getjTextFieldCidade().setText(funcionario.getCidade());
        this.telaFuncionario.getjTextFieldComplemento().setText(funcionario.getComplemento());
        this.telaFuncionario.getjFormattedTextFieldCpf().setText(funcionario.getCpf());
        this.telaFuncionario.getjTextFieldRg().setText(funcionario.getRg());
        this.telaFuncionario.getjTextAreaObs().setText(funcionario.getObs());

        this.telaFuncionario.getjTextFieldNome().requestFocus();
    }
}