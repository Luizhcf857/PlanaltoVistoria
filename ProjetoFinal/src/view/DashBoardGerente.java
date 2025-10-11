package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import controller.FuncionarioController;
import model.Funcionario;

public class DashBoardGerente extends JFrame {

    private static final long serialVersionUID = 1L;
    private final Funcionario usuarioAtual;
    private final FuncionarioController funcionarioCtrl;

    private JPanel painelCards;
    private CardLayout layoutCards;

    private JTable tableFuncionarios;
    private DefaultTableModel modelTabelaFuncionarios;
    private List<Funcionario> funcionarios;

    public DashBoardGerente(Funcionario usuario) {
        this.usuarioAtual = usuario;
        this.funcionarioCtrl = new FuncionarioController();

        configurarJanela();
        inicializarLayout();
        carregarFuncionarios();

        setVisible(true);
    }

    private void configurarJanela() {
        setTitle("Painel do Gerente - DF Vistoria");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void inicializarLayout() {
        JPanel menuLateral = criarMenuLateral();

        layoutCards = new CardLayout();
        painelCards = new JPanel(layoutCards);
        painelCards.setBorder(new EmptyBorder(30, 30, 30, 30));
        painelCards.setBackground(new Color(245, 245, 245));

        painelCards.add(criarPainelResumo(), "Resumo");
        painelCards.add(criarPainelFuncionarios(), "Funcionarios");
        painelCards.add(criarPainelRelatorio(), "Relatorio");

        add(menuLateral, BorderLayout.WEST);
        add(painelCards, BorderLayout.CENTER);
    }

    private JPanel criarMenuLateral() {
        JPanel menu = new JPanel();
        menu.setBackground(new Color(25, 118, 210));
        menu.setPreferredSize(new Dimension(220, 0));
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("DF Vistoria", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnResumo = criarBotaoMenu("Dashboard");
        JButton btnFuncionarios = criarBotaoMenu("Funcionários");
        JButton btnRelat = criarBotaoMenu("Relatórios");
        JButton btnSair = criarBotaoMenu("Sair");

        btnResumo.addActionListener(e -> layoutCards.show(painelCards, "Resumo"));
        btnFuncionarios.addActionListener(e -> {
            carregarFuncionarios();
            layoutCards.show(painelCards, "Funcionarios");
        });
        btnRelat.addActionListener(e -> layoutCards.show(painelCards, "Relatorio"));
        btnSair.addActionListener(e -> dispose());

        menu.add(titulo);
        menu.add(Box.createVerticalStrut(50));
        menu.add(btnResumo);
        menu.add(Box.createVerticalStrut(15));
        menu.add(btnFuncionarios);
        menu.add(Box.createVerticalStrut(15));
        menu.add(btnRelat);
        menu.add(Box.createVerticalGlue());
        menu.add(btnSair);

        return menu;
    }

    private JButton criarBotaoMenu(String texto) {
        JButton botao = new JButton(texto);
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao.setMaximumSize(new Dimension(200, 40));
        botao.setBackground(new Color(21, 101, 192));
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 15));
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                botao.setBackground(new Color(30, 136, 229));
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                botao.setBackground(new Color(21, 101, 192));
            }
        });
        return botao;
    }

    private JPanel criarPainelResumo() {
        JPanel painel = new JPanel(new GridLayout(1, 3, 20, 0));
        painel.setBackground(new Color(245, 245, 245));

        // Você pode colocar aqui cards com estatísticas do sistema (ex: total funcionários, total agendamentos, etc)
        painel.add(criarCard("Total Funcionários", new Color(76, 175, 80), String.valueOf(funcionarioCtrl.ListarFuncionarios().size())));
        painel.add(criarCard("Funcionários Ativos", new Color(33, 150, 243), "N/A"));
        painel.add(criarCard("Funcionários Inativos", new Color(244, 67, 54), "N/A"));

        return painel;
    }

    private JPanel criarCard(String titulo, Color corTitulo, String valor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(25, 25, 25, 25)
        ));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(corTitulo);

        JLabel lblValor = new JLabel(valor, SwingConstants.CENTER);
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 48));
        lblValor.setForeground(new Color(33, 150, 243));

        card.add(lblTitulo, BorderLayout.NORTH);
        card.add(lblValor, BorderLayout.CENTER);

        JPanel container = new JPanel(new BorderLayout());
        container.add(card, BorderLayout.CENTER);

        return container;
    }

    private JPanel criarPainelFuncionarios() {
        JPanel painel = new JPanel(new BorderLayout(15, 15));
        painel.setBackground(new Color(245, 245, 245));

        JLabel titulo = new JLabel("Gerenciamento de Funcionários", SwingConstants.LEFT);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        painel.add(titulo, BorderLayout.NORTH);

        String[] colunas = {"ID", "Nome", "CPF", "Cargo", "Telefone"};
        modelTabelaFuncionarios = new DefaultTableModel(colunas, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tableFuncionarios = new JTable(modelTabelaFuncionarios);
        tableFuncionarios.setRowHeight(30);
        tableFuncionarios.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(tableFuncionarios);
        painel.add(scroll, BorderLayout.CENTER);

        JPanel botoes = new JPanel();
        botoes.setBackground(new Color(245, 245, 245));

        JButton btnAdicionar = new JButton("Adicionar");
        btnAdicionar.setBackground(new Color(66, 133, 244));
        btnAdicionar.setForeground(Color.WHITE);
        btnAdicionar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnAdicionar.addActionListener(e -> abrirFormularioFuncionario(null));

        JButton btnEditar = new JButton("Editar");
        btnEditar.setBackground(new Color(255, 193, 7));
        btnEditar.setForeground(Color.BLACK);
        btnEditar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnEditar.addActionListener(e -> {
            int row = tableFuncionarios.getSelectedRow();
            if (row >= 0) {
                Funcionario f = funcionarios.get(row);
                abrirFormularioFuncionario(f);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um funcionário para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setBackground(new Color(244, 67, 54));
        btnExcluir.setForeground(Color.WHITE);
        btnExcluir.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        btnExcluir.addActionListener(e -> {
            int row = tableFuncionarios.getSelectedRow();
            if (row >= 0) {
                Funcionario f = funcionarios.get(row);
                int opc = JOptionPane.showConfirmDialog(this,
                        "Tem certeza que deseja excluir o funcionário " + f.getNome() + "?",
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION);

                if (opc == JOptionPane.YES_OPTION) {
                    boolean sucesso = funcionarioCtrl.excluirFuncionarioComVerificacao(f.getId_Funcionario());
                    if (sucesso) {
                        JOptionPane.showMessageDialog(this, "Funcionário excluído com sucesso!");
                        carregarFuncionarios();
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Não é possível excluir este funcionário, pois ele está vinculado a outros registros.",
                                "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um funcionário para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });


        
        botoes.add(btnAdicionar);
        botoes.add(btnEditar);
        botoes.add(btnExcluir);

        painel.add(botoes, BorderLayout.SOUTH);

        return painel;
    }

    private JPanel criarPainelRelatorio() {
        JPanel painel = new JPanel();
        painel.setBackground(Color.LIGHT_GRAY);
        painel.add(new JLabel("Relatório de Funcionários (em construção...)"));
        return painel;
    }

    private void carregarFuncionarios() {
        modelTabelaFuncionarios.setRowCount(0);
        funcionarios = funcionarioCtrl.ListarFuncionarios();

        for (Funcionario f : funcionarios) {
            modelTabelaFuncionarios.addRow(new Object[]{
                    f.getId_Funcionario(),
                    f.getNome(),
                    f.getCpf(),
                    f.getCargo(),
                    f.getTelefone()
            });
        }
    }

    private void abrirFormularioFuncionario(Funcionario funcionario) {
        JTextField txtNome = new JTextField();
        JTextField txtCpf = new JTextField();
        JTextField txtCargo = new JTextField();
        JTextField txtTelefone = new JTextField();

        if (funcionario != null) {
            txtNome.setText(funcionario.getNome());
            txtCpf.setText(funcionario.getCpf());
            txtCargo.setText(funcionario.getCargo());
            txtTelefone.setText(funcionario.getTelefone());
        }

        JPanel form = new JPanel(new GridLayout(8, 1, 5, 5));
        form.setPreferredSize(new Dimension(350, 250));

        form.add(new JLabel("Nome:"));
        form.add(txtNome);
        form.add(new JLabel("CPF:"));
        form.add(txtCpf);
        form.add(new JLabel("Cargo:"));
        form.add(txtCargo);
        form.add(new JLabel("Telefone:"));
        form.add(txtTelefone);

        int opc = JOptionPane.showConfirmDialog(this, form, 
                (funcionario == null ? "Adicionar Funcionário" : "Editar Funcionário"), 
                JOptionPane.OK_CANCEL_OPTION);

        if (opc == JOptionPane.OK_OPTION) {
            Funcionario f = funcionario != null ? funcionario : new Funcionario();
            f.setNome(txtNome.getText().trim());
            f.setCpf(txtCpf.getText().trim());
            f.setCargo(txtCargo.getText().trim());
            f.setTelefone(txtTelefone.getText().trim());

            try {
                if (funcionario == null) {
                    funcionarioCtrl.cadastrarFuncionario(f);
                    JOptionPane.showMessageDialog(this, "Funcionário adicionado com sucesso!");
                } else {
                    funcionarioCtrl.atualizarFuncionario(f);
                    JOptionPane.showMessageDialog(this, "Funcionário atualizado com sucesso!");
                }
                carregarFuncionarios();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar funcionário: " + e.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DashBoardGerente(new Funcionario()));
    }
}
