<template>
  <div class="registro-y-tabla">
    <!-- 📋 FORMULARIO -->
    <div class="registro-container">
      <h2 class="titulo">👤 Registrar Usuario</h2>
      <form class="formulario" @submit.prevent="registrarUsuario">
        <div class="campo">
          <label>Nombre Completo:</label>
          <input v-model="usuario.nombreCompleto" type="text" placeholder="Pepe Perez" required />
        </div>
        <div class="campo">
          <label>Correo:</label>
          <input v-model="usuario.correo" type="email" placeholder="correo@correo.com" required />
        </div>
        <div class="campo">
          <label>Contraseña:</label>
          <input v-model="usuario.password" type="password" placeholder="********" required />
        </div>

        <div class="form-group">
          <label>Tipo de usuario:</label>
          <div class="rol-buttons">
            <button type="button" :class="{ activo: rol === 'USUARIO' }" @click="rol = 'USUARIO'">
              👤 Usuario
            </button>
            <button type="button" :class="{ activo: rol === 'ADMINISTRADOR' }" @click="rol = 'ADMINISTRADOR'">
              🛠️ Administrador
            </button>
          </div>
        </div>

        <button class="guardar-btn" type="submit">Registrar</button>

        <button class="cerrar-sesion" v-if="esAdmin" @click="cerrarSesion">
          Cerrar sesión
        </button>
      </form>
    </div>

    <!-- 📊 TABLA DE USUARIOS -->
    <div v-if="esAdmin" class="tabla-usuarios">
      <h3>📋 Lista de Usuarios</h3>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Correo</th>
            <th>Rol</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="u in listaUsuarios" :key="u.idUsuario">
            <td>{{ u.idUsuario }}</td>

            <template v-if="usuarioEditando === u.idUsuario">
              <td><input v-model="usuarioEditado.nombreCompleto" /></td>
              <td><input v-model="usuarioEditado.correo" /></td>
              <td>
                <select v-model="usuarioEditado.rol">
                  <option value="USUARIO">Usuario</option>
                  <option value="ADMINISTRADOR">Administrador</option>
                </select>
              </td>
              <td>
                <button @click="guardarEdicion">💾</button>
                <button @click="cancelarEdicion">❌</button>
              </td>
            </template>

            <template v-else>
              <td>{{ u.nombreCompleto }}</td>
              <td>{{ u.correo }}</td>
              <td>{{ u.rol }}</td>
              <td>
                <template v-if="u.rol === 'USUARIO'">
                  <button @click="editarUsuario(u)">✏️</button>
                  <button @click="eliminarUsuario(u.idUsuario)">🗑️</button>
                </template>
                <span v-else>-</span>
              </td>
            </template>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      usuario: {
        nombreCompleto: "",
        correo: "",
        password: "",
      },
      rol: "",
      listaUsuarios: [],
      esAdmin: false,
      usuarioEditando: null,
      usuarioEditado: null,
    };
  },
  methods: {
    async registrarUsuario() {
      try {
        const response = await fetch("http://localhost:8080/usuarios", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ ...this.usuario, rol: this.rol }),
        });

        if (!response.ok) throw new Error("Error al registrar usuario");

        const data = await response.json();
        const usuarioRegistrado = data.usuarioRegistrado || data;

        localStorage.setItem("userId", usuarioRegistrado.idUsuario);
        localStorage.setItem("correo", usuarioRegistrado.correo);
        localStorage.setItem("rol", usuarioRegistrado.rol);
        localStorage.setItem("registrado", "true");

        alert("✅ Usuario registrado correctamente");

        if (usuarioRegistrado.rol === "ADMINISTRADOR") {
          this.esAdmin = true;
          await this.obtenerUsuarios(); // ⚠️ Importante el await
        } else {
          this.$router.push("/ConfiguracionAhorro");
        }
      } catch (error) {
        console.error("❌ Error al registrar usuario:", error);
        alert("❌ No se pudo registrar el usuario.");
      }
    },

    async obtenerUsuarios() {
      try {
        const response = await fetch("http://localhost:8080/usuarios");
        this.listaUsuarios = await response.json();
      } catch (error) {
        console.error("❌ Error al obtener usuarios:", error);
      }
    },

    async eliminarUsuario(id) {
      if (!confirm("¿Eliminar este usuario?")) return;
      try {
        await fetch(`http://localhost:8080/usuarios/${id}`, {
          method: "DELETE",
        });
        this.listaUsuarios = this.listaUsuarios.filter(
          (u) => u.idUsuario !== id
        );
      } catch (error) {
        console.error("❌ Error al eliminar usuario:", error);
      }
    },

    editarUsuario(usuario) {
      this.usuarioEditando = usuario.idUsuario;
      this.usuarioEditado = { ...usuario };
    },

    cerrarSesion() {
      localStorage.clear();
      this.esAdmin = false;
      this.listaUsuarios = [];
      alert("Sesión cerrada.");
    },
    cancelarEdicion() {
      this.usuarioEditando = null;
      this.usuarioEditado = null;
    },
    async guardarEdicion() {
      try {
        const response = await fetch(
          `http://localhost:8080/usuarios/${this.usuarioEditado.idUsuario}`,
          {
            method: "PUT",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(this.usuarioEditado),
          }
        );

        if (!response.ok) throw new Error("Error al guardar cambios");

        // Actualiza la fila editada en la lista
        const index = this.listaUsuarios.findIndex(
          (u) => u.idUsuario === this.usuarioEditado.idUsuario
        );
        if (index !== -1) {
          this.listaUsuarios.splice(index, 1, { ...this.usuarioEditado });
        }

        // Si se cambió el rol a ADMINISTRADOR, ocultar acciones
        this.usuarioEditando = null;
        this.usuarioEditado = null;
        alert("✅ Cambios guardados correctamente");
      } catch (error) {
        console.error("❌ Error al guardar edición:", error);
        alert("❌ No se pudo guardar la edición.");
      }
    },

    checkRol() {
      const rolGuardado = localStorage.getItem("rol");
      const registrado = localStorage.getItem("registrado");

      if (rolGuardado === "ADMINISTRADOR" && registrado === "true") {
        this.esAdmin = true;
        this.obtenerUsuarios(); // 👈 carga la tabla
      } else {
        this.esAdmin = false;
        this.listaUsuarios = [];
      }
    },
  },
  mounted() {
    this.checkRol();
  },
};
</script>

<style scoped>
.registro-y-tabla {
  display: flex;
  justify-content: center;
  align-items: flex-start;
  gap: 30px;
  margin-top: 50px;
  flex-wrap: wrap;
}

.registro-container {
  width: 350px;
  background: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
}

.titulo {
  font-size: 22px;
  margin-bottom: 20px;
  font-weight: bold;
  text-align: center;
}

.formulario {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.campo {
  display: flex;
  flex-direction: column;
  text-align: left;
}

.campo label {
  font-weight: bold;
  margin-bottom: 5px;
}

.campo input {
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;
}

.guardar-btn,
.cerrar-sesion {
  background: #2c3e50;
  color: white;
  font-size: 1rem;
  font-weight: bold;
  padding: 12px;
  margin-top: 10px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background 0.3s ease-in-out;
}

.guardar-btn:hover,
.cerrar-sesion:hover {
  background: #1a252f;
}

.rol-buttons {
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-top: 10px;
}

.rol-buttons button {
  flex: 1;
  padding: 10px;
  font-size: 16px;
  font-weight: bold;
  border: 2px solid #ccc;
  border-radius: 6px;
  background-color: white;
  cursor: pointer;
}

.rol-buttons button.activo {
  background-color: #007d8b;
  color: white;
  border-color: #083954;
}

.rol-buttons button:hover {
  background-color: #ecf0f1;
}

.tabla-usuarios {
  flex: 1;
  background: white;
  padding: 70px;
  padding-right: 250px;
  border-radius: 10px;
  max-width: 700px;
  box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.1);
}

.tabla-usuarios table {
  width: 100%;
  border-collapse: collapse;
}

.tabla-usuarios th,
.tabla-usuarios td {
  padding: 12px;
  border: 1px solid #ddd;
  text-align: center;
}

.tabla-usuarios th {
  background-color: #2c3e50;
  color: white;
}
</style>
