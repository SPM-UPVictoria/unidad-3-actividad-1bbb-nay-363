package com.astrea.core.naves;

import java.util.Random;

import com.astrea.core.base.NaveEspacial;
import com.astrea.core.interfaces.Propulsable;
import com.astrea.core.exceptions.AstreaException;
import com.astrea.core.exceptions.CombustibleInsuficienteException;
import com.astrea.core.exceptions.FallaSistemasException;

public class NaveExploracion extends NaveEspacial implements Propulsable {
	private static final double CONSUMO_ESTANDAR = 0.8;
    private static final double COSTO_HIPERVIAJE = 50.0;
    private static final double WARP_EXTREMO = 9.0;
    private static final double PROBABILIDAD_FALLO_WARP_EXTREMO = 0.30;
	
	private final Random random;
	
    private double integridadEscudo;
    private boolean hiperviajeListo;
	
	public NaveExploracion(String matricula, String modelo, double combustibleInicial,
                            double capacidadCombustible) throws AstreaException {
        this(matricula, modelo, combustibleInicial, capacidadCombustible, new Random());
    }

    public NaveExploracion(String matricula, String modelo, double combustibleInicial,
                            double capacidadCombustible, Random random) throws AstreaException {
        super(matricula, modelo, combustibleInicial, capacidadCombustible);
        this.integridadEscudo = 100.0;
        this.hiperviajeListo = true;
        this.random = random;
    }

    public double getIntegridadEscudo() {
        return integridadEscudo;
    }

    public boolean isHiperviajeListo() {
        return hiperviajeListo; 
    }

    @Override
    public void viajar(double distanciaAniosLuz) throws CombustibleInsuficienteException, AstreaException {
        if (distanciaAniosLuz <= 0) {
            throw new AstreaException("La distancia a viajar debe ser estrictamente positiva.");
        }

        double consumoTotal = CONSUMO_ESTANDAR * distanciaAniosLuz;

        if (consumoTotal > this.combustible) {
            throw new CombustibleInsuficienteException(
                    "Combustible insuficiente para viajar " + distanciaAniosLuz
                            + " anios luz. Requerido: " + consumoTotal
                            + ", disponible: " + this.combustible);
        }

        this.combustible -= consumoTotal;
    }

    @Override
    public void activarHiperviaje(double factorWarp) throws FallaSistemasException, CombustibleInsuficienteException {
        if (this.combustible < COSTO_HIPERVIAJE) {
            throw new CombustibleInsuficienteException(
                    "Combustible insuficiente para activar el hiperviaje. Requerido: "
                            + COSTO_HIPERVIAJE + ", disponible: " + this.combustible);
        }

        this.combustible -= COSTO_HIPERVIAJE;

        if (factorWarp > WARP_EXTREMO) {
            boolean fallaNucleo = random.nextDouble() < PROBABILIDAD_FALLO_WARP_EXTREMO;
            if (fallaNucleo) {
                this.hiperviajeListo = false;
                throw new FallaSistemasException(
                        "Fallo del nucleo de salto en warp extremo (factorWarp=" + factorWarp + ").");
            }
        }

        this.hiperviajeListo = true;
    }
	
	

}
