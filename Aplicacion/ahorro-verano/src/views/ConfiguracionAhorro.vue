<template>
  <div class="configuracion-ahorro">
    <div class="contenedor">
      <!-- 📅 Calendario con transacciones filtradas -->
      <Calendario :transacciones="transacciones" :usuarioId="userId" @fecha-seleccionada="actualizarFecha"
        @eliminar-transaccion="eliminarTransaccionPadre" />
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
      // Si el userId se almacena en localStorage como string, considera pasarlo sin parseInt o ajusta la prop en Calendario
      userId: localStorage.getItem("userId")
        ? parseInt(localStorage.getItem("userId"))
        : null,
    };
  },
  watch: {
    transacciones: {
      handler(nuevasTransacciones) {
        console.log("🔄 Transacciones en ConfiguracionAhorro.vue:", nuevasTransacciones);
      },
      deep: true,
      immediate: true,
    },
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
        this.transacciones = data.filter(
          (t) => t.usuario && t.usuario.idUsuario === this.userId
        );
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
    async eliminarTransaccionPadre(idTransaccion) {
      try {

        const response = await fetch(`http://localhost:8080/transacciones/transaccion/${idTransaccion}`, {
          method: 'DELETE',
        });
        if (!response.ok) {
          console.error("❌ Error al eliminar la transacción en la base de datos");
          return;
        }
        const transaccionEliminada = this.transacciones.find(
          (t) => t.idTransaccion === idTransaccion
        );
        // Eliminarla del array local
        this.transacciones = this.transacciones.filter(
          (t) => t.idTransaccion !== idTransaccion
        );
        if (transaccionEliminada) {
          console.log(`Transacción "${transaccionEliminada.nombre}" eliminada`);
        } else {
          console.log(`No se encontró la transacción con id ${idTransaccion}`);
        }
      } catch (error) {
        console.error("❌ Error al eliminar la transacción:", error);
      }
    }
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
