<template>
  <div class="calendario-container">
    <h2>📅 Calendario de Gastos e Ingresos</h2>

    <!-- 🔄 Navegación del mes con botones estilizados -->
    <div class="navegacion">
      <button class="btn-nav" @click="cambiarMes(-1)">&#10094; Anterior</button>
      <h3 class="mes-actual">{{ nombreMes }} {{ anio }}</h3>
      <button class="btn-nav" @click="cambiarMes(1)">Siguiente &#10095;</button>
    </div>

    <!-- 📆 Tabla del calendario -->
    <table class="calendario">
      <thead>
        <tr>
          <th v-for="(dia, index) in diasSemana" :key="index">{{ dia }}</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(semana, index) in semanas" :key="index">
          <td v-for="(dia, i) in semana" :key="i" :class="['dia', { 'dia-vacio': !dia }]" @click="seleccionarFecha(dia)"
            :style="!dia ? { pointerEvents: 'none' } : {}">
            <span v-if="dia" class="numero-dia">{{ dia }}</span>
            <div class="puntos">
              <span v-if="hayTransaccion(dia, 'ingreso')" class="punto ingreso"
                @mouseover="mostrarInformacion(dia, 'ingreso', $event)" @mouseleave="ocultarInformacion"></span>
              <span v-if="hayTransaccion(dia, 'gasto')" class="punto gasto"
                @mouseover="mostrarInformacion(dia, 'gasto', $event)" @mouseleave="ocultarInformacion"></span>
            </div>
          </td>
        </tr>
      </tbody>
    </table>

    <div v-if="mostrarTooltipFlag" class="tooltip show"
      :class="{ 'ingreso-tooltip': tooltipTipo === 'ingreso', 'gasto-tooltip': tooltipTipo === 'gasto' }"
      :style="{ top: tooltipY + 'px', left: tooltipX + 'px' }">
      <div class="tooltip-content">
        <strong>{{ tooltipTipo === 'ingreso' ? 'Ingresos' : 'Gastos' }}:</strong>
        <ul>
          <li v-for="(valor, idx) in tooltipTexto" :key="idx">{{ valor }}€</li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "CalendarioGastos",
  props: {
    transacciones: Array, // Recibimos las transacciones de ConfiguracionAhorro.vue
  },
  data() {
    return {
      mes: new Date().getMonth(),
      anio: new Date().getFullYear(),
      diasSemana: ["Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"],
      mostrarTooltipFlag: false,
      tooltipTexto: [],
      tooltipTipo: "",
      tooltipX: 0,
      tooltipY: 0,
    };
  },
  computed: {
    nombreMes() {
      return new Date(this.anio, this.mes).toLocaleString("es-ES", {
        month: "long",
      });
    },
    diasEnMes() {
      return new Date(this.anio, this.mes + 1, 0).getDate();
    },
    semanas() {
      let primerDiaMes = new Date(this.anio, this.mes, 1).getDay();
      primerDiaMes = primerDiaMes === 0 ? 6 : primerDiaMes - 1;
      let diasMes = this.diasEnMes;
      let dias = Array(primerDiaMes)
        .fill(null)
        .concat([...Array(diasMes).keys()].map((i) => i + 1));

      let semanas = [];
      while (dias.length) {
        semanas.push(dias.splice(0, 7));
      }
      return semanas;
    },
  },
  methods: {
    cambiarMes(direccion) {
      this.mes += direccion;
      if (this.mes < 0) {
        this.mes = 11;
        this.anio--;
      } else if (this.mes > 11) {
        this.mes = 0;
        this.anio++;
      }
    },
    hayTransaccion(dia, tipo) {
      return this.transacciones.some(
        (transaccion) => {
          const fecha = new Date(transaccion.fecha);
          return (
            fecha.getDate() === dia &&
            fecha.getMonth() === this.mes && // Asegura que el mes coincida
            fecha.getFullYear() === this.anio && // Asegura que el año coincida
            transaccion.tipo === tipo
          );
        }
      );

    },
    mostrarInformacion(dia, tipo, event) {
      const transaccionesDelDia = this.transacciones.filter(
        (transaccion) =>
          new Date(transaccion.fecha).getDate() === dia &&
          transaccion.tipo === tipo
      );

      if (transaccionesDelDia.length > 0) {
        this.tooltipTexto = transaccionesDelDia.map(
          (transaccion) => `${transaccion.cantidad}`
        );
        this.tooltipTipo = tipo;
        this.tooltipX = event.pageX + 10;
        this.tooltipY = event.pageY + 10;
        this.mostrarTooltipFlag = true;
      }
    },
    ocultarInformacion() {
      this.mostrarTooltipFlag = false;
    },
    seleccionarFecha(dia) {
      if (!dia) return;

      // Crea la fecha sin cambiar la zona horaria
      const fechaSeleccionada = new Date(this.anio, this.mes, dia);

      // Convertir a formato sin problemas de zona horaria
      const formatoFecha = fechaSeleccionada.getFullYear() + '-' +
        String(fechaSeleccionada.getMonth() + 1).padStart(2, '0') + '-' +
        String(fechaSeleccionada.getDate()).padStart(2, '0');

      this.$emit("fecha-seleccionada", formatoFecha);
    },
  },
};
</script>

<style scoped>
/* 📌 Estilos generales */
.calendario-container {
  flex: 7;
  max-width: 70%;
  background: white;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  text-align: center;
  height: 83vh;
  /* Ajusta la altura para que ocupe el 90% de la pantalla */
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  overflow: hidden;
  margin: auto;
}

/* 📆 Tabla del calendario */
.calendario {
  width: 100%;
  border-collapse: collapse;
  border: 1px solid black;
  flex-grow: 1;
}

.calendario th,
.calendario td {
  padding: 10px;
  height: 50px;
  /* Reduce altura de las celdas */
  font-size: 0.9rem;
  border: 1px solid black;
}

.calendario td:hover {
  background-color: #85b1e7;
  transform: scale(1);
}

/* 📆 Tabla del calendario */
.calendario td.dia-vacio {
  pointer-events: none;
  border: none;
  /* Desactiva la selección de los días vacíos */

}

/* 📌 Puntos */
.puntos {
  display: flex;
  justify-content: center;
  gap: 5px;
}

.punto {
  width: 14px;
  height: 14px;
  border-radius: 50%;
  transition: transform 0.2s ease-in-out;
}

.punto:hover {
  transform: scale(1.2);
}

.ingreso {
  background-color: #27ae60;
}

.gasto {
  background-color: #c0392b;
}


/* 🔄 Navegación del mes */
.navegacion {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 10px;
}

.tooltip {
  position: absolute;
  background-color: #27ae60;
  color: #fff;
  border-radius: 8px;
  padding: 10px;
  max-width: 250px;
  font-size: 0.9rem;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
  opacity: 0;
  transform: translateY(-10px);
  transition: opacity 0.3s ease, transform 0.3s ease;
  pointer-events: none;
}

.tooltip.show {
  opacity: 1;
  transform: translateY(0);
}

/* Tooltip de ingreso (verde) */
.ingreso-tooltip {
  background-color: #27ae60;
}

/* Tooltip de gasto (rojo) */
.gasto-tooltip {
  background-color: #c0392b; 
}

.tooltip-content {
  max-height: 150px;
  overflow-y: auto;
}


/* 📌 Botones */
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
</style>
