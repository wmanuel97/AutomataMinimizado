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
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 *
 * 
 */
public class Lienzo0 extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
    private ArrayList<Estado> estados = null;
    private ArrayList<Evento> eventos = null;
    private int indice = -1, socio1 = -1, socio2 = -1;
    public boolean editable = false;
    
    public Lienzo0() {
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

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    
    public void borrar() {
        setEstados(new ArrayList<>());
        setEventos(new ArrayList<>());
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 && isEditable()) {
            JDialog windowBlock = new JDialog(Minimiza.getVentana(), "NUEVO ESTADO", true);
            windowBlock.add(new jEstado(windowBlock, e.getX(), e.getY())); 
            windowBlock.setSize(225, 175);
            windowBlock.setResizable(false);
            windowBlock.setVisible(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int aux = Motor.buscarEstado(getEstados(), e.getPoint());
        if (e.getButton() == MouseEvent.BUTTON1 && aux > -1 && isEditable())
            setIndice(aux);
        if (e.getButton() == MouseEvent.BUTTON3 && aux > -1 && isEditable()) {
            JPopupMenu menuP = new JPopupMenu();
            JMenuItem unir = new JMenuItem("UNIR");
            JMenuItem estado = new JMenuItem("ESTADO");
            JMenuItem eliminar = new JMenuItem("ELIMINAR");
            unir.addActionListener(this);
            estado.addActionListener(this);
            eliminar.addActionListener(this);
            menuP.add(unir);
            menuP.add(estado);
            menuP.add(eliminar);
            menuP.show(e.getComponent(), e.getX(), e.getY());
            setIndice(aux);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
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
        if (e.getModifiersEx() == MouseEvent.BUTTON1_DOWN_MASK && getIndice() > -1 && Motor.validarLienzo(e.getPoint(), getBounds()) && isEditable() && isPosicion) {
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
                    String[] lenguaje = Minimiza.getObjControl().getLenguaje(1);
                    String evento = (String) JOptionPane.showInputDialog(Minimiza.getVentana(), null, "NUEVO EVENTO", JOptionPane.QUESTION_MESSAGE, new ImageIcon("link.png"), lenguaje, lenguaje[0]);
                    if (evento != null) {
                        setEventos(Motor.generarEvento(getEventos(), punto1, punto2, evento));
                        repaint();
                        Minimiza.getObjControl().asociarEstado(aux1.getNombre(), evento, aux2.getNombre());
                        Minimiza.getPanel().refrescar();
                    }
                    setSocio1(-1);
                    setSocio2(-1);
                }
                break;
            case "ESTADO":
                Estado aux0 = getEstados().get(getIndice());
                getEstados().set(getIndice(), new Estado(aux0.getX(), aux0.getY(), aux0.getNombre(), !aux0.getTipo(), aux0.getS()));
                repaint();
                Minimiza.getObjControl().modificarTipo(aux0.getNombre(), !aux0.getTipo());
                Minimiza.getPanel().refrescar();
                break;
            case "ELIMINAR":   
                if (getIndice() > 0) {
                    Estado aux1 = getEstados().get(getIndice());
                    Motor.eliminarEstado(getEstados(), getEventos(), aux1.getNombre());
                    Minimiza.getObjControl().eliminarEstado(aux1.getNombre());
                    Minimiza.getPanel().refrescar();
                    repaint();
                }
                else
                    JOptionPane.showMessageDialog(null, "ESTADO INICIAL", "ERROR", JOptionPane.ERROR_MESSAGE, new ImageIcon("error.png"));
        }
    }
}
