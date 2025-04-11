package com.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.DTO.TipoTransaccionDTO;
import com.proyecto.entity.TipoTransaccion;
import com.proyecto.service.TipoTransaccionService;


/**
 * Controlador REST para la gestión de tipos personalizados de transacciones.
 * 
 * <p>Permite a los usuarios crear, consultar y eliminar sus propios tipos
 * de gastos o ingresos como "Restaurante", "Fiesta", "Nomina", etc.</p>
 * 
 * <p>Se accede desde la URL base <b>/api/tipos</b>.</p>
 * 
 * <p>Este controlador se apoya en {@link TipoTransaccionService} para la lógica de negocio.</p>
 * 
 */

@RestController
@RequestMapping("/api/tipos")

public class TipoTransaccionController {

    @Autowired
    private TipoTransaccionService servicio;
    @Autowired
    private TipoTransaccionService tipoTransaccionService;


    /**
     * Crea un nuevo tipo de transacción personalizado (gasto o ingreso).
     * 
     * @param tipo DTO con los datos del tipo (nombre, tipo, usuarioId, icono).
     */
    @PostMapping
    public void crearTipo(@RequestBody TipoTransaccionDTO tipo) {
        servicio.guardarDesdeDTO(tipo);
    }

    /**
     * Obtiene todos los tipos de transacción creados por un usuario.
     * 
     * @param usuarioId ID del usuario.
     * @return Lista de tipos asociados a ese usuario.
     */
    
    @GetMapping("/{usuarioId}")
    public List<TipoTransaccion> obtenerTiposPorUsuario(@PathVariable int usuarioId) {
        return servicio.obtenerPorUsuario(usuarioId);
    }

    
    /**
     * Obtiene los tipos de transacción de un usuario filtrados por tipo de categoría
     * (gasto o ingreso).
     * 
     * @param usuarioId ID del usuario.
     * @param tipoCategoriaId 1 para gastos, 2 para ingresos.
     * @return Lista filtrada de tipos del usuario.
     */
    
    @GetMapping("/{usuarioId}/{tipoCategoriaId}")
    public List<TipoTransaccion> obtenerTiposPorUsuario(
            @PathVariable Integer usuarioId,
            @PathVariable Integer tipoCategoriaId
    ) {
        return servicio.obtenerPorUsuarioYTipo(usuarioId, tipoCategoriaId);
    }
    
    
    /**
     * Endpoint alternativo para obtener todos los tipos de un usuario.
     * 
     * @param usuarioId ID del usuario.
     * @return Lista de tipos del usuario.
     */
    
    @GetMapping ("/usuario/{usuarioid}")
    public List<TipoTransaccion> obtenerTodosLosTiposPorUsuario(@PathVariable int usuarioId) {
    	return servicio.obtenerPorUsuario(usuarioId);
    }
    
    
    
    /**
     * Elimina un tipo de transacción por su nombre y usuarioId.
     * 
     * @param nombre Nombre del tipo (Ej: "Fiesta").
     * @param usuarioId ID del usuario al que pertenece.
     * @return Respuesta sin contenido si se eliminó correctamente.
     */
    @DeleteMapping("/{nombre}/{usuarioId}")
    public ResponseEntity<Void> eliminarTipo(
            @PathVariable String nombre,
            @PathVariable int usuarioId) {
        tipoTransaccionService.eliminarPorNombreYUsuario(nombre, usuarioId);
        return ResponseEntity.noContent().build();
    }

}
