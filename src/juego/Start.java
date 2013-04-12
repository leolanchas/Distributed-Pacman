/*
 * Start.java
 *
 * Created on 29 de mayo de 2009, 12:11
 */

/**
 *
 * @author  a064892
 *
 *
 */
package juego;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import ClienteServidor.Cliente;
import ClienteServidor.Servidor;

public class Start extends javax.swing.JFrame
{   
    public static Servidor server;
    public static Cliente client;
    String espera = "0";
    
    /** Creates new form Start */
    public Start() {
        initComponents();
        addWindowListener( new WindowAdapter(){       
            public void windowClosing(WindowEvent e) { System.exit(0); }
        });
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Salida = new javax.swing.JToggleButton();
        UnJugador = new javax.swing.JToggleButton();
        DosJugadores = new javax.swing.JToggleButton();
        IP = new javax.swing.JTextField();
        Se = new javax.swing.JRadioButton();
        Cl = new javax.swing.JRadioButton();
        Puerto = new javax.swing.JTextField();
        Nombre = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(javax.swing.UIManager.getDefaults().getColor("tab_highlight_header_fill"));

        Salida.setText("Salir!");
        Salida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalidaActionPerformed(evt);
            }
        });

        UnJugador.setBackground(javax.swing.UIManager.getDefaults().getColor("tab_highlight_header"));
        UnJugador.setText("Un jugador");
        UnJugador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UnJugadorActionPerformed(evt);
            }
        });

        DosJugadores.setText("Dos jugadores");
        DosJugadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DosJugadoresActionPerformed(evt);
            }
        });

        IP.setText("localhost");

        Se.setText("Servidor");
        Se.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        Se.setMargin(new java.awt.Insets(0, 0, 0, 0));
        Se.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SeMouseClicked(evt);
            }
        });

        Cl.setText("Cliente");
        Cl.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        Cl.setMargin(new java.awt.Insets(0, 0, 0, 0));
        Cl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ClMouseClicked(evt);
            }
        });

        Puerto.setText("3035");
        Puerto.setName("Puerto"); // NOI18N
        Puerto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PuertoActionPerformed(evt);
            }
        });

        Nombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Nombre.setText("Tu Nombre");
        Nombre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NombreMouseClicked(evt);
            }
        });
        Nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NombreActionPerformed(evt);
            }
        });
        Nombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                NombreFocusLost(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Salida, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                            .addComponent(UnJugador, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(DosJugadores)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(Se)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(IP, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(Cl)
                                        .addGap(10, 10, 10)
                                        .addComponent(Puerto, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(UnJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(DosJugadores, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Se)
                            .addComponent(IP))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Cl)
                            .addComponent(Puerto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(Salida, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ClMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ClMouseClicked
// TODO add your handling code here:
        Se.setSelected(false);
        Pacman.serv = false;
    }//GEN-LAST:event_ClMouseClicked

    private void SeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SeMouseClicked
// TODO add your handling code here:
         Cl.setSelected(false);
         Pacman.serv = true;
    }//GEN-LAST:event_SeMouseClicked

    private void DosJugadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DosJugadoresActionPerformed
// TODO add your handling code here:
        
        if(Se.isSelected())
        {
            Pacman.serv = true;
            if( Puerto.getText().length() == 4  ) Pacman.puerto = Puerto.getText();

            server = new Servidor( "localhost", Pacman.puerto );
            espera = server.recibirRistra("queue1", 1);
        }
        else
        {
            Pacman.serv = false;
            if( IP.getText().length() >= 6  ) Pacman.ip = IP.getText();

            if( Puerto.getText().length() == 4  ) Pacman.puerto = Puerto.getText();

            client = new Cliente(Pacman.ip, Pacman.puerto );
            client.enviarRistra("1","queue1");
        }
        Pacman.numplayers = 2;
        Pacman.begin.release();
    }//GEN-LAST:event_DosJugadoresActionPerformed

    private void UnJugadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UnJugadorActionPerformed
// TODO add your handling code here:
        Pacman.numplayers = 1;
        Pacman.serv = true;
        Pacman.begin.release();
    }//GEN-LAST:event_UnJugadorActionPerformed

    private void SalidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalidaActionPerformed
// TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_SalidaActionPerformed

    private void PuertoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PuertoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PuertoActionPerformed

    private void NombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NombreActionPerformed

    private void NombreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NombreMouseClicked
        // TODO add your handling code here:
        Nombre.setText("");
    }//GEN-LAST:event_NombreMouseClicked

    private void NombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_NombreFocusLost
        // TODO add your handling code here:
        if (!Nombre.getText().toString().equalsIgnoreCase("") ) 
            Pacman.Nombre = Nombre.getText();

        Nombre.setText( Pacman.Nombre );
    }//GEN-LAST:event_NombreFocusLost

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton Cl;
    private javax.swing.JToggleButton DosJugadores;
    private javax.swing.JTextField IP;
    private javax.swing.JTextField Nombre;
    private javax.swing.JTextField Puerto;
    private javax.swing.JToggleButton Salida;
    private javax.swing.JRadioButton Se;
    private javax.swing.JToggleButton UnJugador;
    // End of variables declaration//GEN-END:variables
    
}
