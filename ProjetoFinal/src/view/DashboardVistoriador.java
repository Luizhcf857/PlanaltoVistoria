package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import model.*;
import controller.*;
import dao.*;

public class DashboardVistoriador extends JPanel {

    private final Funcionario usuarioAtual;
    private final AgendamentoController agendamentoCtrl;
    private final VistoriaController vistoriaCtrl;
    private final VeiculoController veiculoCtrl;
    private final AgendamentoDAO agendamentoDAO;
    private final JPanel painelPrincipal;
    private final CardLayout cardPrincipal;

    private JPanel painelCards;
    private CardLayout layoutCards;

    private JLabel lblPendentes;
    private JLabel lblConcluidos;
    private JTable tableAgendamentos;
    private DefaultTableModel modelTabela;
    private List<Agendamento> agendamentos;

    public DashboardVistoriador(JPanel painelPrincipal, CardLayout cardPrincipal) {
        this.usuarioAtual = new Funcionario();
        this.agendamentoCtrl = new AgendamentoController();
        this.vistoriaCtrl = new VistoriaController();
        this.veiculoCtrl = new VeiculoController();
        this.agendamentoDAO = new AgendamentoDAO();
        this.painelPrincipal = painelPrincipal;
        this.cardPrincipal = cardPrincipal;

        setLayout(new BorderLayout());
        inicializarLayout();
        carregarAgendamentos();
        atualizarCards();
    }

    private void inicializarLayout() {
        setBackground(new Color(30, 30, 30));
        setBorder(null);

        // Top bar (dark, with logo, user, and menu)
        JPanel barraSuperior = criarBarraSuperior();
        add(barraSuperior, BorderLayout.NORTH);

        // Main content area (cards)
        layoutCards = new CardLayout();
        painelCards = new JPanel(layoutCards);
        painelCards.setBackground(new Color(36, 37, 42));
        painelCards.setBorder(new EmptyBorder(16, 16, 16, 16));

        painelCards.add(criarPainelResumo(), "Resumo");
        painelCards.add(criarPainelAgendamentos(), "Agendamentos");
        painelCards.add(criarPainelRelatorio(), "Relatorio");

        add(painelCards, BorderLayout.CENTER);
    }

    private JPanel criarBarraSuperior() {
        JPanel barra = new JPanel(new BorderLayout());
        barra.setBackground(new Color(24, 25, 28));
        barra.setPreferredSize(new Dimension(0, 56));
        barra.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(38, 50, 56)));

        JLabel logo = new JLabel("PLANALTO VISTORIA");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        logo.setForeground(new Color(0xD4AF37));
        logo.setBorder(new EmptyBorder(0, 24, 0, 0));

        JPanel menu = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 10));
        menu.setOpaque(false);

        JButton btnDashboard = criarBotaoMenu("Dashboard");
        JButton btnAgend = criarBotaoMenu("Agendamentos");
        JButton btnRelat = criarBotaoMenu("Relatórios");
        JButton btnNovo = criarBotaoMenu("Novo...");

        btnDashboard.addActionListener(e -> layoutCards.show(painelCards, "Resumo"));
        btnAgend.addActionListener(e -> {
            carregarAgendamentos();
            layoutCards.show(painelCards, "Agendamentos");
        });
        btnRelat.addActionListener(e -> layoutCards.show(painelCards, "Relatorio"));
        btnNovo.addActionListener(e -> {
            JPopupMenu popup = new JPopupMenu();
            JMenuItem itemAgendamento = new JMenuItem("Novo Agendamento");
            JMenuItem itemCliente = new JMenuItem("Novo Cliente");
            JMenuItem itemVeiculo = new JMenuItem("Novo Veículo");
            itemAgendamento.addActionListener(ev -> abrirFormularioAgendamento());
            itemCliente.addActionListener(ev -> abrirFormularioCliente());
            itemVeiculo.addActionListener(ev -> abrirFormularioVeiculo());
            popup.add(itemAgendamento);
            popup.add(itemCliente);
            popup.add(itemVeiculo);
            popup.show(btnNovo, 0, btnNovo.getHeight());
        });

        menu.add(btnDashboard);
        menu.add(btnAgend);
        menu.add(btnRelat);
        menu.add(btnNovo);

        JPanel painelUsuario = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        painelUsuario.setOpaque(false);

        JLabel lblUsuario = new JLabel("Funcionário: " + (usuarioAtual != null ? usuarioAtual.getNome() : "Desconhecido"));
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
        barra.add(menu, BorderLayout.CENTER);
        barra.add(painelUsuario, BorderLayout.EAST);

        return barra;
    }

    private JButton criarBotaoMenu(String texto) {
        JButton botao = new JButton(texto);
        botao.setFocusPainted(false);
        botao.setForeground(Color.WHITE);
        botao.setBackground(new Color(36, 37, 42));
        botao.setFont(new Font("Segoe UI", Font.BOLD, 15));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                botao.setBackground(new Color(55, 71, 79));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                botao.setBackground(new Color(36, 37, 42));
            }
        });
        return botao;
    }

    private JPanel criarPainelResumo() {
        JPanel painel = new JPanel(new GridLayout(1, 2, 20, 0));
        painel.setBackground(new Color(36, 37, 42));

        lblPendentes = criarCard("Pendentes", new Color(255, 152, 0));
        lblConcluidos = criarCard("Concluídos", new Color(76, 175, 80));

        painel.add(lblPendentes.getParent());
        painel.add(lblConcluidos.getParent());

        return painel;
    }

    private JLabel criarCard(String titulo, Color corTitulo) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(44, 45, 50));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(90, 90, 90), 1),
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
        container.setBackground(new Color(36, 37, 42));
        container.add(card, BorderLayout.CENTER);

        return lblValor;
    }



    private JPanel criarPainelAgendamentos() {
        JPanel painel = new JPanel(new BorderLayout(15, 15));
        painel.setBackground(new Color(245, 245, 245));

        JLabel titulo = new JLabel("Agendamentos Cadastrados", SwingConstants.LEFT);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        painel.add(titulo, BorderLayout.NORTH);

        String[] colunas = {"ID", "Data", "Cliente", "Veículo", "Serviço"};
        modelTabela = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tableAgendamentos = new JTable(modelTabela);
        tableAgendamentos.setRowHeight(30);
        tableAgendamentos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        painel.add(new JScrollPane(tableAgendamentos), BorderLayout.CENTER);

        JButton btnNovaVistoria = new JButton("Nova Vistoria");
        btnNovaVistoria.setBackground(new Color(66, 133, 244));
        btnNovaVistoria.setForeground(Color.WHITE);
        btnNovaVistoria.setFont(new Font("Segoe UI", Font.BOLD, 16));

        btnNovaVistoria.addActionListener(e -> {
            int row = tableAgendamentos.getSelectedRow();
            if (row >= 0) {
                Agendamento ag = agendamentos.get(row);
                abrirFormularioVistoriaSelecionado(ag);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um agendamento.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        JPanel botoes = new JPanel();
        botoes.setBackground(painel.getBackground());
        botoes.add(btnNovaVistoria);
        painel.add(botoes, BorderLayout.SOUTH);

        return painel;
    }

    private JPanel painelRelatorio;
    private JTable tableRelatorio;
    private DefaultTableModel modelRelatorio;
    private List<Vistoria> listaVistorias; // dados brutos
    private List<Agendamento> listaAgendamentosCache; // cache para mapear agendamento -> cliente/veiculo

    private JPanel criarPainelRelatorio() {
        painelRelatorio = new JPanel(new BorderLayout(10, 10));
        painelRelatorio.setBackground(new Color(245, 245, 245));
        painelRelatorio.setBorder(new EmptyBorder(20, 20, 20, 20));

        // --- Top: título + filtros ---
        JPanel top = new JPanel(new BorderLayout(10, 10));
        top.setBackground(painelRelatorio.getBackground());

        JLabel titulo = new JLabel("Relatório de Vistorias", SwingConstants.LEFT);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        top.add(titulo, BorderLayout.NORTH);

        JPanel filtros = new JPanel();
        filtros.setBackground(painelRelatorio.getBackground());
        filtros.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 6));

        // Date range
        filtros.add(new JLabel("Data inicial (DD/MM/YYYY):"));
        JTextField txtDataInicial = new JTextField(8);
        filtros.add(txtDataInicial);

        filtros.add(new JLabel("Data final (DD/MM/YYYY):"));
        JTextField txtDataFinal = new JTextField(8);
        filtros.add(txtDataFinal);

        // Cliente combo
        filtros.add(new JLabel("Cliente:"));
        JComboBox<Cliente> comboClienteFiltro = new JComboBox<>();
        comboClienteFiltro.addItem(null); // opção "todos"
        List<Cliente> clientes = ClienteDAO.pegarCliente();
        if (clientes != null) {
            for (Cliente c : clientes) comboClienteFiltro.addItem(c);
        }
        comboClienteFiltro.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Cliente c) setText(c.getId_Cliente() + " - " + c.getNome());
                else setText("Todos");
                return this;
            }
        });
        filtros.add(comboClienteFiltro);

        // Funcionario combo
        filtros.add(new JLabel("Funcionário:"));
        JComboBox<Funcionario> comboFuncFiltro = new JComboBox<>();
        comboFuncFiltro.addItem(null);
        for (Funcionario f : FuncionarioDAO.listar()) comboFuncFiltro.addItem(f);
        comboFuncFiltro.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Funcionario f) setText(f.getId_Funcionario() + " - " + f.getNome());
                else setText("Todos");
                return this;
            }
        });
        filtros.add(comboFuncFiltro);

        // Botões aplicar/limpar
        JButton btnAplicar = new JButton("Aplicar Filtros");
        btnAplicar.setBackground(new Color(66, 133, 244));
        btnAplicar.setForeground(Color.WHITE);
        btnAplicar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        filtros.add(btnAplicar);

        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.setBackground(new Color(158, 158, 158));
        btnLimpar.setForeground(Color.WHITE);
        btnLimpar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        filtros.add(btnLimpar);

        top.add(filtros, BorderLayout.CENTER);
        painelRelatorio.add(top, BorderLayout.NORTH);

        // --- Center: tabela ---
        String[] colunas = {"ID Vistoria", "Data", "Cliente", "Veículo", "Funcionário", "Itens Verificados", "Observações", "ID Agendamento"};
        modelRelatorio = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tableRelatorio = new JTable(modelRelatorio);
        tableRelatorio.setRowHeight(28);
        tableRelatorio.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        painelRelatorio.add(new JScrollPane(tableRelatorio), BorderLayout.CENTER);

        // --- South: ações ---
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.setBackground(painelRelatorio.getBackground());
        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setBackground(new Color(66, 133, 244));
        btnAtualizar.setForeground(Color.WHITE);
        btnAtualizar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        south.add(btnAtualizar);

        // opcional: botão export CSV
        JButton btnExportCsv = new JButton("Exportar CSV");
        btnExportCsv.setBackground(new Color(76, 175, 80));
        btnExportCsv.setForeground(Color.WHITE);
        btnExportCsv.setFont(new Font("Segoe UI", Font.BOLD, 12));
        south.add(btnExportCsv);

        painelRelatorio.add(south, BorderLayout.SOUTH);

        // --- Carregar dados iniciais ---
        carregarRelatorio();

        // --- listeners ---
        btnAtualizar.addActionListener(e -> {
            carregarRelatorio();
            JOptionPane.showMessageDialog(this, "Relatório atualizado.");
        });

        btnAplicar.addActionListener(e -> {
            // aplica filtros conforme campos
            String dataIni = txtDataInicial.getText().trim();
            String dataFim = txtDataFinal.getText().trim();
            Cliente clienteSel = (Cliente) comboClienteFiltro.getSelectedItem();
            Funcionario funcSel = (Funcionario) comboFuncFiltro.getSelectedItem();
            aplicarFiltros(dataIni, dataFim, clienteSel, funcSel);
        });

        btnLimpar.addActionListener(e -> {
            txtDataInicial.setText("");
            txtDataFinal.setText("");
            comboClienteFiltro.setSelectedIndex(0);
            comboFuncFiltro.setSelectedIndex(0);
            aplicarFiltros("", "", null, null);
        });

        btnExportCsv.addActionListener(e -> {
            // Export simples para CSV na pasta do usuário (prompt de salvar)
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Salvar CSV");
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                java.io.File f = fileChooser.getSelectedFile();
                try (java.io.PrintWriter pw = new java.io.PrintWriter(f)) {
                    // cabeçalho
                    for (int i = 0; i < modelRelatorio.getColumnCount(); i++) {
                        pw.print(modelRelatorio.getColumnName(i));
                        if (i < modelRelatorio.getColumnCount() - 1) pw.print(",");
                    }
                    pw.println();
                    // linhas
                    for (int r = 0; r < modelRelatorio.getRowCount(); r++) {
                        for (int c = 0; c < modelRelatorio.getColumnCount(); c++) {
                            Object val = modelRelatorio.getValueAt(r, c);
                            pw.print(val != null ? val.toString().replaceAll(",", " ") : "");
                            if (c < modelRelatorio.getColumnCount() - 1) pw.print(",");
                        }
                        pw.println();
                    }
                    JOptionPane.showMessageDialog(this, "CSV exportado com sucesso!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao exportar CSV: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return painelRelatorio;
    }

    private void carregarRelatorio() {
        modelRelatorio.setRowCount(0);
        listaVistorias = VistoriaDAO.listar(); // espera-se que retorne lista de Vistoria
        if (listaVistorias == null) listaVistorias = new ArrayList<>();
        
        // cria cache de agendamentos pra mapear cliente/veiculo por id do agendamento
        listaAgendamentosCache = new AgendamentoDAO().listar();
        java.util.Map<Integer, Agendamento> agMap = new java.util.HashMap<>();
        for (Agendamento ag : listaAgendamentosCache) agMap.put(ag.getIdAgendamento(), ag);

        for (Vistoria v : listaVistorias) {
            try {
                int idV = v.getIdVistoria();
                java.sql.Date data = v.getDataVistoria();
                String dataStr = (data != null ? new java.text.SimpleDateFormat("dd/MM/yyyy").format(data) : "");

                // buscar agendamento/cliente/veiculo
                Agendamento ag = null;
                if (v.getAgendamento() != null) ag = agMap.get(v.getAgendamento().getIdAgendamento());

                String clienteNome = "(não disponível)";
                String veiculoStr = "(não disponível)";
                if (ag != null && ag.getCliente() != null && ag.getCliente().getId_Cliente() != 0) {
                    Cliente c = ClienteDAO.pegarPorId(ag.getCliente().getId_Cliente());
                    if (c != null) clienteNome = c.getNome();
                }
                if (ag != null && ag.getVeiculo() != null && ag.getVeiculo().getId_Veiculo() != 0) {
                    Veiculo ve = VeiculoDAO.pegarPorId(ag.getVeiculo().getId_Veiculo());
                    if (ve != null) veiculoStr = ve.getModelo() + " (" + ve.getPlaca() + ")";
                }

                // funcionario
                String funcionarioNome = "(não disponível)";
                if (v.getFuncionario() != null && v.getFuncionario().getId_Funcionario() != 0) {
                    Funcionario f = new FuncionarioDAO().buscarPorId(v.getFuncionario().getId_Funcionario());
                    if (f != null) funcionarioNome = f.getNome();
                }

                modelRelatorio.addRow(new Object[]{
                    idV,
                    dataStr,
                    clienteNome,
                    veiculoStr,
                    funcionarioNome,
                    v.getItensVerificados(),
                    v.getObservacao(),
                    (v.getAgendamento() != null ? v.getAgendamento().getIdAgendamento() : "")
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void aplicarFiltros(String dataInicialStr, String dataFinalStr, Cliente clienteFiltro, Funcionario funcFiltro) {
        modelRelatorio.setRowCount(0);
        java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        java.sql.Date dataIni = null, dataFim = null;
        try {
            if (dataInicialStr != null && !dataInicialStr.isBlank()) {
                java.time.LocalDate ld = java.time.LocalDate.parse(dataInicialStr, fmt);
                dataIni = java.sql.Date.valueOf(ld);
            }
            if (dataFinalStr != null && !dataFinalStr.isBlank()) {
                java.time.LocalDate ld2 = java.time.LocalDate.parse(dataFinalStr, fmt);
                dataFim = java.sql.Date.valueOf(ld2);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Formato de data inválido. Use DD/MM/YYYY.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // criar cache de agendamentos
        if (listaAgendamentosCache == null) listaAgendamentosCache = new AgendamentoDAO().listar();
        java.util.Map<Integer, Agendamento> agMap = new java.util.HashMap<>();
        for (Agendamento ag : listaAgendamentosCache) agMap.put(ag.getIdAgendamento(), ag);

        for (Vistoria v : listaVistorias) {
            try {
                // filtro por data
                java.sql.Date dv = v.getDataVistoria();
                if (dataIni != null && (dv == null || dv.before(dataIni))) continue;
                if (dataFim != null && (dv == null || dv.after(dataFim))) continue;

                // filtro por funcionário
                if (funcFiltro != null && v.getFuncionario() != null) {
                    if (v.getFuncionario().getId_Funcionario() != funcFiltro.getId_Funcionario()) continue;
                } else if (funcFiltro != null) continue;

                // filtro por cliente (precisa mapear via agendamento)
                if (clienteFiltro != null) {
                    Agendamento ag = null;
                    if (v.getAgendamento() != null) ag = agMap.get(v.getAgendamento().getIdAgendamento());
                    if (ag == null || ag.getCliente() == null || ag.getCliente().getId_Cliente() != clienteFiltro.getId_Cliente()) continue;
                }

                // se passou nos filtros, montar linha (reaproveita lógica do carregarRelatorio)
                java.sql.Date data = v.getDataVistoria();
                String dataStr = (data != null ? new java.text.SimpleDateFormat("dd/MM/yyyy").format(data) : "");
                Agendamento ag = null;
                if (v.getAgendamento() != null) ag = agMap.get(v.getAgendamento().getIdAgendamento());

                String clienteNome = "(não disponível)";
                String veiculoStr = "(não disponível)";
                if (ag != null && ag.getCliente() != null && ag.getCliente().getId_Cliente() != 0) {
                    Cliente c = ClienteDAO.pegarPorId(ag.getCliente().getId_Cliente());
                    if (c != null) clienteNome = c.getNome();
                }
                if (ag != null && ag.getVeiculo() != null && ag.getVeiculo().getId_Veiculo() != 0) {
                    Veiculo ve = VeiculoDAO.pegarPorId(ag.getVeiculo().getId_Veiculo());
                    if (ve != null) veiculoStr = ve.getModelo() + " (" + ve.getPlaca() + ")";
                }

                String funcionarioNome = "(não disponível)";
                if (v.getFuncionario() != null && v.getFuncionario().getId_Funcionario() != 0) {
                    Funcionario f = new FuncionarioDAO().buscarPorId(v.getFuncionario().getId_Funcionario());
                    if (f != null) funcionarioNome = f.getNome();
                }

                modelRelatorio.addRow(new Object[]{
                    v.getIdVistoria(),
                    dataStr,
                    clienteNome,
                    veiculoStr,
                    funcionarioNome,
                    v.getItensVerificados(),
                    v.getObservacao(),
                    (v.getAgendamento() != null ? v.getAgendamento().getIdAgendamento() : "")
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    private void carregarAgendamentos() {
        modelTabela.setRowCount(0);
        agendamentos = new ArrayList<>();

        try {
            // Buscar todos os agendamentos do DAO
            List<Agendamento> listaAg = agendamentoDAO.listar(); 

            for (Agendamento a : listaAg) {
                // Buscar Cliente pelo ID
                Cliente c = null;
                if (a.getCliente() != null && a.getCliente().getId_Cliente() != 0) {
                    c = ClienteDAO.pegarPorId(a.getCliente().getId_Cliente());
                }
                a.setCliente(c);

                // Buscar Veículo pelo ID
                Veiculo v = null;
                if (a.getVeiculo() != null && a.getVeiculo().getId_Veiculo() != 0) {
                    v = VeiculoDAO.pegarPorId(a.getVeiculo().getId_Veiculo());
                }
                a.setVeiculo(v);

                // Adicionar na lista local
                agendamentos.add(a);

                // Adicionar na tabela
                modelTabela.addRow(new Object[]{
                    a.getIdAgendamento(),
                    a.getDataAgendamento(),
                    (c != null ? c.getNome() : "(sem cliente)"),
                    (v != null ? v.getModelo() + " (" + v.getPlaca() + ")" : "(sem veículo)"),
                    a.getTipoServico()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar agendamentos: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarCards() {
        try {
            List<Agendamento> lista = agendamentoDAO.listar();
            int pendentes = 0;
            int concluidos = 0;
            for (Agendamento ag : lista) {
                if (VistoriaDAO.possuiVistoria(ag.getIdAgendamento())) {
                    concluidos++;
                } else {
                    pendentes++;
                }
            }
            lblPendentes.setText(String.valueOf(pendentes));
            lblConcluidos.setText(String.valueOf(concluidos));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao atualizar contadores: " + e.getMessage());
        }
    }

    // ================= FORMULÁRIOS =================

    private void abrirFormularioCliente() {
        JPanel painelForm = new JPanel(new BorderLayout(10, 10));
        painelForm.setBackground(new Color(245, 245, 245));
        JLabel titulo = new JLabel("Novo Cliente", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        painelForm.add(titulo, BorderLayout.NORTH);

        JPanel campos = new JPanel(new GridLayout(5, 2, 10, 10));
        campos.setBorder(new EmptyBorder(30, 100, 30, 100));
        campos.setBackground(new Color(245, 245, 245));

        JTextField txtNome = new JTextField();
        JTextField txtCpf = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtTelefone = new JTextField();

        campos.add(new JLabel("Nome do Cliente:")); campos.add(txtNome);
        campos.add(new JLabel("CPF:")); campos.add(txtCpf);
        campos.add(new JLabel("Email:")); campos.add(txtEmail);
        campos.add(new JLabel("Telefone:")); campos.add(txtTelefone);

        painelForm.add(campos, BorderLayout.CENTER);

        JPanel botoes = new JPanel();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnVoltar = new JButton("Voltar");
        btnSalvar.setBackground(new Color(66, 133, 244));
        btnSalvar.setForeground(Color.WHITE);
        btnVoltar.setBackground(new Color(158, 158, 158));
        btnVoltar.setForeground(Color.WHITE);
        botoes.add(btnSalvar); botoes.add(btnVoltar);
        painelForm.add(botoes, BorderLayout.SOUTH);

        btnSalvar.addActionListener(e -> {
            try {
                if (txtNome.getText().isBlank() || txtCpf.getText().isBlank()) {
                    JOptionPane.showMessageDialog(this, "Preencha nome e CPF!", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Cliente cliente = new Cliente();
                cliente.setNome(txtNome.getText().trim());
                cliente.setCpf(txtCpf.getText().trim());
                cliente.setEmail(txtEmail.getText().trim());
                cliente.setTelefone(txtTelefone.getText().trim());
                ClienteDAO.cadastrarCliente(cliente);

                JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
                layoutCards.show(painelCards, "Resumo");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        btnVoltar.addActionListener(e -> layoutCards.show(painelCards, "Resumo"));
        painelCards.add(painelForm, "NovoCliente");
        layoutCards.show(painelCards, "NovoCliente");
    }

    private void abrirFormularioVeiculo() {
        JPanel painelForm = new JPanel(new BorderLayout(10, 10));
        painelForm.setBackground(new Color(245, 245, 245));
        JLabel titulo = new JLabel("Novo Veículo", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        painelForm.add(titulo, BorderLayout.NORTH);

        JPanel campos = new JPanel(new GridLayout(6, 2, 10, 10));
        campos.setBorder(new EmptyBorder(30, 100, 30, 100));
        campos.setBackground(new Color(245, 245, 245));

        JComboBox<Cliente> comboCliente = new JComboBox<>();
        for (Cliente c : ClienteDAO.pegarCliente()) comboCliente.addItem(c);
        comboCliente.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Cliente c) setText(c.getNome());
                return this;
            }
        });

        JTextField txtPlaca = new JTextField();
        JTextField txtMarca = new JTextField();
        JTextField txtModelo = new JTextField();
        JTextField txtAno = new JTextField();
        JTextField txtChassi = new JTextField();

        campos.add(new JLabel("Cliente:")); campos.add(comboCliente);
        campos.add(new JLabel("Placa:")); campos.add(txtPlaca);
        campos.add(new JLabel("Marca:")); campos.add(txtMarca);
        campos.add(new JLabel("Modelo:")); campos.add(txtModelo);
        campos.add(new JLabel("Ano:")); campos.add(txtAno);
        campos.add(new JLabel("Número do Chassi:")); campos.add(txtChassi);

        painelForm.add(campos, BorderLayout.CENTER);

        JPanel botoes = new JPanel();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnVoltar = new JButton("Voltar");
        btnSalvar.setBackground(new Color(66, 133, 244));
        btnSalvar.setForeground(Color.WHITE);
        btnVoltar.setBackground(new Color(158, 158, 158));
        btnVoltar.setForeground(Color.WHITE);
        botoes.add(btnSalvar); botoes.add(btnVoltar);
        painelForm.add(botoes, BorderLayout.SOUTH);

        btnSalvar.addActionListener(e -> {
            try {
                Cliente clienteSelecionado = (Cliente) comboCliente.getSelectedItem();
                if (clienteSelecionado == null) {
                    JOptionPane.showMessageDialog(this, "Selecione um cliente!", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Veiculo veiculo = new Veiculo();
                veiculo.setPlaca(txtPlaca.getText().trim());
                veiculo.setMarca(txtMarca.getText().trim());
                veiculo.setModelo(txtModelo.getText().trim());
                veiculo.setAno(txtAno.getText().trim());
                veiculo.setNumero_chassi(txtChassi.getText().trim());
                veiculo.setCliente(clienteSelecionado);

                VeiculoDAO.inserir(veiculo);
                JOptionPane.showMessageDialog(this, "Veículo cadastrado com sucesso!");
                layoutCards.show(painelCards, "Resumo");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar veículo: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        btnVoltar.addActionListener(e -> layoutCards.show(painelCards, "Resumo"));
        painelCards.add(painelForm, "NovoVeiculo");
        layoutCards.show(painelCards, "NovoVeiculo");
    }

    private void abrirFormularioAgendamento() {
        JPanel painelForm = new JPanel(new BorderLayout(10, 10));
        painelForm.setBackground(new Color(245, 245, 245));
        JLabel titulo = new JLabel("Novo Agendamento", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        painelForm.add(titulo, BorderLayout.NORTH);

        JPanel campos = new JPanel(new GridLayout(5, 2, 10, 10));
        campos.setBorder(new EmptyBorder(30, 100, 30, 100));

        JComboBox<Cliente> comboCliente = new JComboBox<>();
        for (Cliente c : ClienteDAO.pegarCliente()) comboCliente.addItem(c);
        comboCliente.setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Cliente c) setText(c.getId_Cliente() + " - " + c.getNome());
                return this;
            }
        });

        JComboBox<Veiculo> comboVeiculo = new JComboBox<>();
        for (Veiculo v : new VeiculoDAO().listar()) comboVeiculo.addItem(v);
        comboVeiculo.setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Veiculo v) setText(v.getId_Veiculo() + " - " + v.getModelo() + " (" + v.getPlaca() + ")");
                return this;
            }
        });

        JComboBox<Funcionario> comboFuncionario = new JComboBox<>();
        for (Funcionario f : FuncionarioDAO.listar()) comboFuncionario.addItem(f);
        comboFuncionario.setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Funcionario f) setText(f.getId_Funcionario() + " - " + f.getNome());
                return this;
            }
        });

        JTextField txtData = new JTextField();
        JTextField txtTipoServico = new JTextField();

        campos.add(new JLabel("Cliente:")); campos.add(comboCliente);
        campos.add(new JLabel("Veículo:")); campos.add(comboVeiculo);
        campos.add(new JLabel("Funcionário:")); campos.add(comboFuncionario);
        campos.add(new JLabel("Data (DD/MM/YYYY):")); campos.add(txtData);
        campos.add(new JLabel("Tipo de Serviço:")); campos.add(txtTipoServico);

        painelForm.add(campos, BorderLayout.CENTER);

        JPanel botoes = new JPanel();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnVoltar = new JButton("Voltar");
        btnSalvar.setBackground(new Color(66, 133, 244));
        btnSalvar.setForeground(Color.WHITE);
        btnVoltar.setBackground(new Color(158, 158, 158));
        btnVoltar.setForeground(Color.WHITE);
        botoes.add(btnSalvar); botoes.add(btnVoltar);
        painelForm.add(botoes, BorderLayout.SOUTH);

        btnSalvar.addActionListener(e -> {
            try {
                Cliente cliente = (Cliente) comboCliente.getSelectedItem();
                Veiculo veiculo = (Veiculo) comboVeiculo.getSelectedItem();
                Funcionario funcionario = (Funcionario) comboFuncionario.getSelectedItem();
                String dataStr = txtData.getText().trim();
                String tipoServico = txtTipoServico.getText();

                if (cliente == null || veiculo == null || funcionario == null || dataStr.isBlank()) {
                    JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                java.sql.Date dataSql;
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate dataLocal = LocalDate.parse(dataStr, formatter);
                    dataSql = java.sql.Date.valueOf(dataLocal);
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(this, "Data inválida! Use o formato DD/MM/YYYY.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Agendamento ag = new Agendamento();
                ag.setCliente(cliente);
                ag.setVeiculo(veiculo);
                ag.setFuncionario(funcionario);
                ag.setDataAgendamento(dataSql);
                ag.setTipoServico(tipoServico);

                AgendamentoDAO.inserir(ag);
                JOptionPane.showMessageDialog(this, "Agendamento cadastrado com sucesso!");
                carregarAgendamentos();
                atualizarCards();
                layoutCards.show(painelCards, "Agendamentos");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        btnVoltar.addActionListener(e -> layoutCards.show(painelCards, "Agendamentos"));
        painelCards.add(painelForm, "NovoAgendamento");
        layoutCards.show(painelCards, "NovoAgendamento");
    }

    private void abrirFormularioVistoriaSelecionado(Agendamento agendamento) {
        JPanel painelForm = new JPanel(new BorderLayout(10, 10));
        painelForm.setBackground(new Color(245, 245, 245));
        JLabel titulo = new JLabel("Registrar Vistoria", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        painelForm.add(titulo, BorderLayout.NORTH);

        JPanel campos = new JPanel(new GridLayout(4, 2, 10, 10));
        campos.setBorder(new EmptyBorder(30, 100, 30, 100));

        JTextField txtData = new JTextField();
        JTextField txtItens = new JTextField();
        JTextField txtObs = new JTextField();

        campos.add(new JLabel("Data da Vistoria (DD/MM/YYYY):")); campos.add(txtData);
        campos.add(new JLabel("Itens Verificados:")); campos.add(txtItens);
        campos.add(new JLabel("Observações:")); campos.add(txtObs);

        painelForm.add(campos, BorderLayout.CENTER);

        JPanel botoes = new JPanel();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnVoltar = new JButton("Voltar");
        btnSalvar.setBackground(new Color(66, 133, 244));
        btnSalvar.setForeground(Color.WHITE);
        btnVoltar.setBackground(new Color(158, 158, 158));
        btnVoltar.setForeground(Color.WHITE);
        botoes.add(btnSalvar); botoes.add(btnVoltar);
        painelForm.add(botoes, BorderLayout.SOUTH);

        btnSalvar.addActionListener(e -> {
            try {
                String dataStr = txtData.getText().trim();
                String itens = txtItens.getText();
                String obs = txtObs.getText();

                if (dataStr.isBlank()) {
                    JOptionPane.showMessageDialog(this, "Informe a data da vistoria!", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                java.sql.Date dataVistoria;
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate dataLocal = LocalDate.parse(dataStr, formatter);
                    dataVistoria = java.sql.Date.valueOf(dataLocal);
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(this, "Data inválida! Use o formato DD/MM/YYYY.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Vistoria v = new Vistoria();
                v.setAgendamento(agendamento);
                v.setFuncionario(agendamento.getFuncionario());  // usar o funcionário do agendamento

                v.setDataVistoria(dataVistoria);
                v.setItensVerificados(itens);
                v.setObservacao(obs);

                VistoriaDAO.inserirVistoria(v);
                JOptionPane.showMessageDialog(this, "Vistoria registrada com sucesso!");
                carregarAgendamentos();
                atualizarCards();
                layoutCards.show(painelCards, "Agendamentos");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        btnVoltar.addActionListener(e -> layoutCards.show(painelCards, "Agendamentos"));
        painelCards.add(painelForm, "NovaVistoria");
        layoutCards.show(painelCards, "NovaVistoria");
    }

}
