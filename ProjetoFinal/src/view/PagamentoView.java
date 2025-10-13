package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import dao.PagamentoDAO;
import model.PagamentoController;
import controller.Pagamento;

public class PagamentoView extends JPanel{
	
	 private PagamentoController pagamento;
	
	public PagamentoView(JPanel principal, CardLayout card, PagamentoController pagamento) {
		this.pagamento = pagamento;
		
		setLayout(new BorderLayout());
		
		
		
		
		
		JLabel titulo = new JLabel("Área de Pagamento", SwingConstants.CENTER);
		titulo.setFont(new Font("Arial", Font.PLAIN, 20));
		
		JPanel armazenarTitulo = new JPanel();
		armazenarTitulo.setLayout(new BorderLayout());
		armazenarTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		armazenarTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		armazenarTitulo.setOpaque(false);
		armazenarTitulo.add(titulo);
		
		JPanel centralizar = new JPanel();
		centralizar.setOpaque(false);
		centralizar.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		
		
		JPanel armazenar = new JPanel();
		armazenar.setLayout(new BoxLayout(armazenar, BoxLayout.Y_AXIS));
		armazenar.setPreferredSize(new Dimension(824, 480));
		armazenar.setOpaque(false);
		armazenar.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JLabel selecionarPagamento = new JLabel("Selecione a opção de pagamento");
		selecionarPagamento.setFont(new Font("Arial", Font.PLAIN, 15));
		
		String[] opcoesPagamento = {"Cartão", "Pix", "Boleto"};
	
		
		JComboBox<String> selecionar = new JComboBox<>(opcoesPagamento);
		selecionar.setPreferredSize(new Dimension(200, 27));
		//selecionar.setAlignmentX(Component.CENTER_ALIGNMENT);
		selecionar.setBorder(BorderFactory.createLineBorder(Color.black));
		selecionar.setBackground(Color.WHITE);
		selecionar.setFocusable(false);
		selecionar.setSelectedIndex(-1);
		selecionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		JPanel panelSelecionar = new JPanel();
		panelSelecionar.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
		panelSelecionar.setPreferredSize(new Dimension(200, 27));
		panelSelecionar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		panelSelecionar.setOpaque(false);
		panelSelecionar.add(selecionarPagamento);
		panelSelecionar.add(selecionar);
		
		
			
		
		CardLayout cards = new CardLayout();
		JPanel panelPrincipal = new JPanel(cards);
		panelPrincipal.setOpaque(false);
		
		
		TelaCartao cartao = new TelaCartao(panelPrincipal, cards, pagamento);
		
		JPanel vazio = new JPanel();
		vazio.setOpaque(false);
		
		Pix telaPix = new Pix(panelPrincipal, cards, pagamento);
		
		Boleto telaBoleto = new Boleto(panelPrincipal, cards, pagamento);
		
		
		panelPrincipal.add(vazio, "Vazio");
		panelPrincipal.add(cartao, "Cartão");
		panelPrincipal.add(telaPix, "Pix");
		panelPrincipal.add(telaBoleto, "Boleto");
		
		
		
		cards.show(panelPrincipal, "Vazio");
		
		selecionar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String opcao = (String) selecionar.getSelectedItem();
				cards.show(panelPrincipal, opcao);
				
			}
			
		});
		
		
		
		
		
		armazenar.add(Box.createVerticalStrut(20));
		armazenar.add(panelSelecionar);
		armazenar.add(panelPrincipal);
		centralizar.add(armazenar);
	
		
		
		//armazenar.add(panelSelecionar);
		
		
		
		add(centralizar);
		add(armazenarTitulo, BorderLayout.NORTH);
		

		setBackground(Color.LIGHT_GRAY);
		
		
	}

}