import { createRouter, createWebHistory } from 'vue-router';
import Inicio from '@/views/Inicio.vue';
import RegistroGastos from '@/views/RegistroGastos.vue';
import RegistroIngresos from '@/views/RegistroIngresos.vue';
import ConfiguracionAhorro from '@/views/ConfiguracionAhorro.vue';
import TiposGastos from '@/views/TiposGastos.vue';
import Estadisticas from '@/views/Estadisticas.vue';



const routes  = [
    {
            path: '/',
            name: 'Inicio',
            component: Inicio
    },
    {
        path: '/RegistroGastos',
        name: 'RegistroGastos',
        component: RegistroGastos
    },
    {
        path: '/RegistroIngresos',
        name: 'RegistroIngresos',
        component: RegistroIngresos
    },
    {
        path: '/ConfiguracionAhorro',
        name: 'ConfiguracionAhorro',
        component: ConfiguracionAhorro
    },
    {
        path: '/TiposGastos',
        name: 'TiposGastos',
        component: TiposGastos
    },
    {
        path: '/Estadisticas',
        name: 'Estadisticas',
        component: Estadisticas
    }
];

const router = createRouter({
    history:createWebHistory(),
     base: process.env.BASE_URL,
     routes
   });
   
   export default router;