package controller;

import dao.VistoriaDAO;
import model.Vistoria;
import model.Funcionario;
import model.Agendamento;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class VistoriaController {

    private VistoriaDAO vistoriaDAO = new VistoriaDAO();

    // Lista local para testes (sem banco)
    private List<Vistoria> listaVistorias = new ArrayList<>();

    // ==============================
    // MÉTODOS USANDO O BANCO DE DADOS
    // ==============================

    public void adicionarVistoriaBD(Vistoria vistoria) {
        try {
            boolean sucesso = vistoriaDAO.inserirVistoria(vistoria);
            if (sucesso) {
                System.out.println("Vistoria adicionada com sucesso no banco de dados!");
            } else {
                System.out.println("Falha ao adicionar vistoria.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao adicionar vistoria: " + e.getMessage());
        }
    }

    public List<Vistoria> listarVistoriasBD() {
        try {
            List<Vistoria> vistorias = vistoriaDAO.listar();
            if (vistorias.isEmpty()) {
                System.out.println("Nenhuma vistoria encontrada no banco de dados.");
            }
            return vistorias;
        } catch (Exception e) {
            System.err.println("Erro ao listar vistorias: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // OBS: esse método abaixo exige que você crie o método buscarVistoriaPorId(int id) no DAO
    public Vistoria buscarVistoriaBD(int id) {
        System.err.println("Método buscarVistoriaPorId ainda não implementado no DAO.");
        return null;
    }

    public void atualizarVistoriaBD(Vistoria vistoria) {
        try {
            vistoriaDAO.atualizar(vistoria);
            System.out.println("Vistoria atualizada com sucesso no banco!");
        } catch (Exception e) {
            System.err.println("Erro ao atualizar vistoria: " + e.getMessage());
        }
    }

    public void removerVistoriaBD(int id) {
        try {
            vistoriaDAO.excluir(id);
            System.out.println("Vistoria removida com sucesso do banco!");
        } catch (Exception e) {
            System.err.println("Erro ao remover vistoria: " + e.getMessage());
        }
    }

    // ==============================
    // MÉTODOS USANDO LISTA LOCAL (TESTE)
    // ==============================

    public void adicionarVistoria(Vistoria vistoria) {
        listaVistorias.add(vistoria);
        System.out.println("Vistoria adicionada localmente com sucesso!");
    }

    public void listarVistorias() {
        if (listaVistorias.isEmpty()) {
            System.out.println("Nenhuma vistoria cadastrada localmente.");
        } else {
            for (Vistoria v : listaVistorias) {
                System.out.println("ID: " + v.getIdVistoria() +
                        " | Funcionário: " + (v.getFuncionario() != null ? v.getFuncionario().getId_Funcionario() : "N/A") +
                        " | Agendamento: " + (v.getAgendamento() != null ? v.getAgendamento().getIdAgendamento() : "N/A") +
                        " | Data: " + v.getDataVistoria() +
                        " | Itens: " + v.getItensVerificados() +
                        " | Observação: " + v.getObservacao());
            }
        }
    }

    public Vistoria buscarVistoria(int id) {
        for (Vistoria v : listaVistorias) {
            if (v.getIdVistoria() == id) {
                return v;
            }
        }
        System.out.println("Vistoria não encontrada localmente.");
        return null;
    }

    public void atualizarVistoria(int id, Date novaData, String novosItens, String novaObs) {
        Vistoria v = buscarVistoria(id);
        if (v != null) {
            v.setDataVistoria(novaData);
            v.setItensVerificados(novosItens);
            v.setObservacao(novaObs);
            System.out.println("Vistoria atualizada localmente com sucesso!");
        } else {
            System.out.println("Vistoria não encontrada para atualização.");
        }
    }

    public void removerVistoria(int id) {
        Vistoria v = buscarVistoria(id);
        if (v != null) {
            listaVistorias.remove(v);
            System.out.println("Vistoria removida localmente com sucesso!");
        } else {
            System.out.println("Vistoria não encontrada para remoção.");
        }
    }
}
