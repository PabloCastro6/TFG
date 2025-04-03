package com.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public void crearTipo(@RequestBody TipoTransaccionDTO tipo) {
        servicio.guardarDesdeDTO(tipo);
    }

    @GetMapping("/{usuarioId}/{tipoCategoriaId}")
    public List<TipoTransaccion> obtenerTiposPorUsuario(
            @PathVariable Integer usuarioId,
            @PathVariable Integer tipoCategoriaId
    ) {
        return servicio.obtenerPorUsuarioYTipo(usuarioId, tipoCategoriaId);
    }
}
