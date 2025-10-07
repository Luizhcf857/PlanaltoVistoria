package view;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import dao.PagamentoDAO;
import model.PagamentoController;
import controller.Pagamento;

public class PagamentoView extends JPanel{
	
	public PagamentoView(JPanel principal, CardLayout card) {
		
		
		
		JLabel titulo = new JLabel("Área de Pagamento");
		titulo.setBounds(20, 50, 0, 0);
		titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		titulo.setFont(new Font("Arial", Font.BOLD, 24));
		
		add(titulo);
		setBackground(Color.LIGHT_GRAY);
		
		
	}

}
