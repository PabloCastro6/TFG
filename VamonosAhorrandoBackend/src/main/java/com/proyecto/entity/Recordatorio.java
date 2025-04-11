package com.proyecto.entity;

import jakarta.persistence.*;
import java.util.Date;


/**
 * Entidad que representa un recordatorio financiero creado por el usuario.
 * 
 * <p>Puede ser un gasto o un ingreso futuro que el usuario quiere recordar.
 * Contiene información como concepto, cantidad, tipo, fecha y el usuario asociado.</p>
 * 
 * <p>Se almacena en la tabla <strong>recordatorios</strong>.</p>
 * 
 */
@Entity
@Table(name = "recordatorios")
public class Recordatorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRecordatorio;
    
    private String concepto;
    private Double cantidad;
    
    @Temporal(TemporalType.DATE)
    private Date fecha;
    
    private String tipo; // "ingreso" o "gasto"

    @ManyToOne
    @JoinColumn(name = "usuarioId")  // Relacionamos la columna usuarioId
    private Usuario usuario;

    // Getters y Setters
    public Integer getIdRecordatorio() {
        return idRecordatorio;
    }

    public void setIdRecordatorio(Integer idRecordatorio) {
        this.idRecordatorio = idRecordatorio;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
