package com.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.entity.Categoria;
import com.proyecto.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
	@Autowired
	private CategoriaService service;

	@GetMapping
	public List<Categoria> obtenerTodas() {
		return service.obtenerTodas();
	}

	@PostMapping
	public ResponseEntity<Categoria> guardar(@RequestBody Categoria categoria) {
		return ResponseEntity.ok(service.guardar(categoria));
	}
}
