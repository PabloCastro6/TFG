<template>
  <div class="configuracion-ahorro">
    <div class="contenedor">
      <!-- 📅 Calendario a la izquierda -->
      <Calendario :transacciones="transacciones" @fecha-seleccionada="actualizarFecha"/>

      <!-- 📌 Formulario a la derecha -->
      <TiposTransacciones :fechaPreseleccionada="fechaSeleccionada" @nueva-transaccion="agregarTransaccion" />
    </div>
  </div>
</template>

<script>
import Calendario from "@/components/Calendario.vue";
import TiposTransacciones from "@/components/TiposTransacciones.vue";

export default {
  name: "ConfiguracionAhorro",
  components: {
    Calendario,
    TiposTransacciones,
  },
  data() {
    return {
      transacciones: [], // Lista de transacciones
      fechaSeleccionada: "",
    };
  },
  methods: {
    agregarTransaccion(transaccion) {
      this.transacciones.push(transaccion);
    },
    actualizarFecha(fecha) {
      this.fechaSeleccionada = fecha;
    },
  },
};
</script>

<style scoped>
.contenedor {
  display: flex;
  justify-content: space-between;
  gap: 20px;
  max-width: 1200px;
  margin: auto;
  padding: 20px;
}


.configuracion-ahorro {
  text-align: center;
}
</style>
