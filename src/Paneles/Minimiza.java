/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paneles;

import Clases.Control;
import javax.swing.JFrame;

/**

 */
public class Minimiza {
    private static final JFrame ventana = new JFrame("Minimizar");
    private static final Control objControl = new Control();
    private static final Auto panel = new Auto(); 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        iniciar();
    }

    private static void iniciar() {
        getPanel().agregarTab(new Lienzo0(), new Lienzo1());
        getVentana().add(getPanel());
        getVentana().setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        getVentana().setSize(1350, 750);
        getVentana().setResizable(true);
        getVentana().setVisible(true);
    }

    public static JFrame getVentana() {
        return ventana;
    }

    public static Auto getPanel() {
        return panel;
    }

    public static Control getObjControl() {
        return objControl;
    }
    
}
