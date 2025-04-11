package com.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.entity.TipoTransaccion;

import jakarta.transaction.Transactional;

public interface TipoTransaccionRepository extends JpaRepository<TipoTransaccion, Integer> {
    List<TipoTransaccion> findByUsuarioIdAndTipoCategoriaId(Integer usuarioId, Integer tipoCategoriaId);
    
    List<TipoTransaccion> findByUsuarioId(Integer usuarioId);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM TipoTransaccion t WHERE t.nombre = :nombre AND t.usuarioId = :usuarioId")
    void deleteByNombreAndUsuarioId(@Param("nombre") String nombre, @Param("usuarioId") int usuarioId);

}
