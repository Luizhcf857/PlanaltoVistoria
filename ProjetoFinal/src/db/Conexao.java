package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;


public class Conexao {
	private static final String URL = "jdbc:mysql://localhost:3306/Planalto_Vistoria";
	private static final String USER = "root";
	private static final String PASSWORD = "1234";
	
	public static final Connection conectar() {
		try {
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			return conn;
			
		}catch(SQLException e) {
			System.err.println("Erro ao conectar ao banco" + e.getMessage());
			return null;
			
		}
		
	}
	
	

	
	

}
