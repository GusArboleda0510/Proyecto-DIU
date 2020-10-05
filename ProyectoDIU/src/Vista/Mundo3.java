/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Control.ControlEnemigos;
import Control.ControlTXT;
import Control.Sonido;
import Control.Tiempo;
import Control.controlJugabilidad;
import Control.controlXMLMundos;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author Alejandra Becerra
 */
public class Mundo3 extends javax.swing.JDialog {

    /**
     * Creates new form Mundo_3
     */
    ControlTXT txt = new ControlTXT();
    Thread t;
    int hora, minuto, segundo;
    String[] dato;
    Sonido s;
    controlJugabilidad jug;
    String[] infoVida_Puntaje = new String[2];
    Boolean colision = false;
    int[] posEnemigo = new int[4];
    int[] posAvatar = new int[4];
    String punt, vida, rutaCarpeta, mapaAct, nombre, nickName, imgpeque;
    JLabel tempEnemigo1 = new JLabel();
    JLabel tempEnemigo2 = new JLabel();
    JLabel tempEnemigo3 = new JLabel();
    controlXMLMundos leerMundos;
    Thread contEnemigos;
    int cambio = 1;

    public Mundo3(java.awt.Frame parent, boolean modal, String nickNameJugador, String imgPeque, String rutaCarpeta) {
        super(parent, modal);
        initComponents();
        this.nickName = nickNameJugador;
        this.imgpeque = imgPeque;
        this.rutaCarpeta = rutaCarpeta;
        try {
            decidirMapa();
        } catch (Exception ex) {
            System.out.println("error en decidirmapa " + ex);
        }
        if (imgPeque != null) {
            jLAvatarMapa1.setIcon(new javax.swing.ImageIcon(getClass().getResource(imgPeque)));
        } else {
            if (nickNameJugador != null) {
                String predPequenia = "/Imagenes/Avatars/Usuarios/" + nickNameJugador + ".png";
                jLAvatarMapa1.setIcon(new javax.swing.ImageIcon(getClass().getResource(predPequenia)));
            }
        }

//        Carga XML (Timepo,Vida,Puntaje)
        dato = txt.leerTiempo();
        JPanelInf();
        String[] aux = dato[0].split(":");

        t = new Tiempo(aux);
        t.start();
        setVisible(true);
    }

    public Mundo3(String nada) {
        // NO BORRAR ESTE CONSTRUCTOR
    }

    private void initVidaPuntaje() {
        JPanelInf();
        dato = txt.leerTiempo();
        String[] aux = dato[0].split(":");
        t = new Tiempo(aux);
        t.start();
    }

    public void JPanelInf() {
        String[] inf = txt.leerVidaPuntaje();
        inf[1] = "" + (Integer.parseInt(inf[1]) + 100);
        Puntaje.setText("" + inf[1]);//inf[1]==puntaje de las vidas del Mundo 1 y (100) equivale a pasar el mundo1
        vidaJLabel(inf);

    }

    public void vidaJLabel(String[] inf) {
        if (colision()) {
            if (inf[0].equals("-1")) {
                dispose();
                new GamerOver(null, true, Puntaje.getText());
            }
            if (inf[0].equals("0")) {
                jlvida1.setVisible(false);
                jlvida2.setVisible(false);
                jlvida3.setVisible(false);

            }
            if (inf[0].equals("1")) {
                jlvida1.setVisible(true);
                jlvida2.setVisible(false);
                jlvida3.setVisible(false);

            }
            if (inf[0].equals("2")) {
                jlvida1.setVisible(true);
                jlvida2.setVisible(true);
                jlvida3.setVisible(false);

            }
            if (inf[0].equals("3")) {
                jlvida1.setVisible(true);
                jlvida2.setVisible(true);
                jlvida3.setVisible(true);

            }
        } else {
            if (inf[0].equals("0")) {
                jlvida1.setVisible(false);
                jlvida2.setVisible(false);
                jlvida3.setVisible(false);

            }
            if (inf[0].equals("1")) {
                jlvida1.setVisible(true);
                jlvida2.setVisible(false);
                jlvida3.setVisible(false);

            }
            if (inf[0].equals("2")) {
                jlvida1.setVisible(true);
                jlvida2.setVisible(true);
                jlvida3.setVisible(false);

            }
            if (inf[0].equals("3")) {
                jlvida1.setVisible(true);
                jlvida2.setVisible(true);
                jlvida3.setVisible(true);

            }
        }

        vida = inf[0];
        this.punt = inf[1];
    }

    private void decidirMapa() throws Exception {
        leerMundos = new controlXMLMundos();
        String nombreMapa = null;
        Mapa1.setFocusable(true);
        Mapa1.setVisible(true);
        nombreMapa = "mapa1";
        mapaAct = nombreMapa;
        leerMundos.consultarXML("mundo3", nombreMapa);
        initEnemigos(nombreMapa);

    }

    private void initEnemigos(String nombMapa) {
        int cantEnemigos = leerMundos.getCantEnemigos();
        String[] skins = leerMundos.getSkinEnemigos();

        M1E1.setVisible(false);
        M1E2.setVisible(false);
        M1E3.setVisible(false);

        if (nombMapa.equals("mapa1")) {
            tempEnemigo1 = M1E1;
            tempEnemigo2 = M1E2;
            tempEnemigo3 = M1E3;
        }

        String SkinEnemigo1;
        String SkinEnemigo2;
        String SkinEnemigo3;

        if (!"aleatorio".equals(skins[0])) {
            SkinEnemigo1 = skins[0];
        } else {
            SkinEnemigo1 = elegirEnemigo();
        }
        if (!"aleatorio".equals(skins[1])) {
            SkinEnemigo2 = skins[0];
        } else {
            SkinEnemigo2 = elegirEnemigo();
        }
        if (!"aleatorio".equals(skins[2])) {
            SkinEnemigo3 = skins[0];
        } else {
            SkinEnemigo3 = elegirEnemigo();
        }        
        if (cantEnemigos >= 1) {
            tempEnemigo1.setName("enemigo1");
            tempEnemigo1.setVisible(true);
            contEnemigos = new ControlEnemigos(tempEnemigo1, "mundo3", nombMapa, SkinEnemigo1);
            contEnemigos.start();
        }
        if (cantEnemigos >= 2) {
            tempEnemigo2.setName("enemigo2");
            tempEnemigo2.setVisible(true);

            contEnemigos = new ControlEnemigos(tempEnemigo2, "mundo3", nombMapa, SkinEnemigo2);
            contEnemigos.start();
        }
        if (cantEnemigos >= 3) {
            tempEnemigo3.setName("enemigo3");
            tempEnemigo3.setVisible(true);
            contEnemigos = new ControlEnemigos(tempEnemigo3, "mundo3", nombMapa, SkinEnemigo3);
            contEnemigos.start();
        }

    }

    private String elegirEnemigo() {
        String ruta = null;
        int num = (int) (Math.random() * 5 + 1);
        if (num == 1) {
            ruta = "/Imagenes/Avatars/Avatar1";
        }
        if (num == 2) {
            ruta = "/Imagenes/Avatars/Avatar2";
        }
        if (num == 3) {
            ruta = "/Imagenes/Avatars/Avatar3";
        }
        if (num == 4) {
            ruta = "/Imagenes/Avatars/Avatar4";
        }
        if (num == 5) {
            ruta = "/Imagenes/Avatars/Avatar5";
        }
        return ruta;
    }

    private boolean colision() {
        boolean coli = false;
        if ("mapa1".equals(mapaAct)) {
            posAvatar = obtenerposAvatar(jLAvatarMapa1);
        }
        if (tempEnemigo1.isVisible()) {
            posEnemigo = obtenerposAvatar(tempEnemigo1);
            coli = validarPosiciones(posEnemigo);
            if (coli) {
                return coli;
            }
        }
        if (tempEnemigo2.isVisible()) {
            posEnemigo = obtenerposAvatar(tempEnemigo2);
            coli = validarPosiciones(posEnemigo);
            if (coli) {
                return coli;
            }
        }
        if (tempEnemigo3.isVisible()) {
            posEnemigo = obtenerposAvatar(tempEnemigo3);
            coli = validarPosiciones(posEnemigo);
            if (coli) {
                return coli;
            }
        }
        return false;
    }

    private int[] obtenerposAvatar(JLabel label) {
        int[] tal = new int[4];
        tal[0] = label.getX();
        tal[1] = label.getY();
        tal[2] = label.getX() + 40;
        tal[3] = label.getY() + 40;
        return tal;
    }

    private boolean validarPosiciones(int[] posEnemigo) {
        if (posAvatar[0] >= posEnemigo[0]) {
            if (posAvatar[0] >= posEnemigo[2]) {
                //nada X
                if (posAvatar[1] >= posEnemigo[1]) {
                    if (posAvatar[1] >= posEnemigo[3]) {
                        //nada Y
                    } else {
                        if (posAvatar[1] <= posEnemigo[3]) {
                            if (posAvatar[0] >= posEnemigo[2]) {
                                //nada
                            } else {
                                return true;
                            }

                        }
                    }
                } else {
                    if (posAvatar[3] <= posEnemigo[1]) {
                        //nada
                    } else {
                        if (posAvatar[3] >= posEnemigo[1]) {
                            if (posAvatar[0] >= posEnemigo[2]) {
                                //nada
                            } else {
                                return true;
                            }
                        }
                    }
                }
            } else {
                if (posAvatar[0] <= posEnemigo[2]) {
                    if (posAvatar[1] >= posEnemigo[1]) {
                        if (posAvatar[1] >= posEnemigo[3]) {
                            //nada
                        } else {
                            if (posAvatar[1] <= posEnemigo[3]) {
                                return true;
                            }

                        }
                    } else {
                        if (posAvatar[1] <= posEnemigo[1]) {
                            if (posAvatar[3] <= posEnemigo[1]) {
                                //nada
                            } else {
                                return true;
                            }
                        }

                    }
                }

            }
        } else {
            if (posAvatar[2] <= posEnemigo[0]) {
                //nada     
            } else {
                if (posAvatar[1] >= posEnemigo[1]) {
                    if (posAvatar[1] >= posEnemigo[3]) {
                        //nadaY
                    } else {
                        if (posAvatar[1] <= posEnemigo[3]) {
                            return true;
                        }
                    }
                } else {
                    if (posAvatar[3] <= posEnemigo[1]) {
                        //nada
                    } else {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void alternarImg(int dir, JLabel avatar) {
        if (dir == 1) {
            if (cambio == 1) {
                avatar.setIcon(new javax.swing.ImageIcon(getClass().getResource(rutaCarpeta + "/arr1.jpg")));
                cambio = 2;
            } else {
                if (cambio == 2) {
                    avatar.setIcon(new javax.swing.ImageIcon(getClass().getResource(rutaCarpeta + "/arr2.jpg")));
                    cambio = 1;
                }
            }
        }
        if (dir == 2) {
            if (cambio == 1) {
                avatar.setIcon(new javax.swing.ImageIcon(getClass().getResource(rutaCarpeta + "/der1.jpg")));
                cambio = 2;
            } else {
                if (cambio == 2) {
                    avatar.setIcon(new javax.swing.ImageIcon(getClass().getResource(rutaCarpeta + "/der2.jpg")));
                    cambio = 1;
                }
            }
        }

        if (dir == 3) {
            if (cambio == 1) {
                avatar.setIcon(new javax.swing.ImageIcon(getClass().getResource(rutaCarpeta + "/abj1.jpg")));
                cambio = 2;
            } else {
                if (cambio == 2) {
                    avatar.setIcon(new javax.swing.ImageIcon(getClass().getResource(rutaCarpeta + "/abj2.jpg")));
                    cambio = 1;
                }
            }
        }
        if (dir == 4) {
            if (cambio == 1) {
                avatar.setIcon(new javax.swing.ImageIcon(getClass().getResource(rutaCarpeta + "/izq1.jpg")));
                cambio = 2;
            } else {
                if (cambio == 2) {
                    avatar.setIcon(new javax.swing.ImageIcon(getClass().getResource(rutaCarpeta + "/izq2.jpg")));
                    cambio = 1;
                }
            }
        }
    }

    public static void main(String[] args) {
        new Mundo3(null, true, "a", "/Imagenes/Avatars/Avatar2/der1.jpg", "/Imagenes/Avatars/Avatar2");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Mapa1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        jLabel109 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        jLabel113 = new javax.swing.JLabel();
        jLabel114 = new javax.swing.JLabel();
        jLabel115 = new javax.swing.JLabel();
        jLabel117 = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
        jLabel120 = new javax.swing.JLabel();
        jLabel122 = new javax.swing.JLabel();
        jLabel125 = new javax.swing.JLabel();
        jLabel127 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLAvatarMapa1 = new javax.swing.JLabel();
        TransladorEntrada = new javax.swing.JLabel();
        TransladorSalida = new javax.swing.JLabel();
        M1E1 = new javax.swing.JLabel();
        M1E2 = new javax.swing.JLabel();
        M1E3 = new javax.swing.JLabel();
        Fondo = new javax.swing.JLabel();
        Informacion = new javax.swing.JPanel();
        jtVida = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jlvida1 = new javax.swing.JLabel();
        jlvida2 = new javax.swing.JLabel();
        jlvida3 = new javax.swing.JLabel();
        jlVolver = new javax.swing.JLabel();
        jlControlGuia = new javax.swing.JLabel();
        jlPuntraje = new javax.swing.JLabel();
        Puntaje = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Mapa1.setPreferredSize(new java.awt.Dimension(0, 0));
        Mapa1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Mapa1KeyPressed(evt);
            }
        });
        Mapa1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 50, 50));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 50, 50));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 50, 50));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 50, 50));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, 50, 50));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 50, 50));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 50, 50));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 50, 50));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, 50, 50));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 550, 50, 50));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 0, 50, 50));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 50, 50, 50));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 100, 50, 50));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 150, 50, 50));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 250, 50, 50));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 300, 50, 50));

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 350, 50, 50));

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 400, 50, 50));

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 450, 50, 50));

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 500, 50, 50));

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 550, 50, 50));

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 50, 50));

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 0, 50, 50));

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, 50, 50));

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 0, 50, 50));

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, 50, 50));

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 50, 50));

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 50, 50));

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 50, 50));

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 0, 50, 50));

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 0, 50, 50));

        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, 50, 50));

        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(1400, -480, 50, 50));

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(1400, -450, 50, 50));

        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(1400, -400, 50, 50));

        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(1400, -360, 50, 50));

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(1400, -310, 50, 50));

        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(1400, -270, 50, 50));

        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 0, 50, 50));

        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 0, 50, 50));

        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 0, 50, 50));

        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 0, 50, 50));

        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 0, 50, 50));

        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 0, 50, 50));

        jLabel48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 0, 50, 50));

        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 550, 50, 50));

        jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 550, 50, 50));

        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 550, 50, 50));

        jLabel52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 550, 50, 50));

        jLabel53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 550, 50, 50));

        jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 550, 50, 50));

        jLabel55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 550, 50, 50));

        jLabel56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 550, 50, 50));

        jLabel57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 550, 50, 50));

        jLabel58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 550, 50, 50));

        jLabel59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 550, 50, 50));

        jLabel60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 550, 50, 50));

        jLabel61.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 550, 50, 50));

        jLabel62.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 550, 50, 50));

        jLabel63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 550, 50, 50));

        jLabel64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 550, 50, 50));

        jLabel65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 550, 50, 50));

        jLabel66.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 550, 50, 50));

        jLabel67.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 50, 50));

        jLabel68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, 50, 50));

        jLabel69.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 250, 50, 50));

        jLabel70.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 250, 50, 50));

        jLabel71.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 250, 50, 50));

        jLabel72.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 250, 50, 50));

        jLabel73.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 250, 50, 50));

        jLabel74.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 250, 50, 50));

        jLabel75.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 250, 50, 50));

        jLabel76.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 250, 50, 50));

        jLabel77.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 250, 50, 50));

        jLabel78.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 250, 50, 50));

        jLabel79.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 250, 50, 50));

        jLabel80.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 250, 50, 50));

        jLabel81.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 250, 50, 50));

        jLabel82.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 250, 50, 50));

        jLabel83.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 250, 50, 50));

        jLabel84.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 300, 50, 50));

        jLabel85.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 300, 50, 50));

        jLabel86.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 300, 50, 50));

        jLabel87.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 300, 50, 50));

        jLabel88.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 300, 50, 50));

        jLabel89.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 300, 50, 50));

        jLabel90.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 300, 50, 50));

        jLabel91.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 300, 50, 50));

        jLabel92.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel92, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 300, 50, 50));

        jLabel93.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel93, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 300, 50, 50));

        jLabel94.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel94, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 300, 50, 50));

        jLabel95.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 300, 50, 50));

        jLabel97.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel97, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 300, 50, 50));

        jLabel98.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel98, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 300, 50, 50));

        jLabel99.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel99, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 300, 50, 50));

        jLabel100.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel100, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 350, 50, 50));

        jLabel101.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel101, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 300, 50, 50));

        jLabel102.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel102, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 250, 50, 50));

        jLabel103.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel103, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 300, 50, 50));

        jLabel104.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel104, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 250, 50, 50));

        jLabel105.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel105, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 450, 50, 50));

        jLabel106.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel106, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 500, 50, 50));

        jLabel109.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel109, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 400, 50, 50));

        jLabel110.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel110, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 350, 50, 50));

        jLabel111.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel111, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 450, 50, 50));

        jLabel112.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel112, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 500, 50, 50));

        jLabel113.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel113, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, 50, 50));

        jLabel114.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel114, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 200, 50, 50));

        jLabel115.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel115, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 400, 50, 50));

        jLabel117.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel117, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 150, 50, 50));

        jLabel118.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel118, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 200, 50, 50));

        jLabel120.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel120, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 50, 50, 50));

        jLabel122.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel122, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 100, 50, 50));

        jLabel125.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel125, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 50, 50, 50));

        jLabel127.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel127, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 100, 50, 50));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        Mapa1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 50, 50));

        jLAvatarMapa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/avatar.png"))); // NOI18N
        Mapa1.add(jLAvatarMapa1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 410, 40, 40));

        TransladorEntrada.setBackground(new java.awt.Color(0, 0, 0));
        TransladorEntrada.setOpaque(true);
        Mapa1.add(TransladorEntrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 350, 50, 50));

        TransladorSalida.setBackground(new java.awt.Color(0, 0, 0));
        TransladorSalida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/hoyo.gif"))); // NOI18N
        TransladorSalida.setOpaque(true);
        Mapa1.add(TransladorSalida, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 50, 50));

        M1E1.setBackground(new java.awt.Color(204, 204, 204));
        M1E1.setOpaque(true);
        Mapa1.add(M1E1, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 390, 40, 40));

        M1E2.setBackground(new java.awt.Color(255, 0, 0));
        M1E2.setOpaque(true);
        Mapa1.add(M1E2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 60, 40, 40));

        M1E3.setBackground(new java.awt.Color(0, 102, 102));
        M1E3.setOpaque(true);
        Mapa1.add(M1E3, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 200, 40, 40));

        Fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoVerde.png"))); // NOI18N
        Mapa1.add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, -1));

        getContentPane().add(Mapa1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 1000, 600));

        Informacion.setBackground(new java.awt.Color(204, 204, 255));
        Informacion.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtVida.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jtVida.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jtVida.setText("Vida");
        Informacion.add(jtVida, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 60, 90, 30));

        jLabel10.setFont(new java.awt.Font("Tempus Sans ITC", 3, 36)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("NIVEL 3");
        Informacion.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 0, 240, 50));

        jlvida1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/vida.png"))); // NOI18N
        jlvida1.setText("jLabel2");
        Informacion.add(jlvida1, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 60, 40, 30));

        jlvida2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/vida.png"))); // NOI18N
        jlvida2.setText("jLabel2");
        Informacion.add(jlvida2, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 60, 40, 30));

        jlvida3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/vida.png"))); // NOI18N
        jlvida3.setText("jLabel2");
        Informacion.add(jlvida3, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 60, 40, 30));

        jlVolver.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlVolver.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Atras.png"))); // NOI18N
        jlVolver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jlVolverVolverMenu(evt);
            }
        });
        Informacion.add(jlVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 80, 40));

        jlControlGuia.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlControlGuia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlControlGuia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Ayuda.png"))); // NOI18N
        jlControlGuia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jlControlGuiaControlGuia(evt);
            }
        });
        Informacion.add(jlControlGuia, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 0, 70, 70));

        jlPuntraje.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlPuntraje.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlPuntraje.setText("Puntaje");
        Informacion.add(jlPuntraje, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 80, 40));

        Puntaje.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        Puntaje.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Puntaje.setText("----------");
        Informacion.add(Puntaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 60, 100, 20));

        getContentPane().add(Informacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 100));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Mapa1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Mapa1KeyPressed
        int desplazamiento = 10, x = jLAvatarMapa1.getX(), y = jLAvatarMapa1.getY();

        switch (evt.getExtendedKeyCode()) {

            case KeyEvent.VK_UP:

                if (limitesM1(x, y, "up")) {
                    if (rutaCarpeta != null) {
                        alternarImg(1, jLAvatarMapa1);
                    }
                    jLAvatarMapa1.setLocation(x, y - desplazamiento);

                    if ((x > 900 && x <= 950) && (y >= 350 && y <= 360)) {
                        jLAvatarMapa1.setLocation(50, 50);
                    }
                    if ((x > 0 && x <= 50) && (y >= 50 && y <= 90)) {
                        jLAvatarMapa1.setLocation(910, 370);
                    }
                }
                break;
            case KeyEvent.VK_DOWN:
                if (limitesM1(x, y, "down")) {
                    if (rutaCarpeta != null) {
                        alternarImg(3, jLAvatarMapa1);
                    }
                    jLAvatarMapa1.setLocation(x, y + desplazamiento);
                }
                break;
            case KeyEvent.VK_LEFT:

                if (limitesM1(x, y, "left")) {
                    if (rutaCarpeta != null) {
                        alternarImg(4, jLAvatarMapa1);
                    }
                    jLAvatarMapa1.setLocation(x - desplazamiento, y);

                }
                break;
            case KeyEvent.VK_RIGHT:
                if (limitesM1(x, y, "right")) {
                    if (rutaCarpeta != null) {
                        alternarImg(2, jLAvatarMapa1);
                    }
                    jLAvatarMapa1.setLocation(x + desplazamiento, y);
                    if (x >= 950 && y >= 200) {
                        try {
                            if (!colision) {
                                infoVida_Puntaje[0] = vida;
                                infoVida_Puntaje[1] = punt;

                                txt.puntaje_vida(infoVida_Puntaje);

                            }

                            t.interrupt();
                            s = new Sonido("cambioMundo.wav");
                            dispose();
                            new Ganadores(null, true);

                        } catch (Exception ex) {
                            System.out.println("Error " + ex.getMessage());
                            Logger.getLogger(Mundo3.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                break;
            case KeyEvent.VK_W:
                if (limitesM1(x, y, "up")) {
                    if (rutaCarpeta != null) {
                        alternarImg(1, jLAvatarMapa1);
                    }
                    jLAvatarMapa1.setLocation(x, y - desplazamiento);

                    if ((x > 900 && x <= 950) && (y >= 350 && y <= 360)) {
                        jLAvatarMapa1.setLocation(50, 50);
                    }
                    if ((x > 0 && x <= 50) && (y >= 50 && y <= 90)) {
                        jLAvatarMapa1.setLocation(910, 370);
                    }
                }
                break;
            case KeyEvent.VK_S:
                if (limitesM1(x, y, "down")) {
                    if (rutaCarpeta != null) {
                        alternarImg(3, jLAvatarMapa1);
                    }
                    jLAvatarMapa1.setLocation(x, y + desplazamiento);
                }
                break;
            case KeyEvent.VK_A:

                if (limitesM1(x, y, "left")) {
                    if (rutaCarpeta != null) {
                        alternarImg(4, jLAvatarMapa1);
                    }
                    jLAvatarMapa1.setLocation(x - desplazamiento, y);

                }
                break;
            case KeyEvent.VK_D:
                if (limitesM1(x, y, "right")) {
                    if (rutaCarpeta != null) {
                        alternarImg(2, jLAvatarMapa1);
                    }
                    jLAvatarMapa1.setLocation(x + desplazamiento, y);
                    if (x >= 950 && y >= 200) {
                        try {

                            s = new Sonido("cambioMundo.wav");
                            t.interrupt();
                            dispose();
                            new Ganadores(null, true);

                        } catch (Exception ex) {
                            System.out.println("Error " + ex.getMessage());
                            Logger.getLogger(Mundo3.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                break;
            case KeyEvent.VK_ESCAPE:
                this.dispose();
                new Mundo3(null, true, nickName, imgpeque, rutaCarpeta);
            break;
        }
        if(colision()){
            s = new Sonido("upsSonido.wav");
            infoVida_Puntaje[0] = "" + (Integer.parseInt(vida) - 1);
            infoVida_Puntaje[1] = "" + (Integer.parseInt(punt) - 50);
            vidaJLabel(infoVida_Puntaje);
            Puntaje.setText(infoVida_Puntaje[1]);
            txt.puntaje_vida(infoVida_Puntaje);
        }
    }//GEN-LAST:event_Mapa1KeyPressed

    public boolean limitesM1(int x, int y, String direccion) {
        boolean limite = true;
        ////////////////////////////////////ARRIBA//////////////////////////////////////////
        if (direccion.equals("up")) {
            if ((x < 50 && (y > 450 && y <= 500))
                    || (x > 40 && (y > 300 && y <= 350))
                    || ((x > 360 && x < 450) || (x > 760 && x < 850)) && (y > 400 && y <= 450)
//                    || ((x > 160 && x < 250) || (x > 560 && x < 660)) && (y > 350 && y < 400)
                    || y == 50
                    || x > 910 && (y > 150 && y <= 200)
                    || ((x > 360 && x < 450) || (x > 760 && x < 850)) && (y > 100 && y <= 150)) {
                return limite = false;
            }
        }
        ///////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////ABAJO//////////////////////////////////////////
        if (direccion.equals("down")) {
            if (y == 510
                || y == 210
                || ((x >= 170 && x <= 240) || (x >= 570 && x <= 640)) && (y >= 110 && y < 160) 
                || ((x >= 170 && x <= 240) || (x >= 570 && x <= 640)) && (y >= 410 && y < 510)) 
            {
                return limite = false;
            }
        }
        ///////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////DERECHO//////////////////////////////////////////
        if (direccion.equals("right")) {
            if (((y > 30 && y < 200) || y > 360) && (x >= 910 && x < 960)
                    || ((y > 110 && y < 210) || (y >= 420 && y <= 510)) && ((x > 150 && x < 210) || (x >= 560 && x < 610))
                    || ((y > 30 && y < 150) || ((y >= 350 && y < 450))) && ((x >= 350 && x < 410) || (x >= 760 && x < 800))) {
                return limite = false;
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////IZQUIERDA//////////////////////////////////////////
        if (direccion.equals("left")) {
            if ((y >= 0 && y < 500) && x <= 50
                    || y > 410 && ((x > 200 && x <= 250) || (x > 600 && x <= 650))
                    || (y > 340 && y < 450) && ((x > 400 && x <= 450) || (x > 800 && x <= 850))
                    || (y > 110 && y < 260) && ((x > 200 && x <= 250) || (x > 600 && x <= 650))
                    || ((y > 40 && y < 150)) && ((x > 400 && x <= 450) || (x > 800 && x <= 850))) {
                return limite = false;
            }

        }
        ///////////////////////////////////////////////////////////////////////////////////////////
        return limite;
    }


    private void jlVolverVolverMenu(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlVolverVolverMenu
        s = new Sonido("click.wav");
        dispose();
        new CrearAvatar(null, true);
    }//GEN-LAST:event_jlVolverVolverMenu

    private void jlControlGuiaControlGuia(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlControlGuiaControlGuia
        s = new Sonido("click.wav");
        t.interrupt();
        new GuiaControles(null, true, "");
    }//GEN-LAST:event_jlControlGuiaControlGuia

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fondo;
    private javax.swing.JPanel Informacion;
    private javax.swing.JLabel M1E1;
    private javax.swing.JLabel M1E2;
    private javax.swing.JLabel M1E3;
    private javax.swing.JPanel Mapa1;
    private javax.swing.JLabel Puntaje;
    private javax.swing.JLabel TransladorEntrada;
    private javax.swing.JLabel TransladorSalida;
    private javax.swing.JLabel jLAvatarMapa1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JLabel jlControlGuia;
    private javax.swing.JLabel jlPuntraje;
    private javax.swing.JLabel jlVolver;
    private javax.swing.JLabel jlvida1;
    private javax.swing.JLabel jlvida2;
    private javax.swing.JLabel jlvida3;
    private javax.swing.JLabel jtVida;
    // End of variables declaration//GEN-END:variables

}
