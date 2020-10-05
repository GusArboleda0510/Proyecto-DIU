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
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

public class Mundo1 extends javax.swing.JDialog {

    /**
     * Creates new form Mundo_1
     */
    Thread contEnemigos;
    Color colorea = new Color(240, 240, 240);
    ControlTXT txt = new ControlTXT();
    Tiempo t;
    Sonido s;
    controlJugabilidad jug;
    int cambio = 1;
    String[] infoVida_Puntaje;
    int[] posEnemigo = new int[4];
    int[] posAvatar = new int[4];
    controlXMLMundos leerMundos;
    String puntaje, vida, rutaCarpeta, mapaAct, nombre, nickName, imgpeque;
    JLabel tempEnemigo1 = new JLabel();
    JLabel tempEnemigo2 = new JLabel();
    JLabel tempEnemigo3 = new JLabel();
    int var = 0;

    public Mundo1(java.awt.Frame parent, boolean modal, String nickNameJugador, String imgPeque, String rutaCarpeta) {
        super(parent, modal);
        initComponents();
        this.nickName = nickNameJugador;
        this.rutaCarpeta = rutaCarpeta;
        this.imgpeque = imgPeque;   
//        System.out.println("nickName " + nickName);
//        System.out.println("rutaCarpeta " + rutaCarpeta);
//        System.out.println("imgpeque " + imgpeque);
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
        txt.crearNickName(nickNameJugador);
        t = new Tiempo(null);
        t.start();

        jug = new controlJugabilidad(jlVida1, jlVida2, jlVida3, Puntaje);
        infoVida_Puntaje = jug.vida(false);
        txt.puntaje_vida(infoVida_Puntaje);

        setVisible(true);
    }

    public Mundo1(String nada) {
        // NO BORRAR ESTE CONSTRUCTOR
    }

    private void decidirMapa() throws Exception {
        leerMundos = new controlXMLMundos();
        String nombreMapa = null;
        int mapa = (int) (Math.random() * 2 + 1);
//        mapa = 1;
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

        leerMundos.consultarXML("mundo1", nombreMapa);
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
            contEnemigos = new ControlEnemigos(tempEnemigo1, "mundo1", nombMapa, SkinEnemigo1);
            contEnemigos.start();
        }
        if (cantEnemigos >= 2) {
            tempEnemigo2.setName("enemigo2");
            tempEnemigo2.setVisible(true);
            contEnemigos = new ControlEnemigos(tempEnemigo2, "mundo1", nombMapa, SkinEnemigo2);
            contEnemigos.start();
        }
        if (cantEnemigos >= 3) { 
            tempEnemigo3.setName("enemigo3");
            tempEnemigo3.setVisible(true);
            contEnemigos = new ControlEnemigos(tempEnemigo3, "mundo1", nombMapa, SkinEnemigo3);
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
        new Mundo1(null, true, "a", "/Imagenes/Avatars/Avatar2/der1.jpg", "/Imagenes/Avatars/Avatar2");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Mapa1 = new javax.swing.JPanel();
        j3 = new javax.swing.JLabel();
        j13 = new javax.swing.JLabel();
        j16 = new javax.swing.JLabel();
        j19 = new javax.swing.JLabel();
        j22 = new javax.swing.JLabel();
        j25 = new javax.swing.JLabel();
        j8 = new javax.swing.JLabel();
        j29 = new javax.swing.JLabel();
        h1 = new javax.swing.JLabel();
        j59 = new javax.swing.JLabel();
        j60 = new javax.swing.JLabel();
        j61 = new javax.swing.JLabel();
        j62 = new javax.swing.JLabel();
        j63 = new javax.swing.JLabel();
        j64 = new javax.swing.JLabel();
        j65 = new javax.swing.JLabel();
        j66 = new javax.swing.JLabel();
        j67 = new javax.swing.JLabel();
        j68 = new javax.swing.JLabel();
        j69 = new javax.swing.JLabel();
        j70 = new javax.swing.JLabel();
        j71 = new javax.swing.JLabel();
        j72 = new javax.swing.JLabel();
        j73 = new javax.swing.JLabel();
        j74 = new javax.swing.JLabel();
        j75 = new javax.swing.JLabel();
        j4 = new javax.swing.JLabel();
        j28 = new javax.swing.JLabel();
        j30 = new javax.swing.JLabel();
        j31 = new javax.swing.JLabel();
        j32 = new javax.swing.JLabel();
        j33 = new javax.swing.JLabel();
        j34 = new javax.swing.JLabel();
        j35 = new javax.swing.JLabel();
        j36 = new javax.swing.JLabel();
        j37 = new javax.swing.JLabel();
        j38 = new javax.swing.JLabel();
        j41 = new javax.swing.JLabel();
        j39 = new javax.swing.JLabel();
        j52 = new javax.swing.JLabel();
        j76 = new javax.swing.JLabel();
        j77 = new javax.swing.JLabel();
        j78 = new javax.swing.JLabel();
        j79 = new javax.swing.JLabel();
        j80 = new javax.swing.JLabel();
        j81 = new javax.swing.JLabel();
        j82 = new javax.swing.JLabel();
        j83 = new javax.swing.JLabel();
        j84 = new javax.swing.JLabel();
        j85 = new javax.swing.JLabel();
        j86 = new javax.swing.JLabel();
        j87 = new javax.swing.JLabel();
        j88 = new javax.swing.JLabel();
        j89 = new javax.swing.JLabel();
        j90 = new javax.swing.JLabel();
        j91 = new javax.swing.JLabel();
        j92 = new javax.swing.JLabel();
        j42 = new javax.swing.JLabel();
        j43 = new javax.swing.JLabel();
        j44 = new javax.swing.JLabel();
        j45 = new javax.swing.JLabel();
        j46 = new javax.swing.JLabel();
        j47 = new javax.swing.JLabel();
        j48 = new javax.swing.JLabel();
        j49 = new javax.swing.JLabel();
        j53 = new javax.swing.JLabel();
        j54 = new javax.swing.JLabel();
        j55 = new javax.swing.JLabel();
        j56 = new javax.swing.JLabel();
        j57 = new javax.swing.JLabel();
        j58 = new javax.swing.JLabel();
        j93 = new javax.swing.JLabel();
        j94 = new javax.swing.JLabel();
        j95 = new javax.swing.JLabel();
        j96 = new javax.swing.JLabel();
        j98 = new javax.swing.JLabel();
        j99 = new javax.swing.JLabel();
        j100 = new javax.swing.JLabel();
        j101 = new javax.swing.JLabel();
        j102 = new javax.swing.JLabel();
        j103 = new javax.swing.JLabel();
        j104 = new javax.swing.JLabel();
        j105 = new javax.swing.JLabel();
        j106 = new javax.swing.JLabel();
        j107 = new javax.swing.JLabel();
        j108 = new javax.swing.JLabel();
        j97 = new javax.swing.JLabel();
        j109 = new javax.swing.JLabel();
        j110 = new javax.swing.JLabel();
        j111 = new javax.swing.JLabel();
        j112 = new javax.swing.JLabel();
        j113 = new javax.swing.JLabel();
        j114 = new javax.swing.JLabel();
        j115 = new javax.swing.JLabel();
        j117 = new javax.swing.JLabel();
        j118 = new javax.swing.JLabel();
        j119 = new javax.swing.JLabel();
        j120 = new javax.swing.JLabel();
        j121 = new javax.swing.JLabel();
        j122 = new javax.swing.JLabel();
        j123 = new javax.swing.JLabel();
        j124 = new javax.swing.JLabel();
        j125 = new javax.swing.JLabel();
        j126 = new javax.swing.JLabel();
        j127 = new javax.swing.JLabel();
        j128 = new javax.swing.JLabel();
        j129 = new javax.swing.JLabel();
        j130 = new javax.swing.JLabel();
        j131 = new javax.swing.JLabel();
        j132 = new javax.swing.JLabel();
        j133 = new javax.swing.JLabel();
        j134 = new javax.swing.JLabel();
        j135 = new javax.swing.JLabel();
        j136 = new javax.swing.JLabel();
        j137 = new javax.swing.JLabel();
        j138 = new javax.swing.JLabel();
        j139 = new javax.swing.JLabel();
        j140 = new javax.swing.JLabel();
        j141 = new javax.swing.JLabel();
        j142 = new javax.swing.JLabel();
        j143 = new javax.swing.JLabel();
        j144 = new javax.swing.JLabel();
        j145 = new javax.swing.JLabel();
        j146 = new javax.swing.JLabel();
        j147 = new javax.swing.JLabel();
        j148 = new javax.swing.JLabel();
        j149 = new javax.swing.JLabel();
        j150 = new javax.swing.JLabel();
        j151 = new javax.swing.JLabel();
        j152 = new javax.swing.JLabel();
        j153 = new javax.swing.JLabel();
        j154 = new javax.swing.JLabel();
        j155 = new javax.swing.JLabel();
        j156 = new javax.swing.JLabel();
        j157 = new javax.swing.JLabel();
        M1E1 = new javax.swing.JLabel();
        M1E2 = new javax.swing.JLabel();
        M1E3 = new javax.swing.JLabel();
        jLAvatarMapa1 = new javax.swing.JLabel();
        fondo = new javax.swing.JLabel();
        Mapa2 = new javax.swing.JPanel();
        j17 = new javax.swing.JLabel();
        j23 = new javax.swing.JLabel();
        j26 = new javax.swing.JLabel();
        j9 = new javax.swing.JLabel();
        j40 = new javax.swing.JLabel();
        h2 = new javax.swing.JLabel();
        j116 = new javax.swing.JLabel();
        j158 = new javax.swing.JLabel();
        j160 = new javax.swing.JLabel();
        j161 = new javax.swing.JLabel();
        j162 = new javax.swing.JLabel();
        j163 = new javax.swing.JLabel();
        j164 = new javax.swing.JLabel();
        j165 = new javax.swing.JLabel();
        j166 = new javax.swing.JLabel();
        j167 = new javax.swing.JLabel();
        j168 = new javax.swing.JLabel();
        j169 = new javax.swing.JLabel();
        j170 = new javax.swing.JLabel();
        j171 = new javax.swing.JLabel();
        j172 = new javax.swing.JLabel();
        j173 = new javax.swing.JLabel();
        j174 = new javax.swing.JLabel();
        j50 = new javax.swing.JLabel();
        j51 = new javax.swing.JLabel();
        j175 = new javax.swing.JLabel();
        j176 = new javax.swing.JLabel();
        j177 = new javax.swing.JLabel();
        j178 = new javax.swing.JLabel();
        j181 = new javax.swing.JLabel();
        j182 = new javax.swing.JLabel();
        j183 = new javax.swing.JLabel();
        j184 = new javax.swing.JLabel();
        j185 = new javax.swing.JLabel();
        j186 = new javax.swing.JLabel();
        j187 = new javax.swing.JLabel();
        j188 = new javax.swing.JLabel();
        j189 = new javax.swing.JLabel();
        j190 = new javax.swing.JLabel();
        j191 = new javax.swing.JLabel();
        j192 = new javax.swing.JLabel();
        j193 = new javax.swing.JLabel();
        j194 = new javax.swing.JLabel();
        j196 = new javax.swing.JLabel();
        j197 = new javax.swing.JLabel();
        j198 = new javax.swing.JLabel();
        j199 = new javax.swing.JLabel();
        j200 = new javax.swing.JLabel();
        j201 = new javax.swing.JLabel();
        j202 = new javax.swing.JLabel();
        j203 = new javax.swing.JLabel();
        j204 = new javax.swing.JLabel();
        j205 = new javax.swing.JLabel();
        j206 = new javax.swing.JLabel();
        j207 = new javax.swing.JLabel();
        j208 = new javax.swing.JLabel();
        j209 = new javax.swing.JLabel();
        j210 = new javax.swing.JLabel();
        j211 = new javax.swing.JLabel();
        j212 = new javax.swing.JLabel();
        j213 = new javax.swing.JLabel();
        j214 = new javax.swing.JLabel();
        j215 = new javax.swing.JLabel();
        j216 = new javax.swing.JLabel();
        j217 = new javax.swing.JLabel();
        j218 = new javax.swing.JLabel();
        j219 = new javax.swing.JLabel();
        j220 = new javax.swing.JLabel();
        j221 = new javax.swing.JLabel();
        j224 = new javax.swing.JLabel();
        j225 = new javax.swing.JLabel();
        j226 = new javax.swing.JLabel();
        j227 = new javax.swing.JLabel();
        j228 = new javax.swing.JLabel();
        j229 = new javax.swing.JLabel();
        j232 = new javax.swing.JLabel();
        j233 = new javax.swing.JLabel();
        j234 = new javax.swing.JLabel();
        j235 = new javax.swing.JLabel();
        j236 = new javax.swing.JLabel();
        j237 = new javax.swing.JLabel();
        j238 = new javax.swing.JLabel();
        j239 = new javax.swing.JLabel();
        j240 = new javax.swing.JLabel();
        j241 = new javax.swing.JLabel();
        j242 = new javax.swing.JLabel();
        j243 = new javax.swing.JLabel();
        j244 = new javax.swing.JLabel();
        j245 = new javax.swing.JLabel();
        j247 = new javax.swing.JLabel();
        j250 = new javax.swing.JLabel();
        j251 = new javax.swing.JLabel();
        j255 = new javax.swing.JLabel();
        j256 = new javax.swing.JLabel();
        j259 = new javax.swing.JLabel();
        j260 = new javax.swing.JLabel();
        j261 = new javax.swing.JLabel();
        j263 = new javax.swing.JLabel();
        j264 = new javax.swing.JLabel();
        j265 = new javax.swing.JLabel();
        j266 = new javax.swing.JLabel();
        j267 = new javax.swing.JLabel();
        j269 = new javax.swing.JLabel();
        j271 = new javax.swing.JLabel();
        j272 = new javax.swing.JLabel();
        j273 = new javax.swing.JLabel();
        j274 = new javax.swing.JLabel();
        j275 = new javax.swing.JLabel();
        j276 = new javax.swing.JLabel();
        j277 = new javax.swing.JLabel();
        j278 = new javax.swing.JLabel();
        j279 = new javax.swing.JLabel();
        j280 = new javax.swing.JLabel();
        j281 = new javax.swing.JLabel();
        j282 = new javax.swing.JLabel();
        j283 = new javax.swing.JLabel();
        j284 = new javax.swing.JLabel();
        j286 = new javax.swing.JLabel();
        j24 = new javax.swing.JLabel();
        jLAvatarMapa2 = new javax.swing.JLabel();
        M2E1 = new javax.swing.JLabel();
        M2E2 = new javax.swing.JLabel();
        M2E3 = new javax.swing.JLabel();
        Fondo = new javax.swing.JLabel();
        Informacion = new javax.swing.JPanel();
        jtVida = new javax.swing.JLabel();
        jlVolver = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jlVida1 = new javax.swing.JLabel();
        jlVida2 = new javax.swing.JLabel();
        jlVida3 = new javax.swing.JLabel();
        jlControlGuia = new javax.swing.JLabel();
        Puntaje = new javax.swing.JLabel();
        jlPuntraje = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        Mapa1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Mapa1KeyPressed(evt);
            }
        });
        Mapa1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        j3.setBackground(new java.awt.Color(204, 204, 204));
        j3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j3.setOpaque(true);
        Mapa1.add(j3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 50, 50));

        j13.setBackground(new java.awt.Color(204, 204, 204));
        j13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j13.setOpaque(true);
        Mapa1.add(j13, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, 50, 50));

        j16.setBackground(new java.awt.Color(204, 204, 204));
        j16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j16.setOpaque(true);
        Mapa1.add(j16, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 100, 50, 50));

        j19.setBackground(new java.awt.Color(204, 204, 204));
        j19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j19.setOpaque(true);
        Mapa1.add(j19, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 100, 50, 50));

        j22.setBackground(new java.awt.Color(204, 204, 204));
        j22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j22.setOpaque(true);
        Mapa1.add(j22, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 100, 50, 50));

        j25.setBackground(new java.awt.Color(204, 204, 204));
        j25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j25.setOpaque(true);
        Mapa1.add(j25, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 350, 50, 50));

        j8.setBackground(new java.awt.Color(204, 204, 204));
        j8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j8.setOpaque(true);
        Mapa1.add(j8, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 350, 50, 50));

        j29.setBackground(new java.awt.Color(204, 204, 204));
        j29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j29.setOpaque(true);
        Mapa1.add(j29, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 50, 50));

        h1.setBackground(new java.awt.Color(204, 204, 204));
        h1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        h1.setOpaque(true);
        Mapa1.add(h1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 50, 50));

        j59.setBackground(new java.awt.Color(204, 204, 204));
        j59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j59.setOpaque(true);
        Mapa1.add(j59, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, 50, 50));

        j60.setBackground(new java.awt.Color(204, 204, 204));
        j60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j60.setOpaque(true);
        Mapa1.add(j60, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, 50, 50));

        j61.setBackground(new java.awt.Color(204, 204, 204));
        j61.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j61.setOpaque(true);
        Mapa1.add(j61, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 50, 50));

        j62.setBackground(new java.awt.Color(204, 204, 204));
        j62.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j62.setOpaque(true);
        Mapa1.add(j62, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 50, 50));

        j63.setBackground(new java.awt.Color(204, 204, 204));
        j63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j63.setOpaque(true);
        Mapa1.add(j63, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 50, 50));

        j64.setBackground(new java.awt.Color(204, 204, 204));
        j64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j64.setOpaque(true);
        Mapa1.add(j64, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 0, 50, 50));

        j65.setBackground(new java.awt.Color(204, 204, 204));
        j65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j65.setOpaque(true);
        Mapa1.add(j65, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 0, 50, 50));

        j66.setBackground(new java.awt.Color(204, 204, 204));
        j66.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j66.setOpaque(true);
        Mapa1.add(j66, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 0, 50, 50));

        j67.setBackground(new java.awt.Color(204, 204, 204));
        j67.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j67.setOpaque(true);
        Mapa1.add(j67, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 0, 50, 50));

        j68.setBackground(new java.awt.Color(204, 204, 204));
        j68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j68.setOpaque(true);
        Mapa1.add(j68, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 0, 50, 50));

        j69.setBackground(new java.awt.Color(204, 204, 204));
        j69.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j69.setOpaque(true);
        Mapa1.add(j69, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 0, 50, 50));

        j70.setBackground(new java.awt.Color(204, 204, 204));
        j70.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j70.setOpaque(true);
        Mapa1.add(j70, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, 50, 50));

        j71.setBackground(new java.awt.Color(204, 204, 204));
        j71.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j71.setOpaque(true);
        Mapa1.add(j71, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 0, 50, 50));

        j72.setBackground(new java.awt.Color(204, 204, 204));
        j72.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j72.setOpaque(true);
        Mapa1.add(j72, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 0, 50, 50));

        j73.setBackground(new java.awt.Color(204, 204, 204));
        j73.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j73.setOpaque(true);
        Mapa1.add(j73, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 0, 50, 50));

        j74.setBackground(new java.awt.Color(204, 204, 204));
        j74.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j74.setOpaque(true);
        Mapa1.add(j74, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 0, 50, 50));

        j75.setBackground(new java.awt.Color(204, 204, 204));
        j75.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j75.setOpaque(true);
        Mapa1.add(j75, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 0, 50, 50));

        j4.setBackground(new java.awt.Color(204, 204, 204));
        j4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j4.setOpaque(true);
        Mapa1.add(j4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 50, 50));

        j28.setBackground(new java.awt.Color(204, 204, 204));
        j28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j28.setToolTipText("");
        j28.setOpaque(true);
        Mapa1.add(j28, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 50, 50));

        j30.setBackground(new java.awt.Color(204, 204, 204));
        j30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j30.setOpaque(true);
        Mapa1.add(j30, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 50, 50));

        j31.setBackground(new java.awt.Color(204, 204, 204));
        j31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j31.setOpaque(true);
        Mapa1.add(j31, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 50, 50));

        j32.setBackground(new java.awt.Color(204, 204, 204));
        j32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j32.setOpaque(true);
        Mapa1.add(j32, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, 50, 50));

        j33.setBackground(new java.awt.Color(204, 204, 204));
        j33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j33.setOpaque(true);
        Mapa1.add(j33, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 50, 50));

        j34.setBackground(new java.awt.Color(204, 204, 204));
        j34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j34.setOpaque(true);
        Mapa1.add(j34, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 50, 50));

        j35.setBackground(new java.awt.Color(204, 204, 204));
        j35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j35.setOpaque(true);
        Mapa1.add(j35, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 50, 50));

        j36.setBackground(new java.awt.Color(204, 204, 204));
        j36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j36.setOpaque(true);
        Mapa1.add(j36, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 50, 50));

        j37.setBackground(new java.awt.Color(204, 204, 204));
        j37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j37.setOpaque(true);
        Mapa1.add(j37, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 50, 50));

        j38.setBackground(new java.awt.Color(204, 204, 204));
        j38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j38.setOpaque(true);
        Mapa1.add(j38, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 50, 50));

        j41.setBackground(new java.awt.Color(204, 204, 204));
        j41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j41.setOpaque(true);
        Mapa1.add(j41, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 50, 50));

        j39.setBackground(new java.awt.Color(204, 204, 204));
        j39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j39.setOpaque(true);
        Mapa1.add(j39, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 550, 50, 50));

        j52.setBackground(new java.awt.Color(204, 204, 204));
        j52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j52.setOpaque(true);
        Mapa1.add(j52, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 550, 50, 50));

        j76.setBackground(new java.awt.Color(204, 204, 204));
        j76.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j76.setOpaque(true);
        Mapa1.add(j76, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 550, 50, 50));

        j77.setBackground(new java.awt.Color(204, 204, 204));
        j77.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j77.setOpaque(true);
        Mapa1.add(j77, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 550, 50, 50));

        j78.setBackground(new java.awt.Color(204, 204, 204));
        j78.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j78.setOpaque(true);
        Mapa1.add(j78, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 550, 50, 50));

        j79.setBackground(new java.awt.Color(204, 204, 204));
        j79.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j79.setOpaque(true);
        Mapa1.add(j79, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 550, 50, 50));

        j80.setBackground(new java.awt.Color(204, 204, 204));
        j80.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j80.setOpaque(true);
        Mapa1.add(j80, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 550, 50, 50));

        j81.setBackground(new java.awt.Color(204, 204, 204));
        j81.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j81.setOpaque(true);
        Mapa1.add(j81, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 550, 50, 50));

        j82.setBackground(new java.awt.Color(204, 204, 204));
        j82.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j82.setOpaque(true);
        Mapa1.add(j82, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 550, 50, 50));

        j83.setBackground(new java.awt.Color(204, 204, 204));
        j83.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j83.setOpaque(true);
        Mapa1.add(j83, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 550, 50, 50));

        j84.setBackground(new java.awt.Color(204, 204, 204));
        j84.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j84.setOpaque(true);
        Mapa1.add(j84, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 550, 50, 50));

        j85.setBackground(new java.awt.Color(204, 204, 204));
        j85.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j85.setOpaque(true);
        Mapa1.add(j85, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 550, 50, 50));

        j86.setBackground(new java.awt.Color(204, 204, 204));
        j86.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j86.setOpaque(true);
        Mapa1.add(j86, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 550, 50, 50));

        j87.setBackground(new java.awt.Color(204, 204, 204));
        j87.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j87.setOpaque(true);
        Mapa1.add(j87, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 550, 50, 50));

        j88.setBackground(new java.awt.Color(204, 204, 204));
        j88.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j88.setOpaque(true);
        Mapa1.add(j88, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 550, 50, 50));

        j89.setBackground(new java.awt.Color(204, 204, 204));
        j89.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j89.setOpaque(true);
        Mapa1.add(j89, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 550, 50, 50));

        j90.setBackground(new java.awt.Color(204, 204, 204));
        j90.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j90.setOpaque(true);
        Mapa1.add(j90, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 550, 50, 50));

        j91.setBackground(new java.awt.Color(204, 204, 204));
        j91.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j91.setOpaque(true);
        Mapa1.add(j91, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 550, 50, 50));

        j92.setBackground(new java.awt.Color(204, 204, 204));
        j92.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j92.setOpaque(true);
        Mapa1.add(j92, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 550, 50, 50));

        j42.setBackground(new java.awt.Color(204, 204, 204));
        j42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j42.setOpaque(true);
        Mapa1.add(j42, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 350, 50, 50));

        j43.setBackground(new java.awt.Color(204, 204, 204));
        j43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j43.setOpaque(true);
        Mapa1.add(j43, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 450, 50, 50));

        j44.setBackground(new java.awt.Color(204, 204, 204));
        j44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j44.setOpaque(true);
        Mapa1.add(j44, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 300, 50, 50));

        j45.setBackground(new java.awt.Color(204, 204, 204));
        j45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j45.setOpaque(true);
        Mapa1.add(j45, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 250, 50, 50));

        j46.setBackground(new java.awt.Color(204, 204, 204));
        j46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j46.setOpaque(true);
        Mapa1.add(j46, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 150, 50, 50));

        j47.setBackground(new java.awt.Color(204, 204, 204));
        j47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j47.setOpaque(true);
        Mapa1.add(j47, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 200, 50, 50));

        j48.setBackground(new java.awt.Color(204, 204, 204));
        j48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j48.setOpaque(true);
        Mapa1.add(j48, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 100, 50, 50));

        j49.setBackground(new java.awt.Color(204, 204, 204));
        j49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j49.setOpaque(true);
        Mapa1.add(j49, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 50, 50, 50));

        j53.setBackground(new java.awt.Color(204, 204, 204));
        j53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j53.setOpaque(true);
        Mapa1.add(j53, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 400, 50, 50));

        j54.setBackground(new java.awt.Color(204, 204, 204));
        j54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j54.setOpaque(true);
        Mapa1.add(j54, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 100, 50, 50));

        j55.setBackground(new java.awt.Color(204, 204, 204));
        j55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j55.setOpaque(true);
        Mapa1.add(j55, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 150, 50, 50));

        j56.setBackground(new java.awt.Color(204, 204, 204));
        j56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j56.setOpaque(true);
        Mapa1.add(j56, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 200, 50, 50));

        j57.setBackground(new java.awt.Color(204, 204, 204));
        j57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j57.setOpaque(true);
        Mapa1.add(j57, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 250, 50, 50));

        j58.setBackground(new java.awt.Color(204, 204, 204));
        j58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j58.setOpaque(true);
        Mapa1.add(j58, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 300, 50, 50));

        j93.setBackground(new java.awt.Color(204, 204, 204));
        j93.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j93.setOpaque(true);
        Mapa1.add(j93, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 350, 50, 50));

        j94.setBackground(new java.awt.Color(204, 204, 204));
        j94.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j94.setOpaque(true);
        Mapa1.add(j94, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 400, 50, 50));

        j95.setBackground(new java.awt.Color(204, 204, 204));
        j95.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j95.setOpaque(true);
        Mapa1.add(j95, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 450, 50, 50));

        j96.setBackground(new java.awt.Color(204, 204, 204));
        j96.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j96.setOpaque(true);
        Mapa1.add(j96, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 500, 50, 50));

        j98.setBackground(new java.awt.Color(204, 204, 204));
        j98.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j98.setOpaque(true);
        Mapa1.add(j98, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 450, 50, 50));

        j99.setBackground(new java.awt.Color(204, 204, 204));
        j99.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j99.setOpaque(true);
        Mapa1.add(j99, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 450, 50, 50));

        j100.setBackground(new java.awt.Color(204, 204, 204));
        j100.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j100.setOpaque(true);
        Mapa1.add(j100, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 450, 50, 50));

        j101.setBackground(new java.awt.Color(204, 204, 204));
        j101.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j101.setOpaque(true);
        Mapa1.add(j101, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 450, 50, 50));

        j102.setBackground(new java.awt.Color(204, 204, 204));
        j102.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j102.setOpaque(true);
        Mapa1.add(j102, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 450, 50, 50));

        j103.setBackground(new java.awt.Color(204, 204, 204));
        j103.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j103.setOpaque(true);
        Mapa1.add(j103, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 450, 50, 50));

        j104.setBackground(new java.awt.Color(204, 204, 204));
        j104.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j104.setOpaque(true);
        Mapa1.add(j104, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 450, 50, 50));

        j105.setBackground(new java.awt.Color(204, 204, 204));
        j105.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j105.setOpaque(true);
        Mapa1.add(j105, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 350, 50, 50));

        j106.setBackground(new java.awt.Color(204, 204, 204));
        j106.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j106.setOpaque(true);
        Mapa1.add(j106, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 350, 50, 50));

        j107.setBackground(new java.awt.Color(204, 204, 204));
        j107.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j107.setOpaque(true);
        Mapa1.add(j107, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, 50, 50));

        j108.setBackground(new java.awt.Color(204, 204, 204));
        j108.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j108.setOpaque(true);
        Mapa1.add(j108, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 350, 50, 50));

        j97.setBackground(new java.awt.Color(204, 204, 204));
        j97.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j97.setOpaque(true);
        Mapa1.add(j97, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 250, 50, 50));

        j109.setBackground(new java.awt.Color(204, 204, 204));
        j109.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j109.setOpaque(true);
        Mapa1.add(j109, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 250, 50, 50));

        j110.setBackground(new java.awt.Color(204, 204, 204));
        j110.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j110.setOpaque(true);
        Mapa1.add(j110, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 250, 50, 50));

        j111.setBackground(new java.awt.Color(204, 204, 204));
        j111.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j111.setOpaque(true);
        Mapa1.add(j111, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 250, 50, 50));

        j112.setBackground(new java.awt.Color(204, 204, 204));
        j112.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j112.setOpaque(true);
        Mapa1.add(j112, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 250, 50, 50));

        j113.setBackground(new java.awt.Color(204, 204, 204));
        j113.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j113.setOpaque(true);
        Mapa1.add(j113, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 250, 50, 50));

        j114.setBackground(new java.awt.Color(204, 204, 204));
        j114.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j114.setToolTipText("");
        j114.setOpaque(true);
        Mapa1.add(j114, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, 50, 50));

        j115.setBackground(new java.awt.Color(204, 204, 204));
        j115.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j115.setToolTipText("");
        j115.setOpaque(true);
        Mapa1.add(j115, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, 50, 50));

        j117.setBackground(new java.awt.Color(204, 204, 204));
        j117.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j117.setOpaque(true);
        Mapa1.add(j117, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 150, 50, 50));

        j118.setBackground(new java.awt.Color(204, 204, 204));
        j118.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j118.setOpaque(true);
        Mapa1.add(j118, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 150, 50, 50));

        j119.setBackground(new java.awt.Color(204, 204, 204));
        j119.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j119.setOpaque(true);
        Mapa1.add(j119, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 250, 50, 50));

        j120.setBackground(new java.awt.Color(204, 204, 204));
        j120.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j120.setOpaque(true);
        Mapa1.add(j120, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 250, 50, 50));

        j121.setBackground(new java.awt.Color(204, 204, 204));
        j121.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j121.setOpaque(true);
        Mapa1.add(j121, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 250, 50, 50));

        j122.setBackground(new java.awt.Color(204, 204, 204));
        j122.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j122.setOpaque(true);
        Mapa1.add(j122, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 250, 50, 50));

        j123.setBackground(new java.awt.Color(204, 204, 204));
        j123.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j123.setOpaque(true);
        Mapa1.add(j123, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 250, 50, 50));

        j124.setBackground(new java.awt.Color(204, 204, 204));
        j124.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j124.setOpaque(true);
        Mapa1.add(j124, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 250, 50, 50));

        j125.setBackground(new java.awt.Color(204, 204, 204));
        j125.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j125.setOpaque(true);
        Mapa1.add(j125, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 350, 50, 50));

        j126.setBackground(new java.awt.Color(204, 204, 204));
        j126.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j126.setOpaque(true);
        Mapa1.add(j126, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 350, 50, 50));

        j127.setBackground(new java.awt.Color(204, 204, 204));
        j127.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j127.setOpaque(true);
        Mapa1.add(j127, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, 50, 50));

        j128.setBackground(new java.awt.Color(204, 204, 204));
        j128.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j128.setOpaque(true);
        Mapa1.add(j128, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 350, 50, 50));

        j129.setBackground(new java.awt.Color(204, 204, 204));
        j129.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j129.setOpaque(true);
        Mapa1.add(j129, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 450, 50, 50));

        j130.setBackground(new java.awt.Color(204, 204, 204));
        j130.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j130.setOpaque(true);
        Mapa1.add(j130, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 450, 50, 50));

        j131.setBackground(new java.awt.Color(204, 204, 204));
        j131.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j131.setOpaque(true);
        Mapa1.add(j131, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 450, 50, 50));

        j132.setBackground(new java.awt.Color(204, 204, 204));
        j132.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j132.setOpaque(true);
        Mapa1.add(j132, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 450, 50, 50));

        j133.setBackground(new java.awt.Color(204, 204, 204));
        j133.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j133.setOpaque(true);
        Mapa1.add(j133, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 450, 50, 50));

        j134.setBackground(new java.awt.Color(204, 204, 204));
        j134.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j134.setOpaque(true);
        Mapa1.add(j134, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 450, 50, 50));

        j135.setBackground(new java.awt.Color(204, 204, 204));
        j135.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j135.setOpaque(true);
        Mapa1.add(j135, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 450, 50, 50));

        j136.setBackground(new java.awt.Color(204, 204, 204));
        j136.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j136.setOpaque(true);
        Mapa1.add(j136, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 450, 50, 50));

        j137.setBackground(new java.awt.Color(204, 204, 204));
        j137.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j137.setOpaque(true);
        Mapa1.add(j137, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 450, 50, 50));

        j138.setBackground(new java.awt.Color(204, 204, 204));
        j138.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j138.setOpaque(true);
        Mapa1.add(j138, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 450, 50, 50));

        j139.setBackground(new java.awt.Color(204, 204, 204));
        j139.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j139.setOpaque(true);
        Mapa1.add(j139, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 450, 50, 50));

        j140.setBackground(new java.awt.Color(204, 204, 204));
        j140.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j140.setOpaque(true);
        Mapa1.add(j140, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 450, 50, 50));

        j141.setBackground(new java.awt.Color(204, 204, 204));
        j141.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j141.setOpaque(true);
        Mapa1.add(j141, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 450, 50, 50));

        j142.setBackground(new java.awt.Color(204, 204, 204));
        j142.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j142.setOpaque(true);
        Mapa1.add(j142, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 450, 50, 50));

        j143.setBackground(new java.awt.Color(204, 204, 204));
        j143.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j143.setOpaque(true);
        Mapa1.add(j143, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 350, 50, 50));

        j144.setBackground(new java.awt.Color(204, 204, 204));
        j144.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j144.setOpaque(true);
        Mapa1.add(j144, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 350, 50, 50));

        j145.setBackground(new java.awt.Color(204, 204, 204));
        j145.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j145.setOpaque(true);
        Mapa1.add(j145, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 350, 50, 50));

        j146.setBackground(new java.awt.Color(204, 204, 204));
        j146.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j146.setOpaque(true);
        Mapa1.add(j146, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 350, 50, 50));

        j147.setBackground(new java.awt.Color(204, 204, 204));
        j147.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j147.setOpaque(true);
        Mapa1.add(j147, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, 50, 50));

        j148.setBackground(new java.awt.Color(204, 204, 204));
        j148.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j148.setToolTipText("");
        j148.setOpaque(true);
        Mapa1.add(j148, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 150, 50, 50));

        j149.setBackground(new java.awt.Color(204, 204, 204));
        j149.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j149.setOpaque(true);
        Mapa1.add(j149, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, 50, 50));

        j150.setBackground(new java.awt.Color(204, 204, 204));
        j150.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j150.setOpaque(true);
        Mapa1.add(j150, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 100, 50, 50));

        j151.setBackground(new java.awt.Color(204, 204, 204));
        j151.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j151.setOpaque(true);
        Mapa1.add(j151, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 150, 50, 50));

        j152.setBackground(new java.awt.Color(204, 204, 204));
        j152.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j152.setOpaque(true);
        Mapa1.add(j152, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 100, 50, 50));

        j153.setBackground(new java.awt.Color(204, 204, 204));
        j153.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j153.setOpaque(true);
        Mapa1.add(j153, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 150, 50, 50));

        j154.setBackground(new java.awt.Color(204, 204, 204));
        j154.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j154.setOpaque(true);
        Mapa1.add(j154, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 100, 50, 50));

        j155.setBackground(new java.awt.Color(204, 204, 204));
        j155.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j155.setOpaque(true);
        Mapa1.add(j155, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 150, 50, 50));

        j156.setBackground(new java.awt.Color(204, 204, 204));
        j156.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j156.setOpaque(true);
        Mapa1.add(j156, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 100, 50, 50));

        j157.setBackground(new java.awt.Color(204, 204, 204));
        j157.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoLadrillo.png"))); // NOI18N
        j157.setOpaque(true);
        Mapa1.add(j157, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 150, 50, 50));

        M1E1.setBackground(new java.awt.Color(204, 204, 204));
        M1E1.setOpaque(true);
        Mapa1.add(M1E1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 300, 40, 40));

        M1E2.setBackground(new java.awt.Color(255, 0, 0));
        M1E2.setOpaque(true);
        Mapa1.add(M1E2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 60, 40, 40));

        M1E3.setBackground(new java.awt.Color(0, 102, 102));
        M1E3.setOpaque(true);
        Mapa1.add(M1E3, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 310, 40, 40));

        jLAvatarMapa1.setBackground(new java.awt.Color(153, 255, 204));
        jLAvatarMapa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/avatar.png"))); // NOI18N
        jLAvatarMapa1.setOpaque(true);
        Mapa1.add(jLAvatarMapa1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 500, 40, 40));

        fondo.setBackground(new java.awt.Color(255, 204, 204));
        fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoVerde.png"))); // NOI18N
        Mapa1.add(fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, 600));

        Mapa2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Mapa2KeyPressed(evt);
            }
        });
        Mapa2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        j17.setBackground(new java.awt.Color(255, 204, 204));
        j17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j17, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 100, 50, 50));

        j23.setBackground(new java.awt.Color(255, 204, 204));
        j23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j23, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 100, 50, 50));

        j26.setBackground(new java.awt.Color(255, 204, 204));
        j26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j26, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 350, 50, 50));

        j9.setBackground(new java.awt.Color(255, 204, 204));
        j9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j9, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, 50, 50));

        j40.setBackground(new java.awt.Color(255, 204, 204));
        j40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j40, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 50, 50));

        h2.setBackground(new java.awt.Color(255, 204, 204));
        h2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(h2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 50, 50));

        j116.setBackground(new java.awt.Color(255, 204, 204));
        j116.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j116, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, 50, 50));

        j158.setBackground(new java.awt.Color(255, 204, 204));
        j158.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j158, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, 50, 50));

        j160.setBackground(new java.awt.Color(255, 204, 204));
        j160.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j160, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 50, 50));

        j161.setBackground(new java.awt.Color(255, 204, 204));
        j161.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j161, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 50, 50));

        j162.setBackground(new java.awt.Color(255, 204, 204));
        j162.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j162, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 50, 50));

        j163.setBackground(new java.awt.Color(255, 204, 204));
        j163.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j163, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 0, 50, 50));

        j164.setBackground(new java.awt.Color(255, 204, 204));
        j164.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j164, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 0, 50, 50));

        j165.setBackground(new java.awt.Color(255, 204, 204));
        j165.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j165, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 0, 50, 50));

        j166.setBackground(new java.awt.Color(255, 204, 204));
        j166.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j166, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 0, 50, 50));

        j167.setBackground(new java.awt.Color(255, 204, 204));
        j167.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j167, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 0, 50, 50));

        j168.setBackground(new java.awt.Color(255, 204, 204));
        j168.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j168, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 0, 50, 50));

        j169.setBackground(new java.awt.Color(255, 204, 204));
        j169.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j169, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, 50, 50));

        j170.setBackground(new java.awt.Color(255, 204, 204));
        j170.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j170, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 0, 50, 50));

        j171.setBackground(new java.awt.Color(255, 204, 204));
        j171.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j171, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 0, 50, 50));

        j172.setBackground(new java.awt.Color(255, 204, 204));
        j172.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j172, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 0, 50, 50));

        j173.setBackground(new java.awt.Color(255, 204, 204));
        j173.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j173, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 0, 50, 50));

        j174.setBackground(new java.awt.Color(255, 204, 204));
        j174.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j174, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 0, 50, 50));

        j50.setBackground(new java.awt.Color(255, 204, 204));
        j50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j50.setOpaque(true);
        Mapa2.add(j50, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 50, 50));

        j51.setBackground(new java.awt.Color(255, 204, 204));
        j51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j51.setOpaque(true);
        Mapa2.add(j51, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 50, 50));

        j175.setBackground(new java.awt.Color(255, 204, 204));
        j175.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j175.setOpaque(true);
        Mapa2.add(j175, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 50, 50));

        j176.setBackground(new java.awt.Color(255, 204, 204));
        j176.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j176.setOpaque(true);
        Mapa2.add(j176, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, 50, 50));

        j177.setBackground(new java.awt.Color(255, 204, 204));
        j177.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j177.setOpaque(true);
        Mapa2.add(j177, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 50, 50));

        j178.setBackground(new java.awt.Color(255, 204, 204));
        j178.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j178.setOpaque(true);
        Mapa2.add(j178, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 50, 50));

        j181.setBackground(new java.awt.Color(255, 204, 204));
        j181.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j181.setOpaque(true);
        Mapa2.add(j181, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 50, 50));

        j182.setBackground(new java.awt.Color(255, 204, 204));
        j182.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j182.setOpaque(true);
        Mapa2.add(j182, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 50, 50));

        j183.setBackground(new java.awt.Color(255, 204, 204));
        j183.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j183.setOpaque(true);
        Mapa2.add(j183, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 50, 50));

        j184.setBackground(new java.awt.Color(255, 204, 204));
        j184.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j184, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 550, 50, 50));

        j185.setBackground(new java.awt.Color(255, 204, 204));
        j185.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j185, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 550, 50, 50));

        j186.setBackground(new java.awt.Color(255, 204, 204));
        j186.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j186, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 550, 50, 50));

        j187.setBackground(new java.awt.Color(255, 204, 204));
        j187.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j187, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 550, 50, 50));

        j188.setBackground(new java.awt.Color(255, 204, 204));
        j188.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j188, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 550, 50, 50));

        j189.setBackground(new java.awt.Color(255, 204, 204));
        j189.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j189, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 550, 50, 50));

        j190.setBackground(new java.awt.Color(255, 204, 204));
        j190.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j190, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 550, 50, 50));

        j191.setBackground(new java.awt.Color(255, 204, 204));
        j191.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j191, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 550, 50, 50));

        j192.setBackground(new java.awt.Color(255, 204, 204));
        j192.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j192, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 550, 50, 50));

        j193.setBackground(new java.awt.Color(255, 204, 204));
        j193.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j193, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 550, 50, 50));

        j194.setBackground(new java.awt.Color(255, 204, 204));
        j194.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j194, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 550, 50, 50));

        j196.setBackground(new java.awt.Color(255, 204, 204));
        j196.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j196, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 550, 50, 50));

        j197.setBackground(new java.awt.Color(255, 204, 204));
        j197.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j197, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 550, 50, 50));

        j198.setBackground(new java.awt.Color(255, 204, 204));
        j198.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j198, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 550, 50, 50));

        j199.setBackground(new java.awt.Color(255, 204, 204));
        j199.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j199, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 550, 50, 50));

        j200.setBackground(new java.awt.Color(255, 204, 204));
        j200.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j200, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 550, 50, 50));

        j201.setBackground(new java.awt.Color(255, 204, 204));
        j201.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j201, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 550, 50, 50));

        j202.setBackground(new java.awt.Color(255, 204, 204));
        j202.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j202, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 550, 50, 50));

        j203.setBackground(new java.awt.Color(255, 204, 204));
        j203.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j203.setOpaque(true);
        Mapa2.add(j203, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 350, 50, 50));

        j204.setBackground(new java.awt.Color(255, 204, 204));
        j204.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j204.setOpaque(true);
        Mapa2.add(j204, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 500, 50, 50));

        j205.setBackground(new java.awt.Color(255, 204, 204));
        j205.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j205.setOpaque(true);
        Mapa2.add(j205, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 300, 50, 50));

        j206.setBackground(new java.awt.Color(255, 204, 204));
        j206.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j206.setOpaque(true);
        Mapa2.add(j206, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 250, 50, 50));

        j207.setBackground(new java.awt.Color(255, 204, 204));
        j207.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j207.setOpaque(true);
        Mapa2.add(j207, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 150, 50, 50));

        j208.setBackground(new java.awt.Color(255, 204, 204));
        j208.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j208.setOpaque(true);
        Mapa2.add(j208, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 200, 50, 50));

        j209.setBackground(new java.awt.Color(255, 204, 204));
        j209.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j209.setOpaque(true);
        Mapa2.add(j209, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 100, 50, 50));

        j210.setBackground(new java.awt.Color(255, 204, 204));
        j210.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j210.setOpaque(true);
        Mapa2.add(j210, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 50, 50, 50));

        j211.setBackground(new java.awt.Color(255, 204, 204));
        j211.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j211.setOpaque(true);
        Mapa2.add(j211, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 400, 50, 50));

        j212.setBackground(new java.awt.Color(255, 204, 204));
        j212.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j212.setOpaque(true);
        Mapa2.add(j212, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 100, 50, 50));

        j213.setBackground(new java.awt.Color(255, 204, 204));
        j213.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j213.setOpaque(true);
        Mapa2.add(j213, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 150, 50, 50));

        j214.setBackground(new java.awt.Color(255, 204, 204));
        j214.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j214.setOpaque(true);
        Mapa2.add(j214, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 200, 50, 50));

        j215.setBackground(new java.awt.Color(255, 204, 204));
        j215.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j215.setOpaque(true);
        Mapa2.add(j215, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 250, 50, 50));

        j216.setBackground(new java.awt.Color(255, 204, 204));
        j216.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j216.setOpaque(true);
        Mapa2.add(j216, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 300, 50, 50));

        j217.setBackground(new java.awt.Color(255, 204, 204));
        j217.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j217.setOpaque(true);
        Mapa2.add(j217, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 350, 50, 50));

        j218.setBackground(new java.awt.Color(255, 204, 204));
        j218.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j218.setOpaque(true);
        Mapa2.add(j218, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 400, 50, 50));

        j219.setBackground(new java.awt.Color(255, 204, 204));
        j219.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j219.setOpaque(true);
        Mapa2.add(j219, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 450, 50, 50));

        j220.setBackground(new java.awt.Color(255, 204, 204));
        j220.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j220.setOpaque(true);
        Mapa2.add(j220, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 500, 50, 50));

        j221.setBackground(new java.awt.Color(255, 204, 204));
        j221.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j221, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 450, 50, 50));

        j224.setBackground(new java.awt.Color(255, 204, 204));
        j224.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j224, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 450, 50, 50));

        j225.setBackground(new java.awt.Color(255, 204, 204));
        j225.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j225, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 400, 50, 50));

        j226.setBackground(new java.awt.Color(255, 204, 204));
        j226.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j226, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 450, 50, 50));

        j227.setBackground(new java.awt.Color(255, 204, 204));
        j227.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j227, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 500, 50, 50));

        j228.setBackground(new java.awt.Color(255, 204, 204));
        j228.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j228, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 350, 50, 50));

        j229.setBackground(new java.awt.Color(255, 204, 204));
        j229.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j229, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 350, 50, 50));

        j232.setBackground(new java.awt.Color(255, 204, 204));
        j232.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j232, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 250, 50, 50));

        j233.setBackground(new java.awt.Color(255, 204, 204));
        j233.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j233, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 250, 50, 50));

        j234.setBackground(new java.awt.Color(255, 204, 204));
        j234.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j234, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 250, 50, 50));

        j235.setBackground(new java.awt.Color(255, 204, 204));
        j235.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j235, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 100, 50, 50));

        j236.setBackground(new java.awt.Color(255, 204, 204));
        j236.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j236, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 250, 50, 50));

        j237.setBackground(new java.awt.Color(255, 204, 204));
        j237.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j237, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 250, 50, 50));

        j238.setBackground(new java.awt.Color(255, 204, 204));
        j238.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j238, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 50, 50));

        j239.setBackground(new java.awt.Color(255, 204, 204));
        j239.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j239, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, 50, 50));

        j240.setBackground(new java.awt.Color(255, 204, 204));
        j240.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j240, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 100, 50, 50));

        j241.setBackground(new java.awt.Color(255, 204, 204));
        j241.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j241, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 100, 50, 50));

        j242.setBackground(new java.awt.Color(255, 204, 204));
        j242.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j242, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 250, 50, 50));

        j243.setBackground(new java.awt.Color(255, 204, 204));
        j243.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j243, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 250, 50, 50));

        j244.setBackground(new java.awt.Color(255, 204, 204));
        j244.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j244, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 250, 50, 50));

        j245.setBackground(new java.awt.Color(255, 204, 204));
        j245.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j245, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 250, 50, 50));

        j247.setBackground(new java.awt.Color(255, 204, 204));
        j247.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j247, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 250, 50, 50));

        j250.setBackground(new java.awt.Color(255, 204, 204));
        j250.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j250, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 350, 50, 50));

        j251.setBackground(new java.awt.Color(255, 204, 204));
        j251.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j251, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 350, 50, 50));

        j255.setBackground(new java.awt.Color(255, 204, 204));
        j255.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j255, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 350, 50, 50));

        j256.setBackground(new java.awt.Color(255, 204, 204));
        j256.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j256, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 400, 50, 50));

        j259.setBackground(new java.awt.Color(255, 204, 204));
        j259.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j259, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 450, 50, 50));

        j260.setBackground(new java.awt.Color(255, 204, 204));
        j260.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j260, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 450, 50, 50));

        j261.setBackground(new java.awt.Color(255, 204, 204));
        j261.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j261, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 350, 50, 50));

        j263.setBackground(new java.awt.Color(255, 204, 204));
        j263.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j263, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 450, 50, 50));

        j264.setBackground(new java.awt.Color(255, 204, 204));
        j264.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j264, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 450, 50, 50));

        j265.setBackground(new java.awt.Color(255, 204, 204));
        j265.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j265, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 450, 50, 50));

        j266.setBackground(new java.awt.Color(255, 204, 204));
        j266.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j266, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 350, 50, 50));

        j267.setBackground(new java.awt.Color(255, 204, 204));
        j267.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j267, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 350, 50, 50));

        j269.setBackground(new java.awt.Color(255, 204, 204));
        j269.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j269, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 350, 50, 50));

        j271.setBackground(new java.awt.Color(255, 204, 204));
        j271.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j271.setOpaque(true);
        Mapa2.add(j271, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 200, 50, 50));

        j272.setBackground(new java.awt.Color(255, 204, 204));
        j272.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j272.setOpaque(true);
        Mapa2.add(j272, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, 50, 50));

        j273.setBackground(new java.awt.Color(255, 204, 204));
        j273.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j273.setOpaque(true);
        Mapa2.add(j273, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, 50, 50));

        j274.setBackground(new java.awt.Color(255, 204, 204));
        j274.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j274.setOpaque(true);
        Mapa2.add(j274, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 150, 50, 50));

        j275.setBackground(new java.awt.Color(255, 204, 204));
        j275.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j275.setOpaque(true);
        Mapa2.add(j275, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 100, 50, 50));

        j276.setBackground(new java.awt.Color(255, 204, 204));
        j276.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j276.setOpaque(true);
        Mapa2.add(j276, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 150, 50, 50));

        j277.setBackground(new java.awt.Color(255, 204, 204));
        j277.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j277.setOpaque(true);
        Mapa2.add(j277, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 100, 50, 50));

        j278.setBackground(new java.awt.Color(255, 204, 204));
        j278.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j278.setOpaque(true);
        Mapa2.add(j278, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 200, 50, 50));

        j279.setBackground(new java.awt.Color(255, 204, 204));
        j279.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j279.setOpaque(true);
        Mapa2.add(j279, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 150, 50, 50));

        j280.setBackground(new java.awt.Color(255, 204, 204));
        j280.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j280.setOpaque(true);
        Mapa2.add(j280, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 150, 50, 50));

        j281.setBackground(new java.awt.Color(255, 204, 204));
        j281.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j281.setOpaque(true);
        Mapa2.add(j281, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 150, 50, 50));

        j282.setBackground(new java.awt.Color(255, 204, 204));
        j282.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j282.setOpaque(true);
        Mapa2.add(j282, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 450, 50, 50));

        j283.setBackground(new java.awt.Color(255, 204, 204));
        j283.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j283, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 450, 50, 50));

        j284.setBackground(new java.awt.Color(255, 204, 204));
        j284.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j284, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 350, 50, 50));

        j286.setBackground(new java.awt.Color(255, 204, 204));
        j286.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        j286.setOpaque(true);
        Mapa2.add(j286, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 150, 50, 50));

        j24.setBackground(new java.awt.Color(255, 204, 204));
        j24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LaberintoAgua.png"))); // NOI18N
        Mapa2.add(j24, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 100, 50, 50));

        jLAvatarMapa2.setBackground(new java.awt.Color(153, 255, 204));
        jLAvatarMapa2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLAvatarMapa2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/avatar.png"))); // NOI18N
        jLAvatarMapa2.setOpaque(true);
        Mapa2.add(jLAvatarMapa2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 510, 40, 40));

        M2E1.setBackground(new java.awt.Color(255, 0, 0));
        M2E1.setOpaque(true);
        Mapa2.add(M2E1, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 260, 40, 40));

        M2E2.setBackground(new java.awt.Color(204, 204, 204));
        M2E2.setOpaque(true);
        Mapa2.add(M2E2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 60, 40, 40));

        M2E3.setBackground(new java.awt.Color(0, 102, 102));
        M2E3.setOpaque(true);
        Mapa2.add(M2E3, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 230, 40, 40));

        Fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoTierra.jpg"))); // NOI18N
        Mapa2.add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, 600));

        Informacion.setBackground(new java.awt.Color(204, 204, 255));
        Informacion.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtVida.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jtVida.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jtVida.setText("Vida");
        Informacion.add(jtVida, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 60, 90, 30));

        jlVolver.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlVolver.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Atras.png"))); // NOI18N
        jlVolver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                VolverMenu(evt);
            }
        });
        Informacion.add(jlVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 80, 40));

        jLabel1.setFont(new java.awt.Font("Tempus Sans ITC", 3, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("NIVEL 1");
        Informacion.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 0, 240, 50));

        jlVida1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/vida.png"))); // NOI18N
        jlVida1.setText("jLabel2");
        Informacion.add(jlVida1, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 60, 40, 30));

        jlVida2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/vida.png"))); // NOI18N
        jlVida2.setText("jLabel2");
        Informacion.add(jlVida2, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 60, 40, 30));

        jlVida3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/vida.png"))); // NOI18N
        jlVida3.setText("jLabel2");
        Informacion.add(jlVida3, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 60, 40, 30));

        jlControlGuia.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlControlGuia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlControlGuia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Ayuda.png"))); // NOI18N
        jlControlGuia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ControlGuia(evt);
            }
        });
        Informacion.add(jlControlGuia, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 20, 70, 60));

        Puntaje.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        Puntaje.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Puntaje.setText("150");
        Informacion.add(Puntaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 60, 100, 30));

        jlPuntraje.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlPuntraje.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlPuntraje.setText("Puntaje");
        Informacion.add(jlPuntraje, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 80, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 965, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(5, 12, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(Mapa2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Mapa1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 3, Short.MAX_VALUE))
                .addComponent(Informacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(Informacion, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(Mapa2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Mapa1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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

            case KeyEvent.VK_W:
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
                    if (x >= 600 && y >= 570) {

                        t.interrupt();

                        Sonido s = new Sonido("cambioMundo.wav");
                        dispose();
                        new Mundo2(null, true, nickName, imgpeque, rutaCarpeta);
                    }
                }
                break;

            case KeyEvent.VK_S:
                if (limitesM2(x, y, "down")) {
                    if (rutaCarpeta != null) {
                        alternarImg(3, jLAvatarMapa2);
                    }
                    jLAvatarMapa2.setLocation(x, y + desplazamiento);
                    if (x >= 600 && y >= 570) {

                        t.interrupt();

                        s = new Sonido("juego.wav");
                        dispose();
                        new Mundo2(null, true, nickName, imgpeque, rutaCarpeta);
                    }
                }
                break;

            case KeyEvent.VK_LEFT:
                if (limitesM2(x, y, "left")) {
                    if (rutaCarpeta != null) {
                        alternarImg(4, jLAvatarMapa2);
                    }
                    jLAvatarMapa2.setLocation(x - desplazamiento, y);
                }
                break;
            case KeyEvent.VK_A:
                if (limitesM2(x, y, "left")) {
                    if (rutaCarpeta != null) {
                        alternarImg(4, jLAvatarMapa2);
                    }
                    jLAvatarMapa2.setLocation(x - desplazamiento, y);
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

            case KeyEvent.VK_D:
                if (limitesM2(x, y, "right")) {
                    if (rutaCarpeta != null) {
                        alternarImg(2, jLAvatarMapa2);
                    }
                    jLAvatarMapa2.setLocation(x + desplazamiento, y);
                }
                break;
            case KeyEvent.VK_ESCAPE:
                this.dispose();
                new Mundo1(null, true, nickName, imgpeque, rutaCarpeta);
            break;
        }
        boolean colision = colision();
        infoVida_Puntaje = jug.vida(colision);
        if (colision) {
            s = new Sonido("upsSonido.wav");
        }
        txt.puntaje_vida(infoVida_Puntaje);

    }//GEN-LAST:event_Mapa2KeyPressed

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
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (limitesM1(x, y, "right")) {
                    if (rutaCarpeta != null) {
                        alternarImg(2, jLAvatarMapa1);
                    }
                    jLAvatarMapa1.setLocation(x + desplazamiento, y);
                    if (x >= 900 && y >= 500) {

                        t.interrupt();

                        Sonido s = new Sonido("cambioMundo.wav");
                        dispose();
                        new Mundo2(null, true, nickName, imgpeque, rutaCarpeta);
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
                }
                break;
            case KeyEvent.VK_D:
                if (limitesM1(x, y, "right")) {
                    if (rutaCarpeta != null) {
                        alternarImg(2, jLAvatarMapa1);
                    }
                    jLAvatarMapa1.setLocation(x + desplazamiento, y);
                    if (x >= 900 && y >= 500) {

                        vida = "3";
                        puntaje = "100";

                        t.interrupt();

                        s = new Sonido("cambioMundo.wav");
                        dispose();
                        new Mundo2(null, true, nickName, imgpeque, rutaCarpeta);
                    }
                }
                break;
            case KeyEvent.VK_ESCAPE:
                this.dispose();
                new Mundo1(null, true, nickName, imgpeque, rutaCarpeta);
            break;    
                

        }
        boolean colision = colision();
        infoVida_Puntaje = jug.vida(colision);
        if (colision) {
            s = new Sonido("upsSonido.wav");
            if(var == 3){
               dispose();
            }
            var++;
        }
        txt.puntaje_vida(infoVida_Puntaje);
    }//GEN-LAST:event_Mapa1KeyPressed

    public boolean limitesM1(int x, int y, String direccion) {
        boolean limite = true;
        ////////////////////////////////////ARRIBA//////////////////////////////////////////
        if (direccion.equals("up")) {
            if ((x < 50 && y >= 500)
                    || ((x >= 70 && x < 850) || (x > 860)) && (y > 460 && y <= 500)
                    || ((x <= 140 || (x > 150 && x <= 230)) || (x >= 260 && x <= 380) || (x >= 520 && x <= 640) || (x >= 670 && x <= 740) || x >= 770) && (y > 350 && y <= 400)
                    || ((x >= 70 && x <= 390) || (x >= 520 && x <= 840)) && (y > 250 && y <= 300)
                    || ((x >= 50 && x <= 190) || (x >= 220 && x <= 290) || (x >= 320 && x <= 390) || (x >= 500 && x <= 640) || (x >= 670 && x <= 740) || (x >= 770 && x <= 840)) && (y > 150 && y <= 200)
                    || ((x >= 200 && x <= 210) || (x >= 400 && x <= 410) || (x >= 650 && x <= 660) || (x >= 850 && x <= 860)) && (y > 100 && y <= 150)
                    || (x >= 50 && x <= 860) && y <= 50) {
                return limite = false;
            }
        }
        ///////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////ABAJO//////////////////////////////////////////
        if (direccion.equals("down")) {
            if (y == 510
                    || (x >= 70 && x <= 840) && (y >= 410 && y < 460)
                    || ((x >= 50 && x <= 140) || (x >= 160 && x <= 230) || (x >= 260 && x <= 380) || (x >= 520 && x <= 640) || (x >= 670 && x <= 740) || x >= 770) && (y >= 310 && y < 360)
                    || ((x >= 70 && x <= 390) || (x >= 520 && x <= 840)) && (y >= 210 && y < 260)
                    || ((x >= 50 && x <= 110) || (x >= 500 && x <= 560) || (x >= 770 && x <= 820)) && (y >= 110 && y < 160)
                    || ((x >= 120 && x <= 290) || (x >= 320 && x <= 490) || (x >= 570 && x < 750) || x >= 820) && (y > 50 && y < 110)) {
                return limite = false;
            }

        }
        ///////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////DERECHO//////////////////////////////////////////
        if (direccion.equals("right")) {
            if ((y >= 420 && y <= 490) && (x > 50 && x <= 110)
                    || (y >= 150 && (x > 400 && x <= 450))
                    || (((y >= 320 && y <= 390) && ((x >= 150 && x < 200) || (x >= 250 && x < 300) || (x >= 510 && x < 600) || (x >= 660 && x < 700) || (x >= 760 && x < 800))))
                    || (y >= 220 && y <= 290) && ((x >= 60 && x < 100) || (x >= 510 && x < 600))
                    || (y >= 70 && y <= 190) && ((x >= 310 && x < 360) || (x >= 660 && x < 710))
                    || ((y > 140 && y <= 190) && (x >= 210 && x < 260))
                    || ((y >= 120 && y <= 190) && (x >= 760 && x < 810))
                    || ((y >= 70 && y <= 110) && ((x >= 110 && x < 200) || (x >= 560 && x < 600) || (x >= 810 && x < 860)))
                    || ((y >= 50 && y <= 490) && (x >= 860 && x < 900))) {
                return limite = false;
            }
//                
        }

        ///////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////IZQUIERDA//////////////////////////////////////////
        if (direccion.equals("left")) {
            if ((y >= 50 && y <= 490) && (x <= 50 && x >= 0)
                    || y >= 0 && x == 0
                    || (y >= 420 && y <= 490) && (x > 800 && x <= 850)
                    || (y >= 150 && (x > 450 && x <= 500))
                    || (((y >= 320 && y <= 390) && ((x > 100 && x <= 150) || (x > 190 && x <= 240) || (x > 340 && x <= 390) || (x > 600 && x <= 650) || (x > 700 && x == 750))))
                    || (y >= 220 && y <= 290) && ((x > 350 && x <= 400) || (x > 800 && x == 850))
                    || (y >= 70 && y <= 190) && ((x > 250 && x <= 300) || (x > 700 && x <= 750))
                    || ((y > 140 && y <= 190) && ((x > 150 && x <= 200) || (x > 350 && x <= 400) || (x > 600 && x <= 650)))
                    || ((y >= 120 && y <= 190) && (x > 800 && x <= 850))
                    || ((y >= 70 && y <= 190) && ((x > 450 && x <= 500) || (x > 700 && x <= 750)))) {
                return limite = false;
            }

        }
        return limite;
    }

    public boolean limitesM2(int x, int y, String direccion) {
        boolean limite = true;
        if (direccion.equals("up")) {
            if (((x <= 40 || (x >= 70 && x <= 140) || (x >= 170 && x <= 240) || (x >= 500 && x <= 640) || (x >= 670 && x <= 740) || x >= 770) && (y > 460 && y <= 500))
                    || ((x <= 140 || (x >= 250 && x <= 310) || (x >= 520 && x <= 840)) && (y > 350 && y <= 400))
                    || (((x >= 70 && x <= 240) || (x >= 320 && x <= 590) || (x >= 670 && x <= 740) || x >= 770) && (y > 250 && y <= 300))
                    || ((x >= 70 && x <= 140) || (x >= 270 && x <= 340) || (x >= 570 && x <= 840)) && (y > 150 && y <= 200)
                    || ((x >= 350 && x <= 560) && (y > 100 && y <= 150))
                    || (x >= 50 && y <= 50)) {
                return limite = false;
            }
        }
        if (direccion.equals("down")) {
            if (((x >= -40 && x <= 590) || x >= 620) && (y >= 510 && y < 560)
                    || ((x >= 70 && x <= 140) || (x >= 500 && x <= 640) || (x >= 670 && x <= 740) || x >= 770) && (y >= 410 && y < 460)
                    || ((x >= 50 && x <= 140) || (x >= 170 && x <= 390) || (x >= 520 && x <= 840)) && (y >= 310 && y < 360)
                    || ((x >= 70 && x <= 170) || (x >= 320 && x <= 590) || x >= 770) && (y >= 210 && y < 260)
                    || ((x >= 650 && x <= 660) || (x >= 750 && x <= 760)) && (y >= 110 && y < 160)
                    || ((x >= 70 && x <= 140) || (x > 160 && x <= 240) || (x >= 270 && x <= 640) || (x >= 670 && x <= 740) || (x >= 770 && x <= 840)) && (y >= 60 && y < 110)) {
                return limite = false;
            }
        }
        if (direccion.equals("right")) {//(y>=420 && y<=490)||(y>=70 && y<=190)||(y>=210 &&y<=280)||
            if (((y >= 420 && y <= 490) || (y >= 70 && y <= 190) || (y >= 220 && y <= 290)) && (x > 50 && x <= 110)
                    || ((y >= 320 && y <= 490 || (y >= 70 && y <= 220)) && (x > 150 && x <= 210))
                    || ((y >= 220 && y <= 290) || y >= 400) && (x > 300 && x <= 360)
                    || (y >= 70 && y <= 190) && (x > 250 && x <= 310)
                    || y >= 150 && (x > 400 && x <= 450)
                    || (y >= 320 && y <= 390) && (x > 500 && x <= 560)
                    || (y >= 150 && y <= 190) && (x > 550 && x <= 600)
                    || y >= 520 && (x > 600 && x <= 650)
                    || ((y >= 70 && y <= 110) || (y >= 420 && y <= 490) || (y >= 200 && y <= 290)) && (x > 650 && x <= 710)
                    || ((y >= 70 && y <= 110) || (y >= 220 && y <= 290) || (y >= 420 && y <= 490)) && (x > 750 && x <= 810)
                    || y >= 0 && (x > 850 && x <= 910)) {
                return limite = false;
            }
        }
        if (direccion.equals("left")) {
            if ((y >= 0 && y <= 490) && x <= 50
                    || y >= 0 && x == 0
                    || ((y >= 420 && y <= 490) || (y >= 70 && y <= 190)) && (x > 110 && x <= 150)
                    || ((y >= 320 && y <= 390) && x <= 150)
                    || ((y >= 400 && y <= 490) || (y >= 70 && y <= 290)) && (x > 210 && x <= 250)
                    || ((y >= 150 && y <= 190) && (x > 310 && x <= 350))
                    || ((y >= 320 && y <= 520) && (x > 350 && x <= 400))
                    || (y >= 140 && (x > 450 && x <= 500))
                    || ((y >= 220 && y <= 290) || y >= 520) && (x > 550 && x <= 600)
                    || ((y >= 70 && y <= 120) || (y >= 420 && y <= 490)) && (x > 600 && x <= 650)
            
                    
                    
              
                    
                    
                    
                    
                    || ((y >= 70 && y <= 120) || (y >= 200 && y <= 290) || (y >= 420 && y <= 490)) && (x > 700 && x <= 750)
                    || ((y >= 70 && y <= 190) || (y >= 320 && y <= 390)) && (x > 800 && x <= 850)) {
                return limite = false;
            }
        }
        return limite;
    }

    private void VolverMenu(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VolverMenu
        s = new Sonido("click.wav");
        t.interrupt();
        contEnemigos.interrupt();
        dispose();
        new CrearAvatar(null, true);
    }//GEN-LAST:event_VolverMenu

    private void ControlGuia(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ControlGuia
        s = new Sonido("click.wav");
        new GuiaControles(null, true, "");
    }//GEN-LAST:event_ControlGuia


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fondo;
    private javax.swing.JPanel Informacion;
    private javax.swing.JLabel M1E1;
    private javax.swing.JLabel M1E2;
    private javax.swing.JLabel M1E3;
    private javax.swing.JLabel M2E1;
    private javax.swing.JLabel M2E2;
    private javax.swing.JLabel M2E3;
    private javax.swing.JPanel Mapa1;
    private javax.swing.JPanel Mapa2;
    private javax.swing.JLabel Puntaje;
    private javax.swing.JLabel fondo;
    private javax.swing.JLabel h1;
    private javax.swing.JLabel h2;
    private javax.swing.JLabel j100;
    private javax.swing.JLabel j101;
    private javax.swing.JLabel j102;
    private javax.swing.JLabel j103;
    private javax.swing.JLabel j104;
    private javax.swing.JLabel j105;
    private javax.swing.JLabel j106;
    private javax.swing.JLabel j107;
    private javax.swing.JLabel j108;
    private javax.swing.JLabel j109;
    private javax.swing.JLabel j110;
    private javax.swing.JLabel j111;
    private javax.swing.JLabel j112;
    private javax.swing.JLabel j113;
    private javax.swing.JLabel j114;
    private javax.swing.JLabel j115;
    private javax.swing.JLabel j116;
    private javax.swing.JLabel j117;
    private javax.swing.JLabel j118;
    private javax.swing.JLabel j119;
    private javax.swing.JLabel j120;
    private javax.swing.JLabel j121;
    private javax.swing.JLabel j122;
    private javax.swing.JLabel j123;
    private javax.swing.JLabel j124;
    private javax.swing.JLabel j125;
    private javax.swing.JLabel j126;
    private javax.swing.JLabel j127;
    private javax.swing.JLabel j128;
    private javax.swing.JLabel j129;
    private javax.swing.JLabel j13;
    private javax.swing.JLabel j130;
    private javax.swing.JLabel j131;
    private javax.swing.JLabel j132;
    private javax.swing.JLabel j133;
    private javax.swing.JLabel j134;
    private javax.swing.JLabel j135;
    private javax.swing.JLabel j136;
    private javax.swing.JLabel j137;
    private javax.swing.JLabel j138;
    private javax.swing.JLabel j139;
    private javax.swing.JLabel j140;
    private javax.swing.JLabel j141;
    private javax.swing.JLabel j142;
    private javax.swing.JLabel j143;
    private javax.swing.JLabel j144;
    private javax.swing.JLabel j145;
    private javax.swing.JLabel j146;
    private javax.swing.JLabel j147;
    private javax.swing.JLabel j148;
    private javax.swing.JLabel j149;
    private javax.swing.JLabel j150;
    private javax.swing.JLabel j151;
    private javax.swing.JLabel j152;
    private javax.swing.JLabel j153;
    private javax.swing.JLabel j154;
    private javax.swing.JLabel j155;
    private javax.swing.JLabel j156;
    private javax.swing.JLabel j157;
    private javax.swing.JLabel j158;
    private javax.swing.JLabel j16;
    private javax.swing.JLabel j160;
    private javax.swing.JLabel j161;
    private javax.swing.JLabel j162;
    private javax.swing.JLabel j163;
    private javax.swing.JLabel j164;
    private javax.swing.JLabel j165;
    private javax.swing.JLabel j166;
    private javax.swing.JLabel j167;
    private javax.swing.JLabel j168;
    private javax.swing.JLabel j169;
    private javax.swing.JLabel j17;
    private javax.swing.JLabel j170;
    private javax.swing.JLabel j171;
    private javax.swing.JLabel j172;
    private javax.swing.JLabel j173;
    private javax.swing.JLabel j174;
    private javax.swing.JLabel j175;
    private javax.swing.JLabel j176;
    private javax.swing.JLabel j177;
    private javax.swing.JLabel j178;
    private javax.swing.JLabel j181;
    private javax.swing.JLabel j182;
    private javax.swing.JLabel j183;
    private javax.swing.JLabel j184;
    private javax.swing.JLabel j185;
    private javax.swing.JLabel j186;
    private javax.swing.JLabel j187;
    private javax.swing.JLabel j188;
    private javax.swing.JLabel j189;
    private javax.swing.JLabel j19;
    private javax.swing.JLabel j190;
    private javax.swing.JLabel j191;
    private javax.swing.JLabel j192;
    private javax.swing.JLabel j193;
    private javax.swing.JLabel j194;
    private javax.swing.JLabel j196;
    private javax.swing.JLabel j197;
    private javax.swing.JLabel j198;
    private javax.swing.JLabel j199;
    private javax.swing.JLabel j200;
    private javax.swing.JLabel j201;
    private javax.swing.JLabel j202;
    private javax.swing.JLabel j203;
    private javax.swing.JLabel j204;
    private javax.swing.JLabel j205;
    private javax.swing.JLabel j206;
    private javax.swing.JLabel j207;
    private javax.swing.JLabel j208;
    private javax.swing.JLabel j209;
    private javax.swing.JLabel j210;
    private javax.swing.JLabel j211;
    private javax.swing.JLabel j212;
    private javax.swing.JLabel j213;
    private javax.swing.JLabel j214;
    private javax.swing.JLabel j215;
    private javax.swing.JLabel j216;
    private javax.swing.JLabel j217;
    private javax.swing.JLabel j218;
    private javax.swing.JLabel j219;
    private javax.swing.JLabel j22;
    private javax.swing.JLabel j220;
    private javax.swing.JLabel j221;
    private javax.swing.JLabel j224;
    private javax.swing.JLabel j225;
    private javax.swing.JLabel j226;
    private javax.swing.JLabel j227;
    private javax.swing.JLabel j228;
    private javax.swing.JLabel j229;
    private javax.swing.JLabel j23;
    private javax.swing.JLabel j232;
    private javax.swing.JLabel j233;
    private javax.swing.JLabel j234;
    private javax.swing.JLabel j235;
    private javax.swing.JLabel j236;
    private javax.swing.JLabel j237;
    private javax.swing.JLabel j238;
    private javax.swing.JLabel j239;
    private javax.swing.JLabel j24;
    private javax.swing.JLabel j240;
    private javax.swing.JLabel j241;
    private javax.swing.JLabel j242;
    private javax.swing.JLabel j243;
    private javax.swing.JLabel j244;
    private javax.swing.JLabel j245;
    private javax.swing.JLabel j247;
    private javax.swing.JLabel j25;
    private javax.swing.JLabel j250;
    private javax.swing.JLabel j251;
    private javax.swing.JLabel j255;
    private javax.swing.JLabel j256;
    private javax.swing.JLabel j259;
    private javax.swing.JLabel j26;
    private javax.swing.JLabel j260;
    private javax.swing.JLabel j261;
    private javax.swing.JLabel j263;
    private javax.swing.JLabel j264;
    private javax.swing.JLabel j265;
    private javax.swing.JLabel j266;
    private javax.swing.JLabel j267;
    private javax.swing.JLabel j269;
    private javax.swing.JLabel j271;
    private javax.swing.JLabel j272;
    private javax.swing.JLabel j273;
    private javax.swing.JLabel j274;
    private javax.swing.JLabel j275;
    private javax.swing.JLabel j276;
    private javax.swing.JLabel j277;
    private javax.swing.JLabel j278;
    private javax.swing.JLabel j279;
    private javax.swing.JLabel j28;
    private javax.swing.JLabel j280;
    private javax.swing.JLabel j281;
    private javax.swing.JLabel j282;
    private javax.swing.JLabel j283;
    private javax.swing.JLabel j284;
    private javax.swing.JLabel j286;
    private javax.swing.JLabel j29;
    private javax.swing.JLabel j3;
    private javax.swing.JLabel j30;
    private javax.swing.JLabel j31;
    private javax.swing.JLabel j32;
    private javax.swing.JLabel j33;
    private javax.swing.JLabel j34;
    private javax.swing.JLabel j35;
    private javax.swing.JLabel j36;
    private javax.swing.JLabel j37;
    private javax.swing.JLabel j38;
    private javax.swing.JLabel j39;
    private javax.swing.JLabel j4;
    private javax.swing.JLabel j40;
    private javax.swing.JLabel j41;
    private javax.swing.JLabel j42;
    private javax.swing.JLabel j43;
    private javax.swing.JLabel j44;
    private javax.swing.JLabel j45;
    private javax.swing.JLabel j46;
    private javax.swing.JLabel j47;
    private javax.swing.JLabel j48;
    private javax.swing.JLabel j49;
    private javax.swing.JLabel j50;
    private javax.swing.JLabel j51;
    private javax.swing.JLabel j52;
    private javax.swing.JLabel j53;
    private javax.swing.JLabel j54;
    private javax.swing.JLabel j55;
    private javax.swing.JLabel j56;
    private javax.swing.JLabel j57;
    private javax.swing.JLabel j58;
    private javax.swing.JLabel j59;
    private javax.swing.JLabel j60;
    private javax.swing.JLabel j61;
    private javax.swing.JLabel j62;
    private javax.swing.JLabel j63;
    private javax.swing.JLabel j64;
    private javax.swing.JLabel j65;
    private javax.swing.JLabel j66;
    private javax.swing.JLabel j67;
    private javax.swing.JLabel j68;
    private javax.swing.JLabel j69;
    private javax.swing.JLabel j70;
    private javax.swing.JLabel j71;
    private javax.swing.JLabel j72;
    private javax.swing.JLabel j73;
    private javax.swing.JLabel j74;
    private javax.swing.JLabel j75;
    private javax.swing.JLabel j76;
    private javax.swing.JLabel j77;
    private javax.swing.JLabel j78;
    private javax.swing.JLabel j79;
    private javax.swing.JLabel j8;
    private javax.swing.JLabel j80;
    private javax.swing.JLabel j81;
    private javax.swing.JLabel j82;
    private javax.swing.JLabel j83;
    private javax.swing.JLabel j84;
    private javax.swing.JLabel j85;
    private javax.swing.JLabel j86;
    private javax.swing.JLabel j87;
    private javax.swing.JLabel j88;
    private javax.swing.JLabel j89;
    private javax.swing.JLabel j9;
    private javax.swing.JLabel j90;
    private javax.swing.JLabel j91;
    private javax.swing.JLabel j92;
    private javax.swing.JLabel j93;
    private javax.swing.JLabel j94;
    private javax.swing.JLabel j95;
    private javax.swing.JLabel j96;
    private javax.swing.JLabel j97;
    private javax.swing.JLabel j98;
    private javax.swing.JLabel j99;
    private javax.swing.JLabel jLAvatarMapa1;
    private javax.swing.JLabel jLAvatarMapa2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jlControlGuia;
    private javax.swing.JLabel jlPuntraje;
    private javax.swing.JLabel jlVida1;
    private javax.swing.JLabel jlVida2;
    private javax.swing.JLabel jlVida3;
    private javax.swing.JLabel jlVolver;
    private javax.swing.JLabel jtVida;
    // End of variables declaration//GEN-END:variables
}
