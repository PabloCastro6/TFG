<template>
  <div class="configuracion-ahorro">
    <div>
      <h2>Calendario</h2>
      <Calendario />
    </div>
  </div>
</template>

<script>
import Calendario from "@/components/Calendario.vue";

export default {
  name: "ConfiguracionAhorro",
  components: {
    Calendario,
  },
};
</script>

<style></style>
