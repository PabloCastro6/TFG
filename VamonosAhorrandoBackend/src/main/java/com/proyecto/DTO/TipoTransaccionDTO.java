package com.proyecto.DTO;


/**
 * DTO que representa un tipo de transacción personalizada creada por el usuario.
 * 
 * Este objeto se utiliza para transferir datos entre el frontend y el backend
 * cuando se crean nuevos tipos de gasto o ingreso (por ejemplo: "Restaurante", "Fiesta").
 * También incluye el ícono asociado que se mostrará en la interfaz.
 */

public class TipoTransaccionDTO {
	
	private String nombre;
    private String tipo; // "gasto" o "ingreso"
    private Integer usuarioId;
    
    private String icono;

    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }
	public String getIcono() {
		return icono;
	}
	public void setIcono(String icono) {
		this.icono = icono;
	}
    
    
}

