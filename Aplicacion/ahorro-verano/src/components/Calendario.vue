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
        <tr v-for="(row, rowIndex) in crearCalendario(diasDelMes, diasAntes)" :key="rowIndex">
          <td v-for="(dia, colIndex) in row" :key="colIndex" :class="['dia', { 'dia-vacio': dia === null }]"
            @click="seleccionarFecha(dia)" :style="dia === null ? { pointerEvents: 'none' } : {}">
            <span v-if="dia" class="numero-dia">{{ dia }}</span>
            <div class="puntos" v-if="dia">
              <!-- Transacciones -->
              <span v-for="trans in transaccionesDelDia(dia)" :key="trans.idTransaccion"
                :data-tooltip="trans && trans.cantidad && trans.tipo ? `${trans.tipo}: ${trans.cantidad}€` : 'Datos no disponibles'"
                :class="['punto', trans.categoria?.nombre.toLowerCase() || 'desconocido', 'tooltip']"
                @click.stop="eliminarTransaccion(trans)">
              </span>
            </div>
            <!-- Recordatorios -->
            <div class="puntos" v-if="dia">
              <span v-for="rec in recordatoriosDelDia(dia)" :key="rec.idRecordatorio || rec.fecha + rec.concepto"
                :data-tooltip="`Recordatorio: ${rec.concepto}, ${rec.cantidad}€`" class="punto recordatorio tooltip"
                style="background-color: yellow;">
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
        // Asegúrate de que recordatorios no sea null ni undefined
        if (newVal && Array.isArray(newVal)) {
          this.recordatoriosInternos = [...newVal];
        } else {
          console.warn("Los recordatorios no son un arreglo válido:", newVal);
        }
      },
      deep: true,
      immediate: true
    }
  },
  methods: {
    crearCalendario(diasDelMes, diasAntes) {
      const calendario = [];
      let semana = new Array(diasAntes).fill(null);
      diasDelMes.forEach(dia => {
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
      this.$forceUpdate();
    },
    transaccionesDelDia(dia) {
      return this.transacciones.filter(({ fecha, usuario }) => {
        const [d, m, y] = fecha.split("-");
        if (!d || !m || !y) return false;
        return +d === dia && +m - 1 === this.mes && +y === this.anio && usuario?.idUsuario == this.usuarioId;
      });
    },
    recordatoriosDelDia(dia) {
      return this.recordatoriosInternos.filter(({ fecha }) => {
        // Convierte la fecha ISO a objeto Date
        const fechaObj = new Date(fecha);
        // Compara día, mes y año
        const isValid = (
          fechaObj.getDate() === dia &&
          fechaObj.getMonth() === this.mes &&
          fechaObj.getFullYear() === this.anio
        );
        console.log(isValid, fechaObj, dia);  // Verifica los datos
        return isValid;
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

/* Colores según la categoría */
.ingreso {
  background-color: #27ae60;
}

.gasto {
  background-color: #c0392b;
}

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

.recordatorios {
  display: flex;
  justify-content: center;
  gap: 5px;
  margin-top: 5px;
}

.recordatorio-icon {
  font-size: 1.2rem;
  cursor: pointer;
}

.punto.recordatorio {
  background-color: rgb(247, 247, 68) !important; /* Color de fondo para los círculos de los recordatorios */
}

.tooltip {
  position: relative;
  display: inline-block;
}

.tooltip:hover::after {
  content: attr(data-tooltip);
  position: absolute;
  top: -30px; /* Puedes ajustar la posición del tooltip */
  left: 50%;
  transform: translateX(-50%);
  padding: 5px 10px;
  background-color: #0b0b0b; /* Fondo oscuro */
  color: rgb(255, 255, 255); /* Texto blanco */
  border-radius: 5px;
  font-size: 0.9rem;
  white-space: nowrap;
  z-index: 10;
  opacity: 1;
  transition: opacity 0.2s;
}

</style>
