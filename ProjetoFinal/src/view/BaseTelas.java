package view;

import javax.swing.*;
import java.awt.*;
import model.Funcionario;



public class BaseTelas {
	
	public static void main(String[] args) {
		//criando o frame com o nome do sistema
		JFrame tela = new JFrame("Planalto Vistoria");
		//definindo tamanho funcao de fechar e o tipo de layout
		tela.setSize(1024, 640);
		tela.setDefaultCloseOperation(tela.EXIT_ON_CLOSE);
		tela.setLayout(new BorderLayout());
		
		//criando o card layout para transições e o JPanel principal
		CardLayout card = new CardLayout();
		JPanel panelPrincipal = new JPanel(card);
		
		PagamentoView telaPagamento = new PagamentoView(panelPrincipal, card);
	
		Funcionario funcionario = new Funcionario();
		
		DashboardVistoriador telaVistoriador = new DashboardVistoriador(panelPrincipal, card);
		
		DashBoardGerente telaGerente = new DashBoardGerente(funcionario, panelPrincipal, card);
		
		TelaLogin login = new TelaLogin(panelPrincipal, card);
		
		
		
		panelPrincipal.add(telaVistoriador, "telaVistoriador");
		
		panelPrincipal.add(telaGerente, "telaGerente");
		
		panelPrincipal.add(telaPagamento, "telaPagamento");
		
		panelPrincipal.add(login, "telaLogin");
		
		card.show(panelPrincipal, "telaLogin");
		
		
		//card.show(panelPrincipal, "telaPagamento");
		
		tela.add(panelPrincipal);
		
		
		//colocando visibilidade para aparecer a tela
		tela.setVisible(true);
		
		
	}
	
	
	

}
