<template>
  <div>
    <h2>Registro Usuario</h2>
    <form @submit.prevent="registrarUsuario">
      <div>
        <label>Nombre Completo</label>
        <input v-model="usuario.nombreCompleto" type="text" required />
      </div>
      <div>
        <label>Correo</label>
        <input v-model="usuario.correo" type="email" required />
      </div>
      <div>
        <label>Contraseña</label>
        <input v-model="usuario.password" type="password" required />
      </div>

      <button type="submit">Registrar</button>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      usuario: {
        nombreCompleto: "",
        correo: "",
        password: "",
      },
    };
  },
  methods: {
    registrarUsuario() {
      console.log("Usuario registrado:", this.usuario);
      
      alert("Usuario registrado ");
    },
  },
};
</script>

<style></style>
