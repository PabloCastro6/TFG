<template>
    <div class="registro-container">
      <h2 class="titulo">📅 Crear Recordatorio</h2>
      <form class="formulario" @submit.prevent="guardarRecordatorio">
        <div class="campo">
          <label>Fecha:</label>
          <input v-model="recordatorio.fecha" type="date" :min="hoy" required />
        </div>
        <div class="campo">
          <label>Tipo:</label>
          <input v-model="recordatorio.tipo" placeholder="Ejemplo: Cita médica" type="text" required />
        </div>
        <div class="campo">
          <label>Cantidad:</label>
          <input v-model="recordatorio.cantidad" placeholder="Ejemplo: 2" type="number" min="1" required />
        </div>
        <button class="guardar-btn" type="submit">Guardar</button>
      </form>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        recordatorio: {
          fecha: "",
          tipo: "",
          cantidad: 1,
        },
        hoy: new Date().toISOString().split("T")[0],
      };
    },
    methods: {
      guardarRecordatorio() {
        console.log("Recordatorio guardado:", this.recordatorio);
        alert("Recordatorio guardado con éxito");
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
  