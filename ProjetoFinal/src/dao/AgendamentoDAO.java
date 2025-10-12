package dao;

import model.Agendamento;
import model.Veiculo;
import model.Funcionario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import db.Conexao;
import model.Cliente;
import java.sql.Date;


public class AgendamentoDAO {
	public static void inserir(Agendamento agendamento) {
		String sql = "INSERT INTO agendamento (Id_Clientes, Id_Veiculos, Id_Funcionarios, data_agendamento, Tipo_Servico) VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setInt(1, agendamento.getCliente().getId_Cliente());
			stmt.setInt(2, agendamento.getVeiculo().getId_Veiculo());
			stmt.setInt(3, agendamento.getFuncionario().getId_Funcionario());
			stmt.setDate(4, agendamento.getDataAgendamento());
			stmt.setString(5, agendamento.getTipoServico());
			stmt.executeUpdate();
			System.out.println("Agendamento cadastrado com sucesso!");
			
			try(ResultSet rs = stmt.getGeneratedKeys()){
				
				if(rs.next()) {
					agendamento.setIdAgendamento(rs.getInt(1));
					
					System.out.println(agendamento.getIdAgendamento());
					
				}
				
			}
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
				agendamento.setIdAgendamento(rs.getInt("Id_Agendamento"));

				Cliente cliente = new Cliente();
				cliente.setId_Cliente(rs.getInt("Id_Clientes"));
				agendamento.setCliente(cliente);

				Veiculo veiculo = new Veiculo();
				veiculo.setId_Veiculo(rs.getInt("Id_Veiculos"));
				agendamento.setVeiculo(veiculo);

				Funcionario funcionario = new Funcionario();
				funcionario.setId(rs.getInt("Id_Funcionarios"));
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
	
	public static List<Agendamento> listarPorCliente(int idCliente) {
	    List<Agendamento> resultado = new ArrayList<>();
	    String sql = "SELECT * FROM agendamento WHERE Id_Clientes = ?";
	    
	    try (Connection conn = Conexao.conectar();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, idCliente);
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                Agendamento agendamento = new Agendamento();
	                agendamento.setIdAgendamento(rs.getInt("Id_Agendamento"));
	                
	                Cliente cliente = new Cliente();
	                cliente.setId_Cliente(rs.getInt("Id_Clientes"));
	                agendamento.setCliente(cliente);

	                Veiculo veiculo = new Veiculo();
	                veiculo.setId_Veiculo(rs.getInt("Id_Veiculos"));
	                agendamento.setVeiculo(veiculo);

	                Funcionario funcionario = new Funcionario();
	                funcionario.setId(rs.getInt("Id_Funcionarios"));
	                agendamento.setFuncionario(funcionario);

	                agendamento.setDataAgendamento(rs.getDate("Data_Agendamento"));
	                agendamento.setTipoServico(rs.getString("Tipo_Servico"));

	                resultado.add(agendamento);
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Erro ao listar agendamentos por cliente: " + e.getMessage());
	    }
	    
	    return resultado;
	}
	
	public static Agendamento pegarPorId(int idAgendamento) {
	    Agendamento agendamento = null;
	    String sql = "SELECT * FROM agendamento WHERE Id_Agendamento = ?";

	    try (Connection conn = Conexao.conectar();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setInt(1, idAgendamento);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                agendamento = new Agendamento();
	                agendamento.setIdAgendamento(rs.getInt("Id_Agendamento"));

	                // Cliente
	                Cliente c = new Cliente();
	                c.setId_Cliente(rs.getInt("Id_Clientes"));
	                agendamento.setCliente(c);

	                // Veículo
	                Veiculo v = new Veiculo();
	                v.setId_Veiculo(rs.getInt("Id_Veiculos"));
	                agendamento.setVeiculo(v);

	                // Funcionário
	                Funcionario f = new Funcionario();
	                f.setId(rs.getInt("Id_Funcionarios"));
	                agendamento.setFuncionario(f);

	                agendamento.setDataAgendamento(rs.getDate("Data_Agendamento"));
	                agendamento.setTipoServico(rs.getString("Tipo_Servico"));
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Erro ao buscar agendamento por ID: " + e.getMessage());
	    }

	    return agendamento;
	}

}
