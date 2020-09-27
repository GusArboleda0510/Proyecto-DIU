/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.CreadordeDocs;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author ANDRES ARBOLEDA
 */
public class ControlCrearAvatar {
    BufferedImage imagen = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
    File fichero;
    String formato = "png";
    Color[][] colores = new Color [2][6];
    CreadordeDocs cd;
    File avatars = new File("Avatars.xml");
    String nickNameJugador;
    public ControlCrearAvatar(Color[][] colores, String nombreAvatar) throws Exception {
        this.colores = colores;  
        fichero = new File("src/Imagenes/Avatars/Usuarios/"+nombreAvatar+".png");
        nickNameJugador = nombreAvatar;      
        dibujarImagen();
        crearImagen();
        guardarXML();
    }
    
    public void dibujarImagen() {
        Graphics g = imagen.getGraphics();
        int ancho = 5, alto = 5, y,fila;
        
        y = 0;
        fila = 0;
        g.setColor(colores[fila][0]);
        g.fillRect(0, y, ancho, alto);    
        g.setColor(colores[fila][1]);
        g.fillRect(5, y, ancho, alto);    
        g.setColor(colores[fila][2]);
        g.fillRect(10, y, ancho, alto);    
        g.setColor(colores[fila][3]);
        g.fillRect(15, y, ancho, alto);    
        g.setColor(colores[fila][4]);
        g.fillRect(20, y, ancho, alto);    
        g.setColor(colores[fila][5]);
        g.fillRect(25, y, ancho, alto);    
        g.setColor(colores[fila][6]);
        g.fillRect(30, y, ancho, alto);    
        g.setColor(colores[fila][7]);
        g.fillRect(35, y, ancho, alto);    
        
        y = 5;
        fila = 1;
        g.setColor(colores[fila][0]);
        g.fillRect(0, y, ancho, alto);    
        g.setColor(colores[fila][1]);
        g.fillRect(5, y, ancho, alto);    
        g.setColor(colores[fila][2]);
        g.fillRect(10, y, ancho, alto);    
        g.setColor(colores[fila][3]);
        g.fillRect(15, y, ancho, alto);    
        g.setColor(colores[fila][4]);
        g.fillRect(20, y, ancho, alto);    
        g.setColor(colores[fila][5]);
        g.fillRect(25, y, ancho, alto);    
        g.setColor(colores[fila][6]);
        g.fillRect(30, y, ancho, alto);    
        g.setColor(colores[fila][7]);
        g.fillRect(35, y, ancho, alto);    
        
        y = 10;
        fila = 2;
        g.setColor(colores[fila][0]);
        g.fillRect(0, y, ancho, alto);    
        g.setColor(colores[fila][1]);
        g.fillRect(5, y, ancho, alto);    
        g.setColor(colores[fila][2]);
        g.fillRect(10, y, ancho, alto);    
        g.setColor(colores[fila][3]);
        g.fillRect(15, y, ancho, alto);    
        g.setColor(colores[fila][4]);
        g.fillRect(20, y, ancho, alto);    
        g.setColor(colores[fila][5]);
        g.fillRect(25, y, ancho, alto);    
        g.setColor(colores[fila][6]);
        g.fillRect(30, y, ancho, alto);    
        g.setColor(colores[fila][7]);
        g.fillRect(35, y, ancho, alto);    
    
        y = 15;
        fila = 3;
        g.setColor(colores[fila][0]);
        g.fillRect(0, y, ancho, alto);    
        g.setColor(colores[fila][1]);
        g.fillRect(5, y, ancho, alto);    
        g.setColor(colores[fila][2]);
        g.fillRect(10, y, ancho, alto);    
        g.setColor(colores[fila][3]);
        g.fillRect(15, y, ancho, alto);    
        g.setColor(colores[fila][4]);
        g.fillRect(20, y, ancho, alto);    
        g.setColor(colores[fila][5]);
        g.fillRect(25, y, ancho, alto);    
        g.setColor(colores[fila][6]);
        g.fillRect(30, y, ancho, alto);    
        g.setColor(colores[fila][7]);
        g.fillRect(35, y, ancho, alto);    

        y = 20;
        fila = 4;
        g.setColor(colores[fila][0]);
        g.fillRect(0, y, ancho, alto);    
        g.setColor(colores[fila][1]);
        g.fillRect(5, y, ancho, alto);    
        g.setColor(colores[fila][2]);
        g.fillRect(10, y, ancho, alto);    
        g.setColor(colores[fila][3]);
        g.fillRect(15, y, ancho, alto);    
        g.setColor(colores[fila][4]);
        g.fillRect(20, y, ancho, alto);    
        g.setColor(colores[fila][5]);
        g.fillRect(25, y, ancho, alto);    
        g.setColor(colores[fila][6]);
        g.fillRect(30, y, ancho, alto);    
        g.setColor(colores[fila][7]);
        g.fillRect(35, y, ancho, alto);    

        y = 25;
        fila = 5;
        g.setColor(colores[fila][0]);
        g.fillRect(0, y, ancho, alto);    
        g.setColor(colores[fila][1]);
        g.fillRect(5, y, ancho, alto);    
        g.setColor(colores[fila][2]);
        g.fillRect(10, y, ancho, alto);    
        g.setColor(colores[fila][3]);
        g.fillRect(15, y, ancho, alto);    
        g.setColor(colores[fila][4]);
        g.fillRect(20, y, ancho, alto);    
        g.setColor(colores[fila][5]);
        g.fillRect(25, y, ancho, alto);    
        g.setColor(colores[fila][6]);
        g.fillRect(30, y, ancho, alto);    
        g.setColor(colores[fila][7]);
        g.fillRect(35, y, ancho, alto);    
        
        y = 30;
        fila = 6;
        g.setColor(colores[fila][0]);
        g.fillRect(0, y, ancho, alto);    
        g.setColor(colores[fila][1]);
        g.fillRect(5, y, ancho, alto);    
        g.setColor(colores[fila][2]);
        g.fillRect(10, y, ancho, alto);    
        g.setColor(colores[fila][3]);
        g.fillRect(15, y, ancho, alto);    
        g.setColor(colores[fila][4]);
        g.fillRect(20, y, ancho, alto);    
        g.setColor(colores[fila][5]);
        g.fillRect(25, y, ancho, alto);    
        g.setColor(colores[fila][6]);
        g.fillRect(30, y, ancho, alto);    
        g.setColor(colores[fila][7]);
        g.fillRect(35, y, ancho, alto);    
        
        y = 35;
        fila = 7;
        g.setColor(colores[fila][0]);
        g.fillRect(0, y, ancho, alto);    
        g.setColor(colores[fila][1]);
        g.fillRect(5, y, ancho, alto);    
        g.setColor(colores[fila][2]);
        g.fillRect(10, y, ancho, alto);    
        g.setColor(colores[fila][3]);
        g.fillRect(15, y, ancho, alto);    
        g.setColor(colores[fila][4]);
        g.fillRect(20, y, ancho, alto);    
        g.setColor(colores[fila][5]);
        g.fillRect(25, y, ancho, alto);    
        g.setColor(colores[fila][6]);
        g.fillRect(30, y, ancho, alto);    
        g.setColor(colores[fila][7]);
        g.fillRect(35, y, ancho, alto);    
   
    }
    
    public void crearImagen() {
        try {
            ImageIO.write(imagen, formato, fichero);
        } catch (IOException e) {
            System.out.println("Error de escritura");
        }
    }
    
    public void guardarXML() throws Exception{
        cd = new CreadordeDocs(avatars, "jugadores");
        Document documento = cd.getDocumento();
        Element raiz = cd.getElementoRaiz();
        generarContenido(documento, raiz);
        cd.generarXML(avatars,documento);
    }

    private void generarContenido(Document documento, Element raiz) {
        Element avatar = documento.createElement("avatar");
        raiz.appendChild(avatar);
        
        Element nickName = documento.createElement("nickname");
        nickName.appendChild(documento.createTextNode(nickNameJugador));
        avatar.appendChild(nickName);

        Element colores = documento.createElement("colores");
        colores.appendChild(documento.createTextNode(obtenerColores()));
        avatar.appendChild(colores);
    }

    private String obtenerColores() {
        String palabra = "";
        for (Color[] arrColores : colores) {
            for (Color color : arrColores) {
                palabra += color.getRed() + " " + color.getGreen()+ " " + color.getBlue() +
                        "\n				 "; 
            }
            palabra += "\n				 ";
        }
        return palabra;
    }
    
    
}
