package model;

import java.time.LocalDate;
import java.sql.Date;

public class Vistoria {
	
	private int idVistoria;
	private Funcionario funcionario;
	private Agendamento agendamento;
	private Date dataVistoria;
	private String itensVerificados;
	private String observacao;

	public Vistoria() {
	}
	
	public Vistoria(int idVistoria, Funcionario funcionarios, Agendamento agendamento,Date dataVistoria, String itensVerificados, String observacao) {
		super();
		this.idVistoria = idVistoria;
		this.funcionario = funcionario;
		this.agendamento = agendamento;
		this.dataVistoria = dataVistoria;
		this.itensVerificados  = itensVerificados;
		this.observacao = observacao;
	}

	//get's
	public int getidVistoria() {
	    return idVistoria;
	}

	public Funcionario getFuncionario() {
	    return funcionario;
	}

	public Agendamento getAgendamento() {
	    return agendamento;
	}

	public Date getDataVistoria() {
	    return dataVistoria;
	}

	public String getItensVerificados() {
	    return itensVerificados;
	}

	public String getObservacao() {
	    return observacao;
	}

	//set's

	public void setIdVistoria(int idVistoria) {
	    this.idVistoria = idVistoria;
	}

	public void setFuncionario(Funcionario funcionario) {
	    this.funcionario = funcionario;
	}

	public void setIdAgendamento(Agendamento agendamento) {
	    this.agendamento = agendamento;
	}

	public void setDataVistoria(Date dataVistoria) {
	    this.dataVistoria = dataVistoria;
	}

	public void setItensVerificados(String itensVerificados) {
	    this.itensVerificados = itensVerificados;
	}

	public void setObservacao(String observacao) {
	    this.observacao = observacao;
	}
}
