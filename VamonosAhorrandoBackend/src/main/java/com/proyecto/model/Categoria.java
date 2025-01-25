package com.proyecto.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias")
public class Categoria {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String tipo; // "ingreso" o "gasto"
}
