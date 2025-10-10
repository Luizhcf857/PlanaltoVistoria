package view;

import javax.swing.*;
import java.awt.*;

public class TesteCartao {

	public static void main(String[] args) {
		JFrame tela = new JFrame("teste cartao");
		
		tela.setDefaultCloseOperation(tela.EXIT_ON_CLOSE);
		tela.setSize(824, 480);
		tela.setLayout(new BorderLayout());
		
		JPanel cartao = new JPanel();
		cartao.setLayout(new BorderLayout());
		
		JPanel container = new JPanel();
		container.setOpaque(false);
		container.setSize(824, 480);
		container.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		container.setLayout(new GridBagLayout());
		
		
		container.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 10, 5, 10);
		gbc.anchor = GridBagConstraints.NORTHWEST;
		
		
		JLabel titulo = new JLabel("Cadastro de cartão");
		titulo.setFont(new Font("Arial", Font.PLAIN, 17));
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panelTitulo = new JPanel();
		panelTitulo.setBorder(BorderFactory.createEmptyBorder(35, 0, 0, 0));
		panelTitulo.add(titulo);
		
		JLabel labelNumCartao = new JLabel("Digite o numero do cartão");
	
		
		JTextField campoNumCartao = new JTextField(17);
		campoNumCartao.setPreferredSize(new Dimension(300, 25));
		
		JPanel panelCampoNumCartao = new JPanel();
		panelCampoNumCartao.setLayout(new BorderLayout());
		panelCampoNumCartao.setPreferredSize(new Dimension(300, 25));
		panelCampoNumCartao.add(campoNumCartao);
		
		JLabel bandeiraCartao = new JLabel("Selecione a bandeira do cartao");
		
		
		
		String[] bandeiras = {"Mastercard", "Visa", "Elo"};
		
		JComboBox<String> selecionarBandeira = new JComboBox(bandeiras);
		selecionarBandeira.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		selecionarBandeira.setPreferredSize(new Dimension(150, 25));
		selecionarBandeira.setSelectedIndex(-1);
		selecionarBandeira.setFocusable(false);
		selecionarBandeira.setBackground(Color.WHITE);
		
		JLabel labelNome = new JLabel("Digite o nome do titular");
		
		
		
		
		JTextField campoNome= new JTextField(100);
		campoNome.setPreferredSize(new Dimension(300, 25));
		
		JPanel panelCampoNome = new JPanel();
		panelCampoNome.setLayout(new BorderLayout());
		panelCampoNome.setPreferredSize(new Dimension(300, 25));
		panelCampoNome.setOpaque(false);
		panelCampoNome.add(campoNome);
		
		JLabel cvc = new JLabel("Digite o cvc do cartão");
		
		
		JTextField campoCvc = new JTextField();
		campoCvc.setPreferredSize(new Dimension(100, 25));
		
		JPanel panelCampoCvc = new JPanel();
		panelCampoCvc.setLayout(new BorderLayout());
		panelCampoCvc.setPreferredSize(new Dimension(100, 25));
		panelCampoCvc.setOpaque(false);
		panelCampoCvc.add(campoCvc);
		
	
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(10, 0, 0, 0);
	
		container.add(labelNumCartao, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(10, 0, 0, 0);
		container.add(panelCampoNumCartao, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(7, 65 ,0, 0);
		container.add(bandeiraCartao, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(7, 68, 0, 0);
		container.add(selecionarBandeira, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(17, 0, 0, 0);
		container.add(labelNome, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(10, 0, 0, 0);
		container.add(panelCampoNome, gbc);
		
		gbc.gridx = 9;
		gbc.gridy = 9;
		gbc.insets = new Insets(0, 0, 0, 0);
		container.add(campoCvc);
		
		gbc.weighty = 1.0; 
		container.add(Box.createVerticalGlue(), gbc);
		
		cartao.add(container, BorderLayout.CENTER);
		cartao.add(panelTitulo, BorderLayout.NORTH);
		
		
		
		tela.add(cartao);
		
		tela.setVisible(true);

	}

}




