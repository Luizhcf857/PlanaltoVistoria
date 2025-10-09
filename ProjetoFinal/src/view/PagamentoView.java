package view;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import dao.PagamentoDAO;
import model.PagamentoController;
import controller.Pagamento;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PagamentoView extends JPanel{
	
	public PagamentoView(JPanel principal, CardLayout card) {
		
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
		
		JPanel armazenar = new JPanel();
		armazenar.setLayout(new BoxLayout(armazenar, BoxLayout.Y_AXIS));
		armazenar.setPreferredSize(new Dimension(824, 480));
		armazenar.setOpaque(false);
		armazenar.setBorder(BorderFactory.createLineBorder(Color.black));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(-45, 0, 0, 0);
		centralizar.add(armazenar, gbc);
		
		
		JLabel selecionarOpcao = new JLabel("Selecione a opção de pagamento");
		selecionarOpcao.setFont(new Font("Arial", Font.PLAIN, 17));
		selecionarOpcao.setForeground(Color.WHITE);
		
		
		String[] opcoesPagamento = {"Cartão", "Pix", "Boleto"};
		
		JComboBox<String> selecionar = new JComboBox<>(opcoesPagamento);
		selecionar.setSelectedIndex(-1);
		selecionar.setPreferredSize(new Dimension(200, 27));
		selecionar.setAlignmentX(Component.CENTER_ALIGNMENT);
		selecionar.setBorder(BorderFactory.createLineBorder(Color.black));
		selecionar.setBackground(Color.WHITE);
		selecionar.setFocusable(false);
		selecionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		JPanel panelSelecionar = new JPanel();
		panelSelecionar.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
		panelSelecionar.setPreferredSize(new Dimension(200, 150));
		panelSelecionar.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		panelSelecionar.setOpaque(false);
		
		CardLayout cardInterno = new CardLayout();
		JPanel gerenciamentoPagamento = new JPanel(cardInterno);
		
		JPanel telaCartao = new JPanel();
		
		selecionar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String opcao = (String) selecionar.getSelectedItem();
				
				
			}	
			
		});
		
		
		panelSelecionar.add(selecionarOpcao);
		panelSelecionar.add(selecionar);
		
		
		//selecionar.setMaximumSize(new Dimension(100,50));
		
		armazenar.add(panelSelecionar);
		
		
		
		add(centralizar);
		add(armazenarTitulo, BorderLayout.NORTH);
		

		setBackground(Color.LIGHT_GRAY);
		
		
	}

}