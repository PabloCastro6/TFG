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

    <!-- 📋 Lista de transacciones del mes actual -->
    <div class="lista-transacciones">
      <h3>📋 Transacciones del mes</h3>
      <ul>
        <li
          v-for="trans in transaccionesFiltradasPorMes"
          :key="trans.idTransaccion"
        >
          <span>{{ formatearFecha(trans.fecha) }}:</span>
          <span :class="trans.categoria.nombre.toLowerCase()">
            {{ trans.tipo }} - {{ trans.cantidad }}€
          </span>
          <button
            class="btn-eliminar"
            @click="eliminarTransaccionDesdeLista(trans.idTransaccion)"
          >
            🗑️
          </button>
        </li>
      </ul>
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
      recordatorios: [],
      fechaSeleccionada: "",
      userId: localStorage.getItem("userId")
        ? parseInt(localStorage.getItem("userId"))
        : null,
      mesActual: new Date().getMonth(),
      anioActual: new Date().getFullYear(),
    };
  },
  computed: {
    transaccionesFiltradasPorMes() {
      return this.transacciones.filter((t) => {
        const partes = t.fecha.split("-");
        const fecha = new Date(`${partes[2]}-${partes[1]}-${partes[0]}`);
        return (
          fecha.getMonth() === this.mesActual &&
          fecha.getFullYear() === this.anioActual &&
          t.usuario?.idUsuario === this.userId
        );
      });
    },
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

        if (!Array.isArray(data)) {
          console.error("❌ Error: El backend no devuelve un array:", data);
          return;
        }

        this.transacciones = data.filter(
          (t) => t.usuario?.idUsuario === this.userId
        );
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
          console.error("❌ Error al eliminar la transacción");
          return;
        }

        this.transacciones = this.transacciones.filter(
          (t) => t.idTransaccion !== idTransaccion
        );
      } catch (error) {
        console.error("❌ Error al eliminar la transacción:", error);
      }
    },
    async eliminarTransaccionDesdeLista(idTransaccion) {
      const confirmar = confirm("¿Eliminar esta transacción?");
      if (!confirmar) return;

      try {
        const response = await fetch(
          `http://localhost:8080/transacciones/transaccion/${idTransaccion}`,
          { method: "DELETE" }
        );

        if (!response.ok) {
          console.error("❌ Error al eliminar en backend");
          return;
        }

        this.transacciones = this.transacciones.filter(
          (t) => t.idTransaccion !== idTransaccion
        );
        console.log("🗑️ Transacción eliminada");
      } catch (err) {
        console.error("❌ Error:", err);
      }
    },
    formatearFecha(fecha) {
      const partes = fecha.split("-");
      return `${partes[0]}/${partes[1]}/${partes[2]}`;
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

.lista-transacciones {
  max-width: 800px;
  margin: 40px auto;
  padding: 20px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}

.lista-transacciones ul {
  list-style: none;
  padding: 0;
}

.lista-transacciones li {
  margin: 10px 0;
  font-size: 1rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.btn-eliminar {
  background: none;
  border: none;
  color: #888;
  cursor: pointer;
  font-size: 1.1rem;
  margin-left: 10px;
  transition: color 0.3s ease;
}

.btn-eliminar:hover {
  color: red;
}

.gasto {
  color: #e74c3c;
}
.ingreso {
  color: #2ecc71;
}
</style>
