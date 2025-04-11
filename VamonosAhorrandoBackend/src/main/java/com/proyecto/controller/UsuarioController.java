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

        // Validar si el rol que intenta usar coincide con el de la base de datos
        if (usuario.getRol() != null && !usuario.getRol().equals(existente.getRol())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Este usuario no tiene permisos para iniciar sesión como " + usuario.getRol());
        }
        
        if (usuario.getRol() != null && !usuario.getRol().name().equalsIgnoreCase(existente.getRol().name())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Este usuario no tiene permisos para iniciar sesión como " + usuario.getRol());
        }


        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("success", true);
        respuesta.put("userId", existente.getIdUsuario());
        respuesta.put("nombre", existente.getNombreCompleto());
        respuesta.put("rol", existente.getRol()); // 🔥 Puedes devolverlo si quieres

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
        Usuario guardado = usuarioService.guardar(usuario);

        if (usuario.getRol() == Rol.ADMINISTRADOR) {
            List<Usuario> todos = usuarioService.obtenerTodos();
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("usuarioRegistrado", guardado);
            respuesta.put("todosLosUsuarios", todos);
            return ResponseEntity.ok(respuesta);
        }

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
        existente.setRol(datos.getRol());

        return ResponseEntity.ok(usuarioService.guardar(existente));
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
