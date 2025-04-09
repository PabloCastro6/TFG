<!-- Hacer una estadisticas del DIA QUE SE ELIJA sobre los gastos y los ingresos
   y del MES global -->

<template>
  <div class="estadisticas-container">
    <h2>📊 Estadísticas de {{ nombreMes }} {{ anio }}</h2>
    <p>Usuario ID: {{ usuarioId }}</p>

    <button @click="cargarEstadisticas">📥 Cargar estadísticas</button>

    <div v-if="resumen">
      <h3>Resumen mensual</h3>
      <ul>
        <li>💰 Ingresos: {{ resumen.ingresos }} €</li>
        <li>💸 Gastos: {{ resumen.gastos }} €</li>
        <li>📈 Saldo: {{ resumen.saldo }} €</li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "Estadisticas",

  data() {
    const hoy = new Date();
    return {
      mes: hoy.getMonth() + 1,
      anio: hoy.getFullYear(),
      resumen: null,
      usuarioId: parseInt(localStorage.getItem("usuarioId")) || 0, //recuperar el ID
    };
  },
  computed: {
    nombreMes() {
      return new Date(this.anio, this.mes - 1).toLocaleString("es-ES", {
        month: "long",
      });
    },
  },
  methods: {
    async cargarEstadisticas() {
      /*PRUEBA*/
      this.resumen = {
        ingresos: 1200,
        gastos: 800,
        saldo: 400,
      };
      try {
        const response = await axios.get(
          `http://localhost:8080/api/estadisticas/${this.usuarioId}`,
          {
            params: {
              mes: this.mes,
              anio: this.anio,
            },
          }
        );
        this.resumen = response.data;
        console.log("📊 Resumen recibido:", this.resumen);
      } catch (error) {
        console.error("❌ Error al obtener estadísticas:", error);
      }
    },
  },
};
</script>

<style scoped>
.estadisticas-container {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}

button {
  margin-bottom: 20px;
  background-color: #3498db;
  color: white;
  padding: 10px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
}

button:hover {
  background-color: #2980b9;
}
</style>
