<template>
  <div>
    <!-- Barra de navegación -->
    <BarraNavegacion />

    <!-- Contenido principal -->
    <div class="contenido">
      <router-view />
    </div>
  </div>
</template>

<script>
import BarraNavegacion from "@/components/BarraNavegacion.vue";

export default {
  name: "App",
  components: {
    BarraNavegacion,
  },
};
</script>

<style>
/* Estilos de ser necesario */
</style>
