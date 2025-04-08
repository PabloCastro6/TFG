<template>
  <div class="inicio">
    <!-- Botón de cerrar sesión en la esquina superior derecha, se muestra si usuarioLogueado es true -->
    <button v-if="usuarioLogueado" class="logout-button" @click="cerrarSesion">
      Cerrar sesión
    </button>

    <div class="top-container">
      <!-- Contenedor para la calculadora y el bloque de texto -->
      <div class="left-container">
        <!-- Calculadora de ahorro -->
        <transition name="fade">
          <div v-if="mostrarCalculadora" class="calculadora">
            <h3 class="gasto">Calculadora de Ahorro</h3>
            <label for="gasto" class="gasto">Gasto Diario (€):</label>
            <input type="number" v-model.number="gastoDiario" placeholder="Introduce tu gasto diario" />
            <label for="reduccion" class="reduccion">Reducción de Gasto (%)</label>
            <input type="range" v-model.number="porcentajeReduccion" min="0" max="50" />
            <p class="valor-reduccion">{{ porcentajeReduccion }}%</p>
            <p class="resultado">
              Si reduces tus gastos un
              <strong>{{ porcentajeReduccion }}%</strong>, podrías ahorrar:
              <strong>{{ ahorroEstimado.toFixed(2) }}€</strong> al mes.
            </p>
            <button class="boton-cerrar" @click="mostrarCalculadora = false">
              Cerrar
            </button>
          </div>
        </transition>

        <!-- Bloque de texto (logo, h1 y p) -->
        <div class="text-section">
          <h1 class="titulo">Bienvenidos a mi aplicación de ahorros</h1>
          <p class="frase">"Ahorrar no es solo guardar, sino saber gastar"</p>
          <img src="../assets/ahorro.png" alt="Logo" class="logo" />
        </div>
      </div>

      <!-- Formulario de inicio de sesión -->
      <div class="login-form">
        <h2>Iniciar sesión</h2>
        <form @submit.prevent="submitForm">
          <div class="form-group">
            <label for="email">Correo Electrónico:</label>
            <input type="email" id="email" v-model="email" required placeholder="Introduce tu correo electrónico"
              autocomplete="username" />
          </div>
          <div class="form-group">
            <label for="password">Contraseña:</label>
            <input type="password" id="password" v-model="password" required placeholder="Introduce tu contraseña"
              autocomplete="current-password" />
          </div>

          <!-- Tipo de usuario-->
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
          <button type="submit" class="btn-submit">Iniciar sesión</button>
        </form>
        <button type="button" class="btn-register" @click="goToRegistroUsuarios">
          Crear nuevo usuario
        </button>
      </div>
    </div>

    <!-- Botón para abrir la calculadora -->
    <button class="boton-calculadora" @click="mostrarCalculadora = true">
      📊 ¿Cuánto podrías ahorrar?
    </button>
  </div>
</template>

<script>
import Swal from "sweetalert2";

export default {
  name: "PaginaInicio",
  data() {
    return {
      mostrarCalculadora: false,
      email: "",
      password: "",
      rol: "",
      gastoDiario: 10,
      porcentajeReduccion: 10,
      usuarioLogueado: localStorage.getItem("registrado") === "true",
      mensajeExito: "",
    };
  },
  computed: {
    ahorroEstimado() {
      return ((this.gastoDiario * this.porcentajeReduccion) / 100) * 30;
    },
  },
  methods: {
    async submitForm() {
      try {
        const response = await fetch(
          "http://localhost:8080/usuarios/iniciarSesion",
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              correo: this.email,
              password: this.password,
              rol: this.rol.toUpperCase(), // Asegúrate de que el rol esté en mayúsculas
            }),
          }
        );

        if (!response.ok) {
          const errorText = await response.text();
          throw new Error(errorText);
        }

        const data = await response.json();
        console.log("Respuesta del servidor:", data);

        if (data.success) {
          localStorage.setItem("registrado", "true");
          localStorage.setItem("correo", this.email);
          localStorage.setItem("userId", data.userId);
          localStorage.setItem("nombreUsuario", data.nombre);
          localStorage.setItem("rol", this.rol); // ✅ LÍNEA AÑADIDA

          this.usuarioLogueado = true;
          this.nombreUsuario = data.nombre;
          this.mensajeExito = data.message;

          Swal.fire({
            title: `¡Bienvenido!`,
            text: data.message,
            icon: "success",
            confirmButtonText: "¡Perfecto!",
            customClass: {
              confirmButton: 'miBotonCancelar'
            }
          });

          setTimeout(() => {
            this.$router.push("/");
          }, 2000);
        } else {
          this.mensajeExito = "Credenciales incorrectas. Inténtalo de nuevo.";
        }
      } catch (error) {
        console.error("Error en inicio de sesión:", error);

        if (error.message.includes("permisos")) {
          Swal.fire({
            icon: "error",
            title: "❌ Acceso denegado",
            text: error.message,
          });
        } else if (error.message.includes("Credenciales")) {
          Swal.fire({
            icon: "error",
            title: "❌ Credenciales inválidas",
            text: "Correo o contraseña incorrectos.",
          });
        } else {
          Swal.fire({
            icon: "error",
            title: "❌ Error",
            text: "Hubo un problema al iniciar sesión. Inténtalo de nuevo.",
          });
        }
      }
    },
    goToRegistroUsuarios() {
      this.$router.push("/RegistroUsuarios");
    },

    cerrarSesion() {
      localStorage.clear();
      this.usuarioLogueado = false;

      Swal.fire({
        icon: "info",
        title: "👋 Sesión cerrada",
        text: "Has cerrado sesión correctamente.",
        confirmButtonText: "Okey",
        customClass: {
          confirmButton: "miBotonCancelar",
        }
      }).then(() => {
        this.$router.push("/");
      });
    },
  },
  mounted() {
    this.usuarioLogueado = localStorage.getItem("registrado") === "true";
  },
};
</script>

<style scoped>
/* Botón de cerrar sesión en la esquina superior derecha */
.logout-button {
  position: absolute;
  top: 10px;
  right: 10px;
  background-color: #e74c3c;
  color: white;
  border: none;
  padding: 10px 15px;
  border-radius: 5px;
  cursor: pointer;
  font-weight: bold;
  z-index: 2000;
  /* Asegura que esté encima de otros elementos */
}

.logout-button:hover {
  background-color: #c0392b;
}

/* Contenedor principal */
.inicio {
  padding: 50px 20px;
  max-width: 1000px;
  margin: auto;
  position: relative;
  /* Para que el botón posicionado sea relativo a este contenedor */
}

/* Contenedor superior que organiza todo */
.top-container {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 20px;
  position: relative;
}

/* Contenedor que agrupa la calculadora y el texto */
.left-container {
  display: flex;
  align-items: center;
  gap: 20px;
  position: relative;
}

/* Bloque de texto */
.text-section {
  flex: 1;
  margin-left: 20%;
}

/* Ajustes en el texto */
.logo {
  width: 420px;
  margin-bottom: 20px;
  margin-left: 5%;
}

.titulo {
  font-size: 28px;
  font-weight: bold;
  margin-bottom: 10px;
  color: #2c3e50;
  width: 100%;
  margin-left: 5%;
}

.frase {
  font-style: italic;
  font-size: 18px;
  color: #666;
  margin-bottom: 30px;
  margin-left: 5%;
}

/* Estilos del formulario */
.login-form {
  width: 300px;
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  margin-top: 6%;
}

.login-form h2 {
  font-size: 22px;
  margin-bottom: 15px;
  text-align: center;
}

.form-group {
  margin-bottom: 15px;
  text-align: center;
}

.form-group label {
  font-weight: bold;
  display: block;
  margin-bottom: 5px;
}

.form-group input {
  width: 90%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 16px;
}

.btn-submit {
  width: 100%;
  background: #2c3e50;
  color: white;
  padding: 12px;
  border: none;
  border-radius: 6px;
  font-size: 16px;
  cursor: pointer;
  transition: background 0.3s ease;
}

.btn-submit:hover {
  background: #1a252f;
}

.btn-register {
  width: 100%;
  margin-top: 10px;
  background: #2c3e50;
  color: white;
  padding: 12px;
  border: none;
  border-radius: 6px;
  font-size: 16px;
  cursor: pointer;
  transition: background 0.3s ease;
}

.btn-register:hover {
  background: #1a252f;
}

/*Tipo de usuario*/
.rol-buttons {
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-top: 10px;
}

.rol-buttons button {
  flex: 1;
  padding: 6px;
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

/* Botón para abrir la calculadora */
.boton-calculadora {
  background-color: #ff9800;
  color: white;
  font-size: 16px;
  font-weight: bold;
  padding: 12px 20px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background 0.3s ease-in-out;
  margin-left: 21%;
}

.boton-calculadora:hover {
  background-color: #d38f28;
}

/* Estilos de la calculadora */
.calculadora {
  background: #90c7cd;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  text-align: center;
  max-width: 290px;
  position: absolute;
  left: -270px;
  top: 50px;
  animation: fadeIn 0.6s ease-in-out;
}

/* Clases de transición para Vue */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.6s ease-in-out, transform 0.6s ease-in-out;
}

.fade-enter,
.fade-leave-to

/* .fade-leave-active para versiones anteriores de Vue */
  {
  opacity: 0;
  transform: translateY(-10px);
}

.boton-cerrar {
  background-color: #002b71;
  color: white;
  font-size: 16px;
  font-weight: bold;
  padding: 9px 10px;
  margin-left: 70px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background 0.3s ease-in-out, transform 0.2s ease;
  margin-top: 15px;
  display: block;
  width: 50%;
  text-transform: uppercase;
  box-shadow: 0 4px 10px rgba(231, 76, 60, 0.4);
}

.boton-cerrar:hover {
  background-color: #060036;
  transform: scale(1.02);
}

.boton-cerrar:active {
  transform: scale(1.05);
}

input[type="number"] {
  width: 80%;
  padding: 8px;
  font-size: 16px;
  border: 2px solid #060036;
  border-radius: 8px;
  outline: none;
  transition: all 0.3s ease-in-out;
  text-align: center;
  background-color: #f9f9f9;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

input[type="number"]:focus {
  border-color: #060036;
  box-shadow: 0 0 8px #1300a2;
  background-color: #fff;
}

@media (max-width: 1024px) {
  .calculadora {
    position: static;
    margin-bottom: 20px;
  }
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(-10px);
  }

  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes fadeOut {
  from {
    opacity: 1;
  }

  to {
    opacity: 0;
  }
}
</style>
