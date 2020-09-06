/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paneles;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * 
 */
public class Lienzo1 extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
    private ArrayList<Estado> estados = null;
    private ArrayList<Evento> eventos = null;
    private int indice = -1, socio1 = -1, socio2 = -1;
    
    public Lienzo1() {
        setEstados(new ArrayList<>());
        setEventos(new ArrayList<>());
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (Evento evento : getEventos())
           evento.pintar(g);
        for (Estado estado : getEstados())
            estado.pintar(g);
    }
    
    public void pintar() {
        String[][] tabla = Minimiza.getObjControl().getTabla();
        for (int i = 1; i < tabla.length; i++) {
            String[] estado1 = tabla[i][0].split(";");
            getEstados().add(new Estado((int) (Math.random() * getBounds().width) + 1, (int) (Math.random() * getBounds().height) + 1, estado1[0], Boolean.parseBoolean(estado1[1]), i == 1));
        }
        for (int i = 1; i < tabla.length; i++) {
            String[] estado1 = tabla[i][0].split(";");
            int iAux = Motor.buscarEstado(getEstados(), estado1[0]);
            for (int j = 1; j < tabla[i].length; j++) {
                String[] uniones = tabla[i][j].split(";");
                String[] estado2 = tabla[0][j].split(";");
                int jAux = Motor.buscarEstado(getEstados(), estado2[0]);
                for (int k = 0; k < uniones.length; k++) {
                    String union = uniones[k];
                    if (union != null && !"".equals(union)) {
                        Point punto1 = new Point(getEstados().get(iAux).getX() + 30, getEstados().get(iAux).getY() + 30);
                        Point punto2 = new Point(getEstados().get(jAux).getX() + 30, getEstados().get(jAux).getY() + 30);
                        setEventos(Motor.generarEvento(getEventos(), punto1, punto2, union));
                    }
                }
            }
        }
        repaint();
    }

    public ArrayList<Estado> getEstados() {
        return estados;
    }

    private void setEstados(ArrayList<Estado> estados) {
        this.estados = estados;
    }

    public ArrayList<Evento> getEventos() {
        return eventos;
    }

    private void setEventos(ArrayList<Evento> eventos) {
        this.eventos = eventos;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public int getSocio1() {
        return socio1;
    }

    public void setSocio1(int socio1) {
        this.socio1 = socio1;
    }

    public int getSocio2() {
        return socio2;
    }

    public void setSocio2(int socio2) {
        this.socio2 = socio2;
    }
    
    public void borrar() {
        setEstados(new ArrayList<>());
        setEventos(new ArrayList<>());
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int aux = Motor.buscarEstado(getEstados(), e.getPoint());
        if (e.getButton() == MouseEvent.BUTTON1 && aux > -1)
            setIndice(aux);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1)
            setIndice(-1);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        boolean isPosicion = Motor.validarSuperposicion(getEstados(), e.getPoint(), getIndice());
        if (e.getModifiersEx() == MouseEvent.BUTTON1_DOWN_MASK && getIndice() > -1 && Motor.validarLienzo(e.getPoint(), getBounds()) && isPosicion) {
            Object[] listaMixta = Motor.moverEstado(getEstados(), getEventos(), e.getPoint(), getIndice());
            setEstados((ArrayList<Estado>) listaMixta[0]);
            setEventos((ArrayList<Evento>) listaMixta[1]);
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "UNIR":
                if (getIndice() > -1 && getSocio1() < 0)
                    setSocio1(getIndice());
                else if (getIndice() > -1 && getSocio2() < 0)
                        setSocio2(getIndice());
                if (getSocio1() > -1 && getSocio2() > -1) {
                    Estado aux1 = getEstados().get(getSocio1());
                    Point punto1 = new Point((int) aux1.getX() + 30, (int) aux1.getY() + 30);
                    Estado aux2 = getEstados().get(getSocio2());
                    Point punto2 = new Point((int) aux2.getX() + 30, (int) aux2.getY() + 30);
                    String evento = JOptionPane.showInputDialog("Evento:");
                    setEventos(Motor.generarEvento(getEventos(), punto1, punto2, evento));
                    setSocio1(-1);
                    setSocio2(-1);
                    repaint();
                    Minimiza.getObjControl().asociarEstado(aux1.getNombre(), evento, aux2.getNombre());
                }
        }
    }
}
