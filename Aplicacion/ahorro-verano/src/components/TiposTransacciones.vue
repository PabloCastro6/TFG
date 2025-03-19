<template>
  <div class="registro-transacciones">
    <h2>Registrar Transacción</h2>

    <label for="fecha">📅 Fecha:</label>
    <input type="date" class="label" v-model="fechaSeleccionada" />

    <label for="tipo">🔄 Categoría:</label>
    <div class="tipo-opciones">
      <button
        :class="{ activo: categoriaSeleccionada === 'ingreso' }"
        @click="seleccionarCategoria('ingreso')"
      >
        💰 Ingreso
      </button>
      <button
        :class="{ activo: categoriaSeleccionada === 'gasto' }"
        @click="seleccionarCategoria('gasto')"
      >
        💸 Gasto
      </button>
    </div>

    <!-- Desplegable para seleccionar el concepto, que será el tipo (subtipo) -->
    <div class="subtipo-opciones" v-if="categoriaSeleccionada">
      <label for="subtipo">📋 Concepto:</label>
      <select v-model="conceptoSeleccionado" class="label">
        <option value="" disabled>Selecciona un concepto</option>
        <option
          v-for="(opcion, index) in opcionesDisponibles"
          :key="index"
          :value="opcion"
        >
          {{ opcion }}
        </option>
      </select>
    </div>

    <label for="cantidad">💵 Cantidad (€):</label>
    <input
      type="number"
      class="label"
      v-model="cantidadSeleccionada"
      placeholder="Introduce la cantidad"
    />

    <button :disabled="!registrado" class="guardar-btn" @click="guardarRegistro">
      Guardar
    </button>
    <p v-if="!registrado" class="alerta">
      Debes iniciar sesión para realizar esta acción.
    </p>
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
    }
  },
  data() {
    return {
      fechaSeleccionada: "",
      // "categoriaSeleccionada" define "ingreso" o "gasto"
      categoriaSeleccionada: "ingreso", // valor por defecto
      cantidadSeleccionada: "",
      // "conceptoSeleccionado" será el concepto (subtipo) que se elige en el desplegable
      conceptoSeleccionado: "",
      // Opciones para el desplegable según la categoría
      opcionesIngreso: ["Salario", "Bonificación", "Otro ingreso"],
      opcionesGasto: ["Alimentación", "Transporte", "Entretenimiento"],
    };
  },
  computed: {
    // Convierte la fecha de yyyy-MM-dd a dd-MM-yyyy para el mensaje
    fechaFormateada() {
      if (!this.fechaSeleccionada) return "";
      const [year, month, day] = this.fechaSeleccionada.split("-");
      return `${day}-${month}-${year}`;
    },
    registrado() {
      return localStorage.getItem("registrado") === "true";
    },
    userId() {
      return localStorage.getItem("userId") || "Desconocido";
    },
    // Opciones según la categoría seleccionada
    opcionesDisponibles() {
      return this.categoriaSeleccionada === "ingreso"
        ? this.opcionesIngreso
        : this.opcionesGasto;
    }
  },
  methods: {
    seleccionarCategoria(categoria) {
      this.categoriaSeleccionada = categoria;
      // Resetea el concepto al cambiar de categoría
      this.conceptoSeleccionado = "";
    },
    async guardarRegistro() {
      if (!this.registrado) {
        alert("Debes iniciar sesión para realizar esta acción.");
        return;
      }
      if (!this.fechaSeleccionada || !this.cantidadSeleccionada || !this.conceptoSeleccionado) {
        alert("⚠️ Por favor, completa todos los campos, incluido el concepto.");
        return;
      }
      
      // Convertir la fecha del input (yyyy-MM-dd) a dd-MM-yyyy
      const [year, month, day] = this.fechaSeleccionada.split("-");
      const fechaParaEnviar = `${day}-${month}-${year}`;

      // Construye el objeto de la nueva transacción
      const nuevaTransaccion = {
        fecha: fechaParaEnviar,
        cantidad: parseFloat(this.cantidadSeleccionada),
        usuario: {
          idUsuario: parseInt(this.userId)
        },
        // La categoría se envía como objeto con la propiedad "nombre"
        categoria: {
          nombre: this.categoriaSeleccionada
        },
        // El campo "tipo" se usa para almacenar el concepto seleccionado
        tipo: this.conceptoSeleccionado
      };

      // Emite el evento (opcional)
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

        alert(`✅ Transacción registrada: ${nuevaTransaccion.categoria.nombre} - ${nuevaTransaccion.tipo} de ${nuevaTransaccion.cantidad}€ el ${this.fechaFormateada}. ID de usuario: ${this.userId}`);
      } catch (error) {
        console.error("Error al enviar la transacción:", error);
        alert("❌ Error al enviar la transacción a la API");
      }
    }
  }
};
</script>

<style scoped>
/* 📌 Estilos generales */
.registro-transacciones {
  flex: 3;
  max-width: 100%;
  width: 40%;
  text-align: center;
  background: white;
  padding: 25px;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  margin: auto;
  transition: transform 0.3s ease-in-out;
  margin-left: 5%;
  margin-top: 5%;
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
  transition: border 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
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

/* 📌 Desplegable de subtipo */
.subtipo-opciones {
  margin-top: 10px;
}
.subtipo-opciones select {
  width: 90%;
  padding: 12px;
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

.alerta {
  color: red;
  margin-top: 10px;
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
