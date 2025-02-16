package com.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.model.Transaccion;
import com.proyecto.service.TransaccionService;

@RestController
@RequestMapping("/api/transacciones")
public class TransaccionController {
    @Autowired
    private TransaccionService service;

    @GetMapping
    public List<Transaccion> obtenerTodas() {
        return service.obtenerTodas();
    }

    @PostMapping
    public ResponseEntity<Transaccion> guardar(@RequestBody Transaccion transaccion) {
        return ResponseEntity.ok(service.guardar(transaccion));
    }
}
