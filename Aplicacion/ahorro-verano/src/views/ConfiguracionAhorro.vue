<template>
  <div class="configuracion-ahorro">
    <div class="contenedor">
      <!-- 📅 Calendario con transacciones filtradas -->
      <Calendario
        :transacciones="transacciones"
        :usuarioId="userId"
        @fecha-seleccionada="actualizarFecha"
        @eliminar-transaccion="eliminarTransaccionPadre"
      />
      <!-- 📌 Formulario de transacciones -->
      <TiposTransacciones
        :fechaPreseleccionada="fechaSeleccionada"
        @nueva-transaccion="agregarTransaccion"
      />
    </div>
  </div>
</template>

<script>
import Calendario from "@/components/Calendario.vue";
import TiposTransacciones from "@/components/TiposTransacciones.vue";
import { eventBus } from "@/eventBus.js";

export default {
  name: "ConfiguracionAhorro",
  components: {
    Calendario,
    TiposTransacciones,
  },
  data() {
    return {
      transacciones: [],
      recordatorios: [], // Aquí se almacenarán los recordatorios obtenidos del backend
      fechaSeleccionada: "",
      userId: localStorage.getItem("userId")
        ? parseInt(localStorage.getItem("userId"))
        : null,
    };
  },

  watch: {
    transacciones: {
      handler(nuevasTransacciones) {
        console.log(
          "🔄 Transacciones en ConfiguracionAhorro.vue:",
          nuevasTransacciones
        );
      },
      deep: true,
      immediate: true,
    },
  },

  mounted() {
    this.obtenerTransacciones();
    this.obtenerRecordatorios();

    // Escuchar cuando se cree un nuevo recordatorio desde cualquier otro componente
    eventBus.on("nuevo-recordatorio", (recordatorio) => {
      this.recordatorios.push(recordatorio);
      console.log(
        "✅ Nuevo recordatorio recibido en ConfiguracionAhorro:",
        recordatorio
      );
    });
  },
  methods: {
    async obtenerTransacciones() {
      try {
        const response = await fetch("http://localhost:8080/transacciones");
        const data = await response.json();

        console.log("📡 Datos recibidos del backend:", data);

        if (!Array.isArray(data)) {
          console.error(
            "❌ Error: El backend no devuelve un array de transacciones, sino:",
            data
          );
          return;
        }
      } catch (error) {
        console.error("❌ Error al obtener transacciones:", error);
      }
    },
    async obtenerRecordatorios() {
      try {
        const response = await fetch(`http://localhost:8080/recordatorios`);
        const data = await response.json();

        if (Array.isArray(data)) {
          this.recordatorios = data;
          console.log("✅ Recordatorios cargados:", this.recordatorios);
        } else {
          console.error("❌ Error: El backend no devuelve un array:", data);
        }
      } catch (error) {
        console.error("❌ Error al obtener recordatorios:", error);
      }
    },
    agregarTransaccion(transaccion) {
      this.obtenerTransacciones();
    },
    actualizarFecha(fecha) {
      this.fechaSeleccionada = fecha;
    },
    async eliminarTransaccionPadre(idTransaccion) {
      try {
        const response = await fetch(
          `http://localhost:8080/transacciones/transaccion/${idTransaccion}`,
          {
            method: "DELETE",
          }
        );

        if (!response.ok) {
          console.error(
            "❌ Error al eliminar la transacción en la base de datos"
          );
          return;
        }
        this.transacciones = this.transacciones.filter(
          (t) => t.idTransaccion !== idTransaccion
        );
      } catch (error) {
        console.error("❌ Error al eliminar la transacción:", error);
      }
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
