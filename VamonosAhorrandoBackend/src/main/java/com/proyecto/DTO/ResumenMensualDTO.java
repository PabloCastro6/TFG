package com.proyecto.DTO;

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
