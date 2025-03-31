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

    <button
      :disabled="!registrado"
      class="guardar-btn"
      @click="guardarRegistro"
    >
      Guardar
    </button>
    <p v-if="!registrado" class="alerta">
      Debes iniciar sesión para realizar esta acción.
    </p>
  </div>
</template>

<script>
import Swal from "sweetalert2";

export default {
  name: "TiposTransacciones",
  props: {
    fechaPreseleccionada: String,
  },
  data() {
    return {
      fechaSeleccionada: "",
      categoriaSeleccionada: "ingreso",
      cantidadSeleccionada: "",
      conceptoSeleccionado: "",
      opcionesIngreso: ["Salario", "Bonificación", "Otro ingreso"],
      opcionesGasto: ["Alimentación", "Transporte", "Entretenimiento"],
    };
  },
  computed: {
    registrado() {
      return localStorage.getItem("registrado") === "true";
    },
    userId() {
      return localStorage.getItem("userId") || "Desconocido";
    },
    opcionesDisponibles() {
      return this.categoriaSeleccionada === "ingreso"
        ? this.opcionesIngreso
        : this.opcionesGasto;
    },
  },
  watch: {
    fechaPreseleccionada(nuevaFecha) {
      this.fechaSeleccionada = nuevaFecha;
    },
  },
  mounted() {
    const ingresosGuardados = localStorage.getItem("categoriasIngresos");
    const gastosGuardados = localStorage.getItem("categoriasGastos");

    if (ingresosGuardados) {
      const opciones = JSON.parse(ingresosGuardados).map((t) => t.nombre);
      this.opcionesIngreso.push(
        ...opciones.filter((n) => !this.opcionesIngreso.includes(n))
      );
    }

    if (gastosGuardados) {
      const opciones = JSON.parse(gastosGuardados).map((t) => t.nombre);
      this.opcionesGasto.push(
        ...opciones.filter((n) => !this.opcionesGasto.includes(n))
      );
    }
  },
  methods: {
    seleccionarCategoria(categoria) {
      this.categoriaSeleccionada = categoria;
      this.conceptoSeleccionado = "";
    },
    guardarRegistro() {
      if (!this.registrado) {
        Swal.fire(
          "⚠️ Atención",
          "Debes iniciar sesión para realizar esta acción.",
          "warning"
        );
        return;
      }

      if (
        !this.fechaSeleccionada ||
        !this.cantidadSeleccionada ||
        !this.conceptoSeleccionado
      ) {
        Swal.fire(
          "⚠️ Campos incompletos",
          "Por favor, completa todos los campos.",
          "error"
        );
        return;
      }

      const userId = parseInt(localStorage.getItem("userId"));

      const transaccion = {
        fecha: this.fechaSeleccionada,
        cantidad: parseFloat(this.cantidadSeleccionada),
        usuario: { idUsuario: parseInt(localStorage.getItem("userId")) },
        categoria: {
          nombre: this.categoriaSeleccionada === "gasto" ? "Gasto" : "Ingreso",
        },
        tipo: this.conceptoSeleccionado,
      };

      console.log("ENVIADO", JSON.stringify(transaccion, null, 2));

      fetch("http://localhost:8080/transacciones/transaccion", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(transaccion),
      })
        .then((response) => {
          if (!response.ok) throw new Error("Error al guardar en el backend");
          return response.json();
        })
        .then((data) => {
          this.$emit("nueva-transaccion", data); // Enviar al componente padre
          Swal.fire({
            title: "✅ Transacción registrada",
            html: `
              <b>📅 Fecha:</b> ${data.fecha} <br>
              <b>🔄 Categoría:</b> ${data.categoria.nombre} <br>
              <b>📋 Concepto:</b> ${data.tipo} <br>
              <b>💵 Cantidad:</b> ${data.cantidad}€ <br>
            `,
            icon: "success",
          });

          this.fechaSeleccionada = "";
          this.conceptoSeleccionado = "";
          this.cantidadSeleccionada = "";
        })
        .catch((error) => {
          console.error("Error al guardar transacción:", error);
          Swal.fire(
            "❌ Error",
            "No se pudo guardar la transacción. Inténtalo de nuevo.",
            "error"
          );
        });
    },
  },
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
