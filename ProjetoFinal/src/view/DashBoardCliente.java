package view;

import model.Cliente;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DashBoardCliente extends JFrame {

    private Cliente cliente;
    private JPanel painelPrincipal;
    private CardLayout cardLayout;

    public DashBoardCliente(Cliente cliente) {
        this.cliente = cliente;
        setTitle("Dashboard Cliente - " + cliente.getNome());
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        // Painel principal com CardLayout
        painelPrincipal = new JPanel();
        cardLayout = new CardLayout();
        painelPrincipal.setLayout(cardLayout);

        // Painel de boas-vindas
        JPanel painelHome = new JPanel();
        painelHome.setLayout(new BorderLayout());
        JLabel lblBoasVindas = new JLabel("Bem-vindo(a), " + cliente.getNome() + "!", SwingConstants.CENTER);
        lblBoasVindas.setFont(new Font("Arial", Font.BOLD, 24));
        painelHome.add(lblBoasVindas, BorderLayout.CENTER);

        // Adicionar painelHome ao painelPrincipal
        painelPrincipal.add(painelHome, "home");

        // Painel de informações do cliente
        JPanel painelInfo = new JPanel();
        painelInfo.setLayout(new GridLayout(5, 1, 10, 10));
        painelInfo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelInfo.add(new JLabel("ID: " + cliente.getId_Cliente()));
        painelInfo.add(new JLabel("Nome: " + cliente.getNome()));
        painelInfo.add(new JLabel("CPF: " + cliente.getCpf()));
        painelInfo.add(new JLabel("Email: " + cliente.getEmail()));
        painelInfo.add(new JLabel("Telefone: " + cliente.getTelefone()));

        painelPrincipal.add(painelInfo, "info");

        // Menu lateral
        JPanel menuLateral = new JPanel();
        menuLateral.setLayout(new GridLayout(0, 1, 10, 10));
        menuLateral.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JButton btnHome = new JButton("Home");
        JButton btnInfo = new JButton("Meus Dados");
        JButton btnSair = new JButton("Sair");

        menuLateral.add(btnHome);
        menuLateral.add(btnInfo);
        menuLateral.add(btnSair);

        // Ações dos botões
        btnHome.addActionListener(e -> cardLayout.show(painelPrincipal, "home"));
        btnInfo.addActionListener(e -> cardLayout.show(painelPrincipal, "info"));
        btnSair.addActionListener(e -> {
            dispose();
            //new TelaLogin().setVisible(true);
        });

        // Layout principal
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(menuLateral, BorderLayout.WEST);
        getContentPane().add(painelPrincipal, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        // Exemplo para teste
        Cliente teste = new Cliente(1, "João Silva", "12345678901", "999999999", "joao@email.com");
        SwingUtilities.invokeLater(() -> new DashBoardCliente(teste).setVisible(true));
    }
}
