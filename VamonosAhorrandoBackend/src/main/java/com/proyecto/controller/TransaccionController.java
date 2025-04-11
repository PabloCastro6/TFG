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


/**
 * Controlador REST para gestionar las transacciones financieras
 * (ingresos y gastos) de los usuarios.
 * 
 * <p>Ofrece endpoints para crear, listar, actualizar y eliminar transacciones.</p>
 * 
 * <p>Ruta base: <b>/transacciones</b></p>
 * 
 * <p>Antes de guardar una transacción, recupera la categoría y el usuario
 * desde sus respectivos repositorios para asegurar la integridad referencial.</p>
 * 
 */

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

	
	  /**
     * Obtiene todas las transacciones registradas en la base de datos.
     * 
     * @return lista completa de transacciones.
     */
	@GetMapping("")
	public Iterable<Transaccion> listarTransacciones() {
		return transaccionService.listarTransacciones();
	}

	
	 /**
     * Obtiene una transacción específica por su ID.
     * 
     * @param id ID de la transacción a buscar.
     * @return Transacción encontrada o error si no existe.
     */
	
	@GetMapping("/transaccion/{id}")
	public Transaccion listarTransaccionesPorId(@PathVariable Integer id) {
		return transaccionService.obtenerTransaccionPorId(id);
	}

	
	/**
     * Crea una nueva transacción.
     * <p>Busca la categoría y el usuario en la base de datos antes de guardar la transacción
     * para evitar relaciones huérfanas.</p>
     * 
     * @param transaccion Objeto transacción recibido desde el frontend.
     * @return La transacción guardada.
     */
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

	
	 /**
     * Actualiza una transacción existente por ID.
     * <p>Igual que en la creación, busca la categoría y el usuario antes de actualizar.</p>
     * 
     * @param id ID de la transacción a actualizar.
     * @param transaccion Objeto con los datos actualizados.
     * @return Transacción actualizada.
     */
	
	@PutMapping("/transaccion/{id}")
	public Transaccion modificarTransaccion(@PathVariable Integer id, @RequestBody Transaccion transaccion) {
		transaccion.setIdTransaccion(id);

		// Recuperar categoría completa
		String categoriaNombre = transaccion.getCategoria().getNombre();
		Categoria categoriaCompleta = categoriaRepository.findByNombre(categoriaNombre)
				.orElseThrow(() -> new RuntimeException("Categoría no encontrada con el nombre: " + categoriaNombre));
		transaccion.setCategoria(categoriaCompleta);

		// Recuperar usuario completo
		int usuarioId = transaccion.getUsuario().getIdUsuario();
		Usuario usuarioCompleto = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		transaccion.setUsuario(usuarioCompleto);

		return transaccionService.actualizarTransaccion(transaccion);
	}

	
	/**
     * Elimina una transacción por su ID.
     * 
     * @param id ID de la transacción a eliminar.
     */
	
	@DeleteMapping("/transaccion/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void borrarTransaccion(@PathVariable Integer id) {
		transaccionService.eliminarTransaccion(id);
	}
}
