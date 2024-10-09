<template>
  <div class="tipos-gastos">
    <h2>Tipos de Gastos</h2>
    <ul>
      <li v-for="(tipo, index) in categoriasGastos" :key="index">
        <i :class="tipo.icono"></i> {{ tipo.nombre }}
      </li>
    </ul>
    <h2>Tipos de Ingresos</h2>
    <ul>
      <li v-for="(tipo, index) in categoriasIngresos" :key="index">
        <i :class="tipo.icono"></i> {{ tipo.nombre }}
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "TiposGastos",
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
        { nombre: "Árbitros", icono: "fas fa-whistle" },
        { nombre: "Camisetas", icono: "fas fa-tshirt" },
      ],
    };
  },
};
</script>

<style>
.tipos-gastos {
  margin: 25px 20px; /* Reducido el margen superior */
  display: flex;
  justify-content: space-between;
}

.tipos-gastos .gastos {
  flex-basis: 45%;
}

.tipos-gastos .ingresos {
  flex-basis: 45%;
}

.tipos-gastos h2 {
  text-align: center;
}

.tipos-gastos ul {
  list-style-type: none;
  padding: 0;
  margin: 0;
}

.tipos-gastos li {
  margin-bottom: 15px;
  display: flex;
  align-items: center;
}

.tipos-gastos i {
  margin-right: 20px;
}
</style>
