<template>
  <div class="calendario-container">
    <h2>📅 Calendario de Gastos e Ingresos</h2>

    <div class="navegacion">
      <button class="btn-nav" @click="cambiarMes(-1)">&#10094; Anterior</button>
      <h3 class="mes-actual">{{ nombreMes }} {{ anio }}</h3>
      <button class="btn-nav" @click="cambiarMes(1)">Siguiente &#10095;</button>
    </div>

    <table class="calendario">
      <thead>
        <tr>
          <th v-for="diaSemana in diasSemana" :key="diaSemana">{{ diaSemana }}</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(row, rowIndex) in crearCalendario()" :key="rowIndex">
          <td v-for="(dia, colIndex) in row" :key="colIndex" :class="['dia', { 'dia-vacio': dia === null }]"
            @click="seleccionarFecha(dia)" :style="dia === null ? { pointerEvents: 'none' } : {}">
            <span v-if="dia" class="numero-dia">{{ dia }}</span>
            <div class="puntos" v-if="dia">
              <!-- Transacciones: usando conversión de fecha -->
              <span v-for="trans in transaccionesDelDia(dia)" :key="trans.idTransaccion"
                :data-tooltip="`${trans.tipo}: ${trans.cantidad}€`"
                :class="['punto', trans.categoria?.nombre.toLowerCase() || 'desconocido', 'tooltip']"
                @click.stop="eliminarTransaccion(trans)">
              </span>
            </div>
            <div class="puntos" v-if="dia">
              <!-- Recordatorios: se muestran como círculos amarillos -->
              <span v-for="rec in recordatoriosDelDia(dia)" :key="rec.idRecordatorio || rec.fecha + rec.concepto"
                :data-tooltip="`Recordatorio: ${rec.concepto}, ${rec.cantidad}€`" class="punto recordatorio tooltip">
              </span>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { eventBus } from "@/eventBus.js";

export default {
  name: "Calendario",
  props: {
    transacciones: {
      type: Array,
      default: () => []
    },
    recordatorios: {
      type: Array,
      default: () => []
    },
    usuarioId: [String, Number]
  },
  data() {
    const hoy = new Date();
    const mes = hoy.getMonth();
    const anio = hoy.getFullYear();
    const primerDiaDelMes = new Date(anio, mes, 1);
    const ultimoDiaDelMes = new Date(anio, mes + 1, 0);
    return {
      mes,
      anio,
      diasSemana: ["Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"],
      diasDelMes: Array.from({ length: ultimoDiaDelMes.getDate() }, (_, i) => i + 1),
      diasAntes: primerDiaDelMes.getDay() === 0 ? 6 : primerDiaDelMes.getDay() - 1,
      recordatoriosInternos: [...this.recordatorios] // Copia inicial
    };
  },
  computed: {
    nombreMes() {
      return new Date(this.anio, this.mes).toLocaleString("es-ES", { month: "long" });
    },
    usuarioIdNum() {
      return parseInt(this.usuarioId) || 0;
    }
  },
  mounted() {
    console.log("Calendario montado, esperando eventos...");
    eventBus.on("nuevo-recordatorio", this.agregarRecordatorio);
  },
  beforeUnmount() {
    eventBus.off("nuevo-recordatorio", this.agregarRecordatorio);
  },
  watch: {
    recordatorios: {
      handler(newVal) {
        if (newVal && Array.isArray(newVal)) {
          this.recordatoriosInternos = [...newVal];
        } else {
          console.warn("Los recordatorios no son un arreglo válido:", newVal);
        }
      },
      deep: true,
      immediate: true
    },
    transacciones(newVal) {
      console.log("📆 Transacciones en Calendario:", newVal);
      console.log("👤 UsuarioId en Calendario:", this.usuarioIdNum);
    }
  },
  methods: {
    // Convierte fecha de transacción de "DD-MM-YYYY" a "YYYY-MM-DD"
    convertirFechaTransaccion(fechaTransaccion) {
      const [dia, mes, anio] = fechaTransaccion.split("-");
      return `${anio}-${mes.padStart(2, "0")}-${dia.padStart(2, "0")}`;
    },
    crearCalendario() {
      const calendario = [];
      let semana = new Array(this.diasAntes).fill(null);
      this.diasDelMes.forEach(dia => {
        semana.push(dia);
        if (semana.length === 7) {
          calendario.push(semana);
          semana = [];
        }
      });
      if (semana.length) calendario.push(semana);
      return calendario;
    },
    cambiarMes(direccion) {
      this.mes += direccion;
      if (this.mes < 0) {
        this.mes = 11;
        this.anio--;
      } else if (this.mes > 11) {
        this.mes = 0;
        this.anio++;
      }
      const primerDiaDelMes = new Date(this.anio, this.mes, 1);
      const ultimoDiaDelMes = new Date(this.anio, this.mes + 1, 0);
      this.diasDelMes = Array.from({ length: ultimoDiaDelMes.getDate() }, (_, i) => i + 1);
      this.diasAntes = primerDiaDelMes.getDay() === 0 ? 6 : primerDiaDelMes.getDay() - 1;
      this.$forceUpdate();
    },
    transaccionesDelDia(dia) {
      return this.transacciones.filter(({ fecha, usuario }) => {
        const [year, month, day] = fecha.split("-").map(Number);
        return day === dia && month === (this.mes + 1) && year === this.anio;
      });
    },
    recordatoriosDelDia(dia) {
      return this.recordatoriosInternos.filter(({ fecha }) => {
        // Los recordatorios vienen en formato "YYYY-MM-DD"
        const [year, month, day] = fecha.split("-").map(Number);
        return day === dia && month - 1 === this.mes && year === this.anio;
      });
    },
    seleccionarFecha(dia) {
      if (dia !== null) {
        const fecha = `${this.anio}-${String(this.mes + 1).padStart(2, "0")}-${String(dia).padStart(2, "0")}`;
        this.$emit("fecha-seleccionada", fecha);
      }
    },
    eliminarTransaccion(trans) {
      if (confirm(`¿Eliminar la transacción ${trans.tipo} de ${trans.cantidad}€?`)) {
        this.$emit("eliminar-transaccion", trans.idTransaccion);
      }
    },
    agregarRecordatorio(recordatorio) {
      console.log("📌 Recibido en Calendario:", recordatorio);
      if (recordatorio && recordatorio.fecha) {
        this.recordatoriosInternos.push(recordatorio);
      } else {
        console.warn("⚠️ Recordatorio inválido recibido:", recordatorio);
      }
    }
  }
};
</script>

<style scoped>
body {
  overflow: hidden;
}

.calendario-container {
  max-width: 80%;
  width: 65%;
  background: white;
  padding: 15px;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  text-align: center;
  height: 85vh;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}

.calendario {
  width: 100%;
  border-collapse: collapse;
  border: 2px solid black;
  margin-bottom: 15px;
}

.calendario th,
.calendario td {
  padding: 15px;
  height: 35px;
  font-size: 1rem;
  border: 2px solid black;
  text-align: center;
}

.calendario td:hover {
  background-color: #85b1e7;
  transform: scale(1);
}

.calendario td.dia-vacio {
  pointer-events: none;
  border: none;
}

.puntos {
  display: flex;
  justify-content: center;
  gap: 5px;
  margin-top: 5px;
}

.punto {
  width: 16px;
  height: 16px;
  border-radius: 50%;
  transition: transform 0.2s ease-in-out;
  cursor: pointer;
}

.punto:hover {
  transform: scale(1.2);
}

/* Colores para transacciones */
.ingreso {
  background-color: #27ae60;
}

.gasto {
  background-color: #c0392b;
}

/* Navegación */
.navegacion {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 10px;
}

.btn-nav {
  background-color: #007bff;
  color: white;
  padding: 8px 12px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 0.9rem;
}

.btn-nav:hover {
  background-color: #0056b3;
}

/* Recordatorios: Círculo amarillo */
.punto.recordatorio {
  background-color: rgb(247, 247, 68) !important;
}

/* Tooltip */
.tooltip {
  position: relative;
  display: inline-block;
}

.tooltip:hover::after {
  content: attr(data-tooltip);
  position: absolute;
  top: -30px;
  left: 50%;
  transform: translateX(-50%);
  padding: 5px 10px;
  background-color: #0b0b0b;
  color: rgb(255, 255, 255);
  border-radius: 5px;
  font-size: 0.9rem;
  white-space: nowrap;
  z-index: 10;
  opacity: 1;
  transition: opacity 0.2s;
}
</style>
