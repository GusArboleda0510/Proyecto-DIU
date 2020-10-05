/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Vista.Menu;
import java.util.concurrent.TimeUnit;



/**
 *
 * @author Alejandra Becerra
 */
public class sonidoJuego extends Thread{
    Sonido s;
    String ruta="juego.wav";
    public  void run(){
        
        try {
                while (true) {                
                s = new Sonido(ruta);
                TimeUnit.SECONDS.sleep(19);
            }
        }
        catch (InterruptedException es) {
            //NADA
            ruta="";
            s.clip.close();
        }
        
        
        catch (Exception e) {
            System.out.println("Error RUN: " + e);
        }
    }
    
}
