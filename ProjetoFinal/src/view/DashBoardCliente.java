package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import dao.ClienteDAO;
import dao.AgendamentoDAO;
import dao.VistoriaDAO;
import dao.VeiculoDAO;
import dao.FuncionarioDAO;
import dao.PagamentoDAO;
import model.Cliente;
import model.Agendamento;
import model.Vistoria;
import model.Veiculo;
import model.PagamentoController;

public class DashBoardCliente extends JPanel {

    private final Cliente clienteAtual;
    private final JPanel painelPrincipal;
    private final CardLayout cardPrincipal;

    private JPanel painelCards;
    private CardLayout layoutCards;

    private JLabel lblPendentes;
    private JLabel lblConcluidos;
    private JTable tableAgendamentos;
    private DefaultTableModel modelTabela;

    private JTable tableRelatorio;
    private DefaultTableModel modelRelatorio;

    private List<Agendamento> agendamentos;

    public DashBoardCliente(JPanel painelPrincipal, CardLayout cardPrincipal) {
        this.painelPrincipal = painelPrincipal;
        this.cardPrincipal = cardPrincipal;

        this.clienteAtual = ClienteDAO.pegarUltimoCliente();

        setLayout(new BorderLayout());
        inicializarLayout();
        carregarRelatoriosCliente();
        atualizarCards();
    }

    // ---------------- Layout principal ----------------
    private void inicializarLayout() {
        JPanel menuLateral = criarMenuLateral();

        layoutCards = new CardLayout();
        painelCards = new JPanel(layoutCards);
        painelCards.setBorder(new EmptyBorder(20, 20, 20, 20));
        painelCards.setBackground(new Color(245, 245, 245));

        painelCards.add(criarPainelResumo(), "Resumo");
        painelCards.add(criarPainelDados(), "Dados");
        painelCards.add(criarPainelAgendamentos(), "Agendamentos");
        painelCards.add(criarPainelRelatorios(), "Relatorios");

        add(menuLateral, BorderLayout.WEST);
        add(painelCards, BorderLayout.CENTER);
    }

    // ---------------- Menu lateral ----------------
    private JPanel criarMenuLateral() {
        JPanel menu = new JPanel();
        menu.setBackground(new Color(25, 118, 210));
        menu.setPreferredSize(new Dimension(220, 0));
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Cliente DF", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnResumo = criarBotaoMenu("Dashboard");
        JButton btnDados = criarBotaoMenu("Dados Pessoais");
        JButton btnAgend = criarBotaoMenu("Agendamentos");
        JButton btnRelat = criarBotaoMenu("Relatórios");
        JButton btnSair = criarBotaoMenu("Sair");

        btnResumo.addActionListener(e -> layoutCards.show(painelCards, "Resumo"));
        btnDados.addActionListener(e -> layoutCards.show(painelCards, "Dados"));
        btnAgend.addActionListener(e -> layoutCards.show(painelCards, "Agendamentos"));
        btnRelat.addActionListener(e -> layoutCards.show(painelCards, "Relatorios"));
        btnSair.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente sair?", "Sair", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) System.exit(0);
        });

        menu.add(titulo);
        menu.add(Box.createVerticalStrut(50));
        menu.add(btnResumo);
        menu.add(Box.createVerticalStrut(15));
        menu.add(btnDados);
        menu.add(Box.createVerticalStrut(15));
        menu.add(btnAgend);
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
            public void mouseEntered(java.awt.event.MouseEvent e) { botao.setBackground(new Color(30, 136, 229)); }
            public void mouseExited(java.awt.event.MouseEvent e) { botao.setBackground(new Color(21, 101, 192)); }
        });
        return botao;
    }

    // ---------------- Painéis ----------------
    private JPanel criarPainelResumo() {
        JPanel painel = new JPanel(new GridLayout(1, 2, 20, 0));
        painel.setBackground(new Color(245, 245, 245));

        lblPendentes = criarCard("Pendentes", new Color(255, 152, 0));
        lblConcluidos = criarCard("Concluídos", new Color(76, 175, 80));

        painel.add(lblPendentes.getParent());
        painel.add(lblConcluidos.getParent());
        return painel;
    }

    private JLabel criarCard(String titulo, Color corTitulo) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(25, 25, 25, 25)
        ));
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(corTitulo);

        JLabel lblValor = new JLabel("0", SwingConstants.CENTER);
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 48));
        lblValor.setForeground(new Color(33, 150, 243));

        card.add(lblTitulo, BorderLayout.NORTH);
        card.add(lblValor, BorderLayout.CENTER);

        JPanel container = new JPanel(new BorderLayout());
        container.add(card, BorderLayout.CENTER);

        return lblValor;
    }

    private JPanel criarPainelDados() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(new Color(245, 245, 245));

        JLabel titulo = new JLabel("Seus Dados Pessoais", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        painel.add(titulo, BorderLayout.NORTH);

        JPanel conteudo = new JPanel(new GridLayout(5, 2, 10, 10));
        conteudo.setBorder(new EmptyBorder(30, 50, 30, 50));
        conteudo.setBackground(painel.getBackground());

        conteudo.add(new JLabel("Nome:"));
        conteudo.add(new JLabel(clienteAtual != null ? clienteAtual.getNome() : "Não encontrado"));
        conteudo.add(new JLabel("CPF:"));
        conteudo.add(new JLabel(clienteAtual != null ? clienteAtual.getCpf() : "Não encontrado"));
        conteudo.add(new JLabel("Telefone:"));
        conteudo.add(new JLabel(clienteAtual != null ? clienteAtual.getTelefone() : "Não encontrado"));
        conteudo.add(new JLabel("Email:"));
        conteudo.add(new JLabel(clienteAtual != null ? clienteAtual.getEmail() : "Não encontrado"));

        painel.add(conteudo, BorderLayout.CENTER);
        return painel;
    }

    // ---------------- Painel Agendamentos ----------------
    private JPanel criarPainelAgendamentos() {
        JPanel painel = new JPanel(new BorderLayout(15, 15));
        painel.setBackground(new Color(245, 245, 245));

        JLabel titulo = new JLabel("Seus Agendamentos", SwingConstants.LEFT);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        painel.add(titulo, BorderLayout.NORTH);

        String[] colunas = {"ID Agendamento", "Data", "Veículo", "Tipo de Serviço", "Status Pagamento", "ID Vistoria", "Ação"};
        modelTabela = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int r, int c) { return c == 6; } // só botão "Pagar"
        };

        tableAgendamentos = new JTable(modelTabela);
        tableAgendamentos.setRowHeight(28);
        tableAgendamentos.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Coluna de botão
        tableAgendamentos.getColumn("Ação").setCellRenderer(new ButtonRenderer());
        tableAgendamentos.getColumn("Ação").setCellEditor(new ButtonEditor(new JCheckBox(), painelPrincipal, cardPrincipal));

        painel.add(new JScrollPane(tableAgendamentos), BorderLayout.CENTER);

        carregarAgendamentosClienteComPagamento();

        // esconder coluna ID Vistoria
        tableAgendamentos.getColumnModel().getColumn(5).setMinWidth(0);
        tableAgendamentos.getColumnModel().getColumn(5).setMaxWidth(0);
        tableAgendamentos.getColumnModel().getColumn(5).setWidth(0);

        return painel;
    }

    private void carregarAgendamentosClienteComPagamento() {
        modelTabela.setRowCount(0); // limpa tabela
        agendamentos = AgendamentoDAO.listarPorCliente(clienteAtual.getId_Cliente());
        if (agendamentos == null) return;

        for (Agendamento ag : agendamentos) {
            List<Vistoria> vistorias = VistoriaDAO.listarPorAgendamento(ag.getIdAgendamento());

            for (Vistoria v : vistorias) {
                Veiculo veiculo = null;
                if (ag.getVeiculo() != null && ag.getVeiculo().getId_Veiculo() != 0) {
                    veiculo = VeiculoDAO.pegarPorId(ag.getVeiculo().getId_Veiculo());
                }

                PagamentoController pagamento = PagamentoDAO.pegarPorVistoria(v.getIdVistoria());
                String statusPagamento = pagamento != null ? pagamento.getStatusPagamento() : "Pendente";

                modelTabela.addRow(new Object[]{
                        ag.getIdAgendamento(),
                        ag.getDataAgendamento(),
                        veiculo != null ? veiculo.getModelo() + " (" + veiculo.getPlaca() + ")" : "(sem veículo)",
                        ag.getTipoServico(),
                        statusPagamento,
                        v.getIdVistoria(),
                        "Pagar"
                });
            }
        }
    }

    // ---------------- Painel Relatórios ----------------
    private JPanel criarPainelRelatorios() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBackground(new Color(245, 245, 245));
        painel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Seus Relatórios", SwingConstants.LEFT);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        painel.add(titulo, BorderLayout.NORTH);

        String[] colunas = {"ID Vistoria", "Data", "Veículo", "Itens Verificados", "Observações"};
        modelRelatorio = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tableRelatorio = new JTable(modelRelatorio);
        tableRelatorio.setRowHeight(28);
        tableRelatorio.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        painel.add(new JScrollPane(tableRelatorio), BorderLayout.CENTER);

        return painel;
    }

    private void carregarRelatoriosCliente() {
        modelRelatorio.setRowCount(0);

        List<Agendamento> agendamentosCliente = AgendamentoDAO.listarPorCliente(clienteAtual.getId_Cliente());
        for (Agendamento ag : agendamentosCliente) {
            List<Vistoria> vistorias = VistoriaDAO.listarPorAgendamento(ag.getIdAgendamento());

            for (Vistoria v : vistorias) {
                Veiculo veiculo = null;
                if (ag.getVeiculo() != null && ag.getVeiculo().getId_Veiculo() != 0) {
                    veiculo = VeiculoDAO.pegarPorId(ag.getVeiculo().getId_Veiculo());
                }

                                modelRelatorio.addRow(new Object[]{
                        v.getIdVistoria(),
                        v.getDataVistoria(),
                        veiculo != null ? veiculo.getModelo() + " (" + veiculo.getPlaca() + ")" : "(sem veículo)",
                        v.getItensVerificados() != null ? v.getItensVerificados() : "",
                        v.getObservacao() != null ? v.getObservacao() : ""
                });
            }
        }
    }

    // ---------------- Atualiza cards ----------------
    private void atualizarCards() {
        int pendentes = 0, concluidos = 0;
        if (agendamentos != null) {
            for (Agendamento ag : agendamentos) {
                List<Vistoria> vistorias = VistoriaDAO.listarPorAgendamento(ag.getIdAgendamento());
                for (Vistoria v : vistorias) {
                    PagamentoController pag = PagamentoDAO.pegarPorVistoria(v.getIdVistoria());
                    if (pag != null && "Pago".equals(pag.getStatusPagamento())) concluidos++;
                    else pendentes++;
                }
            }
        }
        lblPendentes.setText(String.valueOf(pendentes));
        lblConcluidos.setText(String.valueOf(concluidos));
    }

    // ---------------- Renderizador e editor de botão ----------------
    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() { setOpaque(true); }
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;

        private JPanel painelPrincipal;
        private CardLayout cardPrincipal;

        public ButtonEditor(JCheckBox checkBox, JPanel painelPrincipal, CardLayout cardPrincipal) {
            super(checkBox);
            this.painelPrincipal = painelPrincipal;
            this.cardPrincipal = cardPrincipal;
            button = new JButton();
            button.setOpaque(true);

            // Quando clicar, finaliza a edição da célula
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                int row = tableAgendamentos.getSelectedRow();
                int idVistoria = (int) tableAgendamentos.getValueAt(row, 5); // coluna oculta idVistoria

                // Busca pagamento existente
                PagamentoController pagamento = PagamentoDAO.pegarPorVistoria(idVistoria);

                // Cria pagamento se não existir
                if (pagamento == null) {
                    pagamento = new PagamentoController();

                    // Associa a vistoria
                    Vistoria vistoria = new Vistoria();
                    vistoria.setIdVistoria(idVistoria);
                    pagamento.setVistoria(vistoria);

                    // Associa o cliente atual do dashboard
                    pagamento.setCliente(clienteAtual);

                    pagamento.setStatusPagamento("Pendente");

                    // Salva no banco
                    PagamentoDAO.cadastrarPagamento(pagamento);
                }

                // Verifica se já existe uma tela de Pagamento no painel
                PagamentoView telaPagamento = null;
                for (Component comp : painelPrincipal.getComponents()) {
                    if (comp instanceof PagamentoView) {
                        telaPagamento = (PagamentoView) comp;
                        break;
                    }
                }

                if (telaPagamento != null) {
                    // Atualiza tela existente se necessário
                    // telaPagamento.setPagamento(pagamento); // opcional
                } else {
                    // Cria tela nova
                    telaPagamento = new PagamentoView(painelPrincipal, cardPrincipal, pagamento);
                    painelPrincipal.add(telaPagamento, "Pagamento");
                }

                // Mostra a tela de pagamento
                cardPrincipal.show(painelPrincipal, "Pagamento");
            }

            isPushed = false;
            return label;
        }
    }



}

