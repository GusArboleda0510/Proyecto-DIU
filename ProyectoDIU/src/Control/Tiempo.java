/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.util.concurrent.TimeUnit;
import javax.swing.JLabel;

/**
 *
 * @author Alejandra Becerra
 */
public class Tiempo extends Thread{
    int hora,minuto,segundo;
    JLabel Tiempo;
    String[] dato;
    
    
    public Tiempo(JLabel Tiempo,String dato[]) {
        this.Tiempo=Tiempo;
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
                Tiempo.setText(hora + ":" + minuto + ":" + segundo);
                System.out.println(hora + ":" + minuto + ":" + segundo);
                TimeUnit.MILLISECONDS.sleep(999);
            }
        } catch (Exception e) {
            System.out.println("Error RUN: " + e);
        }
    }
}
