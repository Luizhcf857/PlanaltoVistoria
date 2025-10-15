package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import dao.AutenticacaoDAO;
import images.loguin.Imagens;

public class TelaLogin extends JPanel {
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private final Random rand = new Random();

    public TelaLogin(JPanel principal, CardLayout card) {
        setLayout(new GridBagLayout());

        // Painel de fundo estrelado
        JPanel painelFundo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                drawStars(g2d, getWidth(), getHeight());
            }
            private void drawStars(Graphics2D g2d, int width, int height) {
                g2d.setColor(Color.WHITE);
                int quantidade = 35;
                for (int i = 0; i < quantidade; i++) {
                    int x = rand.nextInt(width);
                    int y = rand.nextInt(height);
                    float opacity = (float) (0.15 + 0.5 * Math.abs(Math.sin(System.currentTimeMillis() / (6000.0 + i * 20))));
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

        // Painel vertical para a imagem + login
        JPanel painelVertical = new JPanel();
        painelVertical.setLayout(new BoxLayout(painelVertical, BoxLayout.Y_AXIS));
        painelVertical.setOpaque(false);

        // Imagem do carro acima do login
        JLabel lblCarro = new JLabel();
        lblCarro.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Usa a classe utilitária de imagens
        if (images.loguin.Imagens.CARRO_GRANDE != null) {
            lblCarro.setIcon(images.loguin.Imagens.CARRO_GRANDE);
        } else {
            lblCarro.setText("carro");
            lblCarro.setForeground(Color.WHITE);
        }

        painelVertical.add(lblCarro);
        painelVertical.add(Box.createVerticalStrut(10));
        
        // Painel do formulário (card de login)
        JPanel painelForm = new JPanel();
        painelForm.setPreferredSize(new Dimension(340, 350));
        painelForm.setBackground(new Color(36, 36, 46, 200));
        painelForm.setLayout(new BoxLayout(painelForm, BoxLayout.Y_AXIS));
        painelForm.setBorder(new EmptyBorder(30, 32, 32, 32));

        // Título do Login
        JLabel lblLogin = new JLabel("LOGIN");
        lblLogin.setFont(new Font("SansSerif", Font.BOLD, 32));
        lblLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblLogin.setForeground(new Color(255, 235, 120));
        painelForm.add(lblLogin);
        painelForm.add(Box.createVerticalStrut(22));

        // Campo Email
        txtEmail = new JTextField("E-mail");
        txtEmail.setFont(new Font("SansSerif", Font.BOLD, 15));
        txtEmail.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        txtEmail.setBackground(new Color(26, 26, 36));
        txtEmail.setForeground(Color.WHITE);
        txtEmail.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 80), 1, true),
            new EmptyBorder(10, 12, 10, 12)
        ));
        txtEmail.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtEmail.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (txtEmail.getText().equals("E-mail")) txtEmail.setText("");
            }
            public void focusLost(FocusEvent e) {
                if (txtEmail.getText().isEmpty()) txtEmail.setText("E-mail");
            }
        });
        painelForm.add(txtEmail);
        painelForm.add(Box.createVerticalStrut(14));

        // Campo Senha
        txtSenha = new JPasswordField("Senha");
        txtSenha.setFont(new Font("SansSerif", Font.BOLD, 15));
        txtSenha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        txtSenha.setBackground(new Color(26, 26, 36));
        txtSenha.setForeground(Color.WHITE);
        txtSenha.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 80), 1, true),
            new EmptyBorder(10, 12, 10, 12)
        ));
        txtSenha.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtSenha.setEchoChar((char)0);
        txtSenha.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (String.valueOf(txtSenha.getPassword()).equals("Senha")) {
                    txtSenha.setText("");
                    txtSenha.setEchoChar('•');
                }
            }
            public void focusLost(FocusEvent e) {
                if (String.valueOf(txtSenha.getPassword()).isEmpty()) {
                    txtSenha.setText("Senha");
                    txtSenha.setEchoChar((char)0);
                }
            }
        });
        painelForm.add(txtSenha);
        painelForm.add(Box.createVerticalStrut(22));

        // Botão Entrar
        JButton btnEntrar = new JButton("  LOGIN");
        btnEntrar.setBackground(new Color(255, 235, 120));
        btnEntrar.setForeground(new Color(26, 26, 46));
        btnEntrar.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnEntrar.setFocusPainted(false);
        btnEntrar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btnEntrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEntrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnEntrar.setBorder(BorderFactory.createEmptyBorder(7, 10, 7, 10));
        try {
            btnEntrar.setIcon(new ImageIcon(getClass().getResource("/lock.png")));
        } catch (Exception e) {}
        painelForm.add(btnEntrar);

        // [REMOVIDO SIGN UP]

        painelVertical.add(painelForm);

        // Centraliza tudo na tela
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        painelFundo.add(painelVertical, gbc);

        // Adiciona ao painel principal
        GridBagConstraints gbcRoot = new GridBagConstraints();
        gbcRoot.gridx = 0;
        gbcRoot.gridy = 0;
        gbcRoot.anchor = GridBagConstraints.CENTER;
        gbcRoot.fill = GridBagConstraints.BOTH;
        gbcRoot.weightx = 1.0;
        gbcRoot.weighty = 1.0;
        add(painelFundo, gbcRoot);

        // Lógica do botão de login
        btnEntrar.addActionListener(e -> {
            String email = txtEmail.getText().trim();
            String senha = new String(txtSenha.getPassword()).trim();
            if (email.equals("E-mail") || email.isEmpty() || senha.equals("Senha") || senha.isEmpty()) {
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
                        DashBoardCliente telaCliente = new DashBoardCliente(principal, card);
                        principal.add(telaCliente, "dashboardCliente");
                        card.show(principal, "dashboardCliente");
                        break;
                }
            }
        });
    }
}