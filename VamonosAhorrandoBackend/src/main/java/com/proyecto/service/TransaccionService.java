package com.proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.model.Transaccion;
import com.proyecto.repository.TransaccionRepository;

@Service
public class TransaccionService {
	@Autowired
	private TransaccionRepository repository;

	public List<Transaccion> obtenerTodas() {
		return repository.findAll();
	}

	public Transaccion guardar(Transaccion transaccion) {
		return repository.save(transaccion);
	}
}
