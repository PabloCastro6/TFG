package com.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    private TransaccionRepository transaccionRepository;

    @PostMapping
    public Transaccion crearTransaccion(@RequestBody Transaccion transaccion) {
        return transaccionRepository.save(transaccion);
    }

    @GetMapping
    public List<Transaccion> listarTransacciones() {
        return transaccionRepository.findAll();
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Transaccion> obtenerTransaccionesPorUsuario(@PathVariable Long usuarioId) {
        return transaccionRepository.findByUsuarioIdUsuario(usuarioId);
    }
}
