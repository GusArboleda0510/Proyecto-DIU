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
import javax.swing.ImageIcon;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author ANDRES ARBOLEDA
 */
public class ControlCrearAvatar {
    BufferedImage imagen;
    File archivoImagen;
    String formato = "png";
    Color[][] colores = new Color [2][6];
    CreadordeDocs cd;
    LectordeDocs ld;
    File avatars = new File("persistencia/Avatars.xml");
    String nickNameJugador;
    boolean reescribir;
    String predPequenia = null, predGrande = null;
    String rutaImgPeque;
    public ControlCrearAvatar(Color[][] colores, String nombreAvatar, String ruta, String imagenGrande) throws Exception {
        this.colores = colores;  
        predPequenia = ruta;
        nickNameJugador = nombreAvatar;  
        imagen = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
        if(predPequenia == null){
           predPequenia = "/Imagenes/Avatars/Usuarios/"+nickNameJugador+".png"; 
        }
        rutaImgPeque = "/Imagenes/Avatars/Usuarios/"+nickNameJugador+".png"; 
        predGrande = imagenGrande;
        initImagen();
        if(predGrande == null){
            dibujarImagen();       
        }else{
            insertarImagen();
        }
        
//        crearImagen();

        cd = new CreadordeDocs(avatars, "jugadores");
        if(!imagenExisteXML()){
            guardarXML();  
        }else{
            if(reescribir){
                reemplazarAvatar();
            }
        }
    }
    public ControlCrearAvatar(){
        
    }
    
    public void crearImagen() {
        try {
            ImageIO.write(imagen, formato, archivoImagen);
        } catch (IOException e) {
            System.out.println("Error de escritura");
        }  
    }
    
    public void guardarXML() throws Exception{     
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
        
        String palabraColores;
        if(predGrande == null){
            predPequenia = " ";
            predGrande = " ";
            palabraColores = obtenerColores();
        }else{
            palabraColores = " ";
        }  
                
        Element colores = documento.createElement("colores");
        colores.appendChild(documento.createTextNode(palabraColores));
        avatar.appendChild(colores);             
        
        Element rutapeq = documento.createElement("imgjuego");
        rutapeq.appendChild(documento.createTextNode(predPequenia));
        avatar.appendChild(rutapeq);

        Element rutaGrande = documento.createElement("imgVista");
        rutaGrande.appendChild(documento.createTextNode(predGrande));
        avatar.appendChild(rutaGrande);

    }

    private String obtenerColores() {
        String palabra = "";
        int n = colores.length;
        palabra += " \n			";          
        for (Color[] arrColores : colores) {
            for (Color color : arrColores) {
                palabra += formato(color.getRed()) + " " + formato(color.getGreen())+ " " + 
                        formato(color.getBlue()) +
                        "\n			"; 
            }
            if(n > 1){
                palabra += "\n			";          
            }
            n--;
        }
        return palabra;
    }
    private String formato(int num) {
        String numero = "";
        if(num == 0){
            numero += num+"00";
        }else{
            String aux = num+"";
            if(aux.length() == 2){
                numero += "0"+num;
            }else{
                if(aux.length() == 1){
                    numero += "00"+num;
                }else{
                    numero = num+"";            
                }            
            }
        }
        return numero;
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
                    if (elemento.getElementsByTagName("nickname").item(0).getTextContent().equalsIgnoreCase(nickNameJugador)) {
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
                if (avat.getElementsByTagName("nickname").item(0).getTextContent().equalsIgnoreCase(nickNameJugador)) {                    
                    Node viejo = avat.getElementsByTagName("nickname").item(0);
                    Node nuevo = viejo;  
                    nuevo.setTextContent(nickNameJugador);
                    avat.replaceChild(nuevo, viejo); 
                    String palabraColores;
                    if(predGrande == null){
                        predPequenia = " ";
                        predGrande = " ";
                        palabraColores = obtenerColores();
                    }else{
                        palabraColores = " ";
                    }  
                    viejo = avat.getElementsByTagName("colores").item(0);
                    nuevo = viejo;  
                    nuevo.setTextContent(palabraColores);
                    avat.replaceChild(nuevo, viejo);   
                    
                    
                    viejo = avat.getElementsByTagName("imgjuego").item(0);
                    nuevo = viejo;  
                    nuevo.setTextContent(predPequenia);
                    avat.replaceChild(nuevo, viejo);   
                    
                    viejo = avat.getElementsByTagName("imgVista").item(0);
                    nuevo = viejo;  
                    nuevo.setTextContent(predGrande);
                    avat.replaceChild(nuevo, viejo); 
                    
                }
            }
        }
        cd.generarXML(avatars,documento);

    }
        
    private void insertarImagen() {
        Graphics g = imagen.getGraphics();
        ImageIcon pequenia = new ImageIcon(new ImageIcon(getClass().getResource(predPequenia)).getImage());
        g.drawImage(pequenia.getImage(), 0, 0, null);
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

    public String[] obtenerNickNames() throws Exception {
        String[] nombres;
        ld = new LectordeDocs(avatars);
        Document documento = ld.getDocumento();
        NodeList lista = documento.getElementsByTagName("avatar");
        nombres = new String[lista.getLength()];
        for (int n = 0; n < lista.getLength(); n++) {
            Node nodo = lista.item(n);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element avat = (Element) nodo;
                nombres[n] = avat.getElementsByTagName("nickname").item(0).getTextContent();
            }
        }
        return nombres;
    }

    
    public String consultarXML(String nombre) throws Exception {
        String dondeBuscar = "";
        ld = new LectordeDocs(avatars);
        Document documento = ld.getDocumento();
        NodeList lista = documento.getElementsByTagName("avatar");
        for (int n = 0; n < lista.getLength(); n++) {
            Node nodo = lista.item(n);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element avat = (Element) nodo;
                if (avat.getElementsByTagName("nickname").item(0).getTextContent().equalsIgnoreCase(nombre)) {
                    String palabra = avat.getElementsByTagName("colores").item(0).getTextContent();
                    if(!palabra.equals(" ")){
                        dondeBuscar = "colores";
                        colores = obtenerColoresDeXML(palabra);
                        nickNameJugador = avat.getElementsByTagName("nickname").item(0).getTextContent();
                    }else{
                        if(!avat.getElementsByTagName("imgjuego").item(0).getTextContent().equals(" ")){
                            dondeBuscar = "rutas";
                            predPequenia = avat.getElementsByTagName("imgjuego").item(0).getTextContent();
                            predGrande = avat.getElementsByTagName("imgVista").item(0).getTextContent();
                        }
                        
                    }
                }
            }
        }
        return dondeBuscar;
    }

    private Color[][] obtenerColoresDeXML(String palabra) {
        Color[][] colors= new Color [8][8];
        int r=0, g=0, b=0;
        String[] aux0 = palabra.split("\n");
        int cols = 0;
        for (int j = 0; j < colors.length; j++) {
            cols++;

            for (int n = 0; n < colors[0].length; n++) {
                String[] aux1 = aux0[cols].split(" ");
                String pal = aux1[aux1.length-3];
                r = Integer.parseInt(pal.substring(pal.length()-3, pal.length()));
                g = Integer.parseInt(aux1[aux1.length-2]);
                b = Integer.parseInt(aux1[aux1.length-1]);
                colors[j][n] = new Color(formatoFondo(r), formatoFondo(g), formatoFondo(b));
                cols++;
            } 
        }
        return colors;
    }
    
    public Color[][] getColores() {
        return colores;
    }

    public String getPredPequenia() {
        return predPequenia;
    }

    public String getPredGrande() {
        return predGrande;
    }

    private int formatoFondo(int r) {
        if(r == 233){
            r = 253;
        }
        return r;
    }

    public String getRutaImgPeque() {
        return rutaImgPeque;
    }
    
    public String obtenerRutaImgPeque() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void hacerImgAgain() {
        imagen = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
        dibujarImagen();
        archivoImagen = new File("src/Imagenes/Avatars/Usuarios/"+nickNameJugador+".png");
//        crearImagen();
    }
}
