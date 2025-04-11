import { createRouter, createWebHistory } from "vue-router";
import Inicio from "@/views/Inicio.vue";
import Recordatorio from "@/components/Recordatorio.vue";
import ConfiguracionAhorro from "@/views/ConfiguracionAhorro.vue";
import TiposGastos from "@/views/TiposGastos.vue";
import Estadisticas from "@/views/Estadisticas.vue";
import RegistroUsuarios from "@/views/RegistroUsuarios.vue";
import AccesoDenegado from "@/views/AccesoDenegado.vue";

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
    meta: { requiereRegistro: true }, // 🚀 Ahora está protegido
  },
  {
    path: "/ConfiguracionAhorro",
    name: "ConfiguracionAhorro",
    component: ConfiguracionAhorro,
    meta: { requiereRegistro: true }, // 🚀 Ahora está protegido
  },
  {
    path: "/TiposGastos",
    name: "TiposGastos",
    component: TiposGastos,
    meta: { requiereRegistro: true }, // 🚀 Ahora está protegido
  },
  {
    path: "/Estadisticas",
    name: "Estadisticas",
    component: Estadisticas,
    meta: { requiereRegistro: true }, // 🚀 Ahora está protegido
  },
  {
    path: "/RegistroUsuarios",
    name: "RegistroUsuarios",
    component: RegistroUsuarios,
  },
  {
    path: "/AccesoDenegado",
    name: "AccesoDenegado",
    component: AccesoDenegado,
  },
];

const router = createRouter({
  history: createWebHistory(),
  base: process.env.BASE_URL,
  routes,
});

// Navigation Guard global para proteger rutas que requieren autenticación
router.beforeEach((to, from, next) => {
  const usuarioRegistrado = localStorage.getItem("registrado") === "true";

  if (to.meta.requiereRegistro && !usuarioRegistrado) {
    next("/AccesoDenegado"); // 🔒 Redirige si no está registrado
  } else {
    next(); // ✅ Permite el acceso
  }
});

export default router;
