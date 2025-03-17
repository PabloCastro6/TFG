package com.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proyecto.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    // Método para buscar un usuario por correo
    Usuario findByCorreo(String correo);

    // Método para buscar un usuario por correo y contraseña
    Usuario findByCorreoAndPassword(String correo, String password);
}
