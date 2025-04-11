package com.proyecto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Clase principal de la aplicación Spring Boot.
 * 
 * <p>Esta clase se encarga de arrancar el backend de la aplicación
 * "¡Vámonos, ahorrando!".
 * 
 * 
 * @authores [Gonzalo Campos, Hugo Blanco y Pablo Castro ]
 */
@SpringBootApplication
public class VamonosAhorrandoBackendApplication {

	
	/**
    * Método principal que lanza la aplicación Spring Boot.
    *
    */
    
	public static void main(String[] args) {
		SpringApplication.run(VamonosAhorrandoBackendApplication.class, args);
	}

}
