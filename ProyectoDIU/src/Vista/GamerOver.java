/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Control.ControlTXT;
import Control.Sonido;
import java.awt.Frame;

/**
 *
 * @author Alejandra Becerra
 */
public class GamerOver extends javax.swing.JDialog { 

    String nickname;
    ControlTXT txt =new ControlTXT();
    Sonido s;
    Mundo1 mundo1;

    public GamerOver(Frame owner, boolean modal,String puntaje){
        super(owner, modal);
        initComponents();
        s = new Sonido("GameOver.wav");
        nickname=txt.leerNickName();
        jlPuntaje.setText(puntaje);
        jlNickName.setText(nickname);
        
        setVisible(true);
    }    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        gammerOver = new javax.swing.JLabel();
        nickName = new javax.swing.JLabel();
        jlPuntaje = new javax.swing.JLabel();
        jlNickName = new javax.swing.JLabel();
        Puntaje = new javax.swing.JLabel();
        flechaDerch1 = new javax.swing.JLabel();
        flechaDerch2 = new javax.swing.JLabel();
        flechaDerch3 = new javax.swing.JLabel();
        flechaIzq1 = new javax.swing.JLabel();
        flechaIzq2 = new javax.swing.JLabel();
        flechaIzq3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        gammerOver.setFont(new java.awt.Font("Times New Roman", 3, 90)); // NOI18N
        gammerOver.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gammerOver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gamerOver.jpg"))); // NOI18N
        jPanel1.add(gammerOver, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 640, 460));

        nickName.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        nickName.setForeground(new java.awt.Color(255, 255, 255));
        nickName.setText("NICK NAME");
        jPanel1.add(nickName, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 470, 200, 40));

        jlPuntaje.setFont(new java.awt.Font("Times New Roman", 1, 28)); // NOI18N
        jlPuntaje.setForeground(new java.awt.Color(255, 255, 255));
        jlPuntaje.setText("------");
        jPanel1.add(jlPuntaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 510, 240, 40));

        jlNickName.setFont(new java.awt.Font("Times New Roman", 1, 28)); // NOI18N
        jlNickName.setForeground(new java.awt.Color(255, 255, 255));
        jlNickName.setText("-------");
        jPanel1.add(jlNickName, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 470, 240, 40));

        Puntaje.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        Puntaje.setForeground(new java.awt.Color(255, 255, 255));
        Puntaje.setText("PUNTAJE");
        jPanel1.add(Puntaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 510, 200, 40));

        flechaDerch1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/continuarDerecha.png"))); // NOI18N
        jPanel1.add(flechaDerch1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 580, 50, 50));

        flechaDerch2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/continuarDerecha.png"))); // NOI18N
        jPanel1.add(flechaDerch2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 580, 50, 50));

        flechaDerch3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/continuarDerecha.png"))); // NOI18N
        jPanel1.add(flechaDerch3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 580, 50, 50));

        flechaIzq1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/continuarIzquierda.png"))); // NOI18N
        jPanel1.add(flechaIzq1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 580, 50, 50));

        flechaIzq2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/continuarIzquierda.png"))); // NOI18N
        jPanel1.add(flechaIzq2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 580, 50, 50));

        flechaIzq3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/continuarIzquierda.png"))); // NOI18N
        jPanel1.add(flechaIzq3, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 580, 50, 50));

        jButton1.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("CONTINUAR");
        jButton1.setContentAreaFilled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 570, 530, 66));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(779, 682));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        s = new Sonido("click.wav");
        new Menu();
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String[] args) throws Exception {
        
        new GamerOver(null, true,"");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Puntaje;
    private javax.swing.JLabel flechaDerch1;
    private javax.swing.JLabel flechaDerch2;
    private javax.swing.JLabel flechaDerch3;
    private javax.swing.JLabel flechaIzq1;
    private javax.swing.JLabel flechaIzq2;
    private javax.swing.JLabel flechaIzq3;
    private javax.swing.JLabel gammerOver;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel jlNickName;
    private javax.swing.JLabel jlPuntaje;
    private javax.swing.JLabel nickName;
    // End of variables declaration//GEN-END:variables

    
}
