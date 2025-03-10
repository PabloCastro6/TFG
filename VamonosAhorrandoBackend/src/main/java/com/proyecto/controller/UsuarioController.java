package com.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.entity.Usuario;
import com.proyecto.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:8081") // Permitir peticiones desde Vue para pruebas
public class UsuarioController {
	
	 @Autowired
	    private UsuarioRepository usuarioRepository;

	    @PostMapping
	    public Usuario crearUsuario(@RequestBody Usuario usuario) {
	        return usuarioRepository.save(usuario);
	    }

	    @GetMapping
	    public List<Usuario> listarUsuarios() {
	        return usuarioRepository.findAll();
	    }

}
