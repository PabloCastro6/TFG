package com.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.proyecto.entity.Recordatorio;
import com.proyecto.entity.Usuario;
import com.proyecto.negocio.impl.RecordatorioService;
import com.proyecto.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;


/**
 * Controlador REST que gestiona los recordatorios de los usuarios.
 * 
 * <p>Permite obtener, crear, actualizar y eliminar recordatorios.
 * Cada recordatorio está asociado a un usuario.</p>
 * 
 * <p>Ruta base: {@code /recordatorios}</p>
 * 
 */

@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true")
@RestController
@RequestMapping("/recordatorios")
public class RecordatorioController {

    @Autowired
    RecordatorioService recordatorioService;

    @Autowired
    UsuarioRepository usuarioRepository;

    // Obtener todos los recordatorios
    @GetMapping("")
    public List<Recordatorio> listarRecordatorios() {
        return recordatorioService.listarRecordatorios();
    }

    // Obtener un recordatorio por ID
    @GetMapping("/{id}")
    public Optional<Recordatorio> obtenerRecordatorioPorId(@PathVariable Integer id) {
        return recordatorioService.obtenerRecordatorioPorId(id);
    }

    // Crear un nuevo recordatorio
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Recordatorio crearRecordatorio(@RequestBody Recordatorio recordatorio) {
        int usuarioId = recordatorio.getUsuario().getIdUsuario();
        Usuario usuarioCompleto = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        recordatorio.setUsuario(usuarioCompleto);

        return recordatorioService.crearRecordatorio(recordatorio);
    }

    // Modificar un recordatorio
    @PutMapping("/{id}")
    public Recordatorio modificarRecordatorio(@PathVariable Integer id, @RequestBody Recordatorio recordatorio) {
        recordatorio.setIdRecordatorio(id);
        return recordatorioService.actualizarRecordatorio(recordatorio);
    }

    // Eliminar un recordatorio
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrarRecordatorio(@PathVariable Integer id) {
        recordatorioService.eliminarRecordatorio(id);
    }
}

