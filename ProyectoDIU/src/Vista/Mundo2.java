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
import javax.swing.JLabel;

/**
 *
 * @author Alejandra Becerra
 */
public class Mundo2 extends javax.swing.JDialog {

    /**
     * Creates new form Mundo_2
     */
    ControlTXT txt = new ControlTXT();
    Thread t;
    int hora, minuto, segundo;
    int cambio = 1;
    String[] dato;
    Sonido s;
    Boolean colision = false;
    String[] infoVida_Puntaje = new String[2];
    int[] posEnemigo = new int[4];
    int[] posAvatar = new int[4];
    String punt, vida, rutaCarpeta, mapaAct, nombre, nickName, imgpeque;
    JLabel tempEnemigo1 = new JLabel();
    JLabel tempEnemigo2 = new JLabel();
    JLabel tempEnemigo3 = new JLabel();
    controlXMLMundos leerMundos;
    Thread contEnemigos;

    public Mundo2(java.awt.Frame parent, boolean modal, String nickNameJugador, String imgPeque, String rutaCarpeta) {
        super(parent, modal);
        initComponents();
        initVidaPuntaje();
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
            jLAvatarMapa2.setIcon(new javax.swing.ImageIcon(getClass().getResource(imgPeque)));
        } else {
            if (nickNameJugador != null) {
                String predPequenia = "/Imagenes/Avatars/Usuarios/" + nickNameJugador + ".png";
                jLAvatarMapa1.setIcon(new javax.swing.ImageIcon(getClass().getResource(predPequenia)));
                jLAvatarMapa2.setIcon(new javax.swing.ImageIcon(getClass().getResource(predPequenia)));
            }
        }
        setVisible(true);
    }

    public Mundo2(String nada) {
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

    public void vidaJLabel(String[] inf){
        if(colision()){
            if(inf[0].equals("-1")){
            dispose();
            new GamerOver(null, true, Puntaje.getText());
            }
            if(inf[0].equals("0")){
                jlvida1.setVisible(false);
                jlvida2.setVisible(false);
                jlvida3.setVisible(false);

            }
             if(inf[0].equals("1")){
                jlvida1.setVisible(true);
                jlvida2.setVisible(false);
                jlvida3.setVisible(false);

            }
            if(inf[0].equals("2")){
                jlvida1.setVisible(true);
                jlvida2.setVisible(true);
                jlvida3.setVisible(false);

            }
            if(inf[0].equals("3")){
                jlvida1.setVisible(true);
                jlvida2.setVisible(true);
                jlvida3.setVisible(true);

            }
        }else{
            if(inf[0].equals("0")){
                jlvida1.setVisible(false);
                jlvida2.setVisible(false);
                jlvida3.setVisible(false);

            }
             if(inf[0].equals("1")){
                jlvida1.setVisible(true);
                jlvida2.setVisible(false);
                jlvida3.setVisible(false);

            }
            if(inf[0].equals("2")){
                jlvida1.setVisible(true);
                jlvida2.setVisible(true);
                jlvida3.setVisible(false);

            }
            if(inf[0].equals("3")){
                jlvida1.setVisible(true);
                jlvida2.setVisible(true);
                jlvida3.setVisible(true);

            }
        }
        
        vida=inf[0];
        this.punt=inf[1];
    }
    
    private void decidirMapa() throws Exception {
        leerMundos = new controlXMLMundos();
        String nombreMapa = null;
        int mapa = (int) (Math.random() * 2 + 1);
        if (mapa == 1) {
            Mapa1.setFocusable(true);
            Mapa1.setVisible(true);
            Mapa2.setFocusable(false);
            Mapa2.setVisible(false);
            nombreMapa = "mapa1";
        }
        if (mapa == 2) {
            Mapa1.setFocusable(false);
            Mapa1.setVisible(false);
            Mapa2.setFocusable(true);
            Mapa2.setVisible(true);
            nombreMapa = "mapa2";
        }
        mapaAct = nombreMapa;
        leerMundos.consultarXML("mundo2", nombreMapa);
        initEnemigos(nombreMapa);

    }

    private void initEnemigos(String nombMapa) {
        int cantEnemigos = leerMundos.getCantEnemigos();
        String[] skins = leerMundos.getSkinEnemigos();

        M1E1.setVisible(false);
        M1E2.setVisible(false);
        M1E3.setVisible(false);

        M2E1.setVisible(false);
        M2E2.setVisible(false);
        M2E3.setVisible(false);

        if (nombMapa.equals("mapa1")) {
            tempEnemigo1 = M1E1;
            tempEnemigo2 = M1E2;
            tempEnemigo3 = M1E3;
        }
        if (nombMapa.equals("mapa2")) {
            tempEnemigo1 = M2E1;
            tempEnemigo2 = M2E2;
            tempEnemigo3 = M2E3;
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
            contEnemigos = new ControlEnemigos(tempEnemigo1, "mundo2", nombMapa, SkinEnemigo1);
            contEnemigos.start();
        }
        if (cantEnemigos >= 2) {
            tempEnemigo2.setName("enemigo2");
            tempEnemigo2.setVisible(true);

            contEnemigos = new ControlEnemigos(tempEnemigo2, "mundo2", nombMapa, SkinEnemigo2);
            contEnemigos.start();
        }
        if (cantEnemigos >= 3) {
            tempEnemigo3.setName("enemigo3");
            tempEnemigo3.setVisible(true);
            contEnemigos = new ControlEnemigos(tempEnemigo3, "mundo2", nombMapa, SkinEnemigo3);
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
        } else {
            if ("mapa2".equals(mapaAct)) {
                posAvatar = obtenerposAvatar(jLAvatarMapa2);
            }
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
        new Mundo2(null, true, "a", "/Imagenes/Avatars/Avatar2/der1.jpg", "/Imagenes/Avatars/Avatar2");
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
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
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
        jLabel93 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        jLabel107 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        jLabel109 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        jLabel113 = new javax.swing.JLabel();
        jLabel114 = new javax.swing.JLabel();
        jLabel115 = new javax.swing.JLabel();
        jLabel116 = new javax.swing.JLabel();
        jLabel117 = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
        jLabel119 = new javax.swing.JLabel();
        jLabel120 = new javax.swing.JLabel();
        jLabel121 = new javax.swing.JLabel();
        jLabel122 = new javax.swing.JLabel();
        jLabel123 = new javax.swing.JLabel();
        jLabel124 = new javax.swing.JLabel();
        jLabel125 = new javax.swing.JLabel();
        jLabel126 = new javax.swing.JLabel();
        jLabel127 = new javax.swing.JLabel();
        jLabel128 = new javax.swing.JLabel();
        jLabel129 = new javax.swing.JLabel();
        jLabel130 = new javax.swing.JLabel();
        jLabel134 = new javax.swing.JLabel();
        jLabel135 = new javax.swing.JLabel();
        jLabel136 = new javax.swing.JLabel();
        jLabel137 = new javax.swing.JLabel();
        jLabel138 = new javax.swing.JLabel();
        Salida = new javax.swing.JLabel();
        jLAvatarMapa1 = new javax.swing.JLabel();
        M1E1 = new javax.swing.JLabel();
        M1E2 = new javax.swing.JLabel();
        M1E3 = new javax.swing.JLabel();
        Fondo = new javax.swing.JLabel();
        Mapa2 = new javax.swing.JPanel();
        jLabel92 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        jLabel131 = new javax.swing.JLabel();
        jLabel133 = new javax.swing.JLabel();
        jLabel139 = new javax.swing.JLabel();
        jLabel140 = new javax.swing.JLabel();
        jLabel141 = new javax.swing.JLabel();
        jLabel143 = new javax.swing.JLabel();
        jLabel144 = new javax.swing.JLabel();
        jLabel145 = new javax.swing.JLabel();
        jLabel146 = new javax.swing.JLabel();
        jLabel147 = new javax.swing.JLabel();
        jLabel148 = new javax.swing.JLabel();
        jLabel149 = new javax.swing.JLabel();
        jLabel150 = new javax.swing.JLabel();
        jLabel151 = new javax.swing.JLabel();
        jLabel152 = new javax.swing.JLabel();
        jLabel153 = new javax.swing.JLabel();
        jLabel154 = new javax.swing.JLabel();
        jLabel155 = new javax.swing.JLabel();
        jLabel156 = new javax.swing.JLabel();
        jLabel157 = new javax.swing.JLabel();
        jLabel158 = new javax.swing.JLabel();
        jLabel159 = new javax.swing.JLabel();
        jLabel160 = new javax.swing.JLabel();
        jLabel161 = new javax.swing.JLabel();
        jLabel162 = new javax.swing.JLabel();
        jLabel163 = new javax.swing.JLabel();
        jLabel164 = new javax.swing.JLabel();
        jLabel165 = new javax.swing.JLabel();
        jLabel166 = new javax.swing.JLabel();
        jLabel167 = new javax.swing.JLabel();
        jLabel168 = new javax.swing.JLabel();
        jLabel169 = new javax.swing.JLabel();
        jLabel170 = new javax.swing.JLabel();
        jLabel171 = new javax.swing.JLabel();
        jLabel172 = new javax.swing.JLabel();
        jLabel173 = new javax.swing.JLabel();
        jLabel174 = new javax.swing.JLabel();
        jLabel175 = new javax.swing.JLabel();
        jLabel176 = new javax.swing.JLabel();
        jLabel177 = new javax.swing.JLabel();
        jLabel178 = new javax.swing.JLabel();
        jLabel179 = new javax.swing.JLabel();
        jLabel180 = new javax.swing.JLabel();
        jLabel181 = new javax.swing.JLabel();
        jLabel182 = new javax.swing.JLabel();
        jLabel183 = new javax.swing.JLabel();
        jLabel184 = new javax.swing.JLabel();
        jLabel185 = new javax.swing.JLabel();
        jLabel186 = new javax.swing.JLabel();
        jLabel187 = new javax.swing.JLabel();
        jLabel188 = new javax.swing.JLabel();
        jLabel189 = new javax.swing.JLabel();
        jLabel190 = new javax.swing.JLabel();
        jLabel191 = new javax.swing.JLabel();
        jLabel192 = new javax.swing.JLabel();
        jLabel193 = new javax.swing.JLabel();
        jLabel194 = new javax.swing.JLabel();
        jLabel195 = new javax.swing.JLabel();
        jLabel196 = new javax.swing.JLabel();
        jLabel198 = new javax.swing.JLabel();
        jLabel199 = new javax.swing.JLabel();
        jLabel200 = new javax.swing.JLabel();
        jLabel201 = new javax.swing.JLabel();
        jLabel202 = new javax.swing.JLabel();
        jLabel203 = new javax.swing.JLabel();
        jLabel204 = new javax.swing.JLabel();
        jLabel205 = new javax.swing.JLabel();
        jLabel206 = new javax.swing.JLabel();
        jLabel207 = new javax.swing.JLabel();
        jLabel208 = new javax.swing.JLabel();
        jLabel209 = new javax.swing.JLabel();
        jLabel210 = new javax.swing.JLabel();
        jLabel211 = new javax.swing.JLabel();
        jLabel212 = new javax.swing.JLabel();
        jLabel213 = new javax.swing.JLabel();
        jLabel214 = new javax.swing.JLabel();
        jLabel215 = new javax.swing.JLabel();
        jLabel216 = new javax.swing.JLabel();
        jLabel217 = new javax.swing.JLabel();
        jLabel218 = new javax.swing.JLabel();
        jLabel219 = new javax.swing.JLabel();
        jLabel220 = new javax.swing.JLabel();
        jLabel221 = new javax.swing.JLabel();
        jLabel222 = new javax.swing.JLabel();
        jLabel223 = new javax.swing.JLabel();
        jLabel224 = new javax.swing.JLabel();
        jLabel225 = new javax.swing.JLabel();
        jLabel226 = new javax.swing.JLabel();
        jLabel227 = new javax.swing.JLabel();
        jLabel228 = new javax.swing.JLabel();
        jLabel229 = new javax.swing.JLabel();
        jLabel230 = new javax.swing.JLabel();
        jLabel231 = new javax.swing.JLabel();
        jLabel233 = new javax.swing.JLabel();
        jLabel234 = new javax.swing.JLabel();
        jLabel235 = new javax.swing.JLabel();
        jLabel237 = new javax.swing.JLabel();
        jLabel241 = new javax.swing.JLabel();
        jLabel243 = new javax.swing.JLabel();
        jLabel247 = new javax.swing.JLabel();
        jLabel253 = new javax.swing.JLabel();
        jLabel254 = new javax.swing.JLabel();
        jLabel255 = new javax.swing.JLabel();
        jLabel256 = new javax.swing.JLabel();
        jLabel257 = new javax.swing.JLabel();
        jLabel258 = new javax.swing.JLabel();
        jLabel251 = new javax.swing.JLabel();
        jLabel252 = new javax.swing.JLabel();
        jLabel236 = new javax.swing.JLabel();
        jLabel246 = new javax.swing.JLabel();
        jLabel248 = new javax.swing.JLabel();
        jLabel249 = new javax.swing.JLabel();
        jLabel250 = new javax.swing.JLabel();
        jLabel259 = new javax.swing.JLabel();
        jLabel261 = new javax.swing.JLabel();
        jLabel262 = new javax.swing.JLabel();
        jLAvatarMapa2 = new javax.swing.JLabel();
        M2E1 = new javax.swing.JLabel();
        M2E3 = new javax.swing.JLabel();
        M2E2 = new javax.swing.JLabel();
        Fondo1 = new javax.swing.JLabel();
        Informacion1 = new javax.swing.JPanel();
        jtVida1 = new javax.swing.JLabel();
        jLabel239 = new javax.swing.JLabel();
        jlvida1 = new javax.swing.JLabel();
        jlvida2 = new javax.swing.JLabel();
        jlvida3 = new javax.swing.JLabel();
        jlVolver = new javax.swing.JLabel();
        jlControlGuia = new javax.swing.JLabel();
        jlPuntraje = new javax.swing.JLabel();
        Puntaje = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        Mapa1.setMinimumSize(new java.awt.Dimension(1000, 800));
        Mapa1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Mapa1KeyPressed(evt);
            }
        });
        Mapa1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(255, 51, 51));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel1.setOpaque(true);
        Mapa1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 50, 50));

        jLabel2.setBackground(new java.awt.Color(255, 51, 51));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel2.setOpaque(true);
        Mapa1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 50, 50));

        jLabel3.setBackground(new java.awt.Color(255, 51, 51));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel3.setOpaque(true);
        Mapa1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 50, 50));

        jLabel4.setBackground(new java.awt.Color(255, 51, 51));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel4.setOpaque(true);
        Mapa1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 50, 50));

        jLabel5.setBackground(new java.awt.Color(255, 51, 51));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel5.setOpaque(true);
        Mapa1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 50, 50));

        jLabel6.setBackground(new java.awt.Color(255, 51, 51));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel6.setOpaque(true);
        Mapa1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 50, 50));

        jLabel7.setBackground(new java.awt.Color(255, 51, 51));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel7.setOpaque(true);
        Mapa1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 50, 50));

        jLabel8.setBackground(new java.awt.Color(255, 51, 51));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel8.setOpaque(true);
        Mapa1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 50, 50));

        jLabel9.setBackground(new java.awt.Color(255, 51, 51));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel9.setOpaque(true);
        Mapa1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, 50, 50));

        jLabel10.setBackground(new java.awt.Color(255, 51, 51));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel10.setOpaque(true);
        Mapa1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 50, 50));

        jLabel11.setBackground(new java.awt.Color(255, 51, 51));
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel11.setOpaque(true);
        Mapa1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 250, 50, 50));

        jLabel12.setBackground(new java.awt.Color(255, 51, 51));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel12.setOpaque(true);
        Mapa1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 550, 50, 50));

        jLabel13.setBackground(new java.awt.Color(255, 51, 51));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel13.setOpaque(true);
        Mapa1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 50, 50));

        jLabel14.setBackground(new java.awt.Color(255, 51, 51));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel14.setOpaque(true);
        Mapa1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, 50, 50));

        jLabel15.setBackground(new java.awt.Color(255, 51, 51));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel15.setOpaque(true);
        Mapa1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, 50, 50));

        jLabel16.setBackground(new java.awt.Color(255, 51, 51));
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel16.setOpaque(true);
        Mapa1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 50, 50));

        jLabel17.setBackground(new java.awt.Color(255, 51, 51));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel17.setOpaque(true);
        Mapa1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 50, 50));

        jLabel18.setBackground(new java.awt.Color(255, 51, 51));
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel18.setOpaque(true);
        Mapa1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 50, 50));

        jLabel19.setBackground(new java.awt.Color(255, 51, 51));
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel19.setOpaque(true);
        Mapa1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 0, 50, 50));

        jLabel20.setBackground(new java.awt.Color(255, 51, 51));
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel20.setOpaque(true);
        Mapa1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 0, 50, 50));

        jLabel21.setBackground(new java.awt.Color(255, 51, 51));
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel21.setOpaque(true);
        Mapa1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, 50, 50));

        jLabel22.setBackground(new java.awt.Color(255, 51, 51));
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel22.setOpaque(true);
        Mapa1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 0, 50, 50));

        jLabel23.setBackground(new java.awt.Color(255, 51, 51));
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel23.setOpaque(true);
        Mapa1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 0, 50, 50));

        jLabel24.setBackground(new java.awt.Color(255, 51, 51));
        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel24.setOpaque(true);
        Mapa1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 0, 50, 50));

        jLabel25.setBackground(new java.awt.Color(255, 51, 51));
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel25.setOpaque(true);
        Mapa1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 0, 50, 50));

        jLabel26.setBackground(new java.awt.Color(255, 51, 51));
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel26.setOpaque(true);
        Mapa1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 0, 50, 50));

        jLabel27.setBackground(new java.awt.Color(255, 51, 51));
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel27.setOpaque(true);
        Mapa1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 0, 50, 50));

        jLabel28.setBackground(new java.awt.Color(255, 51, 51));
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel28.setOpaque(true);
        Mapa1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 0, 50, 50));

        jLabel29.setBackground(new java.awt.Color(255, 51, 51));
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel29.setOpaque(true);
        Mapa1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 0, 50, 50));

        jLabel30.setBackground(new java.awt.Color(255, 51, 51));
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel30.setOpaque(true);
        Mapa1.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 0, 50, 50));

        jLabel31.setBackground(new java.awt.Color(255, 51, 51));
        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel31.setOpaque(true);
        Mapa1.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 0, 50, 50));

        jLabel32.setBackground(new java.awt.Color(255, 51, 51));
        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel32.setOpaque(true);
        Mapa1.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 50, 50, 50));

        jLabel33.setBackground(new java.awt.Color(255, 51, 51));
        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel33.setOpaque(true);
        Mapa1.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 100, 50, 50));

        jLabel34.setBackground(new java.awt.Color(255, 51, 51));
        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel34.setOpaque(true);
        Mapa1.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 150, 50, 50));

        jLabel35.setBackground(new java.awt.Color(255, 51, 51));
        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel35.setOpaque(true);
        Mapa1.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 200, 50, 50));

        jLabel36.setBackground(new java.awt.Color(255, 51, 51));
        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel36.setOpaque(true);
        Mapa1.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 250, 50, 50));

        jLabel37.setBackground(new java.awt.Color(255, 51, 51));
        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel37.setOpaque(true);
        Mapa1.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 300, 50, 50));

        jLabel38.setBackground(new java.awt.Color(255, 51, 51));
        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel38.setOpaque(true);
        Mapa1.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 350, 50, 50));

        jLabel39.setBackground(new java.awt.Color(255, 51, 51));
        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel39.setOpaque(true);
        Mapa1.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 400, 50, 50));

        jLabel40.setBackground(new java.awt.Color(255, 51, 51));
        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel40.setOpaque(true);
        Mapa1.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 450, 50, 50));

        jLabel41.setBackground(new java.awt.Color(255, 51, 51));
        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel41.setOpaque(true);
        Mapa1.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 500, 50, 50));

        jLabel42.setBackground(new java.awt.Color(255, 51, 51));
        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel42.setOpaque(true);
        Mapa1.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 550, 50, 50));

        jLabel43.setBackground(new java.awt.Color(255, 51, 51));
        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel43.setOpaque(true);
        Mapa1.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 550, 50, 50));

        jLabel44.setBackground(new java.awt.Color(255, 51, 51));
        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel44.setOpaque(true);
        Mapa1.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 550, 50, 50));

        jLabel45.setBackground(new java.awt.Color(255, 51, 51));
        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel45.setOpaque(true);
        Mapa1.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 550, 50, 50));

        jLabel46.setBackground(new java.awt.Color(255, 51, 51));
        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel46.setOpaque(true);
        Mapa1.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 550, 50, 50));

        jLabel47.setBackground(new java.awt.Color(255, 51, 51));
        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel47.setOpaque(true);
        Mapa1.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 550, 50, 50));

        jLabel48.setBackground(new java.awt.Color(255, 51, 51));
        jLabel48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel48.setOpaque(true);
        Mapa1.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 550, 50, 50));

        jLabel49.setBackground(new java.awt.Color(255, 51, 51));
        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel49.setOpaque(true);
        Mapa1.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 100, 50, 50));

        jLabel50.setBackground(new java.awt.Color(255, 51, 51));
        jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel50.setOpaque(true);
        Mapa1.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 550, 50, 50));

        jLabel51.setBackground(new java.awt.Color(255, 51, 51));
        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel51.setOpaque(true);
        Mapa1.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 550, 50, 50));

        jLabel52.setBackground(new java.awt.Color(255, 51, 51));
        jLabel52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel52.setOpaque(true);
        Mapa1.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 550, 50, 50));

        jLabel53.setBackground(new java.awt.Color(255, 51, 51));
        jLabel53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel53.setOpaque(true);
        Mapa1.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 550, 50, 50));

        jLabel54.setBackground(new java.awt.Color(255, 51, 51));
        jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel54.setOpaque(true);
        Mapa1.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 550, 50, 50));

        jLabel55.setBackground(new java.awt.Color(255, 51, 51));
        jLabel55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel55.setOpaque(true);
        Mapa1.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 550, 50, 50));

        jLabel56.setBackground(new java.awt.Color(255, 51, 51));
        jLabel56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel56.setOpaque(true);
        Mapa1.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 550, 50, 50));

        jLabel57.setBackground(new java.awt.Color(255, 51, 51));
        jLabel57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel57.setOpaque(true);
        Mapa1.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 550, 50, 50));

        jLabel58.setBackground(new java.awt.Color(255, 51, 51));
        jLabel58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel58.setOpaque(true);
        Mapa1.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 550, 50, 50));

        jLabel59.setBackground(new java.awt.Color(255, 51, 51));
        jLabel59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel59.setOpaque(true);
        Mapa1.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 550, 50, 50));

        jLabel60.setBackground(new java.awt.Color(255, 51, 51));
        jLabel60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel60.setOpaque(true);
        Mapa1.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 550, 50, 50));

        jLabel61.setBackground(new java.awt.Color(255, 51, 51));
        jLabel61.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel61.setOpaque(true);
        Mapa1.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 450, 50, 50));

        jLabel62.setBackground(new java.awt.Color(255, 51, 51));
        jLabel62.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel62.setOpaque(true);
        Mapa1.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 550, 50, 50));

        jLabel63.setBackground(new java.awt.Color(255, 51, 51));
        jLabel63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel63.setOpaque(true);
        Mapa1.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 50, 50));

        jLabel65.setBackground(new java.awt.Color(255, 51, 51));
        jLabel65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel65.setOpaque(true);
        Mapa1.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 100, 50, 50));

        jLabel66.setBackground(new java.awt.Color(255, 51, 51));
        jLabel66.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel66.setOpaque(true);
        Mapa1.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, 50, 50));

        jLabel67.setBackground(new java.awt.Color(255, 51, 51));
        jLabel67.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel67.setOpaque(true);
        Mapa1.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 200, 50, 50));

        jLabel68.setBackground(new java.awt.Color(255, 51, 51));
        jLabel68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel68.setOpaque(true);
        Mapa1.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 300, 50, 50));

        jLabel69.setBackground(new java.awt.Color(255, 51, 51));
        jLabel69.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel69.setOpaque(true);
        Mapa1.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 200, 50, 50));

        jLabel70.setBackground(new java.awt.Color(255, 51, 51));
        jLabel70.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel70.setOpaque(true);
        Mapa1.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 250, 50, 50));

        jLabel71.setBackground(new java.awt.Color(255, 51, 51));
        jLabel71.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel71.setOpaque(true);
        Mapa1.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 300, 50, 50));

        jLabel72.setBackground(new java.awt.Color(255, 51, 51));
        jLabel72.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel72.setOpaque(true);
        Mapa1.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 350, 50, 50));

        jLabel73.setBackground(new java.awt.Color(255, 51, 51));
        jLabel73.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel73.setOpaque(true);
        Mapa1.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 400, 50, 50));

        jLabel74.setBackground(new java.awt.Color(255, 51, 51));
        jLabel74.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel74.setOpaque(true);
        Mapa1.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 450, 50, 50));

        jLabel75.setBackground(new java.awt.Color(255, 51, 51));
        jLabel75.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel75.setOpaque(true);
        Mapa1.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, 50, 50));

        jLabel76.setBackground(new java.awt.Color(255, 51, 51));
        jLabel76.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel76.setOpaque(true);
        Mapa1.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 450, 50, 50));

        jLabel77.setBackground(new java.awt.Color(255, 51, 51));
        jLabel77.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel77.setOpaque(true);
        Mapa1.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 450, 50, 50));

        jLabel78.setBackground(new java.awt.Color(255, 51, 51));
        jLabel78.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel78.setOpaque(true);
        Mapa1.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 450, 50, 50));

        jLabel79.setBackground(new java.awt.Color(255, 51, 51));
        jLabel79.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel79.setOpaque(true);
        Mapa1.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 450, 50, 50));

        jLabel80.setBackground(new java.awt.Color(255, 51, 51));
        jLabel80.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel80.setOpaque(true);
        Mapa1.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 450, 50, 50));

        jLabel81.setBackground(new java.awt.Color(255, 51, 51));
        jLabel81.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel81.setOpaque(true);
        Mapa1.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 450, 50, 50));

        jLabel82.setBackground(new java.awt.Color(255, 51, 51));
        jLabel82.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel82.setOpaque(true);
        Mapa1.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 450, 50, 50));

        jLabel83.setBackground(new java.awt.Color(255, 51, 51));
        jLabel83.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel83.setOpaque(true);
        Mapa1.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 450, 50, 50));

        jLabel84.setBackground(new java.awt.Color(255, 51, 51));
        jLabel84.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel84.setOpaque(true);
        Mapa1.add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 450, 50, 50));

        jLabel85.setBackground(new java.awt.Color(255, 51, 51));
        jLabel85.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel85.setOpaque(true);
        Mapa1.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 450, 50, 50));

        jLabel86.setBackground(new java.awt.Color(255, 51, 51));
        jLabel86.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel86.setOpaque(true);
        Mapa1.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 450, 50, 50));

        jLabel87.setBackground(new java.awt.Color(255, 51, 51));
        jLabel87.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel87.setOpaque(true);
        Mapa1.add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 450, 50, 50));

        jLabel88.setBackground(new java.awt.Color(255, 51, 51));
        jLabel88.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel88.setOpaque(true);
        Mapa1.add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 450, 50, 50));

        jLabel89.setBackground(new java.awt.Color(255, 51, 51));
        jLabel89.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel89.setOpaque(true);
        Mapa1.add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 450, 50, 50));

        jLabel90.setBackground(new java.awt.Color(255, 51, 51));
        jLabel90.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel90.setOpaque(true);
        Mapa1.add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 400, 50, 50));

        jLabel91.setBackground(new java.awt.Color(255, 51, 51));
        jLabel91.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel91.setOpaque(true);
        Mapa1.add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, 50, 50));

        jLabel93.setBackground(new java.awt.Color(255, 51, 51));
        jLabel93.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel93.setOpaque(true);
        Mapa1.add(jLabel93, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 350, 50, 50));

        jLabel94.setBackground(new java.awt.Color(255, 51, 51));
        jLabel94.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel94.setOpaque(true);
        Mapa1.add(jLabel94, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 350, 50, 50));

        jLabel96.setBackground(new java.awt.Color(255, 51, 51));
        jLabel96.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel96.setOpaque(true);
        Mapa1.add(jLabel96, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 350, 50, 50));

        jLabel97.setBackground(new java.awt.Color(255, 51, 51));
        jLabel97.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel97.setOpaque(true);
        Mapa1.add(jLabel97, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 200, 50, 50));

        jLabel99.setBackground(new java.awt.Color(255, 51, 51));
        jLabel99.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel99.setOpaque(true);
        Mapa1.add(jLabel99, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 350, 50, 50));

        jLabel100.setBackground(new java.awt.Color(255, 51, 51));
        jLabel100.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel100.setOpaque(true);
        Mapa1.add(jLabel100, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 350, 50, 50));

        jLabel102.setBackground(new java.awt.Color(255, 51, 51));
        jLabel102.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel102.setOpaque(true);
        Mapa1.add(jLabel102, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 350, 50, 50));

        jLabel103.setBackground(new java.awt.Color(255, 51, 51));
        jLabel103.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel103.setOpaque(true);
        Mapa1.add(jLabel103, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, 50, 50));

        jLabel104.setBackground(new java.awt.Color(255, 51, 51));
        jLabel104.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel104.setOpaque(true);
        Mapa1.add(jLabel104, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 100, 50, 50));

        jLabel105.setBackground(new java.awt.Color(255, 51, 51));
        jLabel105.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel105.setOpaque(true);
        Mapa1.add(jLabel105, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 100, 50, 50));

        jLabel106.setBackground(new java.awt.Color(255, 51, 51));
        jLabel106.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel106.setOpaque(true);
        Mapa1.add(jLabel106, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 100, 50, 50));

        jLabel107.setBackground(new java.awt.Color(255, 51, 51));
        jLabel107.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel107.setOpaque(true);
        Mapa1.add(jLabel107, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 100, 50, 50));

        jLabel108.setBackground(new java.awt.Color(255, 51, 51));
        jLabel108.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel108.setOpaque(true);
        Mapa1.add(jLabel108, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 100, 50, 50));

        jLabel109.setBackground(new java.awt.Color(255, 51, 51));
        jLabel109.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel109.setOpaque(true);
        Mapa1.add(jLabel109, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 100, 50, 50));

        jLabel110.setBackground(new java.awt.Color(255, 51, 51));
        jLabel110.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel110.setOpaque(true);
        Mapa1.add(jLabel110, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 100, 50, 50));

        jLabel111.setBackground(new java.awt.Color(255, 51, 51));
        jLabel111.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel111.setOpaque(true);
        Mapa1.add(jLabel111, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 100, 50, 50));

        jLabel112.setBackground(new java.awt.Color(255, 51, 51));
        jLabel112.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel112.setOpaque(true);
        Mapa1.add(jLabel112, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 100, 50, 50));

        jLabel113.setBackground(new java.awt.Color(255, 51, 51));
        jLabel113.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel113.setOpaque(true);
        Mapa1.add(jLabel113, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 200, 50, 50));

        jLabel114.setBackground(new java.awt.Color(255, 51, 51));
        jLabel114.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel114.setOpaque(true);
        Mapa1.add(jLabel114, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 100, 50, 50));

        jLabel115.setBackground(new java.awt.Color(255, 51, 51));
        jLabel115.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel115.setOpaque(true);
        Mapa1.add(jLabel115, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 350, 50, 50));

        jLabel116.setBackground(new java.awt.Color(255, 51, 51));
        jLabel116.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel116.setOpaque(true);
        Mapa1.add(jLabel116, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 300, 50, 50));

        jLabel117.setBackground(new java.awt.Color(255, 51, 51));
        jLabel117.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel117.setOpaque(true);
        Mapa1.add(jLabel117, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 250, 50, 50));

        jLabel118.setBackground(new java.awt.Color(255, 51, 51));
        jLabel118.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel118.setOpaque(true);
        Mapa1.add(jLabel118, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 200, 50, 50));

        jLabel119.setBackground(new java.awt.Color(255, 51, 51));
        jLabel119.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel119.setOpaque(true);
        Mapa1.add(jLabel119, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 150, 50, 50));

        jLabel120.setBackground(new java.awt.Color(255, 51, 51));
        jLabel120.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel120.setOpaque(true);
        Mapa1.add(jLabel120, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 300, 50, 50));

        jLabel121.setBackground(new java.awt.Color(255, 51, 51));
        jLabel121.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel121.setOpaque(true);
        Mapa1.add(jLabel121, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 250, 50, 50));

        jLabel122.setBackground(new java.awt.Color(255, 51, 51));
        jLabel122.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel122.setOpaque(true);
        Mapa1.add(jLabel122, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 200, 50, 50));

        jLabel123.setBackground(new java.awt.Color(255, 51, 51));
        jLabel123.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel123.setOpaque(true);
        Mapa1.add(jLabel123, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 200, 50, 50));

        jLabel124.setBackground(new java.awt.Color(255, 51, 51));
        jLabel124.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel124.setOpaque(true);
        Mapa1.add(jLabel124, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 200, 50, 50));

        jLabel125.setBackground(new java.awt.Color(255, 51, 51));
        jLabel125.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel125.setOpaque(true);
        Mapa1.add(jLabel125, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 300, 50, 50));

        jLabel126.setBackground(new java.awt.Color(255, 51, 51));
        jLabel126.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel126.setOpaque(true);
        Mapa1.add(jLabel126, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 200, 50, 50));

        jLabel127.setBackground(new java.awt.Color(255, 51, 51));
        jLabel127.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel127.setOpaque(true);
        Mapa1.add(jLabel127, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 200, 50, 50));

        jLabel128.setBackground(new java.awt.Color(255, 51, 51));
        jLabel128.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel128.setOpaque(true);
        Mapa1.add(jLabel128, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 200, 50, 50));

        jLabel129.setBackground(new java.awt.Color(255, 51, 51));
        jLabel129.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel129.setOpaque(true);
        Mapa1.add(jLabel129, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 200, 50, 50));

        jLabel130.setBackground(new java.awt.Color(255, 51, 51));
        jLabel130.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel130.setOpaque(true);
        Mapa1.add(jLabel130, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 350, 50, 50));

        jLabel134.setBackground(new java.awt.Color(255, 51, 51));
        jLabel134.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel134.setOpaque(true);
        Mapa1.add(jLabel134, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 200, 50, 50));

        jLabel135.setBackground(new java.awt.Color(255, 51, 51));
        jLabel135.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel135.setOpaque(true);
        Mapa1.add(jLabel135, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 200, 50, 50));

        jLabel136.setBackground(new java.awt.Color(255, 51, 51));
        jLabel136.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel136.setOpaque(true);
        Mapa1.add(jLabel136, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 250, 50, 50));

        jLabel137.setBackground(new java.awt.Color(255, 51, 51));
        jLabel137.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel137.setOpaque(true);
        Mapa1.add(jLabel137, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 250, 50, 50));

        jLabel138.setBackground(new java.awt.Color(255, 51, 51));
        jLabel138.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        jLabel138.setOpaque(true);
        Mapa1.add(jLabel138, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 300, 50, 50));

        Salida.setBackground(new java.awt.Color(0, 0, 0));
        Salida.setText("jLabel64");
        Salida.setOpaque(true);
        Mapa1.add(Salida, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 280, 50, 50));

        jLAvatarMapa1.setBackground(new java.awt.Color(255, 153, 153));
        jLAvatarMapa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/avatar.png"))); // NOI18N
        jLAvatarMapa1.setOpaque(true);
        Mapa1.add(jLAvatarMapa1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 500, 40, 40));

        M1E1.setBackground(new java.awt.Color(204, 204, 204));
        M1E1.setOpaque(true);
        Mapa1.add(M1E1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 60, 40, 40));

        M1E2.setBackground(new java.awt.Color(255, 0, 0));
        M1E2.setOpaque(true);
        Mapa1.add(M1E2, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 400, 40, 40));

        M1E3.setBackground(new java.awt.Color(0, 102, 102));
        M1E3.setOpaque(true);
        Mapa1.add(M1E3, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 300, 40, 40));

        Fondo.setBackground(new java.awt.Color(255, 51, 51));
        Fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoVerde.png"))); // NOI18N
        Fondo.setOpaque(true);
        Mapa1.add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 600));

        getContentPane().add(Mapa1);
        Mapa1.setBounds(0, 100, 1000, 600);

        Mapa2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Mapa2KeyPressed(evt);
            }
        });
        Mapa2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel92.setBackground(new java.awt.Color(255, 51, 51));
        jLabel92.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel92.setOpaque(true);
        Mapa2.add(jLabel92, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 50, 50));

        jLabel95.setBackground(new java.awt.Color(255, 51, 51));
        jLabel95.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel95.setOpaque(true);
        Mapa2.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 50, 50));

        jLabel98.setBackground(new java.awt.Color(255, 51, 51));
        jLabel98.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel98.setOpaque(true);
        Mapa2.add(jLabel98, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 50, 50));

        jLabel101.setBackground(new java.awt.Color(255, 51, 51));
        jLabel101.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel101.setOpaque(true);
        Mapa2.add(jLabel101, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 50, 50));

        jLabel131.setBackground(new java.awt.Color(255, 51, 51));
        jLabel131.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel131.setOpaque(true);
        Mapa2.add(jLabel131, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 50, 50));

        jLabel133.setBackground(new java.awt.Color(255, 51, 51));
        jLabel133.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel133.setOpaque(true);
        Mapa2.add(jLabel133, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 50, 50));

        jLabel139.setBackground(new java.awt.Color(255, 51, 51));
        jLabel139.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel139.setOpaque(true);
        Mapa2.add(jLabel139, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 50, 50));

        jLabel140.setBackground(new java.awt.Color(255, 51, 51));
        jLabel140.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel140.setOpaque(true);
        Mapa2.add(jLabel140, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, 50, 50));

        jLabel141.setBackground(new java.awt.Color(255, 51, 51));
        jLabel141.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel141.setOpaque(true);
        Mapa2.add(jLabel141, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 50, 50));

        jLabel143.setBackground(new java.awt.Color(255, 51, 51));
        jLabel143.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel143.setOpaque(true);
        Mapa2.add(jLabel143, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 550, 50, 50));

        jLabel144.setBackground(new java.awt.Color(255, 51, 51));
        jLabel144.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel144.setOpaque(true);
        Mapa2.add(jLabel144, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 0, 50, 50));

        jLabel145.setBackground(new java.awt.Color(255, 51, 51));
        jLabel145.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel145.setOpaque(true);
        Mapa2.add(jLabel145, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 50, 50, 50));

        jLabel146.setBackground(new java.awt.Color(255, 51, 51));
        jLabel146.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel146.setOpaque(true);
        Mapa2.add(jLabel146, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 100, 50, 50));

        jLabel147.setBackground(new java.awt.Color(255, 51, 51));
        jLabel147.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel147.setOpaque(true);
        Mapa2.add(jLabel147, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 150, 50, 50));

        jLabel148.setBackground(new java.awt.Color(255, 51, 51));
        jLabel148.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel148.setOpaque(true);
        Mapa2.add(jLabel148, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 200, 50, 50));

        jLabel149.setBackground(new java.awt.Color(255, 51, 51));
        jLabel149.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel149.setOpaque(true);
        Mapa2.add(jLabel149, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 250, 50, 50));

        jLabel150.setBackground(new java.awt.Color(255, 51, 51));
        jLabel150.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel150.setOpaque(true);
        Mapa2.add(jLabel150, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 300, 50, 50));

        jLabel151.setBackground(new java.awt.Color(255, 51, 51));
        jLabel151.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel151.setOpaque(true);
        Mapa2.add(jLabel151, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 350, 50, 50));

        jLabel152.setBackground(new java.awt.Color(255, 51, 51));
        jLabel152.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel152.setOpaque(true);
        Mapa2.add(jLabel152, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 400, 50, 50));

        jLabel153.setBackground(new java.awt.Color(255, 51, 51));
        jLabel153.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel153.setOpaque(true);
        Mapa2.add(jLabel153, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 450, 50, 50));

        jLabel154.setBackground(new java.awt.Color(255, 51, 51));
        jLabel154.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel154.setOpaque(true);
        Mapa2.add(jLabel154, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 500, 50, 50));

        jLabel155.setBackground(new java.awt.Color(255, 51, 51));
        jLabel155.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel155.setOpaque(true);
        Mapa2.add(jLabel155, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 550, 50, 50));

        jLabel156.setBackground(new java.awt.Color(255, 51, 51));
        jLabel156.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel156.setOpaque(true);
        Mapa2.add(jLabel156, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 50, 50));

        jLabel157.setBackground(new java.awt.Color(255, 51, 51));
        jLabel157.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel157.setOpaque(true);
        Mapa2.add(jLabel157, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 150, 50, 50));

        jLabel158.setBackground(new java.awt.Color(255, 51, 51));
        jLabel158.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel158.setOpaque(true);
        Mapa2.add(jLabel158, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, 50, 50));

        jLabel159.setBackground(new java.awt.Color(255, 51, 51));
        jLabel159.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel159.setOpaque(true);
        Mapa2.add(jLabel159, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 50, 50));

        jLabel160.setBackground(new java.awt.Color(255, 51, 51));
        jLabel160.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel160.setOpaque(true);
        Mapa2.add(jLabel160, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 50, 50));

        jLabel161.setBackground(new java.awt.Color(255, 51, 51));
        jLabel161.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel161.setOpaque(true);
        Mapa2.add(jLabel161, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 50, 50));

        jLabel162.setBackground(new java.awt.Color(255, 51, 51));
        jLabel162.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel162.setOpaque(true);
        Mapa2.add(jLabel162, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 0, 50, 50));

        jLabel163.setBackground(new java.awt.Color(255, 51, 51));
        jLabel163.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel163.setOpaque(true);
        Mapa2.add(jLabel163, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 0, 50, 50));

        jLabel164.setBackground(new java.awt.Color(255, 51, 51));
        jLabel164.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel164.setOpaque(true);
        Mapa2.add(jLabel164, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, 50, 50));

        jLabel165.setBackground(new java.awt.Color(255, 51, 51));
        jLabel165.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel165.setOpaque(true);
        Mapa2.add(jLabel165, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 0, 50, 50));

        jLabel166.setBackground(new java.awt.Color(255, 51, 51));
        jLabel166.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel166.setOpaque(true);
        Mapa2.add(jLabel166, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 0, 50, 50));

        jLabel167.setBackground(new java.awt.Color(255, 51, 51));
        jLabel167.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel167.setOpaque(true);
        Mapa2.add(jLabel167, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 0, 50, 50));

        jLabel168.setBackground(new java.awt.Color(255, 51, 51));
        jLabel168.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel168.setOpaque(true);
        Mapa2.add(jLabel168, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 0, 50, 50));

        jLabel169.setBackground(new java.awt.Color(255, 51, 51));
        jLabel169.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel169.setOpaque(true);
        Mapa2.add(jLabel169, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 0, 50, 50));

        jLabel170.setBackground(new java.awt.Color(255, 51, 51));
        jLabel170.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel170.setOpaque(true);
        Mapa2.add(jLabel170, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 0, 50, 50));

        jLabel171.setBackground(new java.awt.Color(255, 51, 51));
        jLabel171.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel171.setOpaque(true);
        Mapa2.add(jLabel171, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 0, 50, 50));

        jLabel172.setBackground(new java.awt.Color(255, 51, 51));
        jLabel172.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel172.setOpaque(true);
        Mapa2.add(jLabel172, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 0, 50, 50));

        jLabel173.setBackground(new java.awt.Color(255, 51, 51));
        jLabel173.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel173.setOpaque(true);
        Mapa2.add(jLabel173, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 0, 50, 50));

        jLabel174.setBackground(new java.awt.Color(255, 51, 51));
        jLabel174.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel174.setOpaque(true);
        Mapa2.add(jLabel174, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 550, 50, 50));

        jLabel175.setBackground(new java.awt.Color(255, 51, 51));
        jLabel175.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel175.setOpaque(true);
        Mapa2.add(jLabel175, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 550, 50, 50));

        jLabel176.setBackground(new java.awt.Color(255, 51, 51));
        jLabel176.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel176.setOpaque(true);
        Mapa2.add(jLabel176, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 550, 50, 50));

        jLabel177.setBackground(new java.awt.Color(255, 51, 51));
        jLabel177.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel177.setOpaque(true);
        Mapa2.add(jLabel177, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 550, 50, 50));

        jLabel178.setBackground(new java.awt.Color(255, 51, 51));
        jLabel178.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel178.setOpaque(true);
        Mapa2.add(jLabel178, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 550, 50, 50));

        jLabel179.setBackground(new java.awt.Color(255, 51, 51));
        jLabel179.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel179.setOpaque(true);
        Mapa2.add(jLabel179, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 550, 50, 50));

        jLabel180.setBackground(new java.awt.Color(255, 51, 51));
        jLabel180.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel180.setOpaque(true);
        Mapa2.add(jLabel180, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 550, 50, 50));

        jLabel181.setBackground(new java.awt.Color(255, 51, 51));
        jLabel181.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel181.setOpaque(true);
        Mapa2.add(jLabel181, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 550, 50, 50));

        jLabel182.setBackground(new java.awt.Color(255, 51, 51));
        jLabel182.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel182.setOpaque(true);
        Mapa2.add(jLabel182, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 550, 50, 50));

        jLabel183.setBackground(new java.awt.Color(255, 51, 51));
        jLabel183.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel183.setOpaque(true);
        Mapa2.add(jLabel183, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 550, 50, 50));

        jLabel184.setBackground(new java.awt.Color(255, 51, 51));
        jLabel184.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel184.setOpaque(true);
        Mapa2.add(jLabel184, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 550, 50, 50));

        jLabel185.setBackground(new java.awt.Color(255, 51, 51));
        jLabel185.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel185.setOpaque(true);
        Mapa2.add(jLabel185, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 550, 50, 50));

        jLabel186.setBackground(new java.awt.Color(255, 51, 51));
        jLabel186.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel186.setOpaque(true);
        Mapa2.add(jLabel186, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 550, 50, 50));

        jLabel187.setBackground(new java.awt.Color(255, 51, 51));
        jLabel187.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel187.setOpaque(true);
        Mapa2.add(jLabel187, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 550, 50, 50));

        jLabel188.setBackground(new java.awt.Color(255, 51, 51));
        jLabel188.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel188.setOpaque(true);
        Mapa2.add(jLabel188, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 550, 50, 50));

        jLabel189.setBackground(new java.awt.Color(255, 51, 51));
        jLabel189.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel189.setOpaque(true);
        Mapa2.add(jLabel189, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 550, 50, 50));

        jLabel190.setBackground(new java.awt.Color(255, 51, 51));
        jLabel190.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel190.setOpaque(true);
        Mapa2.add(jLabel190, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 550, 50, 50));

        jLabel191.setBackground(new java.awt.Color(255, 51, 51));
        jLabel191.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel191.setOpaque(true);
        Mapa2.add(jLabel191, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 550, 50, 50));

        jLabel192.setBackground(new java.awt.Color(255, 51, 51));
        jLabel192.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel192.setOpaque(true);
        Mapa2.add(jLabel192, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 50, 50));

        jLabel193.setBackground(new java.awt.Color(255, 51, 51));
        jLabel193.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel193.setOpaque(true);
        Mapa2.add(jLabel193, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 500, 50, 50));

        jLabel194.setBackground(new java.awt.Color(255, 51, 51));
        jLabel194.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel194.setOpaque(true);
        Mapa2.add(jLabel194, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 500, 50, 50));

        jLabel195.setBackground(new java.awt.Color(255, 51, 51));
        jLabel195.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel195.setOpaque(true);
        Mapa2.add(jLabel195, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 450, 50, 50));

        jLabel196.setBackground(new java.awt.Color(255, 51, 51));
        jLabel196.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel196.setOpaque(true);
        Mapa2.add(jLabel196, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, 50, 50));

        jLabel198.setBackground(new java.awt.Color(255, 51, 51));
        jLabel198.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel198.setOpaque(true);
        Mapa2.add(jLabel198, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 450, 50, 50));

        jLabel199.setBackground(new java.awt.Color(255, 51, 51));
        jLabel199.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel199.setOpaque(true);
        Mapa2.add(jLabel199, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 200, 50, 50));

        jLabel200.setBackground(new java.awt.Color(255, 51, 51));
        jLabel200.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel200.setOpaque(true);
        Mapa2.add(jLabel200, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 250, 50, 50));

        jLabel201.setBackground(new java.awt.Color(255, 51, 51));
        jLabel201.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel201.setOpaque(true);
        Mapa2.add(jLabel201, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 300, 50, 50));

        jLabel202.setBackground(new java.awt.Color(255, 51, 51));
        jLabel202.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel202.setOpaque(true);
        Mapa2.add(jLabel202, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 400, 50, 50));

        jLabel203.setBackground(new java.awt.Color(255, 51, 51));
        jLabel203.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel203.setOpaque(true);
        Mapa2.add(jLabel203, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, 50, 50));

        jLabel204.setBackground(new java.awt.Color(255, 51, 51));
        jLabel204.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel204.setOpaque(true);
        Mapa2.add(jLabel204, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, 50, 50));

        jLabel205.setBackground(new java.awt.Color(255, 51, 51));
        jLabel205.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel205.setOpaque(true);
        Mapa2.add(jLabel205, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 150, 50, 50));

        jLabel206.setBackground(new java.awt.Color(255, 51, 51));
        jLabel206.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel206.setOpaque(true);
        Mapa2.add(jLabel206, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 150, 50, 50));

        jLabel207.setBackground(new java.awt.Color(255, 51, 51));
        jLabel207.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel207.setOpaque(true);
        Mapa2.add(jLabel207, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 150, 50, 50));

        jLabel208.setBackground(new java.awt.Color(255, 51, 51));
        jLabel208.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel208.setOpaque(true);
        Mapa2.add(jLabel208, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 150, 50, 50));

        jLabel209.setBackground(new java.awt.Color(255, 51, 51));
        jLabel209.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel209.setOpaque(true);
        Mapa2.add(jLabel209, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 150, 50, 50));

        jLabel210.setBackground(new java.awt.Color(255, 51, 51));
        jLabel210.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel210.setOpaque(true);
        Mapa2.add(jLabel210, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 150, 50, 50));

        jLabel211.setBackground(new java.awt.Color(255, 51, 51));
        jLabel211.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel211.setOpaque(true);
        Mapa2.add(jLabel211, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 150, 50, 50));

        jLabel212.setBackground(new java.awt.Color(255, 51, 51));
        jLabel212.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel212.setOpaque(true);
        Mapa2.add(jLabel212, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 150, 50, 50));

        jLabel213.setBackground(new java.awt.Color(255, 51, 51));
        jLabel213.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel213.setOpaque(true);
        Mapa2.add(jLabel213, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 150, 50, 50));

        jLabel214.setBackground(new java.awt.Color(255, 51, 51));
        jLabel214.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel214.setOpaque(true);
        Mapa2.add(jLabel214, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 150, 50, 50));

        jLabel215.setBackground(new java.awt.Color(255, 51, 51));
        jLabel215.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel215.setOpaque(true);
        Mapa2.add(jLabel215, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 200, 50, 50));

        jLabel216.setBackground(new java.awt.Color(255, 51, 51));
        jLabel216.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel216.setOpaque(true);
        Mapa2.add(jLabel216, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 250, 50, 50));

        jLabel217.setBackground(new java.awt.Color(255, 51, 51));
        jLabel217.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel217.setOpaque(true);
        Mapa2.add(jLabel217, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 300, 50, 50));

        jLabel218.setBackground(new java.awt.Color(255, 51, 51));
        jLabel218.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel218.setOpaque(true);
        Mapa2.add(jLabel218, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 400, 50, 50));

        jLabel219.setBackground(new java.awt.Color(255, 51, 51));
        jLabel219.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel219.setOpaque(true);
        Mapa2.add(jLabel219, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 400, 50, 50));

        jLabel220.setBackground(new java.awt.Color(255, 51, 51));
        jLabel220.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel220.setOpaque(true);
        Mapa2.add(jLabel220, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 450, 50, 50));

        jLabel221.setBackground(new java.awt.Color(255, 51, 51));
        jLabel221.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel221.setOpaque(true);
        Mapa2.add(jLabel221, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 250, 50, 50));

        jLabel222.setBackground(new java.awt.Color(255, 51, 51));
        jLabel222.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel222.setOpaque(true);
        Mapa2.add(jLabel222, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 350, 50, 50));

        jLabel223.setBackground(new java.awt.Color(255, 51, 51));
        jLabel223.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel223.setOpaque(true);
        Mapa2.add(jLabel223, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 400, 50, 50));

        jLabel224.setBackground(new java.awt.Color(255, 51, 51));
        jLabel224.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel224.setOpaque(true);
        Mapa2.add(jLabel224, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 400, 50, 50));

        jLabel225.setBackground(new java.awt.Color(255, 51, 51));
        jLabel225.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel225.setOpaque(true);
        Mapa2.add(jLabel225, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 400, 50, 50));

        jLabel226.setBackground(new java.awt.Color(255, 51, 51));
        jLabel226.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel226.setOpaque(true);
        Mapa2.add(jLabel226, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 400, 50, 50));

        jLabel227.setBackground(new java.awt.Color(255, 51, 51));
        jLabel227.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel227.setOpaque(true);
        Mapa2.add(jLabel227, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 400, 50, 50));

        jLabel228.setBackground(new java.awt.Color(255, 51, 51));
        jLabel228.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel228.setOpaque(true);
        Mapa2.add(jLabel228, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 400, 50, 50));

        jLabel229.setBackground(new java.awt.Color(255, 51, 51));
        jLabel229.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel229.setOpaque(true);
        Mapa2.add(jLabel229, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 400, 50, 50));

        jLabel230.setBackground(new java.awt.Color(255, 51, 51));
        jLabel230.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel230.setOpaque(true);
        Mapa2.add(jLabel230, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 50, 50));

        jLabel231.setBackground(new java.awt.Color(255, 51, 51));
        jLabel231.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel231.setOpaque(true);
        Mapa2.add(jLabel231, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 350, 50, 50));

        jLabel233.setBackground(new java.awt.Color(255, 51, 51));
        jLabel233.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel233.setOpaque(true);
        Mapa2.add(jLabel233, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 500, 50, 50));

        jLabel234.setBackground(new java.awt.Color(255, 51, 51));
        jLabel234.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel234.setOpaque(true);
        Mapa2.add(jLabel234, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 50, 50));

        jLabel235.setBackground(new java.awt.Color(255, 51, 51));
        jLabel235.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel235.setOpaque(true);
        Mapa2.add(jLabel235, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 450, 50, 50));

        jLabel237.setBackground(new java.awt.Color(255, 51, 51));
        jLabel237.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel237.setOpaque(true);
        Mapa2.add(jLabel237, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 100, 50, 50));

        jLabel241.setBackground(new java.awt.Color(255, 51, 51));
        jLabel241.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel241.setOpaque(true);
        Mapa2.add(jLabel241, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 150, 50, 50));

        jLabel243.setBackground(new java.awt.Color(255, 51, 51));
        jLabel243.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel243.setOpaque(true);
        Mapa2.add(jLabel243, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 50, 50, 50));

        jLabel247.setBackground(new java.awt.Color(255, 51, 51));
        jLabel247.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel247.setOpaque(true);
        Mapa2.add(jLabel247, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 400, 50, 50));

        jLabel253.setBackground(new java.awt.Color(255, 51, 51));
        jLabel253.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel253.setOpaque(true);
        Mapa2.add(jLabel253, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, 50, 50));

        jLabel254.setBackground(new java.awt.Color(255, 51, 51));
        jLabel254.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel254.setOpaque(true);
        Mapa2.add(jLabel254, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 300, 50, 50));

        jLabel255.setBackground(new java.awt.Color(255, 51, 51));
        jLabel255.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel255.setOpaque(true);
        Mapa2.add(jLabel255, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 250, 50, 50));

        jLabel256.setBackground(new java.awt.Color(255, 51, 51));
        jLabel256.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel256.setOpaque(true);
        Mapa2.add(jLabel256, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 350, 50, 50));

        jLabel257.setBackground(new java.awt.Color(255, 51, 51));
        jLabel257.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel257.setOpaque(true);
        Mapa2.add(jLabel257, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 150, 50, 50));

        jLabel258.setBackground(new java.awt.Color(255, 51, 51));
        jLabel258.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel258.setOpaque(true);
        Mapa2.add(jLabel258, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 100, 50, 50));

        jLabel251.setBackground(new java.awt.Color(0, 0, 0));
        jLabel251.setOpaque(true);
        Mapa2.add(jLabel251, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 350, 50, 50));

        jLabel252.setBackground(new java.awt.Color(255, 51, 51));
        jLabel252.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel252.setOpaque(true);
        Mapa2.add(jLabel252, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 400, 50, 50));

        jLabel236.setBackground(new java.awt.Color(255, 51, 51));
        jLabel236.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel236.setOpaque(true);
        Mapa2.add(jLabel236, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 300, 50, 50));

        jLabel246.setBackground(new java.awt.Color(255, 51, 51));
        jLabel246.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel246.setOpaque(true);
        Mapa2.add(jLabel246, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 250, 50, 50));

        jLabel248.setBackground(new java.awt.Color(255, 51, 51));
        jLabel248.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel248.setOpaque(true);
        Mapa2.add(jLabel248, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 250, 50, 50));

        jLabel249.setBackground(new java.awt.Color(255, 51, 51));
        jLabel249.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel249.setOpaque(true);
        Mapa2.add(jLabel249, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 250, 50, 50));

        jLabel250.setBackground(new java.awt.Color(255, 51, 51));
        jLabel250.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel250.setOpaque(true);
        Mapa2.add(jLabel250, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 250, 50, 50));

        jLabel259.setBackground(new java.awt.Color(255, 51, 51));
        jLabel259.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel259.setOpaque(true);
        Mapa2.add(jLabel259, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 250, 50, 50));

        jLabel261.setBackground(new java.awt.Color(255, 51, 51));
        jLabel261.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel261.setOpaque(true);
        Mapa2.add(jLabel261, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 250, 50, 50));

        jLabel262.setBackground(new java.awt.Color(255, 51, 51));
        jLabel262.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoArbusto.png"))); // NOI18N
        jLabel262.setOpaque(true);
        Mapa2.add(jLabel262, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 250, 50, 50));

        jLAvatarMapa2.setBackground(new java.awt.Color(204, 51, 255));
        jLAvatarMapa2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/avatar.png"))); // NOI18N
        jLAvatarMapa2.setOpaque(true);
        Mapa2.add(jLAvatarMapa2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 510, 40, 40));

        M2E1.setBackground(new java.awt.Color(255, 0, 0));
        M2E1.setOpaque(true);
        Mapa2.add(M2E1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 40, 40));

        M2E3.setBackground(new java.awt.Color(0, 102, 102));
        M2E3.setOpaque(true);
        Mapa2.add(M2E3, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 350, 40, 40));

        M2E2.setBackground(new java.awt.Color(204, 204, 204));
        M2E2.setOpaque(true);
        Mapa2.add(M2E2, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 450, 40, 40));

        Fondo1.setBackground(new java.awt.Color(204, 51, 255));
        Fondo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoTierra.jpg"))); // NOI18N
        Fondo1.setOpaque(true);
        Mapa2.add(Fondo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 600));

        getContentPane().add(Mapa2);
        Mapa2.setBounds(0, 100, 1000, 590);

        Informacion1.setBackground(new java.awt.Color(204, 204, 255));
        Informacion1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtVida1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jtVida1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jtVida1.setText("Vida");
        Informacion1.add(jtVida1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 60, 90, 30));

        jLabel239.setFont(new java.awt.Font("Tempus Sans ITC", 3, 36)); // NOI18N
        jLabel239.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel239.setText("NIVEL 2");
        Informacion1.add(jLabel239, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 0, 240, 50));

        jlvida1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/vida.png"))); // NOI18N
        jlvida1.setText("jLabel2");
        Informacion1.add(jlvida1, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 60, 40, 30));

        jlvida2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/vida.png"))); // NOI18N
        jlvida2.setText("jLabel2");
        Informacion1.add(jlvida2, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 60, 40, 30));

        jlvida3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/vida.png"))); // NOI18N
        jlvida3.setText("jLabel2");
        Informacion1.add(jlvida3, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 60, 40, 30));

        jlVolver.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlVolver.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Atras.png"))); // NOI18N
        jlVolver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jlVolverVolverMenu(evt);
            }
        });
        Informacion1.add(jlVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 80, 40));

        jlControlGuia.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlControlGuia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlControlGuia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Ayuda.png"))); // NOI18N
        jlControlGuia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jlControlGuiaControlGuia(evt);
            }
        });
        Informacion1.add(jlControlGuia, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 0, 80, 60));

        jlPuntraje.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlPuntraje.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlPuntraje.setText("Puntaje");
        Informacion1.add(jlPuntraje, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 80, 40));

        Puntaje.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        Puntaje.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Puntaje.setText("----------");
        Informacion1.add(Puntaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 60, 100, 20));

        getContentPane().add(Informacion1);
        Informacion1.setBounds(0, 0, 1000, 100);

        setSize(new java.awt.Dimension(1014, 739));
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
                    if ((x >= 440 && y >= 228) && (x <= 510 && y <= 290)) {
                        if (!colision) {
                            infoVida_Puntaje[0] = vida;
                            infoVida_Puntaje[1] = punt;

                            txt.puntaje_vida(infoVida_Puntaje);

                        }
                        t.interrupt();

                        Sonido s = new Sonido("cambioMundo.wav");
                        dispose();
                        new Mundo3(null, true, nickName, imgpeque, rutaCarpeta);
                    }
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (limitesM1(x, y, "right")) {
                    if (rutaCarpeta != null) {
                        alternarImg(2, jLAvatarMapa1);
                    }
                    jLAvatarMapa1.setLocation(x + desplazamiento, y);
                    if ((x >= 440 && y >= 228) && (x <= 510 && y <= 290)) {

                        if (!colision) {

                            infoVida_Puntaje[0] = vida;
                            infoVida_Puntaje[1] = punt;

                            txt.puntaje_vida(infoVida_Puntaje);

                        }
                        t.interrupt();
                        Sonido s = new Sonido("cambioMundo.wav");
                        dispose();
                        new Mundo3(null, true, nickName, imgpeque, rutaCarpeta);
                    }
                }
                break;
            case KeyEvent.VK_W:

                if (limitesM1(x, y, "up")) {
                    if (rutaCarpeta != null) {
                        alternarImg(1, jLAvatarMapa1);
                    }
                    jLAvatarMapa1.setLocation(x, y - desplazamiento);
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
                    if ((x >= 440 && y >= 228) && (x <= 510 && y <= 290)) {

                        if (!colision) {
                            infoVida_Puntaje[0] = vida;
                            infoVida_Puntaje[1] = punt;

                            txt.puntaje_vida(infoVida_Puntaje);

                        }
                        t.interrupt();
                        Sonido s = new Sonido("cambioMundo.wav");
                        dispose();
                        new Mundo3(null, true, nickName, imgpeque, rutaCarpeta);
                    }
                }
                break;
            case KeyEvent.VK_D:
                if (limitesM1(x, y, "right")) {
                    if (rutaCarpeta != null) {
                        alternarImg(2, jLAvatarMapa1);
                    }
                    jLAvatarMapa1.setLocation(x + desplazamiento, y);
                    if ((x >= 440 && y >= 228) && (x <= 510 && y <= 290)) {
                        if (!colision) {
                            infoVida_Puntaje[0] = vida;
                            infoVida_Puntaje[1] = punt;

                            txt.puntaje_vida(infoVida_Puntaje);

                        }
                        t.interrupt();
                        Sonido s = new Sonido("cambioMundo.wav");
                        dispose();
                        new Mundo3(null, true, nickName, imgpeque, rutaCarpeta);
                    }
                }
                break;
        }
        if(colision()){
            infoVida_Puntaje[0] = "" + (Integer.parseInt(vida) - 1);
            infoVida_Puntaje[1] = "" + (Integer.parseInt(punt) - 50);
            vidaJLabel(infoVida_Puntaje);
            Puntaje.setText(infoVida_Puntaje[1]);
            txt.puntaje_vida(infoVida_Puntaje);
        }
        
    }//GEN-LAST:event_Mapa1KeyPressed

    private void Mapa2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Mapa2KeyPressed
        int desplazamiento = 10, x = jLAvatarMapa2.getX(), y = jLAvatarMapa2.getY();

        switch (evt.getExtendedKeyCode()) {

            case KeyEvent.VK_UP:
                if (limitesM2(x, y, "up")) {
                    if (rutaCarpeta != null) {
                        alternarImg(1, jLAvatarMapa2);
                    }
                    jLAvatarMapa2.setLocation(x, y - desplazamiento);
                }
                break;

            case KeyEvent.VK_DOWN:
                if (limitesM2(x, y, "down")) {
                    if (rutaCarpeta != null) {
                        alternarImg(3, jLAvatarMapa2);
                    }
                    jLAvatarMapa2.setLocation(x, y + desplazamiento);
                    if ((x >= 350 && y >= 200) && (x <= 350 && y <= 360)) {
                        if (!colision) {
                            infoVida_Puntaje[0] = vida;
                            infoVida_Puntaje[1] = punt;

                            txt.puntaje_vida(infoVida_Puntaje);

                        }
                        t.interrupt();

                        Sonido s = new Sonido("cambioMundo.wav");
                        dispose();
                        new Mundo3(null, true, nickName, imgpeque, rutaCarpeta);
                    }
                }
                break;
            case KeyEvent.VK_LEFT:
                if (limitesM2(x, y, "left")) {
                    if (rutaCarpeta != null) {
                        alternarImg(4, jLAvatarMapa2);
                    }
                    jLAvatarMapa2.setLocation(x - desplazamiento, y);
                    if ((x >= 350 && y >= 360) && (x <= 390 && y <= 360)) {

                        if (!colision) {
                            infoVida_Puntaje[0] = vida;
                            infoVida_Puntaje[1] = punt;

                            txt.puntaje_vida(infoVida_Puntaje);

                        }
                        t.interrupt();

                        Sonido s = new Sonido("cambioMundo.wav");
                        dispose();
                        new Mundo3(null, true, nickName, imgpeque, rutaCarpeta);
                    }
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (limitesM2(x, y, "right")) {
                    if (rutaCarpeta != null) {
                        alternarImg(2, jLAvatarMapa2);
                    }
                    jLAvatarMapa2.setLocation(x + desplazamiento, y);
                }
                break;
            case KeyEvent.VK_W:
                if (limitesM2(x, y, "up")) {
                    if (rutaCarpeta != null) {
                        alternarImg(1, jLAvatarMapa2);
                    }
                    jLAvatarMapa2.setLocation(x, y - desplazamiento);
                }
                break;

            case KeyEvent.VK_S:
                if (limitesM2(x, y, "down")) {
                    if (rutaCarpeta != null) {
                        alternarImg(3, jLAvatarMapa2);
                    }
                    jLAvatarMapa2.setLocation(x, y + desplazamiento);
                    if ((x >= 350 && y >= 200) && (x <= 350 && y <= 360)) {

                        if (!colision) {
                            infoVida_Puntaje[0] = vida;
                            infoVida_Puntaje[1] = punt;

                            txt.puntaje_vida(infoVida_Puntaje);

                        }

                        Sonido s = new Sonido("cambioMundo.wav");
                        dispose();
                        new Mundo3(null, true, nickName, imgpeque, rutaCarpeta);
                    }
                }
                break;
            case KeyEvent.VK_A:
                if (limitesM2(x, y, "left")) {
                    if (rutaCarpeta != null) {
                        alternarImg(4, jLAvatarMapa2);
                    }
                    jLAvatarMapa2.setLocation(x - desplazamiento, y);
                    if ((x >= 350 && y >= 360) && (x <= 390 && y <= 360)) {
                        if (!colision) {
                            infoVida_Puntaje[0] = vida;
                            infoVida_Puntaje[1] = punt;

                            txt.puntaje_vida(infoVida_Puntaje);

                        }
                        t.interrupt();
                        Sonido s = new Sonido("cambioMundo.wav");
                        dispose();
                        new Mundo3(null, true, nickName, imgpeque, rutaCarpeta);
                    }
                }
                break;
            case KeyEvent.VK_D:
                if (limitesM2(x, y, "right")) {
                    if (rutaCarpeta != null) {
                        alternarImg(2, jLAvatarMapa2);
                    }
                    jLAvatarMapa2.setLocation(x + desplazamiento, y);
                }
                break;
        }
        if(colision()){
            infoVida_Puntaje[0] = "" + (Integer.parseInt(vida) - 1);
            infoVida_Puntaje[1] = "" + (Integer.parseInt(punt) - 50);
            vidaJLabel(infoVida_Puntaje);
            Puntaje.setText(infoVida_Puntaje[1]);
            txt.puntaje_vida(infoVida_Puntaje);
        }

    }//GEN-LAST:event_Mapa2KeyPressed

    public boolean limitesM1(int x, int y, String direccion) {
        boolean limite = true;
        if (direccion.equals("up")) {
            if ((x <= 920 && (y > 0 && y <= 50))
                    || ((x <= 40)) && (y > 450 && y <= 500)
                    || (x > 60 && x < 900) && (y > 450 && y <= 500)
                    || ((x >= 170 && x <= 240) || (x >= 270 && x <= 390) || (x >= 420 && x <= 540) || (x >= 570 && x <= 690) || (x >= 720 && x <= 790)) && (y > 350 && y <= 400)
                    || ((x >= 240 && x <= 310) || (x >= 400 && x <= 560) || (x >= 650 && x <= 710)) && (y > 200 && y <= 250)
                    || ((x >= 150 && x <= 690) || (x >= 720 && x <= 810)) && (y > 100 && y <= 150)) {
                return limite = false;
            }
        }
        if (direccion.equals("down")) {
            if (x >= 0 && (y >= 510 && y < 560)
                    || ((x >= 130 && x <= 810)) && (y >= 410 && y < 460)
                    || ((x >= 270 && x <= 320) || (x >= 420 && x <= 540) || (x >= 640 && x <= 690)) && (y >= 310 && y < 360)
                    || ((x >= 170 && x <= 790)) && (y >= 160 && y < 210)
                    || ((x >= 70 && x <= 690) || (x >= 720 && x <= 890)) && (y >= 60 && y < 110)) {
                return limite = false;
            }
        }
        if (direccion.equals("right")) {
            if ((y >= 70 && y <= 490) && (x >= 60 && x < 110)
                    || (y >= 170 && y <= 390) && (x >= 160 && y < 210)
                    || (((y >= 70 && y <= 140) || (y >= 250 && y <= 390)) && (x >= 710 && x < 760))
                    || ((y >= 250 && y <= 390) && (x >= 560 && x < 600))
                    || ((y >= 200 && y <= 390) && (x >= 160 && x < 210))
                    || ((y >= 150 && y <= 410) && (x >= 810 && x < 850))
                    || ((y >= 250 && y <= 310) && (x >= 310 && x < 360))
                    || ((y >= 320 && y <= 390) && ((x >= 410 && x < 460) || (x >= 260 && x < 310)))
                    || (y >= 50 && (x >= 910 && x < 960))) {
                return limite = false;
            }
        }
        if (direccion.equals("left")) {
            if ((y >= 0 && y <= 490) && x <= 50
                    || (y >= 0 && y <= 520) && x == 0
                    || (y >= 150 && y <= 410) && (x > 100 && x <= 150)
                    || (y >= 250 && y <= 390) && ((x > 200 && x <= 250) || (x > 350 && x <= 400))
                    || (y >= 250 && y <= 310) && (x > 600 && x <= 650)
                    || (y >= 320 && y <= 390) && ((x > 500 && x <= 550) || (x > 650 && x <= 700))
                    || (y >= 70 && y <= 140) && (x > 650 && x <= 700)
                    || (y >= 170 && y <= 390) && (x > 750 && x <= 800)
                    || (y >= 70 && y <= 490) && (x > 850 && x <= 900)) {
                return limite = false;
            }
        }
        return limite;
    }

    public boolean limitesM2(int x, int y, String direccion) {
        boolean limite = true;
        if (direccion.equals("up")) {
            if (((x >= 40 && x <= 920) && y <= 50)
                    || (((x >= 170 && x <= 240) || (x >= 570 && x <= 640)) && y <= 100)
                    || ((x <= 90 || (x >= 200 && x <= 760) || x >= 870) && (y >= 140 && y <= 200))
                    || (((x >= 350 && x <= 690) || (x >= 850 && x <= 890)) && (y >= 250 && y < 310))
                    || (x >= 70 && x <= 120) && (y >= 310 && y <= 350)
                    || (x >= 870 && (y >= 350 && y <= 400))
                    || ((x >= 270 && x <= 840) && (y > 400 && y <= 450))
                    || ((x <= 90 || (x >= 200 && x <= 240) || (x >= 570 && x <= 640)) && (y >= 460 && y <= 500))) {
                return limite = false;
            }
        }
        if (direccion.equals("down")) {

            if (((x <= 90 || (x >= 120 && x <= 760) || (x > 860 && x < 920)) && (y >= 110 && y < 140))
                    || ((x >= 370 && x < 450) && (y >= 60 && y < 100))
                    || (((x >= 270 && x <= 690) || (x >= 850 && x <= 890)) && (y >= 210 && y < 300))
                    || ((x >= 350 && x < 770) && (y >= 360 && y < 400))
                    || ((x >= 70 && x < 120) && (y >= 260 && y < 300))
                    || (((x >= 50 && x < 90) || (x >= 200 && x < 250)) && (y >= 410 && y < 460))
                    || (((x >= 370 && x < 450) || (x >= 770 && x < 850)) && (y >= 460 && y < 500))
                    || ((x >= 10 && x <= 910) && y >= 510)
                    || ((x >= 870 && x <= 910) && (y >= 310 && y < 350))
                    || ((x >= 770 && x <= 840) && (y >= 60 && y < 110))) {
                return limite = false;
            }
        }
        if (direccion.equals("right")) {
            if ((y <= 90 && (x >= 160 && x < 210))
                    || ((y >= 120 && y <= 510) && (x >= 110 && x < 150))
                    || ((y >= 270 && y <= 340) && (x >= 60 && x < 100))
                    || ((y >= 220 && y <= 440) && (x >= 260 && x < 300))
                    || (((y >= 70 && y <= 110) || (y >= 470 && y <= 510)) && (x >= 360 && x < 410))
                    || (((y >= 50 && y <= 90) || (y >= 450 && y <= 490)) && (x >= 560 && x < 610))
                    || (((y >= 70 && y <= 110) || (y >= 470 && y <= 510) || (y >= 200 && y <= 360)) && (x >= 760 && x < 810))
                    || (((y >= 120 && y <= 190) || (y >= 320 && y <= 390)) && x >= 860)
                    || ((y >= 50 && y <= 510) && x >= 910)) {
                return limite = false;
            }
        }
        if (direccion.equals("left")) {
            if ((y >= 50 && y <= 410) && x <= 50
                    || ((y >= 120 && y <= 190) || (y >= 420 && y <= 490)) && (x > 50 && x <= 100)
                    || ((y >= 50 && y <= 90) || (y >= 420 && y <= 490)) && (x > 210 && x <= 250)
                    || (y >= 200 && y <= 510) && (x > 180 && x <= 200)
                    || ((y >= 70 && y <= 110) || (y >= 470 && y <= 510)) && (x > 400 && x <= 450)
                    || ((y >= 50 && y <= 90) || (y >= 450 && y <= 490)) && (x > 600 && x <= 650)
                    || (y >= 300 && y <= 360) && (x > 300 && x <= 350)
                    || (y >= 220 && y <= 290) && (x > 590 && x <= 700)
                    || (y >= 70 && y <= 440) && (x > 800 && x <= 850)
                    || ((y >= 70 && y <= 440) || (y >= 470 && y <= 510)) && (x > 800 && x <= 850)
                    || (y >= 220 && y <= 300) && (x > 860 && x <= 900)) {
                return limite = false;
            }
        }
        return limite;
    }


    private void jlTiempovolverMenu(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlTiempovolverMenu
        s = new Sonido("click.wav");
        dispose();
    }//GEN-LAST:event_jlTiempovolverMenu

    private void jlPuntrajeControlGuia(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlPuntrajeControlGuia
        s = new Sonido("click.wav");
        new GuiaControles(null, true, "");
    }//GEN-LAST:event_jlPuntrajeControlGuia

    private void jlVolverVolverMenu(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlVolverVolverMenu
        s = new Sonido("click.wav");
        dispose();
    }//GEN-LAST:event_jlVolverVolverMenu

    private void jlControlGuiaControlGuia(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlControlGuiaControlGuia
        s = new Sonido("click.wav");
        new GuiaControles(null, true, "");
    }//GEN-LAST:event_jlControlGuiaControlGuia


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fondo;
    private javax.swing.JLabel Fondo1;
    private javax.swing.JPanel Informacion1;
    private javax.swing.JLabel M1E1;
    private javax.swing.JLabel M1E2;
    private javax.swing.JLabel M1E3;
    private javax.swing.JLabel M2E1;
    private javax.swing.JLabel M2E2;
    private javax.swing.JLabel M2E3;
    private javax.swing.JPanel Mapa1;
    private javax.swing.JPanel Mapa2;
    private javax.swing.JLabel Puntaje;
    private javax.swing.JLabel Salida;
    private javax.swing.JLabel jLAvatarMapa1;
    private javax.swing.JLabel jLAvatarMapa2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel143;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel145;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel147;
    private javax.swing.JLabel jLabel148;
    private javax.swing.JLabel jLabel149;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel150;
    private javax.swing.JLabel jLabel151;
    private javax.swing.JLabel jLabel152;
    private javax.swing.JLabel jLabel153;
    private javax.swing.JLabel jLabel154;
    private javax.swing.JLabel jLabel155;
    private javax.swing.JLabel jLabel156;
    private javax.swing.JLabel jLabel157;
    private javax.swing.JLabel jLabel158;
    private javax.swing.JLabel jLabel159;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel160;
    private javax.swing.JLabel jLabel161;
    private javax.swing.JLabel jLabel162;
    private javax.swing.JLabel jLabel163;
    private javax.swing.JLabel jLabel164;
    private javax.swing.JLabel jLabel165;
    private javax.swing.JLabel jLabel166;
    private javax.swing.JLabel jLabel167;
    private javax.swing.JLabel jLabel168;
    private javax.swing.JLabel jLabel169;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel170;
    private javax.swing.JLabel jLabel171;
    private javax.swing.JLabel jLabel172;
    private javax.swing.JLabel jLabel173;
    private javax.swing.JLabel jLabel174;
    private javax.swing.JLabel jLabel175;
    private javax.swing.JLabel jLabel176;
    private javax.swing.JLabel jLabel177;
    private javax.swing.JLabel jLabel178;
    private javax.swing.JLabel jLabel179;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel180;
    private javax.swing.JLabel jLabel181;
    private javax.swing.JLabel jLabel182;
    private javax.swing.JLabel jLabel183;
    private javax.swing.JLabel jLabel184;
    private javax.swing.JLabel jLabel185;
    private javax.swing.JLabel jLabel186;
    private javax.swing.JLabel jLabel187;
    private javax.swing.JLabel jLabel188;
    private javax.swing.JLabel jLabel189;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel190;
    private javax.swing.JLabel jLabel191;
    private javax.swing.JLabel jLabel192;
    private javax.swing.JLabel jLabel193;
    private javax.swing.JLabel jLabel194;
    private javax.swing.JLabel jLabel195;
    private javax.swing.JLabel jLabel196;
    private javax.swing.JLabel jLabel198;
    private javax.swing.JLabel jLabel199;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel200;
    private javax.swing.JLabel jLabel201;
    private javax.swing.JLabel jLabel202;
    private javax.swing.JLabel jLabel203;
    private javax.swing.JLabel jLabel204;
    private javax.swing.JLabel jLabel205;
    private javax.swing.JLabel jLabel206;
    private javax.swing.JLabel jLabel207;
    private javax.swing.JLabel jLabel208;
    private javax.swing.JLabel jLabel209;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel210;
    private javax.swing.JLabel jLabel211;
    private javax.swing.JLabel jLabel212;
    private javax.swing.JLabel jLabel213;
    private javax.swing.JLabel jLabel214;
    private javax.swing.JLabel jLabel215;
    private javax.swing.JLabel jLabel216;
    private javax.swing.JLabel jLabel217;
    private javax.swing.JLabel jLabel218;
    private javax.swing.JLabel jLabel219;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel220;
    private javax.swing.JLabel jLabel221;
    private javax.swing.JLabel jLabel222;
    private javax.swing.JLabel jLabel223;
    private javax.swing.JLabel jLabel224;
    private javax.swing.JLabel jLabel225;
    private javax.swing.JLabel jLabel226;
    private javax.swing.JLabel jLabel227;
    private javax.swing.JLabel jLabel228;
    private javax.swing.JLabel jLabel229;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel230;
    private javax.swing.JLabel jLabel231;
    private javax.swing.JLabel jLabel233;
    private javax.swing.JLabel jLabel234;
    private javax.swing.JLabel jLabel235;
    private javax.swing.JLabel jLabel236;
    private javax.swing.JLabel jLabel237;
    private javax.swing.JLabel jLabel239;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel241;
    private javax.swing.JLabel jLabel243;
    private javax.swing.JLabel jLabel246;
    private javax.swing.JLabel jLabel247;
    private javax.swing.JLabel jLabel248;
    private javax.swing.JLabel jLabel249;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel250;
    private javax.swing.JLabel jLabel251;
    private javax.swing.JLabel jLabel252;
    private javax.swing.JLabel jLabel253;
    private javax.swing.JLabel jLabel254;
    private javax.swing.JLabel jLabel255;
    private javax.swing.JLabel jLabel256;
    private javax.swing.JLabel jLabel257;
    private javax.swing.JLabel jLabel258;
    private javax.swing.JLabel jLabel259;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel261;
    private javax.swing.JLabel jLabel262;
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
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JLabel jlControlGuia;
    private javax.swing.JLabel jlPuntraje;
    private javax.swing.JLabel jlVolver;
    private javax.swing.JLabel jlvida1;
    private javax.swing.JLabel jlvida2;
    private javax.swing.JLabel jlvida3;
    private javax.swing.JLabel jtVida1;
    // End of variables declaration//GEN-END:variables

}
