<!--Se muestra en la parte superior de cada vista para permitir la navegacion de las diferentes secciones-->
<template>
  <ul class="menu-principal">
    <li><router-link to="/">Inicio</router-link></li>
    <li>
      <router-link to="/configuracionAhorro"
        >Configuración de Ahorro</router-link
      >
    </li>
    <li><router-link to="/TiposGastos">Tipos de Gastos</router-link></li>
    <li><router-link to="/Estadisticas">Estadísticas</router-link></li>
    <li><router-link to="/registro-datos">Registro de Datos</router-link></li>
    <li>
      <router-link to="/RegistroIngresos">Registro de Ingresos</router-link>
    </li>
    <li><router-link to="/usuarios">Usuarios</router-link></li>
  </ul>
</template>

<script>
export default {
  name: "BarraNavegacion",
};
</script>

<style>
.menu-principal {
  background-color: #2c3e50; /* Color de fondo */
  padding: 15px 0; /* Espaciado interno */
  list-style-type: none;
  margin: 0;
  padding: 0;
  text-align: center;
}

.menu-principal li {
  display: inline-block;
  margin-right: 20px;
}

.menu-principal li:last-child {
  margin-right: 0;
}

.menu-principal a {
  text-decoration: none;
  color: #fff; /* Color del texto */
  font-weight: bold;
  transition: color 0.3s; /* Transición de color al pasar el ratón */
}

.menu-principal a:hover {
  color: #ff9800; /* Color del texto al pasar el ratón */
}
</style>
