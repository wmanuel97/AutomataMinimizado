/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paneles;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *

 */
public class Motor {
    public static boolean validarLienzo(Point nuevo, Rectangle lienzo) {
        return lienzo.contains(nuevo.getX() - Estado.d / 2, nuevo.getY() - Estado.d / 2, Estado.d, Estado.d);
    }
    public static int buscarEstado(ArrayList<Estado> estados, Point punto) {
        for (int i = 0; i < estados.size(); i++) {
            Estado estado = estados.get(i);
            Rectangle auxCirculo = new Rectangle(estado.getX(), estado.getY(), estado.d, estado.d);
            if (auxCirculo.contains(punto))
                return i;
        }
        return -1;
    }
    
    public static boolean validarSuperposicion(ArrayList<Estado> estados, Point nuevo, int indice) {
        for (int i = 0; i < estados.size(); i++) {
            Estado estado = estados.get(i);
            Rectangle viejo = new Rectangle(estado.getX(), estado.getY(), 60, 60);
            if (viejo.intersects(nuevo.getX() - Estado.d/2, nuevo.getY() - Estado.d/2, Estado.d, Estado.d) && i != indice)
                return false;
        }
        return true;
    }
    
    public static int buscarEstado(ArrayList<Estado> estados, String nombre) {
        for (int i = 0; i < estados.size(); i++) {
            Estado estado = estados.get(i);
            if (estado.getNombre().equals(nombre))
                return i;
        }
        return -1;
    }
    public static Object[] moverEstado(ArrayList<Estado> estados, ArrayList<Evento> eventos, Point2D nuevo, int indice) {
        Object[] listaMixta = new Object[2];
        Estado estado = estados.get(indice);
        Point2D viejo = new Point2D.Double(estado.getX() + 30, estado.getY() + 30);
        estados.set(indice, new Estado((int) nuevo.getX() - estado.d/2, (int) nuevo.getY() - estado.d/2, estado.getNombre(), estado.getTipo(), estado.getS()));
        for (int i = 0; i < eventos.size(); i++) {
            Evento evento = eventos.get(i);
            ArrayList<String> lenguajes = evento.getLenguajes();
            if (evento.getPunto1().equals(viejo)) {
                if (evento.getPuntoC1() == null && evento.getPuntoC2() == null) 
                    eventos.set(i, getUnion(nuevo, evento.getPunto2(), "", 1));
                else if (evento.getPuntoC2() == null) 
                    eventos.set(i, getUnion(nuevo, evento.getPunto2(), "", 2));
                else
                    eventos.set(i, getUnion(nuevo, nuevo, "", 3));
            }
            else if (evento.getPunto2().equals(viejo)) {
                if (evento.getPuntoC1() == null && evento.getPuntoC2() == null) 
                    eventos.set(i, getUnion(evento.getPunto1(), nuevo, "", 1));
                else if (evento.getPuntoC2() == null) 
                    eventos.set(i, getUnion(evento.getPunto1(), nuevo, "", 2));
                else
                    eventos.set(i, getUnion(nuevo, nuevo, "", 2));
            }
            eventos.get(i).setLenguajes(lenguajes);
        }
        listaMixta[0] = estados;
        listaMixta[1] = eventos;
        return listaMixta;
    }

    public static ArrayList<Evento> generarEvento(ArrayList<Evento> eventos, Point2D socio1, Point2D socio2, String lenguaje) {
        Object[] evento1 = buscarEvento(eventos, socio1, socio2);
        Object[] evento2 = buscarEvento(eventos, socio2, socio1);
        if (socio1.equals(socio2)) {    
            if (Boolean.parseBoolean((String) evento1[2])) {
                Evento eventoAux = (Evento) evento1[1];
                eventoAux.getLenguajes().add(lenguaje);
                eventos.set(Integer.parseInt((String) evento1[0]), eventoAux);
            } else {
                Evento eventoAux = getUnion(socio1, socio2, lenguaje, 3);
                eventos.add(eventoAux);
            }
        }
        else if(Boolean.parseBoolean((String) evento1[2]) && Boolean.parseBoolean((String) evento2[2])) {
            Evento eventoAux = (Evento) evento1[1];
            eventoAux.getLenguajes().add(lenguaje);
            eventos.set(Integer.parseInt((String) evento1[0]), eventoAux);
        }
        else if (Boolean.parseBoolean((String) evento2[2])) {
            Evento eventoAux = getUnion(socio1, socio2, lenguaje, 2);
            eventos.add(eventoAux);
        }
        else if (Boolean.parseBoolean((String) evento1[2])) {
            Evento eventoAux = (Evento) evento1[1];
            eventoAux.getLenguajes().add(lenguaje);
            eventos.set(Integer.parseInt((String) evento1[0]), eventoAux);
        }
        else {
            Evento eventoAux = getUnion(socio1, socio2, lenguaje, 1);
            eventos.add(eventoAux);
        }
        return eventos;
    }
    
    public static Object[] buscarEvento(ArrayList<Evento> eventos, Point2D socio1, Point2D socio2) {
        Object[] eventoAux = new Object[3];
        eventoAux[2] = "false";
        for (int i = 0; i < eventos.size(); i++) {
            Evento evento = eventos.get(i);
            if (evento.getPunto1().equals(socio1) && evento.getPunto2().equals(socio2)) {
                eventoAux[0] = Integer.toString(i);
                eventoAux[1] = evento;
                eventoAux[2] = "true";
                return eventoAux;
            }
        }
        return eventoAux;
    }
    
    public static Object[] buscarEvento(ArrayList<Evento> eventos, Point2D socio1) {
        Object[] eventoAux = new Object[3];
        eventoAux[2] = "false";
        for (int i = 0; i < eventos.size(); i++) {
            Evento evento = eventos.get(i);
            if (evento.getPunto1().equals(socio1) || evento.getPunto2().equals(socio1)) {
                eventoAux[0] = Integer.toString(i);
                eventoAux[1] = evento;
                eventoAux[2] = "true";
                return eventoAux;
            }
        }
        return eventoAux;
    }
    
    public static Object[] eliminarEstado(ArrayList<Estado> estados, ArrayList<Evento> eventos, String estado) {
        Object[] listaMixta = new Object[2];
        int j = buscarEstado(estados, estado);
        Estado get0 = estados.get(j);
        for (int i = 0; i < eventos.size(); i++) {
            Object[] evento = buscarEvento(eventos, new Point2D.Double(get0.getX() + 30, get0.getY() + 30));
            if (Boolean.parseBoolean((String) evento[2])) {
                eventos.remove(Integer.parseInt((String) evento[0]));
                --i;
            }
        }
        estados.remove(j);
        listaMixta[0] = estados;
        listaMixta[1] = eventos;
        return listaMixta;
    }
    
    public static Evento getUnion(Point2D socio1, Point2D socio2, String lenguaje, int tipo) {
        switch (tipo) {
            case 1:
                return new Evento(socio1, socio2, null, null, lenguaje);
            case 2:
                double x = 0, y = 0, lx = 0, ly = 0, m = 0;
                lx = Math.abs(socio2.getX() - socio1.getX());
                ly = Math.abs(socio2.getY() - socio1.getY());
                if (lx != 0)
                    m = Math.abs(socio2.getY() - socio1.getY())/(socio2.getX() - socio1.getX());
                if (m != 0 && m != 1 && m != - 1 && lx > 50 && ly > 50) {
                    if (lx > ly) {                        
                        if (socio1.getX() < socio2.getX() && socio2.getY() < socio1.getY()) {
                            x = socio1.getX() + (lx/2) - ((ly/2)*m);
                            y = socio2.getY();
                        } 
                        else if (socio1.getX() < socio2.getX() && socio2.getY() > socio1.getY()) {
                                x = socio2.getX() - (lx/2) + ((ly/2)*m);
                                y = socio1.getY();
                        }
                        else if (socio2.getX() < socio1.getX() && socio1.getY() < socio2.getY()) {
                                x = socio2.getX() + (lx/2) + ((ly/2)*m);
                                y = socio1.getY();
                        } 
                        else if (socio2.getX() < socio1.getX() && socio1.getY() > socio2.getY()) {
                                x = socio1.getX() - (lx/2) - ((ly/2)*m);
                                y = socio2.getY();
                        }
                    } else if (lx < ly) {
                        double m1 = 1/m;
                        if (socio1.getX() < socio2.getX() && socio2.getY() < socio1.getY()) {
                            x = socio1.getX();
                            y = socio1.getY() - (ly/2) - ((lx/2)*m1);
                        } 
                        else if (socio1.getX() < socio2.getX() && socio2.getY() > socio1.getY()) {
                                x = socio2.getX();
                                y = socio2.getY() - (ly/2) - ((lx/2)*m1);
                        }
                        else if (socio2.getX() < socio1.getX() && socio1.getY() < socio2.getY()) {
                                x = socio2.getX();
                                y = socio2.getY() - (ly/2) + ((lx/2)*m1);
                        } 
                        else if (socio2.getX() < socio1.getX() && socio1.getY() > socio2.getY()) {
                                x = socio1.getX();
                                y = socio1.getY() - (ly/2) + ((lx/2)*m1);
                        }
                    }
                }
                else if (m == 0 || lx <= 50 || ly <= 50) {
                    if (ly <= 50) {
                        if (socio1.getX() < socio2.getX()) {
                            x = socio1.getX() + (lx/2);
                            y = socio2.getY() - (lx/2);
                        }
                        else if (socio2.getX() < socio1.getX()) {
                                x = socio1.getX() - (lx/2);
                                y = socio2.getY() - (lx/2);
                        }
                    }
                    else if (lx <= 50) {
                        if (socio1.getY() < socio2.getY()) {
                            x = socio1.getX() + (ly/2);
                            y = socio2.getY() - (ly/2);
                        }
                        else if (socio2.getY() < socio1.getY()) {
                                x = socio1.getX() + (ly/2);
                                y = socio2.getY() + (ly/2);
                        }
                    }
                }
                else if (m == -1 || m == 1) {
                    if (socio1.getX() < socio2.getX() && socio2.getY() < socio1.getY()) {
                            x = socio1.getX();
                            y = socio2.getY();
                        } 
                        else if (socio1.getX() < socio2.getX() && socio2.getY() > socio1.getY()) {
                                x = socio2.getX();
                                y = socio1.getY();
                        }
                        else if (socio2.getX() < socio1.getX() && socio1.getY() < socio2.getY()) {
                                x = socio2.getX();
                                y = socio1.getY();
                        } 
                        else if (socio2.getX() < socio1.getX() && socio1.getY() > socio2.getY()) {
                                x = socio1.getX();
                                y = socio2.getY();
                        }
                }
                return new Evento(socio1, socio2, new Point2D.Double(x, y), null, lenguaje);
            default:
                return new Evento(socio1, socio2, new Point2D.Double(socio1.getX() + 80, socio1.getY() - 80), new Point2D.Double(socio1.getX() - 80, socio1.getY() - 80), lenguaje);
        }
    }
    
    public static Line2D.Double[] getFlecha(Point2D punto1, Point2D punto2) {
        double angulo = Math.atan(Math.abs(punto2.getY() - punto1.getY()) / Math.abs(punto2.getX() - punto1.getX()));
        double x = 0;
        double y = 0;
        double x1 = 0, y1 = 0, x2 = 0, y2 = 0;
        if (punto2.getX() <= punto1.getX() && punto1.getY() <= punto2.getY()) {
            x = punto2.getX() + (Estado.d/2)*Math.cos(angulo);
            y = punto2.getY() - (Estado.d/2)*Math.sin(angulo);
            x1 = x + 10*Math.cos (angulo - Math.toRadians (25));
            y1 = y - 10*Math.sin (angulo - Math.toRadians (25));
            x2 = x + 10*Math.cos (angulo + Math.toRadians (25));
            y2 = y - 10*Math.sin (angulo + Math.toRadians (25));
        }
        else if (punto2.getX() >= punto1.getX() && punto1.getY() <= punto2.getY()) {
                x = punto2.getX() - (Estado.d/2)*Math.cos(angulo);
                y = punto2.getY() - (Estado.d/2)*Math.sin(angulo);
                x1 = x - 10*Math.cos (angulo - Math.toRadians (25));
                y1 = y - 10*Math.sin (angulo - Math.toRadians (25));
                x2 = x - 10*Math.cos (angulo + Math.toRadians (25));
                y2 = y - 10*Math.sin (angulo + Math.toRadians (25));
        }
        else if (punto2.getX() >= punto1.getX() && punto1.getY() >= punto2.getY()) {
                x = punto2.getX() - (Estado.d/2)*Math.cos(angulo);
                y = punto2.getY() + (Estado.d/2)*Math.sin(angulo);
                x1 = x - 10*Math.cos (angulo - Math.toRadians (25));
                y1 = y + 10*Math.sin (angulo - Math.toRadians (25));
                x2 = x - 10*Math.cos (angulo + Math.toRadians (25));
                y2 = y + 10*Math.sin (angulo + Math.toRadians (25));
        }
        else if (punto2.getX() <= punto1.getX() && punto1.getY() >= punto2.getY()) {
                x = punto2.getX() + (Estado.d/2)*Math.cos(angulo);
                y = punto2.getY() + (Estado.d/2)*Math.sin(angulo);
                x1 = x + 10*Math.cos (angulo - Math.toRadians (25));
                y1 = y + 10*Math.sin (angulo - Math.toRadians (25));
                x2 = x + 10*Math.cos (angulo + Math.toRadians (25));
                y2 = y + 10*Math.sin (angulo + Math.toRadians (25));
        }
        Line2D.Double linea1 = new Line2D.Double(x, y, x1, y1);
        Line2D.Double linea2 = new Line2D.Double(x, y, x2, y2);
        Line2D.Double[] lineas = {linea1, linea2};
        return lineas;
    }
    
    public static String getEventos(ArrayList<String> eventos) {
        String texto = "";
        for (int i = 0; i < eventos.size(); i++) {
            if (i < eventos.size() - 1)    
                texto += eventos.get(i) + ",";
            else
                texto += eventos.get(i);
        }
        return texto;
    }
    
    public static Line2D.Double[] getTriangulo(Point2D punto) {
        double x = 0, y = 0, x1 = 0, y1 = 0, x2 = 0, y2 = 0, x3 = 0, y3 = 3;
        x = punto.getX() - (Estado.d/2);
        y = punto.getY();
        x1 = x - 25*Math.cos (Math.toRadians (25));
        y1 = y - 25*Math.sin (Math.toRadians (25));
        x2 = x - 25*Math.cos (Math.toRadians (25));
        y2 = y + 25*Math.sin (Math.toRadians (25));
        x3 = x1;
        y3 = y1;
        Line2D.Double linea1 = new Line2D.Double(x, y, x1, y1);
        Line2D.Double linea2 = new Line2D.Double(x, y, x2, y2);
        Line2D.Double linea3 = new Line2D.Double(x2, y2, x3, y3);
        Line2D.Double[] lineas = {linea1, linea2, linea3};
        return lineas;
    }
    
    @SuppressWarnings("null")
    public static Point2D getPuntoString(Point2D socio1, Point2D socio2, Point2D puntoC1, Point2D puntoC2) {
        Point2D nuevo = new Point2D.Float();
        if (puntoC1 == null && puntoC2 == null)
            nuevo.setLocation((float) (socio1.getX() + socio2.getX())/2, (float) (socio1.getY() + socio2.getY())/2);
        else if (puntoC2 == null) {
                double x = 0, y = 0, lx = 0, ly = 0;
                double m = Math.abs(socio2.getY() - socio1.getY())/(socio2.getX() - socio1.getX());
                lx = Math.abs(socio2.getX() - socio1.getX());
                ly = Math.abs(socio2.getY() - socio1.getY());
                x = puntoC1.getX();
                y = puntoC1.getY();
                if (m != 0 && m != 1 && m != - 1 && lx > 50 && ly > 50) {
                    if (lx > ly) {      
                        if (socio1.getX() < socio2.getX() && socio2.getY() < socio1.getY()) { //
                            x = x + ly*(m/4);
                            y = y + ly*(m/4);
                        } 
                        else if (socio1.getX() < socio2.getX() && socio2.getY() > socio1.getY()) {
                                x = x - ly*(m/4);
                                y = y + ly*(m/4);
                        }
                        else if (socio2.getX() < socio1.getX() && socio1.getY() < socio2.getY()) {
                                x = x - ly*(m/4);
                                y = y - ly*(m/4);
                        } 
                        else if (socio2.getX() < socio1.getX() && socio1.getY() > socio2.getY()) { //
                                x = x + ly*(m/4);
                                y = y - ly*(m/4);
                        }
                    } else if (lx < ly) {
                        double m1 = 1/m;
                        if (socio1.getX() < socio2.getX() && socio2.getY() < socio1.getY()) {
                            x = x + lx*(m1/4);
                            y = y + lx*(m1/4);
                        } 
                        else if (socio1.getX() < socio2.getX() && socio2.getY() > socio1.getY()) {
                                x = x - lx*(m1/4);
                                y = y + lx*(m1/4);
                        }
                        else if (socio2.getX() < socio1.getX() && socio1.getY() < socio2.getY()) {
                                x = x - lx*(m1/4);
                                y = y - lx*(m1/4);
                        } 
                        else if (socio2.getX() < socio1.getX() && socio1.getY() > socio2.getY()) {
                                x = x + lx*(m1/4);
                                y = y - lx*(m1/4);
                        }
                    }
                }
                else if (m == 0 || lx <= 50 || ly <= 50) {
                    if (ly <= 50) 
                        y = y + (lx/4);
                    else if (lx <= 50)
                            x = x - (ly/4);
                }
                else if (m == -1 || m == 1) {
                    if (socio1.getX() < socio2.getX() && socio2.getY() < socio1.getY()) {
                            x = x + (lx/4);
                            y = y + (lx/4);
                        } 
                        else if (socio1.getX() < socio2.getX() && socio2.getY() > socio1.getY()) {
                                x = x - (lx/4);
                                y = y + (lx/4);
                        }
                        else if (socio2.getX() < socio1.getX() && socio1.getY() < socio2.getY()) {
                                x = x + (lx/4);
                                y = y + (lx/4);
                        } 
                        else if (socio2.getX() < socio1.getX() && socio1.getY() > socio2.getY()) {
                                x = x - (lx/4);
                                y = y + (lx/4);
                        }
                }
                nuevo.setLocation(x, y);
        } 
        else {
            double x = puntoC2.getX() + 80;
            double y = puntoC2.getY() + 20;
            nuevo.setLocation(x, y);
        }
        return nuevo;
    }
    
    public static BufferedImage crearImagen(JPanel panel) {
        int w = panel.getWidth();
        int h = panel.getHeight();
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        panel.paint(g);
        return bi;
    }
}
