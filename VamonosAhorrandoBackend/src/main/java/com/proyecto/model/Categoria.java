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
    @JoinColumn(name="usuarioID")
    private Usuario usario;

	public Categoria() {
		super();
	}

	public Categoria(String nombre, String tipo, Usuario usario) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.usario = usario;
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

	public Usuario getUsario() {
		return usario;
	}

	public void setUsario(Usuario usario) {
		this.usario = usario;
	}
    
    
}
