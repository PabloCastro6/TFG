package com.proyecto.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategoria;
    
    private String nombre;
    private String tipo; // "ingreso" o "gasto"
    
    @ManyToOne
    @JoinColumn(name="usuarioId")
    private Usuario usuario;
    
    

	public Categoria() {
		super();
	}

	public Categoria(String nombre, String tipo, Usuario usuario) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.usuario = usuario;
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
    
    
}
