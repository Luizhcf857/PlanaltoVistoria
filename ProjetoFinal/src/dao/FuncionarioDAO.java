package dao;

import db.Conexao;
import model.Funcionario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class FuncionarioDAO {
    public static void inserir(Funcionario funcionario) {
        String sql = "INSERT INTO Funcionarios (Nome, Cpf, Cargo, Telefone) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        	stmt.setString(1, funcionario.getNome());
        	stmt.setString(2, funcionario.getCpf());
        	stmt.setString(3, funcionario.getCargo());
        	stmt.setString(4, funcionario.getTelefone());
        	
            stmt.executeUpdate();
            System.out.println("Funcionario cadastrado com sucesso!");
            
            try(ResultSet rs = stmt.getGeneratedKeys()){
            	
            	if(rs.next()) {
            		funcionario.setId(rs.getInt(1));
            		System.out.println(funcionario.getId_Funcionario());
            		
            	}
            	
            }
            
          

        } catch (SQLException e) {
            System.out.println("Erro para cadastrar Funcionario:" + e.getMessage());
        }
    }

    public static List<Funcionario> listar() {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT * FROM Funcionarios";
        try (Connection conn = Conexao.conectar();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setId(rs.getInt("Id_Funcionarios"));
                funcionario.setNome(rs.getString("Nome"));
                funcionario.setCpf(rs.getString("Cpf"));
                funcionario.setCargo(rs.getString("Cargo"));
                funcionario.setTelefone(rs.getString("Telefone"));
                lista.add(funcionario);
            }
        } catch (SQLException e) {
            System.out.println("Erro para listar funcionario:" + e.getMessage());
        }
        return lista;
    }

    public void atualizar(Funcionario funcionario) {
        String sql = "UPDATE Funcionarios SET Nome = ?, Cpf = ?, Cargo = ?, Telefone = ? WHERE Id_Funcionarios = ?";
        try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setString(3, funcionario.getCargo());
            stmt.setString(4, funcionario.getTelefone());
            stmt.setInt(5, funcionario.getId_Funcionario());
            stmt.executeUpdate();
            System.out.println("Funcionario atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro para atualizar funcionario:" + e.getMessage());
        }
    }

    public void excluir(int id_Funcionario) throws SQLException {
        String sql = "DELETE FROM Funcionarios WHERE Id_Funcionario = ?";
        try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id_Funcionario);
            stmt.executeUpdate();
            System.out.println("Funcionario excluido com sucesso!");
        } 
        // Não captura o SQLException aqui para que ele suba para o controller
    }
    
    public Funcionario buscarPorId(int id_Funcionario) {
        String sql = "SELECT * FROM Funcionarios WHERE Id_Funcionarios = ?";
        Funcionario funcionario = null;
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id_Funcionario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                funcionario = new Funcionario();
                funcionario.setId(rs.getInt("Id_Funcionarios"));
                funcionario.setNome(rs.getString("Nome"));
                funcionario.setCpf(rs.getString("Cpf"));
                funcionario.setCargo(rs.getString("Cargo"));
                funcionario.setTelefone(rs.getString("Telefone"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar funcionário: " + e.getMessage());
        }
        return funcionario;
    }
    
    public static void main(String[] args) {
    	List<Funcionario> funcionarios = FuncionarioDAO.listar();
    	
    	for(Funcionario lista: funcionarios) {
    		System.out.println(lista.getNome());
    		
    	}
  
    	
    }
    
    public Funcionario autenticar(String cpf, String senha) {
        Funcionario f = null;
        String sql = "SELECT * FROM funcionarios WHERE cpf = ? AND senha = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                f = new Funcionario();
                f.setId(rs.getInt("Id_Funcionario"));
                f.setNome(rs.getString("Nome"));
                f.setCpf(rs.getString("Cpf"));
                f.setCargo(rs.getString("Cargo"));
                f.setTelefone(rs.getString("Telefone"));
            
            }

        } catch (SQLException e) {
        	System.err.println("Erro ao autenticar"+ e.getMessage());
        			
        }

        return f;
    }
    
    public static Funcionario pegarPorId(int id) {
        Funcionario funcionario = null;
        String sql = "SELECT * FROM Funcionarios WHERE Id_Funcionarios = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                funcionario = new Funcionario();
                funcionario.setId(rs.getInt("Id_Funcionarios"));
                funcionario.setNome(rs.getString("Nome"));
                funcionario.setCpf(rs.getString("Cpf"));
                funcionario.setTelefone(rs.getString("Telefone"));
                
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return funcionario;
    }
}
