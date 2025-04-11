<template>
  <ul class="menu-principal">
    <li><router-link to="/" exact-active-class="active">Inicio</router-link></li>
    <li><router-link to="/configuracionAhorro" exact-active-class="active">Configuración de Ahorro</router-link></li>
    <li><router-link to="/TiposGastos" exact-active-class="active">Tipos de Gastos/Ingresos</router-link></li>
    <li><router-link to="/Estadisticas" exact-active-class="active">Estadísticas</router-link></li>
    <li><router-link to="/Recordatorio" exact-active-class="active">Recordatorios</router-link></li>
    <li v-if="!auth.registrado || auth.rol === 'ADMINISTRADOR'">
      <router-link to="/RegistroUsuarios" exact-active-class="active">Usuarios</router-link>
    </li>
  </ul>
</template>

<script>
import { useAuthStore } from "@/store/authStore";
import { computed } from "vue";

export default {
  name: "BarraNavegacion",
  setup() {
    const auth = useAuthStore();
    // Para depuración:
    console.log('Estado de auth:', auth.registrado, auth.rol);

    return { auth };
  },
};
</script>

<style>
/* Barra de navegación */
.menu-principal {
  background-color: #2c3e50;
  padding: 15px 0;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  width: 100%;
  z-index: 1000;
  display: flex;
  justify-content: center;
  list-style: none;
}

.menu-principal ul {
  display: flex;
  justify-content: center;
  align-items: center;
  list-style-type: none;
  padding: 0;
  margin: 0;
  width: 100%;
  max-width: 1200px;
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

/* Efecto hover */
.menu-principal a:hover,
.menu-principal a.active {
  color: #ff9800;
  transform: scale(1.1);
}

/* Esto asegura que el enlace activo se mantenga con el color naranja */
.router-link-exact-active {
  color: #ff9800;
  transform: scale(1.1);
}

/* Responsive */
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
