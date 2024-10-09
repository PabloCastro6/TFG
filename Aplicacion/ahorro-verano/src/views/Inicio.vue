<template>
  <div class="inicio">
    <img src="../assets/ahorro.png" alt="Logo" class="logo" />
    <h1>Bienvenidos a mi aplicacion de ahorros</h1>
    <p>"Ahorrar no es solo guardar, sino saber gastar"</p>
  </div>
  <h2 @click="DarlemostrarIcono">¿Quieres saber todo lo que gastas?</h2>
  <img
    v-if="mostrarIcono"
    src="../assets/okey.png"
    alt="Dedo"
    class="icono-dedo"
  />
</template>

<script>
export default {
  name: "PaginaInicio",
  data() {
    return {
      mostrarIcono: false,
    };
  },
  methods: {
    DarlemostrarIcono() {
      this.mostrarIcono = !this.mostrarDedo;
    },
  },
};
</script>

<style>
.inicio {
  text-align: center;
}

.inicio p {
  font-style: italic;
  margin-top: 10px;
}

.icono-dedo {
  width: 150px;
  margin-top: 15px;
  cursor: pointer;
}
</style>
