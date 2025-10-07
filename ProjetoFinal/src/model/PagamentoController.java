package model;

//foi importado a classe do pacote math para tratar big decimal
import java.sql.Date;

//Criando a entidade de pagamento
public class PagamentoController {
	
	//colocando os atributos de pagamento
	private Cliente cliente;//dentro de cliente quero ter acesso ao id
	private  Vistoria vistoria;//dentro de vistoria quero ter acesso ao id
	private Vistoria vistoriaAntiga; //foi criado esse objeto para ter uma vistoria antiga para realizar o update
	private double valor;
	private Date dataPagamento;
	private String formaPagamento;
	private String statusPagamento;
	
	
	public Cliente getCliente() {
		return cliente;
		
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		
	}
	
	public Vistoria getVistoria() {
		return this.vistoria;
		
	}
	public void setVistoria(Vistoria vistoria) {
		this.vistoria = vistoria;
		
	}
	
	public Vistoria getVistoriaAntiga() {
		return this.vistoriaAntiga;
		
	}
	public void setVistoriaAntiga(Vistoria vistoriaAntiga) {
		this.vistoriaAntiga = vistoriaAntiga;
		
	}
	
	public double getValor() {
		return this.valor;
		
	}
	public void setValor(double valor) {
		this.valor = valor;
		
	}
	
	public Date getDataPagamento() {
		return this.dataPagamento;
		
	}
	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
		
	}
	
	public String getFormaPagamento() {
		return this.formaPagamento;
		
	}
	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
		
	}
	
	public String getStatusPagamento() {
		return this.statusPagamento;
		
	}
	public void setStatusPagamento(String statusPagamento) {
		this.statusPagamento = statusPagamento;
		
	}
	
	

}
