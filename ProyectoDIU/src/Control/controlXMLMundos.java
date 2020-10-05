/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;
import Modelo.CreadordeDocs;
import Modelo.LectordeDocs;
import java.io.File;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 *
 * @author ANDRES ARBOLEDA
 */
public class controlXMLMundos {
    LectordeDocs ld;
    File mundos = new File("persistencia/Mundos.xml");
    int CantEnemigos;
    String [] skinEnemigos = new String[3];;
    public controlXMLMundos() throws Exception{
        ld = new LectordeDocs(mundos);    
    }

    public int getCantEnemigos() {
        return CantEnemigos;
    }

    public String[] getSkinEnemigos() {
        return skinEnemigos;
    }
    
    public void consultarXML(String nombreMundo, String nombreMapa) throws Exception {
        Document documento = ld.getDocumento();
        NodeList lista = documento.getElementsByTagName("mundo");
        for (int n = 0; n < lista.getLength(); n++) {
            Node nodo = lista.item(n);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element mundos = (Element) nodo;
                if (mundos.getElementsByTagName("nombreMundo").item(0).getTextContent().equalsIgnoreCase(nombreMundo)) {  
                    int cantMapas = Integer.parseInt(mundos.getElementsByTagName("cantidadMapas").item(0).getTextContent());
                    String nombMapaXML = null;
                    Element mapa = null;
                    for (int i = 0; i < cantMapas; i++) {
                        mapa = (Element)mundos.getElementsByTagName("mapa").item(i);
                        nombMapaXML = mapa.getAttributes().getNamedItem("NombreMapa").getNodeValue();
                        if(nombreMapa.equals(nombMapaXML)){
                            break;
                        }
                    }
                    CantEnemigos = Integer.parseInt(mapa.getElementsByTagName("cantidadEmemigos").item(0).getTextContent());
                    for (int m = 0; m < CantEnemigos; m++) {
                        skinEnemigos[m] =  mapa.getElementsByTagName("skinEnemigo").item(m).getTextContent();
                    }
                }
            }
        }
    }
}
