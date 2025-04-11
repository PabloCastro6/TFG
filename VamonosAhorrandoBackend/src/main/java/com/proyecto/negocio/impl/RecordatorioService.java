package com.proyecto.negocio.impl;

import com.proyecto.entity.Recordatorio;
import com.proyecto.repository.RecordatorioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * Servicio que gestiona la lógica de negocio relacionada con los recordatorios.
 * 
 * <p>Encapsula las operaciones básicas de CRUD utilizando el {@link RecordatorioRepository}.</p>
 * 
 */

@Service
public class RecordatorioService {

    @Autowired
    private RecordatorioRepository recordatorioRepository;

    public List<Recordatorio> listarRecordatorios() {
        return recordatorioRepository.findAll();
    }

    public Optional<Recordatorio> obtenerRecordatorioPorId(Integer id) {
        return recordatorioRepository.findById(id);
    }

    public Recordatorio crearRecordatorio(Recordatorio recordatorio) {
        return recordatorioRepository.save(recordatorio);
    }

    public Recordatorio actualizarRecordatorio(Recordatorio recordatorio) {
        return recordatorioRepository.save(recordatorio);
    }

    public void eliminarRecordatorio(Integer id) {
        recordatorioRepository.deleteById(id);
    }
}

