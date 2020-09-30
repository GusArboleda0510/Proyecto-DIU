/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 *
 * @author Alejandra Becerra
 */
public class ControlTXT {
    File jugabilidad = new File("persistencia/jugabilidad.txt");
    public void crearTXT(String tiempo, String vida, String puntaje) {

        try {
            if (!jugabilidad.exists()) {
                jugabilidad.createNewFile();
            }
            FileWriter fw = new FileWriter(jugabilidad, false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(tiempo);
            bw.write("\n"+vida);
            bw.write("\n"+puntaje);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String[] leerTXT() {
            String[] dato = new String[3];
        try {
            
            FileReader f = new FileReader(jugabilidad);
            BufferedReader b = new BufferedReader(f);
            
            int aux=0;
            String cadena;
            while ((cadena = b.readLine()) != null) {
                System.out.println(cadena);
                dato[aux] = cadena;
                aux++;
            }
            b.close();

        } catch (Exception e) {
        }
            return dato;
    }

}
