/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Clases.Automata;
import java.util.Arrays;

/**
 *

 */
public class Control {
    private Automata auOri = new Automata();
    private Automata auOpt = new Automata();
    
    public void crearAutomata(String[] lenguaje) {
        setAuOri(new Automata());
        getAuOri().getLenguaje().addAll(Arrays.asList(lenguaje));
    }
          
    public void agregarEstado(String estado, boolean tipo) {
        getAuOri().agregarEstado(estado, tipo);
    }
    
    public void eliminarEstado(String estado) {
        getAuOri().eliminarEstado(estado);
    }
    
    public void asociarEstado(String estado1, String evento, String estado2) {
        getAuOri().asociarEstados(estado1, evento, estado2);
    }
    
    public void modificarTipo(String estado, boolean tipo) {
        getAuOri().modificarTipo(estado, tipo);
    }
    
    public boolean buscarEstado(String estado) {
        return getAuOri().obtenerIndice(estado) != -1;
    }
    
    public void borrarAutomata() {
        setAuOri(new Automata());
        setAuOpt(new Automata());
    }
    
    public void optimizarAutomata() {
        setAuOpt(getAuOri().optimizar());
    }
    
    public String imprimirAutomata(int tipo) {
        if (tipo == 1)
            return getAuOri().toString();
        return getAuOpt().toString();
    }
    
    public boolean isAFD() {
        return getAuOri().verificarAFD();
    }
    
    public String[] getLenguaje(int tipo) {
        if (tipo == 1)
            return getAuOri().getStringLenguaje().split(",");
        return getAuOpt().getStringLenguaje().split(",");
    }
    
    public String getLenguaje() {
        return getAuOri().getStringLenguaje();
    }
    
    public String[][] getTabla() {
        if (getAuOpt() != null)
            return getAuOpt().getTabla();
        else
            return new String[0][0];
    }
    
    public boolean esVacio() {
        return getAuOri().getEstados().isEmpty();
    }

    public Automata getAuOri() {
        return auOri;
    }

    public void setAuOri(Automata auOri) {
        this.auOri = auOri;
    }

    public Automata getAuOpt() {
        return auOpt;
    }

    public void setAuOpt(Automata auOpt) {
        this.auOpt = auOpt;
    }
}
