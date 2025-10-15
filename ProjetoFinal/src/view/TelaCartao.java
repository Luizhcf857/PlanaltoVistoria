package view;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;

import model.PagamentoController;
import dao.PagamentoDAO;

public class TelaCartao extends JPanel {

		public TelaCartao(JPanel panelInterno, CardLayout cardsInterno,
                JPanel panelPrincipalExterno, CardLayout cardsExternos,
                PagamentoController pagamento) {
			
			
			JPanel cartao = new JPanel();
			cartao.setLayout(new BorderLayout());
			cartao.setOpaque(false);
			
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
			panelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
			panelTitulo.add(titulo);
			panelTitulo.setOpaque(false);
			
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
			
			
			JTextField campoCvc = new JTextField(3);
			campoCvc.setPreferredSize(new Dimension(120, 25));
			
			JPanel panelCampoCvc = new JPanel();
			panelCampoCvc.setLayout(new BorderLayout());
			panelCampoCvc.setPreferredSize(new Dimension(120, 25));
			panelCampoCvc.setOpaque(false);
			panelCampoCvc.add(campoCvc);
			
			JLabel labelCpf = new JLabel("Digite o cpf do titular");
			
			JTextField campoCpf = new JTextField(11);
			campoCpf.setPreferredSize(new Dimension(200, 25));
			
			JPanel panelCampoCpf = new JPanel();
			panelCampoCpf.setLayout(new BorderLayout());
			panelCampoCpf.setPreferredSize(new Dimension(200, 25));
			panelCampoCpf.setOpaque(false);
			panelCampoCpf.add(campoCpf);
			
			JLabel dataValidade = new JLabel("Digita a data de validade do cartão(dd/mm/yyyy)");
			
			JTextField campoDataValidade = new JTextField(12);
			campoDataValidade.setPreferredSize(new Dimension(150, 25));
			
			JPanel panelDataValidade = new JPanel();
			panelDataValidade.setPreferredSize(new Dimension(150, 25));
			panelDataValidade.setLayout(new BorderLayout());
			panelDataValidade.setOpaque(false);
			panelDataValidade.add(campoDataValidade);
			
			JPanel botoes = new JPanel();
			botoes.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
			botoes.setOpaque(false);
			
			Color corBotaoContinuar = new Color(0, 130, 0);
			
			JButton continuar = new JButton("continuar");
			continuar.setPreferredSize(new Dimension(100,30));
			continuar.setBackground(corBotaoContinuar);
			continuar.setForeground(Color.white);
			continuar.setFont(new Font("Arial", Font.PLAIN, 15));
			continuar.setBorder(BorderFactory.createLineBorder(Color.black));
			continuar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			continuar.setFocusable(false);
			
			
			JButton cancelar = new JButton("cancelar");
			cancelar.setPreferredSize(new Dimension(100, 30));
			cancelar.setBackground(Color.LIGHT_GRAY);
			cancelar.setForeground(Color.black);
			cancelar.setFont(new Font("Arial", Font.PLAIN, 15));
			cancelar.setBorder(BorderFactory.createLineBorder(Color.black));
			cancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			cancelar.setFocusable(false);
			
			botoes.add(continuar);
			botoes.add(cancelar);
			
			
			
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
			
			gbc.gridx = 1;
			gbc.gridy = 3;
			gbc.insets = new Insets(-17, 65, 0, 0);
			container.add(cvc, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 4;
			gbc.insets = new Insets(-26, 68, 0, 0);
			container.add(panelCampoCvc, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 5;
			gbc.insets = new Insets(17, 0, 0, 0);
			container.add(labelCpf, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 6;
			gbc.insets = new Insets(10, 0, 0, 0);
			container.add(panelCampoCpf, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 5;
			gbc.insets = new Insets(17, 65, 0, 0);
			container.add(dataValidade, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 6;
			gbc.insets = new Insets(10, 65, 0, 0);
			container.add(panelDataValidade, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 7;
			gbc.gridwidth = 3;
			gbc.anchor = GridBagConstraints.CENTER;
			gbc.insets = new Insets(70, -10, 0, 0);
			container.add(botoes, gbc);
			
			continuar.addActionListener(e -> {
			    if (selecionarBandeira.getSelectedIndex() == -1 || campoNumCartao.getText().isEmpty()) {
			        JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
			        return;
			    }

			    // Define a forma de pagamento
			    pagamento.setStatusPagamento("Pendente");
			    pagamento.setFormaPagamento("Cartao Debito");
			    pagamento.setValor(150.00);
			    pagamento.setDataPagamento(Date.valueOf("2025-11-01"));
			   
			    PagamentoDAO.cadastrarPagamento(pagamento);
			    
			    // Opcional: preencher mais dados, se quiser salvar no banco detalhes do cartão
			    // pagamento.setNumeroCartao(campoNumCartao.getText());

			    // Atualiza ou cadastra pagamento
			    if (PagamentoDAO.pegarPorVistoria(pagamento.getVistoria().getIdVistoria()) == null) {
			        PagamentoDAO.cadastrarPagamento(pagamento);
			    } else {
			        PagamentoDAO.atualizarStatusPagamento(pagamento);
			    }

			    JOptionPane.showMessageDialog(this, "Pagamento realizado com sucesso!");
			    cardsExternos.show(panelPrincipalExterno, "telaCliente");
			});
			
			
			gbc.weighty = 1.0; 
			container.add(Box.createVerticalGlue(), gbc);
			
			
			
			
			cartao.add(container, BorderLayout.CENTER);
			cartao.add(panelTitulo, BorderLayout.NORTH);
			cartao.setOpaque(false);
			
			
			
			
			add(cartao);
			setOpaque(false);
		
	}
}

