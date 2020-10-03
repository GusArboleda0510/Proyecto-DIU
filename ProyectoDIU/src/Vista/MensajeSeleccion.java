/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

/**
 *
 * @author ANDRES ARBOLEDA
 */
public class MensajeSeleccion extends javax.swing.JDialog {

    /**
     * Creates new form ReescribirAvatar
     */
     boolean reescribir;

    public boolean isReescribir() {
        return reescribir;
    }
    public MensajeSeleccion(java.awt.Frame parent, boolean modal, String mensaje) {
        super(parent, modal);
        initComponents();
        jmsj1.setVisible(false);
        jmsj2.setVisible(false);
        setMensaje(mensaje);
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

        jmsj2 = new javax.swing.JLabel();
        jBNo = new javax.swing.JButton();
        jmsj1 = new javax.swing.JLabel();
        jBSi = new javax.swing.JButton();
        Fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jmsj2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 26)); // NOI18N
        jmsj2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jmsj2.setText("msj2");
        getContentPane().add(jmsj2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 431, 72));

        jBNo.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jBNo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cerrar.png"))); // NOI18N
        jBNo.setText("No");
        jBNo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBNo.setContentAreaFilled(false);
        jBNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBNoActionPerformed(evt);
            }
        });
        getContentPane().add(jBNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 210, 160, 50));

        jmsj1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 26)); // NOI18N
        jmsj1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jmsj1.setText("msj1");
        getContentPane().add(jmsj1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 431, 72));

        jBSi.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jBSi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/si.png"))); // NOI18N
        jBSi.setText("Si");
        jBSi.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBSi.setContentAreaFilled(false);
        jBSi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSiActionPerformed(evt);
            }
        });
        getContentPane().add(jBSi, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, 150, 50));

        Fondo.setFont(new java.awt.Font("Tempus Sans ITC", 3, 26)); // NOI18N
        Fondo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondoBlanco.jpg"))); // NOI18N
        getContentPane().add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 470, 350));

        setSize(new java.awt.Dimension(483, 347));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jBNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNoActionPerformed
        reescribir = false;
        setVisible(false);
    }//GEN-LAST:event_jBNoActionPerformed

    private void jBSiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSiActionPerformed
        reescribir = true;
        setVisible(false);
    }//GEN-LAST:event_jBSiActionPerformed

    /**
     * @param args the command line arguments
     */
    public void setMensaje(String msj){
        String[] arr = msj.split("-");
        jmsj1.setText(arr[0]);
        jmsj1.setVisible(true);
        
        jmsj2.setText(arr[1]);
        jmsj2.setVisible(true);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fondo;
    private javax.swing.JButton jBNo;
    private javax.swing.JButton jBSi;
    private javax.swing.JLabel jmsj1;
    private javax.swing.JLabel jmsj2;
    // End of variables declaration//GEN-END:variables
}
