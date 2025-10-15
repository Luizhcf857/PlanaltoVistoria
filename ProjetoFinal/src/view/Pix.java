package view;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.sql.Date;

import icones.Icones;
import model.PagamentoController;
import dao.PagamentoDAO;

public class Pix extends JPanel{

	public Pix(JPanel panelInterno, CardLayout cardsInterno, 
            JPanel panelPrincipalExterno, CardLayout cardsExternos,
            PagamentoController pagamento) {

		
		JPanel telaPix = new JPanel();
		telaPix.setLayout(new BorderLayout());
		telaPix.setOpaque(false);
		

		JPanel container = new JPanel();
		container.setLayout(new GridBagLayout());
		container.setOpaque(false);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		
		
		ImageIcon iconeSucesso = new ImageIcon(Icones.SUCESSO_PIX);
		iconeSucesso = Icones.removerFundoBranco(iconeSucesso);
		Image iconeRenderizado = iconeSucesso.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon iconeSucessoRenderizado = new ImageIcon(iconeRenderizado);
		
		JLabel iconeConfirmacao = new JLabel(iconeSucessoRenderizado);
		iconeConfirmacao.setOpaque(false);
		
		JLabel mensagem = new JLabel("Qr code pix gerado com sucesso");
		mensagem.setFont(new Font("Aria", Font.PLAIN, 17));
		
		ImageIcon qrCode = new ImageIcon(Icones.QR_CODE);
		Image qrRenderizado = qrCode.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon iconeQrCode = new ImageIcon(qrRenderizado);
		
		JLabel pagamentoQr = new JLabel(iconeQrCode);
		pagamentoQr.setOpaque(false);
		
		Color corBotao = new Color(0, 130, 0);
		
		JButton copiar = new JButton("Copiar codigo pix 📋");
		copiar.setBackground(corBotao);
		copiar.setForeground(Color.white);
		copiar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		copiar.setFont(copiar.getFont().deriveFont(13f));
		copiar.setFocusable(false);
		copiar.setPreferredSize(new Dimension(150, 35));
		copiar.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JPanel panelCopiar = new JPanel();
		panelCopiar.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		panelCopiar.setPreferredSize(new Dimension(150, 35));
		panelCopiar.add(copiar);
		panelCopiar.setOpaque(false);
		
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(30, 0, 0, 0);
		container.add(iconeConfirmacao, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 0, 0, 0);
		container.add(mensagem, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(50, 0, 0, 0);
		container.add(pagamentoQr, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(20, 0, 0, 0);
		container.add(panelCopiar, gbc);
		
		copiar.addActionListener(e -> {
		    String codigoPix = "00020126380014BR.GOV.BCB.PIX0114...";
		    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(codigoPix), null);

		    pagamento.setFormaPagamento("Pix");
		    pagamento.setStatusPagamento("Pago");
		    pagamento.setValor(110.00);
		    pagamento.setDataPagamento(Date.valueOf("2025-11-12"));
		    
		    PagamentoDAO.cadastrarPagamento(pagamento);
		    
		    if (PagamentoDAO.pegarPorVistoria(pagamento.getVistoria().getIdVistoria()) == null) {
		        PagamentoDAO.cadastrarPagamento(pagamento);
		    } else {
		        PagamentoDAO.atualizarStatusPagamento(pagamento);
		    }

		    JOptionPane.showMessageDialog(this, "Código PIX copiado e pagamento registrado!");
		    cardsExternos.show(panelPrincipalExterno, "telaCliente");
		});

		
		gbc.weighty = 1.0;
		container.add(Box.createVerticalGlue(), gbc);
		
		telaPix.add(container, BorderLayout.CENTER);
		
		add(telaPix);
		setOpaque(false);
		
	}
	

}
