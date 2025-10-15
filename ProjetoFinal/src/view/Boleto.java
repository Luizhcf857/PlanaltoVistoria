package view;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import icones.Icones;
import model.PagamentoController;
import dao.PagamentoDAO;
import java.sql.Date;



public class Boleto extends JPanel {
	
	private PagamentoController pagamento;
	
	public Boleto(JPanel panelInterno, CardLayout cardsInterno,
            JPanel panelPrincipalExterno, CardLayout cardsExternos,
            PagamentoController pagamento) {
		
		this.pagamento = pagamento;
		
		JPanel telaBoleto = new JPanel();
		telaBoleto.setLayout(new BorderLayout());
		telaBoleto.setOpaque(false);
		
		JPanel container = new JPanel();
		container.setLayout(new GridBagLayout());
		container.setOpaque(false);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		
		JLabel textoBoletoGerado = new JLabel("Boleto gerado com sucesso!", SwingConstants.CENTER);
		textoBoletoGerado.setFont(new Font("Arial", Font.PLAIN, 20));
		
		JLabel informativo = new JLabel("Utilize o codigo de barras para realizar o pagamento da sua vistoria!");
		informativo.setFont(new Font("Arial", Font.PLAIN, 15));
		
		ImageIcon boleto = new ImageIcon(Icones.BOLETO);
		Image boletoRenderizado = boleto.getImage().getScaledInstance(400, 65, Image.SCALE_SMOOTH);
		ImageIcon boletoPagamento = new ImageIcon(boletoRenderizado);
		
		JLabel labelBoleto = new JLabel(boletoPagamento);
		
		JLabel numBoleto = new JLabel("34195.17515 23456.787128 34123.456005 8 10231000002603", SwingConstants.CENTER);
		numBoleto.setFont(new Font("Arial", Font.PLAIN, 15));
		numBoleto.setOpaque(false);
		
		Color corCopiarBoleto = new Color(0, 130, 0);
		
		JButton copiarBoleto = new JButton("Copiar código de barras 📋");
		copiarBoleto.setPreferredSize(new Dimension(160, 35));
		copiarBoleto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		copiarBoleto.setFocusable(false);
		copiarBoleto.setBackground(corCopiarBoleto);
		copiarBoleto.setForeground(Color.white);
		copiarBoleto.setBorder(BorderFactory.createLineBorder(Color.black));
		copiarBoleto.setFont(copiarBoleto.getFont().deriveFont(12f));
		
		JPanel panelCopiarBoleto = new JPanel();
		panelCopiarBoleto.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		panelCopiarBoleto.setPreferredSize(new Dimension(160, 35));
		panelCopiarBoleto.add(copiarBoleto);
		panelCopiarBoleto.setOpaque(false);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(45, 0, 0, 0);
		container.add(textoBoletoGerado, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(35, 0, 0, 0);
		container.add(informativo, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(30, 0, 0, 0);
		container.add(labelBoleto, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.insets = new Insets(30, 0, 0, 0);
		container.add(numBoleto, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.insets = new Insets(35, 0, 0, 0);
		container.add(panelCopiarBoleto, gbc);
		
		copiarBoleto.addActionListener(e -> {
		    String codigoBoleto = "34195.17515 23456.787128 34123.456005 8 10231000002603";
		    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(codigoBoleto), null);

		    pagamento.setFormaPagamento("Boleto");
		    pagamento.setStatusPagamento("Pago");
		    pagamento.setDataPagamento(Date.valueOf("2025-11-11"));
		    pagamento.setValor(100.00);
		    PagamentoDAO.cadastrarPagamento(pagamento);
		    

		    if (PagamentoDAO.pegarPorVistoria(pagamento.getVistoria().getIdVistoria()) == null) {
		        PagamentoDAO.cadastrarPagamento(pagamento);
		    } else {
		        PagamentoDAO.atualizarStatusPagamento(pagamento);
		    }

		    JOptionPane.showMessageDialog(this, "Código do boleto copiado e pagamento registrado!");
		    cardsExternos.show(panelPrincipalExterno, "telaCliente");
		    
		});
		
		gbc.weighty = 1.0;
		container.add(Box.createVerticalGlue(), gbc);
				
		telaBoleto.add(container, BorderLayout.CENTER);
		
		add(telaBoleto);
		setOpaque(false);
		
	}

}
