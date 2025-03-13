<template>
  <div class="inicio">
    <div class="top-container">
      <!-- Bloque de texto (logo, h1 y p) -->
      <div class="text-section">
        <img src="../assets/ahorro.png" alt="Logo" class="logo" />
        <h1 class="titulo">Bienvenidos a mi aplicación de ahorros</h1>
        <p class="frase">"Ahorrar no es solo guardar, sino saber gastar"</p>
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
      </div>
    </div>

    <!-- Botón para abrir la calculadora -->
    <button
      class="boton-calculadora"
      @click="mostrarCalculadora = true"
    >
      📊 ¿Cuánto podrías ahorrar?
    </button>

    <!-- Calculadora de ahorro -->
    <transition name="fade">
      <div v-if="mostrarCalculadora" class="calculadora">
        <!-- Contenido de la calculadora -->
        <h3 class="gasto">Calculadora de Ahorro</h3>
        <label for="gasto" class="gasto">Gasto Diario (€):</label>
        <input
          type="number"
          v-model.number="gastoDiario"
          placeholder="Introduce tu gasto diario"
        />
        <label for="reduccion" class="reduccion">Reducción de Gasto (%)</label>
        <input
          type="range"
          v-model.number="porcentajeReduccion"
          min="0"
          max="50"
        />
        <p class="valor-reduccion">{{ porcentajeReduccion }}%</p>
        <p class="resultado">
          Si reduces tus gastos un <strong>{{ porcentajeReduccion }}%</strong>,
          podrías ahorrar: <strong>{{ ahorroEstimado.toFixed(2) }}€</strong> al
          mes.
        </p>
        <button class="boton-cerrar" @click="mostrarCalculadora = false">
          Cerrar
        </button>
      </div>
    </transition>
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
      mostrarCalculadora: false,
      gastoDiario: 10, // Valor por defecto
      porcentajeReduccion: 10, // Valor por defecto
    };
  },
  methods: {
    submitForm() {
      alert(`Correo: ${this.email}, Contraseña: ${this.password}`);
    }
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
  max-width: 800px;
  margin: auto;
}

/* Contenedor que agrupa el texto y el formulario */
.top-container {
  display: flex;
  justify-content: space-between;
  align-items: flex-start; /* O "center" si prefieres centrado vertical */
  gap: 20px; /* Espacio entre ambos bloques */
}

/* Bloque de texto */
.text-section {
  flex: 1; /* Toma el espacio disponible */
}

/* Ajustes en el texto */
.logo {
  width: 280px;
  margin-bottom: 20px;
  margin-left: 7%;
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
  width: 300px; /* Fija el ancho deseado */
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  margin-top: 6%;
}

/* Resto de estilos del formulario */
.login-form h2 {
  font-size: 22px;
  margin-bottom: 15px;
  text-align: center;
}

.form-group {
  margin-bottom: 15px;
  text-align: left;
}

.form-group label {
  font-weight: bold;
  display: block;
  margin-bottom: 5px;
}

.form-group input {
  width: 100%;
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
}

.boton-calculadora:hover {
  background-color: #27ae60;
}

/* Estilos de la calculadora y animaciones permanecen iguales */
.calculadora {
  background: #22a9b77a;
  padding: 20px;
  margin-top: 20px;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  text-align: center;
  max-width: 400px;
  margin-left: auto;
  margin-right: auto;
  animation: fadeIn 0.6s ease-in-out;
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

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
</style>
