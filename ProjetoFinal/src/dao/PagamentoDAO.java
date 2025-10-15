package dao;

//importando as classes necessárias para realizar o dao
import java.sql.*;
import model.PagamentoController;
import db.Conexao;
import model.Cliente;
import model.Vistoria;
import java.util.ArrayList;
import java.util.List;


	

public class PagamentoDAO {
	
	
	//Criando o metodo para cadastrar uma forma de pagamento dentro do banco de dados
	public static void  cadastrarPagamento(PagamentoController pagamento) {
		
		//Instanciado o comando sql
		String sql = "INSERT INTO Pagamento (Id_Clientes, Id_Vistoria, Valor, Data_Pagamento, Forma_Pagamento, Status_Pagamento) VALUES "
		+ "(?, ?, ?, ?, ?, ?)";
		
		//adicionando o try catch para inserir os dados dentro do banco, junto com outro comando para pegar a chave primaria
		try(Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
			
			//inserindo os dados de acordo com o tipo dele
			stmt.setInt(1, pagamento.getCliente().getId_Cliente());
			stmt.setInt(2, pagamento.getVistoria().getIdVistoria());
			stmt.setDouble(3, pagamento.getValor());
			stmt.setDate(4, pagamento.getDataPagamento());
			stmt.setString(5, pagamento.getFormaPagamento());
			stmt.setString(6, pagamento.getStatusPagamento());
			
			//executando os dados
			stmt.executeUpdate();
			
			
		}catch(SQLException e) {//Caso acontece algum erro, vai entrar dentro do catch
			System.err.println("Erro ao inserir dados no banco de dados "+ e.getMessage());
			
		}
		
	}
	
	//criando a classe que vai mostrar os pagamentos, vai um retorno do tipo de uma lista
	public static List<PagamentoController> mostrarHistoricoDePagamentos() {
		//criando um arraylist que ira receber os dados
		List<PagamentoController> listaPagamento = new ArrayList<>();
		
		//criando o comando sql
		String sql = "SELECT * FROM Pagamento";
		
		//tentando realizar o comando
		try(Connection conn = Conexao.conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)){
			
			//criando um laço para se existir resultado ele ir atribuindo
			while(rs.next()) {
				//toda vez que o laço gira, cria uma novo pagamento que estava armazenado dentro do db
				PagamentoController historicoPagamento = new PagamentoController();
				
				//criei um objeto de cliente para atribuir o valor de cliente
				Cliente cliente = new Cliente();
				
				//setei o valor nele
				cliente.setId_Cliente(rs.getInt("Id_Clientes"));
				
				//Foi inserido o valor dentro da entidade de pagamento
				historicoPagamento.setCliente(cliente);
				
				//Foi criado o objeto para atribuir o valor de id nele
				Vistoria vistoria = new Vistoria();
				//Foi inserido o valor nele
				vistoria.setIdVistoria(rs.getInt("Id_Vistoria"));
				
				//foi passado o valor para dentro da classe de pagamento
				historicoPagamento.setVistoria(vistoria);
				
				//Inserindo os demais valores dentro de pagamento
				historicoPagamento.setValor(rs.getDouble("Valor"));
				historicoPagamento.setDataPagamento(rs.getDate("Data_Pagamento"));
				historicoPagamento.setFormaPagamento(rs.getString("Forma_Pagamento"));
				historicoPagamento.setStatusPagamento(rs.getString("Status_Pagamento"));
				
				//adicionando os valores dentro da lista
				listaPagamento.add(historicoPagamento);
				
			}
			
		}catch(SQLException e) {
			System.err.println("Erro ao mostrar historico "+ e.getMessage());
			
		}
		//retornando a lista
		return listaPagamento;
		
	}
	
	//metodo para atualizar a forma de pagamento
	public static void atualizarFormaPagamento(PagamentoController pagamento) {
		//passando o comando sql
		String sql = "UPDATE Pagamento SET Id_Clientes = ?, Id_Vistoria = ?, Valor = ?, Data_Pagamento = ?, Forma_Pagamento = ?, "
				+ "Status_Pagamento = ? WHERE Id_Vistoria = ?";
		
		//tentando realizar a conexão
		try(Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
			
			//inserindo os dados dentro do db
			stmt.setInt(1, pagamento.getCliente().getId_Cliente());
			stmt.setInt(2, pagamento.getVistoria().getIdVistoria());
			stmt.setDouble(3, pagamento.getValor());
			stmt.setDate(4, pagamento.getDataPagamento());
			stmt.setString(5, pagamento.getFormaPagamento());
			stmt.setString(6, pagamento.getStatusPagamento());
			stmt.setInt(7, pagamento.getVistoriaAntiga().getIdVistoria());
			
			//executando os dados inseridos
			stmt.executeUpdate();
			
		}catch(SQLException e) {//caso aconteça um erro, entra na exceção
			System.err.println("Erro ao atualizar os dados "+ e.getMessage());
			
		}
		
	}
	
	//Criando o metodo de deletar o pagamento
	public static void deletarPagamento(int idVistoria) {
		//Criando o comando sql
		String sql = "DELETE FROM Pagamento WHERE Id_Vistoria = ?";
		
		//tentando realizar o comando
		try(Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
			
			//passando o valor para o db
			stmt.setInt(1, idVistoria);
			
			//executando o valor passado
			stmt.executeUpdate();
			
		}catch(SQLException e) {//caso dê um erro, entrara na exceção
			System.out.println("Erro ao excluir pagamento "+ e.getMessage());
			
		}
		
		
	}
	
	// Método para pegar o pagamento de uma vistoria específica
    public static PagamentoController pegarPorVistoria(int idVistoria) {
        String sql = "SELECT * FROM Pagamento WHERE Id_Vistoria = ?";
        try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idVistoria);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    PagamentoController pagamento = new PagamentoController();

                    // Cliente
                    Cliente cliente = new Cliente();
                    cliente.setId_Cliente(rs.getInt("Id_Clientes"));
                    pagamento.setCliente(cliente);

                    // Vistoria
                    Vistoria vistoria = new Vistoria();
                    vistoria.setIdVistoria(rs.getInt("Id_Vistoria"));
                    pagamento.setVistoria(vistoria);

                    // Valores do pagamento
                    pagamento.setValor(rs.getDouble("Valor"));
                    pagamento.setDataPagamento(rs.getDate("Data_Pagamento"));
                    pagamento.setFormaPagamento(rs.getString("Forma_Pagamento"));
                    pagamento.setStatusPagamento(rs.getString("Status_Pagamento"));

                    return pagamento;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pagamento por vistoria: " + e.getMessage());
        }
        return null; // se não encontrar nenhum pagamento
    }
    
 // Atualiza o status do pagamento
    public static boolean atualizarStatusPagamento(PagamentoController pagamento) {
        String sql = "UPDATE Pagamento SET Status_Pagamento = ? WHERE Id_Vistoria = ? AND Id_Clientes = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pagamento.getStatusPagamento());
            ps.setInt(2, pagamento.getVistoria().getIdVistoria());
            ps.setInt(3, pagamento.getCliente().getId_Cliente());

            int linhas = ps.executeUpdate();
            return linhas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
	
}
