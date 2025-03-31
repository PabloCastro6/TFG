<template>
  <div class="registro-transacciones center">
    <h2>📅 Crear Recordatorio</h2>

    <label for="fecha">📅 Fecha:</label>
    <input type="date" class="label" v-model="recordatorio.fecha" :min="hoy" required />

    <label for="tipo">🔄 Tipo:</label>
    <div class="tipo-opciones">
      <button :class="{ activo: recordatorio.tipo === 'ingreso' }" @click="recordatorio.tipo = 'ingreso'">
        💰 Ingreso
      </button>
      <button :class="{ activo: recordatorio.tipo === 'gasto' }" @click="recordatorio.tipo = 'gasto'">
        💸 Gasto
      </button>
    </div>

    <div class="subtipo-opciones" v-if="recordatorio.tipo">
      <label for="subtipo">📋 Concepto:</label>
      <select v-model="recordatorio.concepto" class="label">
        <option value="" disabled>Selecciona un concepto</option>
        <option v-for="(opcion, index) in opcionesDisponibles" :key="index" :value="opcion">
          {{ opcion }}
        </option>
      </select>
    </div>

    <label for="cantidad">🔢 Cantidad:</label>
    <input type="number" class="label" v-model="recordatorio.cantidad" placeholder="Introduce la cantidad" min="1" required />

    <button :disabled="!registrado" class="guardar-btn" @click="guardarRecordatorio">Guardar</button>
    <p v-if="!registrado" class="alerta">Debes iniciar sesión para realizar esta acción.</p>
  </div>
</template>

<script>
import { eventBus } from "@/eventBus.js";

export default {
  name: "Recordatorio",
  data() {
    return {
      recordatorio: {
        fecha: "",
        tipo: "",
        cantidad: "",
        concepto: ""
      },
      hoy: new Date().toISOString().split("T")[0],
      opcionesIngreso: ["Salario", "Bonificación", "Otro ingreso"],
      opcionesGasto: ["Alimentación", "Transporte", "Entretenimiento"]
    };
  },
  computed: {
    registrado() {
      return localStorage.getItem("registrado") === "true";
    },
    opcionesDisponibles() {
      return this.recordatorio.tipo === "ingreso"
        ? this.opcionesIngreso
        : this.recordatorio.tipo === "gasto"
          ? this.opcionesGasto
          : [];
    }
  },
  methods: {
    async guardarRecordatorio() {
      if (!this.recordatorio.fecha || !this.recordatorio.concepto || !this.recordatorio.cantidad) {
        alert("⚠️ Completa todos los campos antes de guardar.");
        return;
      }

      // Enviar un objeto con la propiedad usuario como objeto
      const nuevoRecordatorio = {
        fecha: this.recordatorio.fecha,
        tipo: this.recordatorio.tipo,
        cantidad: this.recordatorio.cantidad,
        concepto: this.recordatorio.concepto,
        usuario: { idUsuario: parseInt(localStorage.getItem("userId")) }
      };

      try {
        const response = await fetch("http://localhost:8080/recordatorios", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(nuevoRecordatorio)
        });

        if (!response.ok) throw new Error("Error al guardar recordatorio");

        const data = await response.json();
        console.log("✅ Recordatorio guardado:", data);

        eventBus.emit("nuevo-recordatorio", data); // Emitimos el evento con la respuesta del backend

        // Limpiar formulario después de guardar
        this.recordatorio = { fecha: "", tipo: "", cantidad: "", concepto: "" };
      } catch (error) {
        console.error("❌ Error al guardar recordatorio:", error);
      }
    }
  }
};
</script>

<style scoped>
/* Tus estilos permanecen igual */
.registro-transacciones {
  flex: 3;
  max-width: 30%;
  text-align: center;
  background: white;
  padding: 25px;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  margin: auto;
  transition: transform 0.3s ease-in-out;
}
.registro-transacciones:hover {
  transform: translateY(-3px);
}
.center {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin: auto;
}
.label {
  width: 90%;
  padding: 12px;
  margin-top: 5px;
  border: 2px solid #ccc;
  border-radius: 8px;
  font-size: 1rem;
  transition: border 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
}
h2 {
  font-size: 1.8rem;
  font-weight: bold;
  color: #2c3e50;
  margin-bottom: 15px;
}
label {
  display: block;
  font-size: 1.1rem;
  font-weight: bold;
  margin-top: 15px;
  color: #2c3e50;
}
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
.subtipo-opciones {
  margin-top: 10px;
}
.subtipo-opciones select {
  width: 100%;
  padding: 16px;
  border: 2px solid #ccc;
  border-radius: 8px;
  font-size: 1rem;
  transition: border 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
}
.subtipo-opciones select:focus {
  border-color: #2c3e50;
  outline: none;
  box-shadow: 0 0 8px rgba(44, 62, 80, 0.2);
}
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
.alerta {
  color: red;
  margin-top: 10px;
}
</style>
