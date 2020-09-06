/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.ArrayList;

/**
 *
 *

 */
public class Automata <T> {
    private ArrayList<Nodo> estados = null;
    private ArrayList<T> lenguaje = null;

    public Automata() {
        setEstados(new ArrayList<>());
        setLenguaje(new ArrayList<>());
    }
    
    public void agregarEstado(T estado, boolean tipo) {
        getEstados().add(new Nodo(estado, tipo));
    }
    
    public Nodo obtenerEstado(T estado) {
        for (int i = 0; i < getEstados().size(); i++) {
            Nodo get = getEstados().get(i);
            if (get.getEstado().equals(estado))
                return get;
        }
        return null;
    }
    
    public int obtenerIndice(T estado) {
        for (int i = 0; i < getEstados().size(); i++) {
            Nodo get = getEstados().get(i);
            if (get.getEstado().equals(estado))
                return i;
        }
        return -1;
    }
    
    public void eliminarEstado(T estado) {
        if (obtenerEstado(estado) != null) {
            for (int i = 0; i < getEstados().size(); i++) {
                Nodo get1 = getEstados().get(i);
                for (int j = 0; j < get1.getNodos().size(); j++) {
                    Nodo get2 = (Nodo) get1.getNodos().get(j);
                    if (get2.getEstado().equals(estado)) {
                        get1.getNodos().remove(j);
                        --j;
                    }
                }
            }
            getEstados().remove(obtenerIndice(estado));
        }
    }
    
    public void asociarEstados(T estado1, T evento, T estado2) {
        int get1 = obtenerIndice(estado1);
        Nodo get2 = obtenerEstado(estado2);
        if (get1 != -1 && get2 != null)
            getEstados().get(get1).getNodos().add(new Nodo<>(estado2, evento, get2.getTipo(), get2.getNodos()));
    }
    
    public void disociarEstados(T estado1, T estado2) {
        Nodo get1 = obtenerEstado(estado1);
        for (int i = 0; i < get1.getNodos().size(); i++) {
            Nodo get2 = (Nodo) get1.getNodos().get(i);
            if (get2.getEstado().equals(estado2)) {
                get1.getNodos().remove(i);
                --i;
            }
        }
    }
    
    public void disociarEstados(T estado1, T evento, T estado2) {
        Nodo get1 = obtenerEstado(estado1);
        for (int i = 0; i < get1.getNodos().size(); i++) {
            Nodo get2 = (Nodo) get1.getNodos().get(i);
            if (get2.getEstado().equals(estado2) && get2.getEvento().equals(evento)) {
                get1.getNodos().remove(i);
                --i;
            }
        }
    }
    
    public void modificarEvento(T estado1, T estado2, T evento1, T evento2) {
        Nodo get1 = obtenerEstado(estado1);
        for (int i = 0; i < get1.getNodos().size(); i++) {
            Nodo get2 = (Nodo) get1.getNodos().get(i);
            if (get2.getEstado().equals(estado2) && get2.getEvento().equals(evento1)) {
                get1.getNodos().set(i, new Nodo<>(estado2, evento2, get2.getTipo(), get2.getNodos()));
                --i;
            }
        }
    }
    
    public void modificarTipo(T estado1, boolean tipo) {
        for (int i = 0; i < getEstados().size(); i++) {
            Nodo get1 = getEstados().get(i);
            if (estado1.equals(get1.getEstado())) {
                getEstados().set(i, new Nodo(estado1, get1.getEvento(), tipo, get1.getNodos()));
                break;
            }
        }
    }
    
    public boolean verificarAFD() { 
        for (int i = 0; i < getEstados().size(); i++) {
            Nodo get1 = getEstados().get(i);
            int cont = 0;
            for (int j = 0; j < get1.getNodos().size(); j++) {
                Nodo get2 = (Nodo) get1.getNodos().get(j);
                for (int k = 0; k < getLenguaje().size(); k++) {
                    if (get2.getEvento().equals(getLenguaje().get(k)))
                        cont++;
                }
            }
            if (cont != getLenguaje().size())
                return false;
        }
        return true;
    }
    
    public Automata optimizar() {
        if (verificarAFD()) {
            Object[][] contGrupos = new Object[getEstados().size()][2];
            Object[][] compTabla = new Object[getEstados().size() + 1][getLenguaje().size() + 1];
            for (int i = 0; i < getEstados().size(); i++) { 
                Nodo get1 = getEstados().get(i);
                if (get1.getTipo())
                    contGrupos[i][1] = "X";
                else
                    contGrupos[i][1] = "Y";
                contGrupos[i][0] = get1.getEstado();
            }
            for (int i = 1; i < compTabla.length; i++) 
                compTabla[i][0] = getEstados().get(i - 1).getEstado();
            for (int i = 1; i < compTabla[0].length; i++) 
                compTabla[0][i] = getLenguaje().get(i - 1);
            Object[][] contGruposAux = optimizar(contGrupos, compTabla, false); 
            Object[][] contGruposNue = comprimirGrupos(contGruposAux); 
            System.out.println("Grupos optmizados: \n" + imprimirMatriz(contGruposAux));
            System.out.println("Grupos comprimidos: \n" + imprimirMatriz(contGruposNue));
            compTabla = compararGrupos(contGruposAux, compTabla); 
            Object[][] compTablaNue = comprimirCompTabla(compTabla, contGruposNue.length); 
            System.out.println("Comparación grupos optmizados: \n" + imprimirMatriz(compTabla));
            System.out.println("Comparación grupos comprimidos: \n" + imprimirMatriz(compTablaNue));
            return getOptimizado(contGruposNue, contGrupos, compTablaNue);
        }
        return null;
    }    
    
    private Object[][] optimizar(Object[][] contGrupos, Object[][] compTabla, boolean fin) {
        if (fin)
            return contGrupos;
        else {
            compTabla = compararGrupos(contGrupos, compTabla);
            Object[][] contGruposAux = new Object[getEstados().size()][2];
            for (int i = 1; i < compTabla.length; i++) { 
                String grupo = (String) compTabla[i][1];
                for (int j = 2; j < compTabla[0].length; j++)
                    grupo += compTabla[i][j];
                contGruposAux[i - 1][0] = compTabla[i][0];
                contGruposAux[i - 1][1] = grupo;
            }
            System.out.println(imprimirMatriz(compTabla));
            System.out.println(imprimirMatriz(contGruposAux));
            for (int j = 0; j < getEstados().size() && !fin; j++) { 
                ArrayList<Object> grupos1 = new ArrayList<>();
                ArrayList<Object> grupos2 = new ArrayList<>();
                Nodo get2 = getEstados().get(j);
                Object grupo = new Object();
                for (Object[] contGrupo : contGrupos) { 
                    Object get3 = contGrupo[0];
                    if (get2.getEstado().equals(get3))
                        grupo = contGrupo[1];
                    if (grupo.equals(contGrupo[1]))
                        grupos1.add(get3);
                }
                grupo = new Object();
                for (Object[] contGrupo : contGruposAux) { 
                    Object get3 = contGrupo[0];
                    if (get2.getEstado().equals(get3))
                        grupo = contGrupo[1];
                    if (grupo.equals(contGrupo[1]))
                        grupos2.add(get3);
                }
                for (int k = 0; k < grupos1.size(); k++) {
                    Object get1 = grupos1.get(k);
                    for (int l = 0; l < grupos2.size(); l++) {
                        Object get3 = grupos2.get(l);
                        if (get1.equals(get3)) { 
                            grupos1.remove(k);
                            grupos2.remove(l);
                            --k;
                            break;
                        }
                    }
                }
                fin = grupos1.size() != grupos2.size(); 
            }
            if (!fin) 
                return optimizar(contGrupos, compTabla, !fin);
            else      
                return optimizar(contGruposAux, compTabla, !fin);
        }
    }
    
    private Object[][] comprimirGrupos(Object[][] contGrupos) {
        ArrayList<Object[]> grupos = new ArrayList<>();
        ArrayList<Object> gruposAux = new ArrayList<>();
        ArrayList<Object> miniGruposAux = new ArrayList<>();
        for (Object[] contGrupo : contGrupos) {
            Object get1 = contGrupo[1];
            boolean flag1 = false;            
            for (int j = 0; j < gruposAux.size(); j++) {
                Object get2 = gruposAux.get(j);
                if (get2.equals(get1)) {
                    flag1 = true;
                    break;
                }
            }
            ArrayList<Object> miniGrupos = new ArrayList<>();
            for (Object[] contGrupo1 : contGrupos) {
                Object get3 = contGrupo1[1];
                boolean flag2 = false;
                for (int i = 0; i < miniGruposAux.size(); i++) {
                    Object get4 = miniGruposAux.get(i);
                    if (get4.equals(contGrupo1[0])) {
                        flag2 = true;
                        break;
                    }
                }
                if (get3.equals(get1) && !flag2) {
                    miniGrupos.add(contGrupo1[0]);
                    miniGruposAux.add(contGrupo1[0]);
                }
            }
            if (!flag1) {
                Object[] nuevGrupo = {contGrupo[0], get1, miniGrupos};
                grupos.add(nuevGrupo);
                gruposAux.add(get1);
            }
        }
        Object[][] contGruposAux = new Object[grupos.size()][3];
        for (int i = 0; i < grupos.size(); i++) {
            Object[] get1 = grupos.get(i);         
            contGruposAux[i][0] = "q" + i;
            contGruposAux[i][1] = get1[1];
            contGruposAux[i][2] = get1[2];
        }
        return contGruposAux;
    }
    
    private Object[][] compararGrupos(Object[][] contGrupos, Object[][] compTabla) {
        for (int i = 0; i < getEstados().size(); i++) {
            Nodo get1 = getEstados().get(i);
            int k = -1;
            for (int p = 1; p < compTabla.length; p++) { 
                if (get1.getEstado().equals(compTabla[p][0])) {
                    k = p;
                    break;
                }
            }
            for (int j = 0; j < get1.getNodos().size(); j++) {
                Nodo get2 = (Nodo) get1.getNodos().get(j);
                int l = -1;
                for (int p = 1; p < compTabla[0].length; p++) { 
                    if (get2.getEvento().equals(compTabla[0][p])) {
                        l = p;
                        break;
                    }
                }
                Object grupo = null;
                for (Object[] contGrupo : contGrupos) { 
                    Object get3 = contGrupo[0];
                    if (get3.equals(get2.getEstado())) {
                        grupo = contGrupo[1];
                        break;
                    }
                }
                compTabla[k][l] = grupo; 
            }
        }
        return compTabla;
    }
    
    private Object[][] comprimirCompTabla(Object[][] compTabla, int n) {
        Object[][] compTablaAux = new Object[n + 1][getLenguaje().size() + 1];
        for (int i = 1; i < compTabla[0].length; i++) 
            compTablaAux[0][i] = getLenguaje().get(i - 1);
        ArrayList<Object> gruposAux = new ArrayList<>();
        for (int i = 1, k = 1; i < compTabla.length; i++) {
            String grupo = "";
            boolean flag = false;
            for (int j = 1; j < compTabla[0].length; j++)
                grupo += compTabla[i][j] + ";";
            for (int j = 0; j < gruposAux.size(); j++) {
                if (grupo.equals(gruposAux.get(j))) {
                    flag = true;
                    break;
                }
            }
            String grupoAux = "q" + (k - 1);
            String grupoOri = grupo;
            grupo = grupoAux + ";" + grupo;
            if (!flag) {
                compTablaAux[k] = grupo.split(";");
                gruposAux.add(grupoOri);
                k++;
            }
        }
        return compTablaAux;
    }
    
    private Automata getOptimizado(Object[][] contGrupos, Object[][] contGruposOri, Object[][] compTabla) {
        Automata optimizado = new Automata();
        optimizado.setLenguaje(getLenguaje());
        for (Object[] contGrupo : contGrupos) {
            Object get1 = contGrupo[0];
            ArrayList<Object> miniGrupos = (ArrayList<Object>) contGrupo[2];
            boolean flag = false;
            for (int i = 0; i < miniGrupos.size(); i++) {
                Object get2 = miniGrupos.get(i);
                for (Object[] contGruposOri1 : contGruposOri) {
                    Object get3 = contGruposOri1[1];
                    if (get3.equals("X") && contGruposOri1[0].equals(get2)) {
                        flag = true;
                        break;
                    }
                }
            }            
            optimizado.agregarEstado(get1, flag);
        }
        for (int i = 0; i < optimizado.getEstados().size(); i++) {
            Nodo get1 = (Nodo) optimizado.getEstados().get(i);
            for (int j = 1; j < compTabla.length; j++) {
                Object get2 = compTabla[j][0];
                if (get2.equals(get1.getEstado())) {
                    for (int k = 1, m = 1; k < compTabla[0].length; k++) {
                        Object get3 = compTabla[j][k];
                        for (Object[] contGrupo : contGrupos) {
                            Object get4 = contGrupo[1];
                            Object get5 = contGrupo[0];
                            if (get4.equals(get3)) {
                                optimizado.asociarEstados(get1.getEstado(), compTabla[0][m], get5);
                                ++m;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return optimizado;
    }
    
    public String[][] getTabla() {
        String[][] tabla = new String[getEstados().size() + 1][getEstados().size() + 1];
        for (int i = 0; i < getEstados().size(); i++) {
            Nodo get1 = getEstados().get(i);
            tabla[0][i + 1] = get1.getEstado() + ";" + get1.getTipo();
            tabla[i + 1][0] = get1.getEstado() + ";" + get1.getTipo();
        }
        for (int i = 1; i < tabla.length; i++) {
            String[] estado1 = tabla[i][0].split(";");
            Nodo get1 = obtenerEstado((T) estado1[0]);
            for (int j = 1; j < tabla[i].length; j++) {
                String[] estado2 = tabla[0][j].split(";");
                String eventos = "";
                for (int k = 0; k < get1.getNodos().size(); k++) {
                    Nodo get2 = (Nodo) get1.getNodos().get(k);
                    if (get2.getEstado().equals(estado2[0]))
                        eventos += (String) get2.getEvento() + ";";
                }
                tabla[i][j] = eventos;
            }
        }
        return tabla;
    }

    public String imprimirMatriz(Object[][] matriz) {
        String texto = "";
        for (Object[] get : matriz) {
            for (int j = 0; j < matriz[0].length; j++)
                texto += get[j] + "\t";
            texto += "\n";
        }
        return texto;
    }
    
    public String getStringLenguaje() {
        String texto = "";
        for (int i = 0; i < getLenguaje().size(); i++) {
            if (i < getLenguaje().size() - 1)
                texto += getLenguaje().get(i) + ",";
            else
                texto += getLenguaje().get(i);
        }
        return texto;
    }
    
    @Override
    public String toString() {
        String texto = "";
        for (int i = 0; i < getEstados().size(); i++) {
            Nodo get1 = getEstados().get(i);
            texto += get1.getEstado();
            for (int j = 0; j < get1.getNodos().size(); j++) {
                Nodo get2 = (Nodo) get1.getNodos().get(j);
                texto += "->" + get2.getEvento() + ":" + get2.getEstado();
            }
            texto += "\n";
        }
        return texto;
    }

    public ArrayList<Nodo> getEstados() {
        return estados;
    }

    private void setEstados(ArrayList<Nodo> estados) {
        this.estados = estados;
    }

    public ArrayList<T> getLenguaje() {
        return lenguaje;
    }

    private void setLenguaje(ArrayList<T> lenguaje) {
        this.lenguaje = lenguaje;
    }
}
