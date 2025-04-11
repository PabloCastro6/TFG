package com.proyecto.DTO;


/**
 * DTO que representa un resumen mensual de transacciones económicas.
 * 
 * Este objeto se utiliza para agrupar los ingresos, gastos y el saldo resultante
 * de un mes determinado. El saldo se calcula automáticamente en el constructor
 * como la diferencia entre ingresos y gastos.
 * 
 * <p>Se usa normalmente para mostrar un resumen financiero mensual en la interfaz
 * de usuario o en las estadísticas del usuario.</p>
 */

public class ResumenMensualDTO {

	private double ingresos;
    private double gastos;
    private double saldo;

    public ResumenMensualDTO(double ingresos, double gastos) {
        this.ingresos = ingresos;
        this.gastos = gastos;
        this.saldo = ingresos - gastos;
    }

    public double getIngresos() { return ingresos; }
    public double getGastos() { return gastos; }
    public double getSaldo() { return saldo; }

}
