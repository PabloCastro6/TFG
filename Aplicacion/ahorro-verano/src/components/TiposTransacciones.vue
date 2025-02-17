<template>
  <div class="registro-transacciones">
    <h2>Registrar Transacción</h2>

    <label for="fecha">📅 Fecha:</label>
    <input type="date" v-model="fechaSeleccionada" />

    <label for="tipo">🔄 Tipo:</label>
    <div class="tipo-opciones">
      <button
        :class="{ activo: tipoSeleccionado === 'ingreso' }"
        @click="tipoSeleccionado = 'ingreso'"
      >
        💰 Ingreso
      </button>
      <button
        :class="{ activo: tipoSeleccionado === 'gasto' }"
        @click="tipoSeleccionado = 'gasto'"
      >
        💸 Gasto
      </button>
    </div>

    <label for="cantidad">💵 Cantidad (€):</label>
    <input
      type="number"
      v-model="cantidadSeleccionada"
      placeholder="Introduce el monto"
    />

    <button class="guardar-btn" @click="guardarRegistro">Guardar</button>
  </div>
</template>

<script>
export default {
  name: "TiposTransacciones",
  data() {
    return {
      fechaSeleccionada: "",
      tipoSeleccionado: "ingreso",
      cantidadSeleccionada: "",
    };
  },
  methods: {
    guardarRegistro() {
      if (!this.fechaSeleccionada || !this.cantidadSeleccionada) {
        alert("⚠️ Por favor, completa todos los campos.");
        return;
      }
      const nuevaTransaccion = {
        fecha: this.fechaSeleccionada,
        tipo: this.tipoSeleccionado,
        cantidad: parseFloat(this.cantidadSeleccionada).toFixed(2),
      };
      this.$emit("nueva-transaccion", nuevaTransaccion);
      alert(
        `✅ Transacción registrada: ${nuevaTransaccion.tipo} de ${nuevaTransaccion.cantidad}€ el ${nuevaTransaccion.fecha}`
      );
    },
  },
};
</script>

<style scoped>
/* 📌 Estilos generales */
.registro-transacciones {
  text-align: center;
  background: white;
  padding: 25px;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  max-width: 400px;
  margin: auto;
  transition: transform 0.3s ease-in-out;
}

.registro-transacciones:hover {
  transform: translateY(-3px);
}

/* 📌 Título */
h2 {
  font-size: 1.8rem;
  font-weight: bold;
  color: #2c3e50;
  margin-bottom: 15px;
}

/* 📌 Labels */
label {
  display: block;
  font-size: 1.1rem;
  font-weight: bold;
  margin-top: 15px;
  color: #2c3e50;
}

/* 📌 Inputs */
input {
  width: 100%;
  padding: 12px;
  margin-top: 5px;
  border: 2px solid #ccc;
  border-radius: 8px;
  font-size: 1rem;
  transition: border 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
}

input:focus {
  border-color: #2c3e50;
  outline: none;
  box-shadow: 0 0 8px rgba(44, 62, 80, 0.2);
}

/* 📌 Botones de tipo */
.tipo-opciones {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
}

.tipo-opciones button {
  flex: 1;
  padding: 12px;
  font-size: 1rem;
  font-weight: bold;
  border: 2px solid #ccc;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease-in-out;
  margin: 0 5px;
}

.tipo-opciones button.activo {
  background-color: #27ae60;
  color: white;
  border-color: #1e8449;
}

.tipo-opciones button:nth-child(2).activo {
  background-color: #c0392b;
  border-color: #96281b;
}

/* 📌 Botón de guardar */
.guardar-btn {
  background: #2c3e50;
  color: white;
  font-size: 1.1rem;
  font-weight: bold;
  padding: 14px;
  margin-top: 20px;
  width: 100%;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background 0.3s ease-in-out, transform 0.2s ease-in-out;
}

.guardar-btn:hover {
  background: #1a252f;
  transform: scale(1.05);
}

/* 📌 Estilos responsivos */
@media (max-width: 500px) {
  .registro-transacciones {
    width: 90%;
  }

  .tipo-opciones {
    flex-direction: column;
  }

  .tipo-opciones button {
    margin: 5px 0;
  }
}
</style>
