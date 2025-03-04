package com.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
    Usuario findByCorreo(String correo);
}
