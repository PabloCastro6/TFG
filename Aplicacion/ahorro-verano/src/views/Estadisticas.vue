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
        <strong>📈 Balance mensual:</strong> {{ balance }}€
      </p>
    </div>

    <!-- Media anual -->
    <div class="balance">
      <p><strong>📆 Media anual de ingresos:</strong> {{ mediaIngresosAnual.toFixed(2) }}€</p>
      <p><strong>📆 Media anual de gastos:</strong> {{ mediaGastosAnual.toFixed(2) }}€</p>
      <p :style="{ color: balanceAnual >= 0 ? 'green' : 'red' }">
        <strong>📊 Balance anual:</strong> {{ balanceAnual.toFixed(2) }}€
      </p>
    </div>

    <!-- Gráfico -->
    <div class="grafico" style="height: 400px">
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

ChartJS.register(
  BarElement,
  CategoryScale,
  LinearScale,
  Title,
  Tooltip,
  Legend
);

export default {
  name: "Estadisticas",
  components: {
    BarChart: Bar,
  },
  data() {
    return {
      transacciones: [],
      mesSeleccionado: "",
      totalIngresos: 0,
      totalGastos: 0,
      balance: 0,
      balanceAnual: 0,
      mediaIngresosAnual: 0,
      mediaGastosAnual: 0,
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
            labels: {
              color: '#ffffff',
              font: {
                size: 14,
                family: 'Glaure',
                weight: 'normal'
              }
            }
          },
          title: {
            display: true,
            text: "Ingresos vs Gastos",
            color: '#ffffff',
            font: {
              size: 18,
              family: 'Glaure',
              weight: 'normal'
            }
          },
          tooltip: {
            backgroundColor: '#2c3e50',
            titleColor: '#ffffff',
            bodyColor: '#ecf0f1',
          }
        },
        scales: {
          x: {
            ticks: {
              color: '#ffffff',
              font: {
                size: 13,
                family: 'Glaure',
                weight: 'normal'              }
            },
            grid: {
              color: '#444444'
            }
          },
          y: {
            ticks: {
              color: '#ffffff',
              font: {
                size: 13,
                family: 'Arial'
              }
            },
            grid: {
              color: '#444444'
            }
          }
        }
      }

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

      // Transacciones del mes seleccionado
      const transaccionesMesUsuario = this.transacciones.filter((t) => {
        if (!t.fecha || !t.usuario || t.usuario.idUsuario != userId)
          return false;

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

      // Calcular medias anuales
      const transaccionesAnioUsuario = this.transacciones.filter((t) => {
        if (!t.fecha || !t.usuario || t.usuario.idUsuario != userId)
          return false;
        const [yearStr] = t.fecha.split("-");
        return parseInt(yearStr) === anio;
      });

      const ingresosAnuales = transaccionesAnioUsuario.filter(
        (t) => t.categoria?.nombre.toLowerCase() === "ingreso"
      );
      const gastosAnuales = transaccionesAnioUsuario.filter(
        (t) => t.categoria?.nombre.toLowerCase() === "gasto"
      );

      const sumaIngresosAnual = ingresosAnuales.reduce((sum, t) => sum + t.cantidad, 0);
      const sumaGastosAnual = gastosAnuales.reduce((sum, t) => sum + t.cantidad, 0);

      this.mediaIngresosAnual = sumaIngresosAnual / 12;
      this.mediaGastosAnual = sumaGastosAnual / 12;

      this.balanceAnual = this.mediaIngresosAnual - this.mediaGastosAnual;

      // Actualizar gráfico
      this.chartData = {
        labels: ["Ingresos", "Gastos"],
        datasets: [
          {
            label: "Total (€)",
            backgroundColor: ["#27ae60", "#c0392b"],
            data: [this.totalIngresos, this.totalGastos],
            barThickness: 40,
            maxBarThickness: 50,
            categoryPercentage: 0.6,
            barPercentage: 0.7,
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
  background-color: #000000;
  border-radius: 12px;
  padding: 1rem;
}
</style>
