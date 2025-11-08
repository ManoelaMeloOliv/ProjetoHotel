package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicButtonUI;

public class TelaInicial extends JFrame {
    
    public TelaInicial() {
        setTitle("Sistema de Gerenciamento Hoteleiro");
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        applyTheme();
        initComponents();
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
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);
        add(createFooterPanel(), BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(26, 35, 46));
        panel.setBorder(new EmptyBorder(25, 40, 25, 40));
        
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        leftPanel.setBackground(new Color(26, 35, 46));
        
        JLabel logoLabel = new JLabel("🏨");
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        
        JLabel titleLabel = new JLabel("HOTEL SYSTEM");
        titleLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        
        leftPanel.add(logoLabel);
        leftPanel.add(titleLabel);
        panel.add(leftPanel, BorderLayout.WEST);
        
        return panel;
    }
    
    private JPanel createMainContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(new EmptyBorder(40, 40, 40, 40));
        
        JLabel welcomeLabel = new JLabel("Bem-vindo ao Sistema de Gerenciamento Hoteleiro");
        welcomeLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 26));
        welcomeLabel.setForeground(new Color(52, 73, 94));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        panel.add(welcomeLabel, BorderLayout.NORTH);
        
        JPanel cardsPanel = new JPanel(new GridLayout(3, 3, 20, 20));
        cardsPanel.setBackground(new Color(236, 240, 241));
        cardsPanel.setBorder(new EmptyBorder(30, 20, 30, 20));
        
        cardsPanel.add(createCard("👥 Hóspedes", "Gerenciar cadastro de hóspedes", new Color(52, 152, 219), "hospedes"));
        cardsPanel.add(createCard("🚗 Veículos", "Gerenciar veículos", new Color(46, 204, 113), "veiculos"));
        cardsPanel.add(createCard("🛏️ Quartos", "Gerenciar quartos", new Color(155, 89, 182), "quartos"));
        cardsPanel.add(createCard("📋 Reservas", "Gerenciar reservas", new Color(241, 196, 15), "reservas"));
        cardsPanel.add(createCard("🧾 Check-in/out", "Check-in e Check-out", new Color(230, 126, 34), "checkin"));
        cardsPanel.add(createCard("💰 Caixa", "Gestão financeira", new Color(26, 188, 156), "caixa"));
        cardsPanel.add(createCard("🍽️ Serviços", "Serviços adicionais", new Color(231, 76, 60), "servicos"));
        cardsPanel.add(createCard("👔 Funcionários", "Gerenciar funcionários", new Color(52, 73, 94), "funcionarios"));
        cardsPanel.add(createCard("📊 Dashboard", "Visão geral", new Color(149, 165, 166), "dashboard"));
        
        panel.add(cardsPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
  
    private JPanel createCard(String titulo, String descricao, Color cor, String modulo) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 220, 220), 1, true),
            new EmptyBorder(20, 20, 20, 20)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 18));
        lblTitulo.setForeground(cor);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblDesc = new JLabel("<html><center>" + descricao + "</center></html>");
        lblDesc.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
        lblDesc.setForeground(new Color(52, 73, 94));
        lblDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton btnAcessar = createCustomButton("Acessar", cor);
        btnAcessar.addActionListener(e -> abrirSistema(modulo));
        
        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(250, 250, 250));
                card.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(cor, 2, true),
                    new EmptyBorder(19, 19, 19, 19)
                ));
            }
            
            public void mouseExited(MouseEvent e) {
                card.setBackground(Color.WHITE);
                card.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(220, 220, 220), 1, true),
                    new EmptyBorder(20, 20, 20, 20)
                ));
            }
            
            public void mouseClicked(MouseEvent e) {
                abrirSistema(modulo);
            }
        });
        
        card.add(Box.createVerticalGlue());
        card.add(lblTitulo);
        card.add(Box.createVerticalStrut(10));
        card.add(lblDesc);
        card.add(Box.createVerticalStrut(15));
        card.add(btnAcessar);
        card.add(Box.createVerticalGlue());
        
        return card;
    }
 
    private JButton createCustomButton(String texto, Color cor) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                g2d.setColor(getBackground().darker());
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 10, 10);
                
                g2d.dispose();
                
                super.paintComponent(g);
            }
        };
        
        btn.setText(texto);
        btn.setFont(new Font("Segoe UI Emoji", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(cor);
        
        btn.setUI(new BasicButtonUI());
        
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        Dimension size = new Dimension(120, 38);
        btn.setPreferredSize(size);
        btn.setMinimumSize(size);
        btn.setMaximumSize(size);
        
        final Color corOriginal = cor;
        final Color corHover = cor.darker();
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(corHover);
                btn.repaint();
            }
            
            public void mouseExited(MouseEvent e) {
                btn.setBackground(corOriginal);
                btn.repaint();
            }
        });
        
        return btn;
    }
    
    private JPanel createFooterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(26, 35, 46));
        panel.setBorder(new EmptyBorder(15, 40, 15, 40));
        
        JLabel lblVersao = new JLabel("Sistema Hoteleiro v1.0");
        lblVersao.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
        lblVersao.setForeground(Color.WHITE);
        
        JButton btnSair = createCustomButton("Sair", new Color(192, 57, 43));
        btnSair.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Deseja sair do sistema?", 
                "Confirmar", 
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        
        panel.add(lblVersao, BorderLayout.WEST);
        panel.add(btnSair, BorderLayout.EAST);
        
        return panel;
    }
    
    private void abrirSistema(String modulo) {
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            SistemaHotelMenu sistema = new SistemaHotelMenu();
            sistema.setVisible(true);
            sistema.abrirModulo(modulo);
        });
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaInicial frame = new TelaInicial();
            frame.setVisible(true);
        });
    }
}