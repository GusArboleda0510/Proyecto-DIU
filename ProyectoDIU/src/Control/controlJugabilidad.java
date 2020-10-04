/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Vista.GamerOver;
import javax.swing.JLabel;

/**
 *
 * @author Alejandra Becerra
 */
public class controlJugabilidad {
    JLabel vida1,vida2,vida3,puntaj;
    int vida=3, puntaje=150;
    String[] info= new String[2];
    int colisiones=1;
    public controlJugabilidad() {
    }
    
    public controlJugabilidad(JLabel vida1,JLabel vida2, JLabel vida3, JLabel puntaj) {
        this.vida1=vida1;
        this.vida2=vida2;
        this.vida3=vida3;
        this.puntaj=puntaj;
    }
    public String[]  vida(boolean colsion){
        if(!colsion){
            info[0]=Integer.toString(vida);
            info[1]=Integer.toString(puntaje);
                
//            if(mundo.equals("mundo2")){
//                info[0]=Integer.toString(vida);
//                info[1]=Integer.toString(puntaje+200);
//            }
//            if(mundo.equals("mundo3")){
//                info[0]=Integer.toString(vida);
//                info[1]=Integer.toString(puntaje+300);
//            }
        } else {
            
            System.out.println("colisiones == " +colisiones);
            if (colisiones == 1) {
                vida3.setVisible(false);
                vida -= 1;
                puntaje -= 50;
                puntaj.setText("" + puntaje);
            }
            if (colisiones == 2) {
                vida3.setVisible(false);
                vida2.setVisible(false);
                vida -= 1;
                puntaje -= 50;
                puntaj.setText("" + puntaje);
            }
            if (colisiones == 3) {
                vida1.setVisible(false);
                vida3.setVisible(false);
                vida2.setVisible(false);
                vida -= 1;
                puntaje -= 50;
                puntaj.setText("" + puntaje);

            }
            if (colisiones == 4) {
                String punt = Integer.toString(puntaje);
                new GamerOver(null, true, punt);
            }

            colisiones++;
            info[0] = Integer.toString(vida);
            info[1] = Integer.toString(puntaje);
        }
            return info;
    }
    
}
