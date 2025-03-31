package com.proyecto.negocio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.entity.TipoTransaccion;
import com.proyecto.repository.TipoTransaccionRepository;
import com.proyecto.service.TipoTransaccionService;

@Service
public class TipoTransaccionServiceImpl implements TipoTransaccionService {
	
	@Autowired
	private TipoTransaccionRepository repo;
	
	@Override
	public void guardar (TipoTransaccion tipo) {
		repo.save(tipo);
	}
	
	@Override
	  public List<TipoTransaccion> obtenerPorUsuarioYTipo(Integer usuarioId, Integer tipoCategoriaId) {
        return repo.findByUsuarioIdAndTipoCategoriaId(usuarioId, tipoCategoriaId);
    }

}
