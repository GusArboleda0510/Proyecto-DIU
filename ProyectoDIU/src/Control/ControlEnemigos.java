/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Vista.Mundo1;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;
import javax.swing.JLabel;

/**
 *
 * @author ANDRES ARBOLEDA
 */
public class ControlEnemigos extends Thread{
    JLabel avatar = null;
    JLabel tiemp ;
    Mundo1 mundo= new Mundo1("nada");
    Mundo1.mapa1 mapa;
    String tiempo="";
    int contador=0;
    int hora,minuto,segundo;
//    Mundo1.mapa2 mapa2;


    int posAnterior[] = new int [2];//x,y
    public ControlEnemigos(JLabel avatar, String mapa,String tiempo,JLabel tiemp){
        this.avatar = avatar;
        this.tiemp = tiemp;
        this.tiempo = tiempo;
//        if
        this.mapa = mundo.getMapa1();
//        mapa2 = mundo.getMapa2();
    }
    public void run() {
        int x, y, n=0;
        int dir = (int) (Math.random() * 4 + 1);//arriba, derecha, abajo, izquierda
        while (true) {
            contador++;
            if (n > 50) {
                int aux = dir;
                dir = (int) (Math.random() * 4 + 1);
                if(aux == dir){
                   run(); 
                }
            }
            n++;
//            dir=1;
            if (dir == 1) {
                x = avatar.getX();
                y = avatar.getY();
                y -= 10;
                if(mapa.limites(x, y, getDireccion(x,y))){
                    avatar.setLocation(x, y);
                    posAnterior[0] = x; 
                    posAnterior[1] = y; 
                } else {
                    run();
                }
            }
            if (dir == 2) {
                x = avatar.getX();
                y = avatar.getY();
                x += 10;
                if(mapa.limites(x, y, getDireccion(x,y))){
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
                if(mapa.limites(x, y, getDireccion(x,y))){
                    avatar.setLocation(x, y);
                    posAnterior[0] = x; 
                    posAnterior[1] = y; 
                } else {
                    run();
                }
            }
            
            if (dir == 4) {
                x = avatar.getX();
                y = avatar.getY();
                x -= 10;
                if(mapa.limites(x, y, getDireccion(x,y))){
                    avatar.setLocation(x, y);
                    posAnterior[0] = x; 
                    posAnterior[1] = y; 
                } else {
                    run();
                    
                }
            }
            try {
                ////////////////////////////////////////////

                if (tiempo.equals("tiempo")) {
//                    if (contador % 5 == 0) {
//                        segundo++;
//                        if (segundo > 59) {
//                            segundo = 0;
//                            minuto++;
//                        }
//                        if (minuto > 59) {
//                            segundo = 0;
//                            minuto = 0;
//                            hora++;
//                        }
//                        System.out.println(hora + ":" + minuto + ":" + segundo);
////                        tiempo(hora + ":" + minuto + ":" + segundo);
//                    }

                }
                //////////////////////////////////////////
                TimeUnit.MILLISECONDS.sleep(200);

            } catch (InterruptedException ex) {
                System.out.println("ERROR " + ex);
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
//    public void tiempo(String temporalizador){
//        tiemp.setText(temporalizador);
//    }
}

