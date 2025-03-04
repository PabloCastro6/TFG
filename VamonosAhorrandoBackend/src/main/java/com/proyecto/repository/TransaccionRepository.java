package com.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.model.Transaccion;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Integer> {
	
	List<Transaccion> findByUsuarioIdUsuario(Integer usuarioId);
}

