package dao;

import db.Conexao;
import model.Funcionario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<Funcionario> listar() {
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
        String sql = "UPDATE Funcionarios SET Nome = ?, Cpf = ?, Cargo = ?, Telefone = ? WHERE Id_Funcionario = ?";
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

    public void excluir(int id_Funcionario) {
        String sql = "DELETE FROM Funcionarios WHERE Id_Funcionario = ?";
        try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id_Funcionario);
            stmt.executeUpdate();
            System.out.println("Funcionario excluido com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro para excluir funcionario:" + e.getMessage());
        }
    }

    public Funcionario buscarPorId(int id_Funcionario) {
        String sql = "SELECT * FROM Funcionarios WHERE Id_Funcionario = ?";
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
    	Funcionario funcionario = new Funcionario();
    	
    	funcionario.setNome("Carlos");
    	funcionario.setCargo("Vendedor");
    	funcionario.setCpf("11111111111");
    	funcionario.setTelefone("6187679090");
    	
    	FuncionarioDAO.inserir(funcionario);
  
    	
    }
}
