package com.proyecto.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transacciones")
public class Transaccion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idTransaccion;

	// id, fecha, categoriaId, usuarioid, cantidad

	private LocalDate fecha;
	private double cantidad;

	@ManyToOne
	@JoinColumn(name = "categoriaId")
	private Categoria categoria; // tipo ingreso o gasto

	@ManyToOne
	@JoinColumn(name = "usuarioId")
	private Usuario usuario; // Quien hace la transaccion

	public Transaccion() {
		super();
	}

	public Transaccion(LocalDate fecha, double cantidad, Categoria categoria, Usuario usuario) {
		super();
		this.fecha = fecha;
		this.cantidad = cantidad;
		this.categoria = categoria;
		this.usuario = usuario;
	}

	public Long getIdTransaccion() {
		return idTransaccion;
	}

	public void setIdTransaccion(Long idTransaccion) {
		this.idTransaccion = idTransaccion;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
