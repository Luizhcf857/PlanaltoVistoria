package view;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import dao.PagamentoDAO;
import model.PagamentoController;
import controller.Pagamento;

public class PagamentoView extends JPanel{
	
	public PagamentoView(JPanel principal, CardLayout card) {
		
		
		
		JPanel armazenar = new JPanel();
		armazenar.setMaximumSize(new Dimension(824, 480));
		armazenar.setLayout(new BorderLayout());
		armazenar.setAlignmentX(Component.CENTER_ALIGNMENT);
		armazenar.setOpaque(false);
		armazenar.setBorder(BorderFactory.createLineBorder(Color.black));
		

		
		JLabel titulo = new JLabel("Área de Pagamento");
		titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		titulo.setFont(new Font("Arial", Font.PLAIN, 17));
		
		JPanel armazenarTitulo = new JPanel();
		armazenarTitulo.setLayout(new BoxLayout(armazenarTitulo, BoxLayout.Y_AXIS));
		armazenarTitulo.add(Box.createVerticalStrut(0));
		armazenarTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		armazenarTitulo.setOpaque(false);
		armazenarTitulo.add(titulo);
		
		
		
		JButton selecionar = new JButton("Selecione a opção");
		selecionar.setPreferredSize(new Dimension(195, 25 ));
		selecionar.setBackground(Color.WHITE);
		selecionar.setFocusPainted(false);
		
		JPanel panelSelecionar = new JPanel();
		panelSelecionar.setLayout(new FlowLayout());
		panelSelecionar.setPreferredSize(new Dimension(200, 150));
		panelSelecionar.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		panelSelecionar.add(selecionar);
			
		
		
		//selecionar.setMaximumSize(new Dimension(100,50));
		
		armazenar.add(panelSelecionar);
		
		
		add(armazenar, BorderLayout.CENTER);
		add(armazenarTitulo, BorderLayout.NORTH);

		setBackground(Color.LIGHT_GRAY);
		
		
	}

}
