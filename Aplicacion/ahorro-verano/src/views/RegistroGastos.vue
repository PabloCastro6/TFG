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
.registro-gastos {
  text-align: center;
}

.registro-gastos div {
  margin-bottom: 10px;
}

.registro-gastos label {
  margin-right: 10px;
}
</style>
