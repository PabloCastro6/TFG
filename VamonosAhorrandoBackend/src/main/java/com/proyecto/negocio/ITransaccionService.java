package com.proyecto.negocio;

import java.util.List;

import com.proyecto.entity.Transaccion;

public interface ITransaccionService {

	Iterable<Transaccion> listarTransacciones();

	Transaccion obtenerTransaccionPorId(Integer id);

	Transaccion crearTransaccion(Transaccion transaccion);

	Transaccion actualizarTransaccion(Transaccion transaccion);

	void eliminarTransaccion(Integer id);
	
	List<Transaccion> buscarPorUsuario(Integer idUsuario); //Recuperar transacciones por usuario

}
