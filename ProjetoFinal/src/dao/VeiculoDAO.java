package dao;

import model.Veiculo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import db.Conexao;
import model.Cliente;

public class VeiculoDAO {
	public static void inserir(Veiculo veiculo) {
		String sql = "INSERT INTO Veiculos (Placa, Marca, Modelo, Ano, Numero_chassi, Id_Clientes) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, veiculo.getPlaca());
			stmt.setString(2, veiculo.getMarca());
			stmt.setString(3, veiculo.getModelo());
			stmt.setString(4, veiculo.getAno()); 
			stmt.setString(5, veiculo.getNumero_chassi());
			stmt.setInt(6, veiculo.getCliente().getId_Cliente());
			stmt.executeUpdate();
			System.out.println("Veículo cadastrado com sucesso!");
			
			try(ResultSet rs = stmt.getGeneratedKeys()){
				
				if(rs.next()) {
					veiculo.setId_Veiculo(rs.getInt(1));
					
					System.out.println(veiculo.getId_Veiculo());
				}
				
			}
			
		} catch (SQLException e) {
			System.out.println("Erro no cadastro do veículo: " + e.getMessage());
		}
	}

	public static List<Veiculo> listar() {
		List<Veiculo> lista = new ArrayList<>();
		String sql = "SELECT * FROM Veiculos";
		try (Connection conn = Conexao.conectar();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				Veiculo veiculo = new Veiculo();
				veiculo.setId_Veiculo(rs.getInt("Id_Veiculos"));
				veiculo.setPlaca(rs.getString("Placa"));
				veiculo.setMarca(rs.getString("Marca"));
				veiculo.setModelo(rs.getString("Modelo"));
				veiculo.setAno(rs.getString("Ano"));
				veiculo.setNumero_chassi(rs.getString("Numero_chassi"));
				//veiculo.setId_Cliente(rs.getInt("Id_Cliente"));
				lista.add(veiculo);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao listar veículos: " + e.getMessage());
		}
		return lista;
	}

	public void atualizar(Veiculo veiculo) {
		String sql = "UPDATE Veiculos SET Placa = ?, Marca = ?, Modelo = ?, Ano = ?, Numero_chassi = ?, Id_Cliente = ? WHERE Id_Veiculo = ?";
		try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, veiculo.getPlaca());
			stmt.setString(2, veiculo.getMarca());
			stmt.setString(3, veiculo.getModelo());
			stmt.setString(4, veiculo.getAno());
			stmt.setString(5, veiculo.getNumero_chassi());
			//stmt.setInt(6, veiculo.getId_Cliente());
			stmt.setInt(7, veiculo.getId_Veiculo());
			stmt.executeUpdate();
			System.out.println("Veículo atualizado com sucesso!");
		} catch (SQLException e) {
			System.out.println("Erro ao atualizar veículo: " + e.getMessage());
		}
	}

	public void excluir(int Id_Veiculo) {
		String sql = "DELETE FROM Veiculos WHERE Id_Veiculo = ?";
		try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, Id_Veiculo);
			stmt.executeUpdate();
			System.out.println("Veículo excluído com sucesso!");
		} catch (SQLException e) {
			System.out.println("Erro ao excluir veículo: " + e.getMessage());
		}
	}

	public Veiculo buscarPorPlaca(String placa) {
		String sql = "SELECT * FROM Veiculo WHERE Placa = ?";
		try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, placa);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Veiculo veiculo = new Veiculo();
					veiculo.setId_Veiculo(rs.getInt("Id_Veiculo"));
					veiculo.setPlaca(rs.getString("Placa"));
					veiculo.setMarca(rs.getString("Marca"));
					veiculo.setModelo(rs.getString("Modelo"));
					veiculo.setAno(rs.getString("Ano"));
					veiculo.setNumero_chassi(rs.getString("Numero_chassi"));
					
					return veiculo;
				}
			}
		} catch (SQLException e) {
			System.out.println("Erro ao buscar veículo por placa: " + e.getMessage());
		}
		return null;
	}

	public Veiculo buscarPorChassi(String numeroChassi) {
		String sql = "SELECT * FROM Veiculos WHERE Numero_chassi = ?";
		try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, numeroChassi);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Veiculo veiculo = new Veiculo();
					veiculo.setId_Veiculo(rs.getInt("Id_Veiculo"));
					veiculo.setPlaca(rs.getString("Placa"));
					veiculo.setMarca(rs.getString("Marca"));
					veiculo.setModelo(rs.getString("Modelo"));
					veiculo.setAno(rs.getString("Ano"));
					veiculo.setNumero_chassi(rs.getString("Numero_chassi"));
					//veiculo.setId_Cliente(rs.getInt("Id_Cliente"));
					return veiculo;
				}
			}
		} catch (SQLException e) {
			System.out.println("Erro ao buscar veículo por chassi: " + e.getMessage());
		}
		return null;
	}
	
	public static void main(String[] args) {
		Veiculo veiculo = new Veiculo();
		
		Cliente cliente = new Cliente();
		cliente.setId_Cliente(1);
		
		
		veiculo.setAno("2022");

		veiculo.setMarca("Chevrolet");
		veiculo.setModelo("Camaro");		
		veiculo.setPlaca("MVY1730");
		veiculo.setNumero_chassi("1bdmx1hSBf3Jj3786");
		
		veiculo.setCliente(cliente);
		
		VeiculoDAO.inserir(veiculo);
		
	}
	
	public static Veiculo pegarPorId(int id) {
	    Veiculo veiculo = null;
	    try (Connection conn = Conexao.conectar();
	         PreparedStatement ps = conn.prepareStatement("SELECT * FROM Veiculos WHERE Id_Veiculos = ?")) {
	        ps.setInt(1, id);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            veiculo = new Veiculo();
	            veiculo.setId_Veiculo(rs.getInt("Id_Veiculos"));
	            veiculo.setPlaca(rs.getString("Placa"));
	            veiculo.setMarca(rs.getString("Marca"));
	            veiculo.setModelo(rs.getString("Modelo"));
	            veiculo.setAno(rs.getString("Ano"));
	            veiculo.setNumero_chassi(rs.getString("Numero_Chassi"));
	            // Associa o cliente, se quiser
	            Cliente c = ClienteDAO.pegarPorId(rs.getInt("Id_Clientes"));
	            veiculo.setCliente(c);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return veiculo;
	}

}