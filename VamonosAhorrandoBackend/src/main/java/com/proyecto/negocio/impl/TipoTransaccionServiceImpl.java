package com.proyecto.negocio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.DTO.TipoTransaccionDTO;
import com.proyecto.entity.TipoTransaccion;
import com.proyecto.repository.TipoTransaccionRepository;
import com.proyecto.service.TipoTransaccionService;

@Service
public class TipoTransaccionServiceImpl implements TipoTransaccionService {
	
	@Autowired
	private TipoTransaccionRepository repo;
	
	 @Override
	 public void guardarDesdeDTO(TipoTransaccionDTO dto) {
		    TipoTransaccion tipo = new TipoTransaccion();
		    tipo.setNombre(dto.getNombre());
		    tipo.setUsuarioId(dto.getUsuarioId());

		    if ("gasto".equalsIgnoreCase(dto.getTipo())) {
		        tipo.setTipoCategoriaId(1);
		    } else if ("ingreso".equalsIgnoreCase(dto.getTipo())) {
		        tipo.setTipoCategoriaId(2);
		    } else {
		        throw new RuntimeException("Tipo desconocido: " + dto.getTipo());
		    }

		    repo.save(tipo);
		}
	
	@Override
	  public List<TipoTransaccion> obtenerPorUsuarioYTipo(Integer usuarioId, Integer tipoCategoriaId) {
        return repo.findByUsuarioIdAndTipoCategoriaId(usuarioId, tipoCategoriaId);
    }

}
