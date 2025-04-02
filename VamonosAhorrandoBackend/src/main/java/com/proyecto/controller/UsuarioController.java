package com.proyecto.controller;

import com.proyecto.entity.Rol;
import com.proyecto.entity.Usuario;
import com.proyecto.service.UsuarioService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:8081")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // ✅ Obtener todos los usuarios
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.obtenerTodos();
    }

    // ✅ Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Integer id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }

    // ✅ Registrar nuevo usuario
    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
        Usuario guardado = usuarioService.guardar(usuario);

        if (usuario.getRol() == Rol.ADMINISTRADOR) {
            List<Usuario> todos = usuarioService.obtenerTodos();
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("usuarioRegistrado", guardado);
            respuesta.put("todosLosUsuarios", todos);
            return ResponseEntity.ok(respuesta);
        }

        return ResponseEntity.ok(guardado);
    }

    // ✅ Actualizar un usuario
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Integer id, @RequestBody Usuario datos) {
        Usuario existente = usuarioService.buscarPorId(id);
        if (existente == null) return ResponseEntity.notFound().build();

        existente.setNombreCompleto(datos.getNombreCompleto());
        existente.setCorreo(datos.getCorreo());
        existente.setPassword(datos.getPassword());
        existente.setRol(datos.getRol());

        return ResponseEntity.ok(usuarioService.guardar(existente));
    }

    // ✅ Eliminar un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Usuario existente = usuarioService.buscarPorId(id);
        if (existente == null) return ResponseEntity.notFound().build();

        usuarioService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
