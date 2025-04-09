<template>
  <div class="estadisticas">
    <h1>📊 Estadísticas</h1>

    <!-- Selector de mes -->
    <input type="month" v-model="mesSeleccionado" @change="filtrarDatos" />

    <!-- Balance -->
    <div class="balance">
      <p><strong>💰 Ingresos:</strong> {{ totalIngresos }}€</p>
      <p><strong>🛒 Gastos:</strong> {{ totalGastos }}€</p>
      <p :style="{ color: balance >= 0 ? 'green' : 'red' }">
        <strong>📈 Balance:</strong> {{ balance }}€
      </p>
    </div>

    <!-- Gráfico -->
    <div class="grafico" style="height: 400px;">
      <BarChart :data="chartData" :options="chartOptions" />
    </div>
  </div>
</template>

<script>
import {
  Chart as ChartJS,
  BarElement,
  CategoryScale,
  LinearScale,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import { Bar } from "vue-chartjs";
import Swal from "sweetalert2";

ChartJS.register(BarElement, CategoryScale, LinearScale, Title, Tooltip, Legend);

export default {
  name: "Estadisticas",
  components: {
    BarChart: Bar, // Usamos Bar directamente
  },
  data() {
    return {
      transacciones: [],
      mesSeleccionado: "",
      totalIngresos: 0,
      totalGastos: 0,
      balance: 0,
      chartData: {
        labels: ["Ingresos", "Gastos"],
        datasets: [
          {
            label: "Total (€)",
            backgroundColor: ["#27ae60", "#c0392b"],
            data: [0, 0],
          },
        ],
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            display: true,
          },
          title: {
            display: true,
            text: "Ingresos vs Gastos",
          },
        },
      },
    };
  },
  methods: {
    async obtenerTransacciones() {
      try {
        const response = await fetch("http://localhost:8080/transacciones");
        this.transacciones = await response.json();
      } catch (error) {
        console.error("❌ Error al cargar transacciones:", error);
      }
    },
    filtrarDatos() {
      if (!this.mesSeleccionado) return;

      const userId = localStorage.getItem("userId");
      if (!userId) {
        console.warn("⚠️ No se encontró el ID de usuario en localStorage");
        return;
      }

      const [anioStr, mesStr] = this.mesSeleccionado.split("-");
      const anio = parseInt(anioStr);
      const mes = parseInt(mesStr);

      const transaccionesMesUsuario = this.transacciones.filter((t) => {
        if (!t.fecha || !t.usuario || t.usuario.idUsuario != userId) return false;

        const [yearStr, monthStr] = t.fecha.split("-");
        return parseInt(yearStr) === anio && parseInt(monthStr) === mes;
      });

      if (transaccionesMesUsuario.length === 0) {
        Swal.fire({
          icon: "info",
          title: "Sin transacciones",
          text: "No se encontraron ingresos ni gastos en el mes seleccionado.",
          confirmButtonText: "Entendido",
          customClass: {
            confirmButton: "miBotonCancelar",
          },
        });
      }

      const ingresos = transaccionesMesUsuario.filter(
        (t) => t.categoria?.nombre.toLowerCase() === "ingreso"
      );
      const gastos = transaccionesMesUsuario.filter(
        (t) => t.categoria?.nombre.toLowerCase() === "gasto"
      );

      this.totalIngresos = ingresos.reduce((sum, t) => sum + t.cantidad, 0);
      this.totalGastos = gastos.reduce((sum, t) => sum + t.cantidad, 0);
      this.balance = this.totalIngresos - this.totalGastos;

      this.chartData = {
        labels: ["Ingresos", "Gastos"],
        datasets: [
          {
            label: "Total (€)",
            backgroundColor: ["#27ae60", "#c0392b"],
            data: [this.totalIngresos, this.totalGastos],
          },
        ],
      };
    },
  },
  mounted() {
    this.obtenerTransacciones();
  },
};
</script>

<style scoped>
.estadisticas {
  padding: 2rem;
  max-width: 800px;
  margin: auto;
}

input[type="month"] {
  margin-bottom: 1rem;
  padding: 0.5rem;
  font-size: 1rem;
}

.balance {
  margin: 1rem 0;
  font-size: 1.1rem;
}

.grafico {
  margin-top: 2rem;
}
</style>
