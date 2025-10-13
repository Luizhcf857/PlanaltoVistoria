package model;

import java.sql.Date;

public class Vistoria {

    private int idVistoria;
    private Funcionario funcionario;
    private Agendamento agendamento;
    private Date dataVistoria;
    private String itensVerificados;
    private String observacao;

    // Construtores
    public Vistoria() {
    }

    public Vistoria(int idVistoria, Funcionario funcionario, Agendamento agendamento, Date dataVistoria, String itensVerificados, String observacao) {
        this.idVistoria = idVistoria;
        this.funcionario = funcionario;
        this.agendamento = agendamento;
        this.dataVistoria = dataVistoria;
        this.itensVerificados = itensVerificados;
        this.observacao = observacao;
    }

    // Getters e Setters
    public int getIdVistoria() {
        return idVistoria;
    }

    public void setIdVistoria(int idVistoria) {
        this.idVistoria = idVistoria;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Agendamento getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
    }

    public Date getDataVistoria() {
        return dataVistoria;
    }

    public void setDataVistoria(Date dataVistoria) {
        this.dataVistoria = dataVistoria;
    }

    public String getItensVerificados() {
        return itensVerificados;
    }

    public void setItensVerificados(String itensVerificados) {
        this.itensVerificados = itensVerificados;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

}
