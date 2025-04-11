package com.proyecto.service;

import java.util.List;

import com.proyecto.DTO.TipoTransaccionDTO;
import com.proyecto.entity.TipoTransaccion;

public interface TipoTransaccionService {
	
	 void guardarDesdeDTO(TipoTransaccionDTO tipo);
	    List<TipoTransaccion> obtenerPorUsuarioYTipo(Integer usuarioId, Integer tipoCategoriaId);
	    void eliminarPorNombreYUsuario(String nombre, int usuarioId);
	    
	    List<TipoTransaccion> obtenerPorUsuario(Integer usuarioId);
	    
	}


