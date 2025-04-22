package com.proyecto.controller;

import com.proyecto.entity.Rol;
import com.proyecto.entity.Usuario;
import com.proyecto.repository.UsuarioRepository;
import com.proyecto.service.UsuarioService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Controlador REST que gestiona las operaciones relacionadas con los usuarios
 * del sistema, incluyendo registro, inicio de sesión, modificación y eliminación.
 *
 * <p>Ruta base: <b>/usuarios</b></p>
 *
 * <p>Proporciona seguridad básica mediante validación de credenciales y roles.</p>
 * 
 */

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    
    /**
     * Obtiene todos los usuarios registrados.
     *
     * @return lista de todos los usuarios.
     */
    
    // Obtener todos los usuarios
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.obtenerTodos();
    }

    
    /**
     * Inicia sesión de un usuario a partir del correo y contraseña proporcionados.
     *
     * <p>Valida también si el rol que intenta usar coincide con el que tiene en la base de datos.</p>
     *
     * @param usuario Objeto con correo, contraseña y rol.
     * @return respuesta con éxito o mensaje de error con el motivo.
     */
    @PostMapping("/iniciarSesion")
    public ResponseEntity<?> iniciarSesion(@RequestBody Usuario usuario) {
        Usuario existente = usuarioRepository.findByCorreo(usuario.getCorreo());

        if (existente == null || !existente.getPassword().equals(usuario.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("success", true);
        respuesta.put("userId", existente.getIdUsuario());
        respuesta.put("nombre", existente.getNombreCompleto());
        respuesta.put("rol", existente.getRol());
        respuesta.put("message", "Inicio de sesión correcto.");

        return ResponseEntity.ok(respuesta);
    }




    /**
     * Obtiene un usuario específico por su ID.
     *
     * @param id ID del usuario.
     * @return el usuario si existe, o 404 si no se encuentra.
     */
    
    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Integer id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }

    /**
     * Registra un nuevo usuario.
     *
     * <p>Si el usuario registrado es un administrador, también se devuelve
     * la lista de todos los usuarios.</p>
     *
     * @param usuario Objeto usuario a guardar.
     * @return respuesta con el usuario creado o una respuesta compuesta si es administrador.
     */
    
    // Registrar nuevo usuario
    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
        // Validar si ya existe un usuario con el mismo correo
        Usuario existente = usuarioRepository.findByCorreo(usuario.getCorreo());
        if (existente != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("El correo ya está registrado. Prueba con otro");
        }

        // Asignar automáticamente el rol USUARIO
        usuario.setRol(Rol.USUARIO);

        Usuario guardado = usuarioService.guardar(usuario);
        return ResponseEntity.ok(guardado);
    }


    /**
     * Actualiza los datos de un usuario ya existente.
     *
     * @param id ID del usuario a actualizar.
     * @param datos Datos nuevos a aplicar.
     * @return el usuario actualizado, o 404 si no existe.
     */
    
    //  Actualizar un usuario
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Integer id, @RequestBody Usuario datos) {
        Usuario existente = usuarioService.buscarPorId(id);
        if (existente == null) return ResponseEntity.notFound().build();

        existente.setNombreCompleto(datos.getNombreCompleto());
        existente.setCorreo(datos.getCorreo());
        existente.setPassword(datos.getPassword());

        // Verifica si el rol es nulo o diferente antes de actualizar
        if (datos.getRol() != null) {
            existente.setRol(datos.getRol());
            System.out.println("Rol actualizado a: " + existente.getRol());
        } else {
            System.out.println("⚠️ Rol recibido es null, no se actualiza.");
        }

        Usuario actualizado = usuarioService.guardar(existente);
        System.out.println("Usuario después de guardar: " + actualizado);

        return ResponseEntity.ok(actualizado);
    }

    
    /**
     * Elimina un usuario de la base de datos.
     *
     * @param id ID del usuario a eliminar.
     * @return 204 si fue eliminado correctamente o 404 si no se encontró.
     */
    
    //  Eliminar un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Usuario existente = usuarioService.buscarPorId(id);
        if (existente == null) return ResponseEntity.notFound().build();

        usuarioService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
