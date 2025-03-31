package com.proyecto.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;

@Entity
@Table(name = "transacciones")
public class Transaccion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idTransaccion")
	private Integer idTransaccion;

	@Column(name = "fecha")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate fecha;

	@Column(name = "cantidad")
	private double cantidad;

	@ManyToOne
	@JoinColumn(name = "categoriaId", referencedColumnName = "idCategoria", nullable = false)
	private Categoria categoria;

	@ManyToOne
	@JoinColumn(name = "usuarioId", referencedColumnName = "idUsuario", nullable = false)
	private Usuario usuario;

	@Column(name = "tipo")
	private String tipo;

	public Transaccion() {
	}

	public Transaccion(Integer idTransaccion, LocalDate fecha, double cantidad, Categoria categoria, Usuario usuario,
			String tipo) {
		super();
		this.idTransaccion = idTransaccion;
		this.fecha = fecha;
		this.cantidad = cantidad;
		this.categoria = categoria;
		this.usuario = usuario;
		this.tipo = tipo;
	}

	public Integer getIdTransaccion() {
		return idTransaccion;
	}

	public void setIdTransaccion(Integer idTransaccion) {
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
