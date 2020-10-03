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
    File tiempo = new File("persistencia/tiempo.txt");
    File vida_puntaje = new File("persistencia/vida_Puntaje.txt");
    String p, v;
    public void puntaje_vida(String[] a){
        for (int i = 0; i < 2; i++) {
            System.out.print( a[i]+"---");
        }
        v=a[0];
        p=a[1];
        System.out.println("v= " +v);
        System.out.println("p= " +p);
        Vida_putnajeTXT(v, p);
    }
    public void Vida_putnajeTXT(String v, String p){
        try {
            if (!vida_puntaje.exists()) {
                vida_puntaje.createNewFile();
            }
            
            FileWriter fw = new FileWriter(vida_puntaje,false);
            BufferedWriter bw = new BufferedWriter(fw);
            
            bw.write("\n"+v);
            bw.write("\n"+p);
            bw.close();
            
        } catch (Exception e) {
            System.out.println("Error crear TXT -> "+ e );
        }
    }
    public void tiempoTXT(String tiempo) {//,String vida, String puntaje
        
        try {
            if (!this.tiempo.exists()) {
                this.tiempo.createNewFile();
            }
            
            FileWriter fw = new FileWriter(this.tiempo,false);
            BufferedWriter bw = new BufferedWriter(fw);
            
            bw.write(tiempo);
            bw.close();
            
        } catch (Exception e) {
            System.out.println("Error crear TXT -> "+ e );
        }
    }
    
    public String[] leerTiempo() {
            String[] dato = new String[3];
        try {
            
            FileReader f = new FileReader(tiempo);
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
     public String[] leerVidaPuntaje() {
            String[] dato = new String[3];
        try {
            
            FileReader f = new FileReader(vida_puntaje);
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
    
    
    public String[] leerTodo() {
            String[] dato = new String[3];
        try {
            
            FileReader f = new FileReader(tiempo);
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
            System.out.println("ERROR DE LEER TODO tiempo " +e) ;
        }
        try {
            
            FileReader f = new FileReader(vida_puntaje);
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
            System.out.println("ERROR DE LEER TODO Puntaje y vida " +e);
        }
        for (int i = 0; i < 3; i++) {
            System.out.println(dato[i]);
        }
            return dato;
    }

}
