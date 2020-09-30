/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author Alejandra Becerra
 */
public class Sonido {

    Clip clip;
    String ruta="/Sonido/";
    public Sonido(String archivo) {
        try {
            clip=AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream(ruta+archivo)));
            clip.start();
        } catch (Exception e) {
            System.out.println("ERROR de sonido " +e);
        }
    }
    
}
