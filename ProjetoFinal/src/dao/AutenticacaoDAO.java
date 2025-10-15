package dao;
import db.Conexao;
import java.sql.*;
import model.Autenticacao;
import java.util.ArrayList;
import java.util.List;
import model.Funcionario;
import model.Cliente;

public class AutenticacaoDAO {
	//criando o metodo para inserir dados dentro de autenticacao no db
	public static void inserirAutenticacao(Autenticacao autenticacao) {
		//codigo sql para inserir
		String sql = "INSERT INTO autenticacao(idFuncionario, idCliente, email, senha) VALUES (?, ?, ?, ?)";
		
		//tentando conectar com o banco e inserir os dados
		try(Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			//pegando os valores e colocando dentro do db
			stmt.setInt(1, autenticacao.getFuncionario().getId_Funcionario());
			stmt.setInt(2, autenticacao.getCliente().getId_Cliente());
			stmt.setString(3, autenticacao.getEmail());
			stmt.setString(4, autenticacao.getSenha());
			
			//executando os dados para dentro do db
			stmt.executeUpdate();
			
			//tentando pegar a chave gerada quando inseriou dados da tabela de autenticacao
			try(ResultSet rs = stmt.getGeneratedKeys()){
				//colocando o if para ir para a proxima liha, pois o dado tenta pegar a linha anterior
				if(rs.next()) {
					//caso tenha dado, inserir dentro do atributo id
					autenticacao.setIdAutenticacao(rs.getInt(1));
					
				}
				
			}
			
		}catch(SQLException e) {// caso apresente algum erro, entrara dentro da exceção
			System.err.println("Erro ao inserir dados "+ e.getMessage());
			
		}

	}
	
	//criando um metodo para pegar os dados de autenticacao
	public static List<Autenticacao> pegarAutenticacao(){
		// criando a list de autenticacao
		List<Autenticacao> listar = new ArrayList<>();
		
		//Inserindo o codigo sql
		String sql = "SELECT * FROM autenticacao";
		
		//tentando inserir os dados e conectar com o db
		try(Connection conn = Conexao.conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)){
			//loop para enquanto tiver dados, criar um objeto e inserir os dados dentro dele
			while(rs.next()) {
				//criando o objeto
				Autenticacao autenticacao = new Autenticacao();
				Funcionario funcionario = new Funcionario();
				Cliente cliente = new Cliente();
				
				//inserindo os dados
				autenticacao.setIdAutenticacao(rs.getInt("idAutenticacao"));
				funcionario.setId(rs.getInt("idFuncionario"));
				autenticacao.setFuncionario(funcionario);
				cliente.setId_Cliente(rs.getInt("idCliente"));
				autenticacao.setCliente(cliente);
				autenticacao.setEmail(rs.getString("email"));
				autenticacao.setSenha(rs.getString("senha"));
				
				
				//adicionando o objeto dentro da lista
				listar.add(autenticacao);
			}
			
			//retornando a lista 
			return listar;
			
		}catch(SQLException e) {
			System.err.println("Erro ao carregar dados "+ e.getMessage());
			
			return null;
		}
		
	}
	
	//metodo para atualizar os dados
	public static void atualizarDados(Autenticacao autenticacao) {
		//inserindo o comando sql para atualizar os dados
		String sql = "UPDATE autenticacao SET email = ?, senha = ? WHERE idAutenticacao = ?";
		
		//tentando conectar ao banco e inserir os dados
		try(Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
			
			//Colocando os valores dentro do db
			stmt.setString(1, autenticacao.getEmail());
			stmt.setString(2, autenticacao.getSenha());
			stmt.setInt(3, autenticacao.getIdAutenticacao());
			
			//executando os comandos para dentro do db
			stmt.executeUpdate();
			
		}catch(SQLException e) {
			System.err.println("Erro ao atualizar os dados "+ e.getMessage());
			
		}
	}
	
	//Criando o metodo para excluir os dados
	public static void excluirDados(int idAutenticacao) {
		//Comando sql para excluir
		String sql = "DELETE FROM autenticacao WHERE idAutenticacao = ?";
		
		//tentando conectar ao db e executar o comando
		try(Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
			
			//informando o valor ao db
			stmt.setInt(1, idAutenticacao);
			
			//executando os valores dentro do db
			stmt.executeUpdate();
			
		}catch(SQLException e) {
			System.err.println("Erro ao excluir os dados "+ e.getMessage());
			
		}
		
	}
	
	public static String autenticar(String email, String senha) {
	    String tipoUsuario = null;

	    String sql = "SELECT * FROM autenticacao WHERE email = ? AND senha = ?";

	    try (Connection conn = Conexao.conectar();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, email);
	        stmt.setString(2, senha);

	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            int idFuncionario = rs.getInt("idFuncionario");
	            int idCliente = rs.getInt("idCliente");

	            // Verifica primeiro pelo e-mail
	            if (email.equalsIgnoreCase("gerente@empresa.com")) {
	                tipoUsuario = "gerente";
	            } 
	            else if (idFuncionario != 0) {
	                tipoUsuario = "vistoriador";
	            } 
	            else if (idCliente != 0) {
	                tipoUsuario = "cliente";
	            }
	        }

	    } catch (SQLException e) {
	        System.err.println("Erro ao autenticar: " + e.getMessage());
	    }

	    return tipoUsuario;
	}
	
	public static int pegarIdCliente(String email) {
	    int id = -1;
	    try (Connection conn = Conexao.conectar();
	         PreparedStatement ps = conn.prepareStatement("SELECT idCliente FROM autenticacao WHERE email = ?")) {
	        ps.setString(1, email);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            id = rs.getInt("idCliente");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return id;
	}

	public static int pegarIdFuncionario(String email) {
	    int id = -1;
	    try (Connection conn = Conexao.conectar();
	         PreparedStatement ps = conn.prepareStatement("SELECT idFuncionario FROM autenticacao WHERE email = ?")) {
	        ps.setString(1, email);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            id = rs.getInt("idFuncionario");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return id;
	}

	
}
