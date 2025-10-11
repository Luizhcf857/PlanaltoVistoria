package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import dao.AgendamentoDAO;
import model.Agendamento;

public class DashboardVistoriador extends JPanel {

    private JTable tabelaAgendamentos;
    private DefaultTableModel modeloTabela;
    private JPanel painelPrincipal;
    private CardLayout cardLayout;

    public DashboardVistoriador(JPanel painelPrincipal, CardLayout cardLayout) {
        this.painelPrincipal = painelPrincipal;
        this.cardLayout = cardLayout;

        setLayout(new BorderLayout());
        JLabel titulo = new JLabel("Dashboard do Vistoriador", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(titulo, BorderLayout.NORTH);

        // ==== Tabela ====
        String[] colunas = {"ID", "Cliente", "Veículo", "Funcionário", "Data", "Tipo de Serviço"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaAgendamentos = new JTable(modeloTabela);
        tabelaAgendamentos.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(tabelaAgendamentos);
        add(scroll, BorderLayout.CENTER);

        // ==== Painel de Botões ====
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnAtualizar = new JButton("Atualizar Lista");
        JButton btnAbrir = new JButton("Abrir Vistoria");
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnAbrir);
        add(painelBotoes, BorderLayout.SOUTH);

        // ==== Ações dos botões ====
        btnAtualizar.addActionListener(e -> carregarAgendamentos());
        //btnAbrir.addActionListener(e -> abrirAgendamentoSelecionado());

        carregarAgendamentos(); // carrega ao iniciar
    }

    private void carregarAgendamentos() {
        modeloTabela.setRowCount(0);
        AgendamentoDAO dao = new AgendamentoDAO();
        List<Agendamento> lista = dao.listar();

        for (Agendamento ag : lista) {
            modeloTabela.addRow(new Object[]{
                    ag.getIdAgendamento(),
                    ag.getCliente() != null ? ag.getCliente().getNome() : "(sem cliente)",
                    ag.getVeiculo() != null ? ag.getVeiculo().getModelo() : "(sem veículo)",
                    ag.getFuncionario() != null ? ag.getFuncionario().getNome() : "(sem funcionário)",
                    ag.getDataAgendamento(),
                    ag.getTipoServico()
            });
        }
    }

   /* private void abrirAgendamentoSelecionado() {
        int linha = tabelaAgendamentos.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um agendamento para abrir a vistoria.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabela.getValueAt(linha, 0);
        AgendamentoDAO dao = new AgendamentoDAO();
        Agendamento agendamento = new Agendamento();

        if (agendamento == null) {
            JOptionPane.showMessageDialog(this, "Agendamento não encontrado no banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        abrirFormularioVistoria(agendamento);
    }*/

    private void abrirFormularioVistoria(Agendamento agendamento) {
        // Painel simples de exemplo — pode substituir por sua tela completa depois
        JPanel painelVistoria = new JPanel(new BorderLayout());
        painelVistoria.add(new JLabel("Formulário de Vistoria para o agendamento ID: " + agendamento.getIdAgendamento(),
                SwingConstants.CENTER), BorderLayout.CENTER);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> cardLayout.show(painelPrincipal, "dashboardVistoriador"));
        painelVistoria.add(btnVoltar, BorderLayout.SOUTH);

        painelPrincipal.add(painelVistoria, "formularioVistoria");
        cardLayout.show(painelPrincipal, "formularioVistoria");
    }
    
}
