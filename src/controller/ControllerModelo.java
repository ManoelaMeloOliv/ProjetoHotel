package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.bo.Marca;
import model.bo.Modelo;
import view.TelaModelo;


public class ControllerModelo implements ActionListener {

    private TelaModelo telaModelo;

    public ControllerModelo(TelaModelo telaModelo) {
        this.telaModelo = telaModelo;

        this.telaModelo.getjButtonNovo().addActionListener(this);
        this.telaModelo.getjButtonCancelar().addActionListener(this);
        this.telaModelo.getjButtonGravar().addActionListener(this);
        this.telaModelo.getjButtonFiltrar().addActionListener(this);
        this.telaModelo.getjButtonBuscarTodos().addActionListener(this);
        this.telaModelo.getjButtonSair().addActionListener(this);

       
        this.telaModelo.getjTableDados().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {  
                    carregarModeloSelecionado();
                }
            }
        });

        
        utilities.Utilities.ativaDesativa(this.telaModelo.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaModelo.getjPanelDados(), false);
        
       
        carregarMarcas();
    }

   
  @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == this.telaModelo.getjButtonNovo()) {
            novoModelo();
        } else if (evento.getSource() == this.telaModelo.getjButtonCancelar()) {
            cancelar();
        } else if (evento.getSource() == this.telaModelo.getjButtonGravar()) {
            gravarModelo();
        } else if (evento.getSource() == this.telaModelo.getjButtonFiltrar()) {
            filtrarModelos();
        } else if (evento.getSource() == this.telaModelo.getjButtonBuscarTodos()) {
            buscarTodosModelos();
            
        } else if (evento.getSource() == this.telaModelo.getjButtonSair()) {
           
            javax.swing.SwingUtilities.getWindowAncestor(this.telaModelo).dispose();
        }
    }


    private void carregarMarcas() {
       
        this.telaModelo.getjComboBoxMarca().removeAllItems();
        
      
        List<Marca> listaMarcas = service.MarcaService.Carregar("status", "A");
        
      
        for (Marca marca : listaMarcas) {
            this.telaModelo.getjComboBoxMarca().addItem(marca);
            
            
            System.out.println("Marca adicionada: " + marca.toString()); 
       
        }
        
    
        if (listaMarcas.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Nenhuma marca ativa encontrada!\n" +
                "Cadastre uma marca primeiro usando a TelaMarca.");
        } else {
         
            System.out.println("Total de marcas carregadas: " + listaMarcas.size());
        }
    }

    
    private void novoModelo() {
       
        utilities.Utilities.ativaDesativa(this.telaModelo.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaModelo.getjPanelDados(), true);
        
        this.telaModelo.getjTextFieldId().setEnabled(false);
        this.telaModelo.getjComboBoxStatus().setSelectedIndex(0);
        carregarMarcas();
        this.telaModelo.getjTextFieldDescricao().requestFocus();
    }

    
    private void cancelar() {
       
        utilities.Utilities.ativaDesativa(this.telaModelo.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaModelo.getjPanelDados(), false);
    }

   
    private void gravarModelo() {
    
        if (this.telaModelo.getjTextFieldDescricao().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Descrição é obrigatório!");
            this.telaModelo.getjTextFieldDescricao().requestFocus();
            return;
        }

     
        if (this.telaModelo.getjComboBoxMarca().getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Selecione uma Marca!");
            this.telaModelo.getjComboBoxMarca().requestFocus();
            return;
        }

     
        Modelo modelo = new Modelo();
        
        
        modelo.setDescricao(this.telaModelo.getjTextFieldDescricao().getText().trim());
       
        Marca marcaSelecionada = (Marca) this.telaModelo.getjComboBoxMarca().getSelectedItem();
        modelo.setMarca(marcaSelecionada);
        
        char status = this.telaModelo.getjComboBoxStatus().getSelectedIndex() == 0 ? 'A' : 'C';
        modelo.setStatus(status);

   
        if (this.telaModelo.getjTextFieldId().getText().trim().isEmpty()) {
            service.ModeloService.Criar(modelo);
            JOptionPane.showMessageDialog(null, "Modelo cadastrado com sucesso!");
            
        } else {
            modelo.setId(Integer.parseInt(this.telaModelo.getjTextFieldId().getText()));
            service.ModeloService.Atualizar(modelo);
            JOptionPane.showMessageDialog(null, "Modelo atualizado com sucesso!");
        }

        
        utilities.Utilities.ativaDesativa(this.telaModelo.getjPanelBotoes(), true);
        utilities.Utilities.limpaComponentes(this.telaModelo.getjPanelDados(), false);
        
        if (!this.telaModelo.getjTFFiltro().getText().trim().isEmpty()) {
            filtrarModelos();
        }
    }

   
    private void filtrarModelos() {
    
        if (this.telaModelo.getjTFFiltro().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite algo para filtrar!");
            return;
        }

        DefaultTableModel tabela = (DefaultTableModel) this.telaModelo.getjTableDados().getModel();
        tabela.setRowCount(0);

        if (this.telaModelo.getjCBFiltro().getSelectedIndex() == 0) {
            try {
                int id = Integer.parseInt(this.telaModelo.getjTFFiltro().getText());
                Modelo modelo = service.ModeloService.Carregar(id);
                
                if (modelo.getId() != 0) {
                  
                    tabela.addRow(new Object[]{
                        modelo.getId(),
                        modelo.getDescricao(),
                        modelo.getMarca().getDescricao(),  
                        modelo.getStatus()
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "Modelo não encontrado!");
                }
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Digite um ID válido (número)!");
            }
            
      
        } else if (this.telaModelo.getjCBFiltro().getSelectedIndex() == 1) {
            List<Modelo> listaModelos = service.ModeloService.Carregar("descricao", 
                                        this.telaModelo.getjTFFiltro().getText());
            
          
            for (Modelo modeloAtual : listaModelos) {
                tabela.addRow(new Object[]{
                    modeloAtual.getId(),
                    modeloAtual.getDescricao(),
                    modeloAtual.getMarca().getDescricao(),
                    modeloAtual.getStatus()
                });
            }
            
            if (listaModelos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum modelo encontrado com essa descrição!");
            }
        }
    }

    private void buscarTodosModelos() {
        DefaultTableModel tabela = (DefaultTableModel) this.telaModelo.getjTableDados().getModel();
        tabela.setRowCount(0);

       
        List<Modelo> listaModelos = service.ModeloService.Carregar("status", "A");

        for (Modelo modeloAtual : listaModelos) {
            tabela.addRow(new Object[]{
                modeloAtual.getId(),
                modeloAtual.getDescricao(),
                modeloAtual.getMarca().getDescricao(),
                modeloAtual.getStatus()
            });
        }

        if (listaModelos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum modelo ativo cadastrado!");
        } else {
            JOptionPane.showMessageDialog(null, "Total de modelos ativos: " + listaModelos.size());
        }
    }

    
    private void carregarModeloSelecionado() {
     
        if (this.telaModelo.getjTableDados().getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um modelo na tabela!");
            return;
        }

 
        int codigo = (int) this.telaModelo.getjTableDados().getValueAt(
            this.telaModelo.getjTableDados().getSelectedRow(),
            0
        );

        utilities.Utilities.ativaDesativa(this.telaModelo.getjPanelBotoes(), false);
        utilities.Utilities.limpaComponentes(this.telaModelo.getjPanelDados(), true);

   
        this.telaModelo.getjTextFieldId().setText(codigo + "");
        this.telaModelo.getjTextFieldId().setEnabled(false);

     
        Modelo modelo = service.ModeloService.Carregar(codigo);

  
        this.telaModelo.getjTextFieldDescricao().setText(modelo.getDescricao());
        
        carregarMarcas();
        

        for (int i = 0; i < this.telaModelo.getjComboBoxMarca().getItemCount(); i++) {
            Marca marca = this.telaModelo.getjComboBoxMarca().getItemAt(i);
           
            if (marca.getId() == modelo.getMarca().getId()) {
                this.telaModelo.getjComboBoxMarca().setSelectedIndex(i);
                break;
            }
        }
        
      
        this.telaModelo.getjComboBoxStatus().setSelectedIndex(modelo.getStatus() == 'A' ? 0 : 1);
        this.telaModelo.getjTextFieldDescricao().requestFocus();
    }
}
