package view;

import javax.swing.*;
import java.awt.*;

public class TesteCartao {

	public static void main(String[] args) {
		JFrame tela = new JFrame("teste cartao");
		
		tela.setDefaultCloseOperation(tela.EXIT_ON_CLOSE);
		tela.setSize(1024, 640);
		tela.setLayout(new BorderLayout());
		
		JPanel container = new JPanel();
		container.setOpaque(false);
		container.setLayout(new GridBagLayout());
		
		JPanel cartao = new JPanel();
		cartao.setLayout(new BorderLayout());
		
		JLabel infoCartao = new JLabel("Cadastro de Cartão");
		infoCartao.setFont(new Font("Arial", Font.PLAIN, 19));
		
		JPanel panelInfoCartao = new JPanel();
		panelInfoCartao.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelInfoCartao.setBorder(BorderFactory.createEmptyBorder(10, 155, 0, 0));
		panelInfoCartao.add(infoCartao);
		
		JPanel panelLabelNumCartao = new JPanel();
		panelLabelNumCartao.setLayout(new FlowLayout());
		panelLabelNumCartao.setBorder(BorderFactory.createEmptyBorder(30, 155, 0 ,0));
		
		JLabel labelNumCartao = new JLabel("Digite o numero do cartão");
		panelLabelNumCartao.add(labelNumCartao);
		
		JPanel campoNumCartao = new JPanel();
		campoNumCartao.setLayout(new BoxLayout(campoNumCartao, BoxLayout.Y_AXIS));
		
		
		JTextField numCartao = new JTextField(15);
		campoNumCartao.setAlignmentX(Component.LEFT_ALIGNMENT);
		campoNumCartao.setMaximumSize(new Dimension(100, 35));
		campoNumCartao.add(numCartao);
		
		GridBagConstraints gbcPanelInfoCartao = new GridBagConstraints();
		gbcPanelInfoCartao.anchor = GridBagConstraints.WEST;
		gbcPanelInfoCartao.gridy = 0;
		gbcPanelInfoCartao.gridx = 0;
		gbcPanelInfoCartao.insets = new Insets(-530, -400, 0, 0);
		
		
		GridBagConstraints gbcLabelNumCartao = new GridBagConstraints();
		gbcLabelNumCartao.insets = new Insets(10, 0, 0, 0);
		
		
		
		container.add(panelInfoCartao, gbcPanelInfoCartao);
		container.add(panelLabelNumCartao, gbcLabelNumCartao);
		//container.add(campoNumCartao, gbc);
		cartao.add(container);
		
		
		
		tela.add(cartao);
		
		tela.setVisible(true);

	}

}

