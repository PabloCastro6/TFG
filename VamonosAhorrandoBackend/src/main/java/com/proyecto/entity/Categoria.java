package com.proyecto.entity;

import java.util.List;

import jakarta.persistence.*;


/**
 * Entidad que representa una categoría de transacción, como "Gasto" o "Ingreso".
 * 
 * <p>Una categoría agrupa un conjunto de transacciones realizadas por los usuarios.
 * Esta clase se corresponde con la tabla <b>categorias</b> en la base de datos.</p>
 * 
 * <p>Ejemplos de categorías podrían ser: "Gasto", "Ingreso", "Ahorro", etc.</p>
 * 
 */
@Entity
@Table(name = "categorias")
public class Categoria {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idCategoria")
	private Integer idCategoria;

	@Column(name = "nombre")
	private String nombre;

	@OneToMany(mappedBy = "categoria")
	private List<Transaccion> transacciones;

	public Categoria() {
		super();
	}

	public Categoria(String nombre) {
		super();
		this.nombre = nombre;

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

}
