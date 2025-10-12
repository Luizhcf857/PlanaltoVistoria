package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import dao.AutenticacaoDAO;

public class TelaLogin extends JPanel {

    private JTextField txtEmail;
    private JPasswordField txtSenha;

    public TelaLogin(JPanel principal, CardLayout card) {
        setLayout(new BorderLayout());

        // Painel azul à esquerda
        JPanel painelEsquerdo = new JPanel();
        painelEsquerdo.setBackground(new Color(0, 102, 204));
        painelEsquerdo.setPreferredSize(new Dimension(300, 0));
        painelEsquerdo.setLayout(new GridBagLayout());

        JLabel titulo = new JLabel("<html><center><b>Bem-vindo ao Sistema</b><br>de Gestão</center></html>");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        painelEsquerdo.add(titulo);

        // Painel branco à direita
        JPanel painelDireito = new JPanel();
        painelDireito.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblLogin = new JLabel("LOGIN");
        lblLogin.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        painelDireito.add(lblLogin, gbc);

        gbc.gridwidth = 1;

        JLabel lblEmail = new JLabel("E-mail:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        painelDireito.add(lblEmail, gbc);

        txtEmail = new JTextField(20);
        gbc.gridx = 1;
        painelDireito.add(txtEmail, gbc);

        JLabel lblSenha = new JLabel("Senha:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        painelDireito.add(lblSenha, gbc);

        txtSenha = new JPasswordField(20);
        gbc.gridx = 1;
        painelDireito.add(txtSenha, gbc);

        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.setBackground(new Color(0, 102, 204));
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        painelDireito.add(btnEntrar, gbc);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFocusPainted(false);
        gbc.gridy = 4;
        painelDireito.add(btnCancelar, gbc);

        add(painelEsquerdo, BorderLayout.WEST);
        add(painelDireito, BorderLayout.CENTER);

        // Ação dos botões
        btnEntrar.addActionListener(e -> {
            String email = txtEmail.getText().trim();
            String senha = new String(txtSenha.getPassword()).trim();

            if (email.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String tipoUsuario = AutenticacaoDAO.autenticar(email, senha);

            if (tipoUsuario == null) {
                JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                switch (tipoUsuario) {
                    case "gerente":
                        card.show(principal, "telaGerente");
                        break;
                    case "vistoriador":
                        card.show(principal, "telaVistoriador");
                        break;
                    case "cliente":
                        card.show(principal, "dashboardCliente");
                        break;
                }
            }
        });

        btnCancelar.addActionListener(e -> {
            txtEmail.setText("");
            txtSenha.setText("");
        });
    }
}
