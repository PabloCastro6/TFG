<template>
  <div class="tipos-transacciones">
    <!-- SECCIÓN DE GASTOS -->
    <div class="categorias">
      <h2>💸 Tipos de Gastos</h2>
      <div class="grid">
        <div
          class="card gasto"
          v-for="(tipo, index) in categoriasGastos"
          :key="index"
          @click="abrirModal(tipo, 'gasto')"
        >
          <i :class="tipo.icono"></i>
          <span>{{ tipo.nombre }}</span>
        </div>
      </div>
    </div>

    <!-- FORMULARIO PARA AÑADIR NUEVO GASTO -->
    <div class="nuevo-tipo">
      <input v-model="nuevoGasto" placeholder="Nuevo tipo de gasto" />
      <button @click="agregarNuevoTipo('gasto')">Añadir</button>
    </div>

    <!-- SECCIÓN DE INGRESOS -->
    <div class="categorias">
      <h2>💰 Tipos de Ingresos</h2>
      <div class="grid">
        <div
          class="card ingreso"
          v-for="(tipo, index) in categoriasIngresos"
          :key="index"
          @click="abrirModal(tipo, 'ingreso')"
        >
          <i :class="tipo.icono"></i>
          <span>{{ tipo.nombre }}</span>
        </div>
      </div>
    </div>

    <!-- FORMULARIO PARA AÑADIR NUEVO INGRESO -->
    <div class="nuevo-tipo">
      <input v-model="nuevoIngreso" placeholder="Nuevo tipo de ingreso" />
      <button @click="agregarNuevoTipo('ingreso')">Añadir</button>
    </div>

    <!-- MODAL -->
    <div v-if="mostrarModal" class="modal">
      <div class="modal-contenido">
        <h3>
          Registrar {{ tipoSeleccionado.nombre }} como
          <span :class="tipoSeleccionado.tipo">{{
            tipoSeleccionado.tipo
          }}</span>
        </h3>
        <input type="date" v-model="fechaSeleccionada" />
        <input
          type="number"
          v-model="cantidadSeleccionada"
          placeholder="Cantidad (€)"
          min="0"
        />
        <div class="botones">
          <button :disabled="!registrado" @click="guardarRegistro">
            Confirmar
          </button>
          <button @click="cerrarModal">Cancelar</button>
        </div>
        <p v-if="!registrado" class="alerta">
          Debes iniciar sesión para realizar esta acción.
        </p>
      </div>
    </div>

    <!-- LISTA DE TRANSACCIONES REGISTRADAS -->
    <div class="transacciones">
      <h2>📅 Nuevos Registros</h2>
      <ul>
        <li v-for="(transaccion, index) in transacciones" :key="index">
          <span class="fecha">{{ transaccion.fecha }}</span> -
          <span :class="transaccion.categoria.nombre.toLowerCase()">
            {{
              transaccion.categoria.nombre.toLowerCase() === "gasto"
                ? "🛒 Gasto"
                : "💰 Ingreso"
            }}
            - {{ transaccion.tipo }}: {{ transaccion.cantidad }}€
          </span>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import Swal from "sweetalert2";

export default {
  name: "TiposGastos",
  data() {
    return {
      nuevoGasto: "",
      nuevoIngreso: "",
      categoriasGastos: JSON.parse(
        localStorage.getItem("categoriasGastos")
      ) || [
        { nombre: "Coche", icono: "fas fa-car" },
        { nombre: "Ropa", icono: "fas fa-tshirt" },
        { nombre: "Entretenimiento", icono: "fas fa-film" },
        { nombre: "Comida", icono: "fas fa-utensils" },
        { nombre: "Gasolina", icono: "fas fa-gas-pump" },
        { nombre: "Regalos", icono: "fas fa-gift" },
        { nombre: "Salud", icono: "fas fa-heartbeat" },
        { nombre: "Vacaciones", icono: "fas fa-plane" },
        { nombre: "Deportes", icono: "fas fa-football-ball" },
      ],
      categoriasIngresos: JSON.parse(
        localStorage.getItem("categoriasIngresos")
      ) || [
        { nombre: "Trabajo", icono: "fas fa-briefcase" },
        { nombre: "Alquileres Casas", icono: "fas fa-home" },
        { nombre: "Paga de la Abuela", icono: "fas fa-user-nurse" },
      ],
      mostrarModal: false,
      tipoSeleccionado: {},
      fechaSeleccionada: "",
      cantidadSeleccionada: "",
      transacciones: JSON.parse(localStorage.getItem("transacciones")) || [],
    };
  },
  computed: {
    registrado() {
      return localStorage.getItem("registrado") === "true";
    },
  },
  methods: {
    abrirModal(tipo, categoria) {
      this.tipoSeleccionado = { ...tipo, tipo: categoria };
      this.mostrarModal = true;
      this.fechaSeleccionada = "";
      this.cantidadSeleccionada = "";
    },
    cerrarModal() {
      this.mostrarModal = false;
      this.fechaSeleccionada = "";
      this.cantidadSeleccionada = "";
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

      if (!this.fechaSeleccionada || !this.cantidadSeleccionada) {
        Swal.fire(
          "⚠️ Campos vacíos",
          "Selecciona una fecha y una cantidad",
          "warning"
        );
        return;
      }

      const transaccion = {
        fecha: this.fechaSeleccionada,
        cantidad: parseFloat(this.cantidadSeleccionada),
        usuario: { idUsuario: parseInt(localStorage.getItem("userId")) },
        categoria: {
          nombre: this.tipoSeleccionado.tipo === "gasto" ? "Gasto" : "Ingreso",
        },
        tipo: this.tipoSeleccionado.nombre,
      };

      console.log("🧾 Enviando transacción:", transaccion);

      fetch("http://localhost:8080/transacciones/transaccion", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(transaccion),
      })
        .then((res) => {
          if (!res.ok) throw new Error("Error al guardar en backend");
          return res.json();
        })
        .then((transCreada) => {
          this.transacciones.push(transCreada);
          localStorage.setItem(
            "transacciones",
            JSON.stringify(this.transacciones)
          );
          this.$emit("nueva-transaccion", transCreada);
          Swal.fire(
            "✅ Éxito",
            "Transacción guardada correctamente",
            "success"
          );
          this.cerrarModal();
        })
        .catch((err) => {
          console.error("❌ Error al guardar:", err);
          Swal.fire("❌ Error", "No se pudo guardar la transacción", "error");
        });
    },
    agregarNuevoTipo(tipo) {
      if (tipo === "gasto" && this.nuevoGasto.trim()) {
        const nuevo = {
          nombre: this.nuevoGasto.trim(),
          icono: "fas fa-tags",
        };
        this.categoriasGastos.push(nuevo);
        localStorage.setItem(
          "categoriasGastos",
          JSON.stringify(this.categoriasGastos)
        );

        fetch("http://localhost:8080/api/tipos", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            nombre: nuevo.nombre,
            tipo: "gasto",
            usuarioId: parseInt(localStorage.getItem("userId")),
          }),
        });

        this.nuevoGasto = "";
        Swal.fire(
          "🎉 Gasto añadido",
          `"${nuevo.nombre}" ha sido creado`,
          "success"
        );
      }

      if (tipo === "ingreso" && this.nuevoIngreso.trim()) {
        const nuevo = {
          nombre: this.nuevoIngreso.trim(),
          icono: "fas fa-tags",
        };
        this.categoriasIngresos.push(nuevo);
        localStorage.setItem(
          "categoriasIngresos",
          JSON.stringify(this.categoriasIngresos)
        );

        fetch("http://localhost:8080/api/tipos", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            nombre: nuevo.nombre,
            tipo: "ingreso",
            usuarioId: parseInt(localStorage.getItem("userId")),
          }),
        });

        this.nuevoIngreso = "";
        Swal.fire(
          "🎉 Ingreso añadido",
          `"${nuevo.nombre}" ha sido creado`,
          "success"
        );
      }
    },
  },
};
</script>

<style scoped>
/* Estilos generales */
.tipos-transacciones {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
  padding: 20px;
}

.categorias {
  width: 100%;
  text-align: center;
}

h2 {
  font-size: 1.5rem;
  margin-bottom: 15px;
}

/* GRID DE CATEGORÍAS */
.grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(120px, 1fr));
  gap: 15px;
}

/* TARJETAS */
.card {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background: white;
  padding: 15px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s, box-shadow 0.2s;
  font-size: 1rem;
  cursor: pointer;
}

.card:hover {
  transform: scale(1.05);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
}

.card i {
  font-size: 2rem;
  margin-bottom: 8px;
}

.gasto {
  background: #ffebee;
  color: #d32f2f;
}

.ingreso {
  background: #e8f5e9;
  color: #388e3c;
}

/* MODAL */
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.4); /* Capa oscura */
  backdrop-filter: blur(5px); /* Desenfoque de fondo */
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-contenido {
  background: #ffffff;
  padding: 25px;
  border-radius: 14px;
  text-align: center;
  box-shadow: 0px 8px 16px rgba(0, 0, 0, 0.2);
  min-width: 320px;
  max-width: 90%;
  animation: aparecer 0.3s ease-out;
}

@keyframes aparecer {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.modal h3 {
  font-size: 1.3rem;
  font-weight: bold;
  margin-bottom: 15px;
  color: #333;
}

.modal span {
  font-weight: bold;
  padding: 3px 8px;
  border-radius: 6px;
}

.gasto {
  background: #ffe0e0;
  color: #d32f2f;
}

.ingreso {
  background: #e0ffe0;
  color: #388e3c;
}

.modal input {
  width: 80%;
  padding: 12px;
  font-size: 1rem;
  border: 2px solid #ddd;
  border-radius: 10px;
  outline: none;
  margin-bottom: 15px;
  transition: border-color 0.3s ease-in-out;
}

.modal input:focus {
  border-color: #1976d2;
  box-shadow: 0 0 6px rgba(25, 118, 210, 0.4);
}

/* Botones */
.botones {
  display: flex;
  justify-content: space-between;
}

.botones button {
  padding: 10px 15px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 1rem;
  transition: all 0.3s ease-in-out;
}

.botones button:first-child {
  background-color: #1976d2;
  color: white;
}

.botones button:first-child:hover {
  background-color: #1565c0;
  transform: scale(1.05);
}

.botones button:last-child {
  background-color: #e53935;
  color: white;
}

.botones button:last-child:hover {
  background-color: #c62828;
  transform: scale(1.05);
}

/* Alerta */
.alerta {
  color: red;
  font-weight: bold;
  margin-top: 10px;
}

/* FORMULARIOS PARA AÑADIR NUEVOS TIPOS */
.nuevo-tipo {
  margin-top: 10px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 5px;
}

.nuevo-tipo input {
  padding: 10px;
  width: 250px;
  font-size: 1rem;
  border: 2px solid #ccc;
  border-radius: 14px;
  outline: none;
  transition: all 0.3s ease-in-out;
  background-color: #f8f9fa;
  text-align: center;
}

.nuevo-tipo input:focus {
  border-color: #1976d2;
  box-shadow: 0 0 8px rgba(25, 118, 210, 0.5);
  background-color: white;
}

.nuevo-tipo button {
  padding: 10px 15px;
  background-color: #1976d2;
  color: white;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  transition: all 0.3s ease-in-out;
  font-size: 1rem;
}

.nuevo-tipo button:hover {
  background-color: #1565c0;
  transform: scale(1.05);
}

/* LISTA DE TRANSACCIONES */
.transacciones {
  text-align: center;
  list-style: none;
  padding-left: 0;
}

.fecha {
  font-weight: bold;
}

.gasto {
  color: red;
}

.ingreso {
  color: green;
}
</style>
