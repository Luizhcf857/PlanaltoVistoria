package model;

public class Autenticacao {
	//Criando os atributos da classe
	private int idAutenticacao;
	private String email;
	private String senha;
	
	//criando um construtor vazio
	public Autenticacao() {
		
	}
	
	//getters e setters
	public int getIdAutenticacao() {
		return this.idAutenticacao;
		
	}
	public void setIdAutenticacao(int idAutenticacao) {
		this.idAutenticacao = idAutenticacao;
		
	}
	
	public String getEmail() {
		return this.email;
		
	}
	public void setEmail(String email) {
		this.email = email;
		
	}
	
	public String getSenha() {
		return this.senha;
		
	}
	public void setSenha(String senha) {
		this.senha = senha;
		
	}
	
}
