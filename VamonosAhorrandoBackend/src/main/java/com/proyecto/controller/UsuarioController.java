package com.proyecto.controller;

import com.proyecto.entity.Usuario;
import com.proyecto.repository.UsuarioRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:8081") // Permitir peticiones desde Vue para pruebas
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	// Método para listar usuarios (opcional)
	@GetMapping
	public List<Usuario> listarUsuarios() {
		return usuarioRepository.findAll();
	}

	// Método para crear un nuevo usuario
	@PostMapping
	public Usuario crearUsuario(@RequestBody Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	@PostMapping("/iniciarSesion")
	public ResponseEntity<?> iniciarSesion(@RequestBody Usuario usuario) {
		// Usamos el método findByCorreoAndPassword (asegúrate de tenerlo en tu
		// repositorio)
		Usuario usuarioEncontrado = usuarioRepository.findByCorreoAndPassword(usuario.getCorreo(),
				usuario.getPassword());

		if (usuarioEncontrado != null) {
			// Construir una respuesta JSON con el id del usuario
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("userId", usuarioEncontrado.getIdUsuario());
			response.put("message", "Inicio de sesión exitoso para: " + usuarioEncontrado.getNombreCompleto());
			return ResponseEntity.ok(response);
		} else {
			// Devuelve un error si las credenciales son incorrectas
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("success", false);
			errorResponse.put("message", "Credenciales incorrectas");
			return ResponseEntity.status(401).body(errorResponse);
		}
	}

}
