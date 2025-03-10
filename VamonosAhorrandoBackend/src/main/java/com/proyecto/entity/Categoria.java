package com.proyecto.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "idCategoria")
    private Integer idCategoria;
    
    @Column (name = "nombre")
    private String nombre;
    
    @Column (name = "tipo")
    private String tipo; // "ingreso" o "gasto"
    
    @ManyToOne
    @JoinColumn(name="usuarioId", referencedColumnName= "idUsuario")
    private Usuario usuario;
    
    @OneToMany(mappedBy = "categoria")
    private List<Transaccion> transacciones;
    
    

	public Categoria() {
		super();
	}

	public Categoria(String nombre, String tipo, Usuario usuario) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.usuario = usuario;
	}

	public Integer getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
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
