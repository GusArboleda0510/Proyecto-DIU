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
    File nombre = new File("persistencia/nickName.txt");
    String NickName="";
    
    public String nombre(String nomb){
        return this.NickName=nomb;
    }
    public void puntaje_vida(String[] a){
        Vida_putnajeTXT(a[0], a[1]);
    }
    public void Vida_putnajeTXT(String v, String p){
        
        try {
            if (!vida_puntaje.exists()) {
                vida_puntaje.createNewFile();
            }
            
            FileWriter fw = new FileWriter(vida_puntaje,false);
            BufferedWriter bw = new BufferedWriter(fw);
            
            bw.write(v);
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
    public void crearNickName(String name) {//,String vida, String puntaje
        
        try {
            if (!this.nombre.exists()) {
                this.nombre.createNewFile();
            }
            
            FileWriter fw = new FileWriter(this.nombre,false);
            BufferedWriter bw = new BufferedWriter(fw);
            
            
            bw.write(name);
            bw.close();
//            System.out.println("TXT== " +name);
        } catch (Exception e) {
            System.out.println("Error crear TXT -> "+ e );
        }
    }
    public String[] leerTiempo() {
            String[] dato = new String[1];
        try {
            
            FileReader f = new FileReader(tiempo);
            BufferedReader b = new BufferedReader(f);
            
            int aux=0;
            String cadena;
            while ((cadena = b.readLine()) != null) {
                dato[aux] = cadena;
                aux++;
            }
            b.close();

        } catch (Exception e) {
        }
        
            return dato;
    }
    public String leerNickName() {
            String dato="" ;
        try {
            
            FileReader f = new FileReader(nombre);
            BufferedReader b = new BufferedReader(f);
            
            int aux=0;
            String cadena;
            while ((cadena = b.readLine()) != null) {
                dato = cadena;
                aux++;
            }
            b.close();

        } catch (Exception e) {
        }
        
            return dato;
    }
    public String[] leerVidaPuntaje() {
            String[] dato =  new String[2];
            
        try {
            
            FileReader f = new FileReader(vida_puntaje);
            BufferedReader b = new BufferedReader(f);
            
            int aux=0;
            String cadena;
            while ((cadena = b.readLine()) != null) {
                dato[aux] = cadena;
                aux++;
            }
            b.close();

        } catch (Exception e) {
            System.out.println("Leer Vida_Puntaje " + e);
        }
            return dato;
    }
    public String[] leerTodo() {
            String[] dato = new String[3];
            int aux=0;
        try {
            
            FileReader f = new FileReader(tiempo);
            BufferedReader b = new BufferedReader(f);
            
            
            String cadena;
            while ((cadena = b.readLine()) != null) {
                dato[aux] = cadena;
                
            }
            b.close();
            aux++;
        } catch (Exception e) {
            System.out.println("ERROR DE LEER TODO tiempo " +e) ;
        }
        try {
            
            FileReader f = new FileReader(vida_puntaje);
            BufferedReader b = new BufferedReader(f);
            
            String cadena;
            while ((cadena = b.readLine()) != null) {
                dato[aux] = cadena;
                aux++;
            }
            b.close();
            

        } catch (Exception e) {
            System.out.println("ERROR DE LEER TODO Puntaje y vida " +e);
        }
       
            return dato;
    }

}
