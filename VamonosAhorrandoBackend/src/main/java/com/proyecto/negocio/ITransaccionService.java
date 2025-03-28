package com.proyecto.negocio;

import com.proyecto.entity.Transaccion;

public interface ITransaccionService {

	Iterable<Transaccion> listarTransacciones();

	Transaccion obtenerTransaccionPorId(Integer id);

	Transaccion crearTransaccion(Transaccion transaccion);

	Transaccion actualizarTransaccion(Transaccion transaccion);

	void eliminarTransaccion(Integer id);

}
