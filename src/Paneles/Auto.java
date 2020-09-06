/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paneles;

import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

/**

 */
public class Auto extends javax.swing.JPanel {

    /**

     */
    public Auto() {
        initComponents();
        jButtonNueva.setIcon(new ImageIcon("nuevo.png"));
        jButtonOptimizar.setIcon(new ImageIcon("mini.png"));
        jButtonBorrar.setIcon(new ImageIcon("delete.png"));
        isBorrar();
        refrescar();
    }
    
    public void agregarTab(Lienzo0 normal, Lienzo1 optimizado) {
        jTabbedPane1.add("AUTOMATA", normal);
        jTabbedPane1.add("MINIMIZADO", optimizado);
    }
    
    public void refrescar() {
        String texto = "";
        texto += "LENGUAJE: {" + Minimiza.getObjControl().getLenguaje() + "}\n\n";
        texto += "AFD: " + Minimiza.getObjControl().isAFD() + "\n\n";
        texto += "AUTOMATA ORIGINAL: \n" + Minimiza.getObjControl().imprimirAutomata(1) + "\n";
        texto += "AUTOMATA MINIMIZADO: \n" + Minimiza.getObjControl().imprimirAutomata(2) + "\n";
        jTextAreaDatos.setText(texto);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaDatos = new javax.swing.JTextArea();
        jButtonNueva = new javax.swing.JButton();
        jButtonOptimizar = new javax.swing.JButton();
        jButtonBorrar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jTabbedPane1 = new javax.swing.JTabbedPane();

        jTextAreaDatos.setEditable(false);
        jTextAreaDatos.setColumns(20);
        jTextAreaDatos.setRows(5);
        jScrollPane1.setViewportView(jTextAreaDatos);

        jButtonNueva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNuevaActionPerformed(evt);
            }
        });

        jButtonOptimizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOptimizarActionPerformed(evt);
            }
        });

        jButtonBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBorrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonNueva, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonOptimizar)
                        .addGap(15, 15, 15)
                        .addComponent(jButtonBorrar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(jSeparator1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonNueva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonOptimizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonBorrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE))
                    .addComponent(jTabbedPane1))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonNuevaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNuevaActionPerformed
        // TODO add your handling code here:
        JDialog windowBlock = new JDialog(Minimiza.getVentana(), "LENGUAJE", true);
        windowBlock.add(new Jnuevo(windowBlock));                          
        windowBlock.setSize(305, 100);
        windowBlock.setResizable(false);
        windowBlock.setVisible(true);
    }//GEN-LAST:event_jButtonNuevaActionPerformed

    private void jButtonOptimizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOptimizarActionPerformed
        // TODO add your handling code here:
        if (Minimiza.getObjControl().isAFD()) {
            Minimiza.getObjControl().optimizarAutomata();
            Lienzo1 optimizado = (Lienzo1) Minimiza.getPanel().getjTabbedPane1().getComponents()[1];
            optimizado.borrar();
            optimizado.pintar();
            jTabbedPane1.setSelectedIndex(1);
            refrescar();
        }
        else {
            JOptionPane.showInternalMessageDialog(Minimiza.getVentana().getContentPane(), "Automata AFND", "ERROR", JOptionPane.ERROR_MESSAGE, new ImageIcon("error.png"));
            Lienzo1 optimizado = (Lienzo1) Minimiza.getPanel().getjTabbedPane1().getComponents()[1];
            optimizado.borrar();
        }
    }//GEN-LAST:event_jButtonOptimizarActionPerformed

    private void jButtonBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBorrarActionPerformed
        // TODO add your handling code here:
        Lienzo0 normal = (Lienzo0) Minimiza.getPanel().getjTabbedPane1().getComponents()[0];
        Lienzo1 optimizado = (Lienzo1) Minimiza.getPanel().getjTabbedPane1().getComponents()[1];
        normal.borrar();
        optimizado.borrar();
        Minimiza.getObjControl().borrarAutomata();
        normal.setEditable(false);
        isBorrar();
        refrescar();
    }//GEN-LAST:event_jButtonBorrarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBorrar;
    private javax.swing.JButton jButtonNueva;
    private javax.swing.JButton jButtonOptimizar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextAreaDatos;
    // End of variables declaration//GEN-END:variables

    public void isCrear() {
        jButtonNueva.setEnabled(false);
        jButtonOptimizar.setEnabled(true);
        
        jButtonBorrar.setEnabled(true);
        jTextAreaDatos.setText("");
    }
    
    private void isBorrar() {
        jButtonNueva.setEnabled(true);
        jButtonOptimizar.setEnabled(false);
        
        jButtonBorrar.setEnabled(false);
        jTextAreaDatos.setText("");
    }
    
    public JTabbedPane getjTabbedPane1() {
        return jTabbedPane1;
    }
}