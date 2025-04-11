package com.proyecto.negocio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.DTO.TipoTransaccionDTO;
import com.proyecto.entity.TipoTransaccion;
import com.proyecto.repository.TipoTransaccionRepository;
import com.proyecto.service.TipoTransaccionService;

/**
 * Implementación del servicio {@link TipoTransaccionService} que gestiona los tipos de transacción
 * personalizados por el usuario (como "Restaurante", "Fiesta", etc.) y su persistencia en la base de datos.
 */

@Service
public class TipoTransaccionServiceImpl implements TipoTransaccionService {
	
	@Autowired
	private TipoTransaccionRepository repo;
	
	
	 /**
     * Guarda un nuevo tipo de transacción a partir de un DTO recibido desde el frontend.
     * También asigna automáticamente el tipo de categoría ID según sea "gasto" o "ingreso".
     *
     */
	
	 @Override
	 public void guardarDesdeDTO(TipoTransaccionDTO dto) {
		    TipoTransaccion tipo = new TipoTransaccion();
		    tipo.setNombre(dto.getNombre());
		    tipo.setUsuarioId(dto.getUsuarioId());
		    tipo.setIcono(dto.getIcono());

		    if ("gasto".equalsIgnoreCase(dto.getTipo())) {
		        tipo.setTipoCategoriaId(1);
		    } else if ("ingreso".equalsIgnoreCase(dto.getTipo())) {
		        tipo.setTipoCategoriaId(2);
		    } else {
		        throw new RuntimeException("Tipo desconocido: " + dto.getTipo());
		    }

		    repo.save(tipo);
		}
	
	 /**
	     * Obtiene todos los tipos de transacción de un usuario filtrados por categoría (gasto o ingreso).
	     *
	     */
	@Override
	  public List<TipoTransaccion> obtenerPorUsuarioYTipo(Integer usuarioId, Integer tipoCategoriaId) {
        return repo.findByUsuarioIdAndTipoCategoriaId(usuarioId, tipoCategoriaId);
    }
	
	
	/**
     * Elimina un tipo de transacción por nombre y por el ID del usuario que lo creó.
     *
     */
	 @Override
	    public void eliminarPorNombreYUsuario(String nombre, int usuarioId) {
	        repo.deleteByNombreAndUsuarioId(nombre, usuarioId);
	    }

	 /**
	     * Recupera todos los tipos de transacción (gasto e ingreso) creados por un usuario específico.
	     *
	     */
	 @Override
	 public List<TipoTransaccion> obtenerPorUsuario(Integer usuarioId) {
	     return repo.findByUsuarioId(usuarioId);
	 }

}
