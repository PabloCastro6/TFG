<template>
  <div class="registro-transacciones">
    <h2>Registrar Transacción</h2>

    <label for="fecha">📅 Fecha:</label>
    <input type="date" class="label" v-model="fechaSeleccionada" />

    <label for="tipo">🔄 Tipo:</label>
    <div class="tipo-opciones">
      <button :class="{ activo: tipoSeleccionado === 'ingreso' }" @click="tipoSeleccionado = 'ingreso'">
        💰 Ingreso
      </button>
      <button :class="{ activo: tipoSeleccionado === 'gasto' }" @click="tipoSeleccionado = 'gasto'">
        💸 Gasto
      </button>
    </div>

    <label for="cantidad">💵 Cantidad (€):</label>
    <input type="number" class="label" v-model="cantidadSeleccionada" placeholder="Introduce la cantidad" />

    <button class="guardar-btn" @click="guardarRegistro">Guardar</button>
  </div>
</template>

<script>
export default {
  name: "TiposTransacciones",
  props: {
    fechaPreseleccionada: String,
  },
  watch: {
    fechaPreseleccionada(nuevaFecha) {
      this.fechaSeleccionada = nuevaFecha;
    },
  },
  data() {
    return {
      fechaSeleccionada: "",
      tipoSeleccionado: "ingreso",
      cantidadSeleccionada: "",
    };
  },
  computed: {
    fechaFormateada() {
      if (!this.fechaSeleccionada) return "";
      const [year, month, day] = this.fechaSeleccionada.split("-");
      return `${day}-${month}-${year}`; // Convierte de yyyy-MM-dd a dd-MM-yyyy
    }
  },
  methods: {
    async guardarRegistro() {
      if (!this.fechaSeleccionada || !this.cantidadSeleccionada) {
        alert("⚠️ Por favor, completa todos los campos.");
        return;
      }

      const nuevaTransaccion = {
        fecha: this.fechaSeleccionada, // Se envía en formato yyyy-MM-dd
        tipo: this.tipoSeleccionado,
        cantidad: parseFloat(this.cantidadSeleccionada),
      };

      this.$emit("nueva-transaccion", nuevaTransaccion);

      try {
        const respuesta = await fetch("http://localhost:8080/transacciones/transaccion", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(nuevaTransaccion),
        });

        if (!respuesta.ok) {
          throw new Error("Error en la llamada a la API");
        }

        const data = await respuesta.json();
        console.log("Transacción creada:", data);

        alert(`✅ Transacción registrada: ${nuevaTransaccion.tipo} de ${nuevaTransaccion.cantidad}€ el ${this.fechaFormateada}`);
      } catch (error) {
        console.error("Error al enviar la transacción:", error);
        alert("❌ Error al enviar la transacción a la API");
      }
    }
  },
};
</script>

<style scoped>
/* 📌 Estilos generales */
.registro-transacciones {
  flex: 3;
  /* 30% del espacio disponible */
  max-width: 30%;
  text-align: center;
  background: white;
  padding: 25px;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  margin: auto;
  transition: transform 0.3s ease-in-out;
  margin-left: 5%;
}

.registro-transacciones:hover {
  transform: translateY(-3px);
}

.label {
  width: 90%;
  padding: 12px;
  margin-top: 5px;
  border: 2px solid #ccc;
  border-radius: 8px;
  font-size: 1rem;
  transition: border 0.3sease-in-out, box-shadow 0.3sease-in-out;
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
