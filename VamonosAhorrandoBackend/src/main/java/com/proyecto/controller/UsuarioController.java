package com.proyecto.controller;

import com.proyecto.entity.Usuario;
import com.proyecto.repository.UsuarioRepository;

import java.util.List;

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
        // Buscar usuario por correo y contraseña
        Usuario usuarioEncontrado = usuarioRepository.findByCorreoAndPassword(usuario.getCorreo(), usuario.getPassword());
        
        if (usuarioEncontrado != null) {
            // Si el usuario existe, devolver una respuesta exitosa
            return ResponseEntity.ok("Inicio de sesión exitoso para: " + usuarioEncontrado.getNombreCompleto());
        } else {
            // Si el usuario no existe, devolver un error
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }
    }

}
