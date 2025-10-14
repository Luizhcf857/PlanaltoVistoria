package model;


public class Funcionario {
	private int Id_Funcionario;
	private String Nome;
	private String Cpf;
	private String Cargo;
	private String Telefone;
	
	public Funcionario () {}
	public Funcionario (int Id_Funcionario, String Nome,String Cpf, String Cargo, String Telefone){
	 this.Id_Funcionario = Id_Funcionario;
	 this.Nome = Nome;
	 this.Cpf = Cpf;
	 this.Cargo = Cargo;
	 this.Telefone = Telefone; 
	 
	}
	public int getId_Funcionario() {return Id_Funcionario;}
	 public String getNome() {return Nome;}
	 public String getCpf () {return Cpf;}
	 public String getCargo() {return Cargo;}
	 public String getTelefone () {return Telefone;}
	 
	 public void setId(int Id_Funcionario) {this.Id_Funcionario = Id_Funcionario;} 
	 public void setNome(String Nome) {this.Nome = Nome;}
	 public void setCpf(String Cpf) {this.Cpf = Cpf;}
	 public void setCargo(String Cargo) {this.Cargo = Cargo;}
	 public void setTelefone(String Telefone) {this.Telefone = Telefone;}
	 
	 
	
}