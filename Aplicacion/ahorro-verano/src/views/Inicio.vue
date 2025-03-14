<template>
  <div class="inicio">
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
              Si reduces tus gastos un <strong>{{ porcentajeReduccion }}%</strong>, podrías ahorrar:
              <strong>{{ ahorroEstimado.toFixed(2) }}€</strong> al mes.
            </p>
            <button class="boton-cerrar" @click="mostrarCalculadora = false">Cerrar</button>
          </div>
        </transition>

        <!-- Bloque de texto (logo, h1 y p) -->
        <div class="text-section">
          <img src="../assets/ahorro.png" alt="Logo" class="logo" />
          <h1 class="titulo">Bienvenidos a mi aplicación de ahorros</h1>
          <p class="frase">"Ahorrar no es solo guardar, sino saber gastar"</p>
        </div>
      </div>

      <!-- Formulario de inicio de sesión -->
      <div class="login-form">
        <h2>Iniciar sesión</h2>
        <form @submit.prevent="submitForm">
          <div class="form-group">
            <label for="email">Correo Electrónico:</label>
            <input type="email" id="email" v-model="email" required placeholder="Introduce tu correo electrónico" />
          </div>
          <div class="form-group">
            <label for="password">Contraseña:</label>
            <input type="password" id="password" v-model="password" required placeholder="Introduce tu contraseña" />
          </div>
          <button type="submit" class="btn-submit">Iniciar sesión</button>
        </form>
        <button type="button" class="btn-register" @click="goToRegistroUsuarios">Crear nuevo usuario</button>
      </div>
    </div>

    <!-- Botón para abrir la calculadora -->
    <button class="boton-calculadora" @click="mostrarCalculadora = true">
      📊 ¿Cuánto podrías ahorrar?
    </button>
  </div>
</template>


<script>
export default {
  name: "PaginaInicio",
  data() {
    return {
      mostrarCalculadora: false,
      email: "",
      password: "",
      gastoDiario: 10, // Valor por defecto
      porcentajeReduccion: 10, // Valor por defecto
    };
  },
  methods: {
    submitForm() {
      // Aquí iría la lógica real de autenticación.
      // Suponiendo que la autenticación es exitosa:
      localStorage.setItem("registrado", "true");
      alert(`Inicio de sesión exitoso para ${this.email}`);
      // Opcional: Redirigir a una vista protegida
      // this.$router.push("/ConfiguracionAhorro");
    },
    goToRegistroUsuarios() {
      // Redirige a la vista de RegistroUsuarios
      this.$router.push("/RegistroUsuarios");
    },
  },
  computed: {
    ahorroEstimado() {
      return ((this.gastoDiario * this.porcentajeReduccion) / 100) * 30;
    },
  },
};
</script>

<style scoped>
/* Contenedor principal */
.inicio {
  padding: 50px 20px;
  max-width: 1000px;
  margin: auto;
  position: relative; /* Permite posicionar la calculadora de forma absoluta dentro */
}

/* Contenedor superior que organiza todo */
.top-container {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 20px;
  position: relative; /* Para que la calculadora no afecte otros elementos */
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
}

.frase {
  font-style: italic;
  font-size: 18px;
  color: #666;
  margin-bottom: 30px;
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
  background-color: #2ecc71;
  color: white;
  padding: 12px;
  border: none;
  border-radius: 6px;
  font-size: 16px;
  cursor: pointer;
  transition: background 0.3s ease;
}

.btn-submit:hover {
  background-color: #27ae60;
}

.btn-register {
  width: 100%;
  margin-top: 10px;
  background-color: #007bff;
  color: white;
  padding: 12px;
  border: none;
  border-radius: 6px;
  font-size: 16px;
  cursor: pointer;
  transition: background 0.3s ease;
}

.btn-register:hover {
  background-color: #0056b3;
}

/* Botón de la calculadora */
.boton-calculadora {
  background-color: #2ecc71;
  color: white;
  font-size: 16px;
  font-weight: bold;
  padding: 12px 20px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background 0.3s ease-in-out;
  margin-left: 9%;
}

.boton-calculadora:hover {
  background-color: #27ae60;
}

/* Estilos de la calculadora */
.calculadora {
  background: #90c7cd; /* Color sólido, sin opacidad */
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  text-align: center;
  max-width: 290px;
  position: absolute; /* La deja fija sin afectar al resto */
  left: -270px; /* Mueve la calculadora a la izquierda del h1 */
  top: 50px; /* Ajusta la posición vertical */
  animation: fadeIn 0.6s ease-in-out;
}

/* Transición de la calculadora */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.6s ease;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}

/* Ajustes para evitar solapamiento en pantallas pequeñas */
@media (max-width: 1024px) {
  .calculadora {
    position: static; /* Si la pantalla es pequeña, la muestra normalmente */
    margin-bottom: 20px;
  }
}

/* Animación de aparición */
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
</style>
