<template>
  <div class="calendario-container">
    <h2>📅 Calendario de Gastos e Ingresos</h2>

    <!-- Navegación del mes -->
    <div class="navegacion">
      <button class="btn-nav" @click="cambiarMes(-1)">&#10094; Anterior</button>
      <h3 class="mes-actual">{{ nombreMes }} {{ anio }}</h3>
      <button class="btn-nav" @click="cambiarMes(1)">Siguiente &#10095;</button>
    </div>

    <!-- Tabla del calendario -->
    <table>
      <thead>
        <tr>
          <th v-for="diaSemana in diasSemana" :key="diaSemana">{{ diaSemana }}</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(row, rowIndex) in crearCalendario(diasDelMes, diasAntes)" :key="rowIndex">
          <td v-for="(dia, colIndex) in row" :key="colIndex" :class="['dia', { 'dia-vacio': dia === null }]"
            @click="seleccionarFecha(dia)" @mouseover="mostrarInformacion(dia, $event)" @mouseout="ocultarInformacion"
            :style="dia === null ? { pointerEvents: 'none' } : {}">
            <span v-if="dia" class="numero-dia">{{ dia }}</span>
            <div class="puntos">
              <span v-if="hayTransaccion(dia, 'ingreso')" class="punto ingreso"></span>
              <span v-if="hayTransaccion(dia, 'gasto')" class="punto gasto"></span>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
    <!-- Tooltip para mostrar información de las transacciones -->
    <div v-if="mostrarTooltipFlag" class="tooltip show"
      :class="{ 'ingreso-tooltip': tooltipTipo === 'ingreso', 'gasto-tooltip': tooltipTipo === 'gasto' }"
      :style="{ top: tooltipY + 'px', left: tooltipX + 'px' }">
      <div class="tooltip-content">
        <strong>{{ tooltipTipo === 'ingreso' ? 'Ingresos' : 'Gastos' }}:</strong>
        <ul>
          <li v-for="(valor, idx) in tooltipTexto" :key="idx">{{ valor }}</li>
        </ul>
      </div>
    </div>
    <div v-for="transaccion in transacciones" :key="transaccion.idTransaccion">
      <p>{{ transaccion.fecha }}: {{ transaccion.cantidad }} </p>
    </div>
  </div>
</template>

<script>
export default {
  name: "Calendario",
  props: {
    transacciones: {
      type: Array,
      required: true
    }
  },
  watch: {
    transacciones(newTransacciones) {
      console.log("📋 Transacciones recibidas en Calendario.vue:", JSON.stringify(newTransacciones, null, 2));
    }
  },
  data() {
    const hoy = new Date();
    const mes = hoy.getMonth(); // Mes actual
    const anio = hoy.getFullYear(); // Año actual

    // Obtener el primer día del mes
    const primerDiaDelMes = new Date(anio, mes, 1);

    // Obtener el número de días en el mes
    const ultimoDiaDelMes = new Date(anio, mes + 1, 0);
    const diasDelMes = Array.from({ length: ultimoDiaDelMes.getDate() }, (_, i) => i + 1);

    // Calcular el primer día de la semana para el mes
    const primerDiaDeLaSemana = primerDiaDelMes.getDay(); // 0: Domingo, 1: Lunes, etc.
    const diasAntes = primerDiaDeLaSemana === 0 ? 6 : primerDiaDeLaSemana - 1; // Ajustamos para que la semana comience en lunes

    return {
      mes: mes,
      anio: anio,
      diasSemana: ["Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"],
      diasDelMes: diasDelMes,
      diasAntes: diasAntes, // Número de días en blanco al inicio del mes
      mostrarTooltipFlag: false,
      tooltipTexto: [],
      tooltipTipo: "",
      tooltipX: 0,
      tooltipY: 0,
    };
  },

  mounted() {
    console.log("📡 Transacciones antes de pasar a Calendario:", this.transacciones);
  },
  computed: {
    nombreMes() {
      return new Date(this.anio, this.mes).toLocaleString("es-ES", { month: "long" });
    },
    diasEnMes() {
      return new Date(this.anio, this.mes + 1, 0).getDate();
    },
    semanas() {
      let primerDiaMes = new Date(this.anio, this.mes, 1).getDay();
      // Si getDay() retorna 0 (domingo), lo convertimos a 6 para que la semana inicie en lunes.
      primerDiaMes = primerDiaMes === 0 ? 6 : primerDiaMes - 1;
      let diasMes = this.diasEnMes;
      let dias = Array(primerDiaMes).fill(null).concat([...Array(diasMes).keys()].map(i => i + 1));
      let semanas = [];
      while (dias.length) {
        semanas.push(dias.splice(0, 7));
      }
      return semanas;
    },
    // Convierte usuarioId a número para comparaciones
    usuarioIdNum() {
      return parseInt(this.usuarioId) || 0; // Si no hay usuarioId, usar 0 para evitar errores
    }

  },
  methods: {

    crearCalendario(diasDelMes, diasAntes) {
      const calendario = [];
      let semana = [];

      // Crear los primeros días vacíos para alinear el calendario
      for (let i = 0; i < diasAntes; i++) {
        semana.push(null); // Los primeros días antes del inicio del mes
      }

      // Agregar los días del mes
      diasDelMes.forEach((dia) => {
        semana.push(dia);
        if (semana.length === 7) {
          calendario.push(semana);
          semana = [];
        }
      });

      // Si quedan días en la semana incompleta, añadirla también
      if (semana.length > 0) {
        calendario.push(semana);
      }

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
    },
    hayTransaccion(dia, categoriaFiltro) {
      console.log(`🔍 Verificando transacciones para el día: ${dia}, Mes: ${this.mes + 1}, Año: ${this.anio}`);

      return this.transacciones.some(transaccion => {
        const partesFecha = transaccion.fecha.split("-");
        const fechaFormateada = new Date(`${partesFecha[2]}-${partesFecha[1]}-${partesFecha[0]}`);

        console.log(`📅 Comparando con transacción: ${transaccion.fecha}, Usuario: ${transaccion.usuario.idUsuario}`);

        return (
          fechaFormateada.getDate() === dia &&
          fechaFormateada.getMonth() === this.mes &&
          fechaFormateada.getFullYear() === this.anio &&
          transaccion.categoria &&
          transaccion.categoria.nombre === categoriaFiltro &&
          transaccion.usuario &&
          transaccion.usuario.idUsuario === this.usuarioIdNum
        );
      });
    },

    mostrarInformacion(dia, event) {
      // Filtrar transacciones de ingreso
      const transaccionesIngreso = this.transacciones.filter(transaccion => {
        const partesFecha = transaccion.fecha.split("-");
        const fecha = new Date(partesFecha[2], partesFecha[1] - 1, partesFecha[0]); // Formato correcto: año, mes (0-indexed), día

        return (
          fecha.getDate() === dia &&
          fecha.getMonth() === this.mes &&
          fecha.getFullYear() === this.anio &&
          transaccion.categoria &&
          transaccion.categoria.nombre === 'ingreso' &&
          transaccion.usuario &&
          transaccion.usuario.idUsuario === this.usuarioIdNum
        );
      });

      // Filtrar transacciones de gasto
      const transaccionesGasto = this.transacciones.filter(transaccion => {
        const partesFecha = transaccion.fecha.split("-");
        const fecha = new Date(partesFecha[2], partesFecha[1] - 1, partesFecha[0]);

        return (
          fecha.getDate() === dia &&
          fecha.getMonth() === this.mes &&
          fecha.getFullYear() === this.anio &&
          transaccion.categoria &&
          transaccion.categoria.nombre === 'gasto' &&
          transaccion.usuario &&
          transaccion.usuario.idUsuario === this.usuarioIdNum
        );
      });

      // Mostrar las transacciones en el tooltip si existen
      if (transaccionesIngreso.length > 0 || transaccionesGasto.length > 0) {
        this.tooltipTexto = [
          ...transaccionesIngreso.map(transaccion => `${transaccion.tipo}: ${transaccion.cantidad}€`),
          ...transaccionesGasto.map(transaccion => `${transaccion.tipo}: ${transaccion.cantidad}€`)
        ];

        this.tooltipTipo = transaccionesIngreso.length > 0 ? 'ingreso' : 'gasto';
        this.tooltipX = event.pageX + 10;
        this.tooltipY = event.pageY + 10;
        this.mostrarTooltipFlag = true;
      }
    },
    ocultarInformacion() {
      this.mostrarTooltipFlag = false;
    },
    seleccionarFecha(dia) {
      console.log("Día seleccionado:", dia);
      if (dia === null) return; // Si es un día vacío, no hacer nada
      const fechaSeleccionada = new Date(this.anio, this.mes, dia);
      const formatoFecha = `${fechaSeleccionada.getFullYear()}-${String(fechaSeleccionada.getMonth() + 1).padStart(2, '0')}-${String(fechaSeleccionada.getDate()).padStart(2, '0')}`;
      this.$emit("fecha-seleccionada", formatoFecha); // Emitir la fecha seleccionada
    },


  }
};
</script>

<style scoped>
.calendario-container {
  flex: 7;
  max-width: 70%;
  background: white;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  text-align: center;
  height: 83vh;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  overflow: hidden;
  margin: auto;
}

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
  font-size: 0.9rem;
  border: 1px solid black;
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

.ingreso-tooltip {
  background-color: #27ae60;
}

.gasto-tooltip {
  background-color: #c0392b;
}

.tooltip-content {
  max-height: 150px;
  overflow-y: auto;
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
</style>
