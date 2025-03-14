<!--Se muestra en la parte superior de cada vista para permitir la navegacion de las diferentes secciones-->
<template>
  <ul class="menu-principal">
    <li><router-link to="/">Inicio</router-link></li>
    <li>
      <router-link to="/configuracionAhorro"
        >Configuración de Ahorro</router-link
      >
    </li>
    <li>
      <router-link to="/TiposGastos">Tipos de Gastos/Ingresos</router-link>
    </li>
    <li><router-link to="/Estadisticas">Estadísticas</router-link></li>
    <li><router-link to="/Recordatorio">Recordatorios</router-link></li>

    <li><router-link to="/RegistroUsuarios">Usuarios</router-link></li>
  </ul>
</template>

<script>
export default {
  name: "BarraNavegacion",
};
</script>

<style>
/* Menú de navegación centrado */
.menu-principal {
  background-color: #2c3e50;
  padding: 15px 0;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  width: 100%;
  z-index: 1000;
  display: flex;
  justify-content: center; /* Centra el contenido horizontalmente */
  list-style: none;
}

.menu-principal ul {
  display: flex;
  justify-content: center; /* Centra los elementos */
  align-items: center;
  list-style-type: none;
  padding: 0;
  margin: 0;
  width: 100%;
  max-width: 1200px; /* Ajusta el ancho máximo */
}

.menu-principal li {
  margin: 0 15px;
}

.menu-principal a {
  text-decoration: none;
  color: #ffffff;
  font-weight: bold;
  font-size: 16px;
  transition: color 0.3s, transform 0.3s;
  padding: 10px 15px;
  border-radius: 5px;
}

.menu-principal a:hover {
  color: #ff9800;
  transform: scale(1.1);
}

/* Responsividad para pantallas pequeñas */
@media (max-width: 768px) {
  .menu-principal ul {
    flex-direction: column;
    align-items: center;
  }

  .menu-principal li {
    margin: 8px 0;
  }
}
</style>
