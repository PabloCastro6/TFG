<template>
  <div>
    <!-- Siempre se muestra la barra de navegación -->
    <BarraNavegacion />
    <!-- Contenido principal -->
    <div class="contenido">
      <router-view />
    </div>
  </div>
</template>

<script>
import BarraNavegacion from "@/components/BarraNavegacion.vue";
import { watch, onMounted } from "vue";
import { useRoute } from "vue-router";

export default {
  name: "App",
  components: {
    BarraNavegacion,
  },
  setup() {
    const route = useRoute();

    const updateBodyScroll = (path) => {
      const desactivarScroll = path === "/";
      document.body.style.overflow = desactivarScroll ? "hidden" : "";
    };

    watch(
      () => route.path,
      (newPath) => {
        updateBodyScroll(newPath);
      },
      { immediate: true }
    );

    onMounted(() => {
      updateBodyScroll(route.path);
    });
  },
};
</script>

<style>
@font-face {
  font-family: 'Glaure';
  src: url('@/assets/fonts/Glaure.otf') format('truetype');
  font-weight: normal;
  font-style: normal;
}

body {
  font-family: 'Glaure', sans-serif;
  font-size: 98%;
  margin: 0;
  padding: 0;
  background-image: url('@/assets/background/fondo.png');
  background-size: cover;
  background-repeat: no-repeat;
  background-attachment: fixed;
  background-position: center;
}

body, input {
  font-family: 'Glaure', sans-serif;
  font-size: 98%;
}

button {
  font-family: 'Glaure', sans-serif;
  font-size: 100%;
}

.miBotonConfirmar {
  background-color: #4CAF50 !important;
  color: white !important;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  font-weight: bold;
  font-family: inherit;
}

.miBotonEliminar {
  background-color: #be0a0a !important;
  color: white !important;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  font-weight: bold;
  font-family: inherit;
}

.miBotonCancelar {
  background-color: #2c3e50 !important;
  color: white !important;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  font-weight: bold;
  font-family: inherit;
}
</style>
