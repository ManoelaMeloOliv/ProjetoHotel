package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;


public class TelaFornecedor extends JPanel {
    
    private JComboBox<String> jCBFiltro;
    private JTextField jTFFiltro;
    private JButton jButtonFiltrar;
    private JButton jButtonBuscarTodos;
    private JTable jTableDados;
    
    private JPanel jPanelDados;
    private JTextField jTextFieldId;
    private JTextField jTextFieldNome;
    private JTextField jTextFieldRazaoSocial;
    private JFormattedTextField jFormattedTextFieldCnpj;
    private JFormattedTextField jFormattedTextFieldCpf;
    private JTextField jTextFieldRg;
    private JTextField jTextFieldInscricaoEstadual;
    private JTextField jTextFieldContato;
    private JTextField jTextFieldEmail;
    private JFormattedTextField jFormattedTextFieldFone1;
    private JFormattedTextField jFormattedTextFieldFone2;
    private JFormattedTextField jFormattedTextFieldCep;
    private JTextField jTextFieldLogradouro;
    private JTextField jTextFieldBairro;
    private JTextField jTextFieldCidade;
    private JTextField jTextFieldComplemento;
    private JTextArea jTextAreaObs;
    
    private JPanel jPanelBotoes;
    private JButton jButtonNovo;
    private JButton jButtonCancelar;
    private JButton jButtonGravar;
    private JButton jButtonSair;
    
    
    public TelaFornecedor() {
        setSize(1200, 700);
        applyTheme();
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(0, 0));
        
        add(createHeaderPanel(), BorderLayout.NORTH);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(500);
        splitPane.setDividerSize(8);
        
        splitPane.setLeftComponent(createBuscaPanel());
        splitPane.setRightComponent(createFormularioPanel());
        
        add(splitPane, BorderLayout.CENTER);
        add(createBotoesPanel(), BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(new Color(230, 126, 34));
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));
        
        JLabel lblTitulo = new JLabel("📦 GERENCIAMENTO DE FORNECEDORES");
        lblTitulo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        
        panel.add(lblTitulo);
        
        return panel;
    }
    
    private JPanel createBuscaPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);
        
        mainPanel.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 0, 1, new Color(220, 220, 220)),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel lblTitulo = new JLabel("🔍 Buscar Fornecedores");
        lblTitulo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(52, 73, 94));
        lblTitulo.setBorder(new EmptyBorder(0, 0, 15, 0));
        
        
        JPanel filtroPanel = new JPanel(new GridBagLayout());
        filtroPanel.setBackground(Color.WHITE);
        filtroPanel.setBorder(new EmptyBorder(5, 0, 10, 0));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
       
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.0;
        JLabel lblFiltro = new JLabel("Filtrar por:");
        lblFiltro.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblFiltro.setForeground(new Color(52, 73, 94));
        filtroPanel.add(lblFiltro, gbc);
        
        gbc.gridx = 1; gbc.weightx = 0.35;
        jCBFiltro = new JComboBox<>(new String[]{"ID", "Nome", "CNPJ"});
        jCBFiltro.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jCBFiltro.setPreferredSize(new Dimension(120, 30));
        filtroPanel.add(jCBFiltro, gbc);
        
        gbc.gridx = 2; gbc.weightx = 0.65;
        jTFFiltro = new JTextField();
        jTFFiltro.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jTFFiltro.setPreferredSize(new Dimension(150, 30));
        filtroPanel.add(jTFFiltro, gbc);
       
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; gbc.weightx = 0.5;
        jButtonFiltrar = createSmallButton("Filtrar", new Color(230, 126, 34));
        filtroPanel.add(jButtonFiltrar, gbc);
        
        gbc.gridx = 2; gbc.gridwidth = 1; gbc.weightx = 0.5;
        jButtonBuscarTodos = createSmallButton("Buscar Todos", new Color(211, 84, 0));
        filtroPanel.add(jButtonBuscarTodos, gbc);
        
        String[] colunas = {"ID", "Nome", "CNPJ", "Status"};
        
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        jTableDados = new JTable(model);
        jTableDados.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        jTableDados.setRowHeight(30);
        
        jTableDados.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        jTableDados.getTableHeader().setBackground(new Color(52, 73, 94));
        jTableDados.getTableHeader().setForeground(Color.WHITE);
        
        jTableDados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableDados.setSelectionBackground(new Color(230, 126, 34));
        jTableDados.setSelectionForeground(Color.WHITE);
        
        jTableDados.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTableDados.getColumnModel().getColumn(1).setPreferredWidth(180);
        jTableDados.getColumnModel().getColumn(2).setPreferredWidth(120);
        jTableDados.getColumnModel().getColumn(3).setPreferredWidth(60);
        
        JScrollPane scrollPane = new JScrollPane(jTableDados);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        
        JPanel topPanel = new JPanel(new BorderLayout(0, 10));
        topPanel.setBackground(Color.WHITE);
        topPanel.add(lblTitulo, BorderLayout.NORTH);
        topPanel.add(filtroPanel, BorderLayout.CENTER);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        return mainPanel;
    }
    
    private JPanel createFormularioPanel() {
        jPanelDados = new JPanel(new BorderLayout(10, 10));
        jPanelDados.setBackground(Color.WHITE);
        jPanelDados.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitulo = new JLabel("📋 Dados do Fornecedor");
        lblTitulo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(52, 73, 94));
        lblTitulo.setBorder(new EmptyBorder(0, 0, 15, 0));
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 11));
        
        tabbedPane.addTab("Dados Principais", createDadosPrincipaisPanel());
        tabbedPane.addTab("Endereço", createEnderecoPanel());
        tabbedPane.addTab("Observações", createObservacoesPanel());
        
        jPanelDados.add(lblTitulo, BorderLayout.NORTH);
        jPanelDados.add(tabbedPane, BorderLayout.CENTER);
        
        return jPanelDados;
    }
    
    private JPanel createDadosPrincipaisPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.0;
        panel.add(createLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.3;
        jTextFieldId = new JTextField();
        jTextFieldId.setEnabled(false);
        jTextFieldId.setPreferredSize(new Dimension(100, 30));
        panel.add(jTextFieldId, gbc);
        
        
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.0;
        panel.add(createLabel("* Nome Fantasia:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.weightx = 1.0;
        jTextFieldNome = new JTextField();
        jTextFieldNome.setPreferredSize(new Dimension(300, 30));
        panel.add(jTextFieldNome, gbc);
        
       
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1; gbc.weightx = 0.0;
        panel.add(createLabel("Razão Social:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.weightx = 1.0;
        jTextFieldRazaoSocial = new JTextField();
        jTextFieldRazaoSocial.setPreferredSize(new Dimension(300, 30));
        panel.add(jTextFieldRazaoSocial, gbc);
        
       
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1; gbc.weightx = 0.0;
        panel.add(createLabel("* CNPJ:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.3;
        jFormattedTextFieldCnpj = createFormattedTextField("##.###.###/####-##");
        panel.add(jFormattedTextFieldCnpj, gbc);
        
        
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.0;
        panel.add(createLabel("CPF:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.3;
        jFormattedTextFieldCpf = createFormattedTextField("###.###.###-##");
        panel.add(jFormattedTextFieldCpf, gbc);
        
        gbc.gridx = 2; gbc.weightx = 0.0;
        panel.add(createLabel("RG:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.3;
        jTextFieldRg = new JTextField();
        jTextFieldRg.setPreferredSize(new Dimension(120, 28));
        panel.add(jTextFieldRg, gbc);
        
        
        gbc.gridx = 2; gbc.weightx = 0.0;
        panel.add(createLabel("Insc. Estadual:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.3;
        jTextFieldInscricaoEstadual = new JTextField();
        jTextFieldInscricaoEstadual.setPreferredSize(new Dimension(130, 30));
        panel.add(jTextFieldInscricaoEstadual, gbc);
        
       
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.0;
        panel.add(createLabel("Contato:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.weightx = 1.0;
        jTextFieldContato = new JTextField();
        jTextFieldContato.setPreferredSize(new Dimension(300, 30));
        panel.add(jTextFieldContato, gbc);
        
        
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1; gbc.weightx = 0.0;
        panel.add(createLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.weightx = 1.0;
        jTextFieldEmail = new JTextField();
        jTextFieldEmail.setPreferredSize(new Dimension(300, 30));
        panel.add(jTextFieldEmail, gbc);
        
       
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1; gbc.weightx = 0.0;
        panel.add(createLabel("Telefone 1:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.3;
        jFormattedTextFieldFone1 = createFormattedTextField("(##)#####-####");
        panel.add(jFormattedTextFieldFone1, gbc);
        
        gbc.gridx = 2; gbc.weightx = 0.0;
        panel.add(createLabel("Telefone 2:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.3;
        jFormattedTextFieldFone2 = createFormattedTextField("(##)#####-####");
        panel.add(jFormattedTextFieldFone2, gbc);
        
       
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 4;
        gbc.weighty = 1.0; gbc.fill = GridBagConstraints.BOTH;
        panel.add(new JPanel(), gbc);
        
        return panel;
    }
    
    private JPanel createEnderecoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.0;
        panel.add(createLabel("CEP:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.3;
        jFormattedTextFieldCep = createFormattedTextField("#####-###");
        panel.add(jFormattedTextFieldCep, gbc);
        
       
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.0;
        panel.add(createLabel("Logradouro:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.weightx = 1.0;
        jTextFieldLogradouro = new JTextField();
        jTextFieldLogradouro.setPreferredSize(new Dimension(300, 30));
        panel.add(jTextFieldLogradouro, gbc);
        
      
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1; gbc.weightx = 0.0;
        panel.add(createLabel("Bairro:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.5;
        jTextFieldBairro = new JTextField();
        jTextFieldBairro.setPreferredSize(new Dimension(150, 30));
        panel.add(jTextFieldBairro, gbc);
        
        gbc.gridx = 2; gbc.weightx = 0.0;
        panel.add(createLabel("Cidade:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.5;
        jTextFieldCidade = new JTextField();
        jTextFieldCidade.setPreferredSize(new Dimension(150, 30));
        panel.add(jTextFieldCidade, gbc);
        
     
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.0;
        panel.add(createLabel("Complemento:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.weightx = 1.0;
        jTextFieldComplemento = new JTextField();
        jTextFieldComplemento.setPreferredSize(new Dimension(300, 30));
        panel.add(jTextFieldComplemento, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 4;
        gbc.weighty = 1.0; gbc.fill = GridBagConstraints.BOTH;
        panel.add(new JPanel(), gbc);
        
        return panel;
    }
    
    private JPanel createObservacoesPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JLabel lblObs = createLabel("Observações:");
        panel.add(lblObs, BorderLayout.NORTH);
        
        jTextAreaObs = new JTextArea();
        jTextAreaObs.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jTextAreaObs.setLineWrap(true);
        jTextAreaObs.setWrapStyleWord(true);
        jTextAreaObs.setRows(8);
        
        JScrollPane scrollPane = new JScrollPane(jTextAreaObs);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createBotoesPanel() {
        jPanelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        jPanelBotoes.setBackground(new Color(236, 240, 241));
        jPanelBotoes.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        jButtonNovo = createButton("Novo", new Color(52, 152, 219));
        jButtonNovo.setActionCommand("0");
        
        jButtonCancelar = createButton("Cancelar", new Color(149, 165, 166));
        jButtonCancelar.setActionCommand("1");
        
        jButtonGravar = createButton("Gravar", new Color(46, 204, 113));
        jButtonGravar.setActionCommand("1");
        
        jButtonSair = createButton("Sair", new Color(231, 76, 60));
        jButtonSair.setActionCommand("0");
        
        jPanelBotoes.add(jButtonNovo);
        jPanelBotoes.add(jButtonCancelar);
        jPanelBotoes.add(jButtonGravar);
        jPanelBotoes.add(jButtonSair);
        
        return jPanelBotoes;
    }
    
    private JLabel createLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(new Color(52, 73, 94));
        return label;
    }
    
    private JButton createButton(String texto, Color cor) {
        JButton button = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                
                g2d.setColor(getBackground().darker());
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 8, 8);
                
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        
        button.setText(texto);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(cor);
        
        button.setUI(new BasicButtonUI());
        
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        Dimension size = new Dimension(120, 40);
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setMaximumSize(size);
        
        final Color corOriginal = cor;
        final Color corHover = cor.darker();
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(corHover);
                button.repaint();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(corOriginal);
                button.repaint();
            }
        });
        
        return button;
    }
    
    private JButton createSmallButton(String texto, Color cor) {
        JButton button = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                
                g2d.setColor(getBackground().darker());
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 6, 6);
                
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        
        button.setText(texto);
        button.setFont(new Font("Segoe UI", Font.BOLD, 11));
        button.setForeground(Color.WHITE);
        button.setBackground(cor);
        
        button.setUI(new BasicButtonUI());
        
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        Dimension size = new Dimension(130, 32);
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setMaximumSize(size);
        
        final Color corOriginal = cor;
        final Color corHover = cor.darker();
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(corHover);
                button.repaint();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(corOriginal);
                button.repaint();
            }
        });
        
        return button;
    }
    
    private JFormattedTextField createFormattedTextField(String mask) {
        JFormattedTextField field = null;
        try {
            MaskFormatter formatter = new MaskFormatter(mask);
            formatter.setPlaceholderCharacter('_');
            field = new JFormattedTextField(formatter);
            field.setPreferredSize(new Dimension(130, 30));
        } catch (ParseException e) {
            field = new JFormattedTextField();
            e.printStackTrace();
        }
        return field;
    }
    private void applyTheme() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            } catch (Exception ex) {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
        }
    }

   
    public JComboBox<String> getjCBFiltro() { return jCBFiltro; }
    public JTextField getjTFFiltro() { return jTFFiltro; }
    public JButton getjButtonFiltrar() { return jButtonFiltrar; }
    public JButton getjButtonBuscarTodos() { return jButtonBuscarTodos; }
    public JTable getjTableDados() { return jTableDados; }
    
    public JPanel getjPanelBotoes() { return jPanelBotoes; }
    public JButton getjButtonNovo() { return jButtonNovo; }
    public JButton getjButtonCancelar() { return jButtonCancelar; }
    public JButton getjButtonGravar() { return jButtonGravar; }
    public JButton getjButtonSair() { return jButtonSair; }
    
    public JPanel getjPanelDados() { return jPanelDados; }
    public JTextField getjTextFieldId() { return jTextFieldId; }
    public JTextField getjTextFieldNome() { return jTextFieldNome; }
    public JTextField getjTextFieldRazaoSocial() { return jTextFieldRazaoSocial; }
    public JFormattedTextField getjFormattedTextFieldCnpj() { return jFormattedTextFieldCnpj; }
     public JFormattedTextField getjFormattedTextFieldCpf() { return jFormattedTextFieldCpf; }
    public JTextField getjTextFieldRg() { return jTextFieldRg; }
    public JTextField getjTextFieldInscricaoEstadual() { return jTextFieldInscricaoEstadual; }
    public JTextField getjTextFieldContato() { return jTextFieldContato; }
    public JTextField getjTextFieldEmail() { return jTextFieldEmail; }
    public JFormattedTextField getjFormattedTextFieldFone1() { return jFormattedTextFieldFone1; }
    public JFormattedTextField getjFormattedTextFieldFone2() { return jFormattedTextFieldFone2; }
    public JFormattedTextField getjFormattedTextFieldCep() { return jFormattedTextFieldCep; }
    public JTextField getjTextFieldLogradouro() { return jTextFieldLogradouro; }
    public JTextField getjTextFieldBairro() { return jTextFieldBairro; }
    public JTextField getjTextFieldCidade() { return jTextFieldCidade; }
    public JTextField getjTextFieldComplemento() { return jTextFieldComplemento; }
    public JTextArea getjTextAreaObs() { return jTextAreaObs; }
}