package view;

import javax.swing.*;
import java.awt.*;

public class TesteCartao {

	public static void main(String[] args) {
		JFrame tela = new JFrame("teste cartao");
		
		tela.setDefaultCloseOperation(tela.EXIT_ON_CLOSE);
		tela.setSize(1024, 640);
		tela.setLayout(new BorderLayout());
		
		JPanel cartao = new JPanel();
		cartao.setLayout(new BorderLayout());
		
		JPanel container = new JPanel();
		container.setOpaque(false);
		container.setLayout(new GridBagLayout());
		container.setBorder(BorderFactory.createEmptyBorder(-450, -252, 0, 0));
		
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 10, 5, 10);
		gbc.anchor = GridBagConstraints.NORTHWEST;
		
		
		JLabel titulo = new JLabel("Cadastro de cartão");
		titulo.setFont(new Font("Arial", Font.PLAIN, 17));		
		
		JPanel panelTitulo = new JPanel();
		panelTitulo.setBorder(BorderFactory.createEmptyBorder(35, -255, 0, 0));
		panelTitulo.add(titulo);
		
		JLabel labelNumCartao = new JLabel("Digite o numero do cartão");
		
		JPanel panelNumCartao = new JPanel();
		panelNumCartao.add(labelNumCartao);
		
		JTextField campoNumCartao = new JTextField(17);
		campoNumCartao.setPreferredSize(new Dimension(500, 25));
		
		JPanel panelCampoNumCartao = new JPanel();
		panelCampoNumCartao.setPreferredSize(new Dimension(500, 25));
		panelCampoNumCartao.add(campoNumCartao);
		
	
		gbc.gridx = 0;
		gbc.gridy = 0;
		container.add(panelNumCartao, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		container.add(panelCampoNumCartao, gbc);
		
		cartao.add(container, BorderLayout.CENTER);
		cartao.add(panelTitulo, BorderLayout.NORTH);
		
		
		
		tela.add(cartao);
		
		tela.setVisible(true);

	}

}
}


