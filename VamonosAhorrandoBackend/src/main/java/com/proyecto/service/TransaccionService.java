package com.proyecto.service;

import java.util.List;
import java.util.Optional;

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

    public Transaccion obtenerPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

	public Transaccion actualizar(Integer id, Transaccion transaccionActualizada) {
		 Optional<Transaccion> transaccionExistente = repository.findById(id);
		 if(transaccionExistente.isPresent()) {
			 Transaccion transaccion = transaccionExistente.get();
			 transaccion.setFecha(transaccionActualizada.getFecha());
			 transaccion.setCantidad(transaccionActualizada.getCantidad());
			 transaccion.setCategoria(transaccionActualizada.getCategoria());
			 transaccion.setUsuario(transaccionActualizada.getUsuario());
			 return repository.save(transaccion);
			 
		 }
		return null;
	}
	
	
	public void eliminar(Integer id) {
		repository.deleteById(id);
	}
}
