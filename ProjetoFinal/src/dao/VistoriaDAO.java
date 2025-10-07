package dao;

import model.Vistoria;
import db.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import model.Funcionario;
import model.Agendamento;

public class VistoriaDAO {

	public static boolean inserirVistoria(Vistoria vistoria) {
		String sql = "INSERT INTO vistoria(Id_Funcionarios, Id_Agendamento, Data_Vistoria, Itens_Verificados, Observacao) VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = Conexao.conectar();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			stmt.setInt(1, vistoria.getFuncionario().getId_Funcionario());
			stmt.setInt(2, vistoria.getAgendamento().getIdAgendamento());
			stmt.setDate(3, vistoria.getDataVistoria());
			stmt.setString(4, vistoria.getItensVerificados());
			stmt.setString(5, vistoria.getObservacao());

			stmt.executeUpdate();

			try (ResultSet rs = stmt.getGeneratedKeys()) {
				if (rs.next()) {
					int idVistoria = rs.getInt(1);
					vistoria.setIdVistoria(idVistoria);
				}

			}
			return true;

		} catch (SQLException e) {
			System.err.println("Erro ao inserir vistoria " + e.getMessage());
			return false;
		}

	}

public List<Vistoria> listar(){
	Funcionario funcionario = new Funcionario();
	Agendamento agendamento = new Agendamento();
	List<Vistoria> vistorias = new ArrayList<>();
	String sql = "SELECT * FROM vistoria";
	
	try (Connection conn = Conexao.conectar();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql)){
		
		while (rs.next()) {
			Vistoria vistoria = new Vistoria();
			
			vistoria.setIdVistoria(rs.getInt(rs.getInt("Id_Vistoria")));
			funcionario.setId(rs.getInt("Id_Funcionario"));
			vistoria.setFuncionario(funcionario);
			agendamento.setIdAgendamento(rs.getInt("Id_Agendamento"));
			vistoria.setIdAgendamento(agendamento);
			vistoria.setDataVistoria(rs.getDate("Data_Vistoria"));
			vistoria.setItensVerificados(rs.getString("Itens_Verificados"));
			vistoria.setObservacao(rs.getString("Observacao"));
			
			vistorias.add(vistoria);
		}
	}catch(SQLException e) {
		System.out.println("Erro ao lista vistorias: " + e.getMessage());
	}
	
	return vistorias;
}

	public void atualizar(Vistoria vistoria) {
		String sql = "UPDATE vistoria SET data_vistoria=?, itens_verificados=?, status_pagamento=?, observacoes=?, idPagamento=?, idFuncionario=? WHERE idVistoria=?";

		try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, vistoria.getIdVistoria());
			stmt.setDate(2, vistoria.getDataVistoria());
			stmt.setString(3, vistoria.getItensVerificados());
			stmt.setString(6, vistoria.getObservacao());
			stmt.setInt(7, vistoria.getAgendamento().getIdAgendamento());
			stmt.setInt(8, vistoria.getFuncionario().getId_Funcionario());

			stmt.executeUpdate();
			System.out.println("Vistoria atualizada.");

		} catch (SQLException e) {
			System.out.println("Erro ao atualizar a vistoria: " + e.getMessage());
		}
	}

	public void excluir(int idVistoria) {
		String sql = "DELETE FROM vistoria WHERE idVistoria=?";

		try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, idVistoria);
			stmt.executeUpdate();
			System.out.println("Vistoria excluída com sucesso.: ");

		} catch (SQLException e) {
			System.out.println("Erro ao excluir vistoria: " + e.getMessage());
		}
	}
}