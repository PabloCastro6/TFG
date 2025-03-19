<template>
  <div class="configuracion-ahorro">
    <div class="contenedor">
      <!-- 📅 Calendario con transacciones filtradas -->
      <Calendario :transacciones="transacciones" :usuarioId="userId" @fecha-seleccionada="actualizarFecha" />
      

      <!-- 📌 Formulario de transacciones -->
      <TiposTransacciones :fechaPreseleccionada="fechaSeleccionada" @nueva-transaccion="agregarTransaccion" />
    </div>
  </div>
</template>

<script>
import Calendario from "@/components/Calendario.vue";
import TiposTransacciones from "@/components/TiposTransacciones.vue";

export default {
  name: "ConfiguracionAhorro",
  components: {
    Calendario,
    TiposTransacciones,
  },
  data() {
    return {
      transacciones: [],
      fechaSeleccionada: "",
      userId: localStorage.getItem("userId") ? parseInt(localStorage.getItem("userId")) : null
    };
  },
  watch: {
    transacciones: {
      handler(nuevasTransacciones) {
        console.log("🔄 Transacciones en ConfiguracionAhorro.vue:", nuevasTransacciones);
      },
      deep: true,
      immediate: true
    }
  },
  mounted() {
    this.obtenerTransacciones();
  },
  methods: {
    async obtenerTransacciones() {
      try {
        const response = await fetch("http://localhost:8080/transacciones");
        const data = await response.json();

        console.log("📡 Datos recibidos del backend:", data);

        if (!Array.isArray(data)) {
          console.error("❌ Error: El backend no devuelve un array de transacciones, sino:", data);
          return;
        }

        // Filtrar transacciones del usuario logueado
        this.transacciones = data.filter(t => t.usuario && t.usuario.idUsuario === this.userId);
        console.log("✅ Transacciones del usuario:", this.transacciones);
      } catch (error) {
        console.error("❌ Error al obtener transacciones:", error);
      }
    },
    agregarTransaccion(transaccion) {
      this.transacciones.push(transaccion);
    },
    actualizarFecha(fecha) {
      this.fechaSeleccionada = fecha;
    },
  },
};
</script>

<style scoped>
.contenedor {
  display: flex;
  justify-content: space-between;
  gap: 20px;
  max-width: 1200px;
  margin: auto;
  padding: 20px;
}

.configuracion-ahorro {
  text-align: center;
}
</style>
