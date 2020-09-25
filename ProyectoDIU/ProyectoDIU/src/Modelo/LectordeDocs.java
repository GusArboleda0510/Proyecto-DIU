/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author ANDRES ARBOLEDA
 */
public class LectordeDocs {
    File file;
    Document documento;
    public LectordeDocs(File file) throws ParserConfigurationException, SAXException, IOException{
        this.file = file;
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        documento = db.parse(file);
    }
    
    public void generarXML(File file, Document documento) throws TransformerConfigurationException, IOException, TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();   
        Source fuente = new DOMSource(documento);
        StreamResult rest;
        rest = new StreamResult(file);
        t.transform(fuente, rest);
    }  
    
    
    public Document getDocumento(){
        return documento;
    }    
    
}
