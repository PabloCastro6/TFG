package com.proyecto.DTO;

public class TipoTransaccionDTO {
	
	private String nombre;
    private String tipo; // "gasto" o "ingreso"
    private Integer usuarioId;

    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }
}

