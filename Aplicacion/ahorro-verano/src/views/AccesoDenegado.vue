<template>
    <div class="acceso-denegado">
      <div class="contenedor">
        <h1 class="titulo">¡Acceso Denegado!</h1>
        <p class="mensaje">
          Para acceder a esta página, necesitas estar registrado. 
          Si ya tienes una cuenta, por favor inicia sesión. Si no, puedes
          registrarte <router-link to="/RegistroUsuarios">aquí</router-link>.
        </p>
        <button class="boton-volver" @click="volverAlInicio">Volver al inicio</button>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: "AccesoDenegado",
    methods: {
      volverAlInicio() {
        this.$router.push('/');
      }
    }
  };
  </script>
  
  <style scoped>

  html, body {
    height: 100%;
    margin: 0;
    overflow: hidden; 
  }
  

  .acceso-denegado {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 88vh;
    width: 100%; 
    background: linear-gradient(135deg, #a31f1f, #f83232);
    font-family: 'Arial', sans-serif;
    color: white;
    text-align: center;
    overflow: hidden;
  }
  
  .contenedor {
    background-color: rgba(0, 0, 0, 0.6);
    padding: 30px;
    border-radius: 12px;
    box-shadow: 0 8px 15px rgba(0, 0, 0, 0.2);
    max-width: 600px;
    width: 100%;
    box-sizing: border-box; 
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
  
  .titulo {
    font-size: 36px;
    font-weight: bold;
    margin-bottom: 20px;
    text-transform: uppercase;
    color: #f8f8f8;
    letter-spacing: 2px;
  }
  
  .mensaje {
    font-size: 18px;
    margin-bottom: 20px;
    color: #f8f8f8;
  }
  
  .mensaje a {
    color: rgb(222, 49, 49);
    text-decoration: none;
    font-weight: bold;
    transition: color 0.3s ease;
  }
  
  .mensaje a:hover {
    color: #ff0000;
  }
  
  .boton-volver {
    background-color: #ffffff;
    color: rgb(222, 49, 49);
    font-size: 22px;
    padding: 12px 25px;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    transition: background-color 0.3s ease;
  }
  
  .boton-volver:hover {
    background-color: #ff0000;
    color: white;
  }
  </style>
  