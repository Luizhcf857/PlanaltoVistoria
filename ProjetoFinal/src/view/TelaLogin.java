package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import dao.AutenticacaoDAO;
import model.Cliente;

public class TelaLogin extends JPanel {

    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private final Random rand = new Random();

    public TelaLogin(JPanel principal, CardLayout card) {
        setLayout(new GridBagLayout());

        // Fundo estrelado
        JPanel painelFundo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fundo preto
                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Estrelas lentas
                drawStars(g2d, getWidth(), getHeight());
            }

            private void drawStars(Graphics2D g2d, int width, int height) {
                g2d.setColor(Color.WHITE);
                int quantidade = 30;
                for (int i = 0; i < quantidade; i++) {
                    int x = rand.nextInt(width);
                    int y = rand.nextInt(height - 80);
                    float opacity = (float) (0.1 + 0.9 * Math.abs(Math.sin(System.currentTimeMillis() / (8000.0 + i * 20))));
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                    g2d.fillOval(x, y, rand.nextInt(2) + 2, rand.nextInt(2) + 2);
                }
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            }
        };
        painelFundo.setLayout(new GridBagLayout());
        painelFundo.setOpaque(false);

        Timer timer = new Timer(250, e -> painelFundo.repaint());
        timer.start();

        // Painel branco com o formulário (layout da primeira tela)
        JPanel painelForm = new JPanel();
        painelForm.setPreferredSize(new Dimension(320, 300));
        painelForm.setBackground(Color.WHITE);
        painelForm.setLayout(new BoxLayout(painelForm, BoxLayout.Y_AXIS));
        painelForm.setBorder(new EmptyBorder(35, 35, 35, 35));

        JLabel lblLogin = new JLabel("Login");
        lblLogin.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblLogin.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblLogin.setForeground(new Color(26, 26, 46));
        painelForm.add(lblLogin);
        painelForm.add(Box.createVerticalStrut(22));

        // Label para Email
        JLabel lblEmailField = new JLabel("E-mail:");
        lblEmailField.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lblEmailField.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblEmailField.setForeground(new Color(60, 60, 80));
        painelForm.add(lblEmailField);

        txtEmail = new JTextField();
        txtEmail.setFont(new Font("SansSerif", Font.PLAIN, 15));
        txtEmail.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        txtEmail.setBackground(new Color(238, 238, 238));
        txtEmail.setBorder(new EmptyBorder(8, 14, 8, 14));
        txtEmail.setToolTipText("Digite seu e-mail");
        txtEmail.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelForm.add(txtEmail);
        painelForm.add(Box.createVerticalStrut(17));

        // Label para Senha
        JLabel lblSenhaField = new JLabel("Senha:");
        lblSenhaField.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lblSenhaField.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblSenhaField.setForeground(new Color(60, 60, 80));
        painelForm.add(lblSenhaField);

        txtSenha = new JPasswordField();
        txtSenha.setFont(new Font("SansSerif", Font.PLAIN, 15));
        txtSenha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        txtSenha.setBackground(new Color(238, 238, 238));
        txtSenha.setBorder(new EmptyBorder(8, 14, 8, 14));
        txtSenha.setToolTipText("Digite sua senha");
        txtSenha.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelForm.add(txtSenha);
        painelForm.add(Box.createVerticalStrut(24));

        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.setBackground(new Color(26, 26, 46));
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFont(new Font("SansSerif", Font.BOLD, 15));
        btnEntrar.setFocusPainted(false);
        btnEntrar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnEntrar.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelForm.add(btnEntrar);

        // Alinhar painelForm à esquerda
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 50, 0, 0);
        painelFundo.add(painelForm, gbc);

        GridBagConstraints gbcRoot = new GridBagConstraints();
        gbcRoot.gridx = 0; gbcRoot.gridy = 0;
        gbcRoot.anchor = GridBagConstraints.CENTER;
        gbcRoot.fill = GridBagConstraints.BOTH;
        gbcRoot.weightx = 1.0; gbcRoot.weighty = 1.0;
        add(painelFundo, gbcRoot);

        // -------------------- Lógica da segunda tela --------------------
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
                        // Cria o dashboard do cliente apenas após o login
                        DashBoardCliente telaCliente = new DashBoardCliente(principal, card);
                        principal.add(telaCliente, "telaCliente");
                        card.show(principal, "telaCliente");
                        break;
                }
            }
        });
    }
}
