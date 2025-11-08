package utilities;

import java.awt.Component;
import javax.swing.*;

public class Utilities {

    public static void ativaDesativa(JPanel painel, boolean ativa) {
        for (Component componenteAtual : painel.getComponents()) {

            if (componenteAtual instanceof JButton) {
                if ("0".equals(((JButton) componenteAtual).getActionCommand())) {
                    componenteAtual.setEnabled(ativa);
                } else {
                    componenteAtual.setEnabled(!ativa);
                }

            } else if (componenteAtual instanceof JPanel) {
                ativaDesativa((JPanel) componenteAtual, ativa);

            } else if (componenteAtual instanceof JTabbedPane) {
                JTabbedPane abas = (JTabbedPane) componenteAtual;
                for (int i = 0; i < abas.getTabCount(); i++) {
                    Component compAba = abas.getComponentAt(i);
                    if (compAba instanceof JPanel) {
                        ativaDesativa((JPanel) compAba, ativa);
                    }
                }
            }
        }
    }

    public static void limpaComponentes(JPanel painel, boolean ativa) {
        for (Component componenteAtual : painel.getComponents()) {

            if (componenteAtual instanceof JTextField) {
                ((JTextField) componenteAtual).setText("");
                componenteAtual.setEnabled(ativa);

            } else if (componenteAtual instanceof JFormattedTextField) {
                ((JFormattedTextField) componenteAtual).setValue(null);
                componenteAtual.setEnabled(ativa);

            } else if (componenteAtual instanceof JComboBox) {
                ((JComboBox<?>) componenteAtual).setSelectedIndex(-1);
                componenteAtual.setEnabled(ativa);

            } else if (componenteAtual instanceof JCheckBox) {
                ((JCheckBox) componenteAtual).setSelected(false);
                componenteAtual.setEnabled(ativa);

            } else if (componenteAtual instanceof JPasswordField) {
                ((JPasswordField) componenteAtual).setText("");
                componenteAtual.setEnabled(ativa);

            } else if (componenteAtual instanceof JTextArea) {
                ((JTextArea) componenteAtual).setText("");
                componenteAtual.setEnabled(ativa);

            } else if (componenteAtual instanceof JScrollPane) {
                JScrollPane scroll = (JScrollPane) componenteAtual;
                Component view = scroll.getViewport().getView();

                if (view instanceof JTextArea) {
                    ((JTextArea) view).setText("");
                    view.setEnabled(ativa);

                } else if (view instanceof JPanel) {
                    limpaComponentes((JPanel) view, ativa);
                }

            } else if (componenteAtual instanceof JTabbedPane) {
                JTabbedPane abas = (JTabbedPane) componenteAtual;
                for (int i = 0; i < abas.getTabCount(); i++) {
                    Component compAba = abas.getComponentAt(i);
                    if (compAba instanceof JPanel) {
                        limpaComponentes((JPanel) compAba, ativa);
                    }
                }

            } else if (componenteAtual instanceof JPanel) {
                limpaComponentes((JPanel) componenteAtual, ativa);
            }
        }
    }
}
