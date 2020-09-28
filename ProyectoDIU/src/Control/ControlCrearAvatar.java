/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.CreadordeDocs;
import Modelo.LectordeDocs;
import Vista.MensajeSeleccion;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author ANDRES ARBOLEDA
 */
public class ControlCrearAvatar {
    BufferedImage imagen = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
    File archivoImagen;
    String formato = "png";
    Color[][] colores = new Color [2][6];
    CreadordeDocs cd;
    LectordeDocs ld;
    File avatars = new File("Avatars.xml");
    String nickNameJugador;
    boolean reescribir;
    public ControlCrearAvatar(Color[][] colores, String nombreAvatar) throws Exception {
        this.colores = colores;  
        nickNameJugador = nombreAvatar;       
        initImagen();
        if(!imagenExisteXML()){
            dibujarImagen();
            crearImagen();
            guardarXML();  
        }else{
            if(reescribir){
                reemplazarAvatar();
            }
        }
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
            ImageIO.write(imagen, formato, archivoImagen);
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

    private void initImagen() throws Exception {
        archivoImagen = new File("src/Imagenes/Avatars/Usuarios/"+nickNameJugador+".png");
        if(archivoImagen.exists()){
            String palabra = "El nickname ingresado ya tiene -un avatar, desea Reescribirlo?";
            MensajeSeleccion ra = new MensajeSeleccion(null, true, palabra);
            reescribir = ra.isReescribir();
            if(!ra.isReescribir()){
                throw new Exception("no reescribir");
            }
        }
    }

    private boolean imagenExisteXML() throws Exception {
        if(avatars.exists()){
            ld = new LectordeDocs(avatars);
            Document documento = ld.getDocumento();
            NodeList lista = documento.getElementsByTagName("avatar");
            for (int n = 0; n < lista.getLength(); n++) {
                Node nodo = lista.item(n);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) nodo;
                    if (elemento.getElementsByTagName("nickname").item(0).getTextContent().equals(nickNameJugador)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
        private void reemplazarAvatar() throws Exception {
        ld = new LectordeDocs(avatars);
        Document documento = ld.getDocumento();
        NodeList lista = documento.getElementsByTagName("avatar");
        for (int n = 0; n < lista.getLength(); n++) {
            Node nodo = lista.item(n);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element avat = (Element) nodo;

                if (avat.getElementsByTagName("nickname").item(0).getTextContent().equals(nickNameJugador)) {
                    
                    Node viejo = avat.getElementsByTagName("colores").item(0);
                    Node nuevo = viejo;  
                    nuevo.setTextContent(obtenerColores());
                    avat.replaceChild(nuevo, viejo);       
                }
            }
        }
        System.out.println("control 1");

        cd.generarXML(avatars,documento);
                                            System.out.println("control 2");

    } 
}
