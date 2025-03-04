package com.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.model.Transaccion;
import com.proyecto.repository.TransaccionRepository;
import com.proyecto.service.TransaccionService;

@RestController
@RequestMapping("/transacciones")
public class TransaccionController {

    @Autowired
    private TransaccionService transaccionService;

    @PostMapping
    public ResponseEntity<Transaccion> crearTransaccion(@RequestBody Transaccion transaccion) {
        Transaccion nuevaTransaccion = transaccionService.guardar(transaccion);
        return ResponseEntity.ok(nuevaTransaccion);
    }

    @GetMapping
    public List<Transaccion> listarTransacciones() {
        return transaccionService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaccion> obetenerTransaccionPorId(@PathVariable Integer id){
        Transaccion transaccion = transaccionService.obtenerPorId(id);
    	return (transaccion != null) ? ResponseEntity.ok(transaccion) : ResponseEntity.notFound().build();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Transaccion> actualizarTransaccion (@PathVariable Integer id, @RequestBody Transaccion transaccionActualizada) {
    	Transaccion transaccion = transaccionService.actualizar(id, transaccionActualizada);
    	return (transaccion != null)? ResponseEntity.ok(transaccion) : ResponseEntity.notFound().build();
    }
}
