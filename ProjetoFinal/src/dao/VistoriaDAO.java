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

public class VistoriaDAO {
	
	public boolean inserirVistoria(Vistoria vistoria) {
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
	
	
	
}
