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

    <!-- MODAL PARA INGRESAR DETALLES -->
    <div v-if="mostrarModal" class="modal">
      <div class="modal-contenido">
        <h3>
          Registrar {{ tipoSeleccionado.nombre }} como
          <span :class="tipoSeleccionado.tipo">{{ tipoSeleccionado.tipo }}</span>
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
      <h2>📅 Registros</h2>
      <ul>
        <li v-for="(transaccion, index) in transacciones" :key="index">
          <span class="fecha">{{ transaccion.fecha }}</span> -
          <span :class="transaccion.tipo">
            {{ transaccion.nombre }}: {{ transaccion.cantidad }}€
          </span>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "TiposTransacciones",
  data() {
    return {
      categoriasGastos: [
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
      categoriasIngresos: [
        { nombre: "Trabajo", icono: "fas fa-briefcase" },
        { nombre: "Alquileres Casas", icono: "fas fa-home" },
        { nombre: "Paga de la Abuela", icono: "fas fa-user-nurse" },
      ],
      mostrarModal: false,
      tipoSeleccionado: {},
      fechaSeleccionada: "",
      cantidadSeleccionada: "",
      transacciones: [], // Guardará los registros
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
      // Verifica si el usuario está registrado
      if (!this.registrado) {
        alert("Debes iniciar sesión para realizar esta acción.");
        return;
      }
      if (!this.fechaSeleccionada || !this.cantidadSeleccionada) {
        alert("⚠️ Por favor, selecciona una fecha y una cantidad.");
        return;
      }

      // Guardamos el registro en el array de transacciones
      this.transacciones.push({
        nombre: this.tipoSeleccionado.nombre,
        tipo: this.tipoSeleccionado.tipo,
        fecha: this.fechaSeleccionada,
        cantidad: parseFloat(this.cantidadSeleccionada).toFixed(2),
      });

      // Enviar la transacción a la configuración de ahorro (si es necesario)
      this.$emit("nueva-transaccion", this.transacciones);

      alert(
        `✅ ${this.tipoSeleccionado.nombre} registrado como ${this.tipoSeleccionado.tipo} el ${this.fechaSeleccionada} con ${this.cantidadSeleccionada}€`
      );
      this.cerrarModal();
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
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal-contenido {
  background: white;
  padding: 20px;
  border-radius: 10px;
  text-align: center;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  min-width: 300px;
}

input {
  width: 100%;
  padding: 8px;
  font-size: 1rem;
  margin-bottom: 15px;
}

.botones button {
  padding: 10px;
  margin: 5px;
  cursor: pointer;
}

.alerta {
  color: red;
  font-weight: bold;
  margin-top: 10px;
}

/* LISTA DE TRANSACCIONES */
.transacciones {
  width: 100%;
  text-align: center;
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
