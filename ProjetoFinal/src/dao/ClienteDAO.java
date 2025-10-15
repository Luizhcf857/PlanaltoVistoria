package dao;

//importando os pacotes e classes necessarios
import db.Conexao;
import model.PagamentoController;
import java.sql.*;
import model.Cliente;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
	//metodo para cadastrar cliente
	public static void cadastrarCliente(Cliente cliente) {
		//codigo sql que vai ser executado
		String sql = "INSERT INTO Clientes (Nome, Cpf, Telefone, Email) VALUES (?, ?, ?, ?)";
		//tentativa de conectar com o banco e executar o comando sql, com try e catch
		try(Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			
			//inserindo os dados dentro do db
			stmt.setString(1, cliente.getNome());
			stmt.setString(2, cliente.getCpf());
			stmt.setString(3, cliente.getTelefone());
			stmt.setString(4, cliente.getEmail());
			
			 
			
			//comando para realmente executar o codigo
			stmt.executeUpdate();
			
			//tentando pegar a chave que foi gerada automaticamente pelo db
			try(ResultSet rs = stmt.getGeneratedKeys()){
				
				//o resultado vem uma linha anterior, então é necessário colocar um fi, para avançar para a linha da frente
				if(rs.next()) {
					//gera uma variavel apenas para guardar esse valor, pegando o int do db, do indice 1
					int idGerado = rs.getInt(1);
					
					//coloco o id gerado dentro da entidade cliente
					cliente.setId_Cliente(idGerado);
					System.out.println(cliente.getId_Cliente());
				}
				
			}
			
			 
			
			
		}catch(SQLException e) {
			System.err.println("Erro ao cadastrar cliente "+ e.getMessage());
			
		}
	}
	
	//criado um metodo para recuperar os dados de cliente dentro do db
	public static List<Cliente> pegarCliente() {
		//crinado uma list de arraylist
		List<Cliente> cliente = new ArrayList<>();
		
		//criando o comando sql
		String sql = "SELECT * FROM Clientes";
		
		//tentando realizar a operação e conectar ao db
		try(Connection conn = Conexao.conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)){
			//cria uma laço, para enquanto tiver dados, criar um cliente e atribuir os dados
			while(rs.next()) {
				Cliente novoCliente = new Cliente();
				
				//Inserindo o resultado dentro dos atributos
				novoCliente.setId_Cliente(rs.getInt("Id_Clientes"));
				novoCliente.setNome(rs.getString("Nome"));
				novoCliente.setCpf(rs.getString("Cpf"));
				novoCliente.setTelefone(rs.getString("Telefone"));
				novoCliente.setEmail(rs.getString("Email"));
				
				//adiciona os dados dentro da list
				cliente.add(novoCliente);
				
			
			}
			//retornando a lista de cliente
			return cliente;
			
		}catch(SQLException e) {//caso aconteca uma erro entra dentro da exceção
			System.err.println("Erro ao pegar clientes "+ e.getMessage());
			return null;
		}
		
	}
	
	//Criando o metodo para atualizar o cliente
	public static void atualizarCliente(Cliente cliente) {
		
		//Criando o comando sql
		String sql = "UPDATE Clientes SET Nome = ?, Cpf = ?, Telefone = ?, Email = ? WHERE Id_Clientes = ?";
		
		//tentando conectar com o banco e realizar o sql
		try(Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
			
			//passando os valores para inserir dentro do db
			stmt.setString(1, cliente.getNome());
			stmt.setString(2, cliente.getCpf());
			stmt.setString(3, cliente.getTelefone());
			stmt.setString(4, cliente.getEmail());
			stmt.setInt(5, cliente.getId_Cliente());
			
			//executando os valores que foi inserido
			stmt.executeUpdate();
			
			
		}catch(SQLException e) {//caso apresente um erro, entrara dentro da exceção
			System.err.println("Erro ao atualizar o cliente "+ e.getMessage());
			
		}
		
	}
	
	//Criando o metodo de excluir cliente
	public static void excluirCliente(int idCliente) {
		//Criando o comando sql
		String sql = "DELETE FROM Clientes WHERE Id_Clientes = ?";
		
		//tentando conectar com o banco e realizar o comando sql
		try(Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
			
			//passando os dados para inserir dentro do db
			stmt.setInt(1, idCliente);
			
			//executando os dados dentro do db
			stmt.executeUpdate();
	
		}catch(SQLException e) {//caso dê um erro, entrara dentro da exceção
			System.err.println("Erro ao excluir o cliente "+ e.getMessage());
			
		}
	}
	
	public static Cliente pegarPorId(int id) {
	    Cliente cliente = null;
	    try (Connection conn = Conexao.conectar();
	         PreparedStatement ps = conn.prepareStatement("SELECT * FROM clientes WHERE Id_Clientes = ?")) {
	        ps.setInt(1, id);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            cliente = new Cliente();
	            cliente.setId_Cliente(rs.getInt("Id_Clientes"));
	            cliente.setNome(rs.getString("Nome"));
	            cliente.setCpf(rs.getString("CPF"));
	            cliente.setEmail(rs.getString("Email"));
	            cliente.setTelefone(rs.getString("Telefone"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return cliente;
	}
	
	public static Cliente pegarUltimoCliente() {
        List<Cliente> lista = pegarCliente();
        if (lista != null && !lista.isEmpty()) return lista.get(lista.size() - 1);
        return null;
    }

	
}
