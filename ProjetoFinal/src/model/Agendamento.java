package model;

import java.sql.Date;

public class Agendamento {
	
	private int idAgendamento;
	private Veiculo veiculo;
	private Funcionario funcionario;
	private Cliente cliente;
	private Date dataAgendamento;
	private String tipoServico;
	
	public Agendamento(){
		
	}
	
	public int getIdAgendamento() {
		return this.idAgendamento;
		
	}
	public void setIdAgendamento(int idAgendamento) {
		this.idAgendamento = idAgendamento;
		
	}
	
	public Veiculo getVeiculo() {
		return this.veiculo;
		
	}
	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
		
	}
	
	public Funcionario getFuncionario() {
		return this.funcionario;
		
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
		
	}
	
	public Cliente getCliente() {
		return cliente;
		
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		
	}
	
	public Date getDataAgendamento() {
		return this.dataAgendamento;
		
	}
	public void setDataAgendamento(Date dataAgendamento) {
		this.dataAgendamento = dataAgendamento;
		
	}
	
	public String getTipoServico() {
		return this.tipoServico;
		
	}
	public void setTipoServico(String tipoServico) {
		this.tipoServico = tipoServico;
		
	}
	

}
