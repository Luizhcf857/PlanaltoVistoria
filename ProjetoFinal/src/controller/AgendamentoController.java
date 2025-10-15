package controller;

import model.*;
import dao.AgendamentoDAO;
import java.sql.Date;
import java.util.List;

public class AgendamentoController {

    private AgendamentoDAO dao;

    public AgendamentoController() {
        dao = new AgendamentoDAO();
    }

    public void cadastrarAgendamento(Agendamento agendamento) {
        if (agendamento == null
                || agendamento.getCliente() == null
                || agendamento.getVeiculo() == null
                || agendamento.getFuncionario() == null
                || agendamento.getDataAgendamento() == null
                || agendamento.getTipoServico() == null
                || agendamento.getTipoServico().isEmpty()) {
            System.out.println("Todos os campos são obrigatórios.");
            return;
        }

        if (agendamento.getTipoServico().length() < 3) {
            System.out.println("Tipo de serviço deve ter pelo menos 3 caracteres.");
            return;
        }

        Date agora = new Date(System.currentTimeMillis());
        int comparacao = agendamento.getDataAgendamento().compareTo(agora);

        if (comparacao < 0) {
            System.out.println("Data do agendamento não pode ser no passado.");
            return;
        }

        dao.inserir(agendamento);
        System.out.println("Agendamento cadastrado com sucesso.");
    }

    public void listarAgendamentos() {
        List<Agendamento> agendamentos = dao.listar();
        if (agendamentos.isEmpty()) {
            System.out.println("Nenhum agendamento cadastrado.");
            return;
        }

        for (Agendamento a : agendamentos) {
            System.out.println("Id: " + a.getIdAgendamento()
                    + " | Cliente ID: " + a.getCliente().getId_Cliente()
                    + " | Veículo ID: " + a.getVeiculo().getId_Veiculo()
                    + " | Funcionário ID: " + a.getFuncionario().getId_Funcionario()
                    + " | Data: " + a.getDataAgendamento()
                    + " | Serviço: " + a.getTipoServico());
        }
    }

    public void atualizarAgendamento(Agendamento agendamento) {
        if (agendamento == null
                || agendamento.getCliente() == null
                || agendamento.getVeiculo() == null
                || agendamento.getFuncionario() == null
                || agendamento.getDataAgendamento() == null
                || agendamento.getTipoServico() == null
                || agendamento.getTipoServico().isEmpty()) {
            System.out.println("Todos os campos são obrigatórios.");
            return;
        }

        if (agendamento.getTipoServico().length() < 3) {
            System.out.println("Tipo de serviço deve ter pelo menos 3 caracteres.");
            return;
        }

        dao.atualizar(agendamento);
        System.out.println("Agendamento atualizado.");
    }

    public void excluirAgendamento(int id) {
        dao.excluir(id);
        System.out.println("Agendamento excluído.");
    }

    public void exibirAgendamento(int id) {
        List<Agendamento> agendamentos = dao.listar();
        Agendamento encontrado = null;

        for (Agendamento a : agendamentos) {
            if (a.getIdAgendamento() == id) {
                encontrado = a;
                break;
            }
        }

        if (encontrado != null) {
            System.out.println("---- Detalhes do Agendamento ----");
            System.out.println("Id: " + encontrado.getIdAgendamento());
            System.out.println("Cliente ID: " + encontrado.getCliente().getId_Cliente());
            System.out.println("Funcionário ID: " + encontrado.getFuncionario().getId_Funcionario());
            System.out.println("Veículo ID: " + encontrado.getVeiculo().getId_Veiculo());
            System.out.println("Data: " + encontrado.getDataAgendamento());
            System.out.println("Tipo de Serviço: " + encontrado.getTipoServico());
        } else {
            System.out.println("Não foi possível encontrar o agendamento com ID: " + id);
        }
    }

    public void agendar(int clienteId, int funcionarioId, int veiculoId, Date dataAgendamento, String tipoServico) {
        Cliente cliente = new Cliente();
        cliente.setId_Cliente(clienteId);

        Funcionario funcionario = new Funcionario();
        funcionario.setId(funcionarioId);

        Veiculo veiculo = new Veiculo();
        veiculo.setId_Veiculo(veiculoId);

        Agendamento agendamento = new Agendamento();
        agendamento.setCliente(cliente);
        agendamento.setFuncionario(funcionario);
        agendamento.setVeiculo(veiculo);
        agendamento.setDataAgendamento(dataAgendamento);
        agendamento.setTipoServico(tipoServico);

        cadastrarAgendamento(agendamento);
    }
}
