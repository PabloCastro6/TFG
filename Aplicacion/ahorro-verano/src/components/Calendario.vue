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
          <td v-for="(dia, i) in semana" :key="i" class="dia">
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

    <!-- ℹ️ Tooltip dinámico -->
    <div v-if="mostrarTooltipFlag" class="tooltip" :style="{ top: tooltipY + 'px', left: tooltipX + 'px' }">
      <strong>{{ tooltipTipo === "ingreso" ? "Ingresos" : "Gastos" }}:</strong>
      <ul>
        <li v-for="(valor, idx) in tooltipTexto" :key="idx">{{ valor }}€</li>
      </ul>
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
      diasSemana: ["Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"],
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
  },
};
</script>

<style scoped>
/* 📌 Estilos generales */
.calendario-container {
  flex: 2;
  background: white;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  text-align: center;
  max-width: 800px;
  margin: auto;
}

/* 📆 Tabla del calendario */
.calendario {
  width: 100%;
  border-collapse: collapse;
}

.calendario th {
  font-size: 1.1rem;
  padding: 10px;
}

.calendario td {
  border: 2px solid #e0e0e0;
  padding: 18px;
  text-align: center;
  font-size: 1.2rem;
  font-weight: bold;
  height: 80px;
  transition: background 0.3s ease-in-out, transform 0.2s ease-in-out;
}

.calendario td:hover {
  background-color: #eef2f7;
  transform: scale(1.02);
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

/* ℹ️ Tooltip */
.tooltip {
  position: absolute;
  background-color: rgba(0, 0, 0, 0.9);
  color: white;
  padding: 12px;
  border-radius: 8px;
  font-size: 14px;
  white-space: nowrap;
  z-index: 1000;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
}
</style>
