package com.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.proyecto.entity.Transaccion;
import com.proyecto.entity.Categoria;
import com.proyecto.entity.Usuario;
import com.proyecto.negocio.impl.TransaccionService;
import com.proyecto.repository.CategoriaRepository;
import com.proyecto.repository.UsuarioRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/transacciones")
public class TransaccionController {

	@Autowired
	TransaccionService transaccionService;

	// Se inyectan los repositorios de Categoria y Usuario
	@Autowired
	CategoriaRepository categoriaRepository;

	@Autowired
	UsuarioRepository usuarioRepository;

	@GetMapping("")
	public Iterable<Transaccion> listarTransacciones() {
		return transaccionService.listarTransacciones();
	}

	@GetMapping("/transaccion/{id}")
	public Transaccion listarTransaccionesPorId(@PathVariable Integer id) {
		return transaccionService.obtenerTransaccionPorId(id);
	}

	@PostMapping("/transaccion")
	@ResponseStatus(HttpStatus.CREATED)
	public Transaccion crearTransaccion(@RequestBody Transaccion transaccion) {
		// Recupera el nombre de la categoría enviado en el JSON
		String categoriaNombre = transaccion.getCategoria().getNombre();
		// Busca la categoría completa en la base de datos usando el nombre
		Categoria categoriaCompleta = categoriaRepository.findByNombre(categoriaNombre)
				.orElseThrow(() -> new RuntimeException("Categoría no encontrada con el nombre: " + categoriaNombre));
		transaccion.setCategoria(categoriaCompleta);

		// Recupera el objeto Usuario completo a partir del id enviado en el JSON
		int usuarioId = transaccion.getUsuario().getIdUsuario();
		Usuario usuarioCompleto = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		transaccion.setUsuario(usuarioCompleto);

		return transaccionService.crearTransaccion(transaccion);
	}

	@PutMapping("/transaccion/{id}")
	public Transaccion modificarTransaccion(@PathVariable Integer id, @RequestBody Transaccion transaccion) {
		transaccion.setIdTransaccion(id);
		return transaccionService.actualizarTransaccion(transaccion);
	}

	@DeleteMapping("/transaccion/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void borrarTransaccion(@PathVariable Integer id) {
		transaccionService.eliminarTransaccion(id);
	}
}
