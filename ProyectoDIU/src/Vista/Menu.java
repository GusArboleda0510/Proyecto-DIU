/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Control.Sonido;

/**
 *
 * @author ANDRES ARBOLEDA
 */
public class Menu extends javax.swing.JFrame {

    /**
     * Creates new form Menu
     */
    Sonido s;
    public Menu() {
        initComponents();
        jBJugar1.setVisible(false);
        jbAyuda.setVisible(false);
        jbGanadores.setVisible(false);
        jBCerrar.setVisible(false);
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jBJugar = new javax.swing.JButton();
        jBJugar1 = new javax.swing.JButton();
        jBCerrar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jbGanadores1 = new javax.swing.JButton();
        jbGanadores = new javax.swing.JButton();
        jbAyuda1 = new javax.swing.JButton();
        jbAyuda = new javax.swing.JButton();
        jBCerrar1 = new javax.swing.JButton();
        Fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 10));
        jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel2.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jBJugar.setFont(new java.awt.Font("Times New Roman", 1, 100)); // NOI18N
        jBJugar.setForeground(new java.awt.Color(153, 204, 255));
        jBJugar.setText("Jugar");
        jBJugar.setContentAreaFilled(false);
        jBJugar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBJugarMouseExited(evt);
            }
        });
        jBJugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBJugarActionPerformed(evt);
            }
        });
        jPanel2.add(jBJugar, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 130, 290, 130));

        jBJugar1.setFont(new java.awt.Font("Times New Roman", 1, 60)); // NOI18N
        jBJugar1.setForeground(new java.awt.Color(153, 204, 255));
        jBJugar1.setText("Jugar");
        jBJugar1.setContentAreaFilled(false);
        jBJugar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBJugar1MouseEntered(evt);
            }
        });
        jPanel2.add(jBJugar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 160, 200, 80));

        jBCerrar.setFont(new java.awt.Font("Times New Roman", 1, 100)); // NOI18N
        jBCerrar.setForeground(new java.awt.Color(153, 204, 255));
        jBCerrar.setText("Cerrar");
        jBCerrar.setContentAreaFilled(false);
        jBCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCerrarMouseExited(evt);
            }
        });
        jBCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCerrarActionPerformed(evt);
            }
        });
        jPanel2.add(jBCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 410, 340, 90));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 80)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Menu");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 300, -1));

        jbGanadores1.setFont(new java.awt.Font("Times New Roman", 1, 60)); // NOI18N
        jbGanadores1.setForeground(new java.awt.Color(153, 204, 255));
        jbGanadores1.setText("Ganadores");
        jbGanadores1.setContentAreaFilled(false);
        jbGanadores1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbGanadores1MouseEntered(evt);
            }
        });
        jPanel2.add(jbGanadores1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 280, 330, 100));

        jbGanadores.setFont(new java.awt.Font("Times New Roman", 1, 90)); // NOI18N
        jbGanadores.setForeground(new java.awt.Color(153, 204, 255));
        jbGanadores.setText("Ganadores");
        jbGanadores.setContentAreaFilled(false);
        jbGanadores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbGanadoresMouseExited(evt);
            }
        });
        jbGanadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbGanadoresActionPerformed(evt);
            }
        });
        jPanel2.add(jbGanadores, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 490, 80));

        jbAyuda1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Ayuda1.png"))); // NOI18N
        jbAyuda1.setContentAreaFilled(false);
        jbAyuda1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbAyuda1MouseExited(evt);
            }
        });
        jbAyuda1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAyuda1ActionPerformed(evt);
            }
        });
        jPanel2.add(jbAyuda1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 110, 110));

        jbAyuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Ayuda.png"))); // NOI18N
        jbAyuda.setContentAreaFilled(false);
        jbAyuda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbAyudaMouseEntered(evt);
            }
        });
        jPanel2.add(jbAyuda, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 30, 70, 60));

        jBCerrar1.setFont(new java.awt.Font("Times New Roman", 1, 60)); // NOI18N
        jBCerrar1.setForeground(new java.awt.Color(153, 204, 255));
        jBCerrar1.setText("Cerrar");
        jBCerrar1.setContentAreaFilled(false);
        jBCerrar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCerrar1MouseEntered(evt);
            }
        });
        jBCerrar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCerrar1ActionPerformed(evt);
            }
        });
        jPanel2.add(jBCerrar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 430, -1, 70));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, 540, 540));

        Fondo.setFont(new java.awt.Font("Tahoma", 0, 26)); // NOI18N
        Fondo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Menu.png"))); // NOI18N
        jPanel1.add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 796, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(806, 637));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jBCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCerrarActionPerformed
        dispose();
    }//GEN-LAST:event_jBCerrarActionPerformed

    private void jbAyuda1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAyuda1ActionPerformed
         s = new Sonido("click.wav");
        new GuiaControles(null, true);
    }//GEN-LAST:event_jbAyuda1ActionPerformed

    private void jbAyuda1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbAyuda1MouseExited
        jbAyuda1.setVisible(false);
        jbAyuda.setVisible(true);
    }//GEN-LAST:event_jbAyuda1MouseExited

    private void jbAyudaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbAyudaMouseEntered
        jbAyuda1.setVisible(true);
        jbAyuda.setVisible(false);
    }//GEN-LAST:event_jbAyudaMouseEntered

    private void jBCerrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCerrar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBCerrar1ActionPerformed

    private void jBJugarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBJugarMouseExited
        jBJugar.setVisible(false);
        jBJugar1.setVisible(true);
    }//GEN-LAST:event_jBJugarMouseExited

    private void jBJugar1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBJugar1MouseEntered
        jBJugar.setVisible(true);
        jBJugar1.setVisible(false);
    }//GEN-LAST:event_jBJugar1MouseEntered

    private void jbGanadoresMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbGanadoresMouseExited
        jbGanadores.setVisible(false);
        jbGanadores1.setVisible(true);
    }//GEN-LAST:event_jbGanadoresMouseExited

    private void jbGanadores1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbGanadores1MouseEntered
        jbGanadores.setVisible(true);
        jbGanadores1.setVisible(false);
    }//GEN-LAST:event_jbGanadores1MouseEntered

    private void jBCerrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCerrarMouseExited
        jBCerrar.setVisible(false);
        jBCerrar1.setVisible(true);
    }//GEN-LAST:event_jBCerrarMouseExited

    private void jBCerrar1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCerrar1MouseEntered
        jBCerrar.setVisible(true);
        jBCerrar1.setVisible(false);
    }//GEN-LAST:event_jBCerrar1MouseEntered

    private void jbGanadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbGanadoresActionPerformed
        try {
            s = new Sonido("click.wav");
            new Ganadores(null, true);
        } catch (Exception ex) {
            System.out.println("Error abrir Ganadores "+ ex);
        }
    }//GEN-LAST:event_jbGanadoresActionPerformed

    private void jBJugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBJugarActionPerformed
        s = new Sonido("click.wav");
        new CrearAvatar(null, true);
    }//GEN-LAST:event_jBJugarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fondo;
    private javax.swing.JButton jBCerrar;
    private javax.swing.JButton jBCerrar1;
    private javax.swing.JButton jBJugar;
    private javax.swing.JButton jBJugar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton jbAyuda;
    private javax.swing.JButton jbAyuda1;
    private javax.swing.JButton jbGanadores;
    private javax.swing.JButton jbGanadores1;
    // End of variables declaration//GEN-END:variables
}
