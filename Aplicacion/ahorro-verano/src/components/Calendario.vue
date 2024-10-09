<template>
  <div class="calendario">
    <div class="navegacion">
      <button @click="mesAnterior">Mes Anterior</button>
      <h2>{{ nombreMes }} {{ fechaActual.getFullYear() }}</h2>
      <button @click="mesSiguiente">Mes Siguiente</button>
    </div>
    <table>
      <thead>
        <tr>
          <th>Lunes</th>
          <th>Martes</th>
          <th>Miércoles</th>
          <th>Jueves</th>
          <th>Viernes</th>
          <th>Sábado</th>
          <th>Domingo</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(semana, index) in semanas" :key="index">
          <td
            v-for="(dia, diaIndex) in semana"
            :key="diaIndex"
            @click="abrirVentana(dia)"
            class="dia"
          >
            {{ dia ? dia.getDate() : "" }}
            <div v-if="dia" class="puntos">
              <span
                v-for="(transaccion, tIndex) in obtenerTransacciones(dia)"
                :key="tIndex"
                class="punto"
                :class="{
                  ingreso: transaccion.tipo === 'ingreso',
                  gasto: transaccion.tipo === 'gasto',
                }"
              ></span>
            </div>
          </td>
        </tr>
      </tbody>
    </table>

    <div v-if="mostrarModal" class="modal">
      <div class="modal-contenido">
        <h3>Añadir Gasto o Ingreso</h3>
        <p>
          Día: {{ diaSeleccionado ? diaSeleccionado.getDate() : "" }}
          {{ nombreMes }} {{ fechaActual.getFullYear() }}
        </p>
        <input v-model="cantidad" type="number" placeholder="Cantidad" />
        <select v-model="categoria">
          <option disabled value="">Selecciona una categoría</option>
          <optgroup label="Gastos">
            <option
              v-for="categoria in categoriasGastos"
              :key="categoria"
              :value="categoria"
            >
              {{ categoria }}
            </option>
          </optgroup>
          <optgroup label="Ingresos">
            <option
              v-for="categoria in categoriasIngresos"
              :key="categoria"
              :value="categoria"
            >
              {{ categoria }}
            </option>
          </optgroup>
        </select>
        <div class="botones">
          <button @click="agregarTransaccion('gasto')">Añadir Gasto</button>
          <button @click="agregarTransaccion('ingreso')">Añadir Ingreso</button>
        </div>
        <button @click="cerrarVentana">Cancelar</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Calendario",
  data() {
    return {
      fechaActual: new Date(),
      mostrarModal: false,
      diaSeleccionado: null,
      cantidad: null,
      categoria: "",
      transacciones: [],
      categoriasGastos: [
        "Coche",
        "Ropa",
        "Entretenimiento",
        "Comida",
        "Gasolina",
        "Regalos",
        "Salud",
        "Vacaciones",
        "Deportes",
      ],
      categoriasIngresos: ["Trabajo", "Árbitros", "Camisetas"],
    };
  },
  computed: {
    nombreMes() {
      const meses = [
        "Enero",
        "Febrero",
        "Marzo",
        "Abril",
        "Mayo",
        "Junio",
        "Julio",
        "Agosto",
        "Septiembre",
        "Octubre",
        "Noviembre",
        "Diciembre",
      ];
      return meses[this.fechaActual.getMonth()];
    },
    semanas() {
      const primerDiaMes = new Date(
        this.fechaActual.getFullYear(),
        this.fechaActual.getMonth(),
        1
      );
      const primerDiaSemana = primerDiaMes.getDay() || 7;
      const diasEnMes = new Date(
        this.fechaActual.getFullYear(),
        this.fechaActual.getMonth() + 1,
        0 /*Ultimo dia del mes*/
      ).getDate();
      const semanas = [[]]; /*matriz (array) de matrices (arrays)*/
      let diaActual = 1;

      for (let i = 1; i < primerDiaSemana; i++) {
        semanas[0].push(null);
      }
      while (diaActual <= diasEnMes) {
        const semanaActual = semanas[semanas.length - 1];
        if (semanaActual.length < 7) {
          semanaActual.push(
            new Date(
              this.fechaActual.getFullYear(),
              this.fechaActual.getMonth(),
              diaActual
            )
          );
          diaActual++;5847
          79
        } else {
          semanas.push([]);
        }
      }

      while (semanas[semanas.length - 1].length < 7) {
        semanas[semanas.length - 1].push(null);
      }

      return semanas;
    },
  },
  methods: {
    mesAnterior() {
      this.fechaActual.setMonth(this.fechaActual.getMonth() - 1);
      this.fechaActual = new Date(this.fechaActual);
    },
    mesSiguiente() {
      this.fechaActual.setMonth(this.fechaActual.getMonth() + 1);
      this.fechaActual = new Date(this.fechaActual); //actualización de la fecha
    },
    abrirVentana(dia) {
      if (dia) {
        this.diaSeleccionado = dia;
        this.mostrarModal = true;
      }
    },
    cerrarVentana() {
      this.mostrarModal = false;
      this.diaSeleccionado = null;
      this.cantidad = null;
      this.categoria = "";
    },
    agregarTransaccion(tipo) {
      if (this.diaSeleccionado && this.cantidad && this.categoria) {
        this.transacciones.push({
          fecha: new Date(this.diaSeleccionado),
          tipo,
          cantidad: this.cantidad,
          categoria: this.categoria,
        });
        this.cerrarVentana();
        this.guardarTransaccion({
          fecha: new Date(this.diaSeleccionado),
          tipo,
          cantidad: this.cantidad,
          categoria: this.categoria,
        });
      }
    },
    obtenerTransacciones(dia) {
      return this.transacciones.filter(
        (transaccion) => transaccion.fecha.toDateString() === dia.toDateString()
      );
    },
    guardarTransaccion(transaccion) {
      //  lógica para guardar la transacción en la base de datos
      console.log("Transacción guardada:", transaccion);
    },
  },
};
</script>

<style>
.calendario {
  margin-top: 20px;
}

.navegacion {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

table th,
table td {
  border: 1px solid #ccc;
  padding: 8px;
  text-align: center;
}

.dia {
  position: relative;
  cursor: pointer;
}

.puntos {
  display: flex;
  flex-wrap: wrap;
  gap: 2px;
}

.punto {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  margin: 2px;
}

.ingreso {
  background-color: green;
}

.gasto {
  background-color: red;
}

.modal {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: white;
  border: 1px solid #ccc;
  padding: 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  z-index: 1000;
}

.modal-contenido {
  text-align: center;
}

.modal input,
.modal select {
  display: block;
  margin: 10px auto;
}

.modal .botones {
  display: flex;
  justify-content: space-around;
}

.modal button {
  margin: 5px;
}
</style>
