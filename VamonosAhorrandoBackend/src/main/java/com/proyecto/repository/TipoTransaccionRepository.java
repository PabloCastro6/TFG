package com.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.entity.TipoTransaccion;

public interface TipoTransaccionRepository extends JpaRepository<TipoTransaccion, Integer> {
    List<TipoTransaccion> findByUsuarioIdAndTipoCategoriaId(Integer usuarioId, Integer tipoCategoriaId);
}
