package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.bo.Fornecedor;
import view.TelaFornecedor;

public class ControllerFornecedor implements ActionListener {

    private TelaFornecedor telaFornecedor;

   
    public ControllerFornecedor(TelaFornecedor telaFornecedor) {
        this.telaFornecedor = telaFornecedor;

        this.telaFornecedor.getjButtonNovo().addActionListener(this);
        this.telaFornecedor.getjButtonCancelar().addActionListener(this);
        this.telaFornecedor.getjButtonGravar().addActionListener(this);
        this.telaFornecedor.getjButtonFiltrar().addActionListener(this);
        this.telaFornecedor.getjButtonBuscarTodos().addActionListener(this);
        this.telaFornecedor.getjButtonSair().addActionListener(this);

        this.telaFornecedor.getjTableDados().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    carregarFornecedorSelecionado();
                }
            }
        });

        utilities.Utilities.ativaDesativa(this.telaFornecedor.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaFornecedor.getjPanelDados(), false);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaFornecedor.getjButtonNovo()) {
            novoFornecedor();
        } else if (evento.getSource() == this.telaFornecedor.getjButtonCancelar()) {
            cancelar();
        } else if (evento.getSource() == this.telaFornecedor.getjButtonGravar()) {
            gravarFornecedor();
        } else if (evento.getSource() == this.telaFornecedor.getjButtonFiltrar()) {
            filtrarFornecedores();
        } else if (evento.getSource() == this.telaFornecedor.getjButtonBuscarTodos()) {
            buscarTodosFornecedores();

        } else if (evento.getSource() == this.telaFornecedor.getjButtonSair()) {

            javax.swing.SwingUtilities.getWindowAncestor(this.telaFornecedor).dispose();
        }
    }

    private void novoFornecedor() {
        utilities.Utilities.ativaDesativa(this.telaFornecedor.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaFornecedor.getjPanelDados(), true);
        this.telaFornecedor.getjTextFieldId().setEnabled(false);
        this.telaFornecedor.getjTextFieldNome().requestFocus();
    }

    private void cancelar() {
        utilities.Utilities.ativaDesativa(this.telaFornecedor.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaFornecedor.getjPanelDados(), false);
    }

    private void gravarFornecedor() {
        if (this.telaFornecedor.getjTextFieldNome().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Nome é obrigatório!");
            this.telaFornecedor.getjTextFieldNome().requestFocus();
            return;
        }

        if (this.telaFornecedor.getjTextFieldRazaoSocial().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Razão Social é obrigatório!");
            this.telaFornecedor.getjTextFieldRazaoSocial().requestFocus();
            return;
        }

        if (this.telaFornecedor.getjFormattedTextFieldCnpj().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo CNPJ é obrigatório!");
            this.telaFornecedor.getjFormattedTextFieldCnpj().requestFocus();
            return;
        }

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(this.telaFornecedor.getjTextFieldNome().getText());
        fornecedor.setFone1(this.telaFornecedor.getjFormattedTextFieldFone1().getText());
        fornecedor.setFone2(this.telaFornecedor.getjFormattedTextFieldFone2().getText());
        fornecedor.setEmail(this.telaFornecedor.getjTextFieldEmail().getText());
        fornecedor.setCep(this.telaFornecedor.getjFormattedTextFieldCep().getText());
        fornecedor.setLogradouro(this.telaFornecedor.getjTextFieldLogradouro().getText());
        fornecedor.setBairro(this.telaFornecedor.getjTextFieldBairro().getText());
        fornecedor.setCidade(this.telaFornecedor.getjTextFieldCidade().getText());
        fornecedor.setComplemento(this.telaFornecedor.getjTextFieldComplemento().getText());
        fornecedor.setCpf(this.telaFornecedor.getjFormattedTextFieldCpf().getText());
        fornecedor.setRg(this.telaFornecedor.getjTextFieldRg().getText());
        fornecedor.setObs(this.telaFornecedor.getjTextAreaObs().getText());
        fornecedor.setRazaoSocial(this.telaFornecedor.getjTextFieldRazaoSocial().getText());
        fornecedor.setCnpj(this.telaFornecedor.getjFormattedTextFieldCnpj().getText());
        fornecedor.setInscricaoEstadual(this.telaFornecedor.getjTextFieldInscricaoEstadual().getText());
        fornecedor.setContato(this.telaFornecedor.getjTextFieldContato().getText());

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fornecedor.setDataCadastro(sdf.format(new java.util.Date()));

        if (this.telaFornecedor.getjTextFieldId().getText().trim().isEmpty()) {
            fornecedor.setStatus('A');
            service.FornecedorService.Criar(fornecedor);
            JOptionPane.showMessageDialog(null, "Fornecedor cadastrado com sucesso!");
        } else {
            
            fornecedor.setId(Integer.parseInt(this.telaFornecedor.getjTextFieldId().getText()));
            service.FornecedorService.Atualizar(fornecedor);
            JOptionPane.showMessageDialog(null, "Fornecedor atualizado com sucesso!");
        }

        utilities.Utilities.ativaDesativa(this.telaFornecedor.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaFornecedor.getjPanelDados(), false);
    }

    private void buscarTodosFornecedores() {
        DefaultTableModel tabela = (DefaultTableModel) this.telaFornecedor.getjTableDados().getModel();
        tabela.setRowCount(0);

        List<Fornecedor> listaFornecedores = service.FornecedorService.Carregar("status", "A");

        for (Fornecedor fornecedorAtual : listaFornecedores) {
            tabela.addRow(new Object[]{
                fornecedorAtual.getId(),
                fornecedorAtual.getNome(),
                fornecedorAtual.getCnpj(),
                fornecedorAtual.getStatus()
            });
        }

        if (listaFornecedores.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum fornecedor ativo cadastrado!");
        } else {
            JOptionPane.showMessageDialog(null, "Total de " + listaFornecedores.size() + " fornecedor(es) encontrado(s)!");
        }
    }

    private void filtrarFornecedores() {

        if (this.telaFornecedor.getjTFFiltro().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite algo para filtrar!");
            this.telaFornecedor.getjTFFiltro().requestFocus();
            return;
        }

        DefaultTableModel tabela = (DefaultTableModel) this.telaFornecedor.getjTableDados().getModel();
        tabela.setRowCount(0);

        if (this.telaFornecedor.getjCBFiltro().getSelectedIndex() == 0) {

            try {
                int id = Integer.parseInt(this.telaFornecedor.getjTFFiltro().getText());
                Fornecedor fornecedor = service.FornecedorService.Carregar(id);

                if (fornecedor.getId() != 0) {
                    tabela.addRow(new Object[]{
                        fornecedor.getId(),
                        fornecedor.getNome(),
                        fornecedor.getCnpj(),
                        fornecedor.getStatus()
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "Fornecedor não encontrado!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Digite um ID válido (número)!");
            }

        } else if (this.telaFornecedor.getjCBFiltro().getSelectedIndex() == 1) {

            List<Fornecedor> listaFornecedores = service.FornecedorService.Carregar("nome", this.telaFornecedor.getjTFFiltro().getText());

            for (Fornecedor fornecedorAtual : listaFornecedores) {
                tabela.addRow(new Object[]{
                    fornecedorAtual.getId(),
                    fornecedorAtual.getNome(),
                    fornecedorAtual.getCnpj(),
                    fornecedorAtual.getStatus()
                });
            }

            if (listaFornecedores.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum fornecedor encontrado com esse nome!");
            }

        } else if (this.telaFornecedor.getjCBFiltro().getSelectedIndex() == 2) {

            List<Fornecedor> listaFornecedores = service.FornecedorService.Carregar("cnpj", this.telaFornecedor.getjTFFiltro().getText());

            for (Fornecedor fornecedorAtual : listaFornecedores) {
                tabela.addRow(new Object[]{
                    fornecedorAtual.getId(),
                    fornecedorAtual.getNome(),
                    fornecedorAtual.getCnpj(),
                    fornecedorAtual.getStatus()
                });
            }

            if (listaFornecedores.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum fornecedor encontrado com esse CNPJ!");
            }
        }
    }

    private void carregarFornecedorSelecionado() {
        if (this.telaFornecedor.getjTableDados().getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um fornecedor na tabela!");
            return;
        }

        int codigo = (int) this.telaFornecedor.getjTableDados().getValueAt(
                this.telaFornecedor.getjTableDados().getSelectedRow(), 0
        );

        utilities.Utilities.ativaDesativa(this.telaFornecedor.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaFornecedor.getjPanelDados(), true);

        this.telaFornecedor.getjTextFieldId().setText(codigo + "");
        this.telaFornecedor.getjTextFieldId().setEnabled(false);

        Fornecedor fornecedor = service.FornecedorService.Carregar(codigo);

        this.telaFornecedor.getjTextFieldNome().setText(fornecedor.getNome());
        this.telaFornecedor.getjFormattedTextFieldFone1().setText(fornecedor.getFone1());
        this.telaFornecedor.getjFormattedTextFieldFone2().setText(fornecedor.getFone2());
        this.telaFornecedor.getjTextFieldEmail().setText(fornecedor.getEmail());
        this.telaFornecedor.getjFormattedTextFieldCep().setText(fornecedor.getCep());
        this.telaFornecedor.getjTextFieldLogradouro().setText(fornecedor.getLogradouro());
        this.telaFornecedor.getjTextFieldBairro().setText(fornecedor.getBairro());
        this.telaFornecedor.getjTextFieldCidade().setText(fornecedor.getCidade());
        this.telaFornecedor.getjTextFieldComplemento().setText(fornecedor.getComplemento());
        this.telaFornecedor.getjFormattedTextFieldCnpj().setText(fornecedor.getCnpj());
        this.telaFornecedor.getjTextAreaObs().setText(fornecedor.getObs());

        this.telaFornecedor.getjTextFieldRazaoSocial().setText(fornecedor.getRazaoSocial());
        this.telaFornecedor.getjFormattedTextFieldCnpj().setText(fornecedor.getCnpj());
        this.telaFornecedor.getjTextFieldInscricaoEstadual().setText(fornecedor.getInscricaoEstadual());
        this.telaFornecedor.getjTextFieldContato().setText(fornecedor.getContato());

        this.telaFornecedor.getjTextFieldNome().requestFocus();
    }
}
