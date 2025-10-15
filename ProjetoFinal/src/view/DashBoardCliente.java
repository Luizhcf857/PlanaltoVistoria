// DashBoardCliente.java
package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;
import dao.*;
import model.*;

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

    private void inicializarLayout() {
        JPanel barraSuperior = criarMenuSuperior();

        layoutCards = new CardLayout();
        painelCards = new JPanel(layoutCards);
        painelCards.setBorder(new EmptyBorder(20, 20, 20, 20));
        painelCards.setBackground(new Color(33, 33, 33));

        painelCards.add(criarPainelResumo(), "Resumo");
        painelCards.add(criarPainelDados(), "Dados");
        painelCards.add(criarPainelAgendamentos(), "Agendamentos");
        painelCards.add(criarPainelRelatorios(), "Relatorios");

        setBackground(new Color(48, 48, 48));
        add(barraSuperior, BorderLayout.NORTH);
        add(painelCards, BorderLayout.CENTER);
    }

    // ---------------- MENU SUPERIOR ----------------
    private JPanel criarMenuSuperior() {
        JPanel barra = new JPanel(new BorderLayout());
        barra.setBackground(new Color(18, 18, 18));
        barra.setPreferredSize(new Dimension(0, 60));
        barra.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(38, 50, 56)));

        // LOGO E NOME DO SISTEMA
        JLabel logo = new JLabel("PLANALTO VISTORIA", SwingConstants.LEFT);
        
        Color dourada = new Color(0xD4AF37);
        
        logo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        logo.setForeground(dourada);
        logo.setBorder(new EmptyBorder(0, 20, 0, 0));

        // BOTÕES DE MENU
        JPanel menuBotoes = new JPanel();
        menuBotoes.setOpaque(false);
        menuBotoes.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        JButton btnResumo = criarBotaoMenu("Dashboard");
        JButton btnDados = criarBotaoMenu("Dados Pessoais");
        JButton btnAgend = criarBotaoMenu("Agendamentos");
        JButton btnRelat = criarBotaoMenu("Relatórios");

        btnResumo.addActionListener(e -> layoutCards.show(painelCards, "Resumo"));
        btnDados.addActionListener(e -> layoutCards.show(painelCards, "Dados"));
        btnAgend.addActionListener(e -> layoutCards.show(painelCards, "Agendamentos"));
        btnRelat.addActionListener(e -> layoutCards.show(painelCards, "Relatorios"));

        menuBotoes.add(btnResumo);
        menuBotoes.add(btnDados);
        menuBotoes.add(btnAgend);
        menuBotoes.add(btnRelat);

        // USUÁRIO E SAIR
        JPanel painelUsuario = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        painelUsuario.setOpaque(false);

        JLabel lblUsuario = new JLabel("Usuário: " + (clienteAtual != null ? clienteAtual.getNome() : "Desconhecido"));
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUsuario.setForeground(Color.WHITE);

        JButton btnSair = new JButton("Sair");
        btnSair.setBackground(new Color(244, 67, 54));
        btnSair.setForeground(Color.WHITE);
        btnSair.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSair.setFocusPainted(false);
        btnSair.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSair.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        btnSair.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente sair?", "Sair", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) System.exit(0);
        });

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

    // ---------------- RESUMO ----------------
    private JPanel criarPainelResumo() {
        JPanel painel = new JPanel(new GridLayout(2, 2, 20, 20));
        painel.setBackground(new Color(48, 48, 48));

        lblPendentes = criarCard("Pendentes", new Color(255, 87, 34));
        lblConcluidos = criarCard("Concluídos", new Color(76, 175, 80));

        painel.add(lblPendentes.getParent());
        painel.add(lblConcluidos.getParent());

        return painel;
    }

    private JLabel criarCard(String titulo, Color corTitulo) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(60, 63, 65));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(90, 90, 90), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(corTitulo);

        JLabel lblValor = new JLabel("0", SwingConstants.CENTER);
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 42));
        lblValor.setForeground(new Color(3, 169, 244));

        card.add(lblTitulo, BorderLayout.NORTH);
        card.add(lblValor, BorderLayout.CENTER);

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(new Color(48, 48, 48));
        container.add(card, BorderLayout.CENTER);

        return lblValor;
    }

    // ---------------- DADOS ----------------
    private JPanel criarPainelDados() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(new Color(30, 30, 30));

        JLabel titulo = new JLabel("Seus Dados Pessoais", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        painel.add(titulo, BorderLayout.NORTH);

        JPanel conteudo = new JPanel(new GridLayout(5, 2, 10, 10));
        conteudo.setBorder(new EmptyBorder(30, 50, 30, 50));
        conteudo.setBackground(painel.getBackground());

        conteudo.add(criarLabelEscuro("Nome:"));
        conteudo.add(criarLabelEscuro(clienteAtual != null ? clienteAtual.getNome() : "Não encontrado"));
        conteudo.add(criarLabelEscuro("CPF:"));
        conteudo.add(criarLabelEscuro(clienteAtual != null ? clienteAtual.getCpf() : "Não encontrado"));
        conteudo.add(criarLabelEscuro("Telefone:"));
        conteudo.add(criarLabelEscuro(clienteAtual != null ? clienteAtual.getTelefone() : "Não encontrado"));
        conteudo.add(criarLabelEscuro("Email:"));
        conteudo.add(criarLabelEscuro(clienteAtual != null ? clienteAtual.getEmail() : "Não encontrado"));

        painel.add(conteudo, BorderLayout.CENTER);
        return painel;
    }

    private JLabel criarLabelEscuro(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return label;
    }

    // ---------------- AGENDAMENTOS ----------------
    private JPanel criarPainelAgendamentos() {
        JPanel painel = new JPanel(new BorderLayout(15, 15));
        painel.setBackground(new Color(30, 30, 30));

        JLabel titulo = new JLabel("Seus Agendamentos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);
        painel.add(titulo, BorderLayout.NORTH);

        String[] colunas = {"ID Agendamento", "Data", "Veículo", "Tipo de Serviço", "Status Pagamento", "ID Vistoria", "Ação"};
        modelTabela = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int r, int c) { return c == 6; }
        };

        tableAgendamentos = new JTable(modelTabela);
        tableAgendamentos.setRowHeight(28);
        tableAgendamentos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tableAgendamentos.setBackground(new Color(45, 45, 45));
        tableAgendamentos.setForeground(Color.WHITE);
        tableAgendamentos.setGridColor(new Color(70, 70, 70));

        JTableHeader header = tableAgendamentos.getTableHeader();
        header.setBackground(new Color(60, 60, 60));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));

        tableAgendamentos.getColumn("Ação").setCellRenderer(new ButtonRenderer());
        tableAgendamentos.getColumn("Ação").setCellEditor(new ButtonEditor(
                new JCheckBox(), painelPrincipal, cardPrincipal, tableAgendamentos, clienteAtual
        ));

        carregarAgendamentosClienteComPagamento();

        tableAgendamentos.getColumnModel().getColumn(5).setMinWidth(0);
        tableAgendamentos.getColumnModel().getColumn(5).setMaxWidth(0);
        tableAgendamentos.getColumnModel().getColumn(5).setWidth(0);

        painel.add(new JScrollPane(tableAgendamentos), BorderLayout.CENTER);
        return painel;
    }

    private void carregarAgendamentosClienteComPagamento() {
        modelTabela.setRowCount(0);
        if (clienteAtual == null) return; // <-- Adicione esta linha
        agendamentos = AgendamentoDAO.listarPorCliente(clienteAtual.getId_Cliente());
        if (agendamentos == null) return;

        for (Agendamento ag : agendamentos) {
            List<Vistoria> vistorias = VistoriaDAO.listarPorAgendamento(ag.getIdAgendamento());
            if (vistorias.isEmpty()) {
                Veiculo veiculo = ag.getVeiculo() != null ? VeiculoDAO.pegarPorId(ag.getVeiculo().getId_Veiculo()) : null;
                modelTabela.addRow(new Object[]{
                        ag.getIdAgendamento(),
                        ag.getDataAgendamento(),
                        veiculo != null ? veiculo.getModelo() + " (" + veiculo.getPlaca() + ")" : "(sem veículo)",
                        ag.getTipoServico(),
                        "Sem vistoria",
                        null,
                        "Pagar"
                });
            } else {
                for (Vistoria v : vistorias) {
                    Veiculo veiculo = ag.getVeiculo() != null ? VeiculoDAO.pegarPorId(ag.getVeiculo().getId_Veiculo()) : null;
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
    }

    // ---------------- RELATÓRIOS ----------------
    private JPanel criarPainelRelatorios() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBackground(new Color(30, 30, 30));
        painel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Seus Relatórios");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);
        painel.add(titulo, BorderLayout.NORTH);

        String[] colunas = {"ID Vistoria", "Data", "Veículo", "Itens Verificados", "Observações"};
        modelRelatorio = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tableRelatorio = new JTable(modelRelatorio);
        tableRelatorio.setRowHeight(28);
        tableRelatorio.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tableRelatorio.setBackground(new Color(45, 45, 45));
        tableRelatorio.setForeground(Color.WHITE);

        JTableHeader header = tableRelatorio.getTableHeader();
        header.setBackground(new Color(60, 60, 60));
        header.setForeground(Color.WHITE);

        painel.add(new JScrollPane(tableRelatorio), BorderLayout.CENTER);
        return painel;
    }

    private void carregarRelatoriosCliente() {
        modelRelatorio.setRowCount(0);
        if (clienteAtual == null) return; // <-- Adicione esta linha
        List<Agendamento> agendamentosCliente = AgendamentoDAO.listarPorCliente(clienteAtual.getId_Cliente());
        if (agendamentosCliente == null) return; // <-- Adicione esta linha
        for (Agendamento ag : agendamentosCliente) {
            List<Vistoria> vistorias = VistoriaDAO.listarPorAgendamento(ag.getIdAgendamento());
            for (Vistoria v : vistorias) {
                Veiculo veiculo = ag.getVeiculo() != null ? VeiculoDAO.pegarPorId(ag.getVeiculo().getId_Veiculo()) : null;
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

    // -------- Botões personalizados --------
    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setBackground(new Color(66, 133, 244));
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.BOLD, 13));
        }

        @Override
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
        private JTable tableAgendamentos;
        private Cliente clienteAtual;

        public ButtonEditor(JCheckBox checkBox, JPanel painelPrincipal, CardLayout cardPrincipal,
                            JTable tableAgendamentos, Cliente clienteAtual) {
            super(checkBox);
            this.painelPrincipal = painelPrincipal;
            this.cardPrincipal = cardPrincipal;
            this.tableAgendamentos = tableAgendamentos;
            this.clienteAtual = clienteAtual;

            button = new JButton();
            button.setOpaque(true);
            button.setBackground(new Color(66, 133, 244));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Segoe UI", Font.BOLD, 13));
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                int row = tableAgendamentos.getSelectedRow();
                if (row < 0) return label;

                Object objVistoria = tableAgendamentos.getValueAt(row, 5);
                int idVistoria = (objVistoria != null) ? (Integer) objVistoria : 0;

                PagamentoController pagamento = new PagamentoController();
                pagamento.setCliente(clienteAtual);

                Vistoria vistoria = new Vistoria();
                vistoria.setIdVistoria(idVistoria);
                pagamento.setVistoria(vistoria);

                painelPrincipal.add(new PagamentoView(painelPrincipal, cardPrincipal, pagamento), "TelaPagamento");
                cardPrincipal.show(painelPrincipal, "TelaPagamento");
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
}
