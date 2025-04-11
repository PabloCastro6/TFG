import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import { createPinia } from 'pinia';
import '@fortawesome/fontawesome-free/css/all.css';

const app = createApp(App);
const pinia = createPinia();

app.use(pinia);
app.use(router);
app.mount('#app');


window.addEventListener("beforeunload", () => {
  localStorage.removeItem("registrado");
  localStorage.removeItem("correo");
});
