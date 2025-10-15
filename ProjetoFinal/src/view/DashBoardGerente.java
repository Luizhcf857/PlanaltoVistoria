package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;
import model.PagamentoController;
import dao.PagamentoDAO;

import controller.FuncionarioController;
import model.Funcionario;

public class DashBoardGerente extends JPanel {

    private final Funcionario usuarioAtual;
    private final FuncionarioController funcionarioCtrl;

    private JPanel painelCards;
    private CardLayout layoutCards;

    private JTable tableFuncionarios;
    private DefaultTableModel modelTabelaFuncionarios;
    private List<Funcionario> funcionarios;

    private JLabel lblTotal;
    private JLabel lblAtivos;
    private JLabel lblInativos;

    public DashBoardGerente(Funcionario usuario, JPanel painelPrincipal, CardLayout cardPrincipal) {
        this.usuarioAtual = usuario;
        this.funcionarioCtrl = new FuncionarioController();

        setLayout(new BorderLayout());
        inicializarLayout();
        carregarFuncionarios();
    }

    private void inicializarLayout() {
        JPanel barraSuperior = criarBarraSuperior();

        layoutCards = new CardLayout();
        painelCards = new JPanel(layoutCards);
        painelCards.setBorder(new EmptyBorder(20, 20, 20, 20));
        painelCards.setBackground(new Color(48, 48, 48));

        painelCards.add(criarPainelResumo(), "Resumo");
        painelCards.add(criarPainelFuncionarios(), "Funcionarios");
        painelCards.add(criarPainelRelatorioFinanceiro(), "RelatorioFinanceiro");

        add(barraSuperior, BorderLayout.NORTH);
        add(painelCards, BorderLayout.CENTER);
    }

    private JPanel criarBarraSuperior() {
        JPanel barra = new JPanel(new BorderLayout());
        barra.setBackground(new Color(18, 18, 18));
        barra.setPreferredSize(new Dimension(0, 60));
        barra.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(38, 50, 56)));

        JLabel logo = new JLabel("PLANALTO VISTORIA", SwingConstants.LEFT);
        Color dourada = new Color(0xD4AF37);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        logo.setForeground(dourada);
        logo.setBorder(new EmptyBorder(0, 20, 0, 0));

        JPanel menuBotoes = new JPanel();
        menuBotoes.setOpaque(false);
        menuBotoes.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        JButton btnResumo = criarBotaoMenu("Dashboard");
        JButton btnFuncionarios = criarBotaoMenu("Funcionários");
        //JButton btnRelat = criarBotaoMenu("Relatórios");
        JButton btnSair = criarBotaoMenu("Sair");
        
        JButton btnFinanceiro = criarBotaoMenu("Financeiro");
        btnFinanceiro.addActionListener(e -> layoutCards.show(painelCards, "RelatorioFinanceiro"));
        menuBotoes.add(btnFinanceiro);

        btnResumo.addActionListener(e -> layoutCards.show(painelCards, "Resumo"));
        btnFuncionarios.addActionListener(e -> {
            carregarFuncionarios();
            layoutCards.show(painelCards, "Funcionarios");
        });

        btnSair.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente sair?", "Sair", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) System.exit(0);
        });

        menuBotoes.add(btnResumo);
        menuBotoes.add(btnFuncionarios);
        

        JPanel painelUsuario = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        painelUsuario.setOpaque(false);
        JLabel lblUsuario = new JLabel("Usuário: " + usuarioAtual.getNome());
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUsuario.setForeground(Color.WHITE);
        painelUsuario.add(lblUsuario);
        painelUsuario.add(btnSair);

        barra.add(logo, BorderLayout.WEST);
        barra.add(menuBotoes, BorderLayout.CENTER);
        barra.add(painelUsuario, BorderLayout.EAST);

        return barra;
    }

    private JButton criarBotaoMenu(String texto) {
        JButton botao = new JButton(texto);
        botao.setFocusPainted(false);
        botao.setForeground(Color.WHITE);
        botao.setBackground(new Color(33, 33, 33));
        botao.setFont(new Font("Segoe UI", Font.BOLD, 15));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                botao.setBackground(new Color(55, 71, 79));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                botao.setBackground(new Color(33, 33, 33));
            }
        });

        return botao;
    }

    private JPanel criarPainelResumo() {
        JPanel painel = new JPanel(new GridLayout(1, 3, 20, 0));
        painel.setBackground(new Color(48, 48, 48));

        lblTotal = criarCard("Total Funcionários", new Color(76, 175, 80), String.valueOf(funcionarioCtrl.ListarFuncionarios().size()));
        lblAtivos = criarCard("Funcionários Ativos", new Color(33, 150, 243), "N/A");
        lblInativos = criarCard("Funcionários Inativos", new Color(244, 67, 54), "N/A");

        painel.add(lblTotal.getParent());
        painel.add(lblAtivos.getParent());
        painel.add(lblInativos.getParent());

        return painel;
    }

    private JLabel criarCard(String titulo, Color corTitulo, String valor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(60, 63, 65));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(90, 90, 90), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(corTitulo);

        JLabel lblValor = new JLabel(valor, SwingConstants.CENTER);
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 42));
        lblValor.setForeground(new Color(3, 169, 244));

        card.add(lblTitulo, BorderLayout.NORTH);
        card.add(lblValor, BorderLayout.CENTER);

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(new Color(48, 48, 48));
        container.add(card, BorderLayout.CENTER);

        return lblValor;
    }

    private JPanel criarPainelFuncionarios() {
        JPanel painel = new JPanel(new BorderLayout(15, 15));
        painel.setBackground(new Color(30, 30, 30));

        JLabel titulo = new JLabel("Gerenciamento de Funcionários", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);
        painel.add(titulo, BorderLayout.NORTH);

        String[] colunas = {"ID", "Nome", "CPF", "Cargo", "Telefone"};
        modelTabelaFuncionarios = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tableFuncionarios = new JTable(modelTabelaFuncionarios);
        tableFuncionarios.setRowHeight(28);
        tableFuncionarios.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tableFuncionarios.setBackground(new Color(45, 45, 45));
        tableFuncionarios.setForeground(Color.WHITE);
        tableFuncionarios.setGridColor(new Color(70, 70, 70));

        JTableHeader header = tableFuncionarios.getTableHeader();
        header.setBackground(new Color(60, 60, 60));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));

        painel.add(new JScrollPane(tableFuncionarios), BorderLayout.CENTER);

        JPanel botoes = new JPanel();
        botoes.setBackground(painel.getBackground());

        JButton btnAdicionar = criarBotaoAcao("Adicionar", new Color(66, 133, 244), Color.WHITE, e -> abrirFormularioFuncionario(null));
        JButton btnEditar = criarBotaoAcao("Editar", new Color(255, 193, 7), Color.BLACK, e -> {
            int row = tableFuncionarios.getSelectedRow();
            if (row >= 0) abrirFormularioFuncionario(funcionarios.get(row));
            else JOptionPane.showMessageDialog(this, "Selecione um funcionário para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
        });
        JButton btnExcluir = criarBotaoAcao("Excluir", new Color(244, 67, 54), Color.WHITE, e -> {
            int row = tableFuncionarios.getSelectedRow();
            if (row >= 0) {
                Funcionario f = funcionarios.get(row);
                int opc = JOptionPane.showConfirmDialog(this, "Deseja excluir " + f.getNome() + "?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (opc == JOptionPane.YES_OPTION) {
                    boolean sucesso = funcionarioCtrl.excluirFuncionarioComVerificacao(f.getId_Funcionario());
                    if (sucesso) {
                        JOptionPane.showMessageDialog(this, "Funcionário excluído com sucesso!");
                        carregarFuncionarios();
                    } else {
                        JOptionPane.showMessageDialog(this, "Não é possível excluir este funcionário, pois ele está vinculado a outros registros.", "Erro", JOptionPane.ERROR_MESSAGE);
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

    private JButton criarBotaoAcao(String texto, Color bg, Color fg, java.awt.event.ActionListener listener) {
        JButton btn = new JButton(texto);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.addActionListener(listener);
        return btn;
    }

    private JPanel criarPainelRelatorioFinanceiro() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBackground(new Color(30, 30, 30));
        painel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Relatório Financeiro");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);
        painel.add(titulo, BorderLayout.NORTH);

        String[] colunas = {"Cliente", "ID Vistoria", "Valor", "Data Pagamento", "Forma", "Status"};
        DefaultTableModel modelFinanceiro = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable tabelaFinanceiro = new JTable(modelFinanceiro);
        tabelaFinanceiro.setRowHeight(28);
        tabelaFinanceiro.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabelaFinanceiro.setBackground(new Color(45, 45, 45));
        tabelaFinanceiro.setForeground(Color.WHITE);

        JTableHeader header = tabelaFinanceiro.getTableHeader();
        header.setBackground(new Color(60, 60, 60));
        header.setForeground(Color.WHITE);

        // Carrega os dados do DAO
        List<PagamentoController> pagamentos = dao.PagamentoDAO.mostrarHistoricoDePagamentos();
        for (PagamentoController p : pagamentos) {
            // Aqui você pode buscar o nome do cliente se quiser mostrar ao invés do ID
            String nomeCliente = (p.getCliente() != null && p.getCliente().getNome() != null) ? p.getCliente().getNome() : String.valueOf(p.getCliente().getId_Cliente());
            modelFinanceiro.addRow(new Object[]{
                nomeCliente,
                p.getVistoria() != null ? p.getVistoria().getIdVistoria() : "",
                String.format("R$ %.2f", p.getValor()),
                p.getDataPagamento(),
                p.getFormaPagamento(),
                p.getStatusPagamento()
            });
        }

        painel.add(new JScrollPane(tabelaFinanceiro), BorderLayout.CENTER);
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

        // Atualiza cards de resumo
        lblTotal.setText(String.valueOf(funcionarios.size()));
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
        form.add(new JLabel("Nome:")); form.add(txtNome);
        form.add(new JLabel("CPF:")); form.add(txtCpf);
        form.add(new JLabel("Cargo:")); form.add(txtCargo);
        form.add(new JLabel("Telefone:")); form.add(txtTelefone);

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
                JOptionPane.showMessageDialog(this, "Erro ao salvar funcionário: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
