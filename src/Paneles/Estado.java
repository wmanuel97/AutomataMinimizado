/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paneles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 *

 */
public class Estado {
    private int x = 0, y = 0;
    private String nombre = "";
    private boolean tipo = false;
    public static final int d = 60;
    boolean s = false;

    public Estado(int x, int y, String nombre, boolean tipo, boolean s) {
        setX(x);
        setY(y);
        setNombre(nombre);
        setTipo(tipo);
        setS(s);
    }
    
    public void pintar(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(Color.LIGHT_GRAY);
        g.fillOval(getX(), getY(), d, d);
        g.setColor(Color.BLACK);
        g.drawString(getNombre(), getX() + d/2, getY() + d/2);
        g.setColor(Color.BLACK);
        g.drawOval(getX(), getY(), d, d);
        if (getTipo())
            g.drawOval(getX() + 6, getY() + 6, d - 12, d - 12);
        if (getS()) {
            Line2D.Double[] lineas = Motor.getTriangulo(new Point2D.Double(getX() + (d/2), getY() + (d/2)));
            g2.draw(lineas[0]);
            g2.draw(lineas[1]);
            g2.draw(lineas[2]);
        }
    }

    public int getX() {
        return x;
    }

    private void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public String getNombre() {
        return nombre;
    }

    private void setNombre(String nombre) {
        this.nombre = nombre;
    }

    private void setY(int y) {
        this.y = y;
    }

    public boolean getTipo() {
        return tipo;
    }

    private void setTipo(boolean tipo) {
        this.tipo = tipo;
    }

    public boolean getS() {
        return s;
    }

    private void setS(boolean s) {
        this.s = s;
    }
}
