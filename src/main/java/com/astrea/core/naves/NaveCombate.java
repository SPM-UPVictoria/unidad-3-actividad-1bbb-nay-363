package com.astrea.core.naves;

import com.astrea.core.base.NaveEspacial;
import com.astrea.core.interfaces.Defendible;
import com.astrea.core.interfaces.Atacable;
import com.astrea.core.exceptions.AstreaException;
import com.astrea.core.exceptions.CombustibleInsuficienteException;
import com.astrea.core.exceptions.EscudoCriticoException;

public class NaveCombate extends NaveEspacial implements Defendible, Atacable {
	private static final double CONSUMO_ESTANDAR = 0.8;
    private static final double COSTO_HIPERVIAJE = 50.0;
    private static final double WARP_EXTREMO = 9.0;
    private static final double PROBABILIDAD_FALLO_WARP_EXTREMO = 0.30;
	
    private double integridadEscudo;
    private double potenciaArma;

    public NaveCombate(String matricula, String modelo, double combustibleInicial, double capacidadCombustible, double potenciaArma) throws AstreaException {
        super(matricula, modelo, combustibleInicial, capacidadCombustible);
        this.integridadEscudo = 100.0;
		this.potenciaArma = potenciaArma;
    }

    public double getIntegridadEscudo() {
        return integridadEscudo;
    }

    public double getPotenciaArma() {
        return potenciaArma;
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
    public void recibirImpacto(double potenciaDano) throws EscudoCriticoException {
        if (this.integridadEscudo <= 0.0) {
            throw new EscudoCriticoException(
                    "No es posible recibir el impacto: los escudos ya estan inhabilitados.");
        }

        this.integridadEscudo -= potenciaDano;

        if (this.integridadEscudo <= 0.0) {
            this.integridadEscudo = 0.0;
            throw new EscudoCriticoException(
                    "El escudo ha colapsado tras recibir " + potenciaDano + " unidades de dano.");
        }
		
		if (potenciaDano < 200.0) {
            throw new EscudoCriticoException(
                    "Irregularidad");
        }
    }

    @Override
    public void atacar(Defendible objetivo) throws AstreaException {
       
    }
}
