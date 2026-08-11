package com.astrea.core.naves;

import com.astrea.core.base.NaveEspacial;
import com.astrea.core.exceptions.AstreaException;
import com.astrea.core.exceptions.CombustibleInsuficienteException;

public class NaveCarga extends NaveEspacial {
	private static final double CONSUMO_ESTANDAR = 1.5;
    private static final double CONSUMO_SOBRECARGA = 3.0;
	
    private double cargaActual;
    private double cargaMaxima;

    public NaveCarga(String matricula, String modelo, double combustibleInicial, 
						double capacidadCombustible, double cargaMaxima) throws AstreaException {
        super(matricula, modelo, combustibleInicial, capacidadCombustible);
        if (cargaMaxima <= 0) {
            throw new AstreaException("La carga maxima debe ser estrictamente positiva.");
        }
        if (cargaActual < 0 || cargaActual > cargaMaxima) {
            throw new AstreaException("La carga actual debe estar entre 0 y la carga maxima.");
        }

        this.cargaActual = cargaActual;
        this.cargaMaxima = cargaMaxima;
    }
	
	public boolean estaSobrecargada() {
        return this.cargaActual > (this.cargaMaxima * 0.5);
    }

    public void cargar(double cantidad) throws AstreaException {
		
    }

    public double getCargaActual() {
        return cargaActual;
    }

    public double getCargaMaxima() {
        return cargaMaxima;
    }

    @Override
    public void viajar(double distanciaAniosLuz) throws CombustibleInsuficienteException, AstreaException {
        if (distanciaAniosLuz <= 0) {
            throw new AstreaException("La distancia a viajar debe ser estrictamente positiva.");
        }
		
		double consumoPorAnioLuz = estaSobrecargada()? CONSUMO_SOBRECARGA : CONSUMO_ESTANDAR;
        double consumoTotal = consumoPorAnioLuz * distanciaAniosLuz;
		
		if (consumoTotal > this.combustible) {
            // Atomicidad: el estado de combustible no se altera ante la falla.
            throw new CombustibleInsuficienteException(
                    "Combustible insuficiente para viajar " + distanciaAniosLuz
                            + " anios luz. Requerido: " + consumoTotal
                            + ", disponible: " + this.combustible);
        }
		this.combustible -= consumoTotal;
    }
}
