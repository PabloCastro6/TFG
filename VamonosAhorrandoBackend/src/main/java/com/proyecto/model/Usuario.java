package com.proyecto.model;

public class Usuario {
	
	private Long idUsuario; 
	
	private String nombreCompleto;
	private String password;
	private String correo;
	private String rol;
	
	
	public Usuario() {
		super();
	}


	public Usuario(String nombreCompleto, String password, String correo, String rol) {
		super();
		this.nombreCompleto = nombreCompleto;
		this.password = password;
		this.correo = correo;
		this.rol = rol;
	}


	public Long getIdUsuario() {
		return idUsuario;
	}


	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}


	public String getNombreCompleto() {
		return nombreCompleto;
	}


	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
	}


	public String getRol() {
		return rol;
	}


	public void setRol(String rol) {
		this.rol = rol;
	} 
	
	
	

}
