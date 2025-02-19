<template>
  <div class="configuracion-ahorro">
    <div class="contenedor">
      <!-- 📅 Calendario a la izquierda -->
      <Calendario :transacciones="transacciones" />

      <!-- 📌 Formulario a la derecha -->
      <TiposTransacciones @nueva-transaccion="agregarTransaccion" />
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
    };
  },
  methods: {
    agregarTransaccion(nuevaTransaccion) {
      this.transacciones.push(nuevaTransaccion); // Agregamos la nueva transacción
    },
  },
};
</script>

<style scoped>
.contenedor {
  display: flex;
  justify-content: space-between;
  gap: 30px;
  max-width: 1200px;
  margin: auto;
  padding: 20px;
}

.configuracion-ahorro {
  text-align: center;
}
</style>
