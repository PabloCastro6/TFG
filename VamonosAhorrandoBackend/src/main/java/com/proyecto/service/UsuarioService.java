package com.proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.entity.Rol;
import com.proyecto.entity.Usuario;
import com.proyecto.repository.UsuarioRepository;

/**
 * Servicio que proporciona operaciones CRUD y consultas específicas sobre la entidad {@link Usuario}.
 * Encapsula la lógica de negocio relacionada con los usuarios del sistema.
 */

@Service
public class UsuarioService {

	@Autowired
    private UsuarioRepository repository;

    /**
     * Recupera todos los usuarios registrados en el sistema.
     *
     * @return Lista de todos los usuarios.
     */
    public List<Usuario> obtenerTodos() {
        return repository.findAll();
    }

    /**
     * Guarda un nuevo usuario o actualiza uno existente.
     *
     * @param usuario Objeto usuario a guardar.
     * @return Usuario guardado con ID generado si es nuevo.
     */
    public Usuario guardar(Usuario usuario) {
        if (usuario.getRol() == null) {
            usuario.setRol(Rol.USUARIO);
        }
        return repository.save(usuario);
    }

    /**
     * Busca un usuario por su ID.
     *
     * @param id Identificador del usuario.
     * @return Usuario encontrado o null si no existe.
     */
    public Usuario buscarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id Identificador del usuario a eliminar.
     */
    public void eliminarPorId(Integer id) {
        repository.deleteById(id);
    }

    /**
     * Busca una lista de usuarios según su rol (USUARIO o ADMINISTRADOR).
     *
     * @param rol Rol a buscar.
     * @return Lista de usuarios con ese rol.
     */
    public List<Usuario> buscarPorRol(Rol rol) {
        return repository.findByRol(rol);
    }

    /**
     * Busca un usuario a partir de su correo y contraseña.
     * 
     * @param correo Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @return Usuario coincidente o null si no existe.
     */
    public Usuario buscarPorCorreoYPassword(String correo, String password) {
        return repository.findByCorreoAndPassword(correo, password);
    }
}


