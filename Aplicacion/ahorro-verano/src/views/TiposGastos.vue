<template>
  <div class="tipos-transacciones">
    <div class="categorias">
      <h2>💸 Tipos de Gastos</h2>
      <div class="grid">
        <div
          class="card gasto"
          v-for="(tipo, index) in categoriasGastos"
          :key="index"
        >
          <i :class="tipo.icono"></i>
          <span>{{ tipo.nombre }}</span>
        </div>
      </div>
    </div>

    <div class="categorias">
      <h2>💰 Tipos de Ingresos</h2>
      <div class="grid">
        <div
          class="card ingreso"
          v-for="(tipo, index) in categoriasIngresos"
          :key="index"
        >
          <i :class="tipo.icono"></i>
          <span>{{ tipo.nombre }}</span>
        </div>
      </div>
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
        { nombre: "Árbitros", icono: "fas fa-whistle" },
        { nombre: "Camisetas", icono: "fas fa-tshirt" },
      ],
    };
  },
};
</script>

<style scoped>
.tipos-transacciones {
  display: flex;
  justify-content: space-between;
  gap: 20px;
  padding: 20px;
}

.categorias {
  flex: 1;
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
</style>
