package model;

public class Veiculo{
	private Cliente cliente;
	private int idCliente;
	private int Id_Veiculo;
	private String Placa;
	private String Marca;
	private String Modelo;
	private String Ano;
	private String Numero_chassi;
	
	public Veiculo() {}
	public Veiculo(int Id_Veiculo,String Placa, String Marca, String Modelo,String Ano, String Numero_chassi) {
		this.Id_Veiculo = Id_Veiculo;
		this.Placa = Placa;
		this.Marca = Marca;
		this.Modelo = Modelo;
		this.Ano = Ano;
		this.Numero_chassi = Numero_chassi;
	}
	
public int getId_Veiculo() { return Id_Veiculo;}
public String getPlaca() {return Placa;}
public String getMarca() { return Marca;}
public String getModelo() {return Modelo;}
public String getAno() {return Ano;}
public String getNumero_chassi () { return Numero_chassi;}
public Cliente getCliente() {return this.cliente;}
private int getIdCliente() {return this.idCliente;}

	
public void setId_Veiculo(int Id_Veiculo) {this.Id_Veiculo = Id_Veiculo;}
public void setPlaca (String Placa) {this.Placa = Placa;}
public void setMarca ( String Marca) {this.Marca = Marca;}
public void setModelo (String Modelo) {this.Modelo = Modelo;}
public void setAno (String Ano) {this.Ano = Ano;}
public void setNumero_chassi (String Numero_chassi) {this.Numero_chassi = Numero_chassi;}
public void setCliente(Cliente cliente) {this.cliente = cliente;}
public void setIdCliente(int idCliente) {this.idCliente = idCliente;}
}