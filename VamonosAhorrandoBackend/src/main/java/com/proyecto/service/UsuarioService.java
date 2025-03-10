package com.proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.proyecto.entity.Usuario;
import com.proyecto.repository.UsuarioRepository;

public class UsuarioService {
	
    @Autowired
    private UsuarioRepository repository;

    public List<Usuario> obtenerTodos() {
        return repository.findAll();
    }

    public Usuario guardar(Usuario usuario) {
        return repository.save(usuario);
    }

}
