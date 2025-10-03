package dao;

import model.Agendamento;
import model.Veiculo;
import model.Funcionario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import db.Conexao;
import model.Cliente;


public class AgendamentoDAO {
	public void inserir(Agendamento agendamento) {
		String sql = "INSERT INTO agendamento (Id_Cliente, Id_Veiculo, Id_Funcionario, data_agendamento, Tipo_Servico) VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, agendamento.getCliente().getId_Cliente());
			stmt.setInt(2, agendamento.getVeiculo().getId_Veiculo());
			stmt.setInt(3, agendamento.getFuncionario().getId_Funcionario());
			stmt.setDate(4, agendamento.getDataAgendamento());
			stmt.setString(5, agendamento.getTipoServico());
			stmt.executeUpdate();
			System.out.println("Agendamento cadastrado com sucesso!");
		} catch (SQLException e) {
			System.out.println("Erro ao cadastrar agendamento: " + e.getMessage());
		}
	}

	public List<Agendamento> listar() {
		List<Agendamento> lista = new ArrayList<>();
		String sql = "SELECT * FROM agendamento";
		try (Connection conn = Conexao.conectar();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				Agendamento agendamento = new Agendamento();
				agendamento.setIdAgendamento(rs.getInt("id"));

				Cliente cliente = new Cliente();
				cliente.setId(rs.getInt("Id_Cliente"));
				agendamento.setCliente(cliente);

				Veiculo veiculo = new Veiculo();
				veiculo.setId_Veiculo(rs.getInt("Id_Veiculo"));
				agendamento.setVeiculo(veiculo);

				Funcionario funcionario = new Funcionario();
				funcionario.setId(rs.getInt("Id_Funcionario"));
				agendamento.setFuncionario(funcionario);

				agendamento.setDataAgendamento(rs.getDate("data_agendamento"));
				agendamento.setTipoServico(rs.getString("Tipo_Servico"));

				lista.add(agendamento);

			}
		} catch (SQLException e) {
			System.out.println("Erro ao listar agendamentos: " + e.getMessage());
		}
		return lista;
	}

	public void atualizar(Agendamento agendamento) {
		String sql = "UPDATE agendamento SET Id_Cliente = ?, Id_Veiculo = ?, funcionario_id = ?, data_agendamento = ?, Tipo_Servico = ?,  WHERE id = ?";
		try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, agendamento.getCliente().getId_Cliente());
			stmt.setInt(2, agendamento.getVeiculo().getId_Veiculo());
			stmt.setInt(3, agendamento.getFuncionario().getId_Funcionario());
			stmt.setDate(4, agendamento.getDataAgendamento());
			stmt.setString(5, agendamento.getTipoServico());
			stmt.setInt(5, agendamento.getIdAgendamento());
			stmt.executeUpdate();
			System.out.println("Agendamento atualizado com sucesso!");
		} catch (SQLException e) {
			System.out.println("Erro ao atualizar agendamento: " + e.getMessage());
		}
	}

	public void excluir(int id) {
		String sql = "DELETE FROM agendamento WHERE Id_Agendamento = ?";
		try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
			System.out.println("Agendamento excluído com sucesso!");
		} catch (SQLException e) {
			System.out.println("Erro ao excluir agendamento: " + e.getMessage());
		}
	}
}
