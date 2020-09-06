/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.ArrayList;

/**
 * @param <T>
 */
public class Nodo <T> {
    private T estado = null;
    private T evento = null;
    private boolean tipo = false;
    private ArrayList<T> nodos = null;

    public Nodo(T estado, boolean tipo) {
        setEstado(estado);
        setTipo(tipo);
        setNodos(new ArrayList<>());
    }

    public Nodo(T estado, T evento, boolean tipo, ArrayList<T> nodos) {
        setEstado(estado);
        setEvento(evento);
        setTipo(tipo);
        setNodos(nodos);
    }

    public T getEstado() {
        return estado;
    }

    private void setEstado(T estado) {
        this.estado = estado;
    }

    public T getEvento() {
        return evento;
    }

    private void setEvento(T evento) {
        this.evento = evento;
    }

    public boolean getTipo() {
        return tipo;
    }

    private void setTipo(boolean tipo) {
        this.tipo = tipo;
    }

    public ArrayList<T> getNodos() {
        return nodos;
    }

    private void setNodos(ArrayList<T> nodos) {
        this.nodos = nodos;
    }
}
