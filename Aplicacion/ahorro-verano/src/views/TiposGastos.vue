<template>
  <div class="tipos-transacciones">
    <!-- SECCIÓN DE GASTOS -->
    <div class="categorias">
      <h2>💸 Tipos de Gastos</h2>
      <div class="grid">
        <div class="card gasto" v-for="(tipo, index) in categoriasGastos" :key="index"
          @click="abrirModal(tipo, 'gasto')">
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
        <div class="card ingreso" v-for="(tipo, index) in categoriasIngresos" :key="index"
          @click="abrirModal(tipo, 'ingreso')">
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
        <input type="number" v-model="cantidadSeleccionada" placeholder="Cantidad (€)" min="0" />
        <div class="botones">
          <button class="boton-tipo" :disabled="!registrado" @click="guardarRegistro">
            Confirmar
          </button>
          <button class="eliminar-tipo" @click="eliminarTipoDesdeModal">
            Eliminar tipo
          </button>
          <button class="boton-tipo" @click="cerrarModal">Cancelar</button>
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
                ? "Gasto"
                : "Ingreso"
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
  mounted() {
    if (this.registrado) {
      this.cargarTiposDesdeBackend();
    }
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

      if (!this.fechaSeleccionada || !this.cantidadSeleccionada) {
        Swal.fire({
          title: " Campos vacíos",
          text: "Selecciona una fecha y una cantidad",
          icon: "warning",
          confirmButtonText: "Okey",
          customClass: {
            confirmButton: "miBotonCancelar",
          },
        });
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
          Swal.fire({
            title: " Éxito",
            text: "Transacción guardada correctamente",
            icon: "success",
            confirmButtonText: "Okey",
            customClass: {
              confirmButton: "miBotonCancelar",
            },
          });

          this.cerrarModal();
        })
        .catch((err) => {
          console.error(" Error al guardar:", err);
          Swal.fire(" Error", "No se pudo guardar la transacción", "error");
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
            icono: nuevo.icono,
          }),
        });

        this.nuevoGasto = "";
        this.$emit("tipo-actualizado");
        Swal.fire({
          title: " Gasto añadido",
          text: `"${nuevo.nombre}" ha sido creado`,
          icon: "success",
          confirmButtonText: "Okey",
          customClass: {
            confirmButton: "miBotonCancelar",
          },
        });
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
        Swal.fire({
          icon: 'success',
          title: ' Ingreso añadido',
          text: `"${nuevo.nombre}" ha sido creado con éxito.`,
          showConfirmButton: true,
          confirmButtonText: 'Okey',
          backdrop: true,
          customClass: {
            confirmButton: 'miBotonCancelar',
          },
        });
      }
    },
    async cargarTiposDesdeBackend() {
      const userId = parseInt(localStorage.getItem("userId"));
      if (!userId) return;

      try {
        const response = await fetch(
          `http://localhost:8080/api/tipos/${userId}`
        );
        const tipos = await response.json();

        const tiposGastoBackend = tipos
          .filter((t) => t.tipoCategoriaId === 1)
          .map((t) => ({
            nombre: t.nombre,
            icono: t.icono || "fas fa-tags",
          }));

        const tiposIngresoBackend = tipos
          .filter((t) => t.tipoCategoriaId === 2)
          .map((t) => ({
            nombre: t.nombre,
            icono: t.icono || "fas fa-tags",
          }));

        this.categoriasGastos = this.fusionarSinDuplicados(
          this.categoriasGastos,
          tiposGastoBackend
        );
        this.categoriasIngresos = this.fusionarSinDuplicados(
          this.categoriasIngresos,
          tiposIngresoBackend
        );

        this.categoriasGastos = this.eliminarDuplicados(this.categoriasGastos);
        this.categoriasIngresos = this.eliminarDuplicados(this.categoriasIngresos);

        localStorage.setItem(
          "categoriasGastos",
          JSON.stringify(this.categoriasGastos)
        );
        localStorage.setItem(
          "categoriasIngresos",
          JSON.stringify(this.categoriasIngresos)
        );
      } catch (error) {
        console.error("Error al cargar tipos desde backend:", error);
      }
    },

    fusionarSinDuplicados(listaExistente, listaNueva) {
      const nombresExistentes = listaExistente.map((item) =>
        item.nombre.toLowerCase()
      );
      const nuevos = listaNueva.filter(
        (item) => !nombresExistentes.includes(item.nombre.toLowerCase())
      );
      return [...listaExistente, ...nuevos];
    },
    eliminarDuplicados(lista) {
      const nombresVistos = new Set();
      return lista.filter((item) => {
        const nombre = item.nombre.toLowerCase();
        if (nombresVistos.has(nombre)) return false;
        nombresVistos.add(nombre);
        return true;
      });
    },

    eliminarTransaccion(id) {
      this.transacciones = this.transacciones.filter(
        (t) => t.idTransaccion !== id
      );
      localStorage.setItem("transacciones", JSON.stringify(this.transacciones));
    },

    eliminarTipoDesdeModal() {
      const tipo = this.tipoSeleccionado.tipo;
      const nombre = this.tipoSeleccionado.nombre;
      const usuarioId = parseInt(localStorage.getItem("userId"));

      Swal.fire({
        title: "¿Eliminar tipo?",
        text: `"${nombre}" se eliminará de la lista`,
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Sí, eliminar",
        cancelButtonText: "Cancelar",
        customClass: {
          confirmButton: "miBotonEliminar",
          cancelButton: "miBotonCancelar"
        },
      }).then((result) => {
        if (result.isConfirmed) {
          fetch(`http://localhost:8080/api/tipos/${nombre}/${usuarioId}`, {
            method: "DELETE",
          })
            .then((res) => {
              if (!res.ok) throw new Error("Error al eliminar en backend");

              if (tipo === "gasto") {
                this.categoriasGastos = this.categoriasGastos.filter(
                  (t) => t.nombre !== nombre
                );
                localStorage.setItem(
                  "categoriasGastos",
                  JSON.stringify(this.categoriasGastos)
                );
              } else {
                this.categoriasIngresos = this.categoriasIngresos.filter(
                  (t) => t.nombre !== nombre
                );
                localStorage.setItem(
                  "categoriasIngresos",
                  JSON.stringify(this.categoriasIngresos)
                );
              }

              Swal.fire({
                icon: 'success',
                title: ' Eliminado correctamente',
                text: `"${nombre}" ha sido eliminado con éxito.`,
                showConfirmButton: true,
                confirmButtonText: 'Okey',
                backdrop: true,
                customClass: {
                  confirmButton: 'miBotonCancelar',
                },
              });

              this.cerrarModal();
              this.transacciones = this.transacciones.filter((t) => t.tipo !== nombre);
              localStorage.setItem("transacciones", JSON.stringify(this.transacciones));
              this.$emit("tipo-actualizado");
            })
            .catch((err) => {
              console.error(" Error:", err);
              Swal.fire(" Error", "No se pudo eliminar el tipo", "error");
            });
        }
      });
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

.grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(120px, 1fr));
  gap: 15px;
}

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

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.4);
  backdrop-filter: blur(5px);
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

.boton-tipo {
  background-color: #2c3e50 !important;
}

.boton-tipo:hover {
  background-color: #1e2a37 !important;
}

.eliminar-tipo {
  background-color: #c62828;
  color: white;
  border: none;
  border-radius: 8px;
  padding: 10px 12px;
  font-size: 0.9rem;
  cursor: pointer;
  margin-left: 8px;
  transition: background-color 0.3s ease;
}

.eliminar-tipo:hover {
  background-color: #e53935;
}

.alerta {
  color: red;
  font-weight: bold;
  margin-top: 10px;
}

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
  padding: 10px 40px;
  margin-top: 5%;
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

.transacciones {
  text-align: center;
  list-style: none;
  text-decoration: none;
  padding-left: 0;
}

.transacciones ul {
  list-style-type: none;
  padding: 0;
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
