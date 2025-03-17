<template>
  <div class="registro-container">
    <h2 class="titulo">👤 Registrar Usuario</h2>
    <form class="formulario" @submit.prevent="registrarUsuario">
      <div class="campo">
        <label>Nombre Completo:</label>
        <input v-model="usuario.nombreCompleto" placeholder="Pepe Perez Rodriguez" type="text" required />
      </div>
      <div class="campo">
        <label>Correo:</label>
        <input v-model="usuario.correo" placeholder="example@gmail.com" type="email" required />
      </div>
      <div class="campo">
        <label>Contraseña:</label>
        <input v-model="usuario.password" placeholder="*********" type="password" required />
      </div>
      <button class="guardar-btn" type="submit">Registrar</button>
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

<style scoped>
.registro-container {
  width: 350px;
  margin: auto;
  background: white;
  padding: 20px;
  margin-top: 8%;
  border-radius: 10px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
  text-align: center;
}

.titulo {
  font-size: 22px;
  margin-bottom: 20px;
  font-weight: bold;
}

.formulario {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.campo {
  display: flex;
  flex-direction: column;
  text-align: left;
}

.campo label {
  font-weight: bold;
  margin-bottom: 5px;
}

.campo input {
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;
}

.guardar-btn {
  background: #2c3e50;
  color: white;
  font-size: 1.1rem;
  font-weight: bold;
  padding: 14px;
  margin-top: 20px;
  width: 100%;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background 0.3s ease-in-out, transform 0.2s ease-in-out;
}

.guardar-btn:hover {
  background: #1a252f;
  transform: scale(1.05);
}
</style>
