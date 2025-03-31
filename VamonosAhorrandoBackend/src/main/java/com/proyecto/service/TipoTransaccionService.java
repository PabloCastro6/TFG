package com.proyecto.service;

import java.util.List;

import com.proyecto.entity.TipoTransaccion;

public interface TipoTransaccionService {
	 void guardar(TipoTransaccion tipo);
	    List<TipoTransaccion> obtenerPorUsuarioYTipo(Integer usuarioId, Integer tipoCategoriaId);
	}


