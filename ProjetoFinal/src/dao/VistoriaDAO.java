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
		 try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			 
			 stmt.setInt(1, vistoria.getFuncionario().getId_Funcionario());
			 stmt.setInt(2, vistoria.getAgendamento().getIdAgendamento());
			 stmt.setDate(3, vistoria.getDataVistoria());
			 stmt.setString(4, vistoria.getItensVerificados());
			 stmt.setString(5, vistoria.getObservacao());
			 
			 stmt.executeUpdate();
			 
			 
			 
			 
			 try(ResultSet rs = stmt.getGeneratedKeys()){
				 if(rs.next()) {
					 int idVistoria = rs.getInt(1);
					 vistoria.setIdVistoria(idVistoria);
				 }
				 
			 }
			return true;
			
		 }catch(SQLException e) {
				System.err.println("Erro ao inserir vistoria "+ e.getMessage());
				return false;
			}
 
	}
	
	public static void main(String[] args) {
		Vistoria vistoria = new Vistoria();
		
		Funcionario funcionario = new Funcionario();
		funcionario.setId(1);
		
		Agendamento agendamento = new Agendamento();
		agendamento.setIdAgendamento(2);
		
		vistoria.setDataVistoria(Date.valueOf("2025-10-11"));
		vistoria.setItensVerificados("Ainda ira realizar a verificação");
		vistoria.setObservacao("Ainda sera feita a vistoria");
		
		vistoria.setFuncionario(funcionario);
		vistoria.setAgendamento(agendamento);
		
		VistoriaDAO.inserirVistoria(vistoria);
		
		
		
	}
	
	
	
}
