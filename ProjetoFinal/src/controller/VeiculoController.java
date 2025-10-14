package controller;

import dao.VeiculoDAO;
import model.Veiculo;
import model.Cliente;

import java.util.List;

public class VeiculoController {

    private VeiculoDAO dao;

    public VeiculoController() {
        dao = new VeiculoDAO();
    }

    // Cadastrar um novo veículo
    public void cadastrarVeiculo(Veiculo veiculo) {
        if (veiculo == null ||
            veiculo.getPlaca() == null || veiculo.getPlaca().isEmpty() ||
            veiculo.getMarca() == null || veiculo.getMarca().isEmpty() ||
            veiculo.getModelo() == null || veiculo.getModelo().isEmpty() ||
            veiculo.getAno() == null || veiculo.getAno().isEmpty() ||
            veiculo.getNumero_chassi() == null || veiculo.getNumero_chassi().isEmpty() ||
            veiculo.getCliente() == null || veiculo.getCliente().getId_Cliente() <= 0) {
            
            System.out.println("Todos os campos são obrigatórios para cadastrar um veículo.");
            return;
        }

        VeiculoDAO.inserir(veiculo);
    }

    // Listar todos os veículos
    public void listarVeiculos() {
        List<Veiculo> veiculos = dao.listar();
        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo encontrado.");
            return;
        }

        for (Veiculo v : veiculos) {
            System.out.println("ID: " + v.getId_Veiculo()
                    + " | Placa: " + v.getPlaca()
                    + " | Marca: " + v.getMarca()
                    + " | Modelo: " + v.getModelo()
                    + " | Ano: " + v.getAno()
                    + " | Chassi: " + v.getNumero_chassi());
        }
    }

    // Atualizar veículo existente
    public void atualizarVeiculo(Veiculo veiculo) {
        if (veiculo == null || veiculo.getId_Veiculo() <= 0) {
            System.out.println("ID do veículo inválido para atualização.");
            return;
        }

        dao.atualizar(veiculo);
    }

    // Excluir veículo por ID
    public void excluirVeiculo(int idVeiculo) {
        if (idVeiculo <= 0) {
            System.out.println("ID inválido.");
            return;
        }

        dao.excluir(idVeiculo);
    }

    // Buscar veículo por placa
    public void buscarPorPlaca(String placa) {
        if (placa == null || placa.isEmpty()) {
            System.out.println("Placa inválida.");
            return;
        }

        Veiculo v = dao.buscarPorPlaca(placa);
        if (v != null) {
            System.out.println("ID: " + v.getId_Veiculo()
                    + " | Placa: " + v.getPlaca()
                    + " | Marca: " + v.getMarca()
                    + " | Modelo: " + v.getModelo()
                    + " | Ano: " + v.getAno()
                    + " | Chassi: " + v.getNumero_chassi());
        } else {
            System.out.println("Veículo não encontrado com a placa: " + placa);
        }
    }

    // Buscar veículo por número do chassi
    public void buscarPorChassi(String chassi) {
        if (chassi == null || chassi.isEmpty()) {
            System.out.println("Número do chassi inválido.");
            return;
        }

        Veiculo v = dao.buscarPorChassi(chassi);
        if (v != null) {
            System.out.println("ID: " + v.getId_Veiculo()
                    + " | Placa: " + v.getPlaca()
                    + " | Marca: " + v.getMarca()
                    + " | Modelo: " + v.getModelo()
                    + " | Ano: " + v.getAno()
                    + " | Chassi: " + v.getNumero_chassi());
        } else {
            System.out.println("Veículo não encontrado com o chassi: " + chassi);
        }
    }

    // Método para facilitar o cadastro diretamente pelos parâmetros
    public void cadastrarViaParametros(String placa, String marca, String modelo, String ano, String chassi, int idCliente) {
        if (idCliente <= 0) {
            System.out.println("Cliente inválido.");
            return;
        }

        Cliente cliente = new Cliente();
        cliente.setId_Cliente(idCliente);

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(placa);
        veiculo.setMarca(marca);
        veiculo.setModelo(modelo);
        veiculo.setAno(ano);
        veiculo.setNumero_chassi(chassi);
        veiculo.setCliente(cliente);

        cadastrarVeiculo(veiculo);
    }
}

