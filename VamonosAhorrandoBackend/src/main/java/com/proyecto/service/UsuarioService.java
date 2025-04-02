package com.proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.entity.Rol;
import com.proyecto.entity.Usuario;
import com.proyecto.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	public List<Usuario> obtenerTodos() {
		return repository.findAll();
	}

	public Usuario guardar(Usuario usuario) {
		return repository.save(usuario);
	}
	
	 public Usuario buscarPorId(Integer id) {
	        return repository.findById(id).orElse(null);
	    }

	    public void eliminarPorId(Integer id) {
	        repository.deleteById(id);
	    }

	    public List<Usuario> buscarPorRol(Rol rol) {
	        return repository.findByRol(rol);
	    }

		public Usuario buscarPorCorreoYPassword(String correo, String password) {
			 return repository.findByCorreoAndPassword(correo, password);
		}

}
