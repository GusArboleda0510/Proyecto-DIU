/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Vista.Mundo1;
import java.util.concurrent.TimeUnit;
import javax.swing.JLabel;

/**
 *
 * @author ANDRES ARBOLEDA
 */
public class ControlEnemigos extends Thread{
    JLabel avatar = null;
    Mundo1 mundo= new Mundo1("nada");
    Mundo1.mapa1 mapa;
    int contador=0;
//    Mundo1.mapa2 mapa2;


    int posAnterior[] = new int [2];//x,y
    public ControlEnemigos(JLabel avatar, String mapa){
        this.avatar = avatar;
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

