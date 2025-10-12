package model;

public class Cliente{
private int Id_Cliente;
private String Nome;
private String Cpf;
private String Telefone;
private String Email;

public Cliente () {}
 public Cliente (int Id_Cliente, String Nome, String Cpf, String Telefone, String Email) {
	 this.Id_Cliente = Id_Cliente;
	 this.Nome = Nome;
	 this.Cpf = Cpf;
	 this.Telefone = Telefone;
	 this.Email = Email;
	 
 }
 
 public int getId_Cliente() {return Id_Cliente;}
 public String getNome() {return Nome;}
 public String getCpf() {return Cpf;}
 public String getTelefone() {return Telefone;}
 public String getEmail() {return Email;}
 
 public void setId_Cliente(int Id_Cliente) {this.Id_Cliente = Id_Cliente;}
 public void setNome(String Nome) {this.Nome = Nome;}
 public void setCpf(String Cpf) {this.Cpf = Cpf;}
 public void setTelefone(String Telefone) {this.Telefone = Telefone;}
 public void setEmail (String Email ) { this.Email = Email;}

}