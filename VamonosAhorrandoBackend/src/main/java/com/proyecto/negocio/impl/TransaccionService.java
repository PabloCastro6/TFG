package com.proyecto.negocio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.entity.Transaccion;
import com.proyecto.negocio.ITransaccionService;
import com.proyecto.repository.TransaccionRepository;

@Service
public class TransaccionService implements ITransaccionService {

	@Autowired
	TransaccionRepository transaccionRepo;

	@Override
	public Iterable<Transaccion> listarTransacciones() {
		return transaccionRepo.findAll();
	}

	@Override
	public Transaccion obtenerTransaccionPorId(Integer id) {
		return transaccionRepo.findById(id).orElse(null);
	}

	@Override
	public Transaccion crearTransaccion(Transaccion transaccion) {
		transaccion.setIdTransaccion(null);
		return transaccionRepo.save(transaccion);
	}

	@Override
	public Transaccion actualizarTransaccion(Transaccion transaccion) {
		if (transaccion.getIdTransaccion() == null) {
			System.out.println("No se puede modificar una transacción que no tiene ID");
		}
		return transaccionRepo.save(transaccion);
	}

	@Override
	public void eliminarTransaccion(Integer id) {
		transaccionRepo.deleteById(id);
	}

	@Override
	public List<Transaccion> buscarPorUsuario(Integer idUsuario) {
		return transaccionRepo.findByUsuarioIdUsuario(idUsuario);
	}

	public void eliminarPorId(int id) {
		// TODO Auto-generated method stub
		
	}
}
