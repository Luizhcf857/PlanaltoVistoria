package view;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import dao.PagamentoDAO;
import model.PagamentoController;
import controller.Pagamento;

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
		
		String[] opcoesPagamento = {"Cartão", "Pix", "Boleto"};
	
		
		JComboBox<String> selecionar = new JComboBox<>(opcoesPagamento);
		selecionar.setPreferredSize(new Dimension(200, 27));
		selecionar.setAlignmentX(Component.CENTER_ALIGNMENT);
		selecionar.setBorder(BorderFactory.createLineBorder(Color.black));
		selecionar.setBackground(Color.WHITE);
		
		JPanel panelSelecionar = new JPanel();
		panelSelecionar.setPreferredSize(new Dimension(200, 150));
		panelSelecionar.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		panelSelecionar.setOpaque(false);
		panelSelecionar.add(selecionar);
			
		
		
		//selecionar.setMaximumSize(new Dimension(100,50));
		
		armazenar.add(panelSelecionar);
		
		
		
		add(centralizar);
		add(armazenarTitulo, BorderLayout.NORTH);
		

		setBackground(Color.LIGHT_GRAY);
		
		
	}

}
