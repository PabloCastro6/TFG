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

@RestController
@RequestMapping("/api/tipos")
/*@CrossOrigin(origins = "*")*/
public class TipoTransaccionController {

    @Autowired
    private TipoTransaccionService servicio;
    @Autowired
    private TipoTransaccionService tipoTransaccionService;


    @PostMapping
    public void crearTipo(@RequestBody TipoTransaccionDTO tipo) {
        servicio.guardarDesdeDTO(tipo);
    }

    @GetMapping("/{usuarioId}")
    public List<TipoTransaccion> obtenerTiposPorUsuario(@PathVariable int usuarioId) {
        return servicio.obtenerPorUsuario(usuarioId);
    }

    @GetMapping("/{usuarioId}/{tipoCategoriaId}")
    public List<TipoTransaccion> obtenerTiposPorUsuario(
            @PathVariable Integer usuarioId,
            @PathVariable Integer tipoCategoriaId
    ) {
        return servicio.obtenerPorUsuarioYTipo(usuarioId, tipoCategoriaId);
    }
    
    @GetMapping ("/usuario/{usuarioid}")
    public List<TipoTransaccion> obtenerTodosLosTiposPorUsuario(@PathVariable int usuarioId) {
    	return servicio.obtenerPorUsuario(usuarioId);
    }
    
    
    @DeleteMapping("/{nombre}/{usuarioId}")
    public ResponseEntity<Void> eliminarTipo(
            @PathVariable String nombre,
            @PathVariable int usuarioId) {
        tipoTransaccionService.eliminarPorNombreYUsuario(nombre, usuarioId);
        return ResponseEntity.noContent().build();
    }

}
