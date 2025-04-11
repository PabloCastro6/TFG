package com.proyecto.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Entidad que representa a un usuario dentro de la aplicación.
 * 
 * <p>Cada usuario tiene un nombre, correo electrónico, contraseña y un rol que define sus permisos
 * dentro del sistema (por ejemplo, ADMINISTRADOR o USUARIO).</p>
 * 
 * <p>Está relacionada con múltiples transacciones financieras que haya realizado.</p>
 * 
 * <p>Se mapea a la tabla <strong>usuarios</strong> en la base de datos.</p>
 * 
 */

@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idUsuario")
	private Integer idUsuario;

	@Column(name = "nombreCompleto")
	private String nombreCompleto;

	@Column(name = "password")
	private String password;

	@Column(name = "correo")
	private String correo;

	@Enumerated(EnumType.STRING)
	@Column(name = "rol")
	private Rol rol;

	@OneToMany(mappedBy = "usuario")
	private List<Transaccion> transacciones;

	public Usuario() {
		super();
	}

	public Usuario(String nombreCompleto, String password, String correo) {
		super();
		this.nombreCompleto = nombreCompleto;
		this.password = password;
		this.correo = correo;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
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

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	

}
