import { createRouter, createWebHistory } from "vue-router";
import Inicio from "@/views/Inicio.vue";
import Recordatorio from "@/views/Recordatorio.vue";
import ConfiguracionAhorro from "@/views/ConfiguracionAhorro.vue";
import TiposGastos from "@/views/TiposGastos.vue";
import Estadisticas from "@/views/Estadisticas.vue";
import RegistroUsuarios from "@/views/RegistroUsuarios.vue";

const routes = [
  {
    path: "/",
    name: "Inicio",
    component: Inicio,
  },
  {
    path: "/Recordatorio",
    name: "Recordatorio",
    component: Recordatorio,
  },
  {
    path: "/ConfiguracionAhorro",
    name: "ConfiguracionAhorro",
    component: ConfiguracionAhorro,
  },
  {
    path: "/TiposGastos",
    name: "TiposGastos",
    component: TiposGastos,
  },
  {
    path: "/Estadisticas",
    name: "Estadisticas",
    component: Estadisticas,
  },
  {
    path: "/RegistroUsuarios",
    name: "RegistroUsuarios",
    component: RegistroUsuarios,
  },
];

const router = createRouter({
  history: createWebHistory(),
  base: process.env.BASE_URL,
  routes,
});

export default router;
