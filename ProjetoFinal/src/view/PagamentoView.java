package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.PagamentoController;

public class PagamentoView extends JPanel {

    private PagamentoController pagamento;

    public PagamentoView(JPanel painelPrincipal, CardLayout cardPrincipal, PagamentoController pagamento) {
        this.pagamento = pagamento;

        setLayout(new BorderLayout());

        // ---------- Título ----------
        JLabel titulo = new JLabel("Área de Pagamento", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel painelTitulo = new JPanel(new BorderLayout());
        painelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        painelTitulo.setOpaque(false);
        painelTitulo.add(titulo, BorderLayout.CENTER);

        add(painelTitulo, BorderLayout.NORTH);

        // ---------- Centralização ----------
        JPanel centralizar = new JPanel(new GridBagLayout());
        centralizar.setOpaque(false);

        JPanel armazenar = new JPanel();
        armazenar.setLayout(new BoxLayout(armazenar, BoxLayout.Y_AXIS));
        armazenar.setPreferredSize(new Dimension(824, 480));
        armazenar.setOpaque(false);
        armazenar.setBorder(BorderFactory.createLineBorder(Color.black));

        // ---------- Seleção de Pagamento ----------
        JLabel selecionarPagamento = new JLabel("Selecione a opção de pagamento:");
        selecionarPagamento.setFont(new Font("Arial", Font.PLAIN, 15));

        String[] opcoesPagamento = {"Cartão", "Pix", "Boleto"};
        JComboBox<String> selecionar = new JComboBox<>(opcoesPagamento);
        selecionar.setPreferredSize(new Dimension(200, 27));
        selecionar.setBorder(BorderFactory.createLineBorder(Color.black));
        selecionar.setBackground(Color.WHITE);
        selecionar.setFocusable(false);
        selecionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        selecionar.setSelectedIndex(-1); // valor inicial

        JPanel panelSelecionar = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelSelecionar.setOpaque(false);
        panelSelecionar.add(selecionarPagamento);
        panelSelecionar.add(selecionar);

        // ---------- Painel com telas de pagamento ----------
        CardLayout cardsInterno = new CardLayout();
        JPanel panelOpcoesPagamento = new JPanel(cardsInterno);
        panelOpcoesPagamento.setOpaque(false);

        TelaCartao telaCartao = new TelaCartao(panelOpcoesPagamento, cardsInterno, painelPrincipal, cardPrincipal, pagamento);
        Pix telaPix = new Pix(panelOpcoesPagamento, cardsInterno, painelPrincipal, cardPrincipal, pagamento);
        Boleto telaBoleto = new Boleto(panelOpcoesPagamento, cardsInterno, painelPrincipal, cardPrincipal, pagamento);

        panelOpcoesPagamento.add(telaCartao, "Cartão");
        panelOpcoesPagamento.add(telaPix, "Pix");
        panelOpcoesPagamento.add(telaBoleto, "Boleto");

        //cardsInterno.show(panelOpcoesPagamento, "Cartão"); // inicial

        // ---------- ComboBox Listener ----------
        selecionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String opcao = (String) selecionar.getSelectedItem();
                cardsInterno.show(panelOpcoesPagamento, opcao);
            }
        });

        // ---------- Montagem final ----------
        armazenar.add(Box.createVerticalStrut(20));
        armazenar.add(panelSelecionar);
        armazenar.add(Box.createVerticalStrut(15));
        armazenar.add(panelOpcoesPagamento);

        centralizar.add(armazenar);
        add(centralizar, BorderLayout.CENTER);

        setBackground(Color.LIGHT_GRAY);
    }
}
