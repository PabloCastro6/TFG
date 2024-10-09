<template>
  <div class="registro-gastos">
    <h2>Registro de Gastos</h2>
    <div>
      <label for="tipoGasto">Tipo de Gasto:</label>
      <select v-model="tipoGasto" id="tipoGasto">
        <option v-for="tipo in tiposGastos" :key="tipo.id" :value="tipo.id">
          {{ tipo.nombre }}
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
      tipoGasto: null,
      monto: 0,
    };
  },
  computed: {
    tiposGastos() {
      // Aquí puedes obtener los tipos de gastos desde tu base de datos o de donde estés almacenándolos
      return [
        { id: 1, nombre: "Ocio" },
        { id: 2, nombre: "Servicios bancarios" },
        { id: 3, nombre: "Viajes" },
        { id: 4, nombre: "Salud y belleza" },
        // Agrega más tipos de gastos según sea necesario
      ];
    },
  },
  methods: {
    registrarGasto() {
      // Aquí puedes realizar la lógica para registrar el gasto, como guardar los datos en la base de datos
      console.log("Gasto registrado:", {
        tipoGasto: this.tipoGasto,
        monto: this.monto,
      });
      // Después de registrar el gasto, puedes restablecer los campos
      this.tipoGasto = null;
      this.monto = 0;
    },
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
