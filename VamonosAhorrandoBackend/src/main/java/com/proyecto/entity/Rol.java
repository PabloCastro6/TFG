package com.proyecto.entity;


/**
 * Enumeración que representa los diferentes roles que puede tener un usuario en la aplicación.
 * 
 * <ul>
 *   <li><b>USUARIO:</b> Rol básico con acceso limitado (solo puede gestionar sus propios datos).</li>
 *   <li><b>ADMINISTRADOR:</b> Rol con permisos ampliados, como visualizar todos los usuarios o administrar registros.</li>
 * </ul>
 * 
 * <p>Esta enumeración se utiliza principalmente para el control de acceso y visualización de opciones en la interfaz.</p>
 * 
 */
public enum Rol { 
	USUARIO,
    ADMINISTRADOR

}
