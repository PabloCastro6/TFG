package com.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.model.Usuario;
import com.proyecto.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
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
