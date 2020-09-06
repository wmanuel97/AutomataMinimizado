/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paneles;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;

/**
 *

 */
public class Evento {
    private Point2D punto1 = null, punto2 = null, puntoC1 = null, puntoC2 = null;
    ArrayList<String> lenguajes = new ArrayList<>();

    public Evento(Point2D punto1, Point2D punto2, Point2D puntoC1, Point2D puntoC2, String lenguaje) {
        setPunto1(punto1);
        setPunto2(punto2);
        setPuntoC1(puntoC1);
        setPuntoC2(puntoC2);
        insertarEvento(lenguaje);
    }
    
    public void pintar(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (getPuntoC1() == null && getPuntoC2() == null) {
            Line2D.Double linea = new Line2D.Double();
            linea.setLine(getPunto1(), getPunto2());
            g2.draw(linea);
            Line2D.Double[] lineas = Motor.getFlecha(getPunto1(), getPunto2());
            g2.draw(lineas[0]);
            g2.draw(lineas[1]);
            Point2D nuevo = Motor.getPuntoString(getPunto1(), getPunto2(), null, null);
            g2.drawString(Motor.getEventos(getLenguajes()), (float) nuevo.getX(), (float) nuevo.getY());
        }
        else if (getPuntoC2() == null) {
                QuadCurve2D.Double curva = new QuadCurve2D.Double();
                curva.setCurve(getPunto1(), getPuntoC1(), getPunto2());
                g2.draw(curva);
                Line2D.Double[] lineas = Motor.getFlecha(getPuntoC1(), getPunto2());
                g2.draw(lineas[0]);
                g2.draw(lineas[1]);
                Point2D nuevo = Motor.getPuntoString(getPunto1(), getPunto2(), getPuntoC1(), null);
                g2.drawString(Motor.getEventos(getLenguajes()), (float) nuevo.getX(), (float) nuevo.getY());
        }
        else {
                CubicCurve2D.Double curva = new CubicCurve2D.Double();
                curva.setCurve(getPunto1(), getPuntoC1(), getPuntoC2(), getPunto2());
                g2.draw(curva);
                Line2D.Double[] lineas = Motor.getFlecha(getPuntoC1(), getPunto2());
                g2.draw(lineas[0]);
                g2.draw(lineas[1]);
                Point2D nuevo = Motor.getPuntoString(getPunto1(), getPunto2(), getPuntoC1(), getPuntoC2());
                g2.drawString(Motor.getEventos(getLenguajes()), (float) nuevo.getX(), (float) nuevo.getY());
        }
    }
    
    private void insertarEvento(String evento) {
        getLenguajes().add(evento);
    }

    public Point2D getPunto1() {
        return punto1;
    }

    private void setPunto1(Point2D punto1) {
        this.punto1 = punto1;
    }

    public Point2D getPunto2() {
        return punto2;
    }

    private void setPunto2(Point2D punto2) {
        this.punto2 = punto2;
    }

    public Point2D getPuntoC1() {
        return puntoC1;
    }

    private void setPuntoC1(Point2D puntoC1) {
        this.puntoC1 = puntoC1;
    }

    public Point2D getPuntoC2() {
        return puntoC2;
    }

    private void setPuntoC2(Point2D puntoC2) {
        this.puntoC2 = puntoC2;
    }

    public ArrayList<String> getLenguajes() {
        return lenguajes;
    }

    public void setLenguajes(ArrayList<String> lenguajes) {
        this.lenguajes = lenguajes;
    }
}
