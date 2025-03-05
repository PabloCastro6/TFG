<template>
  <div class="inicio">
    <img src="../assets/ahorro.png" alt="Logo" class="logo" />
    <h1 class="titulo">Bienvenidos a mi aplicación de ahorros</h1>
    <p class="frase">"Ahorrar no es solo guardar, sino saber gastar"</p>

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
      gastoDiario: 10, // Valor por defecto
      porcentajeReduccion: 10, // Valor por defecto
    };
  },
  computed: {
    ahorroEstimado() {
      return ((this.gastoDiario * this.porcentajeReduccion) / 100) * 30;
    },
  },
};
</script>

<style scoped>
/* ======= ESTILOS GENERALES ======= */
body {
  font-family: "Poppins", sans-serif;
  margin: 0;
  padding: 0;
  background-color: #f4f4f9;
  color: #2c3e50;
  text-align: center;
}

/* ======= SECCIÓN PRINCIPAL ======= */
.inicio {
  padding: 50px 20px;
  max-width: 800px;
  margin: auto;
}

/* Logo */
.logo {
  width: 180px;
  margin-bottom: 20px;
}

/* Título */
.titulo {
  font-size: 28px;
  font-weight: bold;
  margin-bottom: 10px;
  color: #2c3e50;
}

/* Frase inspiradora */
.frase {
  font-style: italic;
  font-size: 18px;
  color: #666;
  margin-bottom: 30px;
}

/* ======= BOTÓN DE LA CALCULADORA ======= */
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

/* ======= CALCULADORA ======= */
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

/* Títulos dentro de la calculadora */
.calculadora h3 {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 15px;
}

/* Inputs */
.calculadora input[type="number"] {
  width: 80%;
  padding: 8px;
  margin: 10px 0;
  border: 2px solid #ccc;
  border-radius: 6px;
  font-size: 16px;
  text-align: center;
}

/* Rango de reducción */
.calculadora input[type="range"] {
  width: 100%;
  margin: 10px 0;
}

.valor-reduccion {
  font-size: 20px;
  font-weight: bold;
  color: #3d0350;
}

/* Resultado */
.resultado, .reduccion, .gasto {
  font-size: 18px;
  font-weight: bold;
  color: #000e3def;
  margin-top: 10px;
}

/* Botón para cerrar la calculadora */
.boton-cerrar {
  background-color: #e74c3c;
  color: white;
  font-size: 17px;
  font-weight: bold;
  padding: 11px;
  padding-left: 35px;
  padding-right: 35px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  margin-top: 15px;
  transition: background 0.6s ease-in-out;
}

.boton-cerrar:hover {
  background-color: #c0392b;
}

/* ======= ANIMACIONES ======= */
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

/* Transición para la calculadora */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
</style>
