import { defineStore } from 'pinia';

export const useAuthStore = defineStore('auth', {
  state: () => ({
    rol: localStorage.getItem('rol') || '',
    userId: localStorage.getItem('userId') || null,
    registrado: localStorage.getItem('registrado') === 'true',
  }),
  actions: {
    setUserData({ rol, userId }) {
      this.rol = rol;
      this.userId = userId;
      this.registrado = true;
    },
    logout() {
      this.rol = '';
      this.userId = null;
      this.registrado = false;
      localStorage.clear();
    }
  }
});
