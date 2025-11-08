package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.bo.Marca;
import model.bo.Modelo;
import model.bo.Veiculo;
import view.TelaVeiculo;

public class ControllerVeiculo implements ActionListener {

    private TelaVeiculo telaVeiculo;

    public ControllerVeiculo(TelaVeiculo telaVeiculo) {
        this.telaVeiculo = telaVeiculo;

        this.telaVeiculo.getjButtonNovo().addActionListener(this);
        this.telaVeiculo.getjButtonCancelar().addActionListener(this);
        this.telaVeiculo.getjButtonGravar().addActionListener(this);
        this.telaVeiculo.getjButtonFiltrar().addActionListener(this);
        this.telaVeiculo.getjButtonBuscarTodos().addActionListener(this);
        this.telaVeiculo.getjButtonSair().addActionListener(this);

        this.telaVeiculo.getjTableDados().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    carregarVeiculoSelecionado();
                }
            }
        });
      //Quando a marca muda no ComboBox, ele chama carregarModelosPorMarca()
        this.telaVeiculo.getjComboBoxMarca().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    carregarModelosPorMarca();
                }
            }
        });
        
        // os  botao principal ficam ativos ai quando vc clica em um desativas outos 

        utilities.Utilities.ativaDesativa(this.telaVeiculo.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaVeiculo.getjPanelDados(), false);

        carregarMarcas();
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaVeiculo.getjButtonNovo()) {
            novoVeiculo();
        } else if (evento.getSource() == this.telaVeiculo.getjButtonCancelar()) {
            cancelar();
        } else if (evento.getSource() == this.telaVeiculo.getjButtonGravar()) {
            gravarVeiculo();
        } else if (evento.getSource() == this.telaVeiculo.getjButtonFiltrar()) {
            filtrarVeiculos();
        } else if (evento.getSource() == this.telaVeiculo.getjButtonBuscarTodos()) {
            buscarTodosVeiculos();

        } else if (evento.getSource() == this.telaVeiculo.getjButtonSair()) {

            javax.swing.SwingUtilities.getWindowAncestor(this.telaVeiculo).dispose();
        }
    }

    private void carregarMarcas() {
        this.telaVeiculo.getjComboBoxMarca().removeAllItems();

        List<Marca> listaMarcas = service.MarcaService.Carregar("status", "A");

        for (Marca marca : listaMarcas) {
            this.telaVeiculo.getjComboBoxMarca().addItem(marca);
        }

        if (listaMarcas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma marca ativa encontrada! Cadastre uma marca primeiro.");
        }
    }

    private void carregarModelosPorMarca() {
        this.telaVeiculo.getjComboBoxModelo().removeAllItems();

        Marca marcaSelecionada = (Marca) this.telaVeiculo.getjComboBoxMarca().getSelectedItem();

        if (marcaSelecionada == null) {
            return;
        }

        List<Modelo> todosModelos = service.ModeloService.Carregar("status", "A");

        for (Modelo modelo : todosModelos) {
            if (modelo.getMarca().getId() == marcaSelecionada.getId()) {
                this.telaVeiculo.getjComboBoxModelo().addItem(modelo);
            }
        }

        if (this.telaVeiculo.getjComboBoxModelo().getItemCount() == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum modelo ativo encontrado para esta marca!");
        }
    }

    private void novoVeiculo() {
        utilities.Utilities.ativaDesativa(this.telaVeiculo.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaVeiculo.getjPanelDados(), true);

        this.telaVeiculo.getjTextFieldId().setEnabled(false);
        this.telaVeiculo.getjComboBoxStatus().setSelectedIndex(0);

        carregarMarcas();

        this.telaVeiculo.getjTextFieldPlaca().requestFocus();
    }

    private void cancelar() {
        utilities.Utilities.ativaDesativa(this.telaVeiculo.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaVeiculo.getjPanelDados(), false);
    }

    private void gravarVeiculo() {
        if (this.telaVeiculo.getjTextFieldPlaca().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Placa é obrigatório!");
            this.telaVeiculo.getjTextFieldPlaca().requestFocus();
            return;
        }

        if (this.telaVeiculo.getjTextFieldCor().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Cor é obrigatório!");
            this.telaVeiculo.getjTextFieldCor().requestFocus();
            return;
        }

        if (this.telaVeiculo.getjComboBoxMarca().getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Selecione uma Marca!");
            this.telaVeiculo.getjComboBoxMarca().requestFocus();
            return;
        }

        if (this.telaVeiculo.getjComboBoxModelo().getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Selecione um Modelo!");
            this.telaVeiculo.getjComboBoxModelo().requestFocus();
            return;
        }

        Veiculo veiculo = new Veiculo();

        veiculo.setPlaca(this.telaVeiculo.getjTextFieldPlaca().getText().toUpperCase().trim());
        veiculo.setCor(this.telaVeiculo.getjTextFieldCor().getText().trim());

        Modelo modeloSelecionado = (Modelo) this.telaVeiculo.getjComboBoxModelo().getSelectedItem();
        veiculo.setModelo(modeloSelecionado);

        char status = this.telaVeiculo.getjComboBoxStatus().getSelectedIndex() == 0 ? 'A' : 'C';
        veiculo.setStatus(status);

        if (this.telaVeiculo.getjTextFieldId().getText().trim().isEmpty()) {
            service.VeiculoService.Criar(veiculo);
            JOptionPane.showMessageDialog(null, "Veículo cadastrado com sucesso!");

        } else {
            veiculo.setId(Integer.parseInt(this.telaVeiculo.getjTextFieldId().getText()));
            service.VeiculoService.Atualizar(veiculo);
            JOptionPane.showMessageDialog(null, "Veículo atualizado com sucesso!");
        }

        utilities.Utilities.ativaDesativa(this.telaVeiculo.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaVeiculo.getjPanelDados(), false);

        if (!this.telaVeiculo.getjTFFiltro().getText().trim().isEmpty()) {
            filtrarVeiculos();
        }
    }

    private void filtrarVeiculos() {
        if (this.telaVeiculo.getjTFFiltro().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite algo para filtrar!");
            return;
        }

        DefaultTableModel tabela = (DefaultTableModel) this.telaVeiculo.getjTableDados().getModel();
        tabela.setRowCount(0);

        if (this.telaVeiculo.getjCBFiltro().getSelectedIndex() == 0) {
            
            try {
                int id = Integer.parseInt(this.telaVeiculo.getjTFFiltro().getText());
                Veiculo veiculo = service.VeiculoService.Carregar(id);

                if (veiculo.getId() != 0) {
                    tabela.addRow(new Object[]{
                        veiculo.getId(),
                        veiculo.getPlaca(),
                        veiculo.getCor(),
                        veiculo.getModelo().getMarca().getDescricao(),
                        veiculo.getModelo().getDescricao(),
                        veiculo.getStatus()
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "Veículo não encontrado!");
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Digite um ID válido (número)!");
            }

        } else if (this.telaVeiculo.getjCBFiltro().getSelectedIndex() == 1) {
           
            List<Veiculo> listaVeiculos = service.VeiculoService.Carregar("v.placa", this.telaVeiculo.getjTFFiltro().getText());

            for (Veiculo veiculoAtual : listaVeiculos) {
                tabela.addRow(new Object[]{
                    veiculoAtual.getId(),
                    veiculoAtual.getPlaca(),
                    veiculoAtual.getCor(),
                    veiculoAtual.getModelo().getMarca().getDescricao(),
                    veiculoAtual.getModelo().getDescricao(),
                    veiculoAtual.getStatus()
                });
            }

            if (listaVeiculos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum veículo encontrado com essa placa!");
            }

        } else if (this.telaVeiculo.getjCBFiltro().getSelectedIndex() == 2) {
            
            List<Veiculo> listaVeiculos = service.VeiculoService.Carregar("v.cor", this.telaVeiculo.getjTFFiltro().getText());

            for (Veiculo veiculoAtual : listaVeiculos) {
                tabela.addRow(new Object[]{
                    veiculoAtual.getId(),
                    veiculoAtual.getPlaca(),
                    veiculoAtual.getCor(),
                    veiculoAtual.getModelo().getMarca().getDescricao(),
                    veiculoAtual.getModelo().getDescricao(),
                    veiculoAtual.getStatus()
                });
            }

            if (listaVeiculos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum veículo encontrado com essa cor!");
            }
        }
    }

    private void buscarTodosVeiculos() {
        DefaultTableModel tabela = (DefaultTableModel) this.telaVeiculo.getjTableDados().getModel();
        tabela.setRowCount(0);

        
        List<Veiculo> listaVeiculos = service.VeiculoService.Carregar("v.status", "A");

        for (Veiculo veiculoAtual : listaVeiculos) {
            tabela.addRow(new Object[]{
                veiculoAtual.getId(),
                veiculoAtual.getPlaca(),
                veiculoAtual.getCor(),
                veiculoAtual.getModelo().getMarca().getDescricao(),
                veiculoAtual.getModelo().getDescricao(),
                veiculoAtual.getStatus()
            });
        }

        if (listaVeiculos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum veículo ativo cadastrado!");
        } else {
            JOptionPane.showMessageDialog(null, "Total de veículos ativos: " + listaVeiculos.size());
        }
    }

    private void carregarVeiculoSelecionado() {
        if (this.telaVeiculo.getjTableDados().getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um veículo na tabela!");
            return;
        }

        int codigo = (int) this.telaVeiculo.getjTableDados().getValueAt(
                this.telaVeiculo.getjTableDados().getSelectedRow(),
                0
        );

        utilities.Utilities.ativaDesativa(this.telaVeiculo.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaVeiculo.getjPanelDados(), true);

        this.telaVeiculo.getjTextFieldId().setText(codigo + "");
        this.telaVeiculo.getjTextFieldId().setEnabled(false);

        Veiculo veiculo = service.VeiculoService.Carregar(codigo);

        this.telaVeiculo.getjTextFieldPlaca().setText(veiculo.getPlaca());
        this.telaVeiculo.getjTextFieldCor().setText(veiculo.getCor());

        carregarMarcas();

        for (int i = 0; i < this.telaVeiculo.getjComboBoxMarca().getItemCount(); i++) {
            Marca marca = this.telaVeiculo.getjComboBoxMarca().getItemAt(i);
            if (marca.getId() == veiculo.getModelo().getMarca().getId()) {
                this.telaVeiculo.getjComboBoxMarca().setSelectedIndex(i);
                break;
            }
        }

        for (int i = 0; i < this.telaVeiculo.getjComboBoxModelo().getItemCount(); i++) {
            Modelo modelo = this.telaVeiculo.getjComboBoxModelo().getItemAt(i);
            if (modelo.getId() == veiculo.getModelo().getId()) {
                this.telaVeiculo.getjComboBoxModelo().setSelectedIndex(i);
                break;
            }
        }

        this.telaVeiculo.getjComboBoxStatus().setSelectedIndex(veiculo.getStatus() == 'A' ? 0 : 1);

        this.telaVeiculo.getjTextFieldPlaca().requestFocus();
    }
}
