/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import javax.swing.JLabel;

/**
 *
 * @author Alejandra Becerra
 */
public class controlJugabilidad {
    JLabel vida1,vida2,vida3,puntaj;
    int vida=3, puntaje=150;

    public controlJugabilidad() {
    }
    
    public controlJugabilidad(JLabel vida1,JLabel vida2, JLabel vida3, JLabel puntaj) {
        this.vida1=vida1;
        this.vida2=vida2;
        this.vida3=vida3;
        this.puntaj=puntaj;
    }
    public void vida(int colisiones){
        if(colisiones==1){
            vida3.setVisible(false);
            vida-=1;
            puntaje-=50;
            puntaj.setText(""+puntaje);
        }
        if(colisiones==2){
            vida3.setVisible(false);
            vida2.setVisible(false);
            vida-=1;
            puntaje-=100;
            puntaj.setText(""+puntaje);
        }
        if(colisiones==3){
            vida1.setVisible(false);
            vida3.setVisible(false);
            vida2.setVisible(false);
            vida-=1;
            puntaje-=150;
            puntaj.setText(""+puntaje);
            
        }
        if(colisiones==4){
            //GAMER OVER
        }
    }

    public int getVida() {
        return vida;
    }

    public int getPuntaje() {
        
        return puntaje;
    }
    
}
