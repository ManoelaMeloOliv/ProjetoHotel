package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;



public class SistemaHotelMenu extends JFrame {

    private JPanel contentPanel;

    public SistemaHotelMenu() {
        setTitle("Sistema de Gerenciamento Hoteleiro");
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        add(createSideMenu(), BorderLayout.WEST);

        contentPanel = createContentPanel();
        add(contentPanel, BorderLayout.CENTER);

        applyTheme();
    }

    private JPanel createSideMenu() {
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setPreferredSize(new Dimension(280, getHeight()));
        menu.setBackground(new Color(26, 35, 46));
        menu.setBorder(new EmptyBorder(20, 0, 20, 0));

      
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(new Color(26, 35, 46));
        header.setMaximumSize(new Dimension(280, 100));

        JLabel logo = new JLabel("🏨 HOTEL SYSTEM");
        logo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 26));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Gestão Hoteleira");
        subtitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        subtitle.setForeground(Color.WHITE);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(Box.createVerticalStrut(10));
        header.add(logo);
        header.add(Box.createVerticalStrut(5));
        header.add(subtitle);
        header.add(Box.createVerticalStrut(20));
        menu.add(header);

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(260, 1));
        sep.setForeground(new Color(45, 55, 70));
        menu.add(sep);
        menu.add(Box.createVerticalStrut(20));

       
        JPanel menuItemsPanel = new JPanel();
        menuItemsPanel.setLayout(new BoxLayout(menuItemsPanel, BoxLayout.Y_AXIS));
        menuItemsPanel.setBackground(new Color(26, 35, 46));

       
        menuItemsPanel.add(createMenuButton("🏠 Dashboard", "dashboard"));
        menuItemsPanel.add(Box.createVerticalStrut(5));
        menuItemsPanel.add(createMenuButton("👤 Hóspedes", "hospedes"));
        menuItemsPanel.add(Box.createVerticalStrut(5));
        menuItemsPanel.add(createMenuButton("🚗 Veículos", "veiculos"));
        menuItemsPanel.add(Box.createVerticalStrut(5));
        menuItemsPanel.add(createMenuButton("🛏️ Quartos", "quartos"));
        menuItemsPanel.add(Box.createVerticalStrut(5));
        menuItemsPanel.add(createMenuButton("🏷️ Modelo ", "modelo"));
        menuItemsPanel.add(Box.createVerticalStrut(5));
        menuItemsPanel.add(createMenuButton("🏢 Marca ", "marca"));
        menuItemsPanel.add(Box.createVerticalStrut(5));
        menuItemsPanel.add(createMenuButton("📋 Reservas", "reservas"));
        menuItemsPanel.add(Box.createVerticalStrut(5));
        menuItemsPanel.add(createMenuButton("🧾 Check-in/out", "checkin"));
        menuItemsPanel.add(Box.createVerticalStrut(5));
        menuItemsPanel.add(createMenuButton("🅿️ Estacionamento", "estacionamento"));
        menuItemsPanel.add(Box.createVerticalStrut(5));
        menuItemsPanel.add(createMenuButton("💰 Caixa", "caixa"));
        menuItemsPanel.add(Box.createVerticalStrut(5));
        menuItemsPanel.add(createMenuButton("🍽️ Serviços", "servicos"));
        menuItemsPanel.add(Box.createVerticalStrut(5));
        menuItemsPanel.add(createMenuButton("☕ Produtos Copa", "produtos"));
        menuItemsPanel.add(Box.createVerticalStrut(5));
        menuItemsPanel.add(createMenuButton("🏭 Fornecedores", "fornecedores"));
        menuItemsPanel.add(Box.createVerticalStrut(5));
        menuItemsPanel.add(createMenuButton("👔 Funcionários", "funcionarios"));

        JScrollPane scrollPane = new JScrollPane(menuItemsPanel);
        scrollPane.setBackground(new Color(26, 35, 46));
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(new Color(26, 35, 46));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        menu.add(scrollPane);
        menu.add(Box.createVerticalStrut(15));

        JSeparator sep2 = new JSeparator();
        sep2.setMaximumSize(new Dimension(260, 1));
        sep2.setForeground(new Color(45, 55, 70));
        menu.add(sep2);
        menu.add(Box.createVerticalStrut(15));

        JButton btnSair = createMenuButton("🚪 Sair", "exit");
        btnSair.setBackground(new Color(192, 57, 43));
        menu.add(btnSair);

        return menu;
    }

    private JButton createMenuButton(String text, String action) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(260, 48));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(37, 48, 63));
        btn.setBorder(new EmptyBorder(12, 20, 12, 20));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (action.equals("exit")) {
                    btn.setBackground(new Color(169, 50, 38));
                } else {
                    btn.setBackground(new Color(52, 152, 219));
                }
            }

            public void mouseExited(MouseEvent e) {
                if (action.equals("exit")) {
                    btn.setBackground(new Color(192, 57, 43));
                } else {
                    btn.setBackground(new Color(37, 48, 63));
                }
            }
        });

        btn.addActionListener(e -> handleAction(action));
        return btn;
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(236, 240, 241));

        JLabel titleLabel = new JLabel("Dashboard - Visão Geral");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.BLACK);

        JButton btnVoltar = new JButton("← Voltar");
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBackground(new Color(52, 73, 94));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setBorder(new EmptyBorder(8, 15, 8, 15));
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnVoltar.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnVoltar.setBackground(new Color(41, 128, 185));
            }

            public void mouseExited(MouseEvent e) {
                btnVoltar.setBackground(new Color(52, 73, 94));
            }
        });

        btnVoltar.addActionListener(e -> voltarInicio());

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(btnVoltar, BorderLayout.EAST);
        panel.add(headerPanel, BorderLayout.NORTH);

        JPanel cardsPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        cardsPanel.setBackground(new Color(236, 240, 241));
        cardsPanel.setBorder(new EmptyBorder(30, 0, 0, 0));

        cardsPanel.add(createDashCard("Quartos Ocupados", "45/80", new Color(52, 152, 219)));
        cardsPanel.add(createDashCard("Check-ins Hoje", "12", new Color(46, 204, 113)));
        cardsPanel.add(createDashCard("Reservas Ativas", "28", new Color(155, 89, 182)));
        cardsPanel.add(createDashCard("Receita Mês", "R$ 125.430", new Color(241, 196, 15)));
        cardsPanel.add(createDashCard("Hóspedes Ativos", "87", new Color(230, 126, 34)));
        cardsPanel.add(createDashCard("Taxa Ocupação", "56%", new Color(26, 188, 156)));

        panel.add(cardsPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createDashCard(String titulo, String valor, Color cor) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitulo.setForeground(Color.BLACK);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblValor.setForeground(cor);
        lblValor.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(lblTitulo);
        card.add(Box.createVerticalStrut(10));
        card.add(lblValor);

        return card;
    }

    private void handleAction(String action) {
        switch (action) {
            case "exit":
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Deseja sair do sistema?",
                        "Confirmar",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
                break;

            case "hospedes":
                TelaHospede telaHospede = new TelaHospede();
                controller.ControllerHospede controllerHospede = new controller.ControllerHospede(telaHospede);
                trocarTela(telaHospede, "Gestão de Hóspedes");
                break;
                
            case "veiculos":
                TelaVeiculo telaVeiculo = new TelaVeiculo();
                controller.ControllerVeiculo controllerVeiculo = new controller.ControllerVeiculo(telaVeiculo);
                trocarTela(telaVeiculo, "Gestão de Veículos");
                break;

            case "quartos":
                TelaQuarto telaQuarto = new TelaQuarto();
                controller.ControllerQuarto controllerQuarto = new controller.ControllerQuarto(telaQuarto);
                trocarTela(telaQuarto, "Gestão de Quartos");
                break;

            case "modelo":
                TelaModelo telaModelo = new TelaModelo();
                controller.ControllerModelo controllerModelo = new controller.ControllerModelo(telaModelo);
                trocarTela(telaModelo, "Gestão de Modelos ");
                break;

            case "marca":
                TelaMarca telaMarca = new TelaMarca();
                controller.ControllerMarca controllerMarca = new controller.ControllerMarca(telaMarca);
                trocarTela(telaMarca, "Gestão de Marcas ");
                break;

            case "estacionamento":
                TelaVagaEstacionamento telaVagaEstacionamento = new TelaVagaEstacionamento();
                controller.ControllerVagaEstacionamento controllerVagaEstacionamento = 
                    new controller.ControllerVagaEstacionamento(telaVagaEstacionamento);
                trocarTela(telaVagaEstacionamento, "Gestão de Vagas de Estacionamento");
                break;

            case "servicos":
                TelaServico telaServico = new TelaServico();
                controller.ControllerServico controllerServico = new controller.ControllerServico(telaServico);
                trocarTela(telaServico, "Gestão de Serviços");
                break;

            case "produtos":
                TelaProdutoCopa telaProdutoCopa = new TelaProdutoCopa();
                controller.ControllerProdutoCopa controllerProduto = new controller.ControllerProdutoCopa(telaProdutoCopa);
                trocarTela(telaProdutoCopa, "Gestão de Produtos da Copa");
                break;
                
            case "fornecedores":
                TelaFornecedor telaFornecedor = new TelaFornecedor();
                controller.ControllerFornecedor controllerFornecedor = new controller.ControllerFornecedor(telaFornecedor);
                trocarTela(telaFornecedor, "Gestão de Fornecedores");
                break;

            case "funcionarios":
                TelaFuncionario telaFuncionario = new TelaFuncionario();
                controller.ControllerFuncionario controllerFuncionario = new controller.ControllerFuncionario(telaFuncionario);
                trocarTela(telaFuncionario, "Gestão de Funcionários");
                break;
                
            case "dashboard":
                contentPanel.removeAll();

                JPanel headerPanel = new JPanel(new BorderLayout());
                headerPanel.setBackground(new Color(236, 240, 241));

                JLabel titleLabel = new JLabel("Dashboard - Visão Geral");
                titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
                titleLabel.setForeground(Color.BLACK);

                JButton btnVoltar = new JButton("← Voltar");
                btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 13));
                btnVoltar.setForeground(Color.WHITE);
                btnVoltar.setBackground(new Color(52, 73, 94));
                btnVoltar.setFocusPainted(false);
                btnVoltar.setBorder(new EmptyBorder(8, 15, 8, 15));
                btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));

                btnVoltar.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        btnVoltar.setBackground(new Color(41, 128, 185));
                    }

                    public void mouseExited(MouseEvent e) {
                        btnVoltar.setBackground(new Color(52, 73, 94));
                    }
                });

                btnVoltar.addActionListener(e -> voltarInicio());

                headerPanel.add(titleLabel, BorderLayout.WEST);
                headerPanel.add(btnVoltar, BorderLayout.EAST);
                contentPanel.add(headerPanel, BorderLayout.NORTH);

                JPanel cardsPanel = new JPanel(new GridLayout(2, 3, 20, 20));
                cardsPanel.setBackground(new Color(236, 240, 241));
                cardsPanel.setBorder(new EmptyBorder(30, 0, 0, 0));

                cardsPanel.add(createDashCard("Quartos Ocupados", "45/80", new Color(52, 152, 219)));
                cardsPanel.add(createDashCard("Check-ins Hoje", "12", new Color(46, 204, 113)));
                cardsPanel.add(createDashCard("Reservas Ativas", "28", new Color(155, 89, 182)));
                cardsPanel.add(createDashCard("Receita Mês", "R$ 125.430", new Color(241, 196, 15)));
                cardsPanel.add(createDashCard("Hóspedes Ativos", "87", new Color(230, 126, 34)));
                cardsPanel.add(createDashCard("Taxa Ocupação", "56%", new Color(26, 188, 156)));

                contentPanel.add(cardsPanel, BorderLayout.CENTER);

                contentPanel.revalidate();
                contentPanel.repaint();
                break;

            default:
                JOptionPane.showMessageDialog(this,
                        "Tela em desenvolvimento!",
                        "Info",
                        JOptionPane.INFORMATION_MESSAGE);
                break;
        }
    }

    private void trocarTela(JPanel novoPanel, String titulo) {
        contentPanel.removeAll();

        JLabel novoTitulo = new JLabel(titulo);
        novoTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        novoTitulo.setForeground(Color.BLACK);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(236, 240, 241));

        JButton btnVoltar = new JButton("← Voltar");
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBackground(new Color(52, 73, 94));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setBorder(new EmptyBorder(8, 15, 8, 15));
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnVoltar.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnVoltar.setBackground(new Color(41, 128, 185));
            }

            public void mouseExited(MouseEvent e) {
                btnVoltar.setBackground(new Color(52, 73, 94));
            }
        });

        btnVoltar.addActionListener(e -> voltarInicio());

        headerPanel.add(novoTitulo, BorderLayout.WEST);
        headerPanel.add(btnVoltar, BorderLayout.EAST);

        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(novoPanel, BorderLayout.CENTER);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void voltarInicio() {
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            TelaInicial inicial = new TelaInicial();
            inicial.setVisible(true);
        });
    }

    public void abrirModulo(String modulo) {
        handleAction(modulo);
    }

    private void applyTheme() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SistemaHotelMenu frame = new SistemaHotelMenu();
            frame.setVisible(true);
        });
    }
}