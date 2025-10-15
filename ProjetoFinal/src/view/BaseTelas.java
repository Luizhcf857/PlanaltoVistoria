package view;

import javax.swing.*;
import java.awt.*;
import model.Funcionario;
import model.PagamentoController;


public class BaseTelas {

    public static void main(String[] args) {
        JFrame tela = new JFrame("Planalto Vistoria");
        tela.setSize(1024, 640);
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setLayout(new BorderLayout());

        CardLayout card = new CardLayout();
        JPanel panelPrincipal = new JPanel(card);

        PagamentoController pagamento = new PagamentoController();
        Funcionario funcionario = new Funcionario();

        DashboardVistoriador telaVistoriador = new DashboardVistoriador(panelPrincipal, card);
        DashBoardGerente telaGerente = new DashBoardGerente(funcionario, panelPrincipal, card);

        TelaLogin login = new TelaLogin(panelPrincipal, card);
        DashBoardCliente telaCliente = new DashBoardCliente(panelPrincipal, card);
        //Pix pix = new Pix(panelPrincipal, card, pagamento);
        
        panelPrincipal.add(telaVistoriador, "telaVistoriador");
        panelPrincipal.add(telaGerente, "telaGerente");
        panelPrincipal.add(login, "telaLogin");
        //panelPrincipal.add(pix, "telaPix");
        panelPrincipal.add(telaCliente, "telaCliente");
       
        
        

        // Começa mostrando a tela de login
        card.show(panelPrincipal, "telaLogin");
        
        
          
        
     
        tela.add(panelPrincipal);
        tela.setVisible(true);
    }
}

	
	
	


