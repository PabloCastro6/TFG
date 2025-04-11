package com.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.proyecto.entity.Recordatorio;

/**
 * Repositorio JPA para la entidad {@link Recordatorio}.
 * 
 * <p>Proporciona operaciones CRUD básicas como guardar, buscar, eliminar y listar recordatorios.</p>
 * 
 * <p>Extiende {@link JpaRepository}, por lo que no es necesario implementar los métodos manualmente.</p>
 * 
 */
public interface RecordatorioRepository extends JpaRepository<Recordatorio, Integer> {
}

