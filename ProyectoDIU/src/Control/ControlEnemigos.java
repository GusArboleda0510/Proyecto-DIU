/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Vista.Mundo1;
import Vista.Mundo2;
import Vista.Mundo3;
import java.util.concurrent.TimeUnit;
import javax.swing.JLabel;

/**
 *
 * @author ANDRES ARBOLEDA
 */
public class ControlEnemigos extends Thread{
    int cambio = 1;
    JLabel avatar = null;
    Mundo1 mundo1= new Mundo1("nada");
    Mundo2 mundo2= new Mundo2("nada");
    Mundo3 mundo3= new Mundo3("nada");
    String nombMundo, nombMapa;
    int contador=0;
    int posAnterior[] = new int [2];//x,y
    String rutaCarpeta;
    public ControlEnemigos(JLabel avatar, String mundo, String mapa, String rutaCarpeta){
        this.avatar = avatar;
        this.nombMapa = mapa;
        this.nombMundo = mundo;
        this.rutaCarpeta = rutaCarpeta;
    }
    
    public void run() {
        int x, y, n = 0;
        int dir = (int) (Math.random() * 4 + 1);//arriba, derecha, abajo, izquierda
//        dir = 2;
        while (true) {
            if (n > 10) {
                int aux = dir;
                dir = (int) (Math.random() * 4 + 1);
                if (aux == dir) {
                    run();
                }
            }
            n++;
            if (dir == 1) {
                if(rutaCarpeta != null){
                    alternarImg(dir);  
                }
                x = avatar.getX();
                y = avatar.getY();
                y -= 10;
                
                if (consultarMapa(x, y, getDireccion(x, y))) {
                    avatar.setLocation(x, y);
                    posAnterior[0] = x;
                    posAnterior[1] = y;
                } else {
                    run();
                }
            }
            
            if (dir == 2) {
                if(rutaCarpeta != null){
                    alternarImg(dir);  
                }               
                x = avatar.getX();
                y = avatar.getY();
                x += 10;
                if (consultarMapa(x, y, getDireccion(x, y))) {
                    avatar.setLocation(x, y);
                    posAnterior[0] = x;
                    posAnterior[1] = y;
                } else {
                    run();
                }
            }

            if (dir == 3) {
                if(rutaCarpeta != null){
                    alternarImg(dir);  
                }
                x = avatar.getX();
                y = avatar.getY();
                y += 10;
                if (consultarMapa(x, y, getDireccion(x, y))) {
                    avatar.setLocation(x, y);
                    posAnterior[0] = x;
                    posAnterior[1] = y;
                } else {
                    run();
                }
            }

            if (dir == 4) {
                if(rutaCarpeta != null){
                    alternarImg(dir);  
                }
                x = avatar.getX();
                y = avatar.getY();
                x -= 10;
                if (consultarMapa(x, y, getDireccion(x, y))) {
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
                System.out.println("Error en Hilo " + ex);
            }
        }

    }
    
    private String getDireccion(int x, int y) {
        String direccion = "";
        if(x > posAnterior[0]){
            direccion = "right";
        }
        if(x < posAnterior[0]){
            direccion = "left";
        }
        if(y > posAnterior[1]){
            direccion = "down";
        }
        if(y < posAnterior[1]){
            direccion = "up";
        }
        return direccion;    
    } 

    private void alternarImg(int dir) {
        if (dir == 1) {
            if (cambio == 1) {
                avatar.setIcon(new javax.swing.ImageIcon(getClass().getResource(rutaCarpeta+"/arr1.jpg")));
                cambio = 2;
            } else {
                if (cambio == 2) {
                    avatar.setIcon(new javax.swing.ImageIcon(getClass().getResource(rutaCarpeta+"/arr2.jpg")));
                    cambio = 1;
                }
            }
        }
        if (dir == 2) {
            if (cambio == 1) {
                avatar.setIcon(new javax.swing.ImageIcon(getClass().getResource(rutaCarpeta+"/der1.jpg")));
                cambio = 2;
            } else {
                if (cambio == 2) {
                    avatar.setIcon(new javax.swing.ImageIcon(getClass().getResource(rutaCarpeta+"/der2.jpg")));
                    cambio = 1;
                }
            }
        }
        
        if (dir == 3) {
            if (cambio == 1) {
                avatar.setIcon(new javax.swing.ImageIcon(getClass().getResource(rutaCarpeta+"/abj1.jpg")));
                cambio = 2;
            } else {
                if (cambio == 2) {
                    avatar.setIcon(new javax.swing.ImageIcon(getClass().getResource(rutaCarpeta+"/abj2.jpg")));
                    cambio = 1;
                }
            }
        }
        if (dir == 4) {
            if (cambio == 1) {
                avatar.setIcon(new javax.swing.ImageIcon(getClass().getResource(rutaCarpeta+"/izq1.jpg")));
                cambio = 2;
            } else {
                if (cambio == 2) {
                    avatar.setIcon(new javax.swing.ImageIcon(getClass().getResource(rutaCarpeta+"/izq2.jpg")));
                    cambio = 1;
                }
            }
        }
    }

    private boolean consultarMapa(int x, int y, String direccion) {
        if(nombMundo.equals("mundo1")){
            if(nombMapa.equals("mapa1")){
                return mundo1.limitesM1(x, y, direccion);
            }else{
                if(nombMapa.equals("mapa2")){
                    return mundo1.limitesM2(x, y, direccion);
                } 
            }  
        }
        if(nombMundo.equals("mundo2")){
            if(nombMapa.equals("mapa1")){
                return mundo2.limitesM1(x, y, direccion);
            }else{
                if(nombMapa.equals("mapa2")){
                    return mundo2.limitesM2(x, y, direccion);
                } 
            }  
        }
        if(nombMundo.equals("mundo3")){
            if(nombMapa.equals("mapa1")){
                return mundo3.limitesM1(x, y, direccion);
            } 
        } 
        return false;
    }
}

