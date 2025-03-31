package com.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.proyecto.entity.Recordatorio;

public interface RecordatorioRepository extends JpaRepository<Recordatorio, Integer> {
}

