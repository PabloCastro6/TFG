<template>
  <div class="registro-gastos">
    <h2>Registro de Gastos</h2>
    <div>
      <label for="tipoGasto">Tipo de Gasto:</label>
      <select v-model="tipoGasto" id="tipoGasto">
        <option v-for="tipo in tiposGastos" :key="tipo" :value="tipo">
          {{ tipo }}
        </option>
      </select>
    </div>
    <div>
      <label for="monto">Monto (€):</label>
      <input
        type="number"
        id="monto"
        v-model="monto"
        placeholder="Ingrese el monto del gasto"
      />
    </div>
    <button @click="registrarGasto">Registrar Gasto</button>
  </div>
</template>

<script>
export default {
  name: "RegistroGastos",
  data() {
    return {
      tipoGasto: "",
      monto: null,
      tiposGastos: [],
    };
  },
  methods: {
    async registrarGasto() {
      if (this.tipoGasto && this.monto > 0) {
        const gasto = { tipo: this.tipoGasto, monto: this.monto };
        console.log("Enviar al backend:", gasto);
        this.tipoGasto = "";
        this.monto = null;
      } else {
        alert("Por favor, selecciona un tipo de gasto y un monto válido.");
      }
    },
  },
  async mounted() {
    this.tiposGastos = ["Ocio", "Salud", "Comida"]; // Simulación de datos del backend
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
