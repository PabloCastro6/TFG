package com.proyecto.negocio;

import java.util.List;

import com.proyecto.entity.Transaccion;

/**
 * Interfaz de servicio que define las operaciones principales para la gestión de transacciones
 * (ingresos y gastos) dentro de la aplicación.
 * 
 * Implementada por {@code TransaccionService} para interactuar con la capa de persistencia.
 */

public interface ITransaccionService {

	 /**
     * Recupera todas las transacciones almacenadas en la base de datos.
     * 
     * @return un {@code Iterable} con todas las transacciones existentes.
     */
    Iterable<Transaccion> listarTransacciones();

    /**
     * Busca una transacción específica por su ID.
     * 
     * @param id el identificador único de la transacción.
     * @return la transacción correspondiente si existe, o {@code null} si no se encuentra.
     */
    Transaccion obtenerTransaccionPorId(Integer id);

    /**
     * Crea y guarda una nueva transacción en la base de datos.
     * 
     * @param transaccion la transacción a registrar.
     * @return la transacción creada y persistida.
     */
    Transaccion crearTransaccion(Transaccion transaccion);

    /**
     * Actualiza una transacción existente con nuevos datos.
     * 
     * @param transaccion la transacción modificada.
     * @return la transacción actualizada.
     */
    Transaccion actualizarTransaccion(Transaccion transaccion);

    /**
     * Elimina una transacción de la base de datos por su ID.
     * 
     * @param id el identificador de la transacción a eliminar.
     */
    void eliminarTransaccion(Integer id);

    /**
     * Busca todas las transacciones asociadas a un usuario concreto.
     * 
     * @param idUsuario el ID del usuario.
     * @return una lista de transacciones asociadas al usuario.
     */
    List<Transaccion> buscarPorUsuario(Integer idUsuario);
}
