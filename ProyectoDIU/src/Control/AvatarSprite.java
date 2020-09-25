/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Vista.Mundo1;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author ANDRES ARBOLEDA
 */
public class AvatarSprite extends Thread{

    JLabel avatar = null;
    Mundo1 mundo = new Mundo1("Nada");
    Mundo1.mapa1 mapa;
    JLabel sprite1 = null, sprite2 = null;
    int posAnterior[] = new int[2];//x,y

    public AvatarSprite(JLabel avatar) {
        this.avatar = avatar;
        mapa = mundo.getMapa1();
        sprite1 = new JLabel();
        sprite2 = new JLabel();

    }

    public void run() {
        int x, y, n = 0, cambio = 1;
        int dir = (int) (Math.random() * 4 + 1);//arriba, derecha, abajo, izquierda
        while (true) {
            if (cambio == 1) { 
                
//                if (cambio == 1) {
//                if(var == 1){
//                    sprite1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Avatars/Avatar1/1.png")));
//                    var=2;
//                }
//                if(var == 2){
//                    sprite1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Avatars/Avatar1/2.png")));
//                    var=1;
//                }
                sprite1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Avatars/Avatar1/1.png")));
                avatar.setIcon(sprite1.getIcon());
//                alternar(1);
                System.out.println("m "+avatar.getIcon());

            }
            if (cambio == 2) {   
                sprite1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Avatars/Avatar1/3.png")));
                avatar.setIcon(sprite1.getIcon());
//                alternar(2);
            }        
            
            if (n > 10) {
                int aux = dir;
                dir = (int) (Math.random() * 4 + 1);
                if (aux == dir) {
                    run();
                }
            }
            n++;
//            dir=1;
            if (dir == 1) {
                x = avatar.getX();
                y = avatar.getY();
                y -= 10;
                if (mapa.limites(x, y, getDireccion(x, y))) {
                    avatar.setLocation(x, y);
                    posAnterior[0] = x;
                    posAnterior[1] = y;
                } else {
                    run();
                }
            }
            if (dir == 2) {
                cambio=1;
                x = avatar.getX();
                y = avatar.getY();
                x += 10;
                if (mapa.limites(x, y, getDireccion(x, y))) {
                    avatar.setLocation(x, y);
                    posAnterior[0] = x;
                    posAnterior[1] = y;
                } else {
                    run();
                }
            }

            if (dir == 3) {
                x = avatar.getX();
                y = avatar.getY();
                y += 10;
                if (mapa.limites(x, y, getDireccion(x, y))) {
                    avatar.setLocation(x, y);
                    posAnterior[0] = x;
                    posAnterior[1] = y;
                } else {
                    run();
                }
            }

            if (dir == 4) {
                cambio=2;
                x = avatar.getX();
                y = avatar.getY();
                x -= 10;
                if (mapa.limites(x, y, getDireccion(x, y))) {
                    avatar.setLocation(x, y);
                    posAnterior[0] = x;
                    posAnterior[1] = y;
                } else {
                    run();
                }
            }
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException ex) {
                System.out.println("ERRor");
            }
        }

    }

    private String getDireccion(int x, int y) {
        String direccion = "";
        if (x > posAnterior[0]) {
            direccion = "right";
        }
        if (x < posAnterior[0]) {
            direccion = "left";
        }
        if (y > posAnterior[1]) {
            direccion = "down";
        }
        if (y < posAnterior[1]) {
            direccion = "up";
        }
        return direccion;
    }

    private void alternar(int dir) {
        if (dir == 1) {
            sprite1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Avatars/Avatar1/1.png")));
            sprite2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Avatars/Avatar1/2.png")));
            System.out.println(avatar.getIcon() == sprite1.getIcon());
            System.out.println(" " + avatar.getIcon() + " " + sprite1.getIcon());
            if (avatar.getIcon() == sprite1.getIcon()) {
                System.out.println("1");
                avatar.setIcon(sprite2.getIcon());
                return;
            }
            if (avatar.getIcon() == sprite2.getIcon()) {
                System.out.println("2");
                avatar.setIcon(sprite1.getIcon());
                return;
            }
        }
        if (dir == 2) {
            sprite1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Avatars/Avatar1/3.png")));
            sprite2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Avatars/Avatar1/4.png")));
//            System.out.println("control");
            if (avatar.getIcon() == sprite1.getIcon()) {
                System.out.println("3");
                avatar.setIcon(sprite2.getIcon());
                return;
            }
            if (avatar.getIcon() == sprite2.getIcon()) {
                System.out.println("4");
                avatar.setIcon(sprite1.getIcon());
                return;
            }
        }
    }
}
