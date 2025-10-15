package controller;

import dao.FuncionarioDAO;
import model.Funcionario;
import java.util.List;

public class FuncionarioController {
	private FuncionarioDAO dao;

	public FuncionarioController() {
		dao = new FuncionarioDAO();
	}

	public void cadastrarFuncionario(Funcionario funcionario) {
		if (funcionario.getNome().isEmpty() || funcionario.getCpf().isEmpty() || funcionario.getCargo().isEmpty()
				|| funcionario.getTelefone().isEmpty()) {
			System.out.println("Todos os campos são Obrigatorios.");
			return;
		}
		if (funcionario.getCpf().length() != 11) {
			System.out.println("Cpf deve conter 11 digitos.");
			return;

		}
		if (funcionario.getCpf().length() < 10 || funcionario.getTelefone().length() > 11) {
			System.out.println("Telefone deve conter 10 ou 11 digititos.");
			return;

		}

		dao.inserir(funcionario);

	}

	public List<Funcionario> ListarFuncionarios() {
		List<Funcionario> funcionario = dao.listar();
		if (funcionario.isEmpty()) {
			System.out.println("Nenhum funcionario cadastrado.");
			
		}
		for (Funcionario f : funcionario) {
			System.out.println("Id:" + f.getId_Funcionario() + "  | Nome:" + f.getNome() + " | CPF:" + f.getCpf()
					+ "| cargo: " + f.getCargo() + " | Telefone:" + f.getTelefone());

		}
		return funcionario;
	}

	public void atualizarFuncionario(Funcionario funcionario) {
		if (funcionario.getNome().isEmpty() || funcionario.getCpf().isEmpty() || funcionario.getCargo().isEmpty()
				|| funcionario.getTelefone().isEmpty()) {
			System.out.println("Todos os campos são obrigatorios.");
			return;

		}
		if (funcionario.getCpf().length() != 11) {
			System.out.println("CPF deve conter 11 digitos.");
			return;
		}
		dao.atualizar(funcionario);

	}

	public boolean excluirFuncionarioComVerificacao(int id) {
        try {
            dao.excluir(id);
            return true; // sucesso
        } catch (Exception e) {
            System.out.println("Erro ao excluir funcionário: " + e.getMessage()); // log no terminal
            return false; // falha
        }
    }

	public Funcionario buscarFuncionarioPorId(int id) {
		return dao.buscarPorId(id);
	}

	public void exibirFuncionario(int id) {
		Funcionario funcionario = dao.buscarPorId(id);
		if (funcionario != null) {
			System.out.println("Id:" + funcionario.getId_Funcionario() + "| Nome: " + funcionario.getNome() + " | Cpf: "
					+ funcionario.getCargo() + "| Telefone:" + funcionario.getTelefone());

		}
		
	}
	
	public Funcionario autenticar(String cpf, String senha) {
	    FuncionarioDAO dao = new FuncionarioDAO();
	    return dao.autenticar(cpf, senha);
	}

}
