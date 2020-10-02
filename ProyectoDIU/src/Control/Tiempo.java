/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author Alejandra Becerra
 */

   public class Tiempo extends Thread{
    int hora,minuto,segundo;
    String[] dato;
    String tiempo;
    ControlTXT txt = new ControlTXT();
    controlJugabilidad jug = new controlJugabilidad();
    public Tiempo(String dato[]) {
        this.dato=dato;
        
    }
    
    public void run() {
        if(dato!=null){
            hora=Integer.parseInt(dato[0]);
            minuto=Integer.parseInt(dato[1]);
            segundo=Integer.parseInt(dato[2]);
        }else{
            hora=0;minuto=0;segundo=0;
        }
            
        try {
            for (;;) {
                segundo++;
                if (segundo > 59) {
                    segundo = 0;
                    minuto++;
                }
                if (minuto > 59) {
                    segundo = 0;
                    minuto = 0;
                    hora++;
                }
                tiempo=hora + ":" + minuto + ":" + segundo;
//                txt.setTiempo(tiempo);
                System.out.println(hora + ":" + minuto + ":" + segundo);
                TimeUnit.SECONDS.sleep(1);
            }
        }
        catch (InterruptedException es) {
            System.out.println("TIEMPO= " + tiempo);
            
            System.out.println("RETORNA PUNTAJE: " + jug.getPuntaje());
            System.out.println("RETORNA VIDA: " + jug.getVida());
            txt.crearTXT(tiempo,Integer.toString(jug.getVida()),Integer.toString(jug.getPuntaje()));
        }
        catch (Exception e) {
            System.out.println("Error RUN: " + e);
        }
    }
    public String getTiempo(){
        return tiempo;
    }
}
