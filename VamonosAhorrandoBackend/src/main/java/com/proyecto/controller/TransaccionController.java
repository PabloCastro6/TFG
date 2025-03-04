package com.proyecto.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.proyecto.model.Transaccion;
import com.proyecto.service.TransaccionService;

@RestController
@RequestMapping("/transacciones")
public class TransaccionController {

    private static final Logger logger = LoggerFactory.getLogger(TransaccionController.class);
    private final TransaccionService transaccionService;

    @Autowired
    public TransaccionController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    @GetMapping
    public List<Transaccion> listarTransacciones() {
        logger.info("📌 Se llamó al endpoint GET /transacciones");
        List<Transaccion> transacciones = transaccionService.obtenerTodas();
        logger.info("🔍 Transacciones encontradas: {}", transacciones.size());
        return transacciones;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaccion> obtenerTransaccionPorId(@PathVariable Integer id) {
        logger.info("📌 Se llamó al endpoint GET /transacciones/{}", id);
        Transaccion transaccion = transaccionService.obtenerPorId(id);
        return (transaccion != null) ? ResponseEntity.ok(transaccion) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Transaccion> crearTransaccion(@RequestBody Transaccion transaccion) {
        logger.info("📌 Se llamó al endpoint POST /transacciones con datos: {}", transaccion);
        Transaccion nuevaTransaccion = transaccionService.guardar(transaccion);
        return ResponseEntity.ok(nuevaTransaccion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaccion> actualizarTransaccion(@PathVariable Integer id, @RequestBody Transaccion transaccionActualizada) {
        logger.info("📌 Se llamó al endpoint PUT /transacciones/{} con datos: {}", id, transaccionActualizada);
        Transaccion transaccion = transaccionService.actualizar(id, transaccionActualizada);
        return (transaccion != null) ? ResponseEntity.ok(transaccion) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTransaccion(@PathVariable Integer id) {
        logger.info("📌 Se llamó al endpoint DELETE /transacciones/{}", id);
        transaccionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
