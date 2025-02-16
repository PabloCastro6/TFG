<template>
  <div class="contenedor-principal">
    <!-- Sección izquierda: Calendario -->
    <div class="calendario-container">
      <h2>Calendario de Gastos e Ingresos</h2>

      <!-- Navegación del mes -->
      <div class="navegacion">
        <button @click="cambiarMes(-1)">&#10094; Anterior</button>
        <h3>{{ nombreMes }} {{ anio }}</h3>
        <button @click="cambiarMes(1)">Siguiente &#10095;</button>
      </div>

      <!-- Tabla del calendario -->
      <table class="calendario">
        <thead>
          <tr>
            <th v-for="(dia, index) in diasSemana" :key="index">{{ dia }}</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(semana, index) in semanas" :key="index">
            <td v-for="(dia, i) in semana" :key="i" class="dia">
              <span v-if="dia">{{ dia }}</span>
              <div class="puntos">
                <span
                  v-if="obtenerTransacciones(dia, 'ingreso').length > 0"
                  class="punto ingreso"
                  @mouseover="mostrarInformacion(dia, 'ingreso', $event)"
                  @mouseleave="ocultarInformacion"
                ></span>
                <span
                  v-if="obtenerTransacciones(dia, 'gasto').length > 0"
                  class="punto gasto"
                  @mouseover="mostrarInformacion(dia, 'gasto', $event)"
                  @mouseleave="ocultarInformacion"
                ></span>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Sección derecha: Formulario -->
    <div class="formulario-container">
      <h2>Registrar Transacción</h2>

      <label for="dia">Día del mes:</label>
      <input
        type="number"
        v-model="diaSeleccionado"
        min="1"
        :max="diasEnMes"
        placeholder="Ej: 15"
      />

      <label for="tipo">Tipo:</label>
      <select v-model="tipoTransaccion">
        <option value="ingreso">Ingreso</option>
        <option value="gasto">Gasto</option>
      </select>

      <label for="monto">Monto (€):</label>
      <input type="number" v-model="monto" placeholder="Introduce el monto" />

      <button @click="registrarTransaccion">Guardar</button>
    </div>

    <!-- Tooltip dinámico -->
    <div
      v-if="mostrarTooltipFlag"
      class="tooltip"
      :style="{ top: tooltipY + 'px', left: tooltipX + 'px' }"
    >
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
  data() {
    return {
      mes: new Date().getMonth(),
      anio: new Date().getFullYear(),
      diasSemana: ["Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"],
      transacciones: {},
      mostrarTooltipFlag: false,
      tooltipTexto: [],
      tooltipTipo: "",
      tooltipX: 0,
      tooltipY: 0,
      tipoTransaccion: "ingreso",
      monto: null,
      diaSeleccionado: null,
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
    obtenerTransacciones(dia, tipo) {
      let clave = `${this.mes}-${this.anio}-${dia}`;
      return this.transacciones[clave]?.[tipo] || [];
    },
    mostrarInformacion(dia, tipo, event) {
      let clave = `${this.mes}-${this.anio}-${dia}`;
      this.tooltipTexto = this.transacciones[clave]?.[tipo] || [];
      this.tooltipTipo = tipo;
      this.tooltipX = event.pageX + 10;
      this.tooltipY = event.pageY + 10;
      this.mostrarTooltipFlag = true;
    },
    ocultarInformacion() {
      this.mostrarTooltipFlag = false;
    },
    registrarTransaccion() {
      if (
        !this.diaSeleccionado ||
        this.diaSeleccionado < 1 ||
        this.diaSeleccionado > this.diasEnMes
      ) {
        alert("Por favor, introduce un día válido del mes.");
        return;
      }
      if (!this.monto || this.monto <= 0) {
        alert("Introduce un monto válido");
        return;
      }

      let clave = `${this.mes}-${this.anio}-${this.diaSeleccionado}`;

      if (!this.transacciones[clave]) {
        this.transacciones[clave] = { ingreso: [], gasto: [] };
      }

      this.transacciones[clave][this.tipoTransaccion].push(this.monto);
    },
  },
};
</script>

<style>
/* ====== ESTILOS GENERALES ====== */
* {
  box-sizing: border-box;
  font-family: "Poppins", sans-serif;
  margin: 0;
  padding: 0;
}

body {
  background-color: #f4f6f9;
  color: #2c3e50;
}

/* ====== CONTENEDOR PRINCIPAL ====== */
.contenedor-principal {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 30px;
  max-width: 1200px;
  margin: 50px auto;
  padding: 20px;
}

/* ====== CALENDARIO ====== */
.calendario-container {
  flex: 2;
  background: white;
  padding: 25px;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  text-align: center;
  transition: transform 0.3s ease-in-out;
}

.calendario-container:hover {
  transform: translateY(-3px);
}

/* ====== TÍTULOS ====== */
h2 {
  color: #2c3e50;
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 15px;
}

/* ====== NAVEGACIÓN DEL MES ====== */
.navegacion {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.navegacion button {
  background-color: #2c3e50;
  color: white;
  border: none;
  padding: 12px 18px;
  cursor: pointer;
  font-weight: bold;
  border-radius: 8px;
  transition: background 0.3s ease-in-out, transform 0.2s ease-in-out;
}

.navegacion button:hover {
  background-color: #1a252f;
  transform: scale(1.05);
}

/* ====== TABLA DEL CALENDARIO ====== */
.calendario {
  width: 100%;
  border-collapse: collapse;
}

.calendario th,
.calendario td {
  border: 1px solid #e0e0e0;
  padding: 15px;
  text-align: center;
  font-weight: bold;
  position: relative;
  transition: background 0.3s ease-in-out;
}

.calendario td {
  height: 65px;
  cursor: pointer;
}

.calendario td:hover {
  background-color: #eef2f7;
  transform: scale(1.02);
}

/* ====== PUNTOS DE INGRESOS Y GASTOS ====== */
.puntos {
  display: flex;
  justify-content: center;
  gap: 5px;
  margin-top: 5px;
}

.punto {
  width: 14px;
  height: 14px;
  border-radius: 50%;
  position: relative;
  transition: transform 0.2s ease-in-out;
}

.punto:hover {
  transform: scale(1.3);
}

.ingreso {
  background-color: #27ae60;
}

.gasto {
  background-color: #c0392b;
}

/* ====== FORMULARIO ====== */
.formulario-container {
  flex: 1;
  background: white;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  transition: transform 0.3s ease-in-out;
}

.formulario-container:hover {
  transform: translateY(-3px);
}

label {
  font-size: 16px;
  font-weight: bold;
  margin-top: 15px;
}

input,
select {
  width: 100%;
  padding: 12px;
  margin-top: 5px;
  border: 2px solid #ccc;
  border-radius: 8px;
  font-size: 16px;
  transition: border 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
}

input:focus,
select:focus {
  border-color: #2c3e50;
  outline: none;
  box-shadow: 0 0 8px rgba(44, 62, 80, 0.2);
}

button {
  background: #2c3e50;
  color: white;
  font-size: 16px;
  font-weight: bold;
  padding: 14px;
  margin-top: 20px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background 0.3s ease-in-out, transform 0.2s ease-in-out;
}

button:hover {
  background: #1a252f;
  transform: scale(1.05);
}

/* ====== TOOLTIP (HOVER SOBRE PUNTOS) ====== */
.tooltip {
  position: absolute;
  background-color: rgba(0, 0, 0, 0.8);
  color: white;
  padding: 10px 15px;
  border-radius: 8px;
  font-size: 14px;
  white-space: nowrap;
  pointer-events: none;
  transition: opacity 0.2s ease-in-out;
  z-index: 1000;
}

/* ====== RESPONSIVE DESIGN ====== */
@media (max-width: 1024px) {
  .contenedor-principal {
    flex-direction: column;
    align-items: center;
  }

  .calendario-container,
  .formulario-container {
    width: 90%;
  }
}

@media (max-width: 600px) {
  .calendario th,
  .calendario td {
    padding: 12px;
  }

  .punto {
    width: 12px;
    height: 12px;
  }

  button {
    font-size: 14px;
    padding: 12px;
  }
}
</style>
