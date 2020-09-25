/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.ModeloMundo1;
import Vista.Mundo1;

/**
 *
 * @author ANDRES ARBOLEDA
 */

public class ControlMundo1 {
    ModeloMundo1 modelo = new ModeloMundo1();
    
    Mundo1 mundo;
    Mundo1.mapa1 map;
    
    String tipoMundo, cantEnemigos, horaDia, avatar;
   
    public ControlMundo1(){
//        tipoMundo = modelo.getTipoMundo();
//        cantEnemigos = modelo.getCantEnemigos();
//        horaDia = modelo.getHoraDia();
//        avatar = modelo.getAvatar();
        mundo = new Mundo1();
        map = mundo.getMapa1();
        
//        System.out.println("Tal2 " + map.prueba(1));
    }     
    
    public static void main(String[] args) {
        new ControlMundo1();
    }
}
