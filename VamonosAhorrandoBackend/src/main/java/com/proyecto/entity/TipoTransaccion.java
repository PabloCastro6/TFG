package com.proyecto.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;


/**
 * Entidad que representa un tipo personalizado de transacción creado por el usuario.
 * 
 * <p>Se utiliza para registrar diferentes tipos de gastos o ingresos, como "Restaurante",
 * "Fiesta", "Salario", etc., y se asocia a un usuario específico.</p>
 * 
 * <p>Se guarda en la tabla <b>tipos_transaccion</b> en la base de datos.</p>
 * 
 * <p>Este tipo puede ser de dos categorías:
 * <ul>
 *   <li><b>tipoCategoriaId = 1</b>: Gasto</li>
 *   <li><b>tipoCategoriaId = 2</b>: Ingreso</li>
 * </ul></p>
 * 
 * <p>Además, incluye un campo de icono que se utiliza para representar visualmente 
 * el tipo en el frontend.</p>
 * 
 */

@Entity
@Table(name = "tipos_transaccion")
public class TipoTransaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipo;

    private String nombre;
    private Integer tipoCategoriaId; // 1: Gasto, 2: Ingreso
    private Integer usuarioId;
    
    @Transient // NO se guarda en la BBDD
    private String tipo; // "gasto" o "ingreso" que viene desde el frontend
    
    private String icono;
    
   

    // Getters y setters
    public Integer getIdTipo() { return idTipo; }
    public void setIdTipo(Integer idTipo) { this.idTipo = idTipo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

  
    public Integer getTipoCategoriaId() { return tipoCategoriaId; }
    public void setTipoCategoriaId(Integer tipoCategoriaId) { this.tipoCategoriaId = tipoCategoriaId; }

    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }
    
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
	public String getIcono() {
		return icono;
	}
	public void setIcono(String icono) {
		this.icono = icono;
	}
    
    
	
    
    
}
