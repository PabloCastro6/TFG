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
    <table class="calendario">
      <thead>
        <tr>
          <th v-for="diaSemana in diasSemana" :key="diaSemana">{{ diaSemana }}</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(row, rowIndex) in crearCalendario(diasDelMes, diasAntes)" :key="rowIndex">
          <td
            v-for="(dia, colIndex) in row"
            :key="colIndex"
            :class="['dia', { 'dia-vacio': dia === null }]"
            @click="seleccionarFecha(dia)"
            :style="dia === null ? { pointerEvents: 'none' } : {}"
          >
            <span v-if="dia" class="numero-dia">{{ dia }}</span>
            <!-- Puntos de transacciones con tooltip y opción para eliminar -->
            <div class="puntos" v-if="dia">
              <span
                v-for="trans in transaccionesDelDia(dia)"
                :key="trans.idTransaccion"
                :data-tooltip="`${trans.tipo}: ${trans.cantidad}€`"
                :class="['punto', trans.categoria.nombre.toLowerCase(), 'tooltip']"
                @click.stop="eliminarTransaccion(trans)"
              ></span>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: "Calendario",
  props: {
    transacciones: {
      type: Array,
      required: true
    },
    usuarioId: {
      type: [String, Number],
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
    const mes = hoy.getMonth();
    const anio = hoy.getFullYear();
    const primerDiaDelMes = new Date(anio, mes, 1);
    const ultimoDiaDelMes = new Date(anio, mes + 1, 0);
    const diasDelMes = Array.from({ length: ultimoDiaDelMes.getDate() }, (_, i) => i + 1);
    const primerDiaDeLaSemana = primerDiaDelMes.getDay();
    const diasAntes = primerDiaDeLaSemana === 0 ? 6 : primerDiaDeLaSemana - 1;
    return {
      mes,
      anio,
      diasSemana: ["Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"],
      diasDelMes,
      diasAntes
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
  methods: {
    crearCalendario(diasDelMes, diasAntes) {
      const calendario = [];
      let semana = [];
      for (let i = 0; i < diasAntes; i++) {
        semana.push(null);
      }
      diasDelMes.forEach((dia) => {
        semana.push(dia);
        if (semana.length === 7) {
          calendario.push(semana);
          semana = [];
        }
      });
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
      const primerDiaDelMes = new Date(this.anio, this.mes, 1);
      const ultimoDiaDelMes = new Date(this.anio, this.mes + 1, 0);
      this.diasDelMes = Array.from({ length: ultimoDiaDelMes.getDate() }, (_, i) => i + 1);
      const primerDiaDeLaSemana = primerDiaDelMes.getDay();
      this.diasAntes = primerDiaDeLaSemana === 0 ? 6 : primerDiaDeLaSemana - 1;
    },
    transaccionesDelDia(dia) {
      return this.transacciones.filter(transaccion => {
        const partesFecha = transaccion.fecha.split("-");
        const fechaFormateada = new Date(`${partesFecha[2]}-${partesFecha[1]}-${partesFecha[0]}`);
        return (
          fechaFormateada.getDate() === dia &&
          fechaFormateada.getMonth() === this.mes &&
          fechaFormateada.getFullYear() === this.anio &&
          transaccion.usuario &&
          transaccion.usuario.idUsuario === this.usuarioIdNum
        );
      });
    },
    seleccionarFecha(dia) {
      if (dia === null) return;
      const fechaSeleccionada = new Date(this.anio, this.mes, dia);
      const formatoFecha = `${fechaSeleccionada.getFullYear()}-${String(fechaSeleccionada.getMonth() + 1).padStart(2, '0')}-${String(fechaSeleccionada.getDate()).padStart(2, '0')}`;
      this.$emit("fecha-seleccionada", formatoFecha);
    },
    eliminarTransaccion(trans) {
      if (confirm(`¿Estás seguro de eliminar la transacción ${trans.tipo} de ${trans.cantidad}€?`)) {
        // Emitir un evento para que el componente padre elimine la transacción
        this.$emit("eliminar-transaccion", trans.idTransaccion);
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
  height: 78vh;
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

/* Estilo general del tooltip */
.tooltip {
  position: relative;
  cursor: pointer;
}

.tooltip::after {
  content: attr(data-tooltip);
  position: absolute;
  bottom: 120%;
  left: 50%;
  transform: translateX(-50%);
  background-color: rgba(0, 0, 0, 0.8);
  color: white;
  padding: 8px;
  border-radius: 5px;
  font-size: 14px;
  white-space: nowrap;
  visibility: hidden;
  opacity: 0;
  transition: opacity 0.3s ease-in-out, transform 0.2s ease-in-out;
}

.tooltip:hover::after {
  visibility: visible;
  opacity: 1;
  transform: translateX(-50%) translateY(-5px);
}

/* Estilos para ingresos (verde) */
.ingreso.tooltip::after {
  background-color: #2ecc71;
}

/* Estilos para gastos (rojo) */
.gasto.tooltip::after {
  background-color: #e74c3c;
}
</style>
